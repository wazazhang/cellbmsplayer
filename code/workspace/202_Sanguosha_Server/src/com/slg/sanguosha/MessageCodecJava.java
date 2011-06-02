package com.slg.sanguosha;

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
	public String getVersion() {
		return "Thu Jun 02 16:12:45 CST 2011";
	}

	public void readExternal(MutualMessage msg, NetDataInput in) throws IOException 
	{
		if (msg.getClass().equals(com.slg.entity.Currency.class)) {
			_r((com.slg.entity.Currency)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.entity.GuageNumber.class)) {
			_r((com.slg.entity.GuageNumber)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.entity.Player.class)) {
			_r((com.slg.entity.Player)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.GetTimeRequest.class)) {
			_r((com.slg.sanguosha.Messages.GetTimeRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.GetTimeResponse.class)) {
			_r((com.slg.sanguosha.Messages.GetTimeResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LoginRequest.class)) {
			_r((com.slg.sanguosha.Messages.LoginRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LoginResponse.class)) {
			_r((com.slg.sanguosha.Messages.LoginResponse)msg, in); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LogoutRequest.class)) {
			_r((com.slg.sanguosha.Messages.LogoutRequest)msg, in); return;
		}

	}

	public void writeExternal(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.slg.entity.Currency.class)) {
			_w((com.slg.entity.Currency)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.entity.GuageNumber.class)) {
			_w((com.slg.entity.GuageNumber)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.entity.Player.class)) {
			_w((com.slg.entity.Player)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.GetTimeRequest.class)) {
			_w((com.slg.sanguosha.Messages.GetTimeRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.GetTimeResponse.class)) {
			_w((com.slg.sanguosha.Messages.GetTimeResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LoginRequest.class)) {
			_w((com.slg.sanguosha.Messages.LoginRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LoginResponse.class)) {
			_w((com.slg.sanguosha.Messages.LoginResponse)msg, out); return;
		}
		if (msg.getClass().equals(com.slg.sanguosha.Messages.LogoutRequest.class)) {
			_w((com.slg.sanguosha.Messages.LogoutRequest)msg, out); return;
		}

	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Currency
//	----------------------------------------------------------------------------------------------------
	public com.slg.entity.Currency new_com_slg_entity_Currency(){return new com.slg.entity.Currency();}
	private void _r(com.slg.entity.Currency msg, NetDataInput in) throws IOException {
		msg.gold = in.readInt();
	}
	private void _w(com.slg.entity.Currency msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.gold);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.GuageNumber
//	----------------------------------------------------------------------------------------------------
	public com.slg.entity.GuageNumber new_com_slg_entity_GuageNumber(){return new com.slg.entity.GuageNumber();}
	private void _r(com.slg.entity.GuageNumber msg, NetDataInput in) throws IOException {
	}
	private void _w(com.slg.entity.GuageNumber msg, NetDataOutput out) throws IOException {
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.entity.Player
//	----------------------------------------------------------------------------------------------------
	public com.slg.entity.Player new_com_slg_entity_Player(){return new com.slg.entity.Player();}
	private void _r(com.slg.entity.Player msg, NetDataInput in) throws IOException {
		msg.player_id = in.readInt();
		msg.name = in.readUTF();
		msg.office = in.readInt();
		msg.exp = in.readExternal(com.slg.entity.GuageNumber.class);
		msg.level = in.readInt();
		msg.ap = in.readExternal(com.slg.entity.GuageNumber.class);
		msg.currency = in.readExternal(com.slg.entity.Currency.class);
	}
	private void _w(com.slg.entity.Player msg, NetDataOutput out) throws IOException {
		out.writeInt(msg.player_id);
		out.writeUTF(msg.name);
		out.writeInt(msg.office);
		out.writeExternal(msg.exp);
		out.writeInt(msg.level);
		out.writeExternal(msg.ap);
		out.writeExternal(msg.currency);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.GetTimeRequest
//	----------------------------------------------------------------------------------------------------
	public com.slg.sanguosha.Messages.GetTimeRequest new_com_slg_sanguosha_Messages_GetTimeRequest(){return new com.slg.sanguosha.Messages.GetTimeRequest();}
	private void _r(com.slg.sanguosha.Messages.GetTimeRequest msg, NetDataInput in) throws IOException {
		msg.message = in.readUTF();
	}
	private void _w(com.slg.sanguosha.Messages.GetTimeRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.message);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.GetTimeResponse
//	----------------------------------------------------------------------------------------------------
	public com.slg.sanguosha.Messages.GetTimeResponse new_com_slg_sanguosha_Messages_GetTimeResponse(){return new com.slg.sanguosha.Messages.GetTimeResponse();}
	private void _r(com.slg.sanguosha.Messages.GetTimeResponse msg, NetDataInput in) throws IOException {
		msg.time = in.readUTF();
	}
	private void _w(com.slg.sanguosha.Messages.GetTimeResponse msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.time);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LoginRequest
//	----------------------------------------------------------------------------------------------------
	public com.slg.sanguosha.Messages.LoginRequest new_com_slg_sanguosha_Messages_LoginRequest(){return new com.slg.sanguosha.Messages.LoginRequest();}
	private void _r(com.slg.sanguosha.Messages.LoginRequest msg, NetDataInput in) throws IOException {
		msg.name = in.readUTF();
		msg.validate = in.readUTF();
		msg.version = in.readUTF();
	}
	private void _w(com.slg.sanguosha.Messages.LoginRequest msg, NetDataOutput out) throws IOException {
		out.writeUTF(msg.name);
		out.writeUTF(msg.validate);
		out.writeUTF(msg.version);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LoginResponse
//	----------------------------------------------------------------------------------------------------
	public com.slg.sanguosha.Messages.LoginResponse new_com_slg_sanguosha_Messages_LoginResponse(){return new com.slg.sanguosha.Messages.LoginResponse();}
	private void _r(com.slg.sanguosha.Messages.LoginResponse msg, NetDataInput in) throws IOException {
		msg.result = in.readShort();
		msg.version = in.readUTF();
		msg.player_data = in.readExternal(com.slg.entity.Player.class);
	}
	private void _w(com.slg.sanguosha.Messages.LoginResponse msg, NetDataOutput out) throws IOException {
		out.writeShort(msg.result);
		out.writeUTF(msg.version);
		out.writeExternal(msg.player_data);
	}

//	----------------------------------------------------------------------------------------------------
//	com.slg.sanguosha.Messages.LogoutRequest
//	----------------------------------------------------------------------------------------------------
	public com.slg.sanguosha.Messages.LogoutRequest new_com_slg_sanguosha_Messages_LogoutRequest(){return new com.slg.sanguosha.Messages.LogoutRequest();}
	private void _r(com.slg.sanguosha.Messages.LogoutRequest msg, NetDataInput in) throws IOException {
	}
	private void _w(com.slg.sanguosha.Messages.LogoutRequest msg, NetDataOutput out) throws IOException {
	}


}
