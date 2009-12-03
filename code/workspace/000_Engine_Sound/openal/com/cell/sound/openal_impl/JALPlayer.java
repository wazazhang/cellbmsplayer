package com.cell.sound.openal_impl;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;

import net.java.games.joal.AL;
import net.java.games.sound3d.AudioSystem3D;
import net.java.games.sound3d.Source;

public class JALPlayer implements IPlayer, Comparable<JALPlayer>
{
	final AL al;

	final int[]	source;

	JALSound			al_sound;
	
	public long 		last_bind_time = 0;
	
	JALPlayer(AL al) throws Exception
	{
		this.al = al;
		al.alGetError();
		JALSoundManager.checkError(al);
		{
			int[] source = new int[1];
			
			// Bind buffer with a source.
			al.alGenSources(1, source, 0);
	
			if (JALSoundManager.checkError(al)) {
				throw new Exception("Error generating OpenAL source !");
			}
	
			this.source = source;
			
			float[] sourcePos = { 0.0f, 0.0f, 0.0f };
			float[] sourceVel = { 0.0f, 0.0f, 0.0f };
			
			al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
			al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);
			
			al.alSourcef(source[0], AL.AL_PITCH,	1.0f);
			al.alSourcef(source[0], AL.AL_GAIN,		1.0f);
			al.alSourcei(source[0], AL.AL_LOOPING,	0);
			
			JALSoundManager.checkError(al);
		}
	}
	
	@Override
	synchronized public void setSound(ISound sound)
	{
		if (source!=null) 
		{
			{
				if (al_sound!=null && al_sound.buffer!=null) {
					al.alSourcei(source[0], AL.AL_BUFFER, 0);
//					al.alSourceUnqueueBuffers(source[0], 1, al_sound.buffer, 0);
					if (JALSoundManager.checkError(al)) {
						System.err.println("Error : alSourceUnqueueBuffers : " + al_sound);
						return;
					}
				}
				
				if (sound instanceof JALSound) {
					al_sound = (JALSound)sound;
					if (al_sound.buffer != null) {
//						al.alSourceQueueBuffers(source[0], 1, al_sound.buffer, 0);
						al.alSourcei(source[0], AL.AL_BUFFER, al_sound.buffer[0]);
						if (JALSoundManager.checkError(al)) {
							System.err.println("Error : alSourceQueueBuffers : " + al_sound);
							return;
						}
						al_sound.binded_source = this;
						last_bind_time = System.currentTimeMillis();
					}
				}
			}
		}
	}

	@Override
	synchronized public ISound getSound() {
		return al_sound;
	}
	
	@Override
	synchronized public void play(boolean loop) {
		if (source!=null && al_sound!=null) {			
			al.alSourcei(source[0], AL.AL_LOOPING,	loop?1:0);
			if (JALSoundManager.checkError(al)) {
			} else {
				al.alSourcePlay(source[0]);
				if (JALSoundManager.checkError(al)) {}
			}
		}
	}
	
	synchronized public void pause() {
		if (source!=null){
			al.alSourcePause(source[0]);
			if (JALSoundManager.checkError(al)) {
			}
		}
	}
	
	synchronized public void stop() {
		if (source!=null){
			al.alSourceStop(source[0]);
			if (JALSoundManager.checkError(al)) {}
		}
	}

	@Override
	synchronized public boolean isPlaying() {
		if (source!=null) {
			int state[] = new int[1];
			al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state, 0);
			if (JALSoundManager.checkError(al)) {
				return true;
			}
			return state[0] == AL.AL_PLAYING;
		}
		return true;
	}
	
	synchronized public void dispose() {
		stop();
	}

	@Override
	synchronized public int compareTo(JALPlayer o) {
		return (int)(this.last_bind_time - o.last_bind_time);
	}
	
	@Override
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
