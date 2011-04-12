package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.ResultPak;
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
	public HashMap<Integer, CardData> card_list;
	
	/** 是否破冰 */
	public boolean isOpenIce;
	
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
	
	public CardData removeCard(CardData c){
		if (card_list == null){
			return null;
		}
		return card_list.remove(c.id);
	}
	
	public CardData removeCard(int id){
		if (card_list == null){
			return null;
		}
		return card_list.remove(id);
	}
	
	public void addCard(CardData c){
		if (card_list == null){
			card_list = new HashMap<Integer, CardData>();
		}
		card_list.put(c.id, c);
	}
	
	public int getHandCardPonit(){
		int point = 0;
		for (CardData cd:card_list.values()){
			if (cd.point == 0){
				point += 30;
			}else{
				point += cd.point;
			}
		}
		return point;
	}
	
	public ResultPak onPlayerWin(){
		ResultPak pak = new ResultPak();
		pak.is_win = true;
		pak.point = 10;
		return pak;
	}
	
	public ResultPak onPlayerLose(){
		ResultPak pak = new ResultPak();
		pak.is_win = false;
		pak.point = -5;
		return pak;
	}
}
