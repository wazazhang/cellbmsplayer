package com.cell.bms.oal;

import net.java.games.joal.AL;
import net.java.games.sound3d.AudioSystem3D;
import net.java.games.sound3d.Source;

public class JALPlayer 
{
	final AL al;

	// Sources are points emitting sound.
	private int[] source = new int[1];

	public JALPlayer(AL al) throws Exception
	{
		this.al = al;

		// Bind buffer with a source.
		al.alGenSources(1, source, 0);

		if (al.alGetError() != AL.AL_NO_ERROR) {
			throw new Exception("Error generating OpenAL source !");
		}

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
		
	}
	
	public void bindBuffer(JALSound sound)
	{
		al.alSourcei(source[0], AL.AL_BUFFER,	sound.buffer[0]);
		al.alSourcef(source[0], AL.AL_PITCH,	1.0f);
		al.alSourcef(source[0], AL.AL_GAIN,		1.0f);
		al.alSourcei(source[0], AL.AL_LOOPING,	sound.loop[0]);

		// Do another error check
		if (al.alGetError() != AL.AL_NO_ERROR) {
			System.err.println("Error setting up OpenAL source : " + sound.sound_name);
			return;
		}
		
	}

	public boolean isFree() {
		int state[] = new int[1];
		al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state, 0);
		return state[0] != AL.AL_PLAYING;
	}
	
	public void play() {
		al.alSourcePlay(source[0]);
	}
	
	public void pause() {
		al.alSourcePause(source[0]);
	}
	
	public void stop() {
        al.alSourceStop(source[0]);
	}
	
	public void dispose() {
	    al.alDeleteSources(1, source, 0);
	    System.out.println("alDeleteSources : ");
	}
	
	@Override
	protected void finalize() throws Throwable {
		dispose();
	}
	

}
