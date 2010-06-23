package com.net.minaimpl.server;

import java.net.SocketAddress;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.SystemMessageS2C;
import com.net.server.Channel;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.Server;

public class ClientSessionImpl implements ClientSession 
{
	private static final Logger log = LoggerFactory.getLogger(ClientSessionImpl.class.getName());
	
	final protected IoSession 		Session;
	final protected AbstractServer	Server;
	protected ClientSessionListener	Listener;
	
	public ClientSessionImpl(IoSession session, AbstractServer server){
		Session = session;
		Server = server;
	}
	
	public Server getServer() {
		return Server;
	}
	
	public IoSession getIoSession() {
		return Session;
	}
	
	public String toString() {
		return Session.toString();
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof ClientSessionImpl) {
			return Session.equals(((ClientSessionImpl)obj).Session);
		}
		return false;
	}
	
	public boolean disconnect(boolean force) 
	{
		Session.close(force);
		return true;
	}
	
	public String getName()
	{
		return Session.getRemoteAddress().toString();
	}
	
	public long getID()
	{
		return Session.getId();
	}
	
	public SocketAddress getAddress()
	{
		return Session.getRemoteAddress();
	}

	public boolean isConnected() 
	{
		return Session.isConnected();
	}

	void setListener(ClientSessionListener listener) {
		Listener = listener;
	}
	
	@Override
	public ClientSessionListener getListener() {
		return Listener;
	}
	
	public boolean send(MessageHeader message) {
		Server.write(Session, 
				message, 
				MessageHeader.PROTOCOL_SESSION_MESSAGE, 
				0, 0, 0);
		return true;
	}
	
	
	public void send(MessageHeader request, MessageHeader response){
		Server.write(Session, 
				response, 
				MessageHeader.PROTOCOL_SESSION_MESSAGE, 
				0, 0, request.PacketNumber);
	}
	
	
	public Object getAttribute(Object key) {
		return Session.getAttribute(key);
	}

	public Object setAttribute(Object key, Object value){
		return Session.setAttribute(key, value);
	}

	public Object removeAttribute(Object key){
		return Session.removeAttribute(key);
	}

	public boolean containsAttribute(Object key){
		return Session.containsAttribute(key);
	}

	public Set<Object> getAttributeKeys(){
		return Session.getAttributeKeys();
	}
	
//	synchronized public void stopHeartBeat() {
//		if (heartbeat_future!=null) {
//			heartbeat_future.cancel(false);
//		}
//	}
	
	@Override
	public long getReceivedBytes() {
		return Session.getReadBytes();
	}
	@Override
	public long getReceivedMessageCount() {
		return Session.getReadMessages();
	}
	@Override
	public long getSentBytes() {
		return Session.getWrittenBytes();
	}
	@Override
	public long getSentMessageCount() {
		return Session.getWrittenMessages();
	}
	
	
//	synchronized public void startHeartBeat(ThreadPool pool, final long heartbeat_timeout)
//	{
//		if (heartbeat_future == null || 
//			heartbeat_future.isDone() || 
//			heartbeat_future.isCancelled())
//		{
//			// 在heartbeat_timeout ms秒后检查是否在这段时间内没有消息发送过来
//			heartbeat_future = pool.scheduleAtFixedRate(new Runnable(){
//				public void run() {
//					ClientSessionImpl iosession = ClientSessionImpl.this;
//					if (iosession.getIdleDuration() >= heartbeat_timeout) {
//						log.error("long time no message (" + iosession.getIdleDuration() + "), kick " + iosession);
//						iosession.disconnect(false);
//					}
//				}
//			}, 
//			heartbeat_timeout + 1, 
//			heartbeat_timeout);
//		}
//	}
	
	
}