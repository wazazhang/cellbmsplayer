package com.net.client;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;

/**
 * 有固定数量链接的服务
 * @see NetService
 * @author WAZA
 */
public class PoolNetService extends BasicNetService
{
	final private PoolNetServiceAdapter	adapter;

	final private ServerSession[] 		sessions;
	
	public PoolNetService(
			ThreadPool				thread_pool, 
			PoolNetServiceAdapter	adapter,
			int						session_count)
	{
		super(thread_pool);
		this.adapter	= adapter;
		this.sessions	= new ServerSession[session_count];
		for (int i = 0; i < sessions.length; i++) {
			sessions[i] = adapter.createServerSession(this, getSessionListener());
		}
	}
	
	@Override
	public void close(boolean force) {
		for (ServerSession session : sessions) {
			if (session != null) {
				session.disconnect(false);
			}
		}
	}
	
	protected ServerSession getSession() {
		for (int i = 0; i < sessions.length; i++) {
			if (sessions[i].isConnected()) {
				return sessions[i];
			} else {
				adapter.reconnect(this, getSessionListener());
			}
		}
		return null;
	}
	
	public void send(MessageHeader message) {
		ServerSession session = getSession();
		session.send(message);
	}
	
}
