package com.fc.lami.model;

import java.util.ArrayList;

import com.cell.CUtil;
import com.fc.lami.Messages.CardData;

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
		}
	}
	
	public void initCard(){
		/** 初始化数字牌 */
		for (int i = 1; i<=13; i++){
			for (int j = 0; j<4; j++){
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
	
	@Override
	public void run(){
		//TODO 处理超时，处理游戏是否结束
	}
}

