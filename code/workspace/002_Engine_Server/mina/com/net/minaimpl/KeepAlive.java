package com.net.minaimpl;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;

public class KeepAlive extends KeepAliveFilter 
{
	private static Logger log = LoggerFactory.getLogger(KeepAlive.class);

	private long SentMessageCount = 0;
	private long ReceivedMessageCount = 0;

	public KeepAlive(KeepAliveMessageFactory messageFactory, int interval_sec, int timeout_sec) {   
        super(messageFactory,
        		IdleStatus.BOTH_IDLE,
        		new ExceptionHandler(), 
        		interval_sec, timeout_sec);   
    }   
       
    public KeepAlive(int interval_sec, int timeout_sec) {   
        super(new KeepAliveMessageFactoryImpl(),
        		IdleStatus.BOTH_IDLE, 
        		new ExceptionHandler(), 
        		interval_sec, timeout_sec);   
        this.setForwardEvent(false); //此消息不会继续传递，不会被业务层看见   
    }

	public long getSentMessageCount() {
		return SentMessageCount;
	}
	
	public long getReceivedMessageCount() {
		return ReceivedMessageCount;
	}

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
    	super.messageReceived(nextFilter, session, message);
//    	log.info("heart beat received");
    	ReceivedMessageCount++;
    }
    
    @Override
    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
    	super.messageSent(nextFilter, session, writeRequest);
//    	log.info("heart beat sent");
    	SentMessageCount++;
    }
    
    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
    	super.sessionIdle(nextFilter, session, status);
    }
    
	static class ExceptionHandler implements KeepAliveRequestTimeoutHandler {      
	    public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session) throws Exception {      
	    	log.error("KeepAliveRequestTimeout : Connection lost, session will be closed");      
	        session.close(true);
	    }      
	}   
	  
	/**  
	 * 继承于KeepAliveMessageFactory，当心跳机制启动的时候，需要该工厂类来判断和定制心跳消息 。
	 * 这里对于心跳，只传输1个字节，最精简心跳结构，心跳消息不能和消息头类型冲突。
	 */  
	static class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory
	{   
		private final byte int_req;
		private final byte int_rep;
		private final IoBuffer KAMSG_REQ;
		private final IoBuffer KAMSG_REP;

	    public KeepAliveMessageFactoryImpl() {
	    	int_req = MessageHeaderCodec.heart_beat_req[0];
			int_rep = MessageHeaderCodec.heart_beat_rep[0];
		    KAMSG_REQ = IoBuffer.wrap(MessageHeaderCodec.heart_beat_req);      
		    KAMSG_REP = IoBuffer.wrap(MessageHeaderCodec.heart_beat_rep);   
		}
	    
	    public Object getRequest(IoSession session) {      
	        return KAMSG_REQ.duplicate();      
	    }      
	  
	    public Object getResponse(IoSession session, Object request) {      
	        return KAMSG_REP.duplicate();      
	    }      
	  
	    public boolean isRequest(IoSession session, Object message) {     
	        if(!(message instanceof IoBuffer))   
	            return false;   
	        IoBuffer realMessage = (IoBuffer)message;   
	        if(realMessage.limit() != 1)   
	            return false;   
	           
	        boolean result = (realMessage.get() == int_req);   
	        realMessage.rewind();   
	        return result;   
	    }      
	  
	    public boolean isResponse(IoSession session, Object message) {       
	        if(!(message instanceof IoBuffer))   
	            return false;   
	        IoBuffer realMessage = (IoBuffer)message;   
	        if(realMessage.limit() != 1)   
	            return false;   
	        boolean result = (realMessage.get() == int_rep);      
	        realMessage.rewind();   
	        return result;   
	    }      
	}  

}
