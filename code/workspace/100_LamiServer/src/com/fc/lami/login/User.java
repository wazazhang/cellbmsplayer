package com.fc.lami.login;

public interface User 
{
	public int		getPoint();
	
	public int		addPoint(int value) throws Exception;

	public int		getName();
	
	public byte 	getSex();
	
	public byte[] 	getHeadImageData();
	
	
}
