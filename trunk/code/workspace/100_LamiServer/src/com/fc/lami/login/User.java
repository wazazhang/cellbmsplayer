package com.fc.lami.login;

public interface User 
{
	public String	getUID();
	
	public int		getScore();
	
	public int		getPoint();
	
	public int		getWin();
	
	public int		getLose();


	
	public int		addScore(int value);
	
	public int		addPoint(int value);

	public int		addWin(int value);
	
	public int		addLose(int value);
	
	
	public void		save();
}
