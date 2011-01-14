package com.cell.sound.openal_impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CUtil;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.IStreamPlayer;

import net.java.games.joal.AL;
import net.java.games.sound3d.AudioSystem3D;
import net.java.games.sound3d.Source;

public class JALPlayer extends JALSource implements IStreamPlayer
{
	int size = 0;
	
	JALPlayer(AL al) throws Exception
	{
		super(al);
	}
	
	@Override
	synchronized public void setSound(ISound sound)
	{
		if (source!=null) {
			if (sound instanceof JALSound) {
				JALSound al_sound = (JALSound)sound;
				if (al_sound.buffer != null) {
					al.alSourcei(source[0], AL.AL_BUFFER, al_sound.buffer[0]);
					JALSoundManager.checkError(al);
				}
			}
		}
	}

	@Override
	synchronized public void queue(ISound sound) 
	{
		if (source != null)
		{
			clearProcessed();
			
			if (sound instanceof JALSound) {
				JALSound al_sound = (JALSound) sound;
				if (al_sound.buffer != null) {
					al.alSourceQueueBuffers(source[0], 1, al_sound.buffer, 0);
					size += al_sound.getSize();
//					System.out.println("  queue" +
//							" : " + al_sound.buffer[0] + 
//							" : " + al_sound + 
//							" : total="+CUtil.getBytesSizeString(size));
					JALSoundManager.checkError(al);
				}
			}
		}
	}
}
