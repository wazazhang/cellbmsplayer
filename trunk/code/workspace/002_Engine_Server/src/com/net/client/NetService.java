package com.net.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.MessageHeader;


public class NetService
{
	static Logger log = LoggerFactory.getLogger(NetService.class.getName());
	
	private class ExitTask extends Thread
	{
		public void run() {
			try {
				disconnect(false);
				log.info("NetService : clear connection !");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private class SimpleClientListenerImpl implements ServerSessionListener
	{
		SimpleClientListenerImpl() {}
		
		public void connected(ServerSession session) 
		{
			log.info("reconnected : " + session);
			if (client_listener!=null){
				client_listener.connected(session);
			}
		}
		
	    public void disconnected(ServerSession session, boolean graceful, String reason) 
	    {
	    	log.info("disconnected : " + graceful + " : " + reason);
	    	if (client_listener!=null){
				client_listener.disconnected(session, graceful, reason);
			}
	    }

	    public void receivedMessage(ServerSession session, MessageHeader message)
	    {
			if (message != null) {
				if (tryReceivedNotify(message)) {
					return;
				} else if (tryReceivedResponse(message)) {
					return;
				} else if (!tryPushUnhandledNotify(message)) {
					log.error("handle no listener message : " + message);
				}
			} else {
				log.error("handle null message !");
			}
	    }
	    

	    public void joinedChannel(ServerSession session, ClientChannel channel) 
	    {
	    	log.info("joined channel : \"" + channel.getID() + "\"");
	    	if (client_listener!=null){
				client_listener.joinedChannel(session, channel);
			}
	    }
	    
	    
	    public void leftChannel(ClientChannel channel) 
	    {
	    	log.info("left channel : \""  + channel.getID() + "\"");
	        if (client_listener!=null){
	        	client_listener.leftChannel(channel);
	    	}
	    }
	    
	    public void receivedChannelMessage(ClientChannel channel, MessageHeader message)
	    {
			if (message != null) {
				if (tryReceivedNotify(message)) {
					return;
				} else if (!tryPushUnhandledNotify(message)) {
					log.error("handle no listener channel message : " + message);
				}
			} else {
				log.error("handle null channel message !");
			}
		}

	}
	
	/**request从1开始*/
	static private AtomicInteger SendedPacks = new AtomicInteger(1);
	
	@SuppressWarnings("unchecked")
	class Request implements Runnable
	{
		final public MessageHeader 		Message;
		final public WaitingListener[] 	Listener;
		final public long 				SendTimeOut;
		
		MessageHeader					Response;
		
		public Request(MessageHeader msg,  long timeout, WaitingListener ... listener)
		{
			if (SendedPacks.get() == 0) {
				SendedPacks.incrementAndGet();
			}
			msg.PacketNumber 	= SendedPacks.getAndIncrement();
	    	
			this.Message 		= msg;
			this.Listener 		= listener;
			this.SendTimeOut 	= timeout > 0 ? timeout : 0;
		}
		
		public void run () 
		{
//			System.out.println("request " + this);
			if (SendTimeOut>0) {
				synchronized (this) {
					send(Message);
					try {
						wait(SendTimeOut);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else{
				send(Message);
			}
		}
		
		public void messageResponsed(MessageHeader response) 
		{
//			System.out.println("response " + this);
			Response = response;
			
			if (SendTimeOut>0) {
				synchronized (this){
					request_response_ping.set((int)(response.DynamicReceiveTime - Message.DynamicSendTime));
					notify();
				}
			}

			for (WaitingListener wait : Listener) {
				wait.response(NetService.this, Message, response);
			}
		}
		
		public void timeout() {
			for (WaitingListener wait : Listener) {
				wait.timeout(NetService.this, Message, Message.DynamicSendTime);
			}
		}
		
		protected boolean isDroped() {
			return System.currentTimeMillis() - Message.DynamicSendTime > DropRequestTimeOut;
		}
		
		public String toString() {
			return "Request : Message=" + Message;
		}
		
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	public long DropRequestTimeOut = 60000;

	final protected ServerSession 	Session;
	
	final protected ConcurrentHashMap<Integer, Request> 
	WaitingListeners = new ConcurrentHashMap<Integer, Request>();
	
	final protected ConcurrentHashMap<Class<?>, ArrayList<NotifyListener<?>>> 
	NotifyListeners = new ConcurrentHashMap<Class<?>, ArrayList<NotifyListener<?>>>();
	
	final protected ConcurrentLinkedQueue<MessageHeader>
	UnhandledMessages = new ConcurrentLinkedQueue<MessageHeader>();
	
	private String					ServerHost;
	private Integer 				ServerPort;
	
	private ServerSessionListener 	client_listener;
	
	private long					LastCleanRequestTime 	= System.currentTimeMillis();

	private ReentrantLock 			sendlock				= new ReentrantLock();
	private ReentrantLock 			notifylock				= new ReentrantLock();
	
	private AtomicInteger			request_response_ping	= new AtomicInteger();
	
//	---------------------------------------------------------------------------------------------------------------------------------

	public NetService(
			ServerSession session, 
			ServerSessionListener client_listener) 
	{
		this.Session 			= session;
		this.client_listener	= client_listener;
		Runtime.getRuntime().addShutdownHook(new ExitTask());
	}

	public NetService(ServerSession session) 
	{
		this(session, null);
	}
	
	final public ServerSession getSession() {
		return Session;
	}
	
	final public String getHost() {
		return ServerHost;
	}
	
	final public Integer getPort() {
		return ServerPort;
	}

	final public boolean connect(String host, Integer port) {
		return this.connect(host, port, 10000L);
	}
	
	final public boolean connect(String host, Integer port, Long timeout) {
		sendlock.lock();
		try {
			if (!Session.isConnected()) {
				log.info("connecting... " + host + ":" + port);
	    		try {
	    			ServerHost = host;
	    			ServerPort = port;
	    			Session.connect(host, port, timeout, new SimpleClientListenerImpl());
	    			return true;
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    			return false;
	    		}
	    	}
		} finally {
			sendlock.unlock();
		}
    	return true;
	}

	final public boolean reconnect() 
	{
		sendlock.lock();
		try {
	    	if (!Session.isConnected()) {
				try {
					Session.connect(ServerHost, ServerPort, new SimpleClientListenerImpl());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		} finally {
			sendlock.unlock();
		}
    	return true;
	}
    
	final public boolean isConnected() {
    	if (Session.isConnected()) {
    		return Session.isConnected();
		}else{
			printNotConnectError();
		}
    	return false;
    }
    
	final public void disconnect(boolean force) {
    	if (Session.isConnected()) {
    		Session.disconnect(force);
		}else{
			printNotConnectError();
		}
    }

	final public int getPing() {
		return request_response_ping.get();
	}
	
	final public void printNotConnectError() {
		log.error("session is not connect, please call connect(String host, String port) first !");
	}
//	----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 直接发送，没有监听器
	 * @param message
	 */
	final public void send(MessageHeader message) {
    	if (Session.isConnected()) {
    		try {
    			Session.send(message);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
		}else{
			printNotConnectError();
		}
    }
	
//	----------------------------------------------------------------------------------------------------------------------------
	
    @SuppressWarnings("unchecked")
	final public<T extends MessageHeader> T sendRequest(MessageHeader message, long timeout, WaitingListener ... listeners) {
    	cleanRequestAndNotify();
		Request request = new Request(message, timeout, listeners);
		sendlock.lock();
    	try{
    		WaitingListeners.put(request.Message.PacketNumber, request);
    	}finally {
    		sendlock.unlock();
    	}
		request.run();
		
		if (request.Response != null) {
			try{
				return (T)request.Response;
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
    
    @SuppressWarnings("unchecked")
    final public void sendRequest(MessageHeader message, WaitingListener ... listeners) {
    	sendRequest(message, 0, listeners);
	}
    
    final public void cleanRequestAndNotify() 
    {
    	if (System.currentTimeMillis() - LastCleanRequestTime > DropRequestTimeOut) 
    	{
    		sendlock.lock();
        	try{
        		for (Integer pnum : WaitingListeners.keySet()) {
            		Request req = WaitingListeners.get(pnum);
        			if (req != null && req.isDroped()) {
        				WaitingListeners.remove(pnum);
        				req.timeout();
        				log.error("drop a timeout request : " + req.Message.PacketNumber + " : " + req.toString());
        			}
        		}
        	}
        	catch (Exception err){
        		log.error(err.getMessage(), err);
        	}finally {
        		sendlock.unlock();
        	}
        	
        	notifylock.lock();
    		try{
    			if (!UnhandledMessages.isEmpty()) {
	    			ArrayList<MessageHeader> removed = null;
	    			for (MessageHeader unotify : UnhandledMessages) {
	    				if (System.currentTimeMillis() - unotify.DynamicReceiveTime > DropRequestTimeOut) {
	    					if (removed == null) {
	    						removed = new ArrayList<MessageHeader>(UnhandledMessages.size());
	    					}
	    					removed.add(unotify);
	    					log.info("drop a unhandled notify : " + unotify);
	    				}
	    			}
	    			if (removed!=null) {
	    				UnhandledMessages.removeAll(removed);
	    			}
    			}
    		}catch (Exception err){
        		log.error(err.getMessage(), err);
        	}finally{
    			notifylock.unlock();
    		}
        	
        	LastCleanRequestTime = System.currentTimeMillis();
    	}
    }
    
//	----------------------------------------------------------------------------------------------------------------------------
//	notify
    
	final public void registNotifyListener(Class<?> cls, NotifyListener<?> listener) {
		notifylock.lock();
		try{
			
			ArrayList<NotifyListener<?>> listeners = NotifyListeners.get(cls);
			if (listeners == null) {
				listeners = new ArrayList<NotifyListener<?>>();
				NotifyListeners.put(cls, listeners);
			}
			listeners.add(listener);
			
			if (!UnhandledMessages.isEmpty()) {
				ArrayList<MessageHeader> removed = null;
				for (MessageHeader unotify : UnhandledMessages) {
					if (tryReceivedNotify(unotify)) {
						if (removed == null) {
							removed = new ArrayList<MessageHeader>(UnhandledMessages.size());
						}
						removed.add(unotify);
//						log.info("pop a unhandled notify : " + unotify);
					}
				}
				if (removed!=null) {
					UnhandledMessages.removeAll(removed);
				}
			}
			
		}finally{
			notifylock.unlock();
		}
	}
	
	final public void unregistNotifyListener(Class<?> cls, NotifyListener<?> listener) {
		notifylock.lock();
		try {
			ArrayList<NotifyListener<?>> listeners = NotifyListeners.get(cls);
			if (listeners != null) {
				listeners.remove(listener);
			}
		} finally {
			notifylock.unlock();
		}
	}
	
	final public void clearNotifyListener(Class<?> cls) {
		notifylock.lock();
		try {
			NotifyListeners.remove(cls);
		} finally {
			notifylock.unlock();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	final protected boolean tryReceivedNotify(MessageHeader message) 
	{
		notifylock.lock();
		try{
			ArrayList<NotifyListener<?>> notifys = NotifyListeners.get(message.getClass());
			if (notifys != null && !notifys.isEmpty()){
	    		for (NotifyListener notify : notifys) {
	    			notify.notify(this, message);
	    		}
	    		return true;
	    	}
    	} finally {
			notifylock.unlock();
		}
    	return false;
	}
	
	final protected boolean tryReceivedResponse(MessageHeader message)
	{
		Request request = WaitingListeners.remove(message.PacketNumber);
    	if (request != null) {
    		request.messageResponsed(message);
    		return true;
    	}
    	return false;
	}
	
	final protected boolean tryPushUnhandledNotify(MessageHeader message)
	{
		notifylock.lock();
		try{
			if (message.PacketNumber == 0) {
				UnhandledMessages.add(message);
//				log.info("push a unhandled notify, wait for notify listener : " + message);
				return true;
			}
    	} finally {
			notifylock.unlock();
		}
    	return false;
	}
//	----------------------------------------------------------------------------------------------------------------------------
	

	
	
	
	
}
