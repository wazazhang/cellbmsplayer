package com.net.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CObject;
import com.cell.util.Pair;
import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;


/**
 * 客户端链接到服务器的服务，该类可以方便监听(请求/回馈/通知)
 * @author WAZA
 */
public abstract class BasicNetService
{
	/**request从1开始*/
	final private AtomicInteger 	SendedPacks 			= new AtomicInteger(1);
	
	final private SimpleClientListenerImpl
									session_listener 		= new SimpleClientListenerImpl();
	
	public long 					DropRequestTimeOut 		= 60000;
	
	final private ConcurrentHashMap<Integer, Request> 
									WaitingListeners 		= new ConcurrentHashMap<Integer, Request>();

	
	// notifiers
	final private ReentrantLock		notifies_lock			= new ReentrantLock();
	final private ConcurrentHashMap<Class<?>, Vector<NotifyListener<?>>> 
									notifies_map 				= new ConcurrentHashMap<Class<?>, Vector<NotifyListener<?>>>();
	final private Vector<Pair<Class<?>, NotifyListener<?>>> 
									adding_notifies			= new Vector<Pair<Class<?>, NotifyListener<?>>>();
//	final private Vector<Pair<Class<?>, NotifyListener<?>>> 
//									removing_notifies		= new Vector<Pair<Class<?>, NotifyListener<?>>>();
	//
	
	
	final private ConcurrentLinkedQueue<MessageHeader>
									UnhandledMessages 		= new ConcurrentLinkedQueue<MessageHeader>();
	
	private long					LastCleanRequestTime 	= System.currentTimeMillis();

	private AtomicInteger			request_response_ping	= new AtomicInteger();

	final private ThreadPool		thread_pool;
	
	final protected Logger 			log;
	
//	---------------------------------------------------------------------------------------------------------------------------------

	public BasicNetService(ThreadPool thread_pool) 
	{
		this.log				= LoggerFactory.getLogger(getClass().getName());
		this.thread_pool		= thread_pool;
	}

	final protected ServerSessionListener getSessionListener() {
		return session_listener;
	}

//	----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 断开链接
	 * @param force 是否立即断开
	 */
	abstract public void close(boolean force) ;

	/**
	 * 直接发送，没有监听器
	 * @param message
	 */
	abstract public void send(MessageHeader message) ;

	abstract public void dispose();
	
//	----------------------------------------------------------------------------------------------------------------------------

	/**
	 * 得到网络交互延迟时间
	 * @return
	 */
	final public int getPing() {
		return request_response_ping.get();
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
//	----------------------------------------------------------------------------------------------------------------------------
	
    /**
     * 发送并监听返回
     * @param <T>
     * @param message
     * @param timeout
     * @param listeners
     * @return
     */
    @SuppressWarnings("unchecked")
	final public<T extends MessageHeader> T sendRequest(MessageHeader message, long timeout, WaitingListener ... listeners)
    {
    	if (System.currentTimeMillis() - LastCleanRequestTime > DropRequestTimeOut) {        	
    		LastCleanRequestTime = System.currentTimeMillis();
    		cleanRequestAndNotify();
    	}
    	
		Request request = new Request(message, timeout, listeners);
    	WaitingListeners.put(request.Message.PacketNumber, request);

    	for (WaitingListener l : listeners) {
    		onListeningRequest(message, l);
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
    
    /**
     * 发送并监听返回
     * @param message
     * @param listeners
     */
    @SuppressWarnings("unchecked")
    final public void sendRequest(MessageHeader message, WaitingListener ... listeners) {
    	sendRequest(message, 0, listeners);
	}
    
    /**
     * 强制移除所有等待中的请求
     * @param type
     */
	final public void clearRequests() {
		synchronized (WaitingListeners) {
			for (Integer pnum : new ArrayList<Integer>(WaitingListeners.keySet())) {
				WaitingListeners.remove(pnum);
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------
    
	final public void runCleanTask(ThreadPool tp) {
		tp.schedule(new CleanTask(), DropRequestTimeOut);
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
//	notify
    
	/**
	 * 添加一个用于主动监听服务器端的消息的监听器
	 * @param message_type
	 * @param listener
	 */
	final public void registNotifyListener(Class<? extends MessageHeader> message_type, NotifyListener<?> listener) {
		synchronized (adding_notifies) {
			adding_notifies.add(new Pair<Class<?>, NotifyListener<?>>(message_type, listener));
		}
		cleanUnhandledMessages();
	}
	
	/**
	 * 删除一个用于主动监听服务器端的消息的监听器
	 * @param message_type
	 * @param listener
	 */
	final public void unregistNotifyListener(Class<? extends MessageHeader> message_type, NotifyListener<?> listener) {
		synchronized (notifies_lock){
			Vector<NotifyListener<?>> notifys = notifies_map.get(message_type);
			if (notifys != null) {
				notifys.remove(listener);
			}
		}
	}
	
	/**
	 * 清理所有用于主动监听服务器端的消息的监听器
	 * @param message_type
	 */
	final public void clearNotifyListener(Class<? extends MessageHeader> message_type) {
		notifies_map.remove(message_type);
	}

//	----------------------------------------------------------------------------------------------------------------------------

//  -------------------------------------------------------------------------------------------------------------
  
    /**
     * 立刻清理所有未响应的请求
     */
    final public void cleanRequestAndNotify() 
    {
//    	System.err.println("waiting listeners : " + WaitingListeners.size());
    	
    	try{
    		for (Integer pnum : new ArrayList<Integer>(WaitingListeners.keySet())) {
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
    	}
    	
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
    	}
    }
    
	
    final public void cleanUnhandledMessages()
    {	
    	if (!UnhandledMessages.isEmpty()) {
			ArrayList<MessageHeader> removed = null;
			for (MessageHeader unotify : UnhandledMessages) {
				if (tryReceivedNotify(unotify)) {
					if (removed == null) {
						removed = new ArrayList<MessageHeader>(UnhandledMessages.size());
					}
					removed.add(unotify);
//					log.info("pop a unhandled notify : " + unotify);
				}
			}
			if (removed!=null) {
				UnhandledMessages.removeAll(removed);
			}
		}
    }
    
//    -------------------------------------------------------------------------------------------------------------------
    
	final private void processReceiveSessionMessage(MessageHeader message) {
		try {
			onReceivedMessage(message);
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
		if (tryReceivedNotify(message)) {
			return;
		} else if (tryReceivedResponse(message)) {
			return;
		} else if (!tryPushUnhandledNotify(message)) {
			log.error("handle no listener message : " + message);
		}
	}
	
	final private void processReceiveChannelMessage(MessageHeader message) {
		try {
			onReceivedMessage(message);
		} catch (Exception err) {
			log.error(err.getMessage(), err);
		}
		if (tryReceivedNotify(message)) {
			return;
		} else if (!tryPushUnhandledNotify(message)) {
			log.error("handle no listener channel message : " + message);
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("unchecked")
	final private boolean tryReceivedNotify(MessageHeader message) 
	{
		synchronized (notifies_lock)
		{
			synchronized (adding_notifies) {
				if (!adding_notifies.isEmpty()) {
					for (Pair<Class<?>, NotifyListener<?>> pair : adding_notifies) {
						Vector<NotifyListener<?>> notifys = notifies_map.get(pair.getKey());
						if (notifys == null) {
							notifys = new Vector<NotifyListener<?>>();
							notifies_map.put(pair.getKey(), notifys);
						}
						notifys.add(pair.getValue());
					}
					adding_notifies.clear();
				}
			}
			
			Vector<NotifyListener<?>> notifys = notifies_map.get(message.getClass());
			if (notifys != null && !notifys.isEmpty()) {
				for (NotifyListener notify : notifys) {
					notify.notify(this, message);
				}
				return true;
			}
		}
    	return false;
	}
	
	final private boolean tryReceivedResponse(MessageHeader message)
	{
		Request request = WaitingListeners.remove(message.PacketNumber);
    	if (request != null) {
    		request.messageResponsed(message);    	
    		return true;
    	}
    	return false;
	}
	
	final private boolean tryPushUnhandledNotify(MessageHeader message)
	{
		if (message.PacketNumber == 0) {
			UnhandledMessages.add(message);
			return true;
		}
    	return false;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	protected void onListeningRequest(MessageHeader request, WaitingListener<?,?> listeners){}
	
	protected void onConnected(ServerSession session) {}
	
	protected void onDisconnected(ServerSession session, boolean graceful, String reason) {}

	protected void onJoinedChannel(ServerSession session, ClientChannel channel) {}
    
	protected void onLeftChannel(ClientChannel channel) {}
  
	protected void onReceivedMessage(MessageHeader message){}
//	----------------------------------------------------------------------------------------------------------------------------
	

	private class SimpleClientListenerImpl implements ServerSessionListener
	{
		public void connected(ServerSession session) {
			log.info("reconnected : " + session);
			onConnected(session);
		}
		
	    public void disconnected(ServerSession session, boolean graceful, String reason) {
	    	log.info("disconnected : " + graceful + " : " + reason);
			onDisconnected(session, graceful, reason);
			if (thread_pool != null) {
				thread_pool.executeTask(new CleanTask());
			}
	    }
	    
	    public void joinedChannel(ServerSession session, ClientChannel channel) {
	    	log.info("joined channel : \"" + channel.getID() + "\"");
			onJoinedChannel(session, channel);
	    }

	    public void leftChannel(ClientChannel channel) {
	    	log.info("left channel : \""  + channel.getID() + "\"");
	        onLeftChannel(channel);	
	    }
	    
	    public void receivedMessage(ServerSession session, MessageHeader message)
	    {
			if (message != null) {
				if (thread_pool!=null) {
					thread_pool.executeTask(new ReceiveTask(message));
				} else {
					processReceiveSessionMessage(message);
				}
			} else {
				log.error("handle null message !");
			}
			if (thread_pool != null) {
				thread_pool.executeTask(new CleanTask());
			}
	    }
	    
	    public void receivedChannelMessage(ClientChannel channel, MessageHeader message)
	    {
			if (message != null) {
				if (thread_pool!=null) {
					thread_pool.executeTask(new ReceiveChannelTask(message));
				} else {
					processReceiveChannelMessage(message);
				}
			} else {
				log.error("handle null channel message !");
			}
			if (thread_pool != null) {
				thread_pool.executeTask(new CleanTask());
			}
		}
	    
		private class ReceiveTask implements Runnable
		{
			final MessageHeader message;
			
			public ReceiveTask(MessageHeader message) {
				this.message = message;
			}
			
			@Override
			public void run() {
				try {
					processReceiveSessionMessage(message);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
		}
		
		private class ReceiveChannelTask implements Runnable
		{
			final MessageHeader message;

			public ReceiveChannelTask(MessageHeader message) {
				this.message = message;
			}
			
			@Override
			public void run() {
				try {
					processReceiveChannelMessage(message);
				} catch (Throwable err) {
					err.printStackTrace();
				}
			}
		}
	}


//	-------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------
	
	private class CleanTask implements Runnable
	{
		@Override
		public void run() {
			cleanRequestAndNotify() ;
		}
	}
	
//	-------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	private class Request implements Runnable
	{
		final MessageHeader 				Message;
		final WaitingListener[]				Listeners;
		final long 							SendTimeOut;
		MessageHeader						Response;
		
		private Request(MessageHeader msg, long timeout, WaitingListener ... listeners)
		{
			if (SendedPacks.get() == 0) {
				SendedPacks.incrementAndGet();
			}
			msg.PacketNumber 	= SendedPacks.getAndIncrement();
	    	
			this.Message 		= msg;
			this.SendTimeOut 	= timeout > 0 ? timeout : 0;
			this.Listeners 		= listeners;
		}
		
		public void run () 
		{
//			System.out.println("request " + this);
			if (SendTimeOut > 0) {
				synchronized (this) {
					send(Message);
					try {
						wait(SendTimeOut);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				send(Message);
			}
		}
		
		private void messageResponsed(MessageHeader response) 
		{
//			System.out.println("response " + this);
			Response = response;
			request_response_ping.set((int)(response.DynamicReceiveTime - Message.DynamicSendTime));
			if (SendTimeOut > 0) {
				synchronized (this){
					notify();
				}
			}
			for (WaitingListener wait : Listeners) {
				if (wait != null) {
					wait.response(BasicNetService.this, Message, response);
				}
			}
		}
		
		private void timeout() {
			for (WaitingListener wait : Listeners) {
				if (wait != null) {
					wait.timeout(BasicNetService.this, Message, Message.DynamicSendTime);
				}
			}
		}
		
//		private void removeWaitingListener(Class<?> type) {
//			synchronized (Listener){
//				for (int i=Listener.size()-1; i>=0; --i) {
//					WaitingListener l = Listener.get(i);
//					if (type.isInstance(l)) {
//						Listener.remove(i);
//						log.info("removeWaitingListener : " + l);
//					}
//				}
//			}
//		}
		
		protected boolean isDroped() {
			return System.currentTimeMillis() - Message.DynamicSendTime > DropRequestTimeOut;
		}
		
		public String toString() {
			return "Request : Message=" + Message;
		}
		
	}
	
}
