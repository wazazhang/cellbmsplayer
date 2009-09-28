package com.net.server;

import java.net.SocketAddress;
import java.util.Set;

import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;


public interface ClientSession
{
	public void disconnect(boolean force);
	
	public long getID();
	
	public String getName();
	
	public SocketAddress getAddress();
	
	public boolean isConnected();
	
	public void send(MessageHeader message);

	public void send(MessageHeader request, MessageHeader response);
	
	

	public Object getAttribute(Object key);

	public Object setAttribute(Object key, Object value);

	public Object removeAttribute(Object key);

	public boolean containsAttribute(Object key);

	public Set<Object> getAttributeKeys();
	
	public Server	getServer();
	
	
	public long		getIdleDuration();
	public void 	startHeartBeat(ThreadPool pool, final long heartbeat_timeout);
	public void 	stopHeartBeat() ;
	
}
