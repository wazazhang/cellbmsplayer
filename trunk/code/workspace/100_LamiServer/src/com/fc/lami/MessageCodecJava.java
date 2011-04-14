package com.fc.lami;

import java.io.IOException;
import com.net.mutual.MutualMessage;
import com.net.mutual.MutualMessageCodec;
import com.net.NetDataInput;
import com.net.NetDataOutput;
import com.net.NetDataTypes;


/**
 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
 */
public class MessageCodecJava implements MutualMessageCodec
{
	public void readExternal(MutualMessage msg, NetDataInput in) throws IOException 
	{
		if (msg.getClass().equals(com.fc.lami.Messages.CardData.class)) {
			_r((com.fc.lami.Messages.CardData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.CardStackChangeNotify.class)) {
			_r((com.fc.lami.Messages.CardStackChangeNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.DeskData.class)) {
			_r((com.fc.lami.Messages.DeskData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoNotify.class)) {
			_r((com.fc.lami.Messages.EchoNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoRequest.class)) {
			_r((com.fc.lami.Messages.EchoRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoResponse.class)) {
			_r((com.fc.lami.Messages.EchoResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskNotify.class)) {
			_r((com.fc.lami.Messages.EnterDeskNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskRequest.class)) {
			_r((com.fc.lami.Messages.EnterDeskRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskResponse.class)) {
			_r((com.fc.lami.Messages.EnterDeskResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomNotify.class)) {
			_r((com.fc.lami.Messages.EnterRoomNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomRequest.class)) {
			_r((com.fc.lami.Messages.EnterRoomRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomResponse.class)) {
			_r((com.fc.lami.Messages.EnterRoomResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomNotify.class)) {
			_r((com.fc.lami.Messages.ExitRoomNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomRequest.class)) {
			_r((com.fc.lami.Messages.ExitRoomRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomResponse.class)) {
			_r((com.fc.lami.Messages.ExitRoomResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GameOverNotify.class)) {
			_r((com.fc.lami.Messages.GameOverNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GameStartNotify.class)) {
			_r((com.fc.lami.Messages.GameStartNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardNotify.class)) {
			_r((com.fc.lami.Messages.GetCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardRequest.class)) {
			_r((com.fc.lami.Messages.GetCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardResponse.class)) {
			_r((com.fc.lami.Messages.GetCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetTimeRequest.class)) {
			_r((com.fc.lami.Messages.GetTimeRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetTimeResponse.class)) {
			_r((com.fc.lami.Messages.GetTimeResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskNotify.class)) {
			_r((com.fc.lami.Messages.LeaveDeskNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskRequest.class)) {
			_r((com.fc.lami.Messages.LeaveDeskRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskResponse.class)) {
			_r((com.fc.lami.Messages.LeaveDeskResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LoginRequest.class)) {
			_r((com.fc.lami.Messages.LoginRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LoginResponse.class)) {
			_r((com.fc.lami.Messages.LoginResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LogoutRequest.class)) {
			_r((com.fc.lami.Messages.LogoutRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeNotify.class)) {
			_r((com.fc.lami.Messages.MainMatrixChangeNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeRequest.class)) {
			_r((com.fc.lami.Messages.MainMatrixChangeRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeResponse.class)) {
			_r((com.fc.lami.Messages.MainMatrixChangeResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardNotify.class)) {
			_r((com.fc.lami.Messages.MoveCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardRequest.class)) {
			_r((com.fc.lami.Messages.MoveCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardResponse.class)) {
			_r((com.fc.lami.Messages.MoveCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OpenIceNotify.class)) {
			_r((com.fc.lami.Messages.OpenIceNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.PlayerData.class)) {
			_r((com.fc.lami.Messages.PlayerData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyNotify.class)) {
			_r((com.fc.lami.Messages.ReadyNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyRequest.class)) {
			_r((com.fc.lami.Messages.ReadyRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyResponse.class)) {
			_r((com.fc.lami.Messages.ReadyResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardNotify.class)) {
			_r((com.fc.lami.Messages.RepealSendCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardRequest.class)) {
			_r((com.fc.lami.Messages.RepealSendCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardResponse.class)) {
			_r((com.fc.lami.Messages.RepealSendCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ResultPak.class)) {
			_r((com.fc.lami.Messages.ResultPak)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardNotify.class)) {
			_r((com.fc.lami.Messages.RetakeCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardRequest.class)) {
			_r((com.fc.lami.Messages.RetakeCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardResponse.class)) {
			_r((com.fc.lami.Messages.RetakeCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RoomData.class)) {
			_r((com.fc.lami.Messages.RoomData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardNotify.class)) {
			_r((com.fc.lami.Messages.SendCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardRequest.class)) {
			_r((com.fc.lami.Messages.SendCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardResponse.class)) {
			_r((com.fc.lami.Messages.SendCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SubmitRequest.class)) {
			_r((com.fc.lami.Messages.SubmitRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SubmitResponse.class)) {
			_r((com.fc.lami.Messages.SubmitResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SynchronizeRequest.class)) {
			_r((com.fc.lami.Messages.SynchronizeRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SynchronizeResponse.class)) {
			_r((com.fc.lami.Messages.SynchronizeResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.TurnEndNotify.class)) {
			_r((com.fc.lami.Messages.TurnEndNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.TurnStartNotify.class)) {
			_r((com.fc.lami.Messages.TurnStartNotify)msg, in); return;
		}

	}

	public void writeExternal(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.fc.lami.Messages.CardData.class)) {
			_w((com.fc.lami.Messages.CardData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.CardStackChangeNotify.class)) {
			_w((com.fc.lami.Messages.CardStackChangeNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.DeskData.class)) {
			_w((com.fc.lami.Messages.DeskData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoNotify.class)) {
			_w((com.fc.lami.Messages.EchoNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoRequest.class)) {
			_w((com.fc.lami.Messages.EchoRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoResponse.class)) {
			_w((com.fc.lami.Messages.EchoResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskNotify.class)) {
			_w((com.fc.lami.Messages.EnterDeskNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskRequest.class)) {
			_w((com.fc.lami.Messages.EnterDeskRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterDeskResponse.class)) {
			_w((com.fc.lami.Messages.EnterDeskResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomNotify.class)) {
			_w((com.fc.lami.Messages.EnterRoomNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomRequest.class)) {
			_w((com.fc.lami.Messages.EnterRoomRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EnterRoomResponse.class)) {
			_w((com.fc.lami.Messages.EnterRoomResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomNotify.class)) {
			_w((com.fc.lami.Messages.ExitRoomNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomRequest.class)) {
			_w((com.fc.lami.Messages.ExitRoomRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ExitRoomResponse.class)) {
			_w((com.fc.lami.Messages.ExitRoomResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GameOverNotify.class)) {
			_w((com.fc.lami.Messages.GameOverNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GameStartNotify.class)) {
			_w((com.fc.lami.Messages.GameStartNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardNotify.class)) {
			_w((com.fc.lami.Messages.GetCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardRequest.class)) {
			_w((com.fc.lami.Messages.GetCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetCardResponse.class)) {
			_w((com.fc.lami.Messages.GetCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetTimeRequest.class)) {
			_w((com.fc.lami.Messages.GetTimeRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.GetTimeResponse.class)) {
			_w((com.fc.lami.Messages.GetTimeResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskNotify.class)) {
			_w((com.fc.lami.Messages.LeaveDeskNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskRequest.class)) {
			_w((com.fc.lami.Messages.LeaveDeskRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LeaveDeskResponse.class)) {
			_w((com.fc.lami.Messages.LeaveDeskResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LoginRequest.class)) {
			_w((com.fc.lami.Messages.LoginRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LoginResponse.class)) {
			_w((com.fc.lami.Messages.LoginResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.LogoutRequest.class)) {
			_w((com.fc.lami.Messages.LogoutRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeNotify.class)) {
			_w((com.fc.lami.Messages.MainMatrixChangeNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeRequest.class)) {
			_w((com.fc.lami.Messages.MainMatrixChangeRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MainMatrixChangeResponse.class)) {
			_w((com.fc.lami.Messages.MainMatrixChangeResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardNotify.class)) {
			_w((com.fc.lami.Messages.MoveCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardRequest.class)) {
			_w((com.fc.lami.Messages.MoveCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardResponse.class)) {
			_w((com.fc.lami.Messages.MoveCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OpenIceNotify.class)) {
			_w((com.fc.lami.Messages.OpenIceNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.PlayerData.class)) {
			_w((com.fc.lami.Messages.PlayerData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyNotify.class)) {
			_w((com.fc.lami.Messages.ReadyNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyRequest.class)) {
			_w((com.fc.lami.Messages.ReadyRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ReadyResponse.class)) {
			_w((com.fc.lami.Messages.ReadyResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardNotify.class)) {
			_w((com.fc.lami.Messages.RepealSendCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardRequest.class)) {
			_w((com.fc.lami.Messages.RepealSendCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardResponse.class)) {
			_w((com.fc.lami.Messages.RepealSendCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.ResultPak.class)) {
			_w((com.fc.lami.Messages.ResultPak)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardNotify.class)) {
			_w((com.fc.lami.Messages.RetakeCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardRequest.class)) {
			_w((com.fc.lami.Messages.RetakeCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RetakeCardResponse.class)) {
			_w((com.fc.lami.Messages.RetakeCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RoomData.class)) {
			_w((com.fc.lami.Messages.RoomData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardNotify.class)) {
			_w((com.fc.lami.Messages.SendCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardRequest.class)) {
			_w((com.fc.lami.Messages.SendCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SendCardResponse.class)) {
			_w((com.fc.lami.Messages.SendCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SubmitRequest.class)) {
			_w((com.fc.lami.Messages.SubmitRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SubmitResponse.class)) {
			_w((com.fc.lami.Messages.SubmitResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SynchronizeRequest.class)) {
			_w((com.fc.lami.Messages.SynchronizeRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.SynchronizeResponse.class)) {
			_w((com.fc.lami.Messages.SynchronizeResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.TurnEndNotify.class)) {
			_w((com.fc.lami.Messages.TurnEndNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.TurnStartNotify.class)) {
			_w((com.fc.lami.Messages.TurnStartNotify)msg, out); return;
		}

	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.CardData
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.CardData new_com_fc_lami_Messages_CardData(){return new com.fc.lami.Messages.CardData();}
	private void _r(com.fc.lami.Messages.CardData msg, NetDataInput in) throws IOException {
		msg.id = in.readInt();
		msg.point = in.readInt();
		msg.type = in.readInt();
		msg.x = in.readInt();
		msg.y = in.readInt();
		msg.isSended = in.readBoolean();
	}
	private void _w(com.fc.lami.Messages.CardData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.id);
		out.writeInt(msg.point);
		out.writeInt(msg.type);
		out.writeInt(msg.x);
		out.writeInt(msg.y);
		out.writeBoolean(msg.isSended);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.CardStackChangeNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.CardStackChangeNotify new_com_fc_lami_Messages_CardStackChangeNotify(){return new com.fc.lami.Messages.CardStackChangeNotify();}
	private void _r(com.fc.lami.Messages.CardStackChangeNotify msg, NetDataInput in) throws IOException {
		msg.card_stack_number = in.readInt();
	}
	private void _w(com.fc.lami.Messages.CardStackChangeNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.card_stack_number);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.DeskData
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.DeskData new_com_fc_lami_Messages_DeskData(){return new com.fc.lami.Messages.DeskData();}
	private void _r(com.fc.lami.Messages.DeskData msg, NetDataInput in) throws IOException {
		msg.desk_id = in.readInt();
		msg.is_started = in.readBoolean();
		msg.player_E_id = in.readInt();
		msg.player_W_id = in.readInt();
		msg.player_S_id = in.readInt();
		msg.player_N_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.DeskData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.desk_id);
		out.writeBoolean(msg.is_started);
		out.writeInt(msg.player_E_id);
		out.writeInt(msg.player_W_id);
		out.writeInt(msg.player_S_id);
		out.writeInt(msg.player_N_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EchoNotify new_com_fc_lami_Messages_EchoNotify(){return new com.fc.lami.Messages.EchoNotify();}
	private void _r(com.fc.lami.Messages.EchoNotify msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.EchoNotify msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EchoRequest new_com_fc_lami_Messages_EchoRequest(){return new com.fc.lami.Messages.EchoRequest();}
	private void _r(com.fc.lami.Messages.EchoRequest msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.EchoRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EchoResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EchoResponse new_com_fc_lami_Messages_EchoResponse(){return new com.fc.lami.Messages.EchoResponse();}
	private void _r(com.fc.lami.Messages.EchoResponse msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.EchoResponse msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterDeskNotify new_com_fc_lami_Messages_EnterDeskNotify(){return new com.fc.lami.Messages.EnterDeskNotify();}
	private void _r(com.fc.lami.Messages.EnterDeskNotify msg, NetDataInput in) throws IOException {
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
		msg.desk = in.readExternal(com.fc.lami.Messages.DeskData.class);
		msg.seatID = in.readInt();
	}
	private void _w(com.fc.lami.Messages.EnterDeskNotify msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.player);
		out.writeExternal(msg.desk);
		out.writeInt(msg.seatID);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterDeskRequest new_com_fc_lami_Messages_EnterDeskRequest(){return new com.fc.lami.Messages.EnterDeskRequest();}
	private void _r(com.fc.lami.Messages.EnterDeskRequest msg, NetDataInput in) throws IOException {
		msg.desk_No = in.readInt();
		msg.seat = in.readInt();
	}
	private void _w(com.fc.lami.Messages.EnterDeskRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.desk_No);
		out.writeInt(msg.seat);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterDeskResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterDeskResponse new_com_fc_lami_Messages_EnterDeskResponse(){return new com.fc.lami.Messages.EnterDeskResponse();}
	private void _r(com.fc.lami.Messages.EnterDeskResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
		msg.desk = in.readExternal(com.fc.lami.Messages.DeskData.class);
	}
	private void _w(com.fc.lami.Messages.EnterDeskResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
		out.writeExternal(msg.desk);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterRoomNotify new_com_fc_lami_Messages_EnterRoomNotify(){return new com.fc.lami.Messages.EnterRoomNotify();}
	private void _r(com.fc.lami.Messages.EnterRoomNotify msg, NetDataInput in) throws IOException {
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
	}
	private void _w(com.fc.lami.Messages.EnterRoomNotify msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.player);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterRoomRequest new_com_fc_lami_Messages_EnterRoomRequest(){return new com.fc.lami.Messages.EnterRoomRequest();}
	private void _r(com.fc.lami.Messages.EnterRoomRequest msg, NetDataInput in) throws IOException {
		msg.room_no = in.readInt();
	}
	private void _w(com.fc.lami.Messages.EnterRoomRequest msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.room_no);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.EnterRoomResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.EnterRoomResponse new_com_fc_lami_Messages_EnterRoomResponse(){return new com.fc.lami.Messages.EnterRoomResponse();}
	private void _r(com.fc.lami.Messages.EnterRoomResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
		msg.room = in.readExternal(com.fc.lami.Messages.RoomData.class);
	}
	private void _w(com.fc.lami.Messages.EnterRoomResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
		out.writeExternal(msg.room);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ExitRoomNotify new_com_fc_lami_Messages_ExitRoomNotify(){return new com.fc.lami.Messages.ExitRoomNotify();}
	private void _r(com.fc.lami.Messages.ExitRoomNotify msg, NetDataInput in) throws IOException {
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
		msg.curRoom = in.readExternal(com.fc.lami.Messages.RoomData.class);
		msg.curDesk = in.readExternal(com.fc.lami.Messages.DeskData.class);
	}
	private void _w(com.fc.lami.Messages.ExitRoomNotify msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.player);
		out.writeExternal(msg.curRoom);
		out.writeExternal(msg.curDesk);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ExitRoomRequest new_com_fc_lami_Messages_ExitRoomRequest(){return new com.fc.lami.Messages.ExitRoomRequest();}
	private void _r(com.fc.lami.Messages.ExitRoomRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.ExitRoomRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ExitRoomResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ExitRoomResponse new_com_fc_lami_Messages_ExitRoomResponse(){return new com.fc.lami.Messages.ExitRoomResponse();}
	private void _r(com.fc.lami.Messages.ExitRoomResponse msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.ExitRoomResponse msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameOverNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GameOverNotify new_com_fc_lami_Messages_GameOverNotify(){return new com.fc.lami.Messages.GameOverNotify();}
	private void _r(com.fc.lami.Messages.GameOverNotify msg, NetDataInput in) throws IOException {
		msg.game_over_type = in.readInt();
		msg.result_pak = (com.fc.lami.Messages.ResultPak[])in.readExternalArray(com.fc.lami.Messages.ResultPak.class);
	}
	private void _w(com.fc.lami.Messages.GameOverNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.game_over_type);
		out.writeExternalArray(msg.result_pak);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GameStartNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GameStartNotify new_com_fc_lami_Messages_GameStartNotify(){return new com.fc.lami.Messages.GameStartNotify();}
	private void _r(com.fc.lami.Messages.GameStartNotify msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
	}
	private void _w(com.fc.lami.Messages.GameStartNotify msg, NetDataOutput out) throws IOException {
		out.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GetCardNotify new_com_fc_lami_Messages_GetCardNotify(){return new com.fc.lami.Messages.GetCardNotify();}
	private void _r(com.fc.lami.Messages.GetCardNotify msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
	}
	private void _w(com.fc.lami.Messages.GetCardNotify msg, NetDataOutput out) throws IOException {
		out.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GetCardRequest new_com_fc_lami_Messages_GetCardRequest(){return new com.fc.lami.Messages.GetCardRequest();}
	private void _r(com.fc.lami.Messages.GetCardRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.GetCardRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GetCardResponse new_com_fc_lami_Messages_GetCardResponse(){return new com.fc.lami.Messages.GetCardResponse();}
	private void _r(com.fc.lami.Messages.GetCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.GetCardResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GetTimeRequest new_com_fc_lami_Messages_GetTimeRequest(){return new com.fc.lami.Messages.GetTimeRequest();}
	private void _r(com.fc.lami.Messages.GetTimeRequest msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.GetTimeRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.GetTimeResponse new_com_fc_lami_Messages_GetTimeResponse(){return new com.fc.lami.Messages.GetTimeResponse();}
	private void _r(com.fc.lami.Messages.GetTimeResponse msg, NetDataInput in) throws IOException {
		msg.time = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.GetTimeResponse msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LeaveDeskNotify new_com_fc_lami_Messages_LeaveDeskNotify(){return new com.fc.lami.Messages.LeaveDeskNotify();}
	private void _r(com.fc.lami.Messages.LeaveDeskNotify msg, NetDataInput in) throws IOException {
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
		msg.desk = in.readExternal(com.fc.lami.Messages.DeskData.class);
		msg.seatID = in.readInt();
	}
	private void _w(com.fc.lami.Messages.LeaveDeskNotify msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.player);
		out.writeExternal(msg.desk);
		out.writeInt(msg.seatID);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LeaveDeskRequest new_com_fc_lami_Messages_LeaveDeskRequest(){return new com.fc.lami.Messages.LeaveDeskRequest();}
	private void _r(com.fc.lami.Messages.LeaveDeskRequest msg, NetDataInput in) throws IOException {
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
		msg.desk = in.readExternal(com.fc.lami.Messages.DeskData.class);
		msg.seatID = in.readInt();
	}
	private void _w(com.fc.lami.Messages.LeaveDeskRequest msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.player);
		out.writeExternal(msg.desk);
		out.writeInt(msg.seatID);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LeaveDeskResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LeaveDeskResponse new_com_fc_lami_Messages_LeaveDeskResponse(){return new com.fc.lami.Messages.LeaveDeskResponse();}
	private void _r(com.fc.lami.Messages.LeaveDeskResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.LeaveDeskResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LoginRequest new_com_fc_lami_Messages_LoginRequest(){return new com.fc.lami.Messages.LoginRequest();}
	private void _r(com.fc.lami.Messages.LoginRequest msg, NetDataInput in) throws IOException {
		msg.name = in.readUTF();
	}
	private void _w(com.fc.lami.Messages.LoginRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.name);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LoginResponse new_com_fc_lami_Messages_LoginResponse(){return new com.fc.lami.Messages.LoginResponse();}
	private void _r(com.fc.lami.Messages.LoginResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
		msg.player = in.readExternal(com.fc.lami.Messages.PlayerData.class);
		msg.rooms = (com.fc.lami.Messages.RoomData[])in.readExternalArray(com.fc.lami.Messages.RoomData.class);
	}
	private void _w(com.fc.lami.Messages.LoginResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
		out.writeExternal(msg.player);
		out.writeExternalArray(msg.rooms);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.LogoutRequest new_com_fc_lami_Messages_LogoutRequest(){return new com.fc.lami.Messages.LogoutRequest();}
	private void _r(com.fc.lami.Messages.LogoutRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.LogoutRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MainMatrixChangeNotify new_com_fc_lami_Messages_MainMatrixChangeNotify(){return new com.fc.lami.Messages.MainMatrixChangeNotify();}
	private void _r(com.fc.lami.Messages.MainMatrixChangeNotify msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
	}
	private void _w(com.fc.lami.Messages.MainMatrixChangeNotify msg, NetDataOutput out) throws IOException {
		out.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MainMatrixChangeRequest new_com_fc_lami_Messages_MainMatrixChangeRequest(){return new com.fc.lami.Messages.MainMatrixChangeRequest();}
	private void _r(com.fc.lami.Messages.MainMatrixChangeRequest msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
	}
	private void _w(com.fc.lami.Messages.MainMatrixChangeRequest msg, NetDataOutput out) throws IOException {
		out.writeExternalArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MainMatrixChangeResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MainMatrixChangeResponse new_com_fc_lami_Messages_MainMatrixChangeResponse(){return new com.fc.lami.Messages.MainMatrixChangeResponse();}
	private void _r(com.fc.lami.Messages.MainMatrixChangeResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MainMatrixChangeResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardNotify new_com_fc_lami_Messages_MoveCardNotify(){return new com.fc.lami.Messages.MoveCardNotify();}
	private void _r(com.fc.lami.Messages.MoveCardNotify msg, NetDataInput in) throws IOException {
		msg.cards = in.readIntArray();
		msg.nx = in.readInt();
		msg.ny = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MoveCardNotify msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.cards);
		out.writeInt(msg.nx);
		out.writeInt(msg.ny);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardRequest new_com_fc_lami_Messages_MoveCardRequest(){return new com.fc.lami.Messages.MoveCardRequest();}
	private void _r(com.fc.lami.Messages.MoveCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = in.readIntArray();
		msg.nx = in.readInt();
		msg.ny = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MoveCardRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.cards);
		out.writeInt(msg.nx);
		out.writeInt(msg.ny);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardResponse new_com_fc_lami_Messages_MoveCardResponse(){return new com.fc.lami.Messages.MoveCardResponse();}
	private void _r(com.fc.lami.Messages.MoveCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MoveCardResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OpenIceNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.OpenIceNotify new_com_fc_lami_Messages_OpenIceNotify(){return new com.fc.lami.Messages.OpenIceNotify();}
	private void _r(com.fc.lami.Messages.OpenIceNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.OpenIceNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.PlayerData
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.PlayerData new_com_fc_lami_Messages_PlayerData(){return new com.fc.lami.Messages.PlayerData();}
	private void _r(com.fc.lami.Messages.PlayerData msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.name = in.readUTF();
		msg.score = in.readInt();
		msg.win = in.readInt();
		msg.lose = in.readInt();
		msg.level = in.readInt();
	}
	private void _w(com.fc.lami.Messages.PlayerData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeUTF(msg.name);
		out.writeInt(msg.score);
		out.writeInt(msg.win);
		out.writeInt(msg.lose);
		out.writeInt(msg.level);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ReadyNotify new_com_fc_lami_Messages_ReadyNotify(){return new com.fc.lami.Messages.ReadyNotify();}
	private void _r(com.fc.lami.Messages.ReadyNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.isReady = in.readBoolean();
	}
	private void _w(com.fc.lami.Messages.ReadyNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ReadyRequest new_com_fc_lami_Messages_ReadyRequest(){return new com.fc.lami.Messages.ReadyRequest();}
	private void _r(com.fc.lami.Messages.ReadyRequest msg, NetDataInput in) throws IOException {
		msg.isReady = in.readBoolean();
	}
	private void _w(com.fc.lami.Messages.ReadyRequest msg, NetDataOutput out) throws IOException {
		out.writeBoolean(msg.isReady);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ReadyResponse new_com_fc_lami_Messages_ReadyResponse(){return new com.fc.lami.Messages.ReadyResponse();}
	private void _r(com.fc.lami.Messages.ReadyResponse msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.ReadyResponse msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RepealSendCardNotify new_com_fc_lami_Messages_RepealSendCardNotify(){return new com.fc.lami.Messages.RepealSendCardNotify();}
	private void _r(com.fc.lami.Messages.RepealSendCardNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.cds = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
	}
	private void _w(com.fc.lami.Messages.RepealSendCardNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeExternalArray(msg.cds);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RepealSendCardRequest new_com_fc_lami_Messages_RepealSendCardRequest(){return new com.fc.lami.Messages.RepealSendCardRequest();}
	private void _r(com.fc.lami.Messages.RepealSendCardRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.RepealSendCardRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RepealSendCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RepealSendCardResponse new_com_fc_lami_Messages_RepealSendCardResponse(){return new com.fc.lami.Messages.RepealSendCardResponse();}
	private void _r(com.fc.lami.Messages.RepealSendCardResponse msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.RepealSendCardResponse msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ResultPak
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ResultPak new_com_fc_lami_Messages_ResultPak(){return new com.fc.lami.Messages.ResultPak();}
	private void _r(com.fc.lami.Messages.ResultPak msg, NetDataInput in) throws IOException {
		msg.point = in.readInt();
		msg.is_win = in.readBoolean();
	}
	private void _w(com.fc.lami.Messages.ResultPak msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.point);
		out.writeBoolean(msg.is_win);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardNotify new_com_fc_lami_Messages_RetakeCardNotify(){return new com.fc.lami.Messages.RetakeCardNotify();}
	private void _r(com.fc.lami.Messages.RetakeCardNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.x = in.readInt();
		msg.y = in.readInt();
		msg.n = in.readInt();
	}
	private void _w(com.fc.lami.Messages.RetakeCardNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeInt(msg.x);
		out.writeInt(msg.y);
		out.writeInt(msg.n);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardRequest new_com_fc_lami_Messages_RetakeCardRequest(){return new com.fc.lami.Messages.RetakeCardRequest();}
	private void _r(com.fc.lami.Messages.RetakeCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = in.readIntArray();
	}
	private void _w(com.fc.lami.Messages.RetakeCardRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardResponse new_com_fc_lami_Messages_RetakeCardResponse(){return new com.fc.lami.Messages.RetakeCardResponse();}
	private void _r(com.fc.lami.Messages.RetakeCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.RetakeCardResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RoomData
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RoomData new_com_fc_lami_Messages_RoomData(){return new com.fc.lami.Messages.RoomData();}
	private void _r(com.fc.lami.Messages.RoomData msg, NetDataInput in) throws IOException {
		msg.room_id = in.readInt();
		msg.desks = (com.fc.lami.Messages.DeskData[])in.readExternalArray(com.fc.lami.Messages.DeskData.class);
		msg.players = (com.fc.lami.Messages.PlayerData[])in.readExternalArray(com.fc.lami.Messages.PlayerData.class);
	}
	private void _w(com.fc.lami.Messages.RoomData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.room_id);
		out.writeExternalArray(msg.desks);
		out.writeExternalArray(msg.players);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardNotify new_com_fc_lami_Messages_SendCardNotify(){return new com.fc.lami.Messages.SendCardNotify();}
	private void _r(com.fc.lami.Messages.SendCardNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.cards = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
		msg.x = in.readInt();
		msg.y = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SendCardNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeExternalArray(msg.cards);
		out.writeInt(msg.x);
		out.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardRequest new_com_fc_lami_Messages_SendCardRequest(){return new com.fc.lami.Messages.SendCardRequest();}
	private void _r(com.fc.lami.Messages.SendCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = in.readIntArray();
		msg.x = in.readInt();
		msg.y = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SendCardRequest msg, NetDataOutput out) throws IOException {
		out.writeIntArray(msg.cards);
		out.writeInt(msg.x);
		out.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardResponse new_com_fc_lami_Messages_SendCardResponse(){return new com.fc.lami.Messages.SendCardResponse();}
	private void _r(com.fc.lami.Messages.SendCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SendCardResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SubmitRequest new_com_fc_lami_Messages_SubmitRequest(){return new com.fc.lami.Messages.SubmitRequest();}
	private void _r(com.fc.lami.Messages.SubmitRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.SubmitRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SubmitResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SubmitResponse new_com_fc_lami_Messages_SubmitResponse(){return new com.fc.lami.Messages.SubmitResponse();}
	private void _r(com.fc.lami.Messages.SubmitResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SubmitResponse msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SynchronizeRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SynchronizeRequest new_com_fc_lami_Messages_SynchronizeRequest(){return new com.fc.lami.Messages.SynchronizeRequest();}
	private void _r(com.fc.lami.Messages.SynchronizeRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.SynchronizeRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SynchronizeResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SynchronizeResponse new_com_fc_lami_Messages_SynchronizeResponse(){return new com.fc.lami.Messages.SynchronizeResponse();}
	private void _r(com.fc.lami.Messages.SynchronizeResponse msg, NetDataInput in) throws IOException {
		msg.matrix = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
		msg.player_card = (com.fc.lami.Messages.CardData[])in.readExternalArray(com.fc.lami.Messages.CardData.class);
		msg.left_card = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SynchronizeResponse msg, NetDataOutput out) throws IOException {
		out.writeExternalArray(msg.matrix);
		out.writeExternalArray(msg.player_card);
		out.writeInt(msg.left_card);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnEndNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.TurnEndNotify new_com_fc_lami_Messages_TurnEndNotify(){return new com.fc.lami.Messages.TurnEndNotify();}
	private void _r(com.fc.lami.Messages.TurnEndNotify msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.TurnEndNotify msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.TurnStartNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.TurnStartNotify new_com_fc_lami_Messages_TurnStartNotify(){return new com.fc.lami.Messages.TurnStartNotify();}
	private void _r(com.fc.lami.Messages.TurnStartNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.TurnStartNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}


}