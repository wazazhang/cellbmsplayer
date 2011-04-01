package com.fc.lami;

import java.io.File;
import java.io.IOException;

import com.cell.j2se.CAppBridge;
import com.net.flash.message.FlashMessage;
import com.net.flash.message.FlashMessageCodeGenerator;
import com.net.flash.message.FlashMessageFactory;
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
		public CardData cards[];
		
		public SendCardRequest(CardData cards[]){
			this.cards = cards;
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
		final static public short SEND_CARD_RESULT_SUCCESS = 0;
		/** 放牌失败  该位置已经有牌 */
		final static public short SEND_CARD_RESULT_FAIL_NOT_BLANK = 1;
		/** 放牌失败  用一牌把两边连起来牌组不成立时 */
		final static public short SEND_CARD_RESULT_FAIL_NO_MATCH = 2;
		
		public short result;
		
		public SendCardResponse(short result){
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
		public CardData cards[];
		public int player_id;
		
		public SendCardNotify(int player_id, CardData cards[]){
			this.cards = cards;
			this.player_id = player_id;
		}
		
		public SendCardNotify() {}
		@Override
		public String toString() {
			return "SendCardNotify";
		}
	}
	
	/** 取回自己本回合放出的牌 */
	public static class RetakeCardRequest extends FlashMessage
	{
		public CardData cards[];
		
		public RetakeCardRequest(CardData cards[]){
			this.cards = cards;
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
		final static public short RETAKE_CARD_RESULT_SUCCESS = 0;
		/** 取牌失败  该牌不是本回合放上去的 */
		final static public short RETAKE_CARD_RESULT_FAIL_NOT_THIS_TURN = 1;
		
		public short result;
		
		public RetakeCardResponse(short result){
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
		public CardData cards[];
		
		public RetakeCardNotify(int player_id, CardData cards[]){
			this.player_id = player_id;
			this.cards = cards;
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
		public CardData cards[];
		/** 新的坐标 */
		public int nx, ny;
		
		public MoveCardRequest(CardData cards[], int nx, int ny){
			this.cards = cards;
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
		final static public short MOVE_CARD_RESULT_SUCCESS = 0;
		/** 移牌失败  破坏了已有的牌组 */
		final static public short MOVE_CARD_RESULT_FAIL_NO_MATCH = 1;
		
		public short result;
		public MoveCardResponse(short result){
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
		public CardData cards[];
		/** 新的坐标 */
		public int nx, ny;
		public int player_id;
		
		public MoveCardNotify(int player_id, CardData cards[], int nx, int ny){
			this.player_id = player_id;
			this.cards = cards;
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
		public ReadyRequest() {}
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
		
		public ReadyNotify(int player_id){
			this.player_id = player_id;
		}
		public ReadyNotify() {}
		@Override
		public String toString() {
			return "ReadyResponse";
		}
	}
	
	/** 放牌完毕 */
	public static class OverRequest extends FlashMessage
	{
		public OverRequest() {}
		@Override
		public String toString() {
			return "OverRequest";
		}
	}
	
	/** 放牌完毕返回 */
	public static class OverResponse extends FlashMessage
	{
		/** 放牌结束成功 */
		final static public short OVER_RESULT_SUCCESS = 0;
		/** 放牌结束失败，有不成立的牌组 */
		final static public short OVER_RESULT_FAIL_CARD_COMBI_NO_MATCH = 1;
		/** 放牌结束失败，没有破冰 */
		final static public short OVER_RESULT_FAIL_CARD_NOT_OPEN_ICE = 2;
		
		public short result;
		
		public OverResponse(short result){
			this.result = result;
		}
		
		public OverResponse() {}
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
		public CardData card;
		
		public GetCardResponse(CardData card){
			this.card = card;
		}
		
		public GetCardResponse() {}
		@Override
		public String toString() {
			return "GetCardResponse";
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
	
	/** 撤销出牌返回，包含惩罚牌 */
	public static class RepealSendCardResponse extends FlashMessage
	{
		public CardData cards[];
		
		public RepealSendCardResponse(CardData cards[]){
			this.cards = cards;
		}
		
		public RepealSendCardResponse(){}
		
		@Override
		public String toString() {
			return "RepealSendCardResponse";
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		CAppBridge.init();
		FlashMessageFactory factory = new FlashMessageFactory(null, Messages.class, CardData.class);
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
