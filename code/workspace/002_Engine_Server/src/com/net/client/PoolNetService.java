package com.net.client;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

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

	final private ReentrantLock 		session_lock = new ReentrantLock();
	final private ServerSession[] 		sessions;
	
	public PoolNetService(
			ThreadPool				thread_pool, 
			PoolNetServiceAdapter	adapter,
			int						session_count)
	{
		super(thread_pool);
		this.adapter	= adapter;
		this.sessions	= new ServerSession[session_count];
		this.getSession();
	}
	
	@Override
	public void close(boolean force) {
		session_lock.lock();
		try{
			for (ServerSession session : sessions) {
				if (session != null) {
					session.disconnect(false);
				}
			}
		}finally{
			session_lock.unlock();
		}
	}
	
	private ServerSession getSession() {
		session_lock.lock();
		try{
			for (int i=0; i<sessions.length; i++) {
				if (sessions[i] != null) {
					if (sessions[i].isConnected()) {
						return sessions[i];
					}
				} else {
					sessions[i] = adapter.createServerSession(this, getSessionListener());
				}
			}
		}finally{
			session_lock.unlock();
		}
		return null;
	}
	
	public void send(MessageHeader message) {
		sendlock.lock();
		try{
			ServerSession session = getSession();
		 	if (session!=null && session.isConnected()) {
	    		session.send(message);
			}else{
				log.error("no session found !");
			}
		}finally{
			sendlock.unlock();
		}
	}
	
}
