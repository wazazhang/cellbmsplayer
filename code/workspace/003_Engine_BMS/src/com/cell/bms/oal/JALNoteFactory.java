package com.cell.bms.oal;

import java.util.ArrayList;

import net.java.games.joal.AL;
import net.java.games.joal.ALException;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;
import net.java.games.sound3d.AudioSystem3D;

import com.cell.bms.BMSFile;
import com.cell.bms.IDefineImage;
import com.cell.bms.IDefineSound;

public class JALNoteFactory extends com.cell.bms.NoteFactory
{  
	AL	al;

	// Position of the listener.
	private float[] listenerPos = { 0.0f, 0.0f, 0.0f };
	// Velocity of the listener.
	private float[] listenerVel = { 0.0f, 0.0f, 0.0f };
	// Orientation of the listener. (first 3 elems are "at", second 3 are "up")
	private float[] listenerOri = { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f };

	ArrayList<JALPlayer>	players = new ArrayList<JALPlayer>();
	
	
	public JALNoteFactory() throws Exception
	{
	    // Initialize OpenAL and clear the error bit.
		ALut.alutInit();
		al = ALFactory.getAL();
		al.alGetError();

		// set listeners
	    al.alListenerfv(AL.AL_POSITION, listenerPos, 0);
	    al.alListenerfv(AL.AL_VELOCITY, listenerVel, 0);
	    al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
	    
		for (int i=0; i<128; i++) {
	  		try{
	  			players.add(new JALPlayer(al));
	  		}catch(Exception err){
	  			break;
	  		}
	  	}
	  	System.out.println("Gen OpenAL players : " + players.size());
	}

	@Override
	public void initBMS(BMSFile bms) {
	  	
	}
	
	@Override
	public IDefineImage defineImage(BMSFile bms, String image) {
		return new JAwtImage(bms, image);
	}
	
	@Override
	public IDefineSound defineSound(BMSFile bms, String sound) {
		return new JALSound(this, bms, sound);
	}
	
	JALPlayer getFreePlayer() {
		for (JALPlayer player : players) {
			if (player.isFree()) {
				return player;
			}
		}
		return null;
	}
	
}
