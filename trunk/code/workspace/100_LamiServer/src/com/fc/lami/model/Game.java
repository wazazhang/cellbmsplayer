package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;

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
	public CardData matrix[][];
	/** 玩家操作前备份 */
	public CardData matrix_old[][];
	
	/** 存放玩家本回合打出的牌 */
	HashMap<Integer, CardData> player_put = new HashMap<Integer, CardData>();
	
	int s;
	long turn_start_time;
	int mh;
	int mw;
	
	public Game(Desk desk){
		initCard();
		this.desk = desk;
		player_list = desk.getPlayerList();
		/** 初始化每个玩家的手牌 */
		for (int i = 0; i<player_list.length; i++){
			player_list[i].card_list = new HashMap<Integer, CardData>();
			for (int j = 0; j<startCard; j++){
				player_list[i].addCard(getCardFromCard());
			}
			CardData cds[] = new CardData[player_list[i].card_list.size()]; 
			int p = 0;
			for (CardData cd:player_list[i].card_list.values()){
				cds[p++] = cd;
			}
			player_list[i].session.send(new GameStartNotify(cds));
		}
		
		s = CUtil.getRandom(0, player_list.length);
		turn_start_time = System.currentTimeMillis();
		player_list[s].session.send(new TurnStartNotify());
		mw = LamiConfig.MATRIX_WIDTH;
		mh = LamiConfig.MATRIX_HEIGHT;
		matrix = new CardData[mh][mw];
	}
	
	public void initCard(){
		/** 初始化数字牌 */
		int id = 0;
		for (int i = 1; i<=13; i++){
			for (int j = 1; j<5; j++){
				CardData card = new CardData(i, j);
				card.id = id++;
				left_cards.add(card);
				card = new CardData(i, j);
				card.id = id++;
				left_cards.add(card);
			}
		}
		/** 初始化两张鬼牌 */
		CardData card = new CardData(0, 0);
		card.id = id++;
		left_cards.add(card);
		card = new CardData(0, 0);
		card.id = id++;
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
		//process_open_ice = false;
		matrix_old = null;
		player_put.clear();
	}
	
	private void playerGetCard(int n){
		CardData cds[] = new CardData[n];
		for (int i = 0; i<n; i++){
			cds[i] = getCardFromCard();
			player_list[s].addCard(cds[i]);
		}
		player_list[s].session.send(new GetCardNotify(cds));
	}
	
	public boolean playerGetCard(){
		if (!player_put.isEmpty() /*|| process_open_ice*/){
			return false;
		}
		playerGetCard(1);
		toNextPlayer();
		return true;
	}
	
	/** 把牌放到桌面上，桌面上该位置已经有牌则返回false */
	public int putCardToDesk(int cd[], int x, int y){
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
		}
		
		if (x<0 || x+cd.length > mw || y<0 || y>mh-1){ // 位置不对
			return SendCardResponse.SEND_CARD_RESULT_FAIL_LOCATION_WRONG;
		}
		if (!getCurPlayer().isOpenIce){ // 没破冰前，放出的牌不能和桌上的牌拼接
			if ((x-1>=0 && matrix_old[y][x-1]!=null) || (x+cd.length<mw && matrix_old[y][x+cd.length]!=null)){
				return SendCardResponse.SEND_CARD_RESULT_FAIL_SPLIT;
			}
		}
		for (int i = 0; i<cd.length; i++){
			if (matrix[y][x+i]!=null){ // 该位置已经有牌
				return SendCardResponse.SEND_CARD_RESULT_FAIL_NOT_BLANK;
			}
		}
		CardData cards[] = new CardData[cd.length];
		for (int i = 0; i<cd.length; i++){
			cards[i] = player_list[s].removeCard(cd[i]);
			matrix[y][x+i] = cards[i];
			player_put.put(cd[i], cards[i]);
		}
		
		for (int i = 0; i<player_list.length; i++){
			player_list[i].session.send(new SendCardNotify(getCurPlayer().player_id, cards, x, y));
		}
		
//		if (!getCurPlayer().isOpenIce){
//			if (getCardPoint()>=30){
//				openIce();
//			}
//		}
		return SendCardResponse.SEND_CARD_RESULT_SUCCESS;
	}
	
	/** 移动桌面上的牌 */
	public int MoveCard(int cd[], int x, int y){
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
		}
		
		if (x<0 || x+cd.length > mw || y<0 || y>mh-1){ // 位置不对
			return MoveCardResponse.MOVE_CARD_RESULT_FAIL_LOCATION_WRONG;
		}
		
		if (!getCurPlayer().isOpenIce){
			if (player_put.containsKey(cd[0])==false){ // 未破冰前不能移动原有的牌
				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_CANNT_MOVE;
			}
			/** 未破冰前移动的牌不能和桌上原有的牌拼接 */
			if ((x-1>=0 && matrix_old[y][x-1]!=null) || (x+cd.length<mw && matrix_old[y][x+cd.length]!=null)){
				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_SPLIT;
			}
		}
		CardData cards[] = new CardData[cd.length];
		for (int i = 0; i<cd.length; i++){
			cards[i] = getCardFromMatrix(cd[i]);
			if (cards[i] == null){	// 桌面上没有这张牌
				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_CARD_NOEXIST;
			}
		}
		
		for (int i = 0; i<cd.length; i++){
			removeCardFromMatrix(cd[i]);
			matrix[y][x+i] = cards[i];
		}
		
//		if (!getCurPlayer().isOpenIce){
//			if (getCardPoint()>=30){
//				openIce();
//			}
//		}
		return MoveCardResponse.MOVE_CARD_RESULT_SUCCESS;
	}
	
	private CardData getCardFromMatrix(int card_id){
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null && matrix[i][j].id == card_id){
					return matrix[i][j];
				}
			}
		}
		return null;
	}
	
	private void removeCardFromMatrix(int card_id){
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null && matrix[i][j].id == card_id){
					matrix[i][j] = null;
					return;
				}
			}
		}
	}
	
	/** 取回本回合自己放的牌 */
	public int takeCardFromDesk(int card_id[]){
		CardData cds[] = new CardData[card_id.length];
		for (int i = 0; i<card_id.length; i++){
			if (getCardFromMatrix(card_id[i]) == null){ //桌面上没有这张牌
				return RetakeCardResponse.RETAKE_CARD_RESULT_FAIL_NOEXIST;
			}
			if (player_put.containsKey(card_id[i]) == false){ // 该牌不是本回合放上去的
				return RetakeCardResponse.RETAKE_CARD_RESULT_FAIL_NOT_THIS_TURN;
			}
			cds[i] = getCardFromMatrix(card_id[i]);
		}
		for (int i = 0; i<card_id.length; i++){
			removeCardFromMatrix(card_id[i]);
			player_put.remove(cds[i]);
			player_list[s].addCard(cds[i]);
		}

		return RetakeCardResponse.RETAKE_CARD_RESULT_SUCCESS;
	}
	
	/** 检测桌面上的牌是否都合法 */
	public boolean check(){
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw-2; j++){
				int temp = checkSingle(j, i);
				if (temp == 1){	//该位置有牌但不成立
					return false;
				}else{
					if (temp != 0){
						j += temp;
					}
				}
			}
		}
		return true;
	}
	
	public int checkSingle(int x, int y){
		if (matrix[y][x]==null){
			return 0;
		}
		int xs, xe;
		xs = x;
		xe = x;
		while (xs>0 && matrix[y][xs-1]!=null){
			xs = xs-1;
		}
		while (xe<mw-1 && matrix[y][xe+1]!=null){
			xe = xe+1;
		}
		int length = xe-xs+1;
		if (length < 3){
			return 1;
		}
		
		CardData cds[] = new CardData[length];
		for (int i = 0; i<length; i++){
			cds[i] = matrix[y][xs+i];
		}
		
		if (checkGroup(cds)){
			return length;
		}else{
			return 1;
		}
	}
	
	public boolean checkGroup(CardData cds[]){
		CardData c1 = null;
		CardData c2 = null;
		int c1_index = 0;
		for (int i = 0; i<cds.length; i++){
			if (cds[i].point != 0){
				if (c1 == null){
					c1 = cds[i];
					c1_index = i;
				}else{
					c2 = cds[i];
					break;
				}
			}
		}
		
		// 取得两张非百搭牌
		if (c2 == null){ //如果只有一张非百搭牌，则说明牌组中其他牌都是百搭牌，牌组成立
			return true;
		}
		
		if (c1.point == c2.point){	// 点数相同则检测颜色
			if (cds.length>=4){
				return false;
			}
			int t[] = new int[4];
			for (CardData cd : cds){
				if (cd.point!=0 && cd.point !=c1.point){
					return false;
				}
				if (cd.point!=0){
					t[cd.type-1] ++;
				}
			}
			if (t[0]>1 || t[1]>1 || t[2]>1 || t[3]>1){
				return false;
			}
			return true;
		}else{	// 点数不同则检测顺子
			if (cds.length>13){
				return false;
			}
			for (CardData cd:cds){
				if (cd.point != 0 && cd.type != c1.type){ // 若颜色不同则不成立
					return false;
				}
			}
			if (c1.point<c2.point){ // 顺序
				for (int i = c1_index+1; i<cds.length; i++){
					if (cds[i].point!=0 && cds[i].point != c1.point+(i-c1_index)){
						return false;
					}
				}
			}else{ //倒序
				for (int i = c1_index+1; i<cds.length; i++){
					if (cds[i].point!=0 && cds[i].point != c1.point-(i-c1_index)){
						return false;
					}
				}
			}
			return true;
		}
	}
	
	/** 得到打出的牌的点数 */
	public int getCardPoint(){
		int point = 0;
		if (check()){
			for (CardData c : player_put.values()){
				point+= c.point;
			}
		}
		return point;
	}
	
	//public boolean process_open_ice = false;
	/** 破冰 */
//	public void openIce(){
//		getCurPlayer().isOpenIce = true;
//		process_open_ice = true;
//		matrix_old = null;
//		player_put.clear();
//		for (int i = 0; i<player_list.length; i++){
//			player_list[i].session.send(new OpenIceNotify(getCurPlayer().player_id));
//		}
//	}
	
	/** 撤销 */
	public void repeal(){
		matrix = matrix_old;
		matrix_old = null;

		CardData cds[] = new CardData[player_put.size()];
		int t = 0;
		for (CardData cd : player_put.values()){
			getCurPlayer().addCard(cd);
			cds[t++] = cd;
		}
		for (int i = 0; i<player_list.length; i++){
			player_list[i].session.send(new RepealSendCardNotify(getCurPlayer().player_id, cds));
		}
		
		player_put.clear();
		playerGetCard(1);
	}
	
	/** 提交 */
	public int submit(){
		if (!getCurPlayer().isOpenIce){
			if (getCardPoint()>=30){
				getCurPlayer().isOpenIce = true;
				matrix_old = null;
				player_put.clear();
				for (int i = 0; i<player_list.length; i++){
					player_list[i].session.send(new OpenIceNotify(getCurPlayer().player_id));
				}
				toNextPlayer();
				return SubmitResponse.SUBMIT_RESULT_SUCCESS;	// 破冰成功
			}else{
				return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NOT_OPEN_ICE; // 没有破冰
			}
		}
		if (check() == false){ // 牌组不成立
			return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH;
		}

		matrix_old = null;
		player_put.clear();
		toNextPlayer();
		return SubmitResponse.SUBMIT_RESULT_SUCCESS;
	}
	
	@Override
	public void run(){
		//TODO 处理超时，处理游戏是否结束
		if (System.currentTimeMillis() - turn_start_time>=LamiConfig.TURN_INTERVAL){
			if (!player_put.isEmpty() /*|| process_open_ice*/){
				if (check()){
					toNextPlayer();
				}else{
					repeal();
					toNextPlayer();
				}
			}else{
				playerGetCard(1);
				toNextPlayer();
			}
		}
	}
}

