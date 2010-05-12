package com.net;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;



public abstract class MessageHeader implements Serializable
{
	private static final long serialVersionUID = 1L;
//----------------------------------------------------------------------------------------------------------------		

	/** 传输包的初始大小 */
	final public static int 	PACKAGE_DEFAULT_SIZE 				= 2*1024;// 30k
	
//----------------------------------------------------------------------------------------------------------------		

	/** Session/Server 之间的消息 */
	final public static short	PROTOCOL_SESSION_MESSAGE			= 0x3030;
	/** Session/Channel 之间的消息 */
	final public static short	PROTOCOL_CHANNEL_MESSAGE			= 0x3052;
	
//----------------------------------------------------------------------------------------------------------------		
//	protocol fields
	
	/**频道ID，仅PROTOCOL_CHANNEL_*类型的消息有效*/
	transient public int		ChannelID;
	
	/**频道发送者的SessionID, ipv4*/
	transient public long		ChannelSesseionID;
	
	/**可以同时包含连接的IP地址和端口号以及所在的服务器端的频段号, ipv4*/
	transient public long 		SessionID;
	
	/**消息类型*/
	transient public short 		Protocol;
	
	/**匹配Request和Response的值，如果为0，则代表为Notify*/
	transient public int		PacketNumber	= 0;
	
	
//----------------------------------------------------------------------------------------------------------------		
//	dynamic fields

	/**发送时间*/
	transient public long		DynamicSendTime;
	
	/**接收时间*/
	transient public long		DynamicReceiveTime;
	
//----------------------------------------------------------------------------------------------------------------		
	

	/***
	 * 当反序列化结束后被调用, 仅普通序列化时被调用<br>
	 * @param data
	 */
	protected void onReadResolve(){}

	/***
	 * 当序列化开始前被调用, 仅普通序列化时被调用<br>
	 * @param data
	 */
	protected void onWriteReplace(){}
	
	private Object writeReplace() throws ObjectStreamException {
		onWriteReplace();
		return this;
	}
	private Object readResolve() throws ObjectStreamException {
		onReadResolve();
		return this;
	}
	
}
