package com.cell.sound.openal_impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

import net.java.games.joal.AL;
import net.java.games.joal.ALC;
import net.java.games.joal.ALCcontext;
import net.java.games.joal.ALCdevice;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;

import com.cell.CIO;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;
import com.cell.sound.decoder.ogg_impl.OggDecoder;
import com.cell.sound.mute_impl.NullPlayer;
import com.cell.sound.mute_impl.NullSound;


public class JALSoundManager extends SoundManager
{  
	static JALSoundManager instance;
	
	public static JALSoundManager getInstance() throws Throwable{
		if (instance == null) {
			instance = new JALSoundManager();
		}
		return instance;
	}
	
//	--------------------------------------------------------------------------------------------------
	
	AL	al;
	ALC alc;

	ArrayList<JALPlayer>	players = new ArrayList<JALPlayer>(255);
	

//	--------------------------------------------------------------------------------------------------
	OggDecoder ogg_decoder = new OggDecoder();

//	--------------------------------------------------------------------------------------------------
	
	private JALSoundManager() throws Exception
	{
	    // Initialize OpenAL and clear the error bit.
		ALut.alutInit();
		al = ALFactory.getAL();
		
		if (checkError(al)) 
		{
			System.out.println("OpenAL Error !");
		}
		else 
		{
			alc = ALFactory.getALC();

			// set device, find device with the maximum source
			{
				String[] devices = alc.alcGetDeviceSpecifiers();
				
				int max_source = 0;
				int max_index = 0;
				
				for (int i=0; i<devices.length; i++) 
				{
					System.out.println("OpenAL Device : " + devices[i]);
					
					ALCdevice device = alc.alcOpenDevice(devices[i]);
					
					int[] nummono	= new int[1];
					int[] numstereo = new int[1];
					alc.alcGetIntegerv(device, ALC.ALC_MONO_SOURCES,   1, nummono,   0); 
					alc.alcGetIntegerv(device, ALC.ALC_STEREO_SOURCES, 1, numstereo, 0); 

					System.out.println("\tMax mono sources   : " + nummono[0]); 
					System.out.println("\tMax stereo sources : " + numstereo[0]); 
					
					if (max_source < (nummono[0] + numstereo[0])) {
						max_source = nummono[0] + numstereo[0];
						max_index = i;
					}
				}
				
				System.out.println("Enable OpenAL Device : " + devices[max_index]);
				ALCdevice 	soft_device = alc.alcOpenDevice(devices[max_index]);
				ALCcontext 	context 	= alc.alcCreateContext(soft_device, null);
				
				alc.alcMakeContextCurrent(context);
//				al.alSpeedOfSound(1.0f);
//				al.alDopplerFactor(1.0f);
			}
			
			// set listeners
		    {	
		    	// Position of the listener.
		    	float[] listenerPos = { 0.0f, 0.0f, 0.0f };
		    	// Velocity of the listener.
		    	float[] listenerVel = { 0.0f, 0.0f, 0.0f };
		    	// Orientation of the listener. (first 3 elems are "at", second 3 are "up")
		    	float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

		    	al.alListenerfv(AL.AL_POSITION, 	listenerPos, 0);
			    al.alListenerfv(AL.AL_VELOCITY, 	listenerVel, 0);
			    al.alListenerfv(AL.AL_ORIENTATION, 	listenerOri, 0);
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
	}


//	--------------------------------------------------------------------------------------------------

	synchronized 
	public SoundInfo createSoundInfo(String resource, InputStream is){
		try {
			String name = resource.toLowerCase();
			if (name.endsWith(".wav")) {
				return initWav(resource, is);
			} else if (name.endsWith(".ogg")) {
				return initOgg(resource, is);
			}
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
	
	synchronized 
	public SoundInfo createSoundInfo(String resource) {
		return createSoundInfo(resource, CIO.loadStream(resource));
	}
	
	synchronized 
	public ISound createSound(SoundInfo info) {
		try {
			return new JALSound(this, info);
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return new NullSound();
	}
	
	synchronized 
	public IPlayer createPlayer() 
	{
		for (JALPlayer player : players) {
			if (!player.actived.get()) {
				player.actived.set(true);
//				System.out.println(
//						"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS\n" +
//						"S Create sound : " + player + "\n"+
//						"SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
				return player;
			}
		}
		System.err.println("no free source, cut an active source !");
		return new NullPlayer();
		
	}
	
	synchronized
	public void cleanAllPlayer() {
		for (JALPlayer player : players) {
			player.setSound(null);
			player.dispose();
		}
	}
	
	synchronized
	public void setVolume(float volume) {
		al.alListenerf(AL.AL_GAIN, volume);
	}

//	--------------------------------------------------------------------------------------------------
	

	SoundInfo initWav(String resource, InputStream input) throws Exception
	{
		if (input == null) {
			throw new IOException("InputStream is null !");
		}
		return new JALWavSoundInfo(resource, input);
	}

	SoundInfo initOgg(String resource, InputStream input) throws Exception
	{
		if (input == null) {
			throw new IOException("InputStream is null !");
		}
		SoundInfo info = ogg_decoder.decode(resource, input);
		return info;
	}
	
	static boolean checkError(AL al) {
		int code = al.alGetError();
		if (code != AL.AL_NO_ERROR) {
			try{
				
				throw new Exception("OpenAL Error code : " + code);
			}catch(Exception err) {
				err.printStackTrace();
			}
			return true;
		}
		return false;
	}
	

}
