package com.net.server;

import com.cell.util.concurrent.ThreadPool;
import com.net.AbstractSession;
import com.net.MessageHeader;


public interface ClientSession extends AbstractSession
{
	public String 					getName();

	public void 					send(MessageHeader request, MessageHeader response);
	
	public Server					getServer();
	
	public ClientSessionListener	getListener();

	public void 					startHeartBeat(ThreadPool pool, final long heartbeat_timeout);
	
	public void 					stopHeartBeat() ;
	
}
