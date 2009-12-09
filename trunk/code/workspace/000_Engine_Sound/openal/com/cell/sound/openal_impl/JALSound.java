package com.cell.sound.openal_impl;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;

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
	HashSet<JALPlayer>		binded_source = new HashSet<JALPlayer>(1);
	
	
	JALSound(JALSoundManager factory, SoundInfo info) throws Exception
	{
		
		this.factory	= factory;
		this.al			= factory.al;
		this.info		= info;
		JALSoundManager.checkError(al);

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
				
				if (JALSoundManager.checkError(al)) {
					throw new Exception("Error generating OpenAL buffers : " + toString());
				}
				
				al.alBufferData(buffer[0], format, info.data, info.size, info.frame_rate);
				// Do another error check and return.
				if (JALSoundManager.checkError(al)) {
					al.alDeleteBuffers(1, buffer, 0);
					JALSoundManager.checkError(al);
					throw new Exception(("Error bind WAV file : " + toString()));
				}
				
				this.buffer = buffer;
				
//				System.out.println("init buffer " + buffer[0]);
			}
		}
	}
	
	@Override
	public SoundInfo getSoundInfo() {
		return info;
	}
	
	synchronized public void dispose() 
	{
		for (JALPlayer p : binded_source) {
			if (p.al_sound==this) {				
				p.stop();
				p.setSound(null);
			}
		}
		binded_source.clear();
		
		if (buffer!=null) {
//			System.out.println("dispose buffer " + buffer[0]);
			al.alDeleteBuffers(1, buffer, 0);
			if (JALSoundManager.checkError(al)) {}
			buffer = null;
		}
	}
	
	@Override
	synchronized public String toString() {
		return getClass().getName() + " : " + info.resource;
	}
	
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
