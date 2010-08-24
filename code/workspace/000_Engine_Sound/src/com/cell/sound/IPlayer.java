package com.cell.sound;

public interface IPlayer 
{
	public void 	play(boolean looping);
	
	public void		pause();

	public void		resume();
	
	public void 	stop();
	
	public boolean 	isPlaying();
	
	public void 	dispose() ;
	
	public void		setSound(ISound sound);
	
//	public ISound	getSound();
}
