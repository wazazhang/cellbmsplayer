package com.net.client;


public abstract interface PoolNetServiceAdapter
{
	abstract public ServerSession createServerSession(PoolNetService service, ServerSessionListener listener);
	
	abstract public ServerSession reconnect(PoolNetService service, ServerSessionListener listener);
}
