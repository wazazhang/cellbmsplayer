package com.cell.sound.openal_impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.AudioFormat;

import net.java.games.joal.AL;
import net.java.games.joal.ALC;
import net.java.games.joal.ALCcontext;
import net.java.games.joal.ALCdevice;
import net.java.games.joal.ALConstants;
import net.java.games.joal.ALException;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import net.java.games.sound3d.AudioSystem3D;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundManager;
import com.cell.sound.mute_impl.NullPlayer;
import com.cell.sound.mute_impl.NullSound;

import de.jarnbjo.ogg.BasicStream;
import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.FileStream;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.PhysicalOggStream;
import de.jarnbjo.ogg.UncachedUrlStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisStream;

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
	// Position of the listener.
	private float[] listenerPos = { 0.0f, 0.0f, 0.0f };
	// Velocity of the listener.
	private float[] listenerVel = { 0.0f, 0.0f, 0.0f };
	// Orientation of the listener. (first 3 elems are "at", second 3 are "up")
	private float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

	ArrayList<JALPlayer>	players = new ArrayList<JALPlayer>(255);
	


//	--------------------------------------------------------------------------------------------------
	
	private JALSoundManager() throws Exception
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


//	--------------------------------------------------------------------------------------------------
	
	@Override
	public ISound createSound(String resource, InputStream is) {
		try {
			return new JALSound(this, resource, is);
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return new NullSound();
	}
	
	@Override
	public ISound createSound(String resource) 
	{
		try {
			return new JALSound(this, resource);
		} catch (Throwable err) {
			err.printStackTrace();
		}
		return new NullSound();
	}
	
	@Override
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
		return new NullPlayer();
	}


//	--------------------------------------------------------------------------------------------------
	

	static void initWav(
			InputStream 	input, 
			int[] 			out_format, 
			int[] 			out_size, 
			ByteBuffer[] 	out_data, 
			int[] 			out_freq, 
			int[] 			out_loop) throws IOException
	{
		if (input == null) {
			throw new IOException("InputStream is null !");
		}
		// Load wav data into a buffer.
		ALut.alutLoadWAVFile(input, out_format, out_data, out_size, out_freq, out_loop);
		if (out_data[0] == null) {
			throw new IOException("Error loading WAV file !");
		}
	}

	static void initOgg(
			InputStream 	input, 
			int[] 			out_format, 
			int[] 			out_size, 
			ByteBuffer[] 	out_data, 
			int[] 			out_freq, 
			int[] 			out_loop) throws IOException
	{
		if (input == null) {
			throw new IOException("InputStream is null !");
		}
		
		PhysicalOggStream os = new BasicStream(input);
		
		if (os == null) {
			throw new IOException("Can not create sound !");
		}
		
		try
		{
			AudioFormat	audioFormat	= null;
			ByteArrayOutputStream	baos		= new ByteArrayOutputStream(10240);
			
			for (Object los : os.getLogicalStreams()) 
			{
				LogicalOggStream		loStream	= (LogicalOggStream)los;
				VorbisStream			vStream		= new VorbisStream(loStream);
				IdentificationHeader	vStreamHdr	= vStream.getIdentificationHeader();
				audioFormat	= new AudioFormat(
						vStreamHdr.getSampleRate(), 16, 
						vStreamHdr.getChannels(),
						true, true);
				try {
					byte t = 0;
					byte[] data = new byte[2];
					while(true) {
						vStream.readPcm(data, 0, data.length);
						t = data[0];
						data[0] = data[1];
						data[1] = t;
						baos.write(data);
					}
				}
				catch (EndOfOggStreamException e) {
					// ignore 
				}
				finally{
					vStream.close();
					loStream.close();
				}
			}
			
		    if (audioFormat.getChannels() == 1)
			    out_format[0]	= AL.AL_FORMAT_MONO16;
			else
				out_format[0]	= AL.AL_FORMAT_STEREO16;
		    
		    out_data[0]			= ByteBuffer.wrap(baos.toByteArray());
		    out_size[0]			= baos.size();
		    out_freq[0]			= (int)audioFormat.getFrameRate();
		}
		finally
		{
			os.close();
		}
	}
	
	
	
}
