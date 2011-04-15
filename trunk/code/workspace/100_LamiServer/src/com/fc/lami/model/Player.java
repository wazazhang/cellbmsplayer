package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.ResultPak;
import com.fc.lami.login.User;
import com.net.server.ClientSession;

/**
 * 一个玩家的数据，包含玩家手中的牌，该回合打出的牌
 * @author yagami0079
 *
 */
public class Player implements User{
	public int player_id;
	
	public String name;
	
	public boolean is_ready = false;
	
	public ClientSession session;
	
	public Room cur_room;
	public Desk cur_desk;
	
	public int score = 0;
	public int win = 0;
	public int lose = 0;
	public int level;
	
	/** 手中的牌 */
	public HashMap<Integer, CardData> card_list;
	
	/** 是否破冰 */
	public boolean isOpenIce = false;
	
	public Player(){

	}
	
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
		pd.level = this.level;
		pd.score = this.score;
		pd.win = this.win;
		pd.lose = this.lose;
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
		if (c!=null){
			card_list.put(c.id, c);
		}
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
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
		this.score += 10;
		this.win+=1;
		
		ResultPak pak = new ResultPak();
		pak.is_win = true;
		pak.point = 10;
		return pak;
	}
	
	public ResultPak onPlayerLose(){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
		this.score -= 5;
		this.lose += 1;
		
		ResultPak pak = new ResultPak();
		pak.is_win = false;
		pak.point = -5;
		return pak;
	}

	public void onPlayerEscape(){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
		this.score -= 5;
		this.lose += 1;
	}
	
	@Override
	public int getPoint() {
		// TODO Auto-generated method stub
		return score;
	}

	@Override
	public int addPoint(int value) throws Exception {
		score += value;
		return score;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public byte getSex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getHeadImageData() {
		// TODO Auto-generated method stub
		return null;
	}
}
