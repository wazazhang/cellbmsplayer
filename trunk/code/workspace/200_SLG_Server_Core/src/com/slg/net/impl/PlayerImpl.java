package com.slg.net.impl;

import com.net.server.ClientSession;
import com.slg.IPlayer;
import com.slg.IWorld;
import com.slg.entity.Player;

public class PlayerImpl implements IPlayer
{
	private ClientSession 	session;
	final private IWorld world;
	final private Player player;
	
	public PlayerImpl(IWorld world, Player p){
		this.world = world;
		this.player = p;
		//TODO 
	}
	
	public void onConnected(ClientSession session){
		this.session = session;
	}
	
	public void onDisconnected(){
		this.session = null;
	}
	
	public IWorld getWorld(){
		return world;
	}
	public Player getPlayer(){
		return player;
	}
	@Override
	public int getPlayerID() {
		// TODO Auto-generated method stub
		return player.player_id;
	}

	@Override
	public String getPlayerName() {
		// TODO Auto-generated method stub
		return player.name;
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
		return player.office = office;
	}

	@Override
	public void setPlayerID(int id) {
		player.player_id = id;
	}

}
