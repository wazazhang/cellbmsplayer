package com.cell.sound;

import java.io.InputStream;

public abstract class SoundManager 
{
	private static SoundManager instance;
	
	public static void setSoundManager(SoundManager ins) {
		instance = ins;
	}
	
	public static SoundManager getSoundManager() {
		return instance;
	}
	
//	-----------------------------------------------------------------------------------------------------------
	
	abstract public ISound createSound(String resource);
	
	abstract public ISound createSound(String resource, InputStream is);
	
	abstract public IPlayer createPlayer();

}
