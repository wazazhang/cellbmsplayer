package com.net.minaimpl;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeepAlive extends KeepAliveFilter 
{
	private static Logger log = LoggerFactory.getLogger(KeepAlive.class);
	
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
	  
	static class ExceptionHandler implements KeepAliveRequestTimeoutHandler {      
	    public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session) throws Exception {      
	    	log.error("KeepAliveRequestTimeout : Connection lost, session will be closed");      
	        session.close(true);
	    }      
	}   
	  
	/**  
	 * 继承于KeepAliveMessageFactory，当心跳机制启动的时候，需要该工厂类来判断和定制心跳消息  
	 */  
	static class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {   
	    private static final byte int_req = -1;   
	    private static final byte int_rep = -2;    
	    private static final IoBuffer KAMSG_REQ = IoBuffer.wrap(new byte[]{int_req});      
	    private static final IoBuffer KAMSG_REP = IoBuffer.wrap(new byte[]{int_rep});     
	          
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
