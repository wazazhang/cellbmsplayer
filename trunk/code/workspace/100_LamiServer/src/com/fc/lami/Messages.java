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
	
	public static void main(String[] args) throws IOException
	{
		CAppBridge.init();
		FlashMessageFactory factory = new FlashMessageFactory(null, Messages.class);
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
