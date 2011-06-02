package com.slg.sanguosha.login;

import com.slg.entity.Player;

public interface User 
{
	public String	getUID();
	
//	public String	getName();
	
	
	public void		save();
	
	public Player getDefaultPlayer();
	
//	public Player getPlayer(int index);
}
