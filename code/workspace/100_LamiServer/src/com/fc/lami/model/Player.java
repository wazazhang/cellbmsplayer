package com.fc.lami.model;

import java.util.ArrayList;

import com.fc.lami.Messages;
import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.PlayerData;

/**
 * 一个玩家的数据，包含玩家手中的牌，该回合打出的牌
 * @author yagami0079
 *
 */
public class Player {
	public int player_id;
	
	public String name;
	
	public boolean is_ready;
	
	/** 手中的牌 */
	public ArrayList<CardData> card_list;
 	/** 本回合打出的牌 */
	public ArrayList<CardData> send_list;
	
	public PlayerData getPlayerData(){
		PlayerData pd = new PlayerData();
		pd.player_id = this.player_id;
		pd.name = this.name;
		return pd;
	}
}
