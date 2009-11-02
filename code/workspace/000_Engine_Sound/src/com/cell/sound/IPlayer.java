package com.cell.sound;

public interface IPlayer 
{
	public void 	play();
	
	public void		pause();
	
	public void 	stop();
	
	public boolean 	isPlaying();
	
	
	public void		setSound(ISound sound);
	
	public ISound	getSound();
}
