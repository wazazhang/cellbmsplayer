package com.fc.lami.model;

import java.util.ArrayList;

import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.PlayerData;
import com.net.server.ClientSession;

/**
 * 一个玩家的数据，包含玩家手中的牌，该回合打出的牌
 * @author yagami0079
 *
 */
public class Player {
	public int player_id;
	
	public String name;
	
	public boolean is_ready;
	
	public ClientSession session;
	
	public Room cur_room;
	public Desk cur_desk;
	
	/** 手中的牌 */
	public ArrayList<CardData> card_list;
 	/** 本回合打出的牌 */
	public ArrayList<CardData> send_list;
	
	public Player(ClientSession session, String name, int id){
		this.session = session;
		this.name = name;
		this.player_id = id;
		this.is_ready = false;
	}
	
	public PlayerData getPlayerData(){
		PlayerData pd = new PlayerData();
		pd.player_id = this.player_id;
		pd.name = this.name;
		return pd;
	}
	
	public Game getGame(){
		if (cur_desk!=null){
			return cur_desk.game;
		}
		return null;
	}
}
