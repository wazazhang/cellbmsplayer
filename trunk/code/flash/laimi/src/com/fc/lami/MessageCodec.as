package com.fc.lami
{
	import com.net.client.MessageFactory;
	import com.net.client.Message;
	import com.net.client.NetDataInput;
	import com.net.client.NetDataOutput;
	import com.net.client.NetDataTypes;
	import com.fc.lami.Messages.*;

	/**
	 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
	 */
	public class MessageCodec implements MessageFactory
	{
		public function	getType(msg : Message) : int 
		{
			if (msg is com.fc.lami.Messages.CardData) return 1;
			if (msg is com.fc.lami.Messages.CardStackChangeNotify) return 2;
			if (msg is com.fc.lami.Messages.DeskData) return 3;
			if (msg is com.fc.lami.Messages.EchoNotify) return 4;
			if (msg is com.fc.lami.Messages.EchoRequest) return 5;
			if (msg is com.fc.lami.Messages.EchoResponse) return 6;
			if (msg is com.fc.lami.Messages.EnterDeskNotify) return 7;
			if (msg is com.fc.lami.Messages.EnterDeskRequest) return 8;
			if (msg is com.fc.lami.Messages.EnterDeskResponse) return 9;
			if (msg is com.fc.lami.Messages.EnterRoomNotify) return 10;
			if (msg is com.fc.lami.Messages.EnterRoomRequest) return 11;
			if (msg is com.fc.lami.Messages.EnterRoomResponse) return 12;
			if (msg is com.fc.lami.Messages.ExitRoomNotify) return 13;
			if (msg is com.fc.lami.Messages.ExitRoomRequest) return 14;
			if (msg is com.fc.lami.Messages.ExitRoomResponse) return 15;
			if (msg is com.fc.lami.Messages.GameStartNotify) return 16;
			if (msg is com.fc.lami.Messages.GetCardNotify) return 17;
			if (msg is com.fc.lami.Messages.GetCardRequest) return 18;
			if (msg is com.fc.lami.Messages.GetCardResponse) return 19;
			if (msg is com.fc.lami.Messages.GetTimeRequest) return 20;
			if (msg is com.fc.lami.Messages.GetTimeResponse) return 21;
			if (msg is com.fc.lami.Messages.LeaveDeskNotify) return 22;
			if (msg is com.fc.lami.Messages.LeaveDeskRequest) return 23;
			if (msg is com.fc.lami.Messages.LeaveDeskResponse) return 24;
			if (msg is com.fc.lami.Messages.LoginRequest) return 25;
			if (msg is com.fc.lami.Messages.LoginResponse) return 26;
			if (msg is com.fc.lami.Messages.LogoutRequest) return 27;
			if (msg is com.fc.lami.Messages.MoveCardNotify) return 28;
			if (msg is com.fc.lami.Messages.MoveCardRequest) return 29;
			if (msg is com.fc.lami.Messages.MoveCardResponse) return 30;
			if (msg is com.fc.lami.Messages.OpenIceNotify) return 31;
			if (msg is com.fc.lami.Messages.PlayerData) return 32;
			if (msg is com.fc.lami.Messages.ReadyNotify) return 33;
			if (msg is com.fc.lami.Messages.ReadyRequest) return 34;
			if (msg is com.fc.lami.Messages.ReadyResponse) return 35;
			if (msg is com.fc.lami.Messages.RepealSendCardNotify) return 36;
			if (msg is com.fc.lami.Messages.RepealSendCardRequest) return 37;
			if (msg is com.fc.lami.Messages.RepealSendCardResponse) return 38;
			if (msg is com.fc.lami.Messages.RetakeCardNotify) return 39;
			if (msg is com.fc.lami.Messages.RetakeCardRequest) return 40;
			if (msg is com.fc.lami.Messages.RetakeCardResponse) return 41;
			if (msg is com.fc.lami.Messages.RoomData) return 42;
			if (msg is com.fc.lami.Messages.SendCardNotify) return 43;
			if (msg is com.fc.lami.Messages.SendCardRequest) return 44;
			if (msg is com.fc.lami.Messages.SendCardResponse) return 45;
			if (msg is com.fc.lami.Messages.SubmitRequest) return 46;
			if (msg is com.fc.lami.Messages.SubmitResponse) return 47;
			if (msg is com.fc.lami.Messages.TurnEndNotify) return 48;
			if (msg is com.fc.lami.Messages.TurnStartNotify) return 49;

			return 0;
		}
		
		public function	createMessage(type : int) : Message
		{
			switch(type)
			{
			case 1 : return new com.fc.lami.Messages.CardData;
			case 2 : return new com.fc.lami.Messages.CardStackChangeNotify;
			case 3 : return new com.fc.lami.Messages.DeskData;
			case 4 : return new com.fc.lami.Messages.EchoNotify;
			case 5 : return new com.fc.lami.Messages.EchoRequest;
			case 6 : return new com.fc.lami.Messages.EchoResponse;
			case 7 : return new com.fc.lami.Messages.EnterDeskNotify;
			case 8 : return new com.fc.lami.Messages.EnterDeskRequest;
			case 9 : return new com.fc.lami.Messages.EnterDeskResponse;
			case 10 : return new com.fc.lami.Messages.EnterRoomNotify;
			case 11 : return new com.fc.lami.Messages.EnterRoomRequest;
			case 12 : return new com.fc.lami.Messages.EnterRoomResponse;
			case 13 : return new com.fc.lami.Messages.ExitRoomNotify;
			case 14 : return new com.fc.lami.Messages.ExitRoomRequest;
			case 15 : return new com.fc.lami.Messages.ExitRoomResponse;
			case 16 : return new com.fc.lami.Messages.GameStartNotify;
			case 17 : return new com.fc.lami.Messages.GetCardNotify;
			case 18 : return new com.fc.lami.Messages.GetCardRequest;
			case 19 : return new com.fc.lami.Messages.GetCardResponse;
			case 20 : return new com.fc.lami.Messages.GetTimeRequest;
			case 21 : return new com.fc.lami.Messages.GetTimeResponse;
			case 22 : return new com.fc.lami.Messages.LeaveDeskNotify;
			case 23 : return new com.fc.lami.Messages.LeaveDeskRequest;
			case 24 : return new com.fc.lami.Messages.LeaveDeskResponse;
			case 25 : return new com.fc.lami.Messages.LoginRequest;
			case 26 : return new com.fc.lami.Messages.LoginResponse;
			case 27 : return new com.fc.lami.Messages.LogoutRequest;
			case 28 : return new com.fc.lami.Messages.MoveCardNotify;
			case 29 : return new com.fc.lami.Messages.MoveCardRequest;
			case 30 : return new com.fc.lami.Messages.MoveCardResponse;
			case 31 : return new com.fc.lami.Messages.OpenIceNotify;
			case 32 : return new com.fc.lami.Messages.PlayerData;
			case 33 : return new com.fc.lami.Messages.ReadyNotify;
			case 34 : return new com.fc.lami.Messages.ReadyRequest;
			case 35 : return new com.fc.lami.Messages.ReadyResponse;
			case 36 : return new com.fc.lami.Messages.RepealSendCardNotify;
			case 37 : return new com.fc.lami.Messages.RepealSendCardRequest;
			case 38 : return new com.fc.lami.Messages.RepealSendCardResponse;
			case 39 : return new com.fc.lami.Messages.RetakeCardNotify;
			case 40 : return new com.fc.lami.Messages.RetakeCardRequest;
			case 41 : return new com.fc.lami.Messages.RetakeCardResponse;
			case 42 : return new com.fc.lami.Messages.RoomData;
			case 43 : return new com.fc.lami.Messages.SendCardNotify;
			case 44 : return new com.fc.lami.Messages.SendCardRequest;
			case 45 : return new com.fc.lami.Messages.SendCardResponse;
			case 46 : return new com.fc.lami.Messages.SubmitRequest;
			case 47 : return new com.fc.lami.Messages.SubmitResponse;
			case 48 : return new com.fc.lami.Messages.TurnEndNotify;
			case 49 : return new com.fc.lami.Messages.TurnStartNotify;

			}
			return null;
		}
		
		public function	readExternal(msg : Message,  input : NetDataInput) : void  
		{
		if (msg is com.fc.lami.Messages.CardData) {
			r_CardData_1(com.fc.lami.Messages.CardData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.CardStackChangeNotify) {
			r_CardStackChangeNotify_2(com.fc.lami.Messages.CardStackChangeNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.DeskData) {
			r_DeskData_3(com.fc.lami.Messages.DeskData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoNotify) {
			r_EchoNotify_4(com.fc.lami.Messages.EchoNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			r_EchoRequest_5(com.fc.lami.Messages.EchoRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			r_EchoResponse_6(com.fc.lami.Messages.EchoResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskNotify) {
			r_EnterDeskNotify_7(com.fc.lami.Messages.EnterDeskNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskRequest) {
			r_EnterDeskRequest_8(com.fc.lami.Messages.EnterDeskRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskResponse) {
			r_EnterDeskResponse_9(com.fc.lami.Messages.EnterDeskResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomNotify) {
			r_EnterRoomNotify_10(com.fc.lami.Messages.EnterRoomNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomRequest) {
			r_EnterRoomRequest_11(com.fc.lami.Messages.EnterRoomRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomResponse) {
			r_EnterRoomResponse_12(com.fc.lami.Messages.EnterRoomResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomNotify) {
			r_ExitRoomNotify_13(com.fc.lami.Messages.ExitRoomNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomRequest) {
			r_ExitRoomRequest_14(com.fc.lami.Messages.ExitRoomRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomResponse) {
			r_ExitRoomResponse_15(com.fc.lami.Messages.ExitRoomResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameStartNotify) {
			r_GameStartNotify_16(com.fc.lami.Messages.GameStartNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardNotify) {
			r_GetCardNotify_17(com.fc.lami.Messages.GetCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			r_GetCardRequest_18(com.fc.lami.Messages.GetCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			r_GetCardResponse_19(com.fc.lami.Messages.GetCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			r_GetTimeRequest_20(com.fc.lami.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			r_GetTimeResponse_21(com.fc.lami.Messages.GetTimeResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskNotify) {
			r_LeaveDeskNotify_22(com.fc.lami.Messages.LeaveDeskNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskRequest) {
			r_LeaveDeskRequest_23(com.fc.lami.Messages.LeaveDeskRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskResponse) {
			r_LeaveDeskResponse_24(com.fc.lami.Messages.LeaveDeskResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LoginRequest) {
			r_LoginRequest_25(com.fc.lami.Messages.LoginRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LoginResponse) {
			r_LoginResponse_26(com.fc.lami.Messages.LoginResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LogoutRequest) {
			r_LogoutRequest_27(com.fc.lami.Messages.LogoutRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			r_MoveCardNotify_28(com.fc.lami.Messages.MoveCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			r_MoveCardRequest_29(com.fc.lami.Messages.MoveCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			r_MoveCardResponse_30(com.fc.lami.Messages.MoveCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.OpenIceNotify) {
			r_OpenIceNotify_31(com.fc.lami.Messages.OpenIceNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			r_PlayerData_32(com.fc.lami.Messages.PlayerData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			r_ReadyNotify_33(com.fc.lami.Messages.ReadyNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			r_ReadyRequest_34(com.fc.lami.Messages.ReadyRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			r_ReadyResponse_35(com.fc.lami.Messages.ReadyResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardNotify) {
			r_RepealSendCardNotify_36(com.fc.lami.Messages.RepealSendCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			r_RepealSendCardRequest_37(com.fc.lami.Messages.RepealSendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			r_RepealSendCardResponse_38(com.fc.lami.Messages.RepealSendCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			r_RetakeCardNotify_39(com.fc.lami.Messages.RetakeCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			r_RetakeCardRequest_40(com.fc.lami.Messages.RetakeCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			r_RetakeCardResponse_41(com.fc.lami.Messages.RetakeCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			r_RoomData_42(com.fc.lami.Messages.RoomData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			r_SendCardNotify_43(com.fc.lami.Messages.SendCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			r_SendCardRequest_44(com.fc.lami.Messages.SendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			r_SendCardResponse_45(com.fc.lami.Messages.SendCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SubmitRequest) {
			r_SubmitRequest_46(com.fc.lami.Messages.SubmitRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SubmitResponse) {
			r_SubmitResponse_47(com.fc.lami.Messages.SubmitResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.TurnEndNotify) {
			r_TurnEndNotify_48(com.fc.lami.Messages.TurnEndNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.TurnStartNotify) {
			r_TurnStartNotify_49(com.fc.lami.Messages.TurnStartNotify(msg), input); return;
		}

		}
		
		public function	writeExternal(msg : Message, output : NetDataOutput) : void  
		{
		if (msg is com.fc.lami.Messages.CardData) {
			w_CardData_1(com.fc.lami.Messages.CardData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.CardStackChangeNotify) {
			w_CardStackChangeNotify_2(com.fc.lami.Messages.CardStackChangeNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.DeskData) {
			w_DeskData_3(com.fc.lami.Messages.DeskData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoNotify) {
			w_EchoNotify_4(com.fc.lami.Messages.EchoNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoRequest) {
			w_EchoRequest_5(com.fc.lami.Messages.EchoRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EchoResponse) {
			w_EchoResponse_6(com.fc.lami.Messages.EchoResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskNotify) {
			w_EnterDeskNotify_7(com.fc.lami.Messages.EnterDeskNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskRequest) {
			w_EnterDeskRequest_8(com.fc.lami.Messages.EnterDeskRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterDeskResponse) {
			w_EnterDeskResponse_9(com.fc.lami.Messages.EnterDeskResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomNotify) {
			w_EnterRoomNotify_10(com.fc.lami.Messages.EnterRoomNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomRequest) {
			w_EnterRoomRequest_11(com.fc.lami.Messages.EnterRoomRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.EnterRoomResponse) {
			w_EnterRoomResponse_12(com.fc.lami.Messages.EnterRoomResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomNotify) {
			w_ExitRoomNotify_13(com.fc.lami.Messages.ExitRoomNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomRequest) {
			w_ExitRoomRequest_14(com.fc.lami.Messages.ExitRoomRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ExitRoomResponse) {
			w_ExitRoomResponse_15(com.fc.lami.Messages.ExitRoomResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameStartNotify) {
			w_GameStartNotify_16(com.fc.lami.Messages.GameStartNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardNotify) {
			w_GetCardNotify_17(com.fc.lami.Messages.GetCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			w_GetCardRequest_18(com.fc.lami.Messages.GetCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			w_GetCardResponse_19(com.fc.lami.Messages.GetCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			w_GetTimeRequest_20(com.fc.lami.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			w_GetTimeResponse_21(com.fc.lami.Messages.GetTimeResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskNotify) {
			w_LeaveDeskNotify_22(com.fc.lami.Messages.LeaveDeskNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskRequest) {
			w_LeaveDeskRequest_23(com.fc.lami.Messages.LeaveDeskRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskResponse) {
			w_LeaveDeskResponse_24(com.fc.lami.Messages.LeaveDeskResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LoginRequest) {
			w_LoginRequest_25(com.fc.lami.Messages.LoginRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LoginResponse) {
			w_LoginResponse_26(com.fc.lami.Messages.LoginResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LogoutRequest) {
			w_LogoutRequest_27(com.fc.lami.Messages.LogoutRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			w_MoveCardNotify_28(com.fc.lami.Messages.MoveCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			w_MoveCardRequest_29(com.fc.lami.Messages.MoveCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			w_MoveCardResponse_30(com.fc.lami.Messages.MoveCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.OpenIceNotify) {
			w_OpenIceNotify_31(com.fc.lami.Messages.OpenIceNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			w_PlayerData_32(com.fc.lami.Messages.PlayerData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			w_ReadyNotify_33(com.fc.lami.Messages.ReadyNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			w_ReadyRequest_34(com.fc.lami.Messages.ReadyRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			w_ReadyResponse_35(com.fc.lami.Messages.ReadyResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardNotify) {
			w_RepealSendCardNotify_36(com.fc.lami.Messages.RepealSendCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			w_RepealSendCardRequest_37(com.fc.lami.Messages.RepealSendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			w_RepealSendCardResponse_38(com.fc.lami.Messages.RepealSendCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			w_RetakeCardNotify_39(com.fc.lami.Messages.RetakeCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			w_RetakeCardRequest_40(com.fc.lami.Messages.RetakeCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			w_RetakeCardResponse_41(com.fc.lami.Messages.RetakeCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			w_RoomData_42(com.fc.lami.Messages.RoomData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			w_SendCardNotify_43(com.fc.lami.Messages.SendCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			w_SendCardRequest_44(com.fc.lami.Messages.SendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			w_SendCardResponse_45(com.fc.lami.Messages.SendCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SubmitRequest) {
			w_SubmitRequest_46(com.fc.lami.Messages.SubmitRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SubmitResponse) {
			w_SubmitResponse_47(com.fc.lami.Messages.SubmitResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.TurnEndNotify) {
			w_TurnEndNotify_48(com.fc.lami.Messages.TurnEndNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.TurnStartNotify) {
			w_TurnStartNotify_49(com.fc.lami.Messages.TurnStartNotify(msg), output); return;
		}

		}
		
//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.CardData
//	----------------------------------------------------------------------------------------------------
	function new_CardData_1() : com.fc.lami.Messages.CardData {return new com.fc.lami.Messages.CardData();}
	private function r_CardData_1(msg : com.fc.lami.Messages.CardData, input : NetDataInput) : void {
		msg.id = input.readInt();
		msg.point = input.readInt();
		msg.type = input.readInt();
	}
	private function w_CardData_1(msg : com.fc.lami.Messages.CardData, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeInt(msg.point);
		output.writeInt(msg.type);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.CardStackChangeNotify
//	----------------------------------------------------------------------------------------------------
	function new_CardStackChangeNotify_2() : com.fc.lami.Messages.CardStackChangeNotify {return new com.fc.lami.Messages.CardStackChangeNotify();}
	private function r_CardStackChangeNotify_2(msg : com.fc.lami.Messages.CardStackChangeNotify, input : NetDataInput) : void {
		msg.card_stack_number = input.readInt();
	}
	private function w_CardStackChangeNotify_2(msg : com.fc.lami.Messages.CardStackChangeNotify, output : NetDataOutput) : void {
		output.writeInt(msg.card_stack_number);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.DeskData
//	----------------------------------------------------------------------------------------------------
	function new_DeskData_3() : com.fc.lami.Messages.DeskData {return new com.fc.lami.Messages.DeskData();}
	private function r_DeskData_3(msg : com.fc.lami.Messages.DeskData, input : NetDataInput) : void {
		msg.desk_id = input.readInt();
		msg.is_started = input.readBoolean();
		msg.player_E_id = input.readInt();
		msg.player_W_id = input.readInt();
		msg.player_S_id = input.readInt();
		msg.player_N_id = input.readInt();
	}
	private function w_DeskData_3(msg : com.fc.lami.Messages.DeskData, output : NetDataOutput) : void {
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
	function new_EchoNotify_4() : com.fc.lami.Messages.EchoNotify {return new com.fc.lami.Messages.EchoNotify();}
	private function r_EchoNotify_4(msg : com.fc.lami.Messages.EchoNotify, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoNotify_4(msg : com.fc.lami.Messages.EchoNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	function new_EchoRequest_5() : com.fc.lami.Messages.EchoRequest {return new com.fc.lami.Messages.EchoRequest();}
	private function r_EchoRequest_5(msg : com.fc.lami.Messages.EchoRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoRequest_5(msg : com.fc.lami.Messages.EchoRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	function new_EchoResponse_6() : com.fc.lami.Messages.EchoResponse {return new com.fc.lami.Messages.EchoResponse();}
	private function r_EchoResponse_6(msg : com.fc.lami.Messages.EchoResponse, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_EchoResponse_6(msg : com.fc.lami.Messages.EchoResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskNotify
//	----------------------------------------------------------------------------------------------------
	function new_EnterDeskNotify_7() : com.fc.lami.Messages.EnterDeskNotify {return new com.fc.lami.Messages.EnterDeskNotify();}
	private function r_EnterDeskNotify_7(msg : com.fc.lami.Messages.EnterDeskNotify, input : NetDataInput) : void {
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
	}
	private function w_EnterDeskNotify_7(msg : com.fc.lami.Messages.EnterDeskNotify, output : NetDataOutput) : void {
		output.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskRequest
//	----------------------------------------------------------------------------------------------------
	function new_EnterDeskRequest_8() : com.fc.lami.Messages.EnterDeskRequest {return new com.fc.lami.Messages.EnterDeskRequest();}
	private function r_EnterDeskRequest_8(msg : com.fc.lami.Messages.EnterDeskRequest, input : NetDataInput) : void {
		msg.desk_No = input.readInt();
		msg.seat = input.readInt();
	}
	private function w_EnterDeskRequest_8(msg : com.fc.lami.Messages.EnterDeskRequest, output : NetDataOutput) : void {
		output.writeInt(msg.desk_No);
		output.writeInt(msg.seat);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskResponse
//	----------------------------------------------------------------------------------------------------
	function new_EnterDeskResponse_9() : com.fc.lami.Messages.EnterDeskResponse {return new com.fc.lami.Messages.EnterDeskResponse();}
	private function r_EnterDeskResponse_9(msg : com.fc.lami.Messages.EnterDeskResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_EnterDeskResponse_9(msg : com.fc.lami.Messages.EnterDeskResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomNotify
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomNotify_10() : com.fc.lami.Messages.EnterRoomNotify {return new com.fc.lami.Messages.EnterRoomNotify();}
	private function r_EnterRoomNotify_10(msg : com.fc.lami.Messages.EnterRoomNotify, input : NetDataInput) : void {
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
	}
	private function w_EnterRoomNotify_10(msg : com.fc.lami.Messages.EnterRoomNotify, output : NetDataOutput) : void {
		output.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomRequest
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomRequest_11() : com.fc.lami.Messages.EnterRoomRequest {return new com.fc.lami.Messages.EnterRoomRequest();}
	private function r_EnterRoomRequest_11(msg : com.fc.lami.Messages.EnterRoomRequest, input : NetDataInput) : void {
		msg.room_no = input.readInt();
	}
	private function w_EnterRoomRequest_11(msg : com.fc.lami.Messages.EnterRoomRequest, output : NetDataOutput) : void {
		output.writeInt(msg.room_no);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomResponse
//	----------------------------------------------------------------------------------------------------
	function new_EnterRoomResponse_12() : com.fc.lami.Messages.EnterRoomResponse {return new com.fc.lami.Messages.EnterRoomResponse();}
	private function r_EnterRoomResponse_12(msg : com.fc.lami.Messages.EnterRoomResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
		msg.room = input.readExternal() as com.fc.lami.Messages.RoomData;
	}
	private function w_EnterRoomResponse_12(msg : com.fc.lami.Messages.EnterRoomResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
		output.writeExternal(msg.room);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomNotify
//	----------------------------------------------------------------------------------------------------
	function new_ExitRoomNotify_13() : com.fc.lami.Messages.ExitRoomNotify {return new com.fc.lami.Messages.ExitRoomNotify();}
	private function r_ExitRoomNotify_13(msg : com.fc.lami.Messages.ExitRoomNotify, input : NetDataInput) : void {
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
	}
	private function w_ExitRoomNotify_13(msg : com.fc.lami.Messages.ExitRoomNotify, output : NetDataOutput) : void {
		output.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomRequest
//	----------------------------------------------------------------------------------------------------
	function new_ExitRoomRequest_14() : com.fc.lami.Messages.ExitRoomRequest {return new com.fc.lami.Messages.ExitRoomRequest();}
	private function r_ExitRoomRequest_14(msg : com.fc.lami.Messages.ExitRoomRequest, input : NetDataInput) : void {
	}
	private function w_ExitRoomRequest_14(msg : com.fc.lami.Messages.ExitRoomRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomResponse
//	----------------------------------------------------------------------------------------------------
	function new_ExitRoomResponse_15() : com.fc.lami.Messages.ExitRoomResponse {return new com.fc.lami.Messages.ExitRoomResponse();}
	private function r_ExitRoomResponse_15(msg : com.fc.lami.Messages.ExitRoomResponse, input : NetDataInput) : void {
	}
	private function w_ExitRoomResponse_15(msg : com.fc.lami.Messages.ExitRoomResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameStartNotify
//	----------------------------------------------------------------------------------------------------
	function new_GameStartNotify_16() : com.fc.lami.Messages.GameStartNotify {return new com.fc.lami.Messages.GameStartNotify();}
	private function r_GameStartNotify_16(msg : com.fc.lami.Messages.GameStartNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_GameStartNotify_16(msg : com.fc.lami.Messages.GameStartNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_GetCardNotify_17() : com.fc.lami.Messages.GetCardNotify {return new com.fc.lami.Messages.GetCardNotify();}
	private function r_GetCardNotify_17(msg : com.fc.lami.Messages.GetCardNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_GetCardNotify_17(msg : com.fc.lami.Messages.GetCardNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetCardRequest_18() : com.fc.lami.Messages.GetCardRequest {return new com.fc.lami.Messages.GetCardRequest();}
	private function r_GetCardRequest_18(msg : com.fc.lami.Messages.GetCardRequest, input : NetDataInput) : void {
	}
	private function w_GetCardRequest_18(msg : com.fc.lami.Messages.GetCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetCardResponse_19() : com.fc.lami.Messages.GetCardResponse {return new com.fc.lami.Messages.GetCardResponse();}
	private function r_GetCardResponse_19(msg : com.fc.lami.Messages.GetCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_GetCardResponse_19(msg : com.fc.lami.Messages.GetCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_20() : com.fc.lami.Messages.GetTimeRequest {return new com.fc.lami.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_20(msg : com.fc.lami.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_20(msg : com.fc.lami.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_21() : com.fc.lami.Messages.GetTimeResponse {return new com.fc.lami.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_21(msg : com.fc.lami.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_21(msg : com.fc.lami.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskNotify
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskNotify_22() : com.fc.lami.Messages.LeaveDeskNotify {return new com.fc.lami.Messages.LeaveDeskNotify();}
	private function r_LeaveDeskNotify_22(msg : com.fc.lami.Messages.LeaveDeskNotify, input : NetDataInput) : void {
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
	}
	private function w_LeaveDeskNotify_22(msg : com.fc.lami.Messages.LeaveDeskNotify, output : NetDataOutput) : void {
		output.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskRequest
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskRequest_23() : com.fc.lami.Messages.LeaveDeskRequest {return new com.fc.lami.Messages.LeaveDeskRequest();}
	private function r_LeaveDeskRequest_23(msg : com.fc.lami.Messages.LeaveDeskRequest, input : NetDataInput) : void {
	}
	private function w_LeaveDeskRequest_23(msg : com.fc.lami.Messages.LeaveDeskRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskResponse
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskResponse_24() : com.fc.lami.Messages.LeaveDeskResponse {return new com.fc.lami.Messages.LeaveDeskResponse();}
	private function r_LeaveDeskResponse_24(msg : com.fc.lami.Messages.LeaveDeskResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_LeaveDeskResponse_24(msg : com.fc.lami.Messages.LeaveDeskResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	function new_LoginRequest_25() : com.fc.lami.Messages.LoginRequest {return new com.fc.lami.Messages.LoginRequest();}
	private function r_LoginRequest_25(msg : com.fc.lami.Messages.LoginRequest, input : NetDataInput) : void {
		msg.name = input.readJavaUTF();
	}
	private function w_LoginRequest_25(msg : com.fc.lami.Messages.LoginRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	function new_LoginResponse_26() : com.fc.lami.Messages.LoginResponse {return new com.fc.lami.Messages.LoginResponse();}
	private function r_LoginResponse_26(msg : com.fc.lami.Messages.LoginResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
		msg.rooms = input.readExternalArray();
	}
	private function w_LoginResponse_26(msg : com.fc.lami.Messages.LoginResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
		output.writeExternalArray(msg.rooms);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	function new_LogoutRequest_27() : com.fc.lami.Messages.LogoutRequest {return new com.fc.lami.Messages.LogoutRequest();}
	private function r_LogoutRequest_27(msg : com.fc.lami.Messages.LogoutRequest, input : NetDataInput) : void {
	}
	private function w_LogoutRequest_27(msg : com.fc.lami.Messages.LogoutRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardNotify_28() : com.fc.lami.Messages.MoveCardNotify {return new com.fc.lami.Messages.MoveCardNotify();}
	private function r_MoveCardNotify_28(msg : com.fc.lami.Messages.MoveCardNotify, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_MoveCardNotify_28(msg : com.fc.lami.Messages.MoveCardNotify, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardRequest_29() : com.fc.lami.Messages.MoveCardRequest {return new com.fc.lami.Messages.MoveCardRequest();}
	private function r_MoveCardRequest_29(msg : com.fc.lami.Messages.MoveCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
	}
	private function w_MoveCardRequest_29(msg : com.fc.lami.Messages.MoveCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardResponse_30() : com.fc.lami.Messages.MoveCardResponse {return new com.fc.lami.Messages.MoveCardResponse();}
	private function r_MoveCardResponse_30(msg : com.fc.lami.Messages.MoveCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_MoveCardResponse_30(msg : com.fc.lami.Messages.MoveCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OpenIceNotify
//	----------------------------------------------------------------------------------------------------
	function new_OpenIceNotify_31() : com.fc.lami.Messages.OpenIceNotify {return new com.fc.lami.Messages.OpenIceNotify();}
	private function r_OpenIceNotify_31(msg : com.fc.lami.Messages.OpenIceNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_OpenIceNotify_31(msg : com.fc.lami.Messages.OpenIceNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.PlayerData
//	----------------------------------------------------------------------------------------------------
	function new_PlayerData_32() : com.fc.lami.Messages.PlayerData {return new com.fc.lami.Messages.PlayerData();}
	private function r_PlayerData_32(msg : com.fc.lami.Messages.PlayerData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.name = input.readJavaUTF();
	}
	private function w_PlayerData_32(msg : com.fc.lami.Messages.PlayerData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyNotify
//	----------------------------------------------------------------------------------------------------
	function new_ReadyNotify_33() : com.fc.lami.Messages.ReadyNotify {return new com.fc.lami.Messages.ReadyNotify();}
	private function r_ReadyNotify_33(msg : com.fc.lami.Messages.ReadyNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.isReady = input.readBoolean();
	}
	private function w_ReadyNotify_33(msg : com.fc.lami.Messages.ReadyNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyRequest
//	----------------------------------------------------------------------------------------------------
	function new_ReadyRequest_34() : com.fc.lami.Messages.ReadyRequest {return new com.fc.lami.Messages.ReadyRequest();}
	private function r_ReadyRequest_34(msg : com.fc.lami.Messages.ReadyRequest, input : NetDataInput) : void {
		msg.isReady = input.readBoolean();
	}
	private function w_ReadyRequest_34(msg : com.fc.lami.Messages.ReadyRequest, output : NetDataOutput) : void {
		output.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyResponse
//	----------------------------------------------------------------------------------------------------
	function new_ReadyResponse_35() : com.fc.lami.Messages.ReadyResponse {return new com.fc.lami.Messages.ReadyResponse();}
	private function r_ReadyResponse_35(msg : com.fc.lami.Messages.ReadyResponse, input : NetDataInput) : void {
	}
	private function w_ReadyResponse_35(msg : com.fc.lami.Messages.ReadyResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardNotify_36() : com.fc.lami.Messages.RepealSendCardNotify {return new com.fc.lami.Messages.RepealSendCardNotify();}
	private function r_RepealSendCardNotify_36(msg : com.fc.lami.Messages.RepealSendCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.cds = input.readExternalArray();
	}
	private function w_RepealSendCardNotify_36(msg : com.fc.lami.Messages.RepealSendCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeExternalArray(msg.cds);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardRequest_37() : com.fc.lami.Messages.RepealSendCardRequest {return new com.fc.lami.Messages.RepealSendCardRequest();}
	private function r_RepealSendCardRequest_37(msg : com.fc.lami.Messages.RepealSendCardRequest, input : NetDataInput) : void {
	}
	private function w_RepealSendCardRequest_37(msg : com.fc.lami.Messages.RepealSendCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardResponse_38() : com.fc.lami.Messages.RepealSendCardResponse {return new com.fc.lami.Messages.RepealSendCardResponse();}
	private function r_RepealSendCardResponse_38(msg : com.fc.lami.Messages.RepealSendCardResponse, input : NetDataInput) : void {
	}
	private function w_RepealSendCardResponse_38(msg : com.fc.lami.Messages.RepealSendCardResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardNotify_39() : com.fc.lami.Messages.RetakeCardNotify {return new com.fc.lami.Messages.RetakeCardNotify();}
	private function r_RetakeCardNotify_39(msg : com.fc.lami.Messages.RetakeCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.x = input.readInt();
		msg.y = input.readInt();
		msg.n = input.readInt();
	}
	private function w_RetakeCardNotify_39(msg : com.fc.lami.Messages.RetakeCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
		output.writeInt(msg.n);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardRequest_40() : com.fc.lami.Messages.RetakeCardRequest {return new com.fc.lami.Messages.RetakeCardRequest();}
	private function r_RetakeCardRequest_40(msg : com.fc.lami.Messages.RetakeCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
	}
	private function w_RetakeCardRequest_40(msg : com.fc.lami.Messages.RetakeCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardResponse_41() : com.fc.lami.Messages.RetakeCardResponse {return new com.fc.lami.Messages.RetakeCardResponse();}
	private function r_RetakeCardResponse_41(msg : com.fc.lami.Messages.RetakeCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_RetakeCardResponse_41(msg : com.fc.lami.Messages.RetakeCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RoomData
//	----------------------------------------------------------------------------------------------------
	function new_RoomData_42() : com.fc.lami.Messages.RoomData {return new com.fc.lami.Messages.RoomData();}
	private function r_RoomData_42(msg : com.fc.lami.Messages.RoomData, input : NetDataInput) : void {
		msg.room_id = input.readInt();
		msg.desks = input.readExternalArray();
		msg.players = input.readExternalArray();
	}
	private function w_RoomData_42(msg : com.fc.lami.Messages.RoomData, output : NetDataOutput) : void {
		output.writeInt(msg.room_id);
		output.writeExternalArray(msg.desks);
		output.writeExternalArray(msg.players);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_SendCardNotify_43() : com.fc.lami.Messages.SendCardNotify {return new com.fc.lami.Messages.SendCardNotify();}
	private function r_SendCardNotify_43(msg : com.fc.lami.Messages.SendCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.cards = input.readExternalArray();
		msg.x = input.readInt();
		msg.y = input.readInt();
	}
	private function w_SendCardNotify_43(msg : com.fc.lami.Messages.SendCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeExternalArray(msg.cards);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_SendCardRequest_44() : com.fc.lami.Messages.SendCardRequest {return new com.fc.lami.Messages.SendCardRequest();}
	private function r_SendCardRequest_44(msg : com.fc.lami.Messages.SendCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.x = input.readInt();
		msg.y = input.readInt();
	}
	private function w_SendCardRequest_44(msg : com.fc.lami.Messages.SendCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_SendCardResponse_45() : com.fc.lami.Messages.SendCardResponse {return new com.fc.lami.Messages.SendCardResponse();}
	private function r_SendCardResponse_45(msg : com.fc.lami.Messages.SendCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SendCardResponse_45(msg : com.fc.lami.Messages.SendCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitRequest
//	----------------------------------------------------------------------------------------------------
	function new_SubmitRequest_46() : com.fc.lami.Messages.SubmitRequest {return new com.fc.lami.Messages.SubmitRequest();}
	private function r_SubmitRequest_46(msg : com.fc.lami.Messages.SubmitRequest, input : NetDataInput) : void {
	}
	private function w_SubmitRequest_46(msg : com.fc.lami.Messages.SubmitRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitResponse
//	----------------------------------------------------------------------------------------------------
	function new_SubmitResponse_47() : com.fc.lami.Messages.SubmitResponse {return new com.fc.lami.Messages.SubmitResponse();}
	private function r_SubmitResponse_47(msg : com.fc.lami.Messages.SubmitResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SubmitResponse_47(msg : com.fc.lami.Messages.SubmitResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnEndNotify
//	----------------------------------------------------------------------------------------------------
	function new_TurnEndNotify_48() : com.fc.lami.Messages.TurnEndNotify {return new com.fc.lami.Messages.TurnEndNotify();}
	private function r_TurnEndNotify_48(msg : com.fc.lami.Messages.TurnEndNotify, input : NetDataInput) : void {
	}
	private function w_TurnEndNotify_48(msg : com.fc.lami.Messages.TurnEndNotify, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnStartNotify
//	----------------------------------------------------------------------------------------------------
	function new_TurnStartNotify_49() : com.fc.lami.Messages.TurnStartNotify {return new com.fc.lami.Messages.TurnStartNotify();}
	private function r_TurnStartNotify_49(msg : com.fc.lami.Messages.TurnStartNotify, input : NetDataInput) : void {
	}
	private function w_TurnStartNotify_49(msg : com.fc.lami.Messages.TurnStartNotify, output : NetDataOutput) : void {
	}



	}

}