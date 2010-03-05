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

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.client.ServerSession;
import com.net.client.ServerSessionListener;
import com.net.minaimpl.NetPackageCodec;


public class ServerSessionImpl extends IoHandlerAdapter implements ServerSession 
{

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
		Codec = new NetPackageCodec(cl, ef);
		Connector 	= new NioSocketConnector();
		Connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
		Connector.setHandler(this);
	}
	
	public boolean connect(String host, int port, ServerSessionListener listener) throws IOException {
		return this.connect(host, port, 10000, listener);
	}
	
	public boolean connect(String host, int port, long timeout, ServerSessionListener listener) throws IOException 
	{
		
			if (!isConnected()) 
			{
				SocketAddress address = new InetSocketAddress(host, port);

				Listener 	= listener;
				
				synchronized(this) 
				{
		            ConnectFuture future1 = Connector.connect(address); 
					future1.awaitUninterruptibly(timeout);
					
		            if (!future1.isConnected()) {
		                return false;
		            }
		            Session = future1.getSession();
				}

				if (Session != null && Session.isConnected()) {
//					System.out.println("connected " + Session);
					return true;
				}else{
					System.err.println("not connect : " + address.toString());
				}
			}
			else
			{
				System.err.println("Already connected !");
			}
		
		
		
		return false;
	}
	
	public boolean disconnect(boolean force) {
		synchronized(this) {
			if (Session != null) {
				Session.close(force);
				Session = null;
			}else{
				System.err.println("session is null !");
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
				System.err.println("session is null !");
			}
		}else{
			System.err.println("server not connected !");
		}
		return false;
	}
	
	protected void sendChannel(MessageHeader message, ClientChannelImpl channel) {
		if (Session != null) {
			message.Protocol	= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
			message.ChannelID	= channel.getID();
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
				switch (header.Protocol)
				{
					case MessageHeader.PROTOCOL_CHANNEL_JOIN_S2C:
					{
						ClientChannelImpl channel = new ClientChannelImpl(this, header.ChannelID);
						channels.put(header.ChannelID, channel);
						break;
					}
					case MessageHeader.PROTOCOL_CHANNEL_LEAVE_S2C:
					{
						ClientChannelImpl channel = channels.remove(header.ChannelID);
						if (channel!=null) {
							Listener.leftChannel(channel);
						}
						break;
					}
					case MessageHeader.PROTOCOL_CHANNEL_MESSAGE:
					{
						ClientChannelImpl channel = channels.get(header.ChannelID);
						if (channel!=null) {
							Listener.receivedChannelMessage(channel, header);
						}
						break;
					}
					//
					case MessageHeader.PROTOCOL_SESSION_MESSAGE:
					{
						Listener.receivedMessage(this, header);
						break;
					}
					default:{
						System.err.println("messageReceived : " +
								"unknow protocol("+Integer.toString(header.Protocol, 16)+") : " +
								"data : " + header);
					}
				}
			
			}
			catch (Exception e) 
			{
				System.err.println("messageReceived : bad protocol : " + header);
				e.printStackTrace();
			}
		}
		else
		{
			System.err.println("messageReceived : bad message type : " + message);
		}
	}
	
	public IoSession getIoSession() {
		return Session;
	}

	@Override
	public SocketAddress getAddress() {
		return Session.getRemoteAddress();
	}

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
	
	
//	private class ExitTask extends Thread
//	{
//		public ExitTask() {
//			super("server-session-cleaner");
//		}
//		public void run() {
//			System.out.println("Clear ServerSession connection !");
//			try {
//				synchronized(shutdown_lock) {
//					shutdown_hook = null;
//					disconnect(false);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
