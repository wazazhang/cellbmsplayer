package com.fc.lami;

import java.io.File;
import java.io.IOException;

import com.cell.j2se.CAppBridge;
import com.net.flash.message.FlashMessage;
import com.net.flash.message.FlashMessageCodeGenerator;
import com.net.mutual.MutualMessageCodeGeneratorJava;



public class Messages {

	public static class EchoRequest extends FlashMessage
	{
		public String message;
		
		public EchoRequest(String message) {
			this.message = message;
		}
		public EchoRequest() {}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class EchoResponse extends FlashMessage
	{
		public String message;

		public EchoResponse(String message) {
			this.message = message;
		}
		public EchoResponse() {}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class EchoNotify extends FlashMessage
	{
		public String message;

		public EchoNotify(String message) {
			this.message = message;
		}
		public EchoNotify() {}
		@Override
		public String toString() {
			return message+"";
		}
	}

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
		public String name;
		//public PlayerData nextPlayer;
		//public ClientSession session;
		public PlayerData()
		{
			//this.session = session;
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
		
		public CardData(){
		}
		
		public CardData(int point, int type){
			this.point = point;
			this.type = type;
		}
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
	
	/** 把牌放到桌面上，可以一次性放多个  */
	public static class SendCardRequest extends FlashMessage
	{
		public int cards[];
		
		/** 第一张牌的坐标 */
		public int x;
		public int y;
		
		public SendCardRequest(int cards[], int x, int y){
			this.cards = cards;
			this.x = x;
			this.y = y;
		}
		
		public SendCardRequest() {}
		@Override
		public String toString() {
			return "SendCardRequest";
		}
	}
	
	/** 放牌结果返回 */
	public static class SendCardResponse extends FlashMessage
	{
		/** 放牌成功 */
		final static public int SEND_CARD_RESULT_SUCCESS = 0;
		/** 放牌失败  该位置已经有牌 */
		final static public int SEND_CARD_RESULT_FAIL_NOT_BLANK = 1;
		/** 放牌失败  用一牌把两边连起来牌组不成立时 */
		final static public int SEND_CARD_RESULT_FAIL_NO_MATCH = 2;
		/** 位置不对 */
		final static public int SEND_CARD_RESULT_FAIL_LOCATION_WRONG = 3;
		/** 没有破冰时不能和原有牌拼接 */
		final static public int SEND_CARD_RESULT_FAIL_SPLIT = 4;
		
		public int result;
		
		public SendCardResponse(int result){
			this.result = result;
		}
		
		public SendCardResponse() {}
		@Override
		public String toString() {
			return "SendCardResponse";
		}
	}
	
	/** 别的玩家放牌通知 */
	public static class SendCardNotify extends FlashMessage
	{
		public int player_id;
		public CardData cards[];
		public int x,y;
		
		public SendCardNotify(int player_id, CardData cards[], int x, int y){
			this.cards = cards;
			this.player_id = player_id;
			this.x = x;
			this.y = y;
		}
		
		public SendCardNotify() {}
		@Override
		public String toString() {
			return "SendCardNotify";
		}
	}
	
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
	
	/** 取回自己本回合放出的牌 */
	public static class RetakeCardRequest extends FlashMessage
	{
		public int cards[];
		
		public RetakeCardRequest(int[] card_ids){
			this.cards = card_ids;
		}
		
		public RetakeCardRequest() {}
		@Override
		public String toString() {
			return "RetakeCardRequest";
		}
	}
	
	/** 取牌结果返回 */
	public static class RetakeCardResponse extends FlashMessage
	{
		/** 取牌成功 */
		final static public int RETAKE_CARD_RESULT_SUCCESS = 0;
		/** 取牌失败  该牌不是本回合放上去的 */
		final static public int RETAKE_CARD_RESULT_FAIL_NOT_THIS_TURN = 1;
		/** 桌面上没有这张牌 */
		final static public int RETAKE_CARD_RESULT_FAIL_NOEXIST = 2;
		
		public int result;
		
		public RetakeCardResponse(int result){
			this.result = result;
		}
		
		public RetakeCardResponse() {}
		@Override
		public String toString() {
			return "RetakeCardResponse";
		}
	}
	
	/** 别的玩家取牌通知 */
	public static class RetakeCardNotify extends FlashMessage
	{
		public int player_id;
		public int x, y;
		public int n;
		
		public RetakeCardNotify(int player_id, int x, int y, int n){
			this.player_id = player_id;
			this.x = x;
			this.y = y;
			this.n = n;
		}
		
		public RetakeCardNotify() {}
		@Override
		public String toString() {
			return "RetakeCardNotify";
		}
	}
	
	/** 移动桌面上的牌，可以多个 */
	public static class MoveCardRequest extends FlashMessage
	{
		public int cards[];
		/** 新的坐标 */
		public int nx, ny;
		
		public MoveCardRequest(int card_ids[], int nx, int ny){
			this.cards = card_ids;
			this.nx = nx;
			this.ny = ny;
		}
		
		public MoveCardRequest() {}
		@Override
		public String toString() {
			return "MoveCardRequest";
		}
	}
	
	/** 移牌结果返回 */ 
	public static class MoveCardResponse extends FlashMessage
	{
		/** 移牌成功 */
		final static public int MOVE_CARD_RESULT_SUCCESS = 0;
		/** 未破冰前不能移动原有的牌 */
		final static public int MOVE_CARD_RESULT_FAIL_CANNT_MOVE = 1;
		/** 未破冰前移动的牌不能和桌上原有的牌拼接 */
		final static public int MOVE_CARD_RESULT_FAIL_SPLIT = 2;
		/** 桌面上没有这张牌 */
		final static public int MOVE_CARD_RESULT_FAIL_CARD_NOEXIST = 3;
		/** 位置不对 */
		final static public int MOVE_CARD_RESULT_FAIL_LOCATION_WRONG = 4;
		
		public int result;
		public MoveCardResponse(int result){
			this.result = result;
		}
		public MoveCardResponse() {}
		@Override
		public String toString() {
			return "MoveCardResponse";
		}
	}
	
	/** 别的玩家移牌通知 */
	public static class MoveCardNotify extends FlashMessage
	{
		public int cards[];
		/** 新的坐标 */
		public int nx, ny;
		public int player_id;
		
		public MoveCardNotify(int player_id, int card_ids[], int nx, int ny){
			this.player_id = player_id;
			this.cards = card_ids;
			this.nx = nx;
			this.ny = ny;
		}
		
		public MoveCardNotify() {}
		@Override
		public String toString() {
			return "MoveCardNotify";
		}
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
		
		public int result;
		
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
		public String name;
		
		public LoginRequest(String name){
			this.name = name;
		}
		
		public LoginRequest(){}
		
		@Override
		public String toString() {
			return "LoginRequest";
		}
	}
	
	public static class LoginResponse extends FlashMessage
	{
		final static public int LOGIN_RESULT_SUCCESS = 0;
		final static public int LOGIN_RESULT_FAIL = 1;
		public int result;
		public RoomData rooms[];
		
		public LoginResponse(int result){
			this.result = result;
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
		public ExitRoomResponse(){}
		
		@Override
		public String toString() {
			return "ExitRoomResponse";
		}
	}
	
	public static class ExitRoomNotify extends FlashMessage
	{
		public PlayerData player;
		
		public ExitRoomNotify(PlayerData pd){
			this.player = pd;
		}
		
		public ExitRoomNotify(){}
		
		@Override
		public String toString() {
			return "ExitRoomNotify";
		}
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
		final static public int ENTER_DESK_RESULT_FAIL_PLAYER_EXIST = 1;
		final static public int ENTER_DESK_RESULT_FAIL_NOT_HAVE_ROOM = 2;
		
		public int result;
		
		
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
		public PlayerData player;
		
		public EnterDeskNotify(PlayerData pd){
			this.player = pd;
		}
		
		public EnterDeskNotify(){}
		
		@Override
		public String toString() {
			return "EnterDeskNotify";
		}
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
		public PlayerData player;
		
		public LeaveDeskNotify(PlayerData pd){
			this.player = pd;
		}
		
		public LeaveDeskNotify(){}
		
		@Override
		public String toString() {
			return "LeaveDeskNotify";
		}
	}
	
	/** 玩家回合开始通知 */
	public static class TurnStartNotify extends FlashMessage
	{
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
	
	
	
	public static void main(String[] args) throws IOException
	{
		CAppBridge.init();
		MessageFactory factory = new MessageFactory();
		{
			MutualMessageCodeGeneratorJava gen_java = new MutualMessageCodeGeneratorJava(
					"com.fc.lami",
					"",
					"MessageCodecJava"
					);
			gen_java.genCodeFile(factory, 
					new File("./src"));
		}{
			FlashMessageCodeGenerator gen_as = new FlashMessageCodeGenerator(
					"com.fc.lami",
					"MessageCodec",
					"\timport com.fc.lami.Messages.*;",
					""
					);
			gen_as.genCodeFile(factory, 
					new File(args[0], "\\src"));
		}
		
		
	}
}
