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
	int[] 					buffer;
	JALPlayer				binded_source;
	
	JALSound(JALSoundManager factory, SoundInfo info) throws Exception
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
					throw new Exception("Error generating OpenAL buffers : " + toString());
				}
				
				al.alBufferData(buffer[0], format, info.data, info.size, info.frame_rate);

				// Do another error check and return.
				if (al.alGetError() != AL.AL_NO_ERROR) {
					al.alDeleteBuffers(1, buffer, 0);
					throw new Exception(("Error bind WAV file : " + toString()));
				}
				
				this.buffer = buffer;
				
//				System.out.println("init buffer " + buffer[0]);
			}
		}
	}
	
	
	public void dispose() {
		synchronized(al) {
			if (binded_source!=null && binded_source.al_sound==this) {				
				binded_source.stop();
				binded_source.setSound(null);
				binded_source = null;
//				System.out.println("source stop ");
			}
			if (buffer!=null) {
//				System.out.println("dispose buffer " + buffer[0]);
				al.alDeleteBuffers(1, buffer, 0);
				buffer = null;
				int error = al.alGetError();
				if (error != AL.AL_NO_ERROR) {
					try{
						throw new Exception("error code " + error);
					}catch(Exception err){
						err.printStackTrace();
					}
				}
			}
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
