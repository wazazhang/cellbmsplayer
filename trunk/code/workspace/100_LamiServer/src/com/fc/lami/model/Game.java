package com.fc.lami.model;

import java.util.ArrayList;

import com.cell.CUtil;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.*;

public class Game implements Runnable
{
	public Desk desk;
	public Player player_list[];
	
	public boolean is_over = false;
	final static public int startCard = 14;
	ArrayList<CardData> left_cards = new ArrayList<CardData>();
	
	/** 桌面牌矩阵 */
	public CardData matrix[][] = new CardData[10][26];
	/** 玩家操作前备份 */
	public CardData matrix_old[][] = new CardData[10][26];
	
	int s;
	long turn_start_time;
	
	public Game(Desk desk){
		initCard();
		this.desk = desk;
		player_list = desk.getPlayerList();
		/** 初始化每个玩家的手牌 */
		for (int i = 0; i<player_list.length; i++){
			player_list[i].card_list = new ArrayList<CardData>();
			for (int j = 0; j<startCard; j++){
				player_list[i].card_list.add(getCardFromCard());
			}
			CardData cds[] = new CardData[player_list[i].card_list.size()]; 
			player_list[i].card_list.toArray(cds);
			player_list[i].session.send(new GameStartNotify(cds));
		}
		
		s = CUtil.getRandom(0, player_list.length);
		turn_start_time = System.currentTimeMillis();
		player_list[s].session.send(new TurnStartNotify());
	}
	
	public void initCard(){
		/** 初始化数字牌 */
		for (int i = 1; i<=13; i++){
			for (int j = 1; j<5; j++){
				CardData card = new CardData(i, j);
				left_cards.add(card);
				card = new CardData(i, j);
				left_cards.add(card);
			}
		}
		/** 初始化两张鬼牌 */
		CardData card = new CardData(0, 0);
		left_cards.add(card);
		card = new CardData(0, 0);
		left_cards.add(card);
		
		System.out.println("initcard = "+ left_cards.size());
	}
	
	public CardData getCardFromCard(){
		int n = CUtil.getRandom(0, left_cards.size());
		return left_cards.remove(n);
	}
	
	public int getLeftCardNumber(){
		return left_cards.size();
	}
	
	public boolean isGameOver(){
		return is_over;
	}
	
	/** 当游戏结束时做的处理，玩家加减分，胜率什么的 */
	public void onGameOver(){
		//TODO
		is_over = true;
	}
	
	public Player getCurPlayer(){
		return player_list[s];
	}
	
	public Player getNextPlayer(){
		int z = (s+1) % player_list.length;
		return player_list[z];
	}
	
	public void toNextPlayer(){
		turn_start_time = System.currentTimeMillis();
		player_list[s].session.send(new TurnEndNotify());
		s = (s+1) % player_list.length;
		player_list[s].session.send(new TurnStartNotify());
	}
	
	public void playerGetCard(int n){
		CardData cds[] = new CardData[n];
		for (int i = 0; i<n; i++){
			cds[i] = getCardFromCard();
		}
		player_list[s].session.send(new GetCardNotify(cds));
	}
	
	@Override
	public void run(){
		//TODO 处理超时，处理游戏是否结束
		if (System.currentTimeMillis() - turn_start_time>=LamiConfig.TURN_INTERVAL){
			playerGetCard(1);
			toNextPlayer();
		}
	}
}

