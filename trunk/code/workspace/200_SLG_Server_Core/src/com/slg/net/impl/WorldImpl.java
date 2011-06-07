package com.slg.net.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.slg.IPlayer;
import com.slg.IVillage;
import com.slg.IWorld;

public class WorldImpl implements IWorld {
	//所有玩家
	private ConcurrentHashMap<Integer, IPlayer> playerimpl_map;
	static private AtomicInteger player_ids = new AtomicInteger(0);
	// 名字——>ID表
	private ConcurrentHashMap<String, Integer> id_name_map;
	// 村寨
	private ConcurrentHashMap<Integer, IVillage> village_map;
	static private AtomicInteger village_ids = new AtomicInteger(0);

	public WorldImpl(){
		playerimpl_map = new ConcurrentHashMap<Integer, IPlayer>();
		id_name_map = new ConcurrentHashMap<String, Integer>();
		village_map = new ConcurrentHashMap<Integer, IVillage>();
	}
	@Override
	public IPlayer getPlayer(int uid) {
		return playerimpl_map.get(uid);
	}
	@Override
	public int addPlayer(IPlayer newplayer) {
		newplayer.setPlayerID(player_ids.incrementAndGet());
		playerimpl_map.put(newplayer.getPlayerID(), newplayer);
		id_name_map.put(newplayer.getPlayerName(), newplayer.getPlayerID());
		return 0;
	}
	@Override
	public IPlayer getPlayer(String name) {
		if (id_name_map.get(name)==null){
			return null;
		}
		return playerimpl_map.get(id_name_map.get(name));
	}
	@Override
	public IVillage getVillage(int id) {
		return village_map.get(id);
	}
	@Override
	public int addVillage(IVillage village) {
		village.setVillageID(village_ids.incrementAndGet());
		village_map.put(village.getVillageID(), village);
		return 0;
	}

}
