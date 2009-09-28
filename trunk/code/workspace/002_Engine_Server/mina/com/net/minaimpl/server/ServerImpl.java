package com.net.minaimpl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

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
	private static final Logger _log = LoggerFactory.getLogger(ServerImpl.class.getName());
	
	/** 处理线程的数目 */
	final private int 			IoProcessCount;
	
	// 
	private ServerListener 		SrvListener;
	private IoAcceptor 			Acceptor;
	private NetPackageCodec 	Codec;

	public int					SessionIdleTimeSeconds = 100;
	public long 				StartTime;

	final ChannelManager		channel_manager;
	final ReentrantLock			session_lock	= new ReentrantLock();
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public ServerImpl() 
	{
		this(Thread.currentThread().getContextClassLoader(), null,
				Runtime.getRuntime().availableProcessors() + 1, 
				100);
	}

	public ServerImpl(
			int ioProcessCount, 
			int sessionIdleTimeSeconds) 
	{
		this(Thread.currentThread().getContextClassLoader(), null,
				ioProcessCount, sessionIdleTimeSeconds);
	}
	
	public ServerImpl(
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount, 
			int sessionIdleTimeSeconds) 
	{
		channel_manager			= new ChannelManagerImpl(this);
		IoProcessCount			= ioProcessCount;
		SessionIdleTimeSeconds	= sessionIdleTimeSeconds;
		Codec					= new NetPackageCodec(cl, ef);
	}
	
//	----------------------------------------------------------------------------------------------------------------------

	synchronized public void open(int port, ServerListener listener) throws IOException 
	{
		if (Acceptor==null)
		{
			_log.info("starting server at port : " + port);
			
			StartTime 	= System.currentTimeMillis();
			SrvListener	= listener;
			Acceptor	= new NioSocketAcceptor(IoProcessCount);
			
			Acceptor.getSessionConfig().setBothIdleTime(SessionIdleTimeSeconds);
			Acceptor.setCloseOnDeactivation(true);
			Acceptor.setHandler(this);
			Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			
			Acceptor.bind(new InetSocketAddress(port));
			
			_log.info("server started !");
		}
		else {
			_log.info("Server already open !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (Acceptor!=null) 
		{
			_log.info("server closing...");
			Acceptor.unbind();
			removeAllSession();
			Acceptor.dispose();
			Acceptor = null;
			_log.info("server closed !");
		}
		else{
			_log.info("Server is not open !");
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------

	public ChannelManager getChannelManager() {
		return channel_manager;
	}
	
	private ClientSessionImpl getBindSession(IoSession session)
	{
		Object obj = session.getAttribute(SessionAttributeKey.CLIENT_SESSION);
		if (obj instanceof ClientSessionImpl){
			return (ClientSessionImpl)obj;
		}
		return null;
	}
	
	private void unbindSession(IoSession session)
	{
		ClientSessionImpl client = getBindSession(session);
		
		session.removeAttribute(SessionAttributeKey.CLIENT_SESSION);
		
		if (client != null)		
		{
			try{
				client.stopHeartBeat();
			}catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
			
			try{
				Iterator<Channel> channels = channel_manager.getChannels();
				while (channels.hasNext()) {
					channels.next().leave(client);
				}
			}catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
			
			try{
				if (session.isConnected()) {
					session.close(false);
				}
			}catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
			
			try {
				if (client.Listener != null) {
					client.Listener.disconnected(client);
				}
			} catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
		}
	}
	
	private void bindSession(IoSession session)
	{
		ClientSessionImpl client = new ClientSessionImpl(session, this);
		session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
		client.LastHartBeatTime = System.currentTimeMillis();
		client.setListener(SrvListener.connected(client));
	}
	
	private void removeAllSession()
	{
		session_lock.lock();
		try{
			for (IoSession session : Acceptor.getManagedSessions().values()){
				ClientSessionImpl client = getBindSession(session);
				if (client!=null){
					unbindSession(((ClientSessionImpl)client).Session);
				}
			}
		} finally {
			session_lock.unlock();
		}
	}
	
	
	public Iterator<ClientSession> getSessions() {
		session_lock.lock();
		try{
			HashMap<Long, ClientSession> sessions = new HashMap<Long, ClientSession>(Acceptor.getManagedSessionCount());
			for (IoSession session : Acceptor.getManagedSessions().values()){
				ClientSessionImpl client = getBindSession(session);
				if (client!=null){
					sessions.put(client.getID(), client);
				}
			}
			return sessions.values().iterator();
		} finally {
			session_lock.unlock();
		}
	}
	
	public ClientSession getSession(long sessionID) {
		session_lock.lock();
		try{
			IoSession session = Acceptor.getManagedSessions().get(sessionID);
			ClientSessionImpl client = getBindSession(session);
			if (client!=null){
				return client;
			}
			return null;
		} finally {
			session_lock.unlock();
		}
	}
	
	public ClientSession getSession(Object object){
		session_lock.lock();
		try{
			for (IoSession session : Acceptor.getManagedSessions().values()){
				ClientSessionImpl client = getBindSession(session);
				if (object.equals(client)) {
					return client;
				}
			}
			return null;
		} finally {
			session_lock.unlock();
		}
	}
	
	public boolean hasSession(ClientSession session) {
		session_lock.lock();
		try{
			return Acceptor.getManagedSessions().containsKey(session.getID());
		} finally {
			session_lock.unlock();
		}
	}
	
	public int getSessionCount() {
		session_lock.lock();
		try{
			return Acceptor.getManagedSessionCount();
		} finally {
			session_lock.unlock();
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
		_log.error(cause.getMessage() + "\n" + session, cause);
		unbindSession(session);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		_log.info("sessionIdle : " + session);
		unbindSession(session);
	}
	
	public void sessionOpened(IoSession session) throws Exception {
		_log.info("sessionOpened : " + session);
		bindSession(session);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		_log.info("sessionClosed : " + session);
		unbindSession(session);
	}
	
	public void messageReceived(final IoSession session, final Object message) throws Exception 
	{
		ClientSessionImpl client = getBindSession(session);
		if (client == null) {
			_log.error("client is expire !");
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
					_log.error("unknow message : " + message);
				}
			}
		}
		else
		{
			_log.error("bad message type : " + message);
		}
		
	}
	
	
	
}
