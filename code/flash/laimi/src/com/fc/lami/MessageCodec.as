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
			if (msg is com.fc.lami.Messages.CardData) return 1;
			if (msg is com.fc.lami.Messages.DeskData) return 2;
			if (msg is com.fc.lami.Messages.EchoNotify) return 3;
			if (msg is com.fc.lami.Messages.EchoRequest) return 4;
			if (msg is com.fc.lami.Messages.EchoResponse) return 5;
			if (msg is com.fc.lami.Messages.EnterRoomNotify) return 6;
			if (msg is com.fc.lami.Messages.EnterRoomRequest) return 7;
			if (msg is com.fc.lami.Messages.EnterRoomResponse) return 8;
			if (msg is com.fc.lami.Messages.GetCardRequest) return 9;
			if (msg is com.fc.lami.Messages.GetCardResponse) return 10;
			if (msg is com.fc.lami.Messages.GetTimeRequest) return 11;
			if (msg is com.fc.lami.Messages.GetTimeResponse) return 12;
			if (msg is com.fc.lami.Messages.MoveCardNotify) return 13;
			if (msg is com.fc.lami.Messages.MoveCardRequest) return 14;
			if (msg is com.fc.lami.Messages.MoveCardResponse) return 15;
			if (msg is com.fc.lami.Messages.OverRequest) return 16;
			if (msg is com.fc.lami.Messages.OverResponse) return 17;
			if (msg is com.fc.lami.Messages.PlayerData) return 18;
			if (msg is com.fc.lami.Messages.ReadyNotify) return 19;
			if (msg is com.fc.lami.Messages.ReadyRequest) return 20;
			if (msg is com.fc.lami.Messages.ReadyResponse) return 21;
			if (msg is com.fc.lami.Messages.RepealSendCardRequest) return 22;
			if (msg is com.fc.lami.Messages.RepealSendCardResponse) return 23;
			if (msg is com.fc.lami.Messages.RetakeCardNotify) return 24;
			if (msg is com.fc.lami.Messages.RetakeCardRequest) return 25;
			if (msg is com.fc.lami.Messages.RetakeCardResponse) return 26;
			if (msg is com.fc.lami.Messages.RoomData) return 27;
			if (msg is com.fc.lami.Messages.SendCardNotify) return 28;
			if (msg is com.fc.lami.Messages.SendCardRequest) return 29;
			if (msg is com.fc.lami.Messages.SendCardResponse) return 30;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.fc.lami.Messages.CardData;
			case 2 : return new com.fc.lami.Messages.DeskData;
			case 3 : return new com.fc.lami.Messages.EchoNotify;
			case 4 : return new com.fc.lami.Messages.EchoRequest;
			case 5 : return new com.fc.lami.Messages.EchoResponse;
			case 6 : return new com.fc.lami.Messages.EnterRoomNotify;
			case 7 : return new com.fc.lami.Messages.EnterRoomRequest;
			case 8 : return new com.fc.lami.Messages.EnterRoomResponse;
			case 9 : return new com.fc.lami.Messages.GetCardRequest;
			case 10 : return new com.fc.lami.Messages.GetCardResponse;
			case 11 : return new com.fc.lami.Messages.GetTimeRequest;
			case 12 : return new com.fc.lami.Messages.GetTimeResponse;
			case 13 : return new com.fc.lami.Messages.MoveCardNotify;
			case 14 : return new com.fc.lami.Messages.MoveCardRequest;
			case 15 : return new com.fc.lami.Messages.MoveCardResponse;
			case 16 : return new com.fc.lami.Messages.OverRequest;
			case 17 : return new com.fc.lami.Messages.OverResponse;
			case 18 : return new com.fc.lami.Messages.PlayerData;
			case 19 : return new com.fc.lami.Messages.ReadyNotify;
			case 20 : return new com.fc.lami.Messages.ReadyRequest;
			case 21 : return new com.fc.lami.Messages.ReadyResponse;
			case 22 : return new com.fc.lami.Messages.RepealSendCardRequest;
			case 23 : return new com.fc.lami.Messages.RepealSendCardResponse;
			case 24 : return new com.fc.lami.Messages.RetakeCardNotify;
			case 25 : return new com.fc.lami.Messages.RetakeCardRequest;
			case 26 : return new com.fc.lami.Messages.RetakeCardResponse;
			case 27 : return new com.fc.lami.Messages.RoomData;
			case 28 : return new com.fc.lami.Messages.SendCardNotify;
			case 29 : return new com.fc.lami.Messages.SendCardRequest;
			case 30 : return new com.fc.lami.Messages.SendCardResponse;

			}
			return null;
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.fc.lami.Messages.CardData) {
			r_CardData_1(com.fc.lami.Messages.CardData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.DeskData) {
			r_DeskData_2(com.fc.lami.Messages.DeskData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoNotify) {
			r_EchoNotify_3(com.fc.lami.Messages.EchoNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			r_EchoRequest_4(com.fc.lami.Messages.EchoRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			r_EchoResponse_5(com.fc.lami.Messages.EchoResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomNotify) {
			r_EnterRoomNotify_6(com.fc.lami.Messages.EnterRoomNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomRequest) {
			r_EnterRoomRequest_7(com.fc.lami.Messages.EnterRoomRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomResponse) {
			r_EnterRoomResponse_8(com.fc.lami.Messages.EnterRoomResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			r_GetCardRequest_9(com.fc.lami.Messages.GetCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			r_GetCardResponse_10(com.fc.lami.Messages.GetCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			r_GetTimeRequest_11(com.fc.lami.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			r_GetTimeResponse_12(com.fc.lami.Messages.GetTimeResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			r_MoveCardNotify_13(com.fc.lami.Messages.MoveCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			r_MoveCardRequest_14(com.fc.lami.Messages.MoveCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			r_MoveCardResponse_15(com.fc.lami.Messages.MoveCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.OverRequest) {
			r_OverRequest_16(com.fc.lami.Messages.OverRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.OverResponse) {
			r_OverResponse_17(com.fc.lami.Messages.OverResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			r_PlayerData_18(com.fc.lami.Messages.PlayerData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			r_ReadyNotify_19(com.fc.lami.Messages.ReadyNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			r_ReadyRequest_20(com.fc.lami.Messages.ReadyRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			r_ReadyResponse_21(com.fc.lami.Messages.ReadyResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			r_RepealSendCardRequest_22(com.fc.lami.Messages.RepealSendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			r_RepealSendCardResponse_23(com.fc.lami.Messages.RepealSendCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			r_RetakeCardNotify_24(com.fc.lami.Messages.RetakeCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			r_RetakeCardRequest_25(com.fc.lami.Messages.RetakeCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			r_RetakeCardResponse_26(com.fc.lami.Messages.RetakeCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			r_RoomData_27(com.fc.lami.Messages.RoomData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			r_SendCardNotify_28(com.fc.lami.Messages.SendCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			r_SendCardRequest_29(com.fc.lami.Messages.SendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			r_SendCardResponse_30(com.fc.lami.Messages.SendCardResponse(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.fc.lami.Messages.CardData) {
			w_CardData_1(com.fc.lami.Messages.CardData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.DeskData) {
			w_DeskData_2(com.fc.lami.Messages.DeskData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoNotify) {
			w_EchoNotify_3(com.fc.lami.Messages.EchoNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			w_EchoRequest_4(com.fc.lami.Messages.EchoRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			w_EchoResponse_5(com.fc.lami.Messages.EchoResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomNotify) {
			w_EnterRoomNotify_6(com.fc.lami.Messages.EnterRoomNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomRequest) {
			w_EnterRoomRequest_7(com.fc.lami.Messages.EnterRoomRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomResponse) {
			w_EnterRoomResponse_8(com.fc.lami.Messages.EnterRoomResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			w_GetCardRequest_9(com.fc.lami.Messages.GetCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			w_GetCardResponse_10(com.fc.lami.Messages.GetCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			w_GetTimeRequest_11(com.fc.lami.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			w_GetTimeResponse_12(com.fc.lami.Messages.GetTimeResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			w_MoveCardNotify_13(com.fc.lami.Messages.MoveCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			w_MoveCardRequest_14(com.fc.lami.Messages.MoveCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			w_MoveCardResponse_15(com.fc.lami.Messages.MoveCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.OverRequest) {
			w_OverRequest_16(com.fc.lami.Messages.OverRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.OverResponse) {
			w_OverResponse_17(com.fc.lami.Messages.OverResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			w_PlayerData_18(com.fc.lami.Messages.PlayerData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			w_ReadyNotify_19(com.fc.lami.Messages.ReadyNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			w_ReadyRequest_20(com.fc.lami.Messages.ReadyRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			w_ReadyResponse_21(com.fc.lami.Messages.ReadyResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			w_RepealSendCardRequest_22(com.fc.lami.Messages.RepealSendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			w_RepealSendCardResponse_23(com.fc.lami.Messages.RepealSendCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			w_RetakeCardNotify_24(com.fc.lami.Messages.RetakeCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			w_RetakeCardRequest_25(com.fc.lami.Messages.RetakeCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			w_RetakeCardResponse_26(com.fc.lami.Messages.RetakeCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			w_RoomData_27(com.fc.lami.Messages.RoomData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			w_SendCardNotify_28(com.fc.lami.Messages.SendCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			w_SendCardRequest_29(com.fc.lami.Messages.SendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			w_SendCardResponse_30(com.fc.lami.Messages.SendCardResponse(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.CardData
//	----------------------------------------------------------------------------------------------------
	function new_CardData_1() : com.fc.lami.Messages.CardData {return new com.fc.lami.Messages.CardData();}
	private function r_CardData_1(msg : com.fc.lami.Messages.CardData, input : NetDataInput) : void {
		msg.point = input.readInt();
		msg.type = input.readInt();
		msg.x = input.readInt();
		msg.y = input.readInt();
	}
	private function w_CardData_1(msg : com.fc.lami.Messages.CardData, output : NetDataOutput) : void {
		output.writeInt(msg.point);
		output.writeInt(msg.type);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.DeskData
//	----------------------------------------------------------------------------------------------------
	function new_DeskData_2() : com.fc.lami.Messages.DeskData {return new com.fc.lami.Messages.DeskData();}
	private function r_DeskData_2(msg : com.fc.lami.Messages.DeskData, input : NetDataInput) : void {
		msg.desk_id = input.readInt();
		msg.is_started = input.readBoolean();
		msg.player_E_id = input.readInt();
		msg.player_W_id = input.readInt();
		msg.player_S_id = input.readInt();
		msg.player_N_id = input.readInt();
	}
	private function w_DeskData_2(msg : com.fc.lami.Messages.DeskData, output : NetDataOutput) : void {
		output.writeInt(msg.desk_id);
		output.writeBoolean(msg.is_started);
		output.writeInt(msg.player_E_id);
		output.writeInt(msg.player_W_id);
		output.writeInt(msg.player_S_id);
		output.writeInt(msg.player_N_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	function new_EchoNotify_3() : com.fc.lami.Messages.EchoNotify {return new com.fc.lami.Messages.EchoNotify();}
	private function r_EchoNotify_3(msg : com.fc.lami.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoNotify_3(msg : com.fc.lami.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	function new_EchoRequest_4() : com.fc.lami.Messages.EchoRequest {return new com.fc.lami.Messages.EchoRequest();}
	private function r_EchoRequest_4(msg : com.fc.lami.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoRequest_4(msg : com.fc.lami.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	function new_EchoResponse_5() : com.fc.lami.Messages.EchoResponse {return new com.fc.lami.Messages.EchoResponse();}
	private function r_EchoResponse_5(msg : com.fc.lami.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoResponse_5(msg : com.fc.lami.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomNotify
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomNotify_6() : com.fc.lami.Messages.EnterRoomNotify {return new com.fc.lami.Messages.EnterRoomNotify();}
	private function r_EnterRoomNotify_6(msg : com.fc.lami.Messages.EnterRoomNotify, input : NetDataInput) : void {
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
	}
	private function w_EnterRoomNotify_6(msg : com.fc.lami.Messages.EnterRoomNotify, output : NetDataOutput) : void {
		output.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomRequest
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomRequest_7() : com.fc.lami.Messages.EnterRoomRequest {return new com.fc.lami.Messages.EnterRoomRequest();}
	private function r_EnterRoomRequest_7(msg : com.fc.lami.Messages.EnterRoomRequest, input : NetDataInput) : void {
		msg.room_no = input.readInt();
	}
	private function w_EnterRoomRequest_7(msg : com.fc.lami.Messages.EnterRoomRequest, output : NetDataOutput) : void {
		output.writeInt(msg.room_no);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomResponse
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomResponse_8() : com.fc.lami.Messages.EnterRoomResponse {return new com.fc.lami.Messages.EnterRoomResponse();}
	private function r_EnterRoomResponse_8(msg : com.fc.lami.Messages.EnterRoomResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_EnterRoomResponse_8(msg : com.fc.lami.Messages.EnterRoomResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetCardRequest_9() : com.fc.lami.Messages.GetCardRequest {return new com.fc.lami.Messages.GetCardRequest();}
	private function r_GetCardRequest_9(msg : com.fc.lami.Messages.GetCardRequest, input : NetDataInput) : void {
	}
	private function w_GetCardRequest_9(msg : com.fc.lami.Messages.GetCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetCardResponse_10() : com.fc.lami.Messages.GetCardResponse {return new com.fc.lami.Messages.GetCardResponse();}
	private function r_GetCardResponse_10(msg : com.fc.lami.Messages.GetCardResponse, input : NetDataInput) : void {
		msg.card = input.readExternal() as com.fc.lami.Messages.CardData;
	}
	private function w_GetCardResponse_10(msg : com.fc.lami.Messages.GetCardResponse, output : NetDataOutput) : void {
		output.writeExternal(msg.card);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_11() : com.fc.lami.Messages.GetTimeRequest {return new com.fc.lami.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_11(msg : com.fc.lami.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_11(msg : com.fc.lami.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_12() : com.fc.lami.Messages.GetTimeResponse {return new com.fc.lami.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_12(msg : com.fc.lami.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_12(msg : com.fc.lami.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardNotify_13() : com.fc.lami.Messages.MoveCardNotify {return new com.fc.lami.Messages.MoveCardNotify();}
	private function r_MoveCardNotify_13(msg : com.fc.lami.Messages.MoveCardNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_MoveCardNotify_13(msg : com.fc.lami.Messages.MoveCardNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardRequest_14() : com.fc.lami.Messages.MoveCardRequest {return new com.fc.lami.Messages.MoveCardRequest();}
	private function r_MoveCardRequest_14(msg : com.fc.lami.Messages.MoveCardRequest, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
	}
	private function w_MoveCardRequest_14(msg : com.fc.lami.Messages.MoveCardRequest, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardResponse_15() : com.fc.lami.Messages.MoveCardResponse {return new com.fc.lami.Messages.MoveCardResponse();}
	private function r_MoveCardResponse_15(msg : com.fc.lami.Messages.MoveCardResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
	}
	private function w_MoveCardResponse_15(msg : com.fc.lami.Messages.MoveCardResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OverRequest
//	----------------------------------------------------------------------------------------------------
	function new_OverRequest_16() : com.fc.lami.Messages.OverRequest {return new com.fc.lami.Messages.OverRequest();}
	private function r_OverRequest_16(msg : com.fc.lami.Messages.OverRequest, input : NetDataInput) : void {
	}
	private function w_OverRequest_16(msg : com.fc.lami.Messages.OverRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OverResponse
//	----------------------------------------------------------------------------------------------------
	function new_OverResponse_17() : com.fc.lami.Messages.OverResponse {return new com.fc.lami.Messages.OverResponse();}
	private function r_OverResponse_17(msg : com.fc.lami.Messages.OverResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
	}
	private function w_OverResponse_17(msg : com.fc.lami.Messages.OverResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.PlayerData
//	----------------------------------------------------------------------------------------------------
	function new_PlayerData_18() : com.fc.lami.Messages.PlayerData {return new com.fc.lami.Messages.PlayerData();}
	private function r_PlayerData_18(msg : com.fc.lami.Messages.PlayerData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.name = input.readJavaUTF();
	}
	private function w_PlayerData_18(msg : com.fc.lami.Messages.PlayerData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyNotify
//	----------------------------------------------------------------------------------------------------
	function new_ReadyNotify_19() : com.fc.lami.Messages.ReadyNotify {return new com.fc.lami.Messages.ReadyNotify();}
	private function r_ReadyNotify_19(msg : com.fc.lami.Messages.ReadyNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_ReadyNotify_19(msg : com.fc.lami.Messages.ReadyNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyRequest
//	----------------------------------------------------------------------------------------------------
	function new_ReadyRequest_20() : com.fc.lami.Messages.ReadyRequest {return new com.fc.lami.Messages.ReadyRequest();}
	private function r_ReadyRequest_20(msg : com.fc.lami.Messages.ReadyRequest, input : NetDataInput) : void {
	}
	private function w_ReadyRequest_20(msg : com.fc.lami.Messages.ReadyRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyResponse
//	----------------------------------------------------------------------------------------------------
	function new_ReadyResponse_21() : com.fc.lami.Messages.ReadyResponse {return new com.fc.lami.Messages.ReadyResponse();}
	private function r_ReadyResponse_21(msg : com.fc.lami.Messages.ReadyResponse, input : NetDataInput) : void {
	}
	private function w_ReadyResponse_21(msg : com.fc.lami.Messages.ReadyResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardRequest_22() : com.fc.lami.Messages.RepealSendCardRequest {return new com.fc.lami.Messages.RepealSendCardRequest();}
	private function r_RepealSendCardRequest_22(msg : com.fc.lami.Messages.RepealSendCardRequest, input : NetDataInput) : void {
	}
	private function w_RepealSendCardRequest_22(msg : com.fc.lami.Messages.RepealSendCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardResponse_23() : com.fc.lami.Messages.RepealSendCardResponse {return new com.fc.lami.Messages.RepealSendCardResponse();}
	private function r_RepealSendCardResponse_23(msg : com.fc.lami.Messages.RepealSendCardResponse, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_RepealSendCardResponse_23(msg : com.fc.lami.Messages.RepealSendCardResponse, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardNotify_24() : com.fc.lami.Messages.RetakeCardNotify {return new com.fc.lami.Messages.RetakeCardNotify();}
	private function r_RetakeCardNotify_24(msg : com.fc.lami.Messages.RetakeCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.cards = input.readExternalArray();
	}
	private function w_RetakeCardNotify_24(msg : com.fc.lami.Messages.RetakeCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardRequest_25() : com.fc.lami.Messages.RetakeCardRequest {return new com.fc.lami.Messages.RetakeCardRequest();}
	private function r_RetakeCardRequest_25(msg : com.fc.lami.Messages.RetakeCardRequest, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_RetakeCardRequest_25(msg : com.fc.lami.Messages.RetakeCardRequest, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardResponse_26() : com.fc.lami.Messages.RetakeCardResponse {return new com.fc.lami.Messages.RetakeCardResponse();}
	private function r_RetakeCardResponse_26(msg : com.fc.lami.Messages.RetakeCardResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
	}
	private function w_RetakeCardResponse_26(msg : com.fc.lami.Messages.RetakeCardResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RoomData
//	----------------------------------------------------------------------------------------------------
	function new_RoomData_27() : com.fc.lami.Messages.RoomData {return new com.fc.lami.Messages.RoomData();}
	private function r_RoomData_27(msg : com.fc.lami.Messages.RoomData, input : NetDataInput) : void {
		msg.room_id = input.readInt();
		msg.desks = input.readExternalArray();
		msg.players = input.readExternalArray();
	}
	private function w_RoomData_27(msg : com.fc.lami.Messages.RoomData, output : NetDataOutput) : void {
		output.writeInt(msg.room_id);
		output.writeExternalArray(msg.desks);
		output.writeExternalArray(msg.players);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_SendCardNotify_28() : com.fc.lami.Messages.SendCardNotify {return new com.fc.lami.Messages.SendCardNotify();}
	private function r_SendCardNotify_28(msg : com.fc.lami.Messages.SendCardNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
		msg.player_id = input.readInt();
	}
	private function w_SendCardNotify_28(msg : com.fc.lami.Messages.SendCardNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_SendCardRequest_29() : com.fc.lami.Messages.SendCardRequest {return new com.fc.lami.Messages.SendCardRequest();}
	private function r_SendCardRequest_29(msg : com.fc.lami.Messages.SendCardRequest, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_SendCardRequest_29(msg : com.fc.lami.Messages.SendCardRequest, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_SendCardResponse_30() : com.fc.lami.Messages.SendCardResponse {return new com.fc.lami.Messages.SendCardResponse();}
	private function r_SendCardResponse_30(msg : com.fc.lami.Messages.SendCardResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
	}
	private function w_SendCardResponse_30(msg : com.fc.lami.Messages.SendCardResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
	}



	}

}