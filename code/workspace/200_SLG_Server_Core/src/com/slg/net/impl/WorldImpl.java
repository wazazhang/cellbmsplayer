package com.slg.net.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.slg.IPlayer;
import com.slg.IWorld;

public class WorldImpl implements IWorld {
	static private AtomicInteger player_ids = new AtomicInteger(0);
	//所有玩家
	private ConcurrentHashMap<Integer, IPlayer> playerimpl_map;
	// 名字——>ID表
	private ConcurrentHashMap<String, Integer> id_name_map;

	public WorldImpl(){
		playerimpl_map = new ConcurrentHashMap<Integer, IPlayer>();
		id_name_map = new ConcurrentHashMap<String, Integer>();
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

}
