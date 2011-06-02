package com.slg.sanguosha
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	import com.net.client.NetDataTypes;
	import com.slg.sanguosha.Messages.*;
	import com.slg.entity.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
	
		public function getVersion() : String{
			return "Thu Jun 02 17:17:15 CST 2011";
		}
	
		public function	getType(msg : Message) : int 
		{
			if (msg is com.slg.entity.Currency) return 1;
			if (msg is com.slg.entity.GuageNumber) return 2;
			if (msg is com.slg.entity.Player) return 3;
			if (msg is com.slg.sanguosha.Messages.GetTimeRequest) return 4;
			if (msg is com.slg.sanguosha.Messages.GetTimeResponse) return 5;
			if (msg is com.slg.sanguosha.Messages.LoginRequest) return 6;
			if (msg is com.slg.sanguosha.Messages.LoginResponse) return 7;
			if (msg is com.slg.sanguosha.Messages.LogoutRequest) return 8;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.slg.entity.Currency;
			case 2 : return new com.slg.entity.GuageNumber;
			case 3 : return new com.slg.entity.Player;
			case 4 : return new com.slg.sanguosha.Messages.GetTimeRequest;
			case 5 : return new com.slg.sanguosha.Messages.GetTimeResponse;
			case 6 : return new com.slg.sanguosha.Messages.LoginRequest;
			case 7 : return new com.slg.sanguosha.Messages.LoginResponse;
			case 8 : return new com.slg.sanguosha.Messages.LogoutRequest;

			}
			return null;
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.slg.entity.Currency) {
			r_Currency_1(com.slg.entity.Currency(msg), input); return;
		}
		if (msg is com.slg.entity.GuageNumber) {
			r_GuageNumber_2(com.slg.entity.GuageNumber(msg), input); return;
		}
		if (msg is com.slg.entity.Player) {
			r_Player_3(com.slg.entity.Player(msg), input); return;
		}
		if (msg is com.slg.sanguosha.Messages.GetTimeRequest) {
			r_GetTimeRequest_4(com.slg.sanguosha.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.slg.sanguosha.Messages.GetTimeResponse) {
			r_GetTimeResponse_5(com.slg.sanguosha.Messages.GetTimeResponse(msg), input); return;
		}
		if (msg is com.slg.sanguosha.Messages.LoginRequest) {
			r_LoginRequest_6(com.slg.sanguosha.Messages.LoginRequest(msg), input); return;
		}
		if (msg is com.slg.sanguosha.Messages.LoginResponse) {
			r_LoginResponse_7(com.slg.sanguosha.Messages.LoginResponse(msg), input); return;
		}
		if (msg is com.slg.sanguosha.Messages.LogoutRequest) {
			r_LogoutRequest_8(com.slg.sanguosha.Messages.LogoutRequest(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.slg.entity.Currency) {
			w_Currency_1(com.slg.entity.Currency(msg), output); return;
		}
		if (msg is com.slg.entity.GuageNumber) {
			w_GuageNumber_2(com.slg.entity.GuageNumber(msg), output); return;
		}
		if (msg is com.slg.entity.Player) {
			w_Player_3(com.slg.entity.Player(msg), output); return;
		}
		if (msg is com.slg.sanguosha.Messages.GetTimeRequest) {
			w_GetTimeRequest_4(com.slg.sanguosha.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.slg.sanguosha.Messages.GetTimeResponse) {
			w_GetTimeResponse_5(com.slg.sanguosha.Messages.GetTimeResponse(msg), output); return;
		}
		if (msg is com.slg.sanguosha.Messages.LoginRequest) {
			w_LoginRequest_6(com.slg.sanguosha.Messages.LoginRequest(msg), output); return;
		}
		if (msg is com.slg.sanguosha.Messages.LoginResponse) {
			w_LoginResponse_7(com.slg.sanguosha.Messages.LoginResponse(msg), output); return;
		}
		if (msg is com.slg.sanguosha.Messages.LogoutRequest) {
			w_LogoutRequest_8(com.slg.sanguosha.Messages.LogoutRequest(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Currency
//	----------------------------------------------------------------------------------------------------
	function new_Currency_1() : com.slg.entity.Currency {return new com.slg.entity.Currency();}
	private function r_Currency_1(msg : com.slg.entity.Currency, input : NetDataInput) : void {
		msg.gold = input.readInt();
	}
	private function w_Currency_1(msg : com.slg.entity.Currency, output : NetDataOutput) : void {
		output.writeInt(msg.gold);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.GuageNumber
//	----------------------------------------------------------------------------------------------------
	function new_GuageNumber_2() : com.slg.entity.GuageNumber {return new com.slg.entity.GuageNumber();}
	private function r_GuageNumber_2(msg : com.slg.entity.GuageNumber, input : NetDataInput) : void {
	}
	private function w_GuageNumber_2(msg : com.slg.entity.GuageNumber, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Player
//	----------------------------------------------------------------------------------------------------
	function new_Player_3() : com.slg.entity.Player {return new com.slg.entity.Player();}
	private function r_Player_3(msg : com.slg.entity.Player, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.name = input.readJavaUTF();
		msg.office = input.readInt();
		msg.exp = input.readExternal() as com.slg.entity.GuageNumber;
		msg.level = input.readInt();
		msg.ap = input.readExternal() as com.slg.entity.GuageNumber;
		msg.currency = input.readExternal() as com.slg.entity.Currency;
	}
	private function w_Player_3(msg : com.slg.entity.Player, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.name);
		output.writeInt(msg.office);
		output.writeExternal(msg.exp);
		output.writeInt(msg.level);
		output.writeExternal(msg.ap);
		output.writeExternal(msg.currency);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_4() : com.slg.sanguosha.Messages.GetTimeRequest {return new com.slg.sanguosha.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_4(msg : com.slg.sanguosha.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_4(msg : com.slg.sanguosha.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_5() : com.slg.sanguosha.Messages.GetTimeResponse {return new com.slg.sanguosha.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_5(msg : com.slg.sanguosha.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_5(msg : com.slg.sanguosha.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	function new_LoginRequest_6() : com.slg.sanguosha.Messages.LoginRequest {return new com.slg.sanguosha.Messages.LoginRequest();}
	private function r_LoginRequest_6(msg : com.slg.sanguosha.Messages.LoginRequest, input : NetDataInput) : void {
		msg.name = input.readJavaUTF();
		msg.validate = input.readJavaUTF();
		msg.version = input.readJavaUTF();
	}
	private function w_LoginRequest_6(msg : com.slg.sanguosha.Messages.LoginRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.validate);
		output.writeJavaUTF(msg.version);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	function new_LoginResponse_7() : com.slg.sanguosha.Messages.LoginResponse {return new com.slg.sanguosha.Messages.LoginResponse();}
	private function r_LoginResponse_7(msg : com.slg.sanguosha.Messages.LoginResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
		msg.version = input.readJavaUTF();
		msg.player_data = input.readExternal() as com.slg.entity.Player;
	}
	private function w_LoginResponse_7(msg : com.slg.sanguosha.Messages.LoginResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
		output.writeJavaUTF(msg.version);
		output.writeExternal(msg.player_data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	function new_LogoutRequest_8() : com.slg.sanguosha.Messages.LogoutRequest {return new com.slg.sanguosha.Messages.LogoutRequest();}
	private function r_LogoutRequest_8(msg : com.slg.sanguosha.Messages.LogoutRequest, input : NetDataInput) : void {
	}
	private function w_LogoutRequest_8(msg : com.slg.sanguosha.Messages.LogoutRequest, output : NetDataOutput) : void {
	}



	}

}