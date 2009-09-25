package com.cell.bms.oal;

import net.java.games.joal.AL;
import net.java.games.joal.ALException;
import net.java.games.joal.ALFactory;
import net.java.games.joal.util.ALut;

import com.cell.bms.BMSFile;
import com.cell.bms.IDefineImage;
import com.cell.bms.IDefineSound;

public class NoteFactory extends com.cell.bms.NoteFactory
{  
	AL al;

	public NoteFactory() throws Exception
	{
	    // Initialize OpenAL and clear the error bit.
	    ALut.alutInit();
	    al = ALFactory.getAL();
	    al.alGetError();
	}
	
	@Override
	public IDefineImage defineImage(BMSFile bms, String image) {
		return new JAwtImage(bms, image);
	}
	@Override
	public IDefineSound defineSound(BMSFile bms, String sound) {
		return new JALSound(al, bms, sound);
	}
	
	
}
