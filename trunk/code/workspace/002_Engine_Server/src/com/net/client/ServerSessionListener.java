package com.net.client;

import com.net.MessageHeader;


public interface ServerSessionListener
{
	
	public void connected(ServerSession session);
	
	public void disconnected(ServerSession session, boolean graceful, String reason);

	
	public void sentMessage(ServerSession session, MessageHeader message);

	
    public void joinedChannel(ClientChannel channel) ;
	
    public void leftChannel(ClientChannel channel);
 
	
	public void receivedMessage(ServerSession session, MessageHeader message);
	
    public void receivedChannelMessage(ClientChannel channel, MessageHeader message);
        

}
