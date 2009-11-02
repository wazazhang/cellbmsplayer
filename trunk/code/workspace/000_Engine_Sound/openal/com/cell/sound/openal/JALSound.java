package com.cell.sound.openal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import net.java.games.joal.AL;
import net.java.games.joal.util.ALut;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.sound.ISound;

import de.jarnbjo.ogg.CachedUrlStream;
import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.PhysicalOggStream;
import de.jarnbjo.ogg.UncachedUrlStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisStream;

public class JALSound implements ISound
{
	final JALSoundManager	factory;
	final AL 				al;
	
	// Buffers hold sound data.
	String			sound_name;
	int[] 			buffer;

	int[] 			format 	= new int[1];
	int[] 			size 	= new int[1];
	ByteBuffer[] 	data 	= new ByteBuffer[1];
	int[] 			freq 	= new int[1];
	int[] 			loop	= new int[1];

	
	public JALSound(JALSoundManager factory, String resource) 
	{
		this.factory	= factory;
		this.al			= factory.al;
		this.sound_name	= resource;

		synchronized(al)
		{
			String last = sound_name.toLowerCase();
			
			if (last.endsWith(".wav")) {
				initWav(sound_name);
			}
			else if (last.endsWith(".ogg")) {
				initOgg(sound_name);
			}
		}

	}
	
	private void initWav(String file)
	{
		InputStream is = CIO.loadStream(file);
		
		if (is != null) {
			
			try {
				
				// Load wav data into a buffer.
				{
					ALut.alutLoadWAVFile(is, format, data, size, freq, loop);
					if (data[0] == null) {
						System.err.println("Error loading WAV file : " + file);
						return;
					}
				}
				
				// variables to load into
				{
					int[] buffer = new int[1];
	
					al.alGenBuffers(1, buffer, 0);
	
					if (al.alGetError() != AL.AL_NO_ERROR) {
						System.err.println("Error generating OpenAL buffers : " + file);
						return;
					}
	
					al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);
	
					// Do another error check and return.
					if (al.alGetError() != AL.AL_NO_ERROR) {
						System.err.println("Error bind WAV file : " + file);
						return;
					}
					
					this.buffer = buffer;
				}

			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	private void initOgg(String file)
	{
		try 
		{
			{
				PhysicalOggStream os = null;
				AudioFormat audioFormat = null;
				ByteArrayOutputStream baos = new ByteArrayOutputStream(10240);
				
				URL url = CIO.getResourceURL(file);
				if (url != null) {
					os = new UncachedUrlStream(url);
				} else {
					File of = new File(file);
					if (of.exists()) {
						os = new FileStream(new RandomAccessFile(of, "r"));
					}
				}
				if (os == null) {
					System.err.println("can not create sound : " + file);
				}
				
				
				for (Object los : os.getLogicalStreams()) 
				{
					LogicalOggStream		loStream	= (LogicalOggStream)los;
					VorbisStream			vStream		= new VorbisStream(loStream);
					IdentificationHeader	vStreamHdr	= vStream.getIdentificationHeader();
					
					audioFormat	= new AudioFormat(
							(float) vStreamHdr.getSampleRate(),
							16, 
							vStreamHdr.getChannels(),
							true, true);
										
					try {
						byte t = 0;
						byte[] data = new byte[2];
						while(true) {
							vStream.readPcm(data, 0, data.length);
							t = data[0];
							data[0] = data[1];
							data[1] = t;
							baos.write(data);
						}
					} catch (EndOfOggStreamException e) {}
					
					vStream.close();
					
					loStream.close();

				}
				
				os.close();
				
			    if (audioFormat.getChannels() == 1)
				    format[0]	= AL.AL_FORMAT_MONO16;
				else
				    format[0]	= AL.AL_FORMAT_STEREO16;
			    
				data[0]			= ByteBuffer.wrap(baos.toByteArray());
				size[0]			= baos.size();
				freq[0]			= (int)audioFormat.getFrameRate();
				

				System.out.println(audioFormat + " size = " + baos.size() + " : " + file);
			}
			
			// variables to load into
			{
				int[] buffer = new int[1];

				al.alGenBuffers(1, buffer, 0);

				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error generating OpenAL buffers : " + file);
					return;
				}

				al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

				// Do another error check and return.
				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error bind WAV file : " + file);
					return;
				}

				this.buffer = buffer;
			}

			
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	
	public void dispose() {
		if (buffer!=null) {
			al.alDeleteBuffers(1, buffer, 0);
			System.out.println("alDeleteBuffers : ");
		}
	}
	
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
