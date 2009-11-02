package com.cell.sound.util;

import com.cell.exception.NotImplementedException;
import com.cell.sound.IPlayer;
import com.cell.sound.ISound;
import com.cell.sound.SoundManager;

public class SoundPlayer implements IPlayer
{
	final ISound sound;
	
	final IPlayer player;
	
	public SoundPlayer(String resource) 
	{
		this(SoundManager.getSoundManager(), resource);
	}
	
	public SoundPlayer(SoundManager manager, String resource) 
	{
		this.sound	= manager.createSound(resource);
		this.player	= manager.createPlayer();
		this.player.setSound(sound);
	}
	
	@Override
	public void dispose() {
		this.sound.dispose();
		this.player.dispose();
	}
	
	@Override
	public ISound getSound() {
		return sound;
	}
	
	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}
	
	@Override
	public void pause() {
		player.pause();
	}
	
	@Override
	public void play() {
		player.play();
	}

	@Override
	public void stop() {
		player.stop();
	}
	
	@Override
	public void setSound(ISound sound) {
		throw new NotImplementedException();
	}
	
	
}
