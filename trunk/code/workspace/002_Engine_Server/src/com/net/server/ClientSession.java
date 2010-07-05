package com.net.server;

import com.net.AbstractSession;
import com.net.MessageHeader;


public interface ClientSession extends AbstractSession
{
	public String 					getName();

	public void 					send(MessageHeader request, MessageHeader response);
	
	public Server					getServer();
	
	public ClientSessionListener	getListener();
	
	public boolean					send(MessageHeader message);
	
}
