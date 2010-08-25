package com.cell.sound.openal_impl;


import com.cell.sound.IPlayer;

import net.java.games.joal.AL;

public abstract class JALSource implements IPlayer
{
	final AL	al;
	int[]		source;
	
	JALSource(AL al) throws Exception
	{
		this.al = al;
		JALSoundManager.checkError(al);
		{
			int[] source = new int[1];
			
			// Bind buffer with a source.
			al.alGenSources(1, source, 0);
			if (JALSoundManager.checkError(al)) {
				throw new Exception("Error generating OpenAL source !");
			} else {
				this.source = source;
			}

			System.out.println(
			"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" +
			"S Create sound : " + source[0] + " : " + this + "\n"+
			"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");

			
			float[] zero_v = { 0.0f, 0.0f, 0.0f };
			
			al.alSourcefv(source[0], AL.AL_POSITION,	zero_v, 0);
			al.alSourcefv(source[0], AL.AL_VELOCITY,	zero_v, 0);
			al.alSourcefv(source[0], AL.AL_DIRECTION,	zero_v, 0);
			
			al.alSourcef(source[0], AL.AL_PITCH,			1.0f);
			al.alSourcef(source[0], AL.AL_GAIN,				1.0f);
			al.alSourcef(source[0], AL.AL_ROLLOFF_FACTOR, 	0.0f);

			al.alSourcei(source[0], AL.AL_SOURCE_RELATIVE, 	AL.AL_TRUE);
			al.alSourcei(source[0], AL.AL_LOOPING,			0);
			
			JALSoundManager.checkError(al);
		}
	}

	synchronized public void dispose() {
		if (source != null) {
			try {
				System.out.println(
						"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" +
						"S Dispose sound : " + source[0] + " : " + this + "\n"+
						"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
				clearAllSound();
				al.alDeleteSources(1, source, 0);
			} finally {
				this.source = null;
			}
			
		}
	}

	@Override
	synchronized public void play(boolean loop) {
		if (source != null) {
			al.alSourcei(source[0], AL.AL_LOOPING,	loop?1:0);
			if (JALSoundManager.checkError(al)) {
			} else {
				al.alSourcePlay(source[0]);
				JALSoundManager.checkError(al);
				al.alSourcef(source[0], AL.AL_PITCH,			1.0f);
				al.alSourcef(source[0], AL.AL_GAIN,				1.0f);
			}
		}
	}
	
	synchronized public void pause() {
		if (source!=null){
			al.alSourcePause(source[0]);
			JALSoundManager.checkError(al);
		}
	}
	
	@Override
	synchronized public void resume() {
		if (source!=null){
			al.alSourcePlay(source[0]);
			JALSoundManager.checkError(al);
		}
	}
	
	synchronized public void stop() {
		if (source!=null){
			al.alSourceStop(source[0]);
			JALSoundManager.checkError(al);
		}
	}

	@Override
	synchronized public boolean isPlaying() {
		if (source != null) {
			int state[] = new int[1];
			al.alGetSourcei(source[0], AL.AL_SOURCE_STATE, state, 0);
			if (JALSoundManager.checkError(al)) {
				return true;
			}
			return state[0] == AL.AL_PLAYING;
		}
		return true;
	}
	
	protected void clearAllSound() {
		if (source != null) {
			// stop all sound
			al.alSourceStop(source[0]);
			JALSoundManager.checkError(al);
			// clean all queued sound
			int[] queued = new int[1];
			al.alGetSourcei(source[0], AL.AL_BUFFERS_QUEUED, queued, 0);
			JALSoundManager.checkError(al);
			
			int[] buffers = new int[queued[0]];
			for (int n = 0; n < buffers.length; n++) {
				al.alSourceUnqueueBuffers(source[0], 1, buffers, n);
				JALSoundManager.checkError(al);
			}
			// clean all sound
			al.alSourcei(source[0], AL.AL_BUFFER, 0);
			JALSoundManager.checkError(al);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
}
