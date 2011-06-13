package com.fc.lami;

import java.util.concurrent.atomic.AtomicInteger;

import com.fc.lami.model.Player;

public class PlayerFactory {
	
	public static Player getPlayer(String name){
		Player player = new Player();
		player.name = name;
		player.player_id = ids.addAndGet(delta);
		return player;
	}
}
