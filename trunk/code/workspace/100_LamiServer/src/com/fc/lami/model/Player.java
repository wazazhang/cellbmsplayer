package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.ResultPak;
import com.fc.lami.login.User;
import com.net.server.Channel;
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
	
	public String getUID() {
		return user.getUID();
	}
	
	public PlayerData getPlayerData(){
		PlayerData pd = new PlayerData();
		pd.player_id = this.player_id;
		pd.uid = this.user.getUID();
		pd.level = 1;
		pd.score = this.user.getScore();
		pd.win = this.user.getWin();
		pd.lose = this.user.getLose();
		return pd;
	}
	
	public Channel getCurChannel(){
		if (cur_desk!=null){
			return cur_desk.getChannel();
		}
		if (cur_room!=null){
			return cur_room.getChannel();
		}
		return null;
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
	
	/** 判断是否有3组同色同点的牌 */
	public boolean isCanResetGame(){
		int pair = 0;
		CardData cl[] = new CardData[card_list.size()];
		int index = 0;
		for (CardData cd:card_list.values()){
			cl[index++] = cd;
		}
		
		for (int i = 0; i<cl.length; i++){
			for(int j = i+1; j<cl.length; j++){
				if (cl[i].type == cl[j].type && cl[i].point == cl[j].point){
					pair++;
				}
			}
		}
		if (pair>=3){
			return true;
		}
		return false;
	}
	
	/** 判断是否有可破冰的牌组  */
	public boolean isCanOpenIce(){
		int point_1 = 0;
		int point_2 = 0;
		ArrayList<CardData> player_cards = new ArrayList<CardData>();
		for (CardData cd:card_list.values()){
			player_cards.add(cd);
		}
		ArrayList<CardData> group = getCardGroupV(player_cards);
		while(group.size()>0){
			for (CardData cd : group){
				point_1 += cd.point;
				player_cards.remove(cd);
			}
			group = getCardGroupV(player_cards);
		}
		
		if (point_1>=30){
			return true;
		}
		
		player_cards.clear();
		for (CardData cd:card_list.values()){
			player_cards.add(cd);
		}
		
		group = getCardGroupH(player_cards);
		while(group.size()>0){
			for (CardData cd : group){
				point_2 += cd.point;
				player_cards.remove(cd);
			}
			group = getCardGroupH(player_cards);
		}
		if (point_2>=30){
			return true;
		}
		return false;
	}
	
	public ArrayList<CardData> getCardGroup(){
		ArrayList<CardData> player_cards = new ArrayList<CardData>();
		for (CardData cd:card_list.values()){
			player_cards.add(cd);
		}
		return getCardGroupV(player_cards);
	}
	
	// 纵向优先
	private ArrayList<CardData> getCardGroupV(ArrayList<CardData> cards){
		CardData table[][] = new CardData[4][13];
		for (CardData cd:cards){
			if (cd.type>0){
				if (table[cd.type-1][cd.point-1]==null){
					table[cd.type-1][cd.point-1] = cd;
				}
			}
		}
		
		ArrayList<CardData> l = new ArrayList<CardData>();
		for (int j = 0; j<13; j++){
			for (int i = 0; i<4; i++){
				if (table[i][j]!=null){
					l.add(table[i][j]);
				}
			}
			if (l.size()>=3){
				return l;
			}
			l.clear();
		}
		for (int i = 0; i<4; i++){
			for (int j = 0; j<13; j++){
				if (table[i][j]==null){
					if (l.size()>=3){
						return l;
					}else{
						l.clear();
					}
				}else{
					l.add(table[i][j]);
				}
			}
			if (l.size()>=3){
				return l;
			}
			l.clear();
		}
		return l;
	}
	
	// 横向优先
	private ArrayList<CardData> getCardGroupH(ArrayList<CardData> cards){
		CardData table[][] = new CardData[4][13];
		for (CardData cd:cards){
			if (cd.type>0){
				if (table[cd.type-1][cd.point-1]==null){
					table[cd.type-1][cd.point-1] = cd;
				}
			}
		}
		
		ArrayList<CardData> l = new ArrayList<CardData>();

		for (int i = 0; i<4; i++){
			for (int j = 0; j<13; j++){
				if (table[i][j]==null){
					if (l.size()>=3){
						return l;
					}else{
						l.clear();
					}
				}else{
					l.add(table[i][j]);
				}
			}
			if (l.size()>=3){
				return l;
			}
			l.clear();
		}
		
		for (int j = 0; j<13; j++){
			for (int i = 0; i<4; i++){
				if (table[i][j]!=null){
					l.add(table[i][j]);
				}
			}
			if (l.size()>=3){
				return l;
			}
			l.clear();
		}
		return l;
	}
	
	public ResultPak onPlayerWin(int p){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();

		this.user.addScore(p);
		this.user.addWin(1);
		
		ResultPak pak = new ResultPak();
		pak.player_id = this.player_id;
		pak.is_win = true;
		pak.point = p;
		return pak;
	}
	
	public ResultPak onPlayerLose(int p){
		this.is_ready = false;
		this.isOpenIce = false;
		this.card_list.clear();
		
		this.user.addScore(-p);
		this.user.addLose(1);
		
		ResultPak pak = new ResultPak();
		pak.player_id = this.player_id;
		pak.is_win = false;
		pak.point = -p;
		return pak;
	}

	public int onPlayerEscape(){
		this.is_ready = false;
		this.isOpenIce = false;
		int point = getHandCardPonit();
		this.card_list.clear();
		this.user.addScore(-point);

		this.user.addLose(1);
		return point;
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
