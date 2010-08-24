package com.cell.sound.mute_impl;

import java.io.InputStream;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class NullSoundManager extends SoundManager
{
	@Override
	public void setVolume(float mute) {
		
	}
	
	@Override
	public IPlayer createPlayer() {
		return new NullPlayer();
	}
	
	@Override
	public ISound createSound(SoundInfo info) {
		return new NullSound();
	}
	
	@Override
	public SoundInfo createSoundInfo(String resource, InputStream is) {
		return new NullSoundInfo();	
	}
	
	@Override
	public SoundInfo createSoundInfo(String resource) {
		return new NullSoundInfo();
	}
	
	@Override
	public void cleanAllPlayer() {}
}