package com.cell.sound.openal_impl;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;

import net.java.games.joal.AL;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;

public class JALSound implements ISound
{
	final JALSoundManager	factory;
	final AL 				al;
	final SoundInfo			info;
	final ByteBuffer		bytes;
	// Buffers hold sound data.
	int[] buffer;
	int size;
	
	JALSound(JALSoundManager factory, SoundInfo info) throws Exception
	{
		this.factory	= factory;
		this.al			= factory.al;
		this.info		= info;
		JALSoundManager.checkError(al);
		
		this.bytes = info.getData();
		if (bytes != null) 
		{
			this.size 	= bytes.remaining();
			
			int format = AL.AL_FORMAT_MONO16;
			if (info.getBitLength() == 16) {
				if (info.getChannels()==1) {
					format = AL.AL_FORMAT_MONO16;
				} else {
					format = AL.AL_FORMAT_STEREO16;
				}
			} else if (info.getBitLength() == 8) {
				if (info.getChannels()==1) {
					format = AL.AL_FORMAT_MONO8;
				} else {
					format = AL.AL_FORMAT_STEREO8;
				}
			}

			int[] buffer = new int[1];
			al.alGenBuffers(1, buffer, 0);
			if (JALSoundManager.checkError(al)) {
				throw new Exception("Error generating OpenAL buffers : " + toString());
			} else {
				this.buffer = buffer;
			}
			
			// variables to load into
			al.alBufferData(buffer[0], format, bytes, size, info.getFrameRate());
			// Do another error check and return.
			if (JALSoundManager.checkError(al)) {
				al.alDeleteBuffers(1, buffer, 0);
				if (JALSoundManager.checkError(al)) {}
				throw new Exception("Error generating OpenAL buffers : " + toString());
			}
		}
	}
	
	synchronized public void dispose() 
	{
		if (buffer != null) {
			al.alDeleteBuffers(1, buffer, 0);
			if (JALSoundManager.checkError(al)) {}
			buffer = null;
//			System.out.println("unbind sound !");
		}
	}

	@Override
	public SoundInfo getSoundInfo() {
		return info;
	}
	
	@Override
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + " : " + CUtil.getBytesSizeString(size);
	}
	
}
