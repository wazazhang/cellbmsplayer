package com.cell.sound.mute_impl;

import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundInfo;
import com.cell.sound.SoundManager;

public class NullSoundManager extends SoundManager
{
	@Override
	public IPlayer createPlayer() {
		return new NullPlayer();
	}
	
	@Override
	public ISound createSound(SoundInfo info) {
		return new NullSound();
	}
	
	@Override
	public SoundInfo createSoundInfo(String resource) {
		return new SoundInfo();
	}
}
