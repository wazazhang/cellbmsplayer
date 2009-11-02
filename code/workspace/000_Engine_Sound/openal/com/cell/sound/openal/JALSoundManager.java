package com.cell.sound.openal;

import java.util.ArrayList;
import java.util.Collections;

import net.java.games.joal.AL;
import net.java.games.joal.ALC;
import net.java.games.joal.ALCcontext;
import net.java.games.joal.ALCdevice;
import net.java.games.joal.ALConstants;
import net.java.games.joal.ALException;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import net.java.games.sound3d.AudioSystem3D;

import com.cell.CUtil;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundManager;

public class JALSoundManager extends SoundManager
{  
	AL	al;
	ALC alc;
	// Position of the listener.
	private float[] listenerPos = { 0.0f, 0.0f, 0.0f };
	// Velocity of the listener.
	private float[] listenerVel = { 0.0f, 0.0f, 0.0f };
	// Orientation of the listener. (first 3 elems are "at", second 3 are "up")
	private float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

	ArrayList<JALPlayer>	players = new ArrayList<JALPlayer>(255);
	
	
	public JALSoundManager() throws Exception
	{
	    // Initialize OpenAL and clear the error bit.
		ALut.alutInit();
		al = ALFactory.getAL();
		al.alGetError();
		alc = ALFactory.getALC();
		
		// set device, find device with the maximum source
		{
			String[] devices = alc.alcGetDeviceSpecifiers();
			
			int max_source = 0;
			int max_index = 0;
			
			for (int i=0; i<devices.length; i++) 
			{
				System.out.println("Device : " + devices[i]);
				
				ALCdevice device = alc.alcOpenDevice(devices[i]);
				
				int[] nummono	= new int[1];
				int[] numstereo = new int[1];
				alc.alcGetIntegerv(device, ALC.ALC_MONO_SOURCES, 1, nummono, 0); 
				alc.alcGetIntegerv(device, ALC.ALC_STEREO_SOURCES, 1, numstereo, 0); 

				System.out.println("\tMax mono sources   : " + nummono[0]); 
				System.out.println("\tMax stereo sources : " + numstereo[0]); 
				
				if (max_source < (nummono[0] + numstereo[0])) {
					max_source = nummono[0] + numstereo[0];
					max_index = i;
				}
			}

			ALCdevice soft_device = alc.alcOpenDevice(devices[max_index]);
			ALCcontext context = alc.alcCreateContext(soft_device, null);
			alc.alcMakeContextCurrent(context);
			
		}
		
		// set listeners
	    {
	    	al.alListenerfv(AL.AL_POSITION, listenerPos, 0);
		    al.alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
		    al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
	    }
	    
	    // create players
		for (int i=0; i<255; i++) {
	  		try{
	  			players.add(new JALPlayer(al));
	  		}catch(Exception err){
	  			break;
	  		}
	  	}
	  	System.out.println("Gen OpenAL players : " + players.size());
	}

	
	public ISound createSound(String resource) 
	{
		return new JALSound(this, resource);
	}
	
	public IPlayer createPlayer() 
	{
		for (JALPlayer player : players) {
			if (!player.isPlaying()) {
				return player;
			}
		}
		if (players.size()>0) {
			Collections.sort(players);
			JALPlayer player = players.get(0);
			player.stop();
			System.err.println("no free source, cut an active source !");
			return player;
		}
		return null;
	}
	

	static void swapBytes(byte[] b, int off, int len) {
		byte tempByte;
		for (int i = off; i < (off + len); i += 2) {
			tempByte = b[i];
			b[i] = b[i + 1];
			b[i + 1] = tempByte;
		}
	}

	
}
