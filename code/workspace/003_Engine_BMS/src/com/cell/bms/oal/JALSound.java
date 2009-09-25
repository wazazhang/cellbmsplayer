package com.cell.bms.oal;

import java.nio.ByteBuffer;

import net.java.games.joal.AL;
import net.java.games.joal.ALException;
import net.java.games.joal.util.ALut;

import com.cell.CIO;
import com.cell.bms.BMSFile;
import com.cell.bms.IDefineSound;

import demos.devmaster.lesson1.SingleStaticSource;

public class JALSound implements IDefineSound
{
	final private AL al;
	
	  // Buffers hold sound data.
	  private int[] buffer = new int[1];

	  // Sources are points emitting sound.
	  private int[] source = new int[1];

	  // Position of the source sound.
	  private float[] sourcePos = { 0.0f, 0.0f, 0.0f };

	  // Velocity of the source sound.
	  private float[] sourceVel = { 0.0f, 0.0f, 0.0f };

	  // Position of the listener.
	  private float[] listenerPos = { 0.0f, 0.0f, 0.0f };

	  // Velocity of the listener.
	  private float[] listenerVel = { 0.0f, 0.0f, 0.0f };

	  // Orientation of the listener. (first 3 elems are "at", second 3 are "up")
	  private float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };
	
	
	public JALSound(AL al, BMSFile bms, String sound) 
	{
		this.al = al;
	    // variables to load into
		
		try 
		{
			int[] format = new int[1];
			int[] size = new int[1];
			ByteBuffer[] data = new ByteBuffer[1];
			int[] freq = new int[1];
			int[] loop = new int[1];

			// Load wav data into a buffer.
			al.alGenBuffers(1, buffer, 0);
			
			if (al.alGetError() != AL.AL_NO_ERROR)
				throw new ALException("Error generating OpenAL buffers");

			ALut.alutLoadWAVFile(CIO.loadStream(bms.bms_dir+"/"+sound), 
					format, data, size, freq, loop);
			
			if (data[0] == null) {
				throw new RuntimeException("Error loading WAV file");
			}
			
			System.out.println("sound size = " + size[0]);
			System.out.println("sound freq = " + freq[0]);
			al.alBufferData(buffer[0], format[0], data[0], size[0], freq[0]);

			// Bind buffer with a source.
			al.alGenSources(1, source, 0);

			if (al.alGetError() != AL.AL_NO_ERROR)
				throw new ALException("Error generating OpenAL source");

			al.alSourcei(source[0], AL.AL_BUFFER, buffer[0]);
			al.alSourcef(source[0], AL.AL_PITCH, 1.0f);
			al.alSourcef(source[0], AL.AL_GAIN, 1.0f);
			al.alSourcei(source[0], AL.AL_LOOPING, loop[0]);

			// Do another error check
			if (al.alGetError() != AL.AL_NO_ERROR)
				throw new ALException("Error setting up OpenAL source");

			// Note: for some reason the following two calls are producing an
			// error on one machine with NVidia's OpenAL implementation. This
			// appears to be harmless, so just continue past the error if one
			// occurs.
			al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
			al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);

			
			
		}
		catch(Exception err) {
			err.printStackTrace();
		}
	  

	    al.alListenerfv(AL.AL_POSITION, listenerPos, 0);
	    al.alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
	    al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
	  
	}
	
	@Override
	public void play() {
      al.alSourcePlay(source[0]);
	}
	
	@Override
	public void pause() {
		al.alSourcePause(source[0]);
	}
	@Override
	public void stop() {
		al.alSourceStop(source[0]);
	}
	
	@Override
	protected void finalize() throws Throwable {
	    al.alDeleteBuffers(1, buffer, 0);
	    al.alDeleteSources(1, source, 0);
	}
}
