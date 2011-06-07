package com.slg.net.server;

import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.slg.IPlayer;
import com.slg.net.impl.PlayerImpl;
import com.slg.net.messages.Messages.LogoutRequest;

public class EchoClientSession implements ClientSessionListener
{
	final public ClientSession 	session;
	final public SlgServerListener 		server;
	final public PlayerImpl 	player;
	
	public EchoClientSession(ClientSession session, SlgServerListener server, IPlayer player) {
		this.session = session;
		this.server = server;
		this.player = (PlayerImpl)player;
	}
	
	@Override
	public void disconnected(ClientSession session) {

	}
	
	@Override
	public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {}
	
	@Override
	public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) 
	{
		//退出请求
		if (message instanceof LogoutRequest){
			session.disconnect(false);
//			disconnected(session); // 这方法不能直接用，这是后台检查到链接断开后的call back
		}

		System.out.println(message.toString());
	}
	
}
