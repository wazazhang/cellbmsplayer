package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
public class Player
{
	static private AtomicInteger ids = new AtomicInteger(0);
	
	final public int 			player_id;
	final public User 			user;
	final public ClientSession 	session;
	
	public boolean is_ready = false;
	
	public Room cur_room;
	public Desk cur_desk;
	
//	public int score = 0;
//	public int win = 0;
//	public int lose = 0;
//	public int level;
	
	/** 手中的牌 */
	final public HashMap<Integer, CardData> card_list = new LinkedHashMap<Integer, CardData>();
	
	/** 是否破冰 */
	public boolean isOpenIce = false;
		
	public Player(ClientSession session, User user){
		this.session = session;
		this.user = user;
		this.player_id = ids.incrementAndGet();
		this.is_ready = false;
	}
	
	public String getName() {
		return user.getName();
	}
	
	public PlayerData getPlayerData(){
		PlayerData pd = new PlayerData();
		pd.player_id = this.player_id;
		pd.name = this.user.getName();
		pd.level = 1;
		pd.score = this.user.getScore();
		pd.win = this.user.getWin();
		pd.lose = this.user.getLose();
		return pd;
	}
	
	public Game getGame() {
		if (cur_desk != null) {
			return cur_desk.getGame();
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
//		if (card_list == null){
//			card_list = new HashMap<Integer, CardData>();
//		}
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
//		this.score += 10;
//		this.win+=1;
		this.user.addPoint(10);
		this.user.addWin(1);
		
		ResultPak pak = new ResultPak();
		pak.is_win = true;
		pak.point = 10;
		return pak;
	}
	
	public ResultPak onPlayerLose(){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
//		this.score -= 5;
//		this.lose += 1;
//		this.user.addScore(value); // 输了不该扣分吧
		this.user.addLose(1);
		
		ResultPak pak = new ResultPak();
		pak.is_win = false;
		pak.point = -5;
		return pak;
	}

	public void onPlayerEscape(){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
//		this.score -= 5;
//		this.lose += 1;
		this.user.addLose(1);
	}
	
//	@Override
//	public int getPoint() {
//		// TODO Auto-generated method stub
//		return score;
//	}
//
//	@Override
//	public int addPoint(int value) throws Exception {
//		score += value;
//		return score;
//	}
//
//	@Override
//	public String getName() {
//		return name;
//	}
//
//	@Override
//	public byte getSex() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public byte[] getHeadImageData() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
