package com.cell.sound.mute_impl;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;


public class NullPlayer implements IPlayer
{
	ISound			sound;
	
	@Override
	public void setSound(ISound sound) {
		this.sound = sound;
	}

	@Override
	public ISound getSound() {
		return sound;
	}
	
	public void play() {}
	public void pause() {}
	public void stop() {}

	@Override
	public boolean isPlaying() {
		return false;
	}
	
	public void dispose() {}

	
}
