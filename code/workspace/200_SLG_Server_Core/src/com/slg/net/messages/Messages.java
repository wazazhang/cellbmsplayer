package com.slg.net.messages;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.cell.j2se.CAppBridge;
import com.net.flash.message.FlashMessage;
import com.net.flash.message.FlashMessageCodeGenerator;
import com.net.mutual.MutualMessageCodeGeneratorJava;
import com.slg.entity.Player;

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
	
	/** 登录 */
	public static class LoginRequest extends FlashMessage
	{
		public String name;
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
		public String version;
		public Player player_data;

//		public long server_time;
		
		public LoginResponse(short result, Player p, String version){
			this.result = result;
			this.version = version;
			this.player_data = p;
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

	public static void main(String[] args) throws IOException
	{
		CAppBridge.init();
		MessageFactory factory = new MessageFactory();
		final Date date = new Date();
		{
			MutualMessageCodeGeneratorJava gen_java = new MutualMessageCodeGeneratorJava(
					"com.slg.net.messages",
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
					"com.slg",
					"MessageCodec",
					"\timport com.slg.net.messages.Messages.*;\n" +
					"\timport com.slg.entity.*;",
					"\timport com.slg.entity.*;"
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
