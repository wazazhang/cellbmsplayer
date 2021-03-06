package com.fc.lami.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.LamiConfig;
import com.fc.lami.Messages.CardData;
import com.fc.lami.Messages.GameOverNotify;
import com.fc.lami.Messages.GameStartNotify;
import com.fc.lami.Messages.GetCardNotify;
import com.fc.lami.Messages.MainMatrixChangeNotify;
import com.fc.lami.Messages.MainMatrixChangeResponse;
import com.fc.lami.Messages.MoveCardToDeskResponse;
import com.fc.lami.Messages.MoveCardToPlayerResponse;
import com.fc.lami.Messages.OpenIceNotify;
import com.fc.lami.Messages.OperateCompleteNotify;
import com.fc.lami.Messages.RepealSendCardNotify;
import com.fc.lami.Messages.ResultPak;
import com.fc.lami.Messages.SubmitResponse;
import com.fc.lami.Messages.SynchronizeResponse;
import com.fc.lami.Messages.TurnEndNotify;
import com.fc.lami.Messages.TurnStartNotify;
import com.fc.lami.Messages.TimeOutNotify;
import com.fc.lami.Messages.GameOverToRoomNotify;

public class Game
{
	public Desk desk;
	public Player player_list[];
	public Player cur_player;
	
	public boolean is_over = false;
	public int startCard = 14;
	ArrayList<CardData> left_cards = new ArrayList<CardData>();
//	public boolean is_start_time = false; //发牌时间
	
	/** 发牌时间 */
	final static public int GAME_STATE_GET_CARD = 0;
	/** 确认手牌时间 */
	final static public int GAME_STATE_CONFILM = 1;
	/** 游戏中 */
	final static public int GAME_STATE_GAMING = 2;
	/** 游戏结束 */
	final static public int GAME_STATE_OVER = 3;
	public int game_state;
	boolean is_fast_game = false;
	
	/** 桌面牌矩阵 */
	public CardData matrix[][];
	/** 玩家操作前备份 */
	public CardData matrix_old[][];
	
	/** 存放玩家本回合打出的牌 */
	HashMap<Integer, CardData> player_put = new HashMap<Integer, CardData>();
	
	int cur_player_index;
//	long start_time;			// 游戏开始时间
//	long turn_start_time;		// 回合开始时间
//	long operate_start_time;	// 操作开始时间
	int mh;
	int mw;
	
	int escape_point = 0; //逃跑扣的分
	
	public Game(Desk desk, ThreadPool thread_pool){
		this.desk = desk;
		this.thread_pool = thread_pool;
		if (desk.getRoom().is_fast_game == 1){
			is_fast_game = true;
		}
		startCard = desk.getRoom().start_card_number;
		gameInit();
	}
	
	public void gameInit(){
		initCard();
		
		cancelOperateTimer();
		cancelTrunTimer();
		
		player_list = desk.getPlayerList();
		
		startCard = Math.min(left_cards.size()/player_list.length, startCard);
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
			player_list[i].onGameStart();
			player_list[i].session.send(new GameStartNotify(cds));
		}
		
		confilm = new int[player_list.length];
		cur_player_index = CUtil.getRandom(0, player_list.length);
		cur_player = player_list[cur_player_index];

		mw = LamiConfig.MATRIX_WIDTH;
		mh = LamiConfig.MATRIX_HEIGHT;
		matrix = new CardData[mh][mw];
		
		game_state = GAME_STATE_GET_CARD;
	}
	
//	public boolean isStartTime(){
//		return is_start_time;
//	}
	
	private int confilm[];
	
	public void onPlayerConfilm(Player player){
		if (!isPlayerInGame(player)) return;
		
		
		for (int i = 0; i<confilm.length; i++){
			if (confilm[i]==0){
				confilm[i] = player.player_id;
				break;
			}else if (confilm[i] == player.player_id){
				break;
			}
		}
		if (confilm[confilm.length-1]!=0){
			gameStart();
		}
	}
	
	public void onPlayerGetCardOver(Player player){
		if (!isPlayerInGame(player)) return;
		
		if (game_state == GAME_STATE_GET_CARD){
			gameConfilm();
		}
	}
	
	public void initCard(){
		left_cards.clear();
		/** 初始化数字牌 */
		int id = 0;
		int card_start = 1;
		if (is_fast_game){
			if (desk.getPlayerNumber()==4){
				card_start = 1;
			}
			else if (desk.getPlayerNumber() == 3){
				card_start = 4;
			}
			else if (desk.getPlayerNumber() == 2){
				card_start = 7;
			}
		}
		for (int i = card_start; i<=13; i++){	// 1~6的牌舍去便于测试
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
		card = new CardData(0, 1);
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
	
	public boolean isPlayerInGame(Player p){
		Player p2 = getPlayerByID(p.player_id);
		if (p2!=null){
			return true;
		}
		return false;
	}
	
//	public boolean isGameOver(){
//		return is_over;
//	}
	
	public boolean isConfilmTime(){
		return game_state == GAME_STATE_CONFILM;
	}
	
	public boolean isGetCardTime(){
		return game_state == GAME_STATE_GET_CARD;
	}
	
	public boolean isGameTime(){
		return game_state == GAME_STATE_GAMING;
	}
	
	public void gameConfilm(){
		if (game_state == GAME_STATE_GET_CARD){
			cancelOperateTimer();
			is_over = false;
			if (thread_pool!=null){
				future.set(thread_pool.schedule(new Turn(), LamiConfig.READY_TIME));
			}
			game_state = GAME_STATE_CONFILM;
		}
	}
	
	public void gameStart(){
		if (game_state == GAME_STATE_CONFILM){
			TurnStartNotify notify = new TurnStartNotify(player_list[cur_player_index].player_id, 
					getLeftCardNumber()/*, 
					System.currentTimeMillis()+LamiConfig.TURN_INTERVAL*/);
			desk.broadcast(notify);
			game_state = GAME_STATE_GAMING;
			resetOperateTimer();
			resetTurnTimer();
		}
	}
	
	/** 当游戏结束时做的处理，玩家加减分，胜率什么的 */
	public void onGameOver(){
		//TODO
		if (is_over){
			return;
		}
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
			if (player_list[w].isOpenIce){
				for (int i = 0; i<player_list.length; i++){
					if (i!=w){
						point[i] = point[i]-min;
						rp[i] = player_list[i].onPlayerLose(point[i]);
						sum+=point[i];
					}
				}
				rp[w] = player_list[w].onPlayerWin(sum);
			}else{
				for (int i = 0; i<player_list.length; i++){
					rp[i] = player_list[i].onPlayerLose(0);
				}
			}
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
					rp[i] = player_list[i].onPlayerLose(point[i]);
					sum += point[i];
				}
			}
			rp[w] = player_list[w].onPlayerWin(sum);
			game_over_type = GameOverNotify.GAME_OVER_TYPE_CLEAR;
		}
		GameOverNotify notify = new GameOverNotify(game_over_type, rp);
		
		desk.broadcast(notify);
		desk.game = null;
		cancelOperateTimer();
		cancelTrunTimer();
		thread_pool = null;
		desk.getLogger().info("desk [" + desk.desk_id + "] game over");
		desk.getRoom().broadcast(new GameOverToRoomNotify(desk.desk_id));
	}
	
	public Player getCurPlayer(){
		return cur_player;
	}
	
	public Player getNextPlayer(){
		if (player_list.length == 0){
			return null;
		}
		int z = (cur_player_index+1) % player_list.length;
		return player_list[z];
	}
	
	public void toNextPlayer(){

		cur_player.session.send(new TurnEndNotify());
		cur_player_index = (cur_player_index+1) % player_list.length;
		cur_player = player_list[cur_player_index];
		matrix_old = null;
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j]!=null){
					matrix[i][j].isSended = true;
				}
			}
		}
		TurnStartNotify notify = new TurnStartNotify(cur_player.player_id, 
														getLeftCardNumber()/*, 
														System.currentTimeMillis()+LamiConfig.TURN_INTERVAL*/);
		desk.broadcast(notify);
		//process_open_ice = false;
		player_put.clear();
		resetOperateTimer();
		resetTurnTimer();
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
	
//	/** 把牌放到桌面上，桌面上该位置已经有牌则返回false */
//	public int putCardToDesk(int cd[], int x, int y){
//		if (matrix_old == null){
//			matrix_old = matrix;
//			matrix = new CardData[mh][mw];
//			for (int i = 0; i<mh; i++){
//				for (int j = 0; j<mw; j++){
//					matrix[i][j] = matrix_old[i][j];
//				}
//			}
//		}
//		
//		if (x<0 || x+cd.length > mw || y<0 || y>mh-1){ // 位置不对
//			return SendCardResponse.SEND_CARD_RESULT_FAIL_LOCATION_WRONG;
//		}
//		if (!getCurPlayer().isOpenIce){ // 没破冰前，放出的牌不能和桌上的牌拼接
//			if ((x-1>=0 && matrix_old[y][x-1]!=null) || (x+cd.length<mw && matrix_old[y][x+cd.length]!=null)){
//				return SendCardResponse.SEND_CARD_RESULT_FAIL_SPLIT;
//			}
//		}
//		for (int i = 0; i<cd.length; i++){
//			if (matrix[y][x+i]!=null){ // 该位置已经有牌
//				return SendCardResponse.SEND_CARD_RESULT_FAIL_NOT_BLANK;
//			}
//		}
//		CardData cards[] = new CardData[cd.length];
//		for (int i = 0; i<cd.length; i++){
//			cards[i] = player_list[cur_player_index].removeCard(cd[i]);
//			cards[i].x = x+i;
//			cards[i].y = y;
//			matrix[y][x+i] = cards[i];
//			player_put.put(cd[i], cards[i]);
//		}
//		
//		desk.broadcast(new SendCardNotify(getCurPlayer().player_id, cards));
//		return SendCardResponse.SEND_CARD_RESULT_SUCCESS;
//	}
	
	
	
//	/** 移动桌面上的牌 */
//	public int MoveCard(int cd[], int x, int y){
//		if (matrix_old == null){
//			matrix_old = matrix;
//			matrix = new CardData[mh][mw];
//			for (int i = 0; i<mh; i++){
//				for (int j = 0; j<mw; j++){
//					matrix[i][j] = matrix_old[i][j];
//				}
//			}
//		}
//		
//		if (x<0 || x+cd.length > mw || y<0 || y>mh-1){ // 位置不对
//			return MoveCardResponse.MOVE_CARD_RESULT_FAIL_LOCATION_WRONG;
//		}
//		
//		if (!getCurPlayer().isOpenIce){
//			if (player_put.containsKey(cd[0])==false){ // 未破冰前不能移动原有的牌
//				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_CANNT_MOVE;
//			}
//			/** 未破冰前移动的牌不能和桌上原有的牌拼接 */
//			if ((x-1>=0 && matrix_old[y][x-1]!=null) || (x+cd.length<mw && matrix_old[y][x+cd.length]!=null)){
//				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_SPLIT;
//			}
//		}
//		CardData cards[] = new CardData[cd.length];
//		for (int i = 0; i<cd.length; i++){
//			cards[i] = getCardFromMatrix(cd[i]);
//			if (cards[i] == null){	// 桌面上没有这张牌
//				return MoveCardResponse.MOVE_CARD_RESULT_FAIL_CARD_NOEXIST;
//			}
//		}
//		
//		for (int i = 0; i<cd.length; i++){
//			removeCardFromMatrix(cd[i]);
//			cards[i].x = x+i;
//			cards[i].y = y;
//			matrix[y][x+i] = cards[i];
//		}
//		
//		MoveCardNotify notify = new MoveCardNotify(getCurPlayer().player_id, cd, x, y);
//		desk.broadcast(notify);
////		if (!getCurPlayer().isOpenIce){
////			if (getCardPoint()>=30){
////				openIce();
////			}
////		}
//		return MoveCardResponse.MOVE_CARD_RESULT_SUCCESS;
//	}
	
	private void broadMatrix(){
		ArrayList<CardData> list = new ArrayList<CardData>();
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j]!=null){
					list.add(matrix[i][j]);
				}
			}
		}
		CardData notify_cds[] = new CardData[list.size()];
		list.toArray(notify_cds);
		desk.broadcast(new MainMatrixChangeNotify(false, getCurPlayer().player_id, notify_cds));
		int cur_complete_card_count = getCompleteCardCount();
		if (cur_complete_card_count>complete_card_count){
			
			desk.broadcast(new OperateCompleteNotify(getCurPlayer().player_id));
			resetOperateTimer();
		}
		complete_card_count = cur_complete_card_count;
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
	
	private CardData removeCardFromMatrix(int card_id){
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw; j++){
				if (matrix[i][j] != null && matrix[i][j].id == card_id){
					CardData cd = matrix[i][j];
					matrix[i][j] = null;
					return cd;
				}
			}
		}
		return null;
	}
	
	private CardData getCardFromCurPlayer(int card_id){
		return cur_player.card_list.get(card_id);
	}
	
//	/** 取回本回合自己放的牌 */
//	public int takeCardFromDesk(int card_id[]){
//		CardData cds[] = new CardData[card_id.length];
//		for (int i = 0; i<card_id.length; i++){
//			if (getCardFromMatrix(card_id[i]) == null){ //桌面上没有这张牌
//				return RetakeCardResponse.RETAKE_CARD_RESULT_FAIL_NOEXIST;
//			}
//			if (player_put.containsKey(card_id[i]) == false){ // 该牌不是本回合放上去的
//				return RetakeCardResponse.RETAKE_CARD_RESULT_FAIL_NOT_THIS_TURN;
//			}
//			cds[i] = getCardFromMatrix(card_id[i]);
//		}
//		for (int i = 0; i<card_id.length; i++){
//			removeCardFromMatrix(card_id[i]);
//			player_put.remove(cds[i]);
//			cur_player.addCard(cds[i]);
//		}
//		
//		RetakeCardNotify notify = new RetakeCardNotify(getCurPlayer().player_id, card_id);
//		desk.broadcast(notify);
//		return RetakeCardResponse.RETAKE_CARD_RESULT_SUCCESS;
//	}
	
	public MoveCardToDeskResponse moveCardv3(int card_ids[], int x, int y){
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
		}
		
		if (x<0 || x+card_ids.length > mw || y<0 || y>mh-1){ // 位置不对
			return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_LOCATION_WRONG);
		}
		CardData cards[] = new CardData[card_ids.length]; //所有移动的牌
		ArrayList<CardData> cards_m = new ArrayList<CardData>(); //桌上的牌
		ArrayList<CardData> cards_p = new ArrayList<CardData>(); //玩家手里的牌
		
		for (int i = 0; i<card_ids.length; i++){
			cards[i] = removeCardFromMatrix(card_ids[i]);
			if (cards[i]==null){
				cards[i] = getCurPlayer().card_list.get(card_ids[i]);
				if (cards[i]==null){
					return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_CARD_NOEXIST);	//没有此牌
				}else{
					cards_p.add(cards[i]);
				}
			}else{
				cards_m.add(cards[i]);
			}
		}
		
		if (!getCurPlayer().isOpenIce){///如果玩家未破冰，则不能操作已有的牌，不能和原来的牌拼接
			for(CardData cd:cards_m){
				if (player_put.get(cd.id)==null){
					return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_CANNT_MOVE); //不能移动桌面原有的牌
				}
			}
			
			for (CardData cd:cards_p){
				if (cd.x>=1 && matrix_old[cd.y][cd.x-1]!= null){
					return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_CANNT_SPLICE); //不能和已有的牌拼接
				}
				if (cd.x<mw-1 && matrix_old[cd.y][cd.x+1]!= null){
					return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_CANNT_SPLICE); //不能和已有的牌拼接
				}
			}
		}
		
		for (int i = 0; i<cards.length; i++){
			if (matrix[y][x+i]!=null){
				for (CardData cd:cards_m){//重置桌面原有牌
					matrix[cd.y][cd.x] = cd;
				}
				return new MoveCardToDeskResponse(MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_FAIL_AREALDY_HAVE_CARD); //原位置有牌
			}
		}
		
		for (int i = 0; i<cards.length; i++){
			cards[i].x = x+i;
			cards[i].y = y;
			matrix[y][x+i] = cards[i];
		}
		
		for (CardData cd:cards_p){
			player_put.put(cd.id, cd);
			getCurPlayer().card_list.remove(cd.id);
		}
		broadMatrix();
		return new MoveCardToDeskResponse(
				MoveCardToDeskResponse.MOVE_CARD_TO_DESK_RESULT_SUCCESS,
				card_ids, x, y);
	}
	
	public MoveCardToPlayerResponse moveBack(int[] card_ids){
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
			return new MoveCardToPlayerResponse(MoveCardToPlayerResponse.MOVE_CARD_TO_PLAYER_RESULT_FAIL_CANNT_TAKEBACK); //原有的牌不能取回 
		}
		
		ArrayList<CardData> cards_m = new ArrayList<CardData>();
		for (int id:card_ids){
			CardData cd = getCardFromMatrix(id);
			if (cd!=null){
				if (cd.isSended){
					return new MoveCardToPlayerResponse(MoveCardToPlayerResponse.MOVE_CARD_TO_PLAYER_RESULT_FAIL_CANNT_TAKEBACK); //原有的牌不能取回
				} 
			}else{
				cd = getCardFromCurPlayer(id);
				if (cd == null){
					return new MoveCardToPlayerResponse(MoveCardToPlayerResponse.MOVE_CARD_TO_PLAYER_RESULT_FAIL_CARD_NOEXIST); //无效的牌
				}
			}
		}
		
		for (int id:card_ids){
			CardData cd = removeCardFromMatrix(id);
			if (cd!=null){
				cards_m.add(cd);
			}
		}
		
		int idsn[] = new int[cards_m.size()];
		int i = 0;
		for (CardData cd:cards_m){
			cd.x = -1;
			cd.y = -1;
			getCurPlayer().card_list.put(cd.id, cd);
			idsn[i] = cd.id;
		}
		broadMatrix();
		return new MoveCardToPlayerResponse(
				MoveCardToPlayerResponse.MOVE_CARD_TO_PLAYER_RESULT_SUCCESS,
				idsn);
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
	
	/** 获得桌面上所有不成立的牌 id */
	public int[] getFailedCard(){
		ArrayList<CardData> clist = new ArrayList<CardData>();
		for (int i = 0; i<mh; i++){
			for (int j = 0; j<mw-2; j++){
				CheckResult cr = checkSingle(j, i);
				if (cr.is_success == false && cr.n!=0){
					for (int z = 0; z<cr.n; z++){
						clist.add(matrix[i][j+z]);
					}
				}
				j += cr.n;
			}
		}
		int[] cids = new int[clist.size()];
		int i = 0;
		for (CardData d:clist){
			cids[i++] = d.id;
		}
		return cids;
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
				return false;
//				for (int i = c1_index+1; i<cds.length; i++){
//					if (cds[i].point!=0 && cds[i].point != c1.point-(i-c1_index)){
//						return false;
//					}
//				}
			}
			return true;
		}
	}
	
	/** 得到打出的牌的点数 */
	public int getCardPoint(){
		int point = 0;
		ArrayList<CardData> player_put_card = new ArrayList<CardData>();
//		if (check()){
			for (CardData c : player_put.values()){
				player_put_card.add(c);
			}
			for (CardData c : player_put.values()){
				if (player_put_card.contains(c)){
					CardData[] g = getCardGroup(c.x, c.y);
					boolean is_old = false;
					int tpoint = 0;
					for (CardData cd:g){
						tpoint+=cd.point;
						if (!player_put_card.remove(cd)){
							is_old = true;
						}
					}
					if (is_old == false){
						point += tpoint;
					}
				}
			}
//		}
		return point;
	}
	
	private CardData[] getCardGroup(int x, int y){
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
		
		CardData cds[] = new CardData[length];
		for (int i = 0; i<length; i++){
			cds[i] = matrix[y][xs+i];
		}
		return cds;
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
		desk.broadcast(new MainMatrixChangeNotify(true, getCurPlayer().player_id, m));
		player_put.clear();
	}
	
	/** 提交 */
	public int submit(){
		if (game_state != GAME_STATE_GAMING) return SubmitResponse.SUBMIT_RESULT_FAIL_GAME_NOT_START;
		
		if (player_put.size()==0){
			System.err.println("submit 没有出牌");
			return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NO_SEND;
		}
		if (check() == false){ // 牌组不成立
			System.err.println("submit 牌组不成立");
			return SubmitResponse.SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH;
		}
		if (!getCurPlayer().isOpenIce){
			if (getCardPoint()>=30){
				getCurPlayer().isOpenIce = true;
				desk.broadcast(new OpenIceNotify(getCurPlayer().player_id));
				System.out.println("player:"+getCurPlayer()+"余牌:"+ getCurPlayer().card_list.size());
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
		System.out.println("player:"+getCurPlayer()+"余牌:"+ getCurPlayer().card_list.size());
		if (getCurPlayer().card_list.size() == 0){	//如果玩家牌已出完
			onGameOver();
		}else{
			toNextPlayer();
		}
		return SubmitResponse.SUBMIT_RESULT_SUCCESS;
	}
	
	public int MainMatrixChange(CardData[] cds){
		if (game_state != GAME_STATE_GAMING) return MainMatrixChangeResponse.MAIN_MAIRIX_CHANGE_RESULT_FAIL_GAME_NOT_START;
		if (matrix_old == null){
			matrix_old = matrix;
			matrix = new CardData[mh][mw];
			for (int i = 0; i<mh; i++){
				for (int j = 0; j<mw; j++){
					matrix[i][j] = matrix_old[i][j];
				}
			}
		}		
		
		/** 桌面上新加的牌 */
		ArrayList<CardData> c1 = new  ArrayList<CardData>();
		/** 桌面上减少的牌 */
		ArrayList<CardData> c2 = new  ArrayList<CardData>();
		/** 桌面上移动的牌 */
		ArrayList<CardData> c4 = new  ArrayList<CardData>(); 
		HashMap<Integer, CardData> new_matrix = new HashMap<Integer, CardData>();
		for (CardData cd:cds){
			CardData old = getCardFromMatrix(cd.id);
			if (old == null){
				c1.add(cd);
			}else if(old.x!=cd.x || old.y!=cd.y){
				c4.add(old);
			}
			new_matrix.put(cd.id, cd);
		}
		
		if (new_matrix.size()!=cds.length){ //有被复制的牌
			System.err.println("有被复制的牌");
			return MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL_CARD_COPYED;
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
		
		/** 桌面上有新加的牌，去当前玩家的手里找 */
		ArrayList<CardData> c3 = new  ArrayList<CardData>();
		if (!c1.isEmpty()){
			for (CardData cd:c1){
				CardData cp = getCurPlayer().card_list.get(cd.id);
				if (cp == null){	// 该牌是凭空捏造出来的
					System.err.println("该牌是凭空捏造出来的");
					return MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL_CARD_NOEXIST;
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
				return MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL_CANT_RETAKE;
			}
		}
		
		if (!getCurPlayer().isOpenIce){///如果玩家未破冰，则不能操作已有的牌，不能和原来的牌拼接
			for(CardData cd:c4){
				if (player_put.get(cd.id)==null){
					return MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL_CANNT_MOVE;
				}else{
					if (cd.x>=1 && matrix_old[cd.y][cd.x-1]!= null){
						return MainMatrixChangeResponse.MAIN_MAIRIX_CHANGE_RESULT_FAIL_CANNT_SPLICE;
					}
					if (cd.x<mw-1 && matrix_old[cd.y][cd.x+1]!= null){
						return MainMatrixChangeResponse.MAIN_MAIRIX_CHANGE_RESULT_FAIL_CANNT_SPLICE;
					}
				}
			}
			for (CardData cd:c3){
				if (cd.x>=1 && matrix_old[cd.y][cd.x-1]!= null){
					return MainMatrixChangeResponse.MAIN_MAIRIX_CHANGE_RESULT_FAIL_CANNT_SPLICE;
				}
				if (cd.x<mw-1 && matrix_old[cd.y][cd.x+1]!= null){
					return MainMatrixChangeResponse.MAIN_MAIRIX_CHANGE_RESULT_FAIL_CANNT_SPLICE;
				}
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
		desk.broadcast(new MainMatrixChangeNotify(false, getCurPlayer().player_id, notify_cds));
		int cur_complete_card_count = getCompleteCardCount();
		if (cur_complete_card_count>complete_card_count){
			
			desk.broadcast(new OperateCompleteNotify(getCurPlayer().player_id));
			resetOperateTimer();
		}
		complete_card_count = cur_complete_card_count;
		return MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_SUCCESS;
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
		ResultPak rpl = player.onPlayerEscape();
		this.escape_point += -rpl.point;
		if (getCurPlayer() == player){
			repeal();
		}
//		Player next_player = getNextPlayer();
		player_list = desk.getPlayerList();
		
		ArrayList<ResultPak> rplist = new ArrayList<ResultPak>();
		rplist.add(rpl);
		
		if (player_list.length>0){
//			ResultPak[] rp = new ResultPak[player_list.length];
			for (int i = 0; i<player_list.length; i++){
				rplist.add(player_list[i].onPlayerWin(escape_point/player_list.length));
			}
			ResultPak[] rp = new ResultPak[rplist.size()];
			rplist.toArray(rp);
			
			GameOverNotify notify = new GameOverNotify(GameOverNotify.GAME_OVER_TYPE_ESCAPE, rp);
			desk.broadcast(notify);
			desk.game = null;
			cancelOperateTimer();
			cancelTrunTimer();
			desk.getLogger().info("desk [" + desk.desk_id + "] game over");
			desk.getRoom().broadcast(new GameOverToRoomNotify(desk.desk_id));
		}else{
			GameOverNotify notify = new GameOverNotify(GameOverNotify.GAME_OVER_TYPE_ESCAPE, null);
			desk.broadcast(notify);
			desk.game = null;
			cancelOperateTimer();
			cancelTrunTimer();
			desk.getLogger().info("desk [" + desk.desk_id + "] game over");
			desk.getRoom().broadcast(new GameOverToRoomNotify(desk.desk_id));
		}
		
	}
	
	private void resetOperateTimer(){
		ScheduledFuture<?> f = future.get();
		if (f!=null){
			f.cancel(false);
		}
		future.set(thread_pool.schedule(new Turn(), desk.getRoom().operate_time));
	}
	
	private void cancelOperateTimer(){
		ScheduledFuture<?> f = future.get();
		if (f!=null){
			f.cancel(false);
		}
	}
	
	private void resetTurnTimer(){
		ScheduledFuture<?> f = future2.get();
		if (f!=null){
			f.cancel(false);
		}
		future2.set(thread_pool.schedule(new Turn(), desk.getRoom().turn_interval));
	}
	
	private void cancelTrunTimer(){
		ScheduledFuture<?> f = future2.get();
		if (f!=null){
			f.cancel(false);
		}
	}
	
	private AtomicReference<ScheduledFuture<?>> future = new AtomicReference<ScheduledFuture<?>>();
	private AtomicReference<ScheduledFuture<?>>	future2 = new AtomicReference<ScheduledFuture<?>>();
	private ThreadPool			thread_pool;
	
	class Turn implements Runnable
	{

		@Override
		public void run() {
			if (game_state == GAME_STATE_GET_CARD){
				gameConfilm();
			}
			else if (game_state == GAME_STATE_CONFILM){
				gameStart();
			}else if (game_state == GAME_STATE_GAMING){
				System.err.println("player " + getCurPlayer() + " 超时");
				desk.broadcast(new TimeOutNotify(getCurPlayer().player_id));
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

