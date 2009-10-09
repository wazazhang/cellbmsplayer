package com.cell.bms.oal;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import net.java.games.joal.AL;
import net.java.games.joal.util.ALut;

import com.cell.CIO;
import com.cell.bms.BMSFile;
import com.cell.bms.IDefineSound;

public class JALSound implements IDefineSound
{
	final JALNoteFactory factory;
	final AL al;
	String			sound_name;
	
	// Buffers hold sound data.
	int[] 			buffer;

	int[] 			format 	= new int[1];
	int[] 			size 	= new int[1];
	ByteBuffer[] 	data 	= new ByteBuffer[1];
	int[] 			freq 	= new int[1];
	int[] 			loop	= new int[1];

	
	
	public JALSound(JALNoteFactory factory, BMSFile bms, String sound) 
	{
		this.factory	= factory;
		this.al			= factory.al;
		this.sound_name	= sound;

		synchronized(al)
		{
			if (sound.toLowerCase().endsWith(".wav")) 
			{
				initWav(bms.bms_dir + "/" + sound);
			}
			else if (sound.toLowerCase().endsWith(".ogg")) 
			{
				initOgg(bms.bms_dir + "/" + sound);
			}
		}

	}
	
	private void initWav(String url)
	{
		InputStream is = CIO.loadStream(url);
		
		if (is != null) {
			
			try {
				
				// Load wav data into a buffer.
				{
					ALut.alutLoadWAVFile(is, format, data, size, freq, loop);
	
					if (data[0] == null) {
						System.err.println("Error loading WAV file : " + url);
						return;
					}
				}
				
				// variables to load into
				{
					int[] buffer = new int[1];
	
					al.alGenBuffers(1, buffer, 0);
	
					if (al.alGetError() != AL.AL_NO_ERROR) {
						System.err.println("Error generating OpenAL buffers : " + url);
						return;
					}
	
					al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);
	
					// Do another error check and return.
					if (al.alGetError() != AL.AL_NO_ERROR) {
						System.err.println("Error bind WAV file : " + url);
						return;
					}
					
					this.buffer = buffer;
				}


				// System.out.println(
				// "Create AL sound" +
				// " : size =" + size[0] +
				// " : freq = " + freq[0] +
				// " : " + sound);

			} catch (Exception err) {
				err.printStackTrace();
			}
		}

	}

	private void initOgg(String url)
	{
		try 
		{
			{
				OggDecoder oggDecoder = new OggDecoder(new URL(url));
		
				if (!oggDecoder.initialize()) {
					System.err.println("Error initializing ogg stream...");
					return;
				}
	
				int numChannels = oggDecoder.numChannels();
				
				if (numChannels == 1)
				    format[0] = AL.AL_FORMAT_MONO16;
				else
				    format[0] = AL.AL_FORMAT_STEREO16;
			        
				freq[0] = oggDecoder.sampleRate();
		
				// format, data, size, freq,
			}
			
			// variables to load into
			{
				int[] buffer = new int[1];

				al.alGenBuffers(1, buffer, 0);

				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error generating OpenAL buffers : " + url);
					return;
				}

				al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

				// Do another error check and return.
				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error bind WAV file : " + url);
					return;
				}

				this.buffer = buffer;
			}

			
		} catch (Exception err) {
			err.printStackTrace();
		}
	
	}
	
	@Override
	public void play() {
		if (buffer!=null) {
			JALPlayer player = factory.getFreePlayer();
			if (player != null) {
				player.bindBuffer(this);
				player.play();
			} else {
				System.err.println("Can not play, no free source ! " + sound_name);
			}
		}
	}
	
	@Override
	public void dispose() {
		if (buffer!=null) {
			al.alDeleteBuffers(1, buffer, 0);
			System.out.println("alDeleteBuffers : ");
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		dispose();
	}
}
