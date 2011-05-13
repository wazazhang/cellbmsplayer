package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.cell.CUtil;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.GameOverNotify;
import com.fc.lami.Messages.GameStartNotify;
import com.fc.lami.Messages.GetCardNotify;
import com.fc.lami.Messages.MainMatrixChangeNotify;
import com.fc.lami.Messages.MoveCardResponse;
import com.fc.lami.Messages.OpenIceNotify;
import com.fc.lami.Messages.OperateCompleteNotify;
import com.fc.lami.Messages.RepealSendCardNotify;
import com.fc.lami.Messages.ResultPak;
import com.fc.lami.Messages.RetakeCardResponse;
import com.fc.lami.Messages.SendCardNotify;
import com.fc.lami.Messages.SendCardResponse;
import com.fc.lami.Messages.SubmitResponse;
import com.fc.lami.Messages.SynchronizeResponse;
import com.fc.lami.Messages.TurnEndNotify;
import com.fc.lami.Messages.TurnStartNotify;

public class Game implements Runnable
{
	public Desk desk;
	public Player player_list[];
	public Player cur_player;
	
	public boolean is_over = false;
	final static public int startCard = 14;
	ArrayList<CardData> left_cards = new ArrayList<CardData>();
	public boolean is_start_time = false; //发牌时间
	
	/** 桌面牌矩阵 */
	public CardData matrix[][];
	/** 玩家操作前备份 */
	public CardData matrix_old[][];
	
	/** 存放玩家本回合打出的牌 */
	HashMap<Integer, CardData> player_put = new HashMap<Integer, CardData>();
	
	int cur_player_index;
	long start_time;			// 游戏开始时间
	long turn_start_time;		// 回合开始时间
	long operate_start_time;	// 操作开始时间
	int mh;
	int mw;
	
	public Game(Desk desk){
		this.desk = desk;
		gameInit();
	}
	
	public void gameInit(){
		initCard();
		player_list = desk.getPlayerList();
		/** 初始化每个玩家的手牌 */
		for (int i = 0; i<player_list.length; i++){
//			player_list[i].card_list = new HashMap<Integer, CardData>();
			player_list[i].card_list.clear();
			for (int j = 0; j<startCard; j++){
				player_list[i].addCard(getCardFromCard());
			}
			CardData cds[] = new CardData[player_list[i].card_list.size()]; 
			int p = 0;
			for (CardData cd:player_list[i].card_list.values()){
				cds[p++] = cd;
			}
			player_list[i].session.send(new GameStartNotify(cds, player_list[i].isCanResetGame()));
		}
		
		cur_player_index = CUtil.getRandom(0, player_list.length);
		cur_player = player_list[cur_player_index];
		start_time = System.currentTimeMillis();
		is_start_time = true;
		mw = LamiConfig.MATRIX_WIDTH;
		mh = LamiConfig.MATRIX_HEIGHT;
		matrix = new CardData[mh][mw];
	}
	
	public boolean isStartTime(){
		return is_start_time;
	}
	
	public void initCard(){
		/** 初始化数字牌 */
		int id = 0;
		for (int i = 7; i<=13; i++){	// 1~6的牌舍去便于测试
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
		
//		/** 测试 多加20张鬼牌 */
//		for (int i = 0; i<20; i++){
//			card = new CardData(0, 0);
//			card.id = id++;
//			left_cards.add(card);
//		}
		
		System.out.println("initcard = "+ left_cards.size());
	}
	
	public CardData getCardFromCard(){
		if (left_cards.size()==0){
			onGameOver();
			return null;
		}else{
			int n = CUtil.getRandom(0, left_cards.size());
			return left_cards.remove(n);
		}
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
		int game_over_type = 0;
		ResultPak[] rp = new ResultPak[player_list.length];
		if (left_cards.size()==0){ // 如果牌堆的牌被取完，则比较玩家手中的牌的点数
			int point[] = new int[player_list.length];
			int min = 9999999;
			int w = 0;
			for (int i = 0; i<player_list.length; i++){
				point[i] = player_list[i].getHandCardPonit();
				if (point[i]<min){
					min = point[i];
					w = i;
				}
			}
			int sum = 0;
			for (int i = 0; i<player_list.length; i++){
				if (i!=w){
					point[i] = point[i]-min;
					if (!player_list[i].isOpenIce){
						point[i] += 100;
						if (player_list[i].isCanOpenIce()){
							point[i] += 100;
						}
					}
					rp[i] = player_list[i].onPlayerLose(point[i]);
					sum+=point[i];
				}
			}
			rp[w] = player_list[w].onPlayerWin(sum);
			game_over_type = GameOverNotify.GAME_OVER_TYPE_CARD_OVER;
		}else{
			int point[] = new int[player_list.length];
			int sum = 0;
			int w = 0;
			for (int i = 0; i<player_list.length; i++){
				if (player_list[i].card_list.size()==0){
					w = i;
				}else{
					point[i] = player_list[i].getHandCardPonit();
					if (!player_list[i].isOpenIce){
						point[i] += 100;
						if (player_list[i].isCanOpenIce()){
							point[i] += 100;
						}
					}
					rp[i] = player_list[i].onPlayerLose(point[i]);
					sum += point[i];
				}
			}
			rp[w] = player_list[w].onPlayerWin(sum);
			game_over_type = GameOverNotify.GAME_OVER_TYPE_CLEAR;
		}
		GameOverNotify notify = new GameOverNotify(game_over_type, rp);
		
		for (int i = 0; i<player_list.length; i++){
			player_list[i].session.send(notify);
		}
	}
	
	public Player getCurPlayer(){
		return cur_player;
	}
	
	public Player getNextPlayer(){
		int z = (cur_player_index+1) % player_list.length;
		return player_list[z];
	}
	
	public void toNextPlayer(){

		cur_player.session.send(new TurnEndNotify());
		cur_player_index = (cur_player_index+1) % player_list.length;
		cur_player = player_list[cur_player_index];
		turn_start_time = System.currentTimeMillis();
		operate_start_time = turn_start_time;
		TurnStartNotify notify = new TurnStartNotify(cur_player.player_id, 
														getLeftCardNumber()/*, 
														System.currentTimeMillis()+LamiConfig.TURN_INTERVAL*/);
		desk.broadcast(notify);
		//process_open_ice = false;
		matrix_old = null;
		player_put.clear();
		System.out.println("轮到下一个玩家 时间 "+turn_start_time);
	}
	
	private int playerGetCard(int n){
		CardData cds[] = new CardData[n];
		int c = 0;
		for (int i = 0; i<n; i++){
			CardData cd = getCardFromCard();
			if (cd!=null){
				cds[i] = cd;
				cur_player.addCard(cds[i]);
				c++;
			}
		}
		cur_player.session.send(new GetCardNotify(cds));
		return c;
	}
	
	public boolean playerGetCard(){
		if (!player_put.isEmpty() /*|| process_open_ice*/){
			return false;
		}
		if (playerGetCard(1)==1){
			toNextPlayer();
			return true;
		}
		return false;
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
			cards[i] = player_list[cur_player_index].removeCard(cd[i]);
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
			cur_player.addCard(cds[i]);
		}
		return RetakeCardResponse.RETAKE_CARD_RESULT_SUCCESS;
	}
	
	int complete_card_count = 0;
	
	private int getCompleteCardCount(){	//获取成立的牌数
		int g = 0;
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw-2; j++){
				CheckResult cr = checkSingle(j, i);
				if (cr.is_success == false){	//该位置有牌但不成立

				}else{
					g+=cr.n;
				}
				j += cr.n;
			}
		}
		return g;
	}
	
	/** 检测桌面上的牌是否都合法 */
	public boolean check(){
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw-2; j++){
				CheckResult cr = checkSingle(j, i);
				if (cr.is_success == false && cr.n!=0){	//该位置有牌但不成立
					return false;
				}else{
					j += cr.n;
				}
			}
		}
		return true;
	}
	
	class CheckResult{
		boolean is_success;
		int n;
	}
	
	public CheckResult checkSingle(int x, int y){
		CheckResult cr = new CheckResult();
		if (matrix[y][x]==null){
			cr.n = 0;
			cr.is_success = false;
			return cr;
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
		cr.n = length;
		if (length < 3){
			cr.is_success = false;
			return cr;
		}
		
		CardData cds[] = new CardData[length];
		for (int i = 0; i<length; i++){
			cds[i] = matrix[y][xs+i];
		}
		
		if (checkGroup(cds)){
			cr.is_success = true;
			return cr;
		}else{
			for (CardData cd:cds){
				System.out.println(cd.toString());
			}
			cr.is_success = false;
			return cr;
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
			if (cds.length>4){
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
	
	public void PlayerRepeal(){
		if (matrix_old!=null){
			repeal();
			if (playerGetCard(3)==3){
				toNextPlayer();
			}
		}
	}
	
	/** 撤销 */
	private void repeal(){
		if (matrix_old!=null){
			matrix = matrix_old;
			matrix_old = null;
		}

		CardData cds[] = new CardData[player_put.size()];
		int t = 0;
		for (CardData cd : player_put.values()){
			getCurPlayer().addCard(cd);
			cds[t++] = cd;
		}

		desk.broadcast(new RepealSendCardNotify(getCurPlayer().player_id, cds));

		
		ArrayList<CardData> ml = new ArrayList<CardData>();
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null){
					matrix[i][j].x = j;
					matrix[i][j].y = i;
					ml.add(matrix[i][j]);
				}
			}
		}
		CardData[] m = new CardData[ml.size()];
		ml.toArray(m);
		desk.broadcast(new MainMatrixChangeNotify(true, m));
		player_put.clear();
	}
	
	/** 提交 */
	public int submit(){
		if (player_put.size()==0){
			System.err.println("submit 没有出牌");
			return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NO_SEND;
		}
		if (!getCurPlayer().isOpenIce){
			if (getCardPoint()>=30){
				getCurPlayer().isOpenIce = true;
				for (int i = 0; i<player_list.length; i++){
					player_list[i].session.send(new OpenIceNotify(getCurPlayer().player_id));
				}
				System.out.println("player:"+getCurPlayer().getName()+"余牌:"+ getCurPlayer().card_list.size());
				if (getCurPlayer().card_list.size() == 0){	//如果玩家牌已出完
					onGameOver();
				}else{
					toNextPlayer();
				}
				return SubmitResponse.SUBMIT_RESULT_SUCCESS;	// 破冰成功
			}else{
				System.err.println("submit 没有破冰");
				return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NOT_OPEN_ICE; // 没有破冰
			}
		}
		if (check() == false){ // 牌组不成立
			System.err.println("submit 牌组不成立");
			return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH;
		}
		System.out.println("player:"+getCurPlayer().getName()+"余牌:"+ getCurPlayer().card_list.size());
		if (getCurPlayer().card_list.size() == 0){	//如果玩家牌已出完
			onGameOver();
		}else{
			toNextPlayer();
		}
		return SubmitResponse.SUBMIT_RESULT_SUCCESS;
	}
	
	public boolean MainMatrixChange(CardData[] cds){
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
		}		
		
		ArrayList<CardData> c1 = new  ArrayList<CardData>(); //桌面上新加的牌
		ArrayList<CardData> c2 = new  ArrayList<CardData>(); //桌面上减少的牌
		HashMap<Integer, CardData> new_matrix = new HashMap<Integer, CardData>();
		for (CardData cd:cds){
			if (getCardFromMatrix(cd.id) == null){
				c1.add(cd);
			}
			new_matrix.put(cd.id, cd);
		}
		
		if (new_matrix.size()!=cds.length){ //有被复制的牌
			System.err.println("有被复制的牌");
			return false;
		}
		
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null){
					if (new_matrix.get(matrix[i][j].id)==null){
						c2.add(matrix[i][j]);
					}
				}
			}
		}
		
		ArrayList<CardData> c3 = new  ArrayList<CardData>();
		if (!c1.isEmpty()){  // 桌面上有新加的牌，去当前玩家的手里找
			for (CardData cd:c1){
				CardData cp = getCurPlayer().card_list.get(cd.id);
				if (cp == null){	// 该牌是凭空捏造出来的
					System.err.println("该牌是凭空捏造出来的");
					return false;
				}else{
					cp.x = cd.x;
					cp.y = cd.y;
					c3.add(cp);
				}
			}
		}
		for (CardData cd : c2){
			if (player_put.get(cd.id) == null){ // 该牌不是本回合放上去的
				System.err.println("该牌不是本回合放上去的");
				return false;
			}
		}
		
		CardData[][] matrix_new = new CardData[mh][mw]; 
		CardData[] notify_cds = new CardData[cds.length];
		int p=0;
		for (CardData cd : new_matrix.values()){
			CardData cm = getCardFromMatrix(cd.id);
			if (cm != null){
				cm.x = cd.x;
				cm.y = cd.y;
				cm.isSended = cd.isSended;
				matrix_new[cm.y][cm.x] = cm;
				notify_cds[p++] = cm;
			}
		}
		matrix = matrix_new;
		for (CardData cd:c3){
			matrix_new[cd.y][cd.x] = cd;
			notify_cds[p++] = cd;
			player_put.put(cd.id, cd);
			getCurPlayer().card_list.remove(cd.id);
		}
		
		System.out.println("MainMatrixChange player_put size = "+player_put.size());
		for (CardData cd : c2){
			getCurPlayer().addCard(cd);
			player_put.remove(cd.id);
		}
		desk.broadcast(new MainMatrixChangeNotify(false, notify_cds));
		int cur_complete_card_count = getCompleteCardCount();
		if (cur_complete_card_count>complete_card_count){
			operate_start_time = System.currentTimeMillis();
			getCurPlayer().session.send(new OperateCompleteNotify());
		}
		complete_card_count = cur_complete_card_count;
		return true;
	}
	
	
	private boolean isProcessed(){
		if (matrix_old == null){
			return false;
		}
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (!isSameCard(matrix[i][j],matrix_old[i][j])){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isSameCard(CardData a, CardData b){
		if (a == b){
			return true;
		}
		if (a==null && b!=null){
			return false;
		}
		if (a!=null && b==null){
			return false;
		}
		if (a.type != b.type || a.point != b.point){
			return false;
		}
		return true;
	}
	
	/** 同步桌面和玩家的牌 */
	public SynchronizeResponse SynchronizePlayerCard(int player_id){
		ArrayList<CardData> ml = new ArrayList<CardData>();
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null){
					matrix[i][j].x = j;
					matrix[i][j].y = i;
					ml.add(matrix[i][j]);
				}
			}
		}
		CardData[] m = new CardData[ml.size()];
		ml.toArray(m);
		CardData[] p = null;
		Player player = getPlayerByID(player_id);
		if (player!=null){
			p = new CardData[player.card_list.size()];
			int t = 0;
			for (CardData cd:player.card_list.values()){
				p[t++] = cd;
			}
		}
		
		return new SynchronizeResponse(m, p, getLeftCardNumber());
	}
	
	
	private Player getPlayerByID(int id){
		for (Player p:player_list){
			if (p.player_id == id){
				return p;
			}
		}
		return null;
	}
	
	public void onPlayerLeave(Player player){
		player.onPlayerEscape();
		if (getCurPlayer() == player){
			repeal();
		}
		Player next_player = getNextPlayer();
		player_list = desk.getPlayerList();
		
		if (player_list.length>0){
			if (player_list.length<2){
				ResultPak[] rp = new ResultPak[player_list.length];
				if (player_list[0].isOpenIce){
					rp[0] = player_list[0].onPlayerWin(5);
				}
				GameOverNotify notify = new GameOverNotify(GameOverNotify.GAME_OVER_TYPE_ESCAPE, rp);
				desk.broadcast(notify);
				is_over = true;
			}else{
				for (int i = 0; i<player_list.length; i++){
					if (player_list[i] == next_player){
						cur_player_index = i;
						turn_start_time = System.currentTimeMillis();
						TurnStartNotify notify = new TurnStartNotify(player_list[cur_player_index].player_id, 
																		getLeftCardNumber()/*, 
																		System.currentTimeMillis()+LamiConfig.TURN_INTERVAL*/);
						desk.broadcast(notify);
						//process_open_ice = false;
						matrix_old = null;
						player_put.clear();
						System.out.println("轮到下一个玩家");
						return;
					}
				}
			}
		}else{
			is_over = true;
		}
		
	}
	
	@Override
	public void run(){
		if (is_start_time){	//	游戏开始后延迟10秒轮到第一个玩家
			if (System.currentTimeMillis() - start_time >= 10000){
				turn_start_time = System.currentTimeMillis();
				operate_start_time = turn_start_time;
				TurnStartNotify notify = new TurnStartNotify(player_list[cur_player_index].player_id, 
																getLeftCardNumber()/*, 
																System.currentTimeMillis()+LamiConfig.TURN_INTERVAL*/);
				desk.broadcast(notify);
				is_start_time = false;
			}
		}
		//TODO 处理超时，处理游戏是否结束
		else if (!is_over){
			long cur_time = System.currentTimeMillis();
			if (cur_time - operate_start_time >= LamiConfig.OPERATE_TIME ||
					cur_time - turn_start_time>=LamiConfig.TURN_INTERVAL){
				System.err.println("player " + getCurPlayer().getName() + " 超时");
				System.out.println(cur_time);
				if (!player_put.isEmpty() || isProcessed()/*|| process_open_ice*/){
					if (submit() == SubmitResponse.SUBMIT_RESULT_SUCCESS){
						
					}else{
						repeal();
						if (playerGetCard(3) == 3){ // 撤销罚牌3张
							toNextPlayer();
						}
					}
				}else{
					if (playerGetCard(1)==1){
						toNextPlayer();
					}
				}
			}
		}
	}
}

