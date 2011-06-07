package com.slg;

public interface IWorld {
	public IPlayer getPlayer(int uid);
	
	public int addPlayer(IPlayer newplayer);
}
