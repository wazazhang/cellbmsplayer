package com.net.client;

import java.io.IOException;

import com.net.MessageHeader;

public interface ServerSession 
{
	//public void setServerSessionListener(ServerSessionListener listener);
	
	public boolean	isConnected();
	public boolean	connect(String host, int port, long timeout, ServerSessionListener listener) throws IOException;
	public boolean	connect(String host, int port, ServerSessionListener listener) throws IOException;
	public boolean	disconnect(boolean force);
	public boolean	send(MessageHeader message) throws IOException;
	
	public long 	getSentMessageCount() ;
	public long 	getReceivedMessageCount () ;
	public long 	getSentBytes();
	public long 	getReceivedBytes();
	
	public long		getIdleDuration();
}
