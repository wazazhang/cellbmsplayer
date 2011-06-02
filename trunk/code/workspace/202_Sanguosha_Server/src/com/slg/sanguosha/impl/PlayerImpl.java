package com.slg.sanguosha.impl;

import com.net.server.ClientSession;
import com.slg.IPlayer;
import com.slg.entity.Player;
import com.slg.sanguosha.login.User;

public class PlayerImpl implements IPlayer
{
	final public User 			user;
	final public ClientSession 	session;
	final private Player player;
	
	public PlayerImpl(ClientSession session, User user){
		this.user = user;
		this.session = session;
		this.player = user.getDefaultPlayer();
	}
	
	public Player getPlayer(){
		return player;
	}
	@Override
	public int getPlayerId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getExp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExpMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addExp(int exp) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getApMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addAp(int ap) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addOffice(int off) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setOffice(int office) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getUID() {
		// TODO Auto-generated method stub
		return user.getUID();
	}

}
