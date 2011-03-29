package com.fc.lami
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	
	import com.fc.lami.Messages.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
		public function	getType(msg : Message) : int 
		{
			if (msg is com.fc.lami.Messages.EchoNotify) return 1;
			if (msg is com.fc.lami.Messages.EchoRequest) return 2;
			if (msg is com.fc.lami.Messages.EchoResponse) return 3;
			if (msg is com.fc.lami.Messages.GetTimeRequest) return 4;
			if (msg is com.fc.lami.Messages.GetTimeResponse) return 5;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.fc.lami.Messages.EchoNotify;
			case 2 : return new com.fc.lami.Messages.EchoRequest;
			case 3 : return new com.fc.lami.Messages.EchoResponse;
			case 4 : return new com.fc.lami.Messages.GetTimeRequest;
			case 5 : return new com.fc.lami.Messages.GetTimeResponse;

			}
			return null;
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.fc.lami.Messages.EchoNotify) {
			r_EchoNotify_1(com.fc.lami.Messages.EchoNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			r_EchoRequest_2(com.fc.lami.Messages.EchoRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			r_EchoResponse_3(com.fc.lami.Messages.EchoResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			r_GetTimeRequest_4(com.fc.lami.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			r_GetTimeResponse_5(com.fc.lami.Messages.GetTimeResponse(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.fc.lami.Messages.EchoNotify) {
			w_EchoNotify_1(com.fc.lami.Messages.EchoNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			w_EchoRequest_2(com.fc.lami.Messages.EchoRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			w_EchoResponse_3(com.fc.lami.Messages.EchoResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			w_GetTimeRequest_4(com.fc.lami.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			w_GetTimeResponse_5(com.fc.lami.Messages.GetTimeResponse(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	function new_EchoNotify_1() : com.fc.lami.Messages.EchoNotify {return new com.fc.lami.Messages.EchoNotify();}
	private function r_EchoNotify_1(msg : com.fc.lami.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoNotify_1(msg : com.fc.lami.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	function new_EchoRequest_2() : com.fc.lami.Messages.EchoRequest {return new com.fc.lami.Messages.EchoRequest();}
	private function r_EchoRequest_2(msg : com.fc.lami.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoRequest_2(msg : com.fc.lami.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	function new_EchoResponse_3() : com.fc.lami.Messages.EchoResponse {return new com.fc.lami.Messages.EchoResponse();}
	private function r_EchoResponse_3(msg : com.fc.lami.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoResponse_3(msg : com.fc.lami.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_4() : com.fc.lami.Messages.GetTimeRequest {return new com.fc.lami.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_4(msg : com.fc.lami.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_4(msg : com.fc.lami.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_5() : com.fc.lami.Messages.GetTimeResponse {return new com.fc.lami.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_5(msg : com.fc.lami.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_5(msg : com.fc.lami.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}



	}

}