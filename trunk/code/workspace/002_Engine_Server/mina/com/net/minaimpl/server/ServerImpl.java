package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.SystemMessages.ServerStatusRequestC2S;
import com.net.minaimpl.SystemMessages.ServerStatusResponseS2C;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.ServerListener;


public class ServerImpl extends AbstractServer
{
	final private ChannelManager	channel_manager;
	final ReentrantReadWriteLock	session_rw_lock	= new ReentrantReadWriteLock();

//	----------------------------------------------------------------------------------------------------------------------
	
	public ServerImpl() 
	{
		this(Thread.currentThread().getContextClassLoader(), 
				null,
				Runtime.getRuntime().availableProcessors() + 1, 
				Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public ServerImpl(
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds) 
	{
		this(Thread.currentThread().getContextClassLoader(), 
				null,
				ioProcessCount, 
				sessionWriteIdleTimeSeconds, 
				sessionReadIdleTimeSeconds);
	}
	
	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public ServerImpl(
			ClassLoader 			cl,
			ExternalizableFactory 	ef,
			int 					ioProcessCount, 
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds) 
	{
		super(cl, ef, ioProcessCount, sessionWriteIdleTimeSeconds, sessionReadIdleTimeSeconds);
		this.channel_manager				= new ChannelManagerImpl(this);
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------------

	public ChannelManager getChannelManager() {
		return channel_manager;
	}
	
	private ClientSessionImpl getBindSession(IoSession session)
	{
		session_rw_lock.readLock().lock();
		try {
			Object obj = session.getAttribute(SessionAttributeKey.CLIENT_SESSION);
			if (obj instanceof ClientSessionImpl) {
				return (ClientSessionImpl) obj;
			}
		} finally {
			session_rw_lock.readLock().unlock();
		}
		return null;
	}
	
	
	public Iterator<ClientSession> getSessions() {
		session_rw_lock.readLock().lock();
		try{
			ArrayList<ClientSession> sessions = new ArrayList<ClientSession>(Acceptor.getManagedSessionCount());
			for (IoSession session : Acceptor.getManagedSessions().values()){
				ClientSessionImpl client = getBindSession(session);
				if (client!=null){
					sessions.add(client);
				}
			}
			return sessions.iterator();
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public ClientSession getSession(long sessionID) {
		session_rw_lock.readLock().lock();
		try{
			IoSession session = Acceptor.getManagedSessions().get(sessionID);
			ClientSessionImpl client = getBindSession(session);
			if (client!=null){
				return client;
			}
			return null;
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public ClientSession getSession(Object object){
		session_rw_lock.readLock().lock();
		try{
			for (IoSession session : Acceptor.getManagedSessions().values()){
				ClientSessionImpl client = getBindSession(session);
				if (object.equals(client)) {
					return client;
				}
			}
			return null;
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public boolean hasSession(ClientSession session) {
		session_rw_lock.readLock().lock();
		try{
			return Acceptor.getManagedSessions().containsKey(session.getID());
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public int getSessionCount() {
		session_rw_lock.readLock().lock();
		try{
			return Acceptor.getManagedSessionCount();
		} finally {
			session_rw_lock.readLock().unlock();
		}
	}
	
	public void broadcast(MessageHeader message){
		message.Protocol 		= MessageHeader.PROTOCOL_SESSION_MESSAGE;
		Acceptor.broadcast(message);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
		ClientSessionImpl client = new ClientSessionImpl(session, this);
		session_rw_lock.writeLock().lock();
		try{
			session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
		} finally {
			session_rw_lock.writeLock().unlock();
		}
		client.setListener(SrvListener.connected(client));
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
		ClientSessionImpl client = getBindSession(session);
		session.removeAttribute(SessionAttributeKey.CLIENT_SESSION);
		if (client != null) {
			try {
				Iterator<Channel> channels = channel_manager.getChannels();
				while (channels.hasNext()) {
					channels.next().leave(client);
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
			try {
				if (client.Listener != null) {
					client.Listener.disconnected(client);
				}
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
			}
		}
	}
	
	public void messageReceived(final IoSession session, final Object message) throws Exception 
	{
		ClientSessionImpl client = getBindSession(session);
		if (client == null) {
			log.error("client is expire : " + session);
			return;
		}
		
		//System.out.println(Thread.currentThread().getName() + " messageReceived");
		if (message instanceof MessageHeader)
		{
			if (client.Listener != null)
			{
				MessageHeader header = (MessageHeader)message;
				
				switch (header.Protocol)
				{
				case MessageHeader.PROTOCOL_CHANNEL_MESSAGE:
					ChannelImpl channel = (ChannelImpl)channel_manager.getChannel(header.ChannelID);
					if (channel != null) {
						channel.getChannelListener().receivedMessage(channel, client, header);
					}
					break;
					
				case MessageHeader.PROTOCOL_SESSION_MESSAGE:
					// 默认给系统消息回馈
					if (message instanceof ServerStatusRequestC2S) {
						client.send(header, new ServerStatusResponseS2C(this));
					}
					else {
						client.Listener.receivedMessage(client, header);
					}
					break;
					
				default:
					log.error("unknow message : " + session + " : " + message);
				}
			}
		}
		else
		{
			log.error("bad message type : " + session + " : " + message);
		}
		
	}
	
	
	
}
