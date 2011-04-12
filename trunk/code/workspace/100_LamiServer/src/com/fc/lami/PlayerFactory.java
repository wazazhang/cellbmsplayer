package com.fc.lami;

import com.fc.lami.model.Player;

public class PlayerFactory {
	
	static int ids = 0;
	
	public static Player getPlayer(String name){
		Player player = new Player();
		player.name = name;
		player.player_id = ids++;
		return player;
	}
}
