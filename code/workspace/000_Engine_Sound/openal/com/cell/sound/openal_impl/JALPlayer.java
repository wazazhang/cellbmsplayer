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

		synchronized(al)
		{
			int[] source = new int[1];
			
			// Bind buffer with a source.
			al.alGenSources(1, source, 0);
	
			if (al.alGetError() != AL.AL_NO_ERROR) {
				throw new Exception("Error generating OpenAL source !");
			}
	
			this.source = source;
			
			// Position of the source sound.
			float[] sourcePos = { 0.0f, 0.0f, 0.0f };
			// Velocity of the source sound.
			float[] sourceVel = { 0.0f, 0.0f, 0.0f };
			
			// Note: for some reason the following two calls are producing an
			// error on one machine with NVidia's OpenAL implementation. This
			// appears to be harmless, so just continue past the error if one
			// occurs.
			al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
			al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);
			
			al.alSourcef(source[0], AL.AL_PITCH,	1.0f);
			al.alSourcef(source[0], AL.AL_GAIN,		1.0f);
			al.alSourcei(source[0], AL.AL_LOOPING,	0);
		}
	}
	
	@Override
	public void setSound(ISound sound) {
		if (source!=null && sound instanceof JALSound) {
			al_sound = (JALSound)sound;
			if (al_sound.buffer != null) {
				al.alSourcei(source[0], AL.AL_BUFFER,	al_sound.buffer[0]);
				// Do another error check
				if (al.alGetError() != AL.AL_NO_ERROR) {
					System.err.println("Error setting up OpenAL source : " + al_sound);
					return;
				}
				last_bind_time = System.currentTimeMillis();
			}
		}
	}

	@Override
	public ISound getSound() {
		return al_sound;
	}
	@Override
	public void play(boolean loop) {
		if (source!=null && al_sound!=null) {
			al.alSourcePlay(source[0]);
			al.alSourcei(source[0], AL.AL_LOOPING,	loop?1:0);
		}
	}
	public void pause() {
		if (source!=null){
			al.alSourcePause(source[0]);
		}
	}
	
	public void stop() {
		if (source!=null){
			al.alSourceStop(source[0]);
		}
	}

	@Override
	public boolean isPlaying() {
		if (source!=null) {
			int state[] = new int[1];
			al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state, 0);
			return state[0] == AL.AL_PLAYING;
		}
		return false;
	}
	
	public void dispose() {
		stop();
		if (source!=null) {
			//al.alDeleteSources(1, source, 0);
			//System.out.println("alDeleteSources : ");
		}
	}

	@Override
	public int compareTo(JALPlayer o) {
		return (int)(this.last_bind_time - o.last_bind_time);
	}
	
	@Override
	protected void finalize() throws Throwable {
		dispose();
	}
	
}
