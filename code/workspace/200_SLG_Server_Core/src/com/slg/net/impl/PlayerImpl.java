package com.slg.net.impl;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.net.server.ClientSession;
import com.slg.IPlayer;
import com.slg.IVillage;
import com.slg.IWorld;
import com.slg.entity.Player;

public class PlayerImpl implements IPlayer
{
	private ClientSession 	session;
	final private IWorld world;
	final private Player player;
	
	final private ConcurrentHashMap<String, Integer> currency_list;
	final private ArrayList<Integer> village_list; 
	final private ArrayList<Integer> hero_list;
	
	public PlayerImpl(IWorld world, Player p){
		this.world = world;
		this.player = p;
		//TODO 
		currency_list = new ConcurrentHashMap<String, Integer>();
		for (int i = 0; i<player.currency.key.length; i++){
			currency_list.put(player.currency.key[i], player.currency.value[i]);
		}
		village_list = new ArrayList<Integer>();
		if (player.village_list!=null && player.village_list.length>0){
			for (int i:player.village_list){
				village_list.add(i);
			}
		}
		hero_list = new ArrayList<Integer>();
		if (player.hero_list!=null && player.hero_list.length>0){
			for (int i:player.hero_list){
				hero_list.add(i);
			}
		}
	}
	
	public void onConnected(ClientSession session){
		this.session = session;
	}
	
	public void onDisconnected(){
		this.session = null;
	}
	
	public boolean isConnected(){
		if (session!=null){
			return true;
		}
		else{
			return false;
		}
	}
	public IWorld getWorld(){
		return world;
	}
	
	public Player getPlayerData(){
		int length = currency_list.size();
		player.currency.key = new String[length];
		player.currency.value = new int[length];
		int i = 0;
		for (String key:currency_list.keySet()){
			player.currency.key[i] = key;
			player.currency.value[i] = currency_list.get(key);
			i++;
		}
		player.village_list = new int[village_list.size()];
		i = 0;
		for (Integer I:village_list){
			player.village_list[i] = I;
			i++;
		}
		player.hero_list = new int[hero_list.size()];
		i = 0;
		for (Integer I:hero_list){
			player.hero_list[i] = I;
			i++;
		}
		return player;
	}
	@Override
	public int getPlayerID() {
		return player.player_id;
	}

	@Override
	public String getPlayerName() {
		return player.name;
	}

	@Override
	public int getExp() {
		return player.exp.getValue();
	}

	@Override
	public int getExpMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addExp(int exp) {
		return player.exp.add(exp);
	}

	@Override
	public int getAp() {
		return player.ap.getValue();
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

	@Override
	public IVillage getCurVillage() {
		return world.getVillage(player.cur_village_id);
	}

	@Override
	public void setCurVillage(IVillage village) {
		player.cur_village_id = village.getVillageID();
	}

	@Override
	public void addVillage(IVillage village) {
		world.addVillage(village);
		village_list.add(village.getVillageID());
		if (village_list.size()<2){
			setCurVillage(village);
		}
	}

	
}
