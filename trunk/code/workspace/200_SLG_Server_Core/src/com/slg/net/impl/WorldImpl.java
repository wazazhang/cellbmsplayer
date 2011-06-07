package com.slg.net.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.slg.IPlayer;
import com.slg.IWorld;

public class WorldImpl implements IWorld {
	//所有玩家
	private ConcurrentHashMap<Integer, IPlayer> playerimpl_map;
	static private AtomicInteger player_ids = new AtomicInteger(0);

	public WorldImpl(){
		playerimpl_map = new ConcurrentHashMap<Integer, IPlayer>();
	}
	@Override
	public IPlayer getPlayer(int uid) {
		return playerimpl_map.get(uid);
	}
	@Override
	public int addPlayer(IPlayer newplayer) {
		newplayer.setPlayerID(player_ids.incrementAndGet());
		return 0;
	}

}
