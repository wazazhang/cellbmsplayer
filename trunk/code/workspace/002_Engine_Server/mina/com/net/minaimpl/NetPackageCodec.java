package com.net.minaimpl;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
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


public class NetPackageCodec implements ProtocolCodecFactory
{
	/** 标识为 {@link Serializable} 方式序列化 */
	final public static byte	TRANSMISSION_TYPE_SERIALIZABLE		= 0;
	/** 标识为 {@link ExternalizableMessage} 方式序列化，即以纯手工序列化/反序列化 */
	final public static byte	TRANSMISSION_TYPE_EXTERNALIZABLE	= 1;
	
	private static final Logger _log = LoggerFactory.getLogger(NetPackageCodec.class.getName());

	// 消息头
	final static byte	protocol_start[] 		= new byte[] { 2, 0, 0, 6, };
	// 消息头固定尺寸
	final static int	protocol_fixed_size 	= 4 + 4;

	final static byte[]	zerodata				= new byte[0];

	public static void setProtocolStart(byte b1, byte b2, byte b3, byte b4) 
	{
		protocol_start[0] = b1;
		protocol_start[1] = b2;
		protocol_start[2] = b3;
		protocol_start[3] = b4;
	}
	
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
								// 丢弃掉非法字节//返回true代表这次解包已完成,清空状态并准备下一次解包
								_log.warn("bad head, drop data : " + data);
								return true;
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
	    				int		ChannelID 			= in.getInt();		// 4
    					long	ChannelSesseionID	= in.getLong();		// 8
    					long	SesseionID 			= in.getLong();		// 8
    					short	Protocol 			= in.getShort();	// 2
    					int		PacketNumber		= in.getInt();		// 4
	    				byte	TransmissionType	= in.get();			// 1

	    				// 清空当前的解包状态
	    				session.removeAttribute(SessionAttributeKey.STATUS_DECODING_PROTOCOL);
	    				
	    				final MessageHeader message;
	    				// 解出包包含的二进制消息
	    				switch(TransmissionType) {
						case TRANSMISSION_TYPE_SERIALIZABLE:
		    				message = (MessageHeader)in.getObject(class_loader);
							break;
						case TRANSMISSION_TYPE_EXTERNALIZABLE:
							message = ext_factory.getMessage(in.getInt());	// ext 4
							ExternalizableMessage ext = (ExternalizableMessage)message;
							ext.readExternal(new NetDataInputImpl(in));
							break;
						default:
							throw new NullPointerException("");
						}
	    				
	    				message.ChannelID 			= ChannelID;
	    				message.ChannelSesseionID	= ChannelSesseionID;
	    				message.SessionID 			= SesseionID;
	    				message.Protocol 			= Protocol;
	    				message.PacketNumber		= PacketNumber;

	    				
	    				message.DynamicReceiveTime	= System.currentTimeMillis();
	    				
	    				// 告诉 Protocol Handler 有消息被接收到
	    				out.write(message);
	    				
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
    			MessageHeader header = (MessageHeader)message;

    			IoBuffer buffer = IoBuffer.allocate(MessageHeader.PACKAGE_DEFAULT_SIZE);
    			buffer.setAutoExpand(true);
    			
    			int oldposition = buffer.position();
    			{
	    			// fixed data region
    				{
		    			buffer.put(protocol_start);					// 4
		    			buffer.putInt(protocol_fixed_size);			// 4
    				}
    				
	    			// message data region
    				int cur = buffer.position();
	    			{
						buffer.putInt(header.ChannelID);			// 4
						buffer.putLong(header.ChannelSesseionID);	// 8
						buffer.putLong(header.SessionID);			// 8
						buffer.putShort(header.Protocol);			// 2
						buffer.putInt(header.PacketNumber);			// 4
						
						if (header instanceof ExternalizableMessage) {
							buffer.put(TRANSMISSION_TYPE_EXTERNALIZABLE);	// 1
							buffer.putInt(ext_factory.getType(header));		// ext 4
							ExternalizableMessage ext = (ExternalizableMessage)header;
							ext.writeExternal(new NetDataOutputImpl(buffer));
						} else {
							buffer.put(TRANSMISSION_TYPE_SERIALIZABLE);		// 1
							buffer.putObject(header);
						}
	    			}
	    			buffer.putInt(4, protocol_fixed_size + (buffer.position() - cur));
	    		}
    			TotalSentBytes += (buffer.position() - oldposition);
    			
    			buffer.shrink();
    			
    			buffer.flip();
    			out.write(buffer);
    			
    			header.DynamicSendTime = System.currentTimeMillis();
    			
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

	final ClassLoader				class_loader;
    final ExternalizableFactory		ext_factory;
    
    final private ProtocolEncoder	encoder			= new NetPackageEncoder();
    final private ProtocolDecoder	decoder			= new NetPackageDecoder();
    
//	trace var
    public long 			TotalSentBytes = 0;
    public long 			TotalReceivedBytes = 0;
    
    public long 			SendedMessageCount = 0;
    public long				ReceivedMessageCount = 0;
	
    public NetPackageCodec(ClassLoader cl, ExternalizableFactory ext_factory) 
    {
    	this.class_loader	= cl;
    	this.ext_factory	= ext_factory;
    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
    
//    public ProtocolDecoder getDecoder() throws Exception {
//    	return decoder;
//    }
//    
//    public ProtocolEncoder getEncoder() throws Exception {
//    	return encoder;
//    }
//
//    public static void main(String[] args)
//    {
//		class SObject implements Serializable
//    	{
//			private static final long serialVersionUID = 1L;
//    		
//			int a = 5;
//			String b = "12323";
//			long c = 1234;
//			byte d = 1;
//			short e = 32;
//    	}
//		
//		int count = 100000;
//		
//		SObject object = new SObject();
//		
//
//		try
//		{
//			long startTime = System.currentTimeMillis();
//			
//			for (int i=0; i<count; i++)
//			{
//				IoBuffer buffer = IoBuffer.allocate(MessageHeader.PACKAGE_DEFAULT_SIZE);
//		    	buffer.setAutoExpand(true);
//				buffer.putObject(object);
//				buffer.shrink();
//    			buffer.flip();
//			}
//			
//	    	System.out.println(System.currentTimeMillis() - startTime);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		try
//		{
//			long startTime = System.currentTimeMillis();
//			
//			for (int i=0; i<count; i++) 
//			{
//				ByteArrayOutputStream baos = new ByteArrayOutputStream(MessageHeader.PACKAGE_DEFAULT_SIZE);
//				ObjectOutputStream oos = new ObjectOutputStream(baos);
//	 			oos.writeObject(object);
//
//	 			IoBuffer buffer = IoBuffer.wrap(baos.toByteArray());
//    			buffer.flip();
//			}
//			
//	    	System.out.println(System.currentTimeMillis() - startTime);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
////		try
////		{
////			long startTime = System.currentTimeMillis();
////			for (int i=0; i<100000; i++) {
////				SObject obj = (SObject)buffer.getObject();
////			}
////	    	System.out.println(System.currentTimeMillis() - startTime);
////		}
////		catch (Exception e) {
////			e.printStackTrace();
////		}
//    	
//    	
//    }
}


