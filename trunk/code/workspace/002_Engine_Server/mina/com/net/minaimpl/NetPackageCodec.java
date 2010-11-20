package com.net.minaimpl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.SynchronizedProtocolDecoder;
import org.apache.mina.filter.codec.SynchronizedProtocolEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.io.NIOSerialize;
import com.net.ExternalizableFactory;
import com.net.ExternalizableMessage;
import com.net.MessageHeader;
import com.net.NetDataOutput;
import com.net.Protocol;


public class NetPackageCodec extends MessageHeaderCodec
{
	private static final Logger _log = LoggerFactory.getLogger(NetPackageCodec.class.getName());

	
	
	class NetPackageDecoder extends CumulativeProtocolDecoder 
	{
		@Override
		public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
		{
			int begin = in.position();
			super.decode(session, in, out);
			TotalReceivedBytes += (in.position() - begin);
		}
		
//		如果你能够解析一次，那就需要返回 true; 
//		如果1个不够解析，返回false，继续囤积数据，并等待下一次解析
	    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception 
	    {
    		//得到上次的状态
	    	Integer protocol_size = (Integer)session.getAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    	
	    	try
        	{
	    		// 如果上次无状态，则代表是首次解包
	    		if (protocol_size == null)
        		{
	    			// 有足够的数据
	    			if (in.remaining() >= protocol_fixed_size)
	    			{
						// 判断是否是有效的数据包头
						for (int i = 0; i < protocol_start.length; ++i) {
							byte data = in.get();
							if (data != protocol_start[i]) {
								in.clear();
								// 丢弃掉非法字节//返回true代表这次解包已完成,清空状态并准备下一次解包
								throw new IOException("bad head, drop data : " + data);
							}
						}
			            
			            // 生成新的状态
						protocol_size = new Integer(in.getInt());
						session.setAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL, protocol_size);
	    			}
	    			else
	    			{
	    				// 没有足够的数据时,返回 false
	    				// 返回false代表这次解包未完成,需要等待
	    				return false;
	    			}
        		}
	    		
	    		// 继续解析上一个未完成的包内容
	    		if (protocol_size != null)
	    		{
	    			int message_size = protocol_size - protocol_fixed_size;
	    			
	    			// 如果有足够的数据
	    			if (in.remaining() >= message_size) 
	    			{
	    				// 清空当前的解包状态
	    				session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    				
	    				ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
	    				p.DynamicReceiveTime		= System.currentTimeMillis();

    					p.Protocol 					= in.get();			// 1
    					p.SessionID 				= in.getLong();		// 8
    					p.PacketNumber				= in.getInt();		// 4
    					
    					switch (p.Protocol) {
	    				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
	    					p.ChannelID 			= in.getInt();		// 4
	    					p.ChannelSesseionID		= in.getLong();		// 8
	    					break;
	    				}
    					
    					p.transmission_type			= in.get();			// 1
    					
	    				// 解出包包含的二进制消息
	    				switch(p.transmission_type) {
						case ProtocolImpl.TRANSMISSION_TYPE_SERIALIZABLE:
		    				p.message = (MessageHeader)in.getObject(class_loader);
							break;
						case ProtocolImpl.TRANSMISSION_TYPE_EXTERNALIZABLE:
							p.message = ext_factory.createMessage(in.getInt());	// ext 4
							ExternalizableMessage ext = (ExternalizableMessage)p.message;
							ext.readExternal(new NetDataInputImpl(in));
							break;
						}

	    				// 告诉 Protocol Handler 有消息被接收到
	    				out.write(p);
	    				
	    				//System.out.println("decoded <- " + session.getRemoteAddress() + " : " + protocol);

	    				ReceivedMessageCount ++;
	    				
	    				// 无论如何都返回true，因为当前解包已完成
	    				return true;
	    			} 
	    			// 如果没有足够的数据
	    			else
	    			{
	    				// 没有足够的数据时,返回 false
	    				// 返回false代表这次解包未完成,需要等待
	    				return false;
	    			}
	    		}
        	
	    		return false;
        	}
    		catch(Throwable err)
    		{
        		if (protocol_size != null) {
        			_log.warn("drop and clean decode state ! size = " + protocol_size);
        			session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
        		}
        		//err.printStackTrace();
        		_log.error(err.getMessage() + " : decode error : " + session , err);
        		
        		// 当解包时发生错误，则
        		// 返回true代表这次解包已完成,清空状态并准备下一次解包
        		return true;
        	}
	    	
    	}

	    
	}

	
//	-------------------------------------------------------------------------------------------------------------------
	
    class NetPackageEncoder extends ProtocolEncoderAdapter
    {
    	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception 
    	{
    		try
    		{
    			ProtocolImpl p = (ProtocolImpl)message;
				p.DynamicSendTime = System.currentTimeMillis();
    			
    			IoBuffer buffer = IoBuffer.allocate(PACKAGE_DEFAULT_SIZE);
    			buffer.setAutoExpand(true);
    			
    			
    			int oldposition = buffer.position();
    			{
	    			// fixed data region
    				{
		    			buffer.put			(protocol_start);		// 4
		    			buffer.putInt		(protocol_fixed_size);	// 4
    				}
    				
	    			// message data region
    				int cur = buffer.position();
	    			{
						buffer.put			(p.Protocol);			// 1
						buffer.putLong		(p.SessionID);			// 8
						buffer.putInt		(p.PacketNumber);		// 4
						
						switch (p.Protocol) {
	    				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:
	    				case Protocol.PROTOCOL_CHANNEL_MESSAGE:
							buffer.putInt	(p.ChannelID);			// 4
							buffer.putLong	(p.ChannelSesseionID);	// 8
							break;
						}
						
						if (p.message instanceof ExternalizableMessage) {
							buffer.put		(ProtocolImpl.TRANSMISSION_TYPE_EXTERNALIZABLE);	// 1
							buffer.putInt	(ext_factory.getType(p.message));	// ext 4
							ExternalizableMessage ext = (ExternalizableMessage)p.message;
							ext.writeExternal(new NetDataOutputImpl(buffer));
						} 
						else if (p.message instanceof Serializable) {
							buffer.put		(ProtocolImpl.TRANSMISSION_TYPE_SERIALIZABLE);		// 1
							buffer.putObject(p.message);
						}
						else {
							buffer.put		(ProtocolImpl.TRANSMISSION_TYPE_UNKNOW);			// 1
						}
	    			}
	    			buffer.putInt(4, protocol_fixed_size + (buffer.position() - cur));
	    		}
    			TotalSentBytes += (buffer.position() - oldposition);

    			p.DynamicSendTime = System.currentTimeMillis();
    			
    			buffer.shrink();
    			
    			buffer.flip();
    			out.write(buffer);
    			
    			//System.out.println("encoded -> " + session.getRemoteAddress() + " : " + protocol);
				SendedMessageCount ++;
        	}
    		catch(Throwable err) 
    		{
    			_log.error(err.getMessage() + "\nencode error : " + session + " :\n" + message + "", err);
        		//err.printStackTrace();
        	}
    	}
    }
	
//	-------------------------------------------------------------------------------------------------------------------

    final private ProtocolEncoder	encoder			= new NetPackageEncoder();
    final private ProtocolDecoder	decoder			= new NetPackageDecoder();
    
    public NetPackageCodec(ClassLoader cl, ExternalizableFactory ext_factory) 
    {
    	super(cl, ext_factory);
    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
    

}


