package com.net.minaimpl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.*;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.Server;
import com.net.server.ServerListener;


public class ServerImpl extends IoHandlerAdapter implements Server
{
	/** 处理线程的数目 */
	final private int 			IoProcessCount;
	final private int			SessionReadIdleTimeSeconds;
	final private int			SessionWriteIdleTimeSeconds;

	// 
	private ServerListener 		SrvListener;
	private IoAcceptor 			Acceptor;
	private NetPackageCodec 	Codec;	
	private long 				StartTime;

	
	final private ChannelManager	channel_manager;
	final private Logger 			log 			= LoggerFactory.getLogger(getClass().getName());
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
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds) 
	{
		this.channel_manager				= new ChannelManagerImpl(this);
		this.IoProcessCount					= ioProcessCount;
		this.SessionWriteIdleTimeSeconds	= sessionWriteIdleTimeSeconds;
		this.SessionReadIdleTimeSeconds		= sessionReadIdleTimeSeconds;
		this.Codec							= new NetPackageCodec(cl, ef);
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int port, ServerListener listener) throws IOException 
	{
		if (Acceptor==null)
		{
			log.info("starting server at port : " + port);
			
			StartTime 	= System.currentTimeMillis();
			SrvListener	= listener;
			Acceptor	= new NioSocketAcceptor(IoProcessCount);
			
			Acceptor.getSessionConfig().setReaderIdleTime(SessionReadIdleTimeSeconds);
			Acceptor.getSessionConfig().setWriterIdleTime(SessionWriteIdleTimeSeconds);
//			Acceptor.setCloseOnDeactivation(true);
			Acceptor.setHandler(this);
			Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			
			Acceptor.bind(new InetSocketAddress(port));
			
			log.info("server started !");
		}
		else {
			log.info("Server already open !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (Acceptor!=null) 
		{
			log.info("server closing...");
			Acceptor.unbind();
			removeAllSession();
			Acceptor.dispose();
			Acceptor = null;
			log.info("server closed !");
		}
		else{
			log.info("Server is not open !");
		}
	}

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
	
	private void removeAllSession()
	{
		for (IoSession session : Acceptor.getManagedSessions().values()){
			session.close(false);
		}
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

	public long getSentMessageCount() {
		return Codec.SendedMessageCount;
	}
	
	public long getReceivedMessageCount () {
		return Codec.ReceivedMessageCount;
	}
	
	public long getSentBytes(){
		return Codec.TotalSentBytes;
	}
	
	public long getReceivedBytes(){
		return Codec.TotalReceivedBytes;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session, cause);
		session.close(false);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info("sessionIdle : " + session + " : " + status);
		session.close(false);
	}
	
	public void sessionOpened(IoSession session) throws Exception {
		log.info("sessionOpened : " + session);
		session_rw_lock.writeLock().lock();
		try{
			ClientSessionImpl client = new ClientSessionImpl(session, this);
			session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
			client.LastHartBeatTime = System.currentTimeMillis();
			client.setListener(SrvListener.connected(client));
		} finally {
			session_rw_lock.writeLock().unlock();
		}
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.info("sessionClosed : " + session);
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
			log.error("client is expire !");
			return;
		}
		
		//System.out.println(Thread.currentThread().getName() + " messageReceived");
		if (message instanceof MessageHeader)
		{
			client.LastHartBeatTime = System.currentTimeMillis();
			
			if (client.Listener != null)
			{
				MessageHeader header = (MessageHeader)message;
				
				switch (header.Protocol)
				{
				case MessageHeader.PROTOCOL_CHANNEL_MESSAGE:
					ChannelImpl channel = (ChannelImpl)channel_manager.getChannel(header.ChannelID);
					channel.getChannelListener().receivedMessage(channel, client, header);
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
					log.error("unknow message : " + message);
				}
			}
		}
		else
		{
			log.error("bad message type : " + message);
		}
		
	}
	
	
	
}
