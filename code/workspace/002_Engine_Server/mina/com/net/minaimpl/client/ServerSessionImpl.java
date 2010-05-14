package com.net.minaimpl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.SystemMessageS2C;


public class ServerSessionImpl extends IoHandlerAdapter implements ServerSession 
{
	final Logger log;

	final AtomicReference<IoSession> session_ref = new AtomicReference<IoSession>(null);

	ConcurrentHashMap<Integer, ClientChannelImpl> channels = new ConcurrentHashMap<Integer, ClientChannelImpl>();

	IoConnector 			Connector;
	NetPackageCodec 		Codec;
	ServerSessionListener 	Listener;
	
	public ServerSessionImpl()
	{
		this(Thread.currentThread().getContextClassLoader(), null);
	}

	public ServerSessionImpl(ClassLoader cl, ExternalizableFactory ef)
	{
		log			= LoggerFactory.getLogger(getClass().getName());
		Codec		= new NetPackageCodec(cl, ef);
		Connector 	= new NioSocketConnector();
		Connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
		Connector.setHandler(this);
	}
	
	public boolean connect(String host, int port, ServerSessionListener listener) throws IOException {
		return this.connect(host, port, 10000, listener);
	}
	
	public boolean connect(String host, int port, long timeout, ServerSessionListener listener) throws IOException 
	{
		if (!isConnected()) {
			synchronized (session_ref) {
				SocketAddress address = new InetSocketAddress(host, port);
				Listener = listener;
				Connector.setConnectTimeoutMillis(timeout);
				ConnectFuture future1 = Connector.connect(address);
				future1.awaitUninterruptibly(timeout);
				if (!future1.isConnected()) {
					return false;
				}
				IoSession io_session = future1.getSession();
				if (io_session != null && io_session.isConnected()) {
					session_ref.set(io_session);
					Runtime.getRuntime().addShutdownHook(new CleanTask(io_session));
					return true;
				} else {
					log.error("not connect : " + address.toString());
				}
			}
		} else {
			log.error("Already connected !");
		}
		return false;
	}
	
	public boolean disconnect(boolean force) {
		IoSession io_session = null;
		synchronized (session_ref) {
			io_session = session_ref.getAndSet(null);
		}
		if (io_session != null) {
			io_session.close(force);
		} else {
			log.error("session is null !");
		}
		return false;
	}
	
	public boolean isConnected() {
		synchronized (session_ref) {
			IoSession io_session = session_ref.get();
			if (io_session != null) {
				if (io_session.isConnected()) {
					return true;
				} else {
					session_ref.set(null);
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean send(MessageHeader message) {
		if (isConnected()) {
			IoSession io_session = session_ref.get();
			message.Protocol = MessageHeader.PROTOCOL_SESSION_MESSAGE;
			io_session.write(message);
			return true;
		} else {
			log.error("server not connected !");
		}
		return false;
	}
	
	protected void sendChannel(MessageHeader message, ClientChannelImpl channel) {
		if (isConnected()) {
			IoSession io_session = session_ref.get();
			if (io_session != null) {
				message.Protocol			= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
				message.ChannelID			= channel.getID();
				message.ChannelSesseionID	= getID();
				io_session.write(message);
			}
		}
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

//	public void sessionCreated(IoSession session) throws Exception {}
//	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {}
//	public void messageSent(IoSession session, Object message) throws Exception {}
	
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
	
	public void sessionOpened(IoSession session) throws Exception {
		Listener.connected(this);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		Listener.disconnected(this, true, "sessionClosed");
	}
	
	public void messageReceived(final IoSession iosession, final Object message) throws Exception 
	{
		if (message instanceof MessageHeader) 
		{
			MessageHeader header = (MessageHeader)message;
			try
			{
				if (header.Protocol == MessageHeader.PROTOCOL_CHANNEL_MESSAGE) {
					if (header instanceof SystemMessageS2C) {
						SystemMessageS2C sys = (SystemMessageS2C) header;
						if (sys.event == SystemMessages.EVENT_CHANNEL_JOIN_S2C) {
							channels.put(header.ChannelID, new ClientChannelImpl(this, header.ChannelID));
						} else if (sys.event == SystemMessages.EVENT_CHANNEL_LEAVE_S2C) {
							ClientChannelImpl channel = channels.get(header.ChannelID);
							if (channel != null) {
								Listener.leftChannel(channel);
								channels.remove(header.ChannelID);
							}
						}
					} else {
						ClientChannelImpl channel = channels.get(header.ChannelID);
						Listener.receivedChannelMessage(channel, header);
					}
				} 
				else if (header.Protocol == MessageHeader.PROTOCOL_SESSION_MESSAGE) {
					Listener.receivedMessage(this, header);
				} 
				else {
					log.error("messageReceived" +
							" : unknow protocol(" + Integer.toString(header.Protocol, 16) + ")" +
							" : " + "data : " + header);
				}
			}
			catch (Exception e) 
			{
				log.error("messageReceived : bad protocol : " + header);
				e.printStackTrace();
			}
		}
		else
		{
			log.error("messageReceived : bad message type : " + message);
		}
	}
	
	public IoSession getIoSession() {
		return session_ref.get();
	}

	@Override
	public boolean containsAttribute(Object key) {
		return session_ref.get().containsAttribute(key);
	}

	@Override
	public Object getAttribute(Object key) {
		return session_ref.get().getAttribute(key);
	}
	@Override
	public Set<Object> getAttributeKeys() {
		return session_ref.get().getAttributeKeys();
	}
	
	@Override
	public long getID() {
		return session_ref.get().getId();
	}
	
	@Override
	public Object removeAttribute(Object key) {
		return session_ref.get().removeAttribute(key);
	}
	
	@Override
	public Object setAttribute(Object key, Object value) {
		return session_ref.get().setAttribute(key, value);
	}
	
	public String toString() {
		try {
			synchronized (session_ref) {
				return "" + session_ref.get();
			}
		} catch (Exception err) {
			return err.getMessage();
		}
	}
	
	
	private static class CleanTask extends Thread
	{
		final IoSession session;
		public CleanTask(IoSession session) {
			this.session = session;
		}
		public void run() {
			String info; 
			try {
				info = session.toString();
			} catch (Throwable err){
				info = err.getMessage();
			}
			System.out.println("Clear session : " + info);
			try {
				session.close(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
