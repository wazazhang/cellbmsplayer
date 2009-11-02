package com.cell.sound.openal_impl;

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

import de.jarnbjo.ogg.BasicStream;
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
	int[] 			buffer;
	String			resource;
	
	int[] 			format 	= new int[1];
	int[] 			size 	= new int[1];
	ByteBuffer[] 	data 	= new ByteBuffer[1];
	int[] 			freq 	= new int[1];
	int[] 			loop	= new int[1];

	
	JALSound(JALSoundManager factory, String resource)
	{
		this(factory, resource, CIO.loadStream(resource));
	}
	
	JALSound(JALSoundManager factory, String name, InputStream is)
	{
		this.factory	= factory;
		this.al			= factory.al;
		this.resource	= name;
		name 			= name.toLowerCase();
		
		synchronized(al)
		{
			try{
				if (name.endsWith(".wav")) {
					JALSoundManager.initWav(is, format, size, data, freq, loop);
				}
				else if (name.endsWith(".ogg")) {
					JALSoundManager.initOgg(is, format, size, data, freq, loop);
				}
			}
			catch(Exception err) {
				System.err.println("Init error !" + toString());
				err.printStackTrace();
			}
			
			// variables to load into
			{
				int[] buffer = new int[1];

				al.alGenBuffers(1, buffer, 0);

				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error generating OpenAL buffers : " + toString());
					return;
				}

				al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

				// Do another error check and return.
				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error bind WAV file : " + toString());
					return;
				}
				
				this.buffer = buffer;
			}
		}
	}
	
	
	public void dispose() {
		if (buffer!=null) {
			al.alDeleteBuffers(1, buffer, 0);
			System.out.println("alDeleteBuffers : ");
		}
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " : " + resource;
	}
	
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
