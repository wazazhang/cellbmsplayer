package com.fc.lami;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.cell.j2se.CAppBridge;
import com.net.flash.message.FlashMessage;
import com.net.flash.message.FlashMessageCodeGenerator;
import com.net.mutual.MutualMessageCodeGeneratorJava;



public class Messages {

	public static class GetTimeRequest extends FlashMessage
	{
		public String message;
		
		public GetTimeRequest(String message) {
			this.message = message;
		}
		public GetTimeRequest() {
		}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	
	public static class RoomData extends FlashMessage
	{
		public int room_id;
		
		public String room_name;
		public DeskData desks[];
		public PlayerData players[];

		public RoomData() {}
		
		@Override
		public String toString() {
			return ""+room_id;
		}
	}
	
	public static class DeskData extends FlashMessage
	{
		public int desk_id;
		public String desk_name;
		public int player_number;
		public boolean is_started;
		public int player_E_id;
		public int player_W_id;
		public int player_S_id;
		public int player_N_id;
		
		public DeskData(){}
		
	}
	
	public static class PlayerData extends FlashMessage
	{
		public int player_id;
		public String uid;
		public String player_name;
		public String player_head_url;
		public String sex;
		
		public boolean is_first_game;
		
		public int score;
		public int win;
		public int lose;
		public int level;
		
		public PlayerData()
		{
			//this.session = session;
		}
		
	}
	
	public static class PlayerState extends FlashMessage
	{
		public int player_id;
		public boolean is_ready;
		public boolean is_openice;
	}
	
	public static class PlatformUserData extends FlashMessage
	{
		public String platform_uid;
		
		public String user_uid;
		public String user_name;
		public String user_sex;
		public String user_image_url;
		
		public String getPlatformAddress() {
			return user_uid + "_" + platform_uid;
		}
	}
	
	
	public static class CardData extends FlashMessage
	{
		/** id */
		public int id;
		/** 点数 */
		public int point;
		/** 颜色 */
		public int type;
		public int x;
		public int y;
		public boolean isSended; //是否已经打出
		public CardData(){
		}
		
		public CardData(int point, int type){
			this.point = point;
			this.type = type;
		}
		
		@Override
		public String toString() {
			return "t:"+type+",p:"+point;
		}
	}
	
	public static class RoomSnapShot extends FlashMessage
	{
		public int room_id;
		public String room_name;
		public int player_number_max;
		public int player_number;
		
		public RoomSnapShot(int room_id, String room_name, int player_number, int pm){
			this.room_id = room_id;
			this.room_name = room_name;
			this.player_number = player_number;
			this.player_number_max = pm;
		}
		
		public RoomSnapShot(){}
	}
	/** 比赛结果包 */
	public static class ResultPak extends FlashMessage
	{
		public int point;
		public int player_id;
		public boolean is_win;
		
		public ResultPak(){}
	}
	
	public static class GetTimeResponse extends FlashMessage
	{
		public String time;

		public GetTimeResponse(String time) {
			this.time = time;
		}
		public GetTimeResponse() {}
		@Override
		public String toString() {
			return time+"";
		}
	}
	
//	/** 把牌放到桌面上，可以一次性放多个  */
//	public static class SendCardRequest extends FlashMessage
//	{
//		public int cards[];
//		
//		/** 第一张牌的坐标 */
//		public int x;
//		public int y;
//		
//		public SendCardRequest(int cards[], int x, int y){
//			this.cards = cards;
//			this.x = x;
//			this.y = y;
//		}
//		
//		public SendCardRequest() {}
//		@Override
//		public String toString() {
//			return "SendCardRequest";
//		}
//	}
	
//	/** 放牌结果返回 */
//	public static class SendCardResponse extends FlashMessage
//	{
//		/** 放牌成功 */
//		final static public int SEND_CARD_RESULT_SUCCESS = 0;
//		/** 放牌失败  该位置已经有牌 */
//		final static public int SEND_CARD_RESULT_FAIL_NOT_BLANK = 1;
//		/** 放牌失败  用一牌把两边连起来牌组不成立时 */
//		final static public int SEND_CARD_RESULT_FAIL_NO_MATCH = 2;
//		/** 位置不对 */
//		final static public int SEND_CARD_RESULT_FAIL_LOCATION_WRONG = 3;
//		/** 没有破冰时不能和原有牌拼接 */
//		final static public int SEND_CARD_RESULT_FAIL_SPLIT = 4;
//		
//		public int result;
//		
//		public SendCardResponse(int result){
//			this.result = result;
//		}
//		
//		public SendCardResponse() {}
//		@Override
//		public String toString() {
//			return "SendCardResponse";
//		}
//	}
	
//	/** 别的玩家放牌通知 */
//	public static class SendCardNotify extends FlashMessage
//	{
//		public int player_id;
//		public CardData cards[];
//		
//		public SendCardNotify(int player_id, CardData cards[]){
//			this.cards = cards;
//			this.player_id = player_id;
//		}
//		
//		public SendCardNotify() {}
//		@Override
//		public String toString() {
//			return "SendCardNotify";
//		}
//	}
	
	/** 破冰通知 */
	public static class OpenIceNotify extends FlashMessage
	{
		public int player_id;
		public OpenIceNotify(int player_id){
			this.player_id = player_id;
		}
		
		public OpenIceNotify(){}
		@Override
		public String toString() {
			return "BreakNotify";
		}
	}
	
//	/** 取回自己本回合放出的牌 */
//	public static class RetakeCardRequest extends FlashMessage
//	{
//		public int cards[];
//		
//		public RetakeCardRequest(int[] card_ids){
//			this.cards = card_ids;
//		}
//		
//		public RetakeCardRequest() {}
//		@Override
//		public String toString() {
//			return "RetakeCardRequest";
//		}
//	}
	
//	/** 取牌结果返回 */
//	public static class RetakeCardResponse extends FlashMessage
//	{
//		/** 取牌成功 */
//		final static public int RETAKE_CARD_RESULT_SUCCESS = 0;
//		/** 取牌失败  该牌不是本回合放上去的 */
//		final static public int RETAKE_CARD_RESULT_FAIL_NOT_THIS_TURN = 1;
//		/** 桌面上没有这张牌 */
//		final static public int RETAKE_CARD_RESULT_FAIL_NOEXIST = 2;
//		
//		public int result;
//		
//		public RetakeCardResponse(int result){
//			this.result = result;
//		}
//		
//		public RetakeCardResponse() {}
//		@Override
//		public String toString() {
//			return "RetakeCardResponse";
//		}
//	}
	
//	/** 别的玩家取牌通知 */
//	public static class RetakeCardNotify extends FlashMessage
//	{
//		public int player_id;
//		public int cards[];
//		
//		public RetakeCardNotify(int player_id, int cards[]){
//			this.player_id = player_id;
//			this.cards = cards;
//		}
//		
//		public RetakeCardNotify() {}
//		@Override
//		public String toString() {
//			return "RetakeCardNotify";
//		}
//	}
	
//	/** 移动桌面上的牌，可以多个 */
//	public static class MoveCardRequest extends FlashMessage
//	{
//		public int cards[];
//		/** 新的坐标 */
//		public int nx, ny;
//		
//		public MoveCardRequest(int card_ids[], int nx, int ny){
//			this.cards = card_ids;
//			this.nx = nx;
//			this.ny = ny;
//		}
//		
//		public MoveCardRequest() {}
//		@Override
//		public String toString() {
//			return "MoveCardRequest";
//		}
//	}
	
//	/** 移牌结果返回 */ 
//	public static class MoveCardResponse extends FlashMessage
//	{
//		/** 移牌成功 */
//		final static public int MOVE_CARD_RESULT_SUCCESS = 0;
//		/** 未破冰前不能移动原有的牌 */
//		final static public int MOVE_CARD_RESULT_FAIL_CANNT_MOVE = 1;
//		/** 未破冰前移动的牌不能和桌上原有的牌拼接 */
//		final static public int MOVE_CARD_RESULT_FAIL_SPLIT = 2;
//		/** 桌面上没有这张牌 */
//		final static public int MOVE_CARD_RESULT_FAIL_CARD_NOEXIST = 3;
//		/** 位置不对 */
//		final static public int MOVE_CARD_RESULT_FAIL_LOCATION_WRONG = 4;
//		
//		public int result;
//		public MoveCardResponse(int result){
//			this.result = result;
//		}
//		public MoveCardResponse() {}
//		@Override
//		public String toString() {
//			return "MoveCardResponse";
//		}
//	}
	
//	/** 别的玩家移牌通知 */
//	public static class MoveCardNotify extends FlashMessage
//	{
//		public int cards[];
//		/** 新的坐标 */
//		public int nx, ny;
//		public int player_id;
//		
//		public MoveCardNotify(int player_id, int card_ids[], int nx, int ny){
//			this.player_id = player_id;
//			this.cards = card_ids;
//			this.nx = nx;
//			this.ny = ny;
//		}
//		
//		public MoveCardNotify() {}
//		@Override
//		public String toString() {
//			return "MoveCardNotify";
//		}
//	}
	
	/** 把牌放到桌面 */
	public static class MoveCardToDeskRequest extends FlashMessage
	{
		public int[] ids;
		public int x;
		public int y;
		
		public MoveCardToDeskRequest(int[] ids, int x, int y){
			this.ids = ids;
			this.x = x;
			this.y = y;
		}
		
		public MoveCardToDeskRequest(){}
	}
	
	public static class MoveCardToDeskResponse extends FlashMessage
	{
		final static public int MOVE_CARD_TO_DESK_RESULT_SUCCESS = 0;
		/** 位置错误 */
		final static public int MOVE_CARD_TO_DESK_RESULT_FAIL_LOCATION_WRONG = 1;
		/** 牌不存在 */
		final static public int MOVE_CARD_TO_DESK_RESULT_FAIL_CARD_NOEXIST = 2;
		/** 未破冰不能移动原有的牌 */
		final static public int MOVE_CARD_TO_DESK_RESULT_FAIL_CANNT_MOVE = 3;
		/** 未破冰不能和原有的牌拼接 */
		final static public int MOVE_CARD_TO_DESK_RESULT_FAIL_CANNT_SPLICE = 4;
		/** 目标位置有牌 */
		final static public int MOVE_CARD_TO_DESK_RESULT_FAIL_AREALDY_HAVE_CARD = 5;;
		
		public int result;
		public int[] ids;
		public int x;
		public int y;
		
		public MoveCardToDeskResponse(int result, int ids[], int x, int y){
			this.result = result;
			this.ids = ids;
			this.x = x;
			this.y = y;
		}
		
		public MoveCardToDeskResponse(int result){
			this.result = result;
		}
		public MoveCardToDeskResponse() {}
	}
	
	/** 把牌放到玩家手里 */
	public static class MoveCardToPlayerRequest extends FlashMessage
	{
		public int[] ids;
		public int x;
		public int y;
		
		public MoveCardToPlayerRequest(int ids[], int x, int y){
			this.ids = ids;
		}
		
		public MoveCardToPlayerRequest(){}
	}
	
	public static class MoveCardToPlayerResponse extends FlashMessage
	{
		final static public int MOVE_CARD_TO_PLAYER_RESULT_SUCCESS = 0;
		final static public int MOVE_CARD_TO_PLAYER_RESULT_FAIL_CANNT_TAKEBACK = 1;
		final static public int MOVE_CARD_TO_PLAYER_RESULT_FAIL_CARD_NOEXIST = 2;
		
		public int result;
		public int[] ids;
		
		public MoveCardToPlayerResponse(int result, int ids[]){
			this.result = result;
			this.ids = ids;
		}
		
		public MoveCardToPlayerResponse(int result){
			this.result = result;
		}
		
		public MoveCardToPlayerResponse(){}
	}
	
	/** 准备就绪请求 */
	public static class ReadyRequest extends FlashMessage
	{
		public boolean isReady;
		public ReadyRequest(boolean ready) {
			isReady = ready;
		}
		
		public ReadyRequest(){}
		@Override
		public String toString() {
			return "ReadyRequest";
		}
	}
	
	/** 准备就绪请求返回 */
	public static class ReadyResponse extends FlashMessage
	{
		public ReadyResponse() {}
		@Override
		public String toString() {
			return "ReadyResponse";
		}
	}
	
	/** 别的玩家准备就绪通知 */
	public static class ReadyNotify extends FlashMessage
	{
		public int player_id;
		public boolean isReady;
		
		public ReadyNotify(int player_id, boolean is_ready){
			this.player_id = player_id;
			this.isReady = is_ready;
		}
		public ReadyNotify() {}
		@Override
		public String toString() {
			return "ReadyResponse";
		}
	}
	
	public static class GameStartToRoomNotify extends FlashMessage
	{
		public int desk_id;
		
		public GameStartToRoomNotify(int did){
			this.desk_id = did;
		}
		
		public GameStartToRoomNotify() {}
		@Override
		public String toString() {
			return "GameStartToRoomNotify";
		}
	}
	
	public static class GameOverToRoomNotify extends FlashMessage
	{
		public int desk_id;
		
		public GameOverToRoomNotify(int did){
			this.desk_id = did;
		}
		
		public GameOverToRoomNotify() {}
		@Override
		public String toString() {
			return "GameOverToRoomNotify";
		}
	}
	
	public static class GameStartNotify extends FlashMessage
	{
		public CardData cards[];
		
		public GameStartNotify(CardData cards[]){
			this.cards = cards;
		}
		
		public GameStartNotify() {}
		@Override
		public String toString() {
			return "GameStartNotify";
		}
	}
	
	public static class GameResetRequest extends FlashMessage
	{
		public boolean is_reset;
		
		public GameResetRequest(boolean reset){
			this.is_reset = reset;
		}
		public GameResetRequest(){}
	}
	
	public static class GameResetResponse extends FlashMessage
	{
		final static public int GAME_RESET_RESULT_SUCCESS = 0;
		final static public int GAME_RESET_RESULT_FAIL_TIMEOUT = 1;
		final static public int GAME_RESET_RESULT_FAIL_CANT_RESET = 2;
		
		public int result;
		
		public GameResetResponse(int result){
			this.result = result;
		}
		
		public GameResetResponse(){}
	}
	
	public static class GameResetNotify extends FlashMessage
	{
		public int player_id;
		
		public GameResetNotify(int pid){
			this.player_id = pid;
		}
		
		public GameResetNotify(){}
	}
	
	/** 游戏结束通知 */
	public static class GameOverNotify extends FlashMessage
	{
		/** 游戏结束方式     有人出完牌 */
		final static public int GAME_OVER_TYPE_CLEAR = 0;
		/** 游戏结束方式     牌堆的牌摸空了 */
		final static public int GAME_OVER_TYPE_CARD_OVER = 1;
		/** 游戏结束方式    有人逃跑了（断线也算） */
		final static public int GAME_OVER_TYPE_ESCAPE = 2;
		/** 游戏结束方式 */
		public int game_over_type;
		
		public ResultPak[] result_pak;
		
		public GameOverNotify(int type, ResultPak[] pak){
			this.game_over_type = type;
			this.result_pak = pak;
		}
		
		public GameOverNotify(){}
		@Override
		public String toString() {
			return "GameOverNotify";
		}
	}
	
	/** 放牌完毕 */
	public static class SubmitRequest extends FlashMessage
	{
		public SubmitRequest() {}
		@Override
		public String toString() {
			return "SubmitRequest";
		}
	}
	
	/** 放牌完毕返回 */
	public static class SubmitResponse extends FlashMessage
	{
		/** 放牌结束成功 */
		final static public int SUBMIT_RESULT_SUCCESS = 0;
		/** 放牌结束失败，有不成立的牌组 */
		final static public int SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH = 1;
		/** 放牌结束失败，没有破冰 */
		final static public int SUBMIT_RESULT_FAIL_CARD_NOT_OPEN_ICE = 2;
		/** 放牌结束失败, 没有出牌 */
		final static public int SUBMIT_RESULT_FAIL_CARD_NO_SEND = 3;
		/** 放牌结束失败，游戏未开始 */
		final static public int SUBMIT_RESULT_FAIL_GAME_NOT_START = 4;
		
		public int result;
		
		public int[] fail_cards;
		
		public SubmitResponse(int result){
			this.result = result;
		}
		
		public SubmitResponse() {}
		@Override
		public String toString() {
			return "OverResponse";
		}
	}
	
	/** 摸牌 */
	public static class GetCardRequest extends FlashMessage
	{
		public GetCardRequest() {}
		@Override
		public String toString() {
			return "GetCardRequest";
		}
	}
	
	/** 摸牌返回 */
	public static class GetCardResponse extends FlashMessage
	{
		final static public int GET_CARD_RESULT_SUCCESS = 0;
		/** 有出牌操作 */
		final static public int GET_CARD_RESULT_FAIL_SEND_CARD = 1;
		/** 没有轮到行动 */
		final static public int GET_CARD_RESULT_FAIL_NOT_TURN = 2;
		
		public int result;
		
		public GetCardResponse(int result){
			this.result = result;
		}
		
		public GetCardResponse() {}
		@Override
		public String toString() {
			return "GetCardResponse";
		}
	}
	
	/** 获得牌通知 */
	public static class GetCardNotify extends FlashMessage
	{
		public CardData cards[];
		
		public GetCardNotify(CardData cards[]){
			this.cards = cards;
		}
		
		public GetCardNotify() {}
		@Override
		public String toString() {
			return "GetCardNotify";
		}
	}
	
	/** 牌堆数量改变通知 */
	public static class CardStackChangeNotify extends FlashMessage
	{
		public int card_stack_number;
		
		public CardStackChangeNotify(int n){
			this.card_stack_number = n;
		}
		
		public CardStackChangeNotify() {}
		@Override
		public String toString() {
			return "CardStackChangeNotify";
		}
	}
	
	/** 撤销出牌 */
	public static class RepealSendCardRequest extends FlashMessage
	{
		public RepealSendCardRequest(){}
		
		@Override
		public String toString() {
			return "RepealSendCardRequest";
		}
	}
	
	/** 撤销出牌返回 */
	public static class RepealSendCardResponse extends FlashMessage
	{
		
		public RepealSendCardResponse(){}
		
		@Override
		public String toString() {
			return "RepealSendCardResponse";
		}
	}
	
	/** 撤销出牌通知 */
	public static class RepealSendCardNotify extends FlashMessage
	{
		public int player_id;
		
		public CardData cds[];
		
		public RepealSendCardNotify(int player_id, CardData cds[]){
			this.player_id = player_id;
			this.cds = cds;
		}
		
		public RepealSendCardNotify(){}
		
		@Override
		public String toString() {
			return "RepealSendCardNotify";
		}
	}
	
	/** 登录 */
	public static class LoginRequest extends FlashMessage
	{
		public PlatformUserData platform_user_data;
		public String validate;
		public String version;
		public LoginRequest(){}
		
		@Override
		public String toString() {
			return "LoginRequest";
		}
	}
	
	public static class LoginResponse extends FlashMessage
	{
		final static public short LOGIN_RESULT_SUCCESS 				= 1;
		final static public short LOGIN_RESULT_FAIL					= -1;
		final static public short LOGIN_RESULT_FAIL_ALREADY_LOGIN 	= -2;
		final static public short LOGIN_RESULT_FAIL_BAD_VERSION 	= -3;
		
		public short result;
		public PlayerData player;
		public RoomSnapShot rooms[];
		public String version;
		public String reason;
//		public long server_time;
		
		public LoginResponse(short result, PlayerData player, String version, String reason){
			this.result = result;
			this.player = player;
			this.version = version;
			this.reason = reason;
		}

		public LoginResponse(){}
		
		@Override
		public String toString(){
			return "LoginResponse";
		}
	}
	
	public static class LogoutRequest extends FlashMessage
	{
		public LogoutRequest(){
			
		}
		
		@Override
		public String toString(){
			return "LogoutRequest";
		}
	}
	
	
	/** 进房间 */
	public static class EnterRoomRequest extends FlashMessage
	{
		/** 房间号 */
		public int room_no;
		
		public EnterRoomRequest(int no){
			this.room_no = no;
		}
		
		public EnterRoomRequest(){}
		
		@Override
		public String toString() {
			return "EnterRoomRequest";
		}
	}
	
	public static class EnterRoomResponse extends FlashMessage
	{
		final static public int ENTER_ROOM_RESULT_SUCCESS = 0;
		final static public int ENTER_ROOM_RESULT_FAIL_ROOM_FULL = 1;
		final static public int ENTER_ROOM_RESULT_FAIL_ROOM_NOT_EXIST = 2;
		
		public int result;
		public RoomData room;
		
		public EnterRoomResponse(int result){
			this.result = result;
		}
		
		public EnterRoomResponse(){}
		
		@Override
		public String toString() {
			return "EnterRoomResponse";
		}
	}
	
	/** 有人进入房间 */
	public static class EnterRoomNotify extends FlashMessage
	{
		public PlayerData player;
		
		public EnterRoomNotify(PlayerData player){
			this.player = player;
		}
		
		public EnterRoomNotify(){}
		
		@Override
		public String toString() {
			return "EnterRoomNotify";
		}
	}
	
	/** 出房间 */
	public static class ExitRoomRequest extends FlashMessage
	{
		public ExitRoomRequest(){}
		
		@Override
		public String toString() {
			return "ExitRoomRequest";
		}
	}
	
	public static class ExitRoomResponse extends FlashMessage
	{
		public RoomSnapShot rooms[];
		
		public ExitRoomResponse(RoomSnapShot rooms[]){
			this.rooms = rooms;
		}
		
		public ExitRoomResponse(){}
		
		@Override
		public String toString() {
			return "ExitRoomResponse";
		}
	}
	
	public static class ExitRoomNotify extends FlashMessage
	{
		public int player_id;
		
		public ExitRoomNotify(int pid){
			this.player_id = pid;
		}
		
		public ExitRoomNotify(){}
		
		@Override
		public String toString() {
			return "ExitRoomNotify";
		}
	}
	
	public static class FreshRoomNotify extends FlashMessage
	{
		public RoomSnapShot room;
		
		public FreshRoomNotify(RoomSnapShot room){
			this.room = room;
		}
		
		public FreshRoomNotify(){}
	}
	
	/** 进桌子 */
	public static class EnterDeskRequest extends FlashMessage
	{
		final static public int SEAT_EAST = 0;
		final static public int SEAT_WEST = 1;
		final static public int SEAT_SOUTH = 2;
		final static public int SEAT_NORTH = 3;
		
		public int desk_No;
		public int seat;
		
		public EnterDeskRequest(int desk_No, int seat){
			this.desk_No = desk_No;
			this.seat = seat;
		}
		
		public EnterDeskRequest(){}
		
		@Override
		public String toString() {
			return "EnterDeskRequest";
		}
	}
	
	public static class EnterDeskResponse extends FlashMessage
	{
		final static public int ENTER_DESK_RESULT_SUCCESS = 0;
		final static public int ENTER_DESK_RESULT_FAIL_PLAYER_EXIST = 1;	// 该座位有人
		final static public int ENTER_DESK_RESULT_FAIL_NOT_HAVE_ROOM = 2;	//玩家没有进入房间
		final static public int ENTER_DESK_RESULT_FAIL_NO_IDLE_SEAT = 3;	// 没有空闲的座位
		final static public int ENTER_DESK_RESULT_FAIL_NO_IDLE_DESK = 4;	// 没有空闲的桌子
		final static public int ENTER_DESK_RESULT_FAIL_GAME_STARTED = 5;	// 已经开始游戏
		public int result;
		public int desk_id;
		public int seat;
		public int turn_interval;
		public int operate_time;
		
		public PlayerState[] ps;
		
		public EnterDeskResponse(int result,int desk_id, int seat, int t, int ot, PlayerState[] ps){
			this.result = result;
			this.desk_id = desk_id;
			this.seat = seat;
			this.turn_interval = t;
			this.operate_time = ot;
			this.ps = ps;
		}
		
		public EnterDeskResponse(int result){
			this.result = result;
		}
		public EnterDeskResponse(){}
		
		@Override
		public String toString() {
			return "EnterDeskResponse";
		}
	}
	
	public static class EnterDeskNotify extends FlashMessage
	{
		public int player_id;
		public int desk_id;
		public int seatID;
		public EnterDeskNotify(int pid, int did,int seatid){
			this.player_id = pid;
			this.desk_id = did;
			this.seatID = seatid;
		}
		public EnterDeskNotify(){}
		@Override
		public String toString() {
			return "EnterDeskNotify";
		}
	}
	
	public static class AutoEnterRequest extends FlashMessage
	{
		public AutoEnterRequest(){}
	}
	
	public static class AutoEnterResponse extends FlashMessage
	{
		final static public int AUTO_ENETR_RESULT_SUCCESS = 0;
		final static public int AUTO_ENTER_RESULT_FAIL_NO_IDLE_SEAT = 1; 
		
		public int result;
		public RoomData room;
		
		public AutoEnterResponse(int result){
			this.result = result;
		}
		
		public AutoEnterResponse(){}
	}
	
	/** 玩家获得手牌完毕 */
	public static class GetCardOverRequest extends FlashMessage
	{
		public GetCardOverRequest(){}
	}
	
	/** 返回是否能重置牌 */
	public static class GetCardOverResponse extends FlashMessage
	{
		public boolean is_can_reset;
		
		public GetCardOverResponse(boolean is){
			this.is_can_reset = is;
		}
		public GetCardOverResponse(){}
	}
	
	/** 离开桌子 */
	public static class LeaveDeskRequest extends FlashMessage
	{

		public LeaveDeskRequest(){}
		
		@Override
		public String toString() {
			return "LeaveDeskRequest";
		}
	}
	
	public static class LeaveDeskResponse extends FlashMessage
	{
		final static public int LEAVE_DESK_RESULT_SUCCESS = 0;
		final static public int LEAVE_DESK_RESULT_FAIL_GAMING = 1;
		
		public int result;
		
		public LeaveDeskResponse(int result){
			this.result = result;
		}
		
		public LeaveDeskResponse(){}
		
		@Override
		public String toString() {
			return "LeaveDeskResponse";
		}
	}
	
	public static class LeaveDeskNotify extends FlashMessage
	{
		public int player_id;
		public int desk_id;
		
		public LeaveDeskNotify(int pid, int did){
			this.player_id = pid;
			this.desk_id = did;
		}
		
		public LeaveDeskNotify(){}
		
		@Override
		public String toString() {
			return "LeaveDeskNotify";
		}
	}
	
	public static class LeaveDeskForceRequest extends FlashMessage
	{
		public LeaveDeskForceRequest(){}
	}
	
	public static class LeaveDeskForceResponse extends FlashMessage
	{
		final static public int LEAVE_DESK_RESULT_SUCCESS = 0;
		public int result;
		
		public LeaveDeskForceResponse(int result){
			this.result = result;
		}
		
		public LeaveDeskForceResponse(){}
	}
	
	/** 玩家被踢出桌子通知 */
	public static class KickOutNotify extends FlashMessage
	{
		/** 超时被系统踢出 */
		final public static int KICK_OUT_REASON_TIME_OUT = 1;
		/** 被其他玩家踢出 */
		final public static int KICK_OUT_REASON_OTHER_PLAYER = 2;
		
		public int reason;
		public KickOutNotify(int r){
			this.reason = r;
		}
		
		public KickOutNotify(){}
	}
	
	/** 玩家回合开始通知 */
	public static class TurnStartNotify extends FlashMessage
	{
		public int player_id;
		public int stack_num;
//		public long end_time;
		
		public TurnStartNotify(int pid, int num){
			this.player_id = pid;
			this.stack_num = num;
//			this.end_time = et;
		}
		
		public TurnStartNotify(){}
		@Override
		public String toString() {
			return "TurnStartNotify";
		}
	}
	
	/** 玩家回合结束通知 */
	public static class TurnEndNotify extends FlashMessage
	{
		public TurnEndNotify(){}
		@Override
		public String toString() {
			return "TurnEndNotify";
		}
	}
	
	/** 一次操作成功 完成一个牌组 */
	public static class OperateCompleteNotify extends FlashMessage
	{
		public int player_id;
		
		public OperateCompleteNotify(int pid){
			this.player_id = pid;
		}
		public OperateCompleteNotify(){}
		@Override
		public String toString() {
			return "OperateCompleteNotify";
		}
	}
	

	
		//主信息框变化广播
	public static class MainMatrixChangeNotify extends FlashMessage
	{
		public boolean is_hardhanded; //是否强制
		public CardData[] cards;
		
		public int player_id;
		
		public MainMatrixChangeNotify(boolean is_hardhanded, int pid, CardData[] cards){
			this.is_hardhanded = is_hardhanded;
			this.player_id = pid;
			this.cards = cards;
		}
		public MainMatrixChangeNotify()
		{	
		}
		@Override
		public String toString() {
			return "MainMatrixNotify";
		}
	}
	//主信息框变化请求
	public static class MainMatrixChangeRequest extends FlashMessage
	{
		public CardData[] cards;
		public MainMatrixChangeRequest(CardData[] cards){
			this.cards = cards;
		}
		public MainMatrixChangeRequest(){}
		@Override
		public String toString() {
			return "MainMatrixChangeRequest";
		}
	}
	
	public static class MainMatrixChangeResponse extends FlashMessage
	{
		final static public int MAIN_MATRIX_CHANGE_RESULT_SUCCESS = 0;
		final static public int MAIN_MATRIX_CHANGE_RESULT_FAIL_CARD_COPYED = 1; 
		final static public int MAIN_MATRIX_CHANGE_RESULT_FAIL_CARD_NOEXIST = 2;
		final static public int MAIN_MATRIX_CHANGE_RESULT_FAIL_CANT_RETAKE = 3;
		final static public int MAIN_MATRIX_CHANGE_RESULT_FAIL_CANNT_MOVE = 4;
		final static public int MAIN_MAIRIX_CHANGE_RESULT_FAIL_CANNT_SPLICE = 5;
		final static public int MAIN_MAIRIX_CHANGE_RESULT_FAIL_GAME_NOT_START = 6;
		
		public int result;
		
		public MainMatrixChangeResponse(int result){
			this.result = result;
		}
		
		public MainMatrixChangeResponse(){
		}
		@Override
		public String toString() {
			return "MainMatrixChangeResponse";
		}
	}
	
	public static class SynchronizeRequest extends FlashMessage
	{
//		public int player_id;
//		
//		public SynchronizeRequest(int player_id){
//			this.player_id = player_id;
//		}
		
		public SynchronizeRequest(){}
		
		@Override
		public String toString() {
			return "SynchronizeRequest";
		}
	}
	
	public static class SynchronizeResponse extends FlashMessage
	{
		public CardData[] matrix;	//桌子上的牌
		public CardData[] player_card; //玩家的手牌
		public int left_card;	//牌堆的剩余牌数
		
		public SynchronizeResponse(CardData[] m, CardData[] p, int left){
			this.matrix = m;
			this.player_card = p;
			this.left_card = left;
		}
		
		public SynchronizeResponse(){}
		
		@Override
		public String toString() {
			return "SynchronizeResponse";
		}
	}
	
	public static class TimeOutNotify extends FlashMessage
	{
		public int player_id;
		
		public TimeOutNotify(int pid){
			this.player_id = pid;
		}
		
		public TimeOutNotify(){}
	}
	
	public static class GetPlayerDataRequest extends FlashMessage
	{
		public int player_id;
		
		public GetPlayerDataRequest(int pid){
			this.player_id = pid;
		}
		
		public GetPlayerDataRequest(){}
	}
	
	public static class GetPlayerDataResponse extends FlashMessage
	{
		public PlayerData player;
		
		public GetPlayerDataResponse(PlayerData p){
			this.player = p;
		}
		
		public GetPlayerDataResponse(){}
	}
	
	public static class PlayerUpdateNotify extends FlashMessage
	{
		public PlayerData player;
		
		public PlayerUpdateNotify(PlayerData p){
			this.player = p;
		}
		
		public PlayerUpdateNotify(){}
	}
	// 玩家在当前频道发言
	public static class SpeakToPublicRequest extends FlashMessage
	{
		public String message;
		
		public SpeakToPublicRequest(String str){
			this.message = str;
		}
		
		public SpeakToPublicRequest(){}
	}
	
	public static class SpeakToPublicResponse extends FlashMessage
	{
		public SpeakToPublicResponse(){}
	}
	
	public static class SpeakToPublicNotify extends FlashMessage
	{
		final static public int CHANNEL_TYPE_HALL = 0;
		final static public int CHANNEL_TYPE_ROOM = 1;
		final static public int CHANNEL_TYPE_DESK = 2;
		
		public int player_id;
		public int channel_type;
		public String message;
		
		public SpeakToPublicNotify(int pid, int ctype, String message){
			this.player_id = pid;
			this.channel_type = ctype;
			this.message = message;
		}
		
		public SpeakToPublicNotify(){}
	}
	
	// 玩家对某玩家私聊
	public static class SpeakToPrivateRequest extends FlashMessage
	{
		public String player_name;
		public String message;
		
		public SpeakToPrivateRequest(String name, String str){
			this.player_name = name;
			this.message = str;
		}
		
		public SpeakToPrivateRequest(){}
	}
	
	public static class SpeakToPrivateResponse extends FlashMessage
	{
		final static public int SPEAK_TO_PRIVATE_RESULT_SUCCESS = 0;
		final static public int SPEAK_TO_PRIVATE_RESULT_FAIL_PLAYER_NOEXIST = 1;
		
		public int result;
		
		public SpeakToPrivateResponse(int result){
			this.result = result;
		}
		
		public SpeakToPrivateResponse(){}
	}
	
	public static class SpeakToPrivateNotify extends FlashMessage
	{
		public String player_name;
		public String message;
		
		public SpeakToPrivateNotify(String name, String message){
			this.player_name = name;
			this.message = message;
		}
		
		public SpeakToPrivateNotify(){}
	}
	
	// 向某频道发送消息
	public static class SpeakToChannelRequest extends FlashMessage
	{
		public int channel;
		public String message;
		
		public SpeakToChannelRequest(int channel, String message){
			this.channel = channel;
			this.message = message;
		}
		
		public SpeakToChannelRequest(){}
	}
	
	public static class SpeakToChannelNotify extends FlashMessage
	{
		public int player_id;
		public String message;
		
		public SpeakToChannelNotify(int pid, String message){
			this.player_id = pid;
			this.message = message;
		}
		
		public SpeakToChannelNotify(){}
	}
	
	public static class SpeakToChannelResponse extends FlashMessage
	{
		final static public int SPEAK_TO_CHANNEL_RESULT_SUCCESS = 0;
		final static public int SPEAK_TO_CHANNEL_RESULT_FAIL_CHANNEL_NOEXIST = 1;
		
		public int result;
		
		public SpeakToChannelResponse(int result){
			this.result = result;
		}
		
		public SpeakToChannelResponse(){}
	}
	
	public static class EnterDeskAsVisitorRequest extends FlashMessage
	{
		public int desk_id;
		
		public EnterDeskAsVisitorRequest(int did){
			this.desk_id = did;
		}
		public EnterDeskAsVisitorRequest(){}
	}
	
	public static class EnterDeskAsVisitorResponse extends FlashMessage
	{
		final static public int ENTER_DESK_VISITOR_RESULT_SUCCESS = 0;
		final static public int ENTER_DESK_VISITOR_RESULT_FAIL_ALREADY_IN_DESK = 1;
		final static public int ENTER_DESK_VISITOR_RESULT_FAIL_NO_DESK = 2;
		final static public int ENTER_DESK_VISITOR_RESULT_FAIL_NO_ROOM = 3;
		public int result;
		public int desk_id;
		public int turn_interval;
		public int operate_time;
		public PlayerState[] ps;
		
		public EnterDeskAsVisitorResponse(int result, int did, int ti, int ot, PlayerState[] ps){
			this.result = result;
			this.desk_id = did;
			this.turn_interval = ti;
			this.operate_time = ot;
			this.ps = ps;
		}
		
		public EnterDeskAsVisitorResponse(int result){
			this.result = result;
		}
		
		public EnterDeskAsVisitorResponse(){}
	}
	
	public static void main(String[] args) throws IOException
	{
		CAppBridge.init();
		MessageFactory factory = new MessageFactory();
		final Date date = new Date();
		{
			MutualMessageCodeGeneratorJava gen_java = new MutualMessageCodeGeneratorJava(
					"com.fc.lami",
					"",
					"MessageCodecJava"
					){
				@Override
				public String getVersion() {
					return date.toString();
				}
			};
			gen_java.genCodeFile(factory, 
					new File("./src"));
		}{
			FlashMessageCodeGenerator gen_as = new FlashMessageCodeGenerator(
					"com.fc.lami",
					"MessageCodec",
					"\timport com.fc.lami.Messages.*;",
					""
					){
				@Override
				public String getVersion() {
					return date.toString();
				}
			};
			gen_as.genCodeFile(factory, 
					new File(args[0], "\\src"));
		}
		
		
	}
}
