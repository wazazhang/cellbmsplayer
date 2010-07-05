package com.net;


import java.net.SocketAddress;

import java.util.Set;

public interface AbstractSession 
{
	public long 			getID();
	
	public boolean			isConnected();
	
	public boolean			disconnect(boolean force);
	
	
	
	public long 			getSentMessageCount() ;
	public long 			getReceivedMessageCount () ;
	public long 			getSentBytes();
	public long 			getReceivedBytes();

	public Object 			getAttribute(Object key);
	public Object 			setAttribute(Object key, Object value);
	public Object 			removeAttribute(Object key);
	public boolean 			containsAttribute(Object key);
	public Set<Object>		getAttributeKeys();
	
}
