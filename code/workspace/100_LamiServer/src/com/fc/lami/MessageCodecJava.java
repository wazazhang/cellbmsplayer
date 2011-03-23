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
		if (msg.getClass().equals(com.fc.lami.Messages.EchoNotify.class)) {
			_r((com.fc.lami.Messages.EchoNotify)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoRequest.class)) {
			_r((com.fc.lami.Messages.EchoRequest)msg, in); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoResponse.class)) {
			_r((com.fc.lami.Messages.EchoResponse)msg, in); return;
		}

	}

	public void writeExternal(MutualMessage msg, NetDataOutput out) throws IOException 
	{
		if (msg.getClass().equals(com.fc.lami.Messages.EchoNotify.class)) {
			_w((com.fc.lami.Messages.EchoNotify)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoRequest.class)) {
			_w((com.fc.lami.Messages.EchoRequest)msg, out); return;
		}
		if (msg.getClass().equals(com.fc.lami.Messages.EchoResponse.class)) {
			_w((com.fc.lami.Messages.EchoResponse)msg, out); return;
		}

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


}
