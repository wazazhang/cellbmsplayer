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
	
		public function getVersion() : String{
			return "Sat May 14 14:22:43 CST 2011";
		}
	
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
			if (msg is com.fc.lami.Messages.GameOverNotify) return 16;
			if (msg is com.fc.lami.Messages.GameOverToRoomNotify) return 17;
			if (msg is com.fc.lami.Messages.GameResetRequest) return 18;
			if (msg is com.fc.lami.Messages.GameResetResponse) return 19;
			if (msg is com.fc.lami.Messages.GameStartNotify) return 20;
			if (msg is com.fc.lami.Messages.GameStartToRoomNotify) return 21;
			if (msg is com.fc.lami.Messages.GetCardNotify) return 22;
			if (msg is com.fc.lami.Messages.GetCardRequest) return 23;
			if (msg is com.fc.lami.Messages.GetCardResponse) return 24;
			if (msg is com.fc.lami.Messages.GetTimeRequest) return 25;
			if (msg is com.fc.lami.Messages.GetTimeResponse) return 26;
			if (msg is com.fc.lami.Messages.LeaveDeskNotify) return 27;
			if (msg is com.fc.lami.Messages.LeaveDeskRequest) return 28;
			if (msg is com.fc.lami.Messages.LeaveDeskResponse) return 29;
			if (msg is com.fc.lami.Messages.LoginRequest) return 30;
			if (msg is com.fc.lami.Messages.LoginResponse) return 31;
			if (msg is com.fc.lami.Messages.LogoutRequest) return 32;
			if (msg is com.fc.lami.Messages.MainMatrixChangeNotify) return 33;
			if (msg is com.fc.lami.Messages.MainMatrixChangeRequest) return 34;
			if (msg is com.fc.lami.Messages.MainMatrixChangeResponse) return 35;
			if (msg is com.fc.lami.Messages.MoveCardNotify) return 36;
			if (msg is com.fc.lami.Messages.MoveCardRequest) return 37;
			if (msg is com.fc.lami.Messages.MoveCardResponse) return 38;
			if (msg is com.fc.lami.Messages.OpenIceNotify) return 39;
			if (msg is com.fc.lami.Messages.OperateCompleteNotify) return 40;
			if (msg is com.fc.lami.Messages.PlayerData) return 41;
			if (msg is com.fc.lami.Messages.ReadyNotify) return 42;
			if (msg is com.fc.lami.Messages.ReadyRequest) return 43;
			if (msg is com.fc.lami.Messages.ReadyResponse) return 44;
			if (msg is com.fc.lami.Messages.RepealSendCardNotify) return 45;
			if (msg is com.fc.lami.Messages.RepealSendCardRequest) return 46;
			if (msg is com.fc.lami.Messages.RepealSendCardResponse) return 47;
			if (msg is com.fc.lami.Messages.ResultPak) return 48;
			if (msg is com.fc.lami.Messages.RetakeCardNotify) return 49;
			if (msg is com.fc.lami.Messages.RetakeCardRequest) return 50;
			if (msg is com.fc.lami.Messages.RetakeCardResponse) return 51;
			if (msg is com.fc.lami.Messages.RoomData) return 52;
			if (msg is com.fc.lami.Messages.RoomSnapShot) return 53;
			if (msg is com.fc.lami.Messages.SendCardNotify) return 54;
			if (msg is com.fc.lami.Messages.SendCardRequest) return 55;
			if (msg is com.fc.lami.Messages.SendCardResponse) return 56;
			if (msg is com.fc.lami.Messages.SpeakToChannelNotify) return 57;
			if (msg is com.fc.lami.Messages.SpeakToChannelRequest) return 58;
			if (msg is com.fc.lami.Messages.SpeakToChannelResponse) return 59;
			if (msg is com.fc.lami.Messages.SpeakToPrivateNotify) return 60;
			if (msg is com.fc.lami.Messages.SpeakToPrivateRequest) return 61;
			if (msg is com.fc.lami.Messages.SpeakToPrivateResponse) return 62;
			if (msg is com.fc.lami.Messages.SpeakToPublicNotify) return 63;
			if (msg is com.fc.lami.Messages.SpeakToPublicRequest) return 64;
			if (msg is com.fc.lami.Messages.SpeakToPublicResponse) return 65;
			if (msg is com.fc.lami.Messages.SubmitRequest) return 66;
			if (msg is com.fc.lami.Messages.SubmitResponse) return 67;
			if (msg is com.fc.lami.Messages.SynchronizeRequest) return 68;
			if (msg is com.fc.lami.Messages.SynchronizeResponse) return 69;
			if (msg is com.fc.lami.Messages.TurnEndNotify) return 70;
			if (msg is com.fc.lami.Messages.TurnStartNotify) return 71;

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
			case 16 : return new com.fc.lami.Messages.GameOverNotify;
			case 17 : return new com.fc.lami.Messages.GameOverToRoomNotify;
			case 18 : return new com.fc.lami.Messages.GameResetRequest;
			case 19 : return new com.fc.lami.Messages.GameResetResponse;
			case 20 : return new com.fc.lami.Messages.GameStartNotify;
			case 21 : return new com.fc.lami.Messages.GameStartToRoomNotify;
			case 22 : return new com.fc.lami.Messages.GetCardNotify;
			case 23 : return new com.fc.lami.Messages.GetCardRequest;
			case 24 : return new com.fc.lami.Messages.GetCardResponse;
			case 25 : return new com.fc.lami.Messages.GetTimeRequest;
			case 26 : return new com.fc.lami.Messages.GetTimeResponse;
			case 27 : return new com.fc.lami.Messages.LeaveDeskNotify;
			case 28 : return new com.fc.lami.Messages.LeaveDeskRequest;
			case 29 : return new com.fc.lami.Messages.LeaveDeskResponse;
			case 30 : return new com.fc.lami.Messages.LoginRequest;
			case 31 : return new com.fc.lami.Messages.LoginResponse;
			case 32 : return new com.fc.lami.Messages.LogoutRequest;
			case 33 : return new com.fc.lami.Messages.MainMatrixChangeNotify;
			case 34 : return new com.fc.lami.Messages.MainMatrixChangeRequest;
			case 35 : return new com.fc.lami.Messages.MainMatrixChangeResponse;
			case 36 : return new com.fc.lami.Messages.MoveCardNotify;
			case 37 : return new com.fc.lami.Messages.MoveCardRequest;
			case 38 : return new com.fc.lami.Messages.MoveCardResponse;
			case 39 : return new com.fc.lami.Messages.OpenIceNotify;
			case 40 : return new com.fc.lami.Messages.OperateCompleteNotify;
			case 41 : return new com.fc.lami.Messages.PlayerData;
			case 42 : return new com.fc.lami.Messages.ReadyNotify;
			case 43 : return new com.fc.lami.Messages.ReadyRequest;
			case 44 : return new com.fc.lami.Messages.ReadyResponse;
			case 45 : return new com.fc.lami.Messages.RepealSendCardNotify;
			case 46 : return new com.fc.lami.Messages.RepealSendCardRequest;
			case 47 : return new com.fc.lami.Messages.RepealSendCardResponse;
			case 48 : return new com.fc.lami.Messages.ResultPak;
			case 49 : return new com.fc.lami.Messages.RetakeCardNotify;
			case 50 : return new com.fc.lami.Messages.RetakeCardRequest;
			case 51 : return new com.fc.lami.Messages.RetakeCardResponse;
			case 52 : return new com.fc.lami.Messages.RoomData;
			case 53 : return new com.fc.lami.Messages.RoomSnapShot;
			case 54 : return new com.fc.lami.Messages.SendCardNotify;
			case 55 : return new com.fc.lami.Messages.SendCardRequest;
			case 56 : return new com.fc.lami.Messages.SendCardResponse;
			case 57 : return new com.fc.lami.Messages.SpeakToChannelNotify;
			case 58 : return new com.fc.lami.Messages.SpeakToChannelRequest;
			case 59 : return new com.fc.lami.Messages.SpeakToChannelResponse;
			case 60 : return new com.fc.lami.Messages.SpeakToPrivateNotify;
			case 61 : return new com.fc.lami.Messages.SpeakToPrivateRequest;
			case 62 : return new com.fc.lami.Messages.SpeakToPrivateResponse;
			case 63 : return new com.fc.lami.Messages.SpeakToPublicNotify;
			case 64 : return new com.fc.lami.Messages.SpeakToPublicRequest;
			case 65 : return new com.fc.lami.Messages.SpeakToPublicResponse;
			case 66 : return new com.fc.lami.Messages.SubmitRequest;
			case 67 : return new com.fc.lami.Messages.SubmitResponse;
			case 68 : return new com.fc.lami.Messages.SynchronizeRequest;
			case 69 : return new com.fc.lami.Messages.SynchronizeResponse;
			case 70 : return new com.fc.lami.Messages.TurnEndNotify;
			case 71 : return new com.fc.lami.Messages.TurnStartNotify;

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
		if (msg is com.fc.lami.Messages.GameOverNotify) {
			r_GameOverNotify_16(com.fc.lami.Messages.GameOverNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameOverToRoomNotify) {
			r_GameOverToRoomNotify_17(com.fc.lami.Messages.GameOverToRoomNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameResetRequest) {
			r_GameResetRequest_18(com.fc.lami.Messages.GameResetRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameResetResponse) {
			r_GameResetResponse_19(com.fc.lami.Messages.GameResetResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameStartNotify) {
			r_GameStartNotify_20(com.fc.lami.Messages.GameStartNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GameStartToRoomNotify) {
			r_GameStartToRoomNotify_21(com.fc.lami.Messages.GameStartToRoomNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardNotify) {
			r_GetCardNotify_22(com.fc.lami.Messages.GetCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			r_GetCardRequest_23(com.fc.lami.Messages.GetCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			r_GetCardResponse_24(com.fc.lami.Messages.GetCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			r_GetTimeRequest_25(com.fc.lami.Messages.GetTimeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			r_GetTimeResponse_26(com.fc.lami.Messages.GetTimeResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskNotify) {
			r_LeaveDeskNotify_27(com.fc.lami.Messages.LeaveDeskNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskRequest) {
			r_LeaveDeskRequest_28(com.fc.lami.Messages.LeaveDeskRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskResponse) {
			r_LeaveDeskResponse_29(com.fc.lami.Messages.LeaveDeskResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LoginRequest) {
			r_LoginRequest_30(com.fc.lami.Messages.LoginRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LoginResponse) {
			r_LoginResponse_31(com.fc.lami.Messages.LoginResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.LogoutRequest) {
			r_LogoutRequest_32(com.fc.lami.Messages.LogoutRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeNotify) {
			r_MainMatrixChangeNotify_33(com.fc.lami.Messages.MainMatrixChangeNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeRequest) {
			r_MainMatrixChangeRequest_34(com.fc.lami.Messages.MainMatrixChangeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeResponse) {
			r_MainMatrixChangeResponse_35(com.fc.lami.Messages.MainMatrixChangeResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			r_MoveCardNotify_36(com.fc.lami.Messages.MoveCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			r_MoveCardRequest_37(com.fc.lami.Messages.MoveCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			r_MoveCardResponse_38(com.fc.lami.Messages.MoveCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.OpenIceNotify) {
			r_OpenIceNotify_39(com.fc.lami.Messages.OpenIceNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.OperateCompleteNotify) {
			r_OperateCompleteNotify_40(com.fc.lami.Messages.OperateCompleteNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			r_PlayerData_41(com.fc.lami.Messages.PlayerData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			r_ReadyNotify_42(com.fc.lami.Messages.ReadyNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			r_ReadyRequest_43(com.fc.lami.Messages.ReadyRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			r_ReadyResponse_44(com.fc.lami.Messages.ReadyResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardNotify) {
			r_RepealSendCardNotify_45(com.fc.lami.Messages.RepealSendCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			r_RepealSendCardRequest_46(com.fc.lami.Messages.RepealSendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			r_RepealSendCardResponse_47(com.fc.lami.Messages.RepealSendCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.ResultPak) {
			r_ResultPak_48(com.fc.lami.Messages.ResultPak(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			r_RetakeCardNotify_49(com.fc.lami.Messages.RetakeCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			r_RetakeCardRequest_50(com.fc.lami.Messages.RetakeCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			r_RetakeCardResponse_51(com.fc.lami.Messages.RetakeCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			r_RoomData_52(com.fc.lami.Messages.RoomData(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.RoomSnapShot) {
			r_RoomSnapShot_53(com.fc.lami.Messages.RoomSnapShot(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			r_SendCardNotify_54(com.fc.lami.Messages.SendCardNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			r_SendCardRequest_55(com.fc.lami.Messages.SendCardRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			r_SendCardResponse_56(com.fc.lami.Messages.SendCardResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelNotify) {
			r_SpeakToChannelNotify_57(com.fc.lami.Messages.SpeakToChannelNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelRequest) {
			r_SpeakToChannelRequest_58(com.fc.lami.Messages.SpeakToChannelRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelResponse) {
			r_SpeakToChannelResponse_59(com.fc.lami.Messages.SpeakToChannelResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateNotify) {
			r_SpeakToPrivateNotify_60(com.fc.lami.Messages.SpeakToPrivateNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateRequest) {
			r_SpeakToPrivateRequest_61(com.fc.lami.Messages.SpeakToPrivateRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateResponse) {
			r_SpeakToPrivateResponse_62(com.fc.lami.Messages.SpeakToPrivateResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicNotify) {
			r_SpeakToPublicNotify_63(com.fc.lami.Messages.SpeakToPublicNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicRequest) {
			r_SpeakToPublicRequest_64(com.fc.lami.Messages.SpeakToPublicRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicResponse) {
			r_SpeakToPublicResponse_65(com.fc.lami.Messages.SpeakToPublicResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SubmitRequest) {
			r_SubmitRequest_66(com.fc.lami.Messages.SubmitRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SubmitResponse) {
			r_SubmitResponse_67(com.fc.lami.Messages.SubmitResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SynchronizeRequest) {
			r_SynchronizeRequest_68(com.fc.lami.Messages.SynchronizeRequest(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.SynchronizeResponse) {
			r_SynchronizeResponse_69(com.fc.lami.Messages.SynchronizeResponse(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.TurnEndNotify) {
			r_TurnEndNotify_70(com.fc.lami.Messages.TurnEndNotify(msg), input); return;
		}
		if (msg is com.fc.lami.Messages.TurnStartNotify) {
			r_TurnStartNotify_71(com.fc.lami.Messages.TurnStartNotify(msg), input); return;
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
		if (msg is com.fc.lami.Messages.GameOverNotify) {
			w_GameOverNotify_16(com.fc.lami.Messages.GameOverNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameOverToRoomNotify) {
			w_GameOverToRoomNotify_17(com.fc.lami.Messages.GameOverToRoomNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameResetRequest) {
			w_GameResetRequest_18(com.fc.lami.Messages.GameResetRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameResetResponse) {
			w_GameResetResponse_19(com.fc.lami.Messages.GameResetResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameStartNotify) {
			w_GameStartNotify_20(com.fc.lami.Messages.GameStartNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GameStartToRoomNotify) {
			w_GameStartToRoomNotify_21(com.fc.lami.Messages.GameStartToRoomNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardNotify) {
			w_GetCardNotify_22(com.fc.lami.Messages.GetCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardRequest) {
			w_GetCardRequest_23(com.fc.lami.Messages.GetCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetCardResponse) {
			w_GetCardResponse_24(com.fc.lami.Messages.GetCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeRequest) {
			w_GetTimeRequest_25(com.fc.lami.Messages.GetTimeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.GetTimeResponse) {
			w_GetTimeResponse_26(com.fc.lami.Messages.GetTimeResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskNotify) {
			w_LeaveDeskNotify_27(com.fc.lami.Messages.LeaveDeskNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskRequest) {
			w_LeaveDeskRequest_28(com.fc.lami.Messages.LeaveDeskRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LeaveDeskResponse) {
			w_LeaveDeskResponse_29(com.fc.lami.Messages.LeaveDeskResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LoginRequest) {
			w_LoginRequest_30(com.fc.lami.Messages.LoginRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LoginResponse) {
			w_LoginResponse_31(com.fc.lami.Messages.LoginResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.LogoutRequest) {
			w_LogoutRequest_32(com.fc.lami.Messages.LogoutRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeNotify) {
			w_MainMatrixChangeNotify_33(com.fc.lami.Messages.MainMatrixChangeNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeRequest) {
			w_MainMatrixChangeRequest_34(com.fc.lami.Messages.MainMatrixChangeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MainMatrixChangeResponse) {
			w_MainMatrixChangeResponse_35(com.fc.lami.Messages.MainMatrixChangeResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardNotify) {
			w_MoveCardNotify_36(com.fc.lami.Messages.MoveCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardRequest) {
			w_MoveCardRequest_37(com.fc.lami.Messages.MoveCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.MoveCardResponse) {
			w_MoveCardResponse_38(com.fc.lami.Messages.MoveCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.OpenIceNotify) {
			w_OpenIceNotify_39(com.fc.lami.Messages.OpenIceNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.OperateCompleteNotify) {
			w_OperateCompleteNotify_40(com.fc.lami.Messages.OperateCompleteNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.PlayerData) {
			w_PlayerData_41(com.fc.lami.Messages.PlayerData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyNotify) {
			w_ReadyNotify_42(com.fc.lami.Messages.ReadyNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyRequest) {
			w_ReadyRequest_43(com.fc.lami.Messages.ReadyRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ReadyResponse) {
			w_ReadyResponse_44(com.fc.lami.Messages.ReadyResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardNotify) {
			w_RepealSendCardNotify_45(com.fc.lami.Messages.RepealSendCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardRequest) {
			w_RepealSendCardRequest_46(com.fc.lami.Messages.RepealSendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RepealSendCardResponse) {
			w_RepealSendCardResponse_47(com.fc.lami.Messages.RepealSendCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.ResultPak) {
			w_ResultPak_48(com.fc.lami.Messages.ResultPak(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardNotify) {
			w_RetakeCardNotify_49(com.fc.lami.Messages.RetakeCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardRequest) {
			w_RetakeCardRequest_50(com.fc.lami.Messages.RetakeCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RetakeCardResponse) {
			w_RetakeCardResponse_51(com.fc.lami.Messages.RetakeCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RoomData) {
			w_RoomData_52(com.fc.lami.Messages.RoomData(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.RoomSnapShot) {
			w_RoomSnapShot_53(com.fc.lami.Messages.RoomSnapShot(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardNotify) {
			w_SendCardNotify_54(com.fc.lami.Messages.SendCardNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardRequest) {
			w_SendCardRequest_55(com.fc.lami.Messages.SendCardRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SendCardResponse) {
			w_SendCardResponse_56(com.fc.lami.Messages.SendCardResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelNotify) {
			w_SpeakToChannelNotify_57(com.fc.lami.Messages.SpeakToChannelNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelRequest) {
			w_SpeakToChannelRequest_58(com.fc.lami.Messages.SpeakToChannelRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToChannelResponse) {
			w_SpeakToChannelResponse_59(com.fc.lami.Messages.SpeakToChannelResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateNotify) {
			w_SpeakToPrivateNotify_60(com.fc.lami.Messages.SpeakToPrivateNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateRequest) {
			w_SpeakToPrivateRequest_61(com.fc.lami.Messages.SpeakToPrivateRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPrivateResponse) {
			w_SpeakToPrivateResponse_62(com.fc.lami.Messages.SpeakToPrivateResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicNotify) {
			w_SpeakToPublicNotify_63(com.fc.lami.Messages.SpeakToPublicNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicRequest) {
			w_SpeakToPublicRequest_64(com.fc.lami.Messages.SpeakToPublicRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SpeakToPublicResponse) {
			w_SpeakToPublicResponse_65(com.fc.lami.Messages.SpeakToPublicResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SubmitRequest) {
			w_SubmitRequest_66(com.fc.lami.Messages.SubmitRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SubmitResponse) {
			w_SubmitResponse_67(com.fc.lami.Messages.SubmitResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SynchronizeRequest) {
			w_SynchronizeRequest_68(com.fc.lami.Messages.SynchronizeRequest(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.SynchronizeResponse) {
			w_SynchronizeResponse_69(com.fc.lami.Messages.SynchronizeResponse(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.TurnEndNotify) {
			w_TurnEndNotify_70(com.fc.lami.Messages.TurnEndNotify(msg), output); return;
		}
		if (msg is com.fc.lami.Messages.TurnStartNotify) {
			w_TurnStartNotify_71(com.fc.lami.Messages.TurnStartNotify(msg), output); return;
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
		msg.x = input.readInt();
		msg.y = input.readInt();
		msg.isSended = input.readBoolean();
	}
	private function w_CardData_1(msg : com.fc.lami.Messages.CardData, output : NetDataOutput) : void {
		output.writeInt(msg.id);
		output.writeInt(msg.point);
		output.writeInt(msg.type);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
		output.writeBoolean(msg.isSended);
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
		msg.desk_name = input.readJavaUTF();
		msg.player_number = input.readInt();
		msg.is_started = input.readBoolean();
		msg.player_E_id = input.readInt();
		msg.player_W_id = input.readInt();
		msg.player_S_id = input.readInt();
		msg.player_N_id = input.readInt();
	}
	private function w_DeskData_3(msg : com.fc.lami.Messages.DeskData, output : NetDataOutput) : void {
		output.writeInt(msg.desk_id);
		output.writeJavaUTF(msg.desk_name);
		output.writeInt(msg.player_number);
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
		msg.player_id = input.readInt();
		msg.desk_id = input.readInt();
		msg.seatID = input.readInt();
	}
	private function w_EnterDeskNotify_7(msg : com.fc.lami.Messages.EnterDeskNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.desk_id);
		output.writeInt(msg.seatID);
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
		msg.desk_id = input.readInt();
		msg.seat = input.readInt();
		msg.turn_interval = input.readInt();
		msg.operate_time = input.readInt();
	}
	private function w_EnterDeskResponse_9(msg : com.fc.lami.Messages.EnterDeskResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
		output.writeInt(msg.desk_id);
		output.writeInt(msg.seat);
		output.writeInt(msg.turn_interval);
		output.writeInt(msg.operate_time);
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
		msg.player_id = input.readInt();
	}
	private function w_ExitRoomNotify_13(msg : com.fc.lami.Messages.ExitRoomNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
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
		msg.rooms = input.readExternalArray();
	}
	private function w_ExitRoomResponse_15(msg : com.fc.lami.Messages.ExitRoomResponse, output : NetDataOutput) : void {
		output.writeExternalArray(msg.rooms);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameOverNotify
//	----------------------------------------------------------------------------------------------------
	function new_GameOverNotify_16() : com.fc.lami.Messages.GameOverNotify {return new com.fc.lami.Messages.GameOverNotify();}
	private function r_GameOverNotify_16(msg : com.fc.lami.Messages.GameOverNotify, input : NetDataInput) : void {
		msg.game_over_type = input.readInt();
		msg.result_pak = input.readExternalArray();
	}
	private function w_GameOverNotify_16(msg : com.fc.lami.Messages.GameOverNotify, output : NetDataOutput) : void {
		output.writeInt(msg.game_over_type);
		output.writeExternalArray(msg.result_pak);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameOverToRoomNotify
//	----------------------------------------------------------------------------------------------------
	function new_GameOverToRoomNotify_17() : com.fc.lami.Messages.GameOverToRoomNotify {return new com.fc.lami.Messages.GameOverToRoomNotify();}
	private function r_GameOverToRoomNotify_17(msg : com.fc.lami.Messages.GameOverToRoomNotify, input : NetDataInput) : void {
		msg.desk_id = input.readInt();
	}
	private function w_GameOverToRoomNotify_17(msg : com.fc.lami.Messages.GameOverToRoomNotify, output : NetDataOutput) : void {
		output.writeInt(msg.desk_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameResetRequest
//	----------------------------------------------------------------------------------------------------
	function new_GameResetRequest_18() : com.fc.lami.Messages.GameResetRequest {return new com.fc.lami.Messages.GameResetRequest();}
	private function r_GameResetRequest_18(msg : com.fc.lami.Messages.GameResetRequest, input : NetDataInput) : void {
	}
	private function w_GameResetRequest_18(msg : com.fc.lami.Messages.GameResetRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameResetResponse
//	----------------------------------------------------------------------------------------------------
	function new_GameResetResponse_19() : com.fc.lami.Messages.GameResetResponse {return new com.fc.lami.Messages.GameResetResponse();}
	private function r_GameResetResponse_19(msg : com.fc.lami.Messages.GameResetResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_GameResetResponse_19(msg : com.fc.lami.Messages.GameResetResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameStartNotify
//	----------------------------------------------------------------------------------------------------
	function new_GameStartNotify_20() : com.fc.lami.Messages.GameStartNotify {return new com.fc.lami.Messages.GameStartNotify();}
	private function r_GameStartNotify_20(msg : com.fc.lami.Messages.GameStartNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
		msg.is_can_reset = input.readBoolean();
	}
	private function w_GameStartNotify_20(msg : com.fc.lami.Messages.GameStartNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
		output.writeBoolean(msg.is_can_reset);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameStartToRoomNotify
//	----------------------------------------------------------------------------------------------------
	function new_GameStartToRoomNotify_21() : com.fc.lami.Messages.GameStartToRoomNotify {return new com.fc.lami.Messages.GameStartToRoomNotify();}
	private function r_GameStartToRoomNotify_21(msg : com.fc.lami.Messages.GameStartToRoomNotify, input : NetDataInput) : void {
		msg.desk_id = input.readInt();
	}
	private function w_GameStartToRoomNotify_21(msg : com.fc.lami.Messages.GameStartToRoomNotify, output : NetDataOutput) : void {
		output.writeInt(msg.desk_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_GetCardNotify_22() : com.fc.lami.Messages.GetCardNotify {return new com.fc.lami.Messages.GetCardNotify();}
	private function r_GetCardNotify_22(msg : com.fc.lami.Messages.GetCardNotify, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_GetCardNotify_22(msg : com.fc.lami.Messages.GetCardNotify, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetCardRequest_23() : com.fc.lami.Messages.GetCardRequest {return new com.fc.lami.Messages.GetCardRequest();}
	private function r_GetCardRequest_23(msg : com.fc.lami.Messages.GetCardRequest, input : NetDataInput) : void {
	}
	private function w_GetCardRequest_23(msg : com.fc.lami.Messages.GetCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetCardResponse_24() : com.fc.lami.Messages.GetCardResponse {return new com.fc.lami.Messages.GetCardResponse();}
	private function r_GetCardResponse_24(msg : com.fc.lami.Messages.GetCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_GetCardResponse_24(msg : com.fc.lami.Messages.GetCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeRequest_25() : com.fc.lami.Messages.GetTimeRequest {return new com.fc.lami.Messages.GetTimeRequest();}
	private function r_GetTimeRequest_25(msg : com.fc.lami.Messages.GetTimeRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_GetTimeRequest_25(msg : com.fc.lami.Messages.GetTimeRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	function new_GetTimeResponse_26() : com.fc.lami.Messages.GetTimeResponse {return new com.fc.lami.Messages.GetTimeResponse();}
	private function r_GetTimeResponse_26(msg : com.fc.lami.Messages.GetTimeResponse, input : NetDataInput) : void {
		msg.time = input.readJavaUTF();
	}
	private function w_GetTimeResponse_26(msg : com.fc.lami.Messages.GetTimeResponse, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskNotify
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskNotify_27() : com.fc.lami.Messages.LeaveDeskNotify {return new com.fc.lami.Messages.LeaveDeskNotify();}
	private function r_LeaveDeskNotify_27(msg : com.fc.lami.Messages.LeaveDeskNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.desk_id = input.readInt();
	}
	private function w_LeaveDeskNotify_27(msg : com.fc.lami.Messages.LeaveDeskNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.desk_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskRequest
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskRequest_28() : com.fc.lami.Messages.LeaveDeskRequest {return new com.fc.lami.Messages.LeaveDeskRequest();}
	private function r_LeaveDeskRequest_28(msg : com.fc.lami.Messages.LeaveDeskRequest, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.desk_id = input.readInt();
	}
	private function w_LeaveDeskRequest_28(msg : com.fc.lami.Messages.LeaveDeskRequest, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.desk_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskResponse
//	----------------------------------------------------------------------------------------------------
	function new_LeaveDeskResponse_29() : com.fc.lami.Messages.LeaveDeskResponse {return new com.fc.lami.Messages.LeaveDeskResponse();}
	private function r_LeaveDeskResponse_29(msg : com.fc.lami.Messages.LeaveDeskResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_LeaveDeskResponse_29(msg : com.fc.lami.Messages.LeaveDeskResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	function new_LoginRequest_30() : com.fc.lami.Messages.LoginRequest {return new com.fc.lami.Messages.LoginRequest();}
	private function r_LoginRequest_30(msg : com.fc.lami.Messages.LoginRequest, input : NetDataInput) : void {
		msg.name = input.readJavaUTF();
		msg.validate = input.readJavaUTF();
	}
	private function w_LoginRequest_30(msg : com.fc.lami.Messages.LoginRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.name);
		output.writeJavaUTF(msg.validate);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	function new_LoginResponse_31() : com.fc.lami.Messages.LoginResponse {return new com.fc.lami.Messages.LoginResponse();}
	private function r_LoginResponse_31(msg : com.fc.lami.Messages.LoginResponse, input : NetDataInput) : void {
		msg.result = input.readShort();
		msg.player = input.readExternal() as com.fc.lami.Messages.PlayerData;
		msg.rooms = input.readExternalArray();
	}
	private function w_LoginResponse_31(msg : com.fc.lami.Messages.LoginResponse, output : NetDataOutput) : void {
		output.writeShort(msg.result);
		output.writeExternal(msg.player);
		output.writeExternalArray(msg.rooms);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	function new_LogoutRequest_32() : com.fc.lami.Messages.LogoutRequest {return new com.fc.lami.Messages.LogoutRequest();}
	private function r_LogoutRequest_32(msg : com.fc.lami.Messages.LogoutRequest, input : NetDataInput) : void {
	}
	private function w_LogoutRequest_32(msg : com.fc.lami.Messages.LogoutRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeNotify
//	----------------------------------------------------------------------------------------------------
	function new_MainMatrixChangeNotify_33() : com.fc.lami.Messages.MainMatrixChangeNotify {return new com.fc.lami.Messages.MainMatrixChangeNotify();}
	private function r_MainMatrixChangeNotify_33(msg : com.fc.lami.Messages.MainMatrixChangeNotify, input : NetDataInput) : void {
		msg.is_hardhanded = input.readBoolean();
		msg.cards = input.readExternalArray();
	}
	private function w_MainMatrixChangeNotify_33(msg : com.fc.lami.Messages.MainMatrixChangeNotify, output : NetDataOutput) : void {
		output.writeBoolean(msg.is_hardhanded);
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeRequest
//	----------------------------------------------------------------------------------------------------
	function new_MainMatrixChangeRequest_34() : com.fc.lami.Messages.MainMatrixChangeRequest {return new com.fc.lami.Messages.MainMatrixChangeRequest();}
	private function r_MainMatrixChangeRequest_34(msg : com.fc.lami.Messages.MainMatrixChangeRequest, input : NetDataInput) : void {
		msg.cards = input.readExternalArray();
	}
	private function w_MainMatrixChangeRequest_34(msg : com.fc.lami.Messages.MainMatrixChangeRequest, output : NetDataOutput) : void {
		output.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeResponse
//	----------------------------------------------------------------------------------------------------
	function new_MainMatrixChangeResponse_35() : com.fc.lami.Messages.MainMatrixChangeResponse {return new com.fc.lami.Messages.MainMatrixChangeResponse();}
	private function r_MainMatrixChangeResponse_35(msg : com.fc.lami.Messages.MainMatrixChangeResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_MainMatrixChangeResponse_35(msg : com.fc.lami.Messages.MainMatrixChangeResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardNotify_36() : com.fc.lami.Messages.MoveCardNotify {return new com.fc.lami.Messages.MoveCardNotify();}
	private function r_MoveCardNotify_36(msg : com.fc.lami.Messages.MoveCardNotify, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
		msg.player_id = input.readInt();
	}
	private function w_MoveCardNotify_36(msg : com.fc.lami.Messages.MoveCardNotify, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardRequest_37() : com.fc.lami.Messages.MoveCardRequest {return new com.fc.lami.Messages.MoveCardRequest();}
	private function r_MoveCardRequest_37(msg : com.fc.lami.Messages.MoveCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.nx = input.readInt();
		msg.ny = input.readInt();
	}
	private function w_MoveCardRequest_37(msg : com.fc.lami.Messages.MoveCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.nx);
		output.writeInt(msg.ny);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_MoveCardResponse_38() : com.fc.lami.Messages.MoveCardResponse {return new com.fc.lami.Messages.MoveCardResponse();}
	private function r_MoveCardResponse_38(msg : com.fc.lami.Messages.MoveCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_MoveCardResponse_38(msg : com.fc.lami.Messages.MoveCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OpenIceNotify
//	----------------------------------------------------------------------------------------------------
	function new_OpenIceNotify_39() : com.fc.lami.Messages.OpenIceNotify {return new com.fc.lami.Messages.OpenIceNotify();}
	private function r_OpenIceNotify_39(msg : com.fc.lami.Messages.OpenIceNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
	}
	private function w_OpenIceNotify_39(msg : com.fc.lami.Messages.OpenIceNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OperateCompleteNotify
//	----------------------------------------------------------------------------------------------------
	function new_OperateCompleteNotify_40() : com.fc.lami.Messages.OperateCompleteNotify {return new com.fc.lami.Messages.OperateCompleteNotify();}
	private function r_OperateCompleteNotify_40(msg : com.fc.lami.Messages.OperateCompleteNotify, input : NetDataInput) : void {
	}
	private function w_OperateCompleteNotify_40(msg : com.fc.lami.Messages.OperateCompleteNotify, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.PlayerData
//	----------------------------------------------------------------------------------------------------
	function new_PlayerData_41() : com.fc.lami.Messages.PlayerData {return new com.fc.lami.Messages.PlayerData();}
	private function r_PlayerData_41(msg : com.fc.lami.Messages.PlayerData, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.name = input.readJavaUTF();
		msg.score = input.readInt();
		msg.win = input.readInt();
		msg.lose = input.readInt();
		msg.level = input.readInt();
	}
	private function w_PlayerData_41(msg : com.fc.lami.Messages.PlayerData, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeJavaUTF(msg.name);
		output.writeInt(msg.score);
		output.writeInt(msg.win);
		output.writeInt(msg.lose);
		output.writeInt(msg.level);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyNotify
//	----------------------------------------------------------------------------------------------------
	function new_ReadyNotify_42() : com.fc.lami.Messages.ReadyNotify {return new com.fc.lami.Messages.ReadyNotify();}
	private function r_ReadyNotify_42(msg : com.fc.lami.Messages.ReadyNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.isReady = input.readBoolean();
	}
	private function w_ReadyNotify_42(msg : com.fc.lami.Messages.ReadyNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyRequest
//	----------------------------------------------------------------------------------------------------
	function new_ReadyRequest_43() : com.fc.lami.Messages.ReadyRequest {return new com.fc.lami.Messages.ReadyRequest();}
	private function r_ReadyRequest_43(msg : com.fc.lami.Messages.ReadyRequest, input : NetDataInput) : void {
		msg.isReady = input.readBoolean();
	}
	private function w_ReadyRequest_43(msg : com.fc.lami.Messages.ReadyRequest, output : NetDataOutput) : void {
		output.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyResponse
//	----------------------------------------------------------------------------------------------------
	function new_ReadyResponse_44() : com.fc.lami.Messages.ReadyResponse {return new com.fc.lami.Messages.ReadyResponse();}
	private function r_ReadyResponse_44(msg : com.fc.lami.Messages.ReadyResponse, input : NetDataInput) : void {
	}
	private function w_ReadyResponse_44(msg : com.fc.lami.Messages.ReadyResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardNotify_45() : com.fc.lami.Messages.RepealSendCardNotify {return new com.fc.lami.Messages.RepealSendCardNotify();}
	private function r_RepealSendCardNotify_45(msg : com.fc.lami.Messages.RepealSendCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.cds = input.readExternalArray();
	}
	private function w_RepealSendCardNotify_45(msg : com.fc.lami.Messages.RepealSendCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeExternalArray(msg.cds);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardRequest_46() : com.fc.lami.Messages.RepealSendCardRequest {return new com.fc.lami.Messages.RepealSendCardRequest();}
	private function r_RepealSendCardRequest_46(msg : com.fc.lami.Messages.RepealSendCardRequest, input : NetDataInput) : void {
	}
	private function w_RepealSendCardRequest_46(msg : com.fc.lami.Messages.RepealSendCardRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RepealSendCardResponse_47() : com.fc.lami.Messages.RepealSendCardResponse {return new com.fc.lami.Messages.RepealSendCardResponse();}
	private function r_RepealSendCardResponse_47(msg : com.fc.lami.Messages.RepealSendCardResponse, input : NetDataInput) : void {
	}
	private function w_RepealSendCardResponse_47(msg : com.fc.lami.Messages.RepealSendCardResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ResultPak
//	----------------------------------------------------------------------------------------------------
	function new_ResultPak_48() : com.fc.lami.Messages.ResultPak {return new com.fc.lami.Messages.ResultPak();}
	private function r_ResultPak_48(msg : com.fc.lami.Messages.ResultPak, input : NetDataInput) : void {
		msg.point = input.readInt();
		msg.player_id = input.readInt();
		msg.is_win = input.readBoolean();
	}
	private function w_ResultPak_48(msg : com.fc.lami.Messages.ResultPak, output : NetDataOutput) : void {
		output.writeInt(msg.point);
		output.writeInt(msg.player_id);
		output.writeBoolean(msg.is_win);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardNotify_49() : com.fc.lami.Messages.RetakeCardNotify {return new com.fc.lami.Messages.RetakeCardNotify();}
	private function r_RetakeCardNotify_49(msg : com.fc.lami.Messages.RetakeCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.x = input.readInt();
		msg.y = input.readInt();
		msg.n = input.readInt();
	}
	private function w_RetakeCardNotify_49(msg : com.fc.lami.Messages.RetakeCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
		output.writeInt(msg.n);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardRequest_50() : com.fc.lami.Messages.RetakeCardRequest {return new com.fc.lami.Messages.RetakeCardRequest();}
	private function r_RetakeCardRequest_50(msg : com.fc.lami.Messages.RetakeCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
	}
	private function w_RetakeCardRequest_50(msg : com.fc.lami.Messages.RetakeCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_RetakeCardResponse_51() : com.fc.lami.Messages.RetakeCardResponse {return new com.fc.lami.Messages.RetakeCardResponse();}
	private function r_RetakeCardResponse_51(msg : com.fc.lami.Messages.RetakeCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_RetakeCardResponse_51(msg : com.fc.lami.Messages.RetakeCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RoomData
//	----------------------------------------------------------------------------------------------------
	function new_RoomData_52() : com.fc.lami.Messages.RoomData {return new com.fc.lami.Messages.RoomData();}
	private function r_RoomData_52(msg : com.fc.lami.Messages.RoomData, input : NetDataInput) : void {
		msg.room_id = input.readInt();
		msg.desks = input.readExternalArray();
		msg.players = input.readExternalArray();
	}
	private function w_RoomData_52(msg : com.fc.lami.Messages.RoomData, output : NetDataOutput) : void {
		output.writeInt(msg.room_id);
		output.writeExternalArray(msg.desks);
		output.writeExternalArray(msg.players);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RoomSnapShot
//	----------------------------------------------------------------------------------------------------
	function new_RoomSnapShot_53() : com.fc.lami.Messages.RoomSnapShot {return new com.fc.lami.Messages.RoomSnapShot();}
	private function r_RoomSnapShot_53(msg : com.fc.lami.Messages.RoomSnapShot, input : NetDataInput) : void {
		msg.room_id = input.readInt();
		msg.room_name = input.readJavaUTF();
		msg.player_number_max = input.readInt();
		msg.player_number = input.readInt();
	}
	private function w_RoomSnapShot_53(msg : com.fc.lami.Messages.RoomSnapShot, output : NetDataOutput) : void {
		output.writeInt(msg.room_id);
		output.writeJavaUTF(msg.room_name);
		output.writeInt(msg.player_number_max);
		output.writeInt(msg.player_number);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardNotify
//	----------------------------------------------------------------------------------------------------
	function new_SendCardNotify_54() : com.fc.lami.Messages.SendCardNotify {return new com.fc.lami.Messages.SendCardNotify();}
	private function r_SendCardNotify_54(msg : com.fc.lami.Messages.SendCardNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.cards = input.readExternalArray();
		msg.x = input.readInt();
		msg.y = input.readInt();
	}
	private function w_SendCardNotify_54(msg : com.fc.lami.Messages.SendCardNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeExternalArray(msg.cards);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardRequest
//	----------------------------------------------------------------------------------------------------
	function new_SendCardRequest_55() : com.fc.lami.Messages.SendCardRequest {return new com.fc.lami.Messages.SendCardRequest();}
	private function r_SendCardRequest_55(msg : com.fc.lami.Messages.SendCardRequest, input : NetDataInput) : void {
		msg.cards = input.readIntArray();
		msg.x = input.readInt();
		msg.y = input.readInt();
	}
	private function w_SendCardRequest_55(msg : com.fc.lami.Messages.SendCardRequest, output : NetDataOutput) : void {
		output.writeIntArray(msg.cards);
		output.writeInt(msg.x);
		output.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardResponse
//	----------------------------------------------------------------------------------------------------
	function new_SendCardResponse_56() : com.fc.lami.Messages.SendCardResponse {return new com.fc.lami.Messages.SendCardResponse();}
	private function r_SendCardResponse_56(msg : com.fc.lami.Messages.SendCardResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SendCardResponse_56(msg : com.fc.lami.Messages.SendCardResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToChannelNotify
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToChannelNotify_57() : com.fc.lami.Messages.SpeakToChannelNotify {return new com.fc.lami.Messages.SpeakToChannelNotify();}
	private function r_SpeakToChannelNotify_57(msg : com.fc.lami.Messages.SpeakToChannelNotify, input : NetDataInput) : void {
		msg.player_name = input.readJavaUTF();
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToChannelNotify_57(msg : com.fc.lami.Messages.SpeakToChannelNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.player_name);
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToChannelRequest
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToChannelRequest_58() : com.fc.lami.Messages.SpeakToChannelRequest {return new com.fc.lami.Messages.SpeakToChannelRequest();}
	private function r_SpeakToChannelRequest_58(msg : com.fc.lami.Messages.SpeakToChannelRequest, input : NetDataInput) : void {
		msg.channel = input.readInt();
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToChannelRequest_58(msg : com.fc.lami.Messages.SpeakToChannelRequest, output : NetDataOutput) : void {
		output.writeInt(msg.channel);
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToChannelResponse
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToChannelResponse_59() : com.fc.lami.Messages.SpeakToChannelResponse {return new com.fc.lami.Messages.SpeakToChannelResponse();}
	private function r_SpeakToChannelResponse_59(msg : com.fc.lami.Messages.SpeakToChannelResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SpeakToChannelResponse_59(msg : com.fc.lami.Messages.SpeakToChannelResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPrivateNotify
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPrivateNotify_60() : com.fc.lami.Messages.SpeakToPrivateNotify {return new com.fc.lami.Messages.SpeakToPrivateNotify();}
	private function r_SpeakToPrivateNotify_60(msg : com.fc.lami.Messages.SpeakToPrivateNotify, input : NetDataInput) : void {
		msg.player_name = input.readJavaUTF();
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToPrivateNotify_60(msg : com.fc.lami.Messages.SpeakToPrivateNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.player_name);
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPrivateRequest
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPrivateRequest_61() : com.fc.lami.Messages.SpeakToPrivateRequest {return new com.fc.lami.Messages.SpeakToPrivateRequest();}
	private function r_SpeakToPrivateRequest_61(msg : com.fc.lami.Messages.SpeakToPrivateRequest, input : NetDataInput) : void {
		msg.pname = input.readJavaUTF();
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToPrivateRequest_61(msg : com.fc.lami.Messages.SpeakToPrivateRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.pname);
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPrivateResponse
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPrivateResponse_62() : com.fc.lami.Messages.SpeakToPrivateResponse {return new com.fc.lami.Messages.SpeakToPrivateResponse();}
	private function r_SpeakToPrivateResponse_62(msg : com.fc.lami.Messages.SpeakToPrivateResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SpeakToPrivateResponse_62(msg : com.fc.lami.Messages.SpeakToPrivateResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPublicNotify
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPublicNotify_63() : com.fc.lami.Messages.SpeakToPublicNotify {return new com.fc.lami.Messages.SpeakToPublicNotify();}
	private function r_SpeakToPublicNotify_63(msg : com.fc.lami.Messages.SpeakToPublicNotify, input : NetDataInput) : void {
		msg.player_name = input.readJavaUTF();
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToPublicNotify_63(msg : com.fc.lami.Messages.SpeakToPublicNotify, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.player_name);
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPublicRequest
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPublicRequest_64() : com.fc.lami.Messages.SpeakToPublicRequest {return new com.fc.lami.Messages.SpeakToPublicRequest();}
	private function r_SpeakToPublicRequest_64(msg : com.fc.lami.Messages.SpeakToPublicRequest, input : NetDataInput) : void {
		msg.message = input.readJavaUTF();
	}
	private function w_SpeakToPublicRequest_64(msg : com.fc.lami.Messages.SpeakToPublicRequest, output : NetDataOutput) : void {
		output.writeJavaUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SpeakToPublicResponse
//	----------------------------------------------------------------------------------------------------
	function new_SpeakToPublicResponse_65() : com.fc.lami.Messages.SpeakToPublicResponse {return new com.fc.lami.Messages.SpeakToPublicResponse();}
	private function r_SpeakToPublicResponse_65(msg : com.fc.lami.Messages.SpeakToPublicResponse, input : NetDataInput) : void {
	}
	private function w_SpeakToPublicResponse_65(msg : com.fc.lami.Messages.SpeakToPublicResponse, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitRequest
//	----------------------------------------------------------------------------------------------------
	function new_SubmitRequest_66() : com.fc.lami.Messages.SubmitRequest {return new com.fc.lami.Messages.SubmitRequest();}
	private function r_SubmitRequest_66(msg : com.fc.lami.Messages.SubmitRequest, input : NetDataInput) : void {
	}
	private function w_SubmitRequest_66(msg : com.fc.lami.Messages.SubmitRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitResponse
//	----------------------------------------------------------------------------------------------------
	function new_SubmitResponse_67() : com.fc.lami.Messages.SubmitResponse {return new com.fc.lami.Messages.SubmitResponse();}
	private function r_SubmitResponse_67(msg : com.fc.lami.Messages.SubmitResponse, input : NetDataInput) : void {
		msg.result = input.readInt();
	}
	private function w_SubmitResponse_67(msg : com.fc.lami.Messages.SubmitResponse, output : NetDataOutput) : void {
		output.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SynchronizeRequest
//	----------------------------------------------------------------------------------------------------
	function new_SynchronizeRequest_68() : com.fc.lami.Messages.SynchronizeRequest {return new com.fc.lami.Messages.SynchronizeRequest();}
	private function r_SynchronizeRequest_68(msg : com.fc.lami.Messages.SynchronizeRequest, input : NetDataInput) : void {
	}
	private function w_SynchronizeRequest_68(msg : com.fc.lami.Messages.SynchronizeRequest, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SynchronizeResponse
//	----------------------------------------------------------------------------------------------------
	function new_SynchronizeResponse_69() : com.fc.lami.Messages.SynchronizeResponse {return new com.fc.lami.Messages.SynchronizeResponse();}
	private function r_SynchronizeResponse_69(msg : com.fc.lami.Messages.SynchronizeResponse, input : NetDataInput) : void {
		msg.matrix = input.readExternalArray();
		msg.player_card = input.readExternalArray();
		msg.left_card = input.readInt();
	}
	private function w_SynchronizeResponse_69(msg : com.fc.lami.Messages.SynchronizeResponse, output : NetDataOutput) : void {
		output.writeExternalArray(msg.matrix);
		output.writeExternalArray(msg.player_card);
		output.writeInt(msg.left_card);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnEndNotify
//	----------------------------------------------------------------------------------------------------
	function new_TurnEndNotify_70() : com.fc.lami.Messages.TurnEndNotify {return new com.fc.lami.Messages.TurnEndNotify();}
	private function r_TurnEndNotify_70(msg : com.fc.lami.Messages.TurnEndNotify, input : NetDataInput) : void {
	}
	private function w_TurnEndNotify_70(msg : com.fc.lami.Messages.TurnEndNotify, output : NetDataOutput) : void {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnStartNotify
//	----------------------------------------------------------------------------------------------------
	function new_TurnStartNotify_71() : com.fc.lami.Messages.TurnStartNotify {return new com.fc.lami.Messages.TurnStartNotify();}
	private function r_TurnStartNotify_71(msg : com.fc.lami.Messages.TurnStartNotify, input : NetDataInput) : void {
		msg.player_id = input.readInt();
		msg.stack_num = input.readInt();
	}
	private function w_TurnStartNotify_71(msg : com.fc.lami.Messages.TurnStartNotify, output : NetDataOutput) : void {
		output.writeInt(msg.player_id);
		output.writeInt(msg.stack_num);
	}



	}

}