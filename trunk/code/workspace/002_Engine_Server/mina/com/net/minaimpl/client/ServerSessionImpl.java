package com.net.minaimpl.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Hashtable;
import java.util.Set;
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
	final private Logger 			log;

	private ServerSessionListener 					Listener;
	private Hashtable<Integer, ClientChannelImpl> 	channels			= new Hashtable<Integer, ClientChannelImpl>();
	private IoSession 								Session;
	
	IoConnector										Connector;
	NetPackageCodec									Codec;
	
	long											LastHartBeatTime	= 0;
	
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
			SocketAddress address = new InetSocketAddress(host, port);

			Listener = listener;

			synchronized (this) {
				ConnectFuture future1 = Connector.connect(address);
				future1.awaitUninterruptibly(timeout);

				if (!future1.isConnected()) {
					return false;
				}
				Session = future1.getSession();
			}

			if (Session != null && Session.isConnected()) {
				Runtime.getRuntime().addShutdownHook(new CleanTask(Session));
				return true;
			} else {
				log.error("not connect : " + address.toString());
			}
		} else {
			log.error("Already connected !");
		}
		return false;
	}
	
	public boolean disconnect(boolean force) {
		synchronized(this) {
			if (Session != null) {
				Session.close(force);
				Session = null;
			}else{
				log.error("session is null !");
			}
		}
		return false;
	}
	
	public boolean isConnected() {
		synchronized(this) {
			if (Session != null) {
				return Session.isConnected();
			}
		}
		return false;
	}
	
	public boolean send(MessageHeader message) {
		if (isConnected()) {
			if (Session != null) {
				message.Protocol = MessageHeader.PROTOCOL_SESSION_MESSAGE;
				Session.write(message);
				return true;
			}else{
				log.error("session is null !");
			}
		}else{
			log.error("server not connected !");
		}
		return false;
	}
	
	protected void sendChannel(MessageHeader message, ClientChannelImpl channel) {
		if (Session != null) {
			message.Protocol			= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
			message.ChannelID			= channel.getID();
			message.ChannelSesseionID	= getID();
			Session.write(message);
		}
	}
	
	synchronized public long getIdleDuration() {
		return System.currentTimeMillis() - LastHartBeatTime;
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
		LastHartBeatTime = System.currentTimeMillis();
		Listener.connected(this);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		synchronized(this) {
			Listener.disconnected(this, true, "sessionClosed : " + toString());
		}
	}
	
	public void messageReceived(final IoSession iosession, final Object message) throws Exception 
	{
		if (message instanceof MessageHeader) 
		{
			LastHartBeatTime = System.currentTimeMillis();
			
			MessageHeader header = (MessageHeader)message;
			
			try
			{
				if (header.Protocol == MessageHeader.PROTOCOL_CHANNEL_MESSAGE) {
					ClientChannelImpl channel = channels.get(header.ChannelID);
					if (channel != null) {
						if (header instanceof SystemMessageS2C) {
							SystemMessageS2C sys = (SystemMessageS2C) header;
							if (sys.event == SystemMessages.EVENT_CHANNEL_JOIN_S2C) {
								channels.put(header.ChannelID, channel);
							} else if (sys.event == SystemMessages.EVENT_CHANNEL_LEAVE_S2C) {
								Listener.leftChannel(channel);
								channels.remove(header.ChannelID);
							}
						}
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
		return Session;
	}

//	@Override
//	public SocketAddress getAddress() {
//		return Session.getRemoteAddress();
//	}

	@Override
	public boolean containsAttribute(Object key) {
		return Session.containsAttribute(key);
	}

	@Override
	public Object getAttribute(Object key) {
		return Session.getAttribute(key);
	}
	@Override
	public Set<Object> getAttributeKeys() {
		return Session.getAttributeKeys();
	}
	
	@Override
	public long getID() {
		return Session.getId();
	}
	
	@Override
	public Object removeAttribute(Object key) {
		return Session.removeAttribute(key);
	}
	
	@Override
	public Object setAttribute(Object key, Object value) {
		return Session.setAttribute(key, value);
	}
	
	public String toString() {
		try{
			if (!Session.isClosing()) {
				return "" + Session;
			} else {
				return "closing ... ";
			}
		}catch(Exception err){
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
				info = session.getRemoteAddress() + "";
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
