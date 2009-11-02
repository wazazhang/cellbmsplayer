package com.cell.sound.openal_impl;

import java.io.InputStream;
import java.nio.ByteBuffer;

import net.java.games.joal.AL;

import com.cell.CIO;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;

public class JALSound implements ISound
{
	final JALSoundManager	factory;
	final AL 				al;
	final SoundInfo			info;
	
	// Buffers hold sound data.
	int[] 			buffer;
	
	JALSound(JALSoundManager factory, SoundInfo info)
	{
		this.factory	= factory;
		this.al			= factory.al;
		this.info		= info;
		
		synchronized(al)
		{
			int format = AL.AL_FORMAT_MONO16;
			if (info.bit_length == 16) {
				if (info.channels==1) {
					format = AL.AL_FORMAT_MONO16;
				} else {
					format = AL.AL_FORMAT_STEREO16;
				}
			}
			else if (info.bit_length == 8) {
				if (info.channels==1) {
					format = AL.AL_FORMAT_MONO8;
				} else {
					format = AL.AL_FORMAT_STEREO8;
				}
			}
			
			// variables to load into
			{
				int[] buffer = new int[1];

				al.alGenBuffers(1, buffer, 0);

				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error generating OpenAL buffers : " + toString());
					return;
				}
				
				al.alBufferData(buffer[0], format, info.data, info.size, info.frame_rate);

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
		}
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " : " + info.resource;
	}
	
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
