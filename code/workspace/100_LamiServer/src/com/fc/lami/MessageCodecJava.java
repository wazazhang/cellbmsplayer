package com.fc.lami;

import java.io.IOException;
import com.net.mutual.MutualMessage;
import com.net.mutual.MutualMessageCodec;
import com.net.NetDataInput;
import com.net.NetDataOutput;


/**
 * 此代码为自动生成。不需要在此修改。若有错误，请修改代码生成器。
 */
public class MessageCodecJava implements MutualMessageCodec
{
	public void readExternal(MutualMessage msg, NetDataInput in) throws IOException 
	{
		if (msg.getClass().equals(com.fc.lami.CardData.class)) {
			_r((com.fc.lami.CardData)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Desktop.class)) {
			_r((com.fc.lami.Desktop)msg, in); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardNotify.class)) {
			_r((com.fc.lami.Messages.MoveCardNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardRequest.class)) {
			_r((com.fc.lami.Messages.MoveCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardResponse.class)) {
			_r((com.fc.lami.Messages.MoveCardResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OverRequest.class)) {
			_r((com.fc.lami.Messages.OverRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OverResponse.class)) {
			_r((com.fc.lami.Messages.OverResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.Player.class)) {
			_r((com.fc.lami.Messages.Player)msg, in); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardRequest.class)) {
			_r((com.fc.lami.Messages.RepealSendCardRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardResponse.class)) {
			_r((com.fc.lami.Messages.RepealSendCardResponse)msg, in); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.Room.class)) {
			_r((com.fc.lami.Messages.Room)msg, in); return;
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

	}

	public void writeExternal(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.fc.lami.CardData.class)) {
			_w((com.fc.lami.CardData)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Desktop.class)) {
			_w((com.fc.lami.Desktop)msg, out); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardNotify.class)) {
			_w((com.fc.lami.Messages.MoveCardNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardRequest.class)) {
			_w((com.fc.lami.Messages.MoveCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.MoveCardResponse.class)) {
			_w((com.fc.lami.Messages.MoveCardResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OverRequest.class)) {
			_w((com.fc.lami.Messages.OverRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.OverResponse.class)) {
			_w((com.fc.lami.Messages.OverResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.Player.class)) {
			_w((com.fc.lami.Messages.Player)msg, out); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardRequest.class)) {
			_w((com.fc.lami.Messages.RepealSendCardRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.RepealSendCardResponse.class)) {
			_w((com.fc.lami.Messages.RepealSendCardResponse)msg, out); return;
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
		if (msg.getClass().equals(com.fc.lami.Messages.Room.class)) {
			_w((com.fc.lami.Messages.Room)msg, out); return;
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

	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.CardData
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.CardData new_com_fc_lami_CardData(){return new com.fc.lami.CardData();}
	private void _r(com.fc.lami.CardData msg, NetDataInput in) throws IOException {
		msg.point = in.readInt();
		msg.type = in.readInt();
		msg.x = in.readInt();
		msg.y = in.readInt();
	}
	private void _w(com.fc.lami.CardData msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.point);
		out.writeInt(msg.type);
		out.writeInt(msg.x);
		out.writeInt(msg.y);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Desktop
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Desktop new_com_fc_lami_Desktop(){return new com.fc.lami.Desktop();}
	private void _r(com.fc.lami.Desktop msg, NetDataInput in) throws IOException {
		msg.matrix = (com.fc.lami.CardData[][])in.readAnyArray(com.fc.lami.CardData[][].class);
		msg.matrix_old = (com.fc.lami.CardData[][])in.readAnyArray(com.fc.lami.CardData[][].class);
	}
	private void _w(com.fc.lami.Desktop msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.matrix);
		out.writeAnyArray(msg.matrix_old);
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
		msg.card = in.readExternal(com.fc.lami.CardData.class);
	}
	private void _w(com.fc.lami.Messages.GetCardResponse msg, NetDataOutput out) throws IOException {
		out.writeExternal(msg.card);
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
//	com.fc.lami.Messages.MoveCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardNotify new_com_fc_lami_Messages_MoveCardNotify(){return new com.fc.lami.Messages.MoveCardNotify();}
	private void _r(com.fc.lami.Messages.MoveCardNotify msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
		msg.nx = in.readInt();
		msg.ny = in.readInt();
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MoveCardNotify msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
		out.writeInt(msg.nx);
		out.writeInt(msg.ny);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardRequest new_com_fc_lami_Messages_MoveCardRequest(){return new com.fc.lami.Messages.MoveCardRequest();}
	private void _r(com.fc.lami.Messages.MoveCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
		msg.nx = in.readInt();
		msg.ny = in.readInt();
	}
	private void _w(com.fc.lami.Messages.MoveCardRequest msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
		out.writeInt(msg.nx);
		out.writeInt(msg.ny);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.MoveCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.MoveCardResponse new_com_fc_lami_Messages_MoveCardResponse(){return new com.fc.lami.Messages.MoveCardResponse();}
	private void _r(com.fc.lami.Messages.MoveCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readShort();
	}
	private void _w(com.fc.lami.Messages.MoveCardResponse msg, NetDataOutput out) throws IOException {
		out.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OverRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.OverRequest new_com_fc_lami_Messages_OverRequest(){return new com.fc.lami.Messages.OverRequest();}
	private void _r(com.fc.lami.Messages.OverRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.OverRequest msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.OverResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.OverResponse new_com_fc_lami_Messages_OverResponse(){return new com.fc.lami.Messages.OverResponse();}
	private void _r(com.fc.lami.Messages.OverResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readShort();
	}
	private void _w(com.fc.lami.Messages.OverResponse msg, NetDataOutput out) throws IOException {
		out.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.Player
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.Player new_com_fc_lami_Messages_Player(){return new com.fc.lami.Messages.Player();}
	private void _r(com.fc.lami.Messages.Player msg, NetDataInput in) throws IOException {
		msg.name = in.readUTF();
		msg.nextPlayer = in.readExternal(com.fc.lami.Messages.Player.class);
	}
	private void _w(com.fc.lami.Messages.Player msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.name);
		out.writeExternal(msg.nextPlayer);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ReadyNotify new_com_fc_lami_Messages_ReadyNotify(){return new com.fc.lami.Messages.ReadyNotify();}
	private void _r(com.fc.lami.Messages.ReadyNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.ReadyNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.ReadyRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.ReadyRequest new_com_fc_lami_Messages_ReadyRequest(){return new com.fc.lami.Messages.ReadyRequest();}
	private void _r(com.fc.lami.Messages.ReadyRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.fc.lami.Messages.ReadyRequest msg, NetDataOutput out) throws IOException {
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
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
	}
	private void _w(com.fc.lami.Messages.RepealSendCardResponse msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardNotify new_com_fc_lami_Messages_RetakeCardNotify(){return new com.fc.lami.Messages.RetakeCardNotify();}
	private void _r(com.fc.lami.Messages.RetakeCardNotify msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
	}
	private void _w(com.fc.lami.Messages.RetakeCardNotify msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeAnyArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardRequest new_com_fc_lami_Messages_RetakeCardRequest(){return new com.fc.lami.Messages.RetakeCardRequest();}
	private void _r(com.fc.lami.Messages.RetakeCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
	}
	private void _w(com.fc.lami.Messages.RetakeCardRequest msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.RetakeCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.RetakeCardResponse new_com_fc_lami_Messages_RetakeCardResponse(){return new com.fc.lami.Messages.RetakeCardResponse();}
	private void _r(com.fc.lami.Messages.RetakeCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readShort();
	}
	private void _w(com.fc.lami.Messages.RetakeCardResponse msg, NetDataOutput out) throws IOException {
		out.writeShort(msg.result);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.Room
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.Room new_com_fc_lami_Messages_Room(){return new com.fc.lami.Messages.Room();}
	private void _r(com.fc.lami.Messages.Room msg, NetDataInput in) throws IOException {
		msg.RoomId = in.readUTF();
		msg.Started = in.readBoolean();
	}
	private void _w(com.fc.lami.Messages.Room msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.RoomId);
		out.writeBoolean(msg.Started);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardNotify
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardNotify new_com_fc_lami_Messages_SendCardNotify(){return new com.fc.lami.Messages.SendCardNotify();}
	private void _r(com.fc.lami.Messages.SendCardNotify msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
		msg.player_id = in.readInt();
	}
	private void _w(com.fc.lami.Messages.SendCardNotify msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
		out.writeInt(msg.player_id);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardRequest
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardRequest new_com_fc_lami_Messages_SendCardRequest(){return new com.fc.lami.Messages.SendCardRequest();}
	private void _r(com.fc.lami.Messages.SendCardRequest msg, NetDataInput in) throws IOException {
		msg.cards = (com.fc.lami.CardData[])in.readAnyArray(com.fc.lami.CardData[].class);
	}
	private void _w(com.fc.lami.Messages.SendCardRequest msg, NetDataOutput out) throws IOException {
		out.writeAnyArray(msg.cards);
	}

//	----------------------------------------------------------------------------------------------------
//	com.fc.lami.Messages.SendCardResponse
//	----------------------------------------------------------------------------------------------------
	public com.fc.lami.Messages.SendCardResponse new_com_fc_lami_Messages_SendCardResponse(){return new com.fc.lami.Messages.SendCardResponse();}
	private void _r(com.fc.lami.Messages.SendCardResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readShort();
	}
	private void _w(com.fc.lami.Messages.SendCardResponse msg, NetDataOutput out) throws IOException {
		out.writeShort(msg.result);
	}


}
