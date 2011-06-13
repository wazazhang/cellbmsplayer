package com.fc.lami.model;

import com.fc.lami.LamiServerListener;
import com.net.MessageHeader;
import com.net.flash.message.FlashMessage;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;

/**
 * 大厅
 * 没进房间的玩家在这里
 * @author yagami0079
 *
 */
public class Hall implements ChannelListener
{

	final private Channel		channel;
	
	public Hall(LamiServerListener server){
		channel = server.createChannel(this); 
	}
	
	public void broadcast(FlashMessage msg)
	{
		channel.send(msg);
	}

	public void leave(ClientSession session){
		channel.leave(session);
	}
	
	public void join(ClientSession session){
		channel.join(session);
	}
	
	@Override
	public void receivedMessage(Channel channel, ClientSession sender,
			MessageHeader message) {
		
	}

	@Override
	public void sessionLeaved(Channel channel, ClientSession session) {
	}

	@Override
	public void sessionJoined(Channel channel, ClientSession session) {
	}

}
