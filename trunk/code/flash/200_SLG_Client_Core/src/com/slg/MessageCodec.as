package com.slg
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	import com.net.client.NetDataTypes;
	import com.slg.net.messages.Messages.*;
	import com.slg.entity.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
	
		public function getVersion() : String{
			return "Tue Jun 07 15:29:46 CST 2011";
		}
	
		public function	getType(msg : Message) : int 
		{
			if (msg is com.slg.entity.Currency) return 1;
			if (msg is com.slg.entity.GuageNumber) return 2;
			if (msg is com.slg.entity.Hero) return 3;
			if (msg is com.slg.entity.Player) return 4;
			if (msg is com.slg.entity.Position) return 5;
			if (msg is com.slg.entity.Village) return 6;
			if (msg is com.slg.net.messages.Messages.GetTimeRequest) return 7;
			if (msg is com.slg.net.messages.Messages.GetTimeResponse) return 8;
			if (msg is com.slg.net.messages.Messages.LoginRequest) return 9;
			if (msg is com.slg.net.messages.Messages.LoginResponse) return 10;
			if (msg is com.slg.net.messages.Messages.LogoutRequest) return 11;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.slg.entity.Currency;
			case 2 : return new com.slg.entity.GuageNumber;
			case 3 : return new com.slg.entity.Hero;
			case 4 : return new com.slg.entity.Player;
			case 5 : return new com.slg.entity.Position;
			case 6 : return new com.slg.entity.Village;
			case 7 : return new com.slg.net.messages.Messages.GetTimeRequest;
			case 8 : return new com.slg.net.messages.Messages.GetTimeResponse;
			case 9 : return new com.slg.net.messages.Messages.LoginRequest;
			case 10 : return new com.slg.net.messages.Messages.LoginResponse;
			case 11 : return new com.slg.net.messages.Messages.LogoutRequest;

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
		if (msg is com.slg.entity.Hero) {
			r_Hero_3(com.slg.entity.Hero(msg), input); return;
		}
		if (msg is com.slg.entity.Player) {
			r_Player_4(com.slg.entity.Player(msg), input); return;
		}
		if (msg is com.slg.entity.Position) {
			r_Position_5(com.slg.entity.Position(msg), input); return;
		}
		if (msg is com.slg.entity.Village) {
			r_Village_6(com.slg.entity.Village(msg), input); return;
		}
		if (msg is com.slg.net.messages.Messages.GetTimeRequest) {
			r_GetTimeRequest_7(com.slg.net.messages.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.slg.net.messages.Messages.GetTimeResponse) {
			r_GetTimeResponse_8(com.slg.net.messages.Messages.GetTimeResponse(msg), input); return;
		}
		if (msg is com.slg.net.messages.Messages.LoginRequest) {
			r_LoginRequest_9(com.slg.net.messages.Messages.LoginRequest(msg), input); return;
		}
		if (msg is com.slg.net.messages.Messages.LoginResponse) {
			r_LoginResponse_10(com.slg.net.messages.Messages.LoginResponse(msg), input); return;
		}
		if (msg is com.slg.net.messages.Messages.LogoutRequest) {
			r_LogoutRequest_11(com.slg.net.messages.Messages.LogoutRequest(msg), input); return;
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
		if (msg is com.slg.entity.Hero) {
			w_Hero_3(com.slg.entity.Hero(msg), output); return;
		}
		if (msg is com.slg.entity.Player) {
			w_Player_4(com.slg.entity.Player(msg), output); return;
		}
		if (msg is com.slg.entity.Position) {
			w_Position_5(com.slg.entity.Position(msg), output); return;
		}
		if (msg is com.slg.entity.Village) {
			w_Village_6(com.slg.entity.Village(msg), output); return;
		}
		if (msg is com.slg.net.messages.Messages.GetTimeRequest) {
			w_GetTimeRequest_7(com.slg.net.messages.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.slg.net.messages.Messages.GetTimeResponse) {
			w_GetTimeResponse_8(com.slg.net.messages.Messages.GetTimeResponse(msg), output); return;
		}
		if (msg is com.slg.net.messages.Messages.LoginRequest) {
			w_LoginRequest_9(com.slg.net.messages.Messages.LoginRequest(msg), output); return;
		}
		if (msg is com.slg.net.messages.Messages.LoginResponse) {
			w_LoginResponse_10(com.slg.net.messages.Messages.LoginResponse(msg), output); return;
		}
		if (msg is com.slg.net.messages.Messages.LogoutRequest) {
			w_LogoutRequest_11(com.slg.net.messages.Messages.LogoutRequest(msg), output); return;
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
//	com.slg.entity.Hero
//	----------------------------------------------------------------------------------------------------
	function new_Hero_3() : com.slg.entity.Hero {return new com.slg.entity.Hero();}
	private function r_Hero_3(msg : com.slg.entity.Hero, input : NetDataInput) : void {
		msg.id = input.readInt();
		msg.name = input.readJavaUTF();
		msg.exp = input.readExternal() as com.slg.entity.GuageNumber;
		msg.level = input.readInt();
		msg.hp = input.readExternal() as com.slg.entity.GuageNumber;
		msg.attack = input.readInt();
		msg.politics = input.readInt();
		msg.commander = input.readInt();
		msg.sex = input.readInt();
		msg.loyalty = input.readExternal() as com.slg.entity.GuageNumber;
		msg.quality = input.readInt();
		msg.max_skill_count = input.readInt();
		msg.skill_list = input.readExternalArray();
		msg.max_item_count = input.readInt();
		msg.item_list = input.readExternalArray();
	}
	private function w_Hero_3(msg : com.slg.entity.Hero, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeJavaUTF(msg.name);
		output.writeExternal(msg.exp);
		output.writeInt(msg.level);
		output.writeExternal(msg.hp);
		output.writeInt(msg.attack);
		output.writeInt(msg.politics);
		output.writeInt(msg.commander);
		output.writeInt(msg.sex);
		output.writeExternal(msg.loyalty);
		output.writeInt(msg.quality);
		output.writeInt(msg.max_skill_count);
		output.writeExternalArray(msg.skill_list);
		output.writeInt(msg.max_item_count);
		output.writeExternalArray(msg.item_list);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Player
//	----------------------------------------------------------------------------------------------------
	function new_Player_4() : com.slg.entity.Player {return new com.slg.entity.Player();}
	private function r_Player_4(msg : com.slg.entity.Player, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.name = input.readJavaUTF();
		msg.office = input.readInt();
		msg.exp = input.readExternal() as com.slg.entity.GuageNumber;
		msg.level = input.readInt();
		msg.ap = input.readExternal() as com.slg.entity.GuageNumber;
		msg.currency = input.readExternal() as com.slg.entity.Currency;
		msg.cur_village_id = input.readInt();
	}
	private function w_Player_4(msg : com.slg.entity.Player, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.name);
		output.writeInt(msg.office);
		output.writeExternal(msg.exp);
		output.writeInt(msg.level);
		output.writeExternal(msg.ap);
		output.writeExternal(msg.currency);
		output.writeInt(msg.cur_village_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Position
//	----------------------------------------------------------------------------------------------------
	function new_Position_5() : com.slg.entity.Position {return new com.slg.entity.Position();}
	private function r_Position_5(msg : com.slg.entity.Position, input : NetDataInput) : void {
	}
	private function w_Position_5(msg : com.slg.entity.Position, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Village
//	----------------------------------------------------------------------------------------------------
	function new_Village_6() : com.slg.entity.Village {return new com.slg.entity.Village();}
	private function r_Village_6(msg : com.slg.entity.Village, input : NetDataInput) : void {
		msg.id = input.readInt();
		msg.name = input.readJavaUTF();
		msg.player_id = input.readInt();
		msg.food = input.readInt();
		msg.city_id = input.readInt();
		msg.buildings = input.readIntArray();
		msg.heros = input.readIntArray();
		msg.arms_list = input.readExternalArray();
		msg.soldiers_list = input.readExternalArray();
	}
	private function w_Village_6(msg : com.slg.entity.Village, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeJavaUTF(msg.name);
		output.writeInt(msg.player_id);
		output.writeInt(msg.food);
		output.writeInt(msg.city_id);
		output.writeIntArray(msg.buildings);
		output.writeIntArray(msg.heros);
		output.writeExternalArray(msg.arms_list);
		output.writeExternalArray(msg.soldiers_list);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.net.messages.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_7() : com.slg.net.messages.Messages.GetTimeRequest {return new com.slg.net.messages.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_7(msg : com.slg.net.messages.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_7(msg : com.slg.net.messages.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.net.messages.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_8() : com.slg.net.messages.Messages.GetTimeResponse {return new com.slg.net.messages.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_8(msg : com.slg.net.messages.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_8(msg : com.slg.net.messages.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.net.messages.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	function new_LoginRequest_9() : com.slg.net.messages.Messages.LoginRequest {return new com.slg.net.messages.Messages.LoginRequest();}
	private function r_LoginRequest_9(msg : com.slg.net.messages.Messages.LoginRequest, input : NetDataInput) : void {
		msg.name = input.readJavaUTF();
		msg.validate = input.readJavaUTF();
		msg.version = input.readJavaUTF();
	}
	private function w_LoginRequest_9(msg : com.slg.net.messages.Messages.LoginRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.validate);
		output.writeJavaUTF(msg.version);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.net.messages.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	function new_LoginResponse_10() : com.slg.net.messages.Messages.LoginResponse {return new com.slg.net.messages.Messages.LoginResponse();}
	private function r_LoginResponse_10(msg : com.slg.net.messages.Messages.LoginResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
		msg.version = input.readJavaUTF();
		msg.player_data = input.readExternal() as com.slg.entity.Player;
		msg.village = input.readExternal() as com.slg.entity.Village;
	}
	private function w_LoginResponse_10(msg : com.slg.net.messages.Messages.LoginResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
		output.writeJavaUTF(msg.version);
		output.writeExternal(msg.player_data);
		output.writeExternal(msg.village);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.net.messages.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	function new_LogoutRequest_11() : com.slg.net.messages.Messages.LogoutRequest {return new com.slg.net.messages.Messages.LogoutRequest();}
	private function r_LogoutRequest_11(msg : com.slg.net.messages.Messages.LogoutRequest, input : NetDataInput) : void {
	}
	private function w_LogoutRequest_11(msg : com.slg.net.messages.Messages.LogoutRequest, output : NetDataOutput) : void {
	}



	}

}