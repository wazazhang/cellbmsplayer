package com.cell.sound;

public abstract class SoundManager 
{
	private static SoundManager instance;
	
	public static void setSoundManager(SoundManager ins) {
		instance = ins;
	}
	
	
//	-----------------------------------------------------------------------------------------------------------
	
	abstract public ISound createSound(String resource);
	
	abstract public IPlayer createPlayer();

}
