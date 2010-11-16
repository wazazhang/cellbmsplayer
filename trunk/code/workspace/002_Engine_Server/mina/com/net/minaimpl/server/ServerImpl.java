package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.mina.core.session.IoSession;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.minaimpl.ProtocolImpl;
import com.net.minaimpl.ProtocolPool;
import com.net.minaimpl.SessionAttributeKey;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;


public class ServerImpl extends AbstractServer
{
	final private ChannelManager	channel_manager;

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
			int 					sessionReadIdleTimeSeconds, 
			boolean					close_on_error) 
	{
		super(cl, ef, ioProcessCount, sessionWriteIdleTimeSeconds, sessionReadIdleTimeSeconds, close_on_error);
		this.channel_manager				= new ChannelManagerImpl(this);
	}	
//	----------------------------------------------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------------------------------------------

	public ChannelManager getChannelManager() {
		return channel_manager;
	}
	
	private ClientSessionImpl getBindSession(IoSession session)
	{
		return (ClientSessionImpl)session.getAttribute(SessionAttributeKey.CLIENT_SESSION);
	}
	
	class SessionIterator implements Iterator<ClientSession>
	{
		public SessionIterator() {
			Acceptor.getManagedSessions().containsKey(SessionAttributeKey.CLIENT_SESSION);
		}
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public ClientSession next() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	public Iterator<ClientSession> getSessions() {
		ArrayList<ClientSession> sessions = new ArrayList<ClientSession>(Acceptor.getManagedSessionCount());
		for (IoSession session : Acceptor.getManagedSessions().values()) {
			ClientSessionImpl client = getBindSession(session);
			if (client != null) {
				sessions.add(client);
			}
		}
		return sessions.iterator();
	}
	
	public ClientSession getSession(long sessionID) {
		IoSession session = Acceptor.getManagedSessions().get(sessionID);
		ClientSessionImpl client = getBindSession(session);
		return client;
	}
	
	public ClientSession getSession(Object object){
		for (IoSession session : Acceptor.getManagedSessions().values()){
			ClientSessionImpl client = getBindSession(session);
			if (object.equals(client)) {
				return client;
			}
		}
		return null;
	}
	
	public boolean hasSession(ClientSession session) {
		return Acceptor.getManagedSessions().containsKey(session.getID());
	}
	
	public int getSessionCount() {
		return Acceptor.getManagedSessionCount();
	}
	
	public void broadcast(MessageHeader message){
		ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
		p.Protocol = Protocol.PROTOCOL_SESSION_MESSAGE;
		Acceptor.broadcast(p);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
		ClientSessionImpl client = new ClientSessionImpl(session, this);
		session.setAttribute(SessionAttributeKey.CLIENT_SESSION, client);
		client.setListener(SrvListener.connected(client));
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
		ClientSessionImpl client = (ClientSessionImpl)session.removeAttribute(SessionAttributeKey.CLIENT_SESSION);
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
		//System.out.println(Thread.currentThread().getName() + " messageReceived");
		if (message instanceof Protocol)
		{
			ClientSessionImpl client = getBindSession(session);
			if (client != null && client.Listener != null)
			{
				Protocol header = (Protocol)message;
				
				switch (header.getProtocol())
				{
				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
					ChannelImpl channel = (ChannelImpl)channel_manager.getChannel(header.getChannelID());
					if (channel != null) {
						channel.getChannelListener().receivedMessage(channel, client, header.getMessage());
					}
					break;
					
				case Protocol.PROTOCOL_SESSION_MESSAGE:
					client.Listener.receivedMessage(client, header.getMessage());
					break;
					
//				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
//					break;
//				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
//					break;
					
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
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception 
	{
		if (message instanceof Protocol) {
			ClientSessionImpl client = getBindSession(session);
			if (client != null && client.Listener != null) {
				Protocol header = (Protocol) message;
//				client.Listener.sentMessage(client, header.getMessage());
			}
		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}
	
}
