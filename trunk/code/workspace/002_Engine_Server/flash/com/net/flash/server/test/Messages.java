package com.net.flash.server.test;

import java.io.IOException;

import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataInput;
import com.net.NetDataOutput;

public class Messages 
{
	public static class EchoRequest extends MessageHeader implements ExternalizableMessage
	{
		public String message;
		public EchoRequest(String message) {
			this.message = message;
		}
		public EchoRequest() {}
		@Override
		public void readExternal(NetDataInput in) throws IOException {
			message = in.readUTF();
		}	
		@Override
		public void writeExternal(NetDataOutput out) throws IOException {
			out.writeUTF(message);
		}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
	public static class EchoResponse extends MessageHeader implements ExternalizableMessage
	{
		public String message;
		public EchoResponse(String message) {
			this.message = message;
		}
		public EchoResponse() {}
		@Override
		public void readExternal(NetDataInput in) throws IOException {
			message = in.readUTF();
		}	
		@Override
		public void writeExternal(NetDataOutput out) throws IOException {
			out.writeUTF(message);
		}
		@Override
		public String toString() {
			return message+"";
		}
	}
	
}
