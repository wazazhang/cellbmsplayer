package com.net.minaimpl;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.net.MessageHeader;
import com.net.minaimpl.server.ServerImpl;

public interface SystemMessages 
{
	/** 当前Session加入频道的事件 */
	final public static byte	EVENT_CHANNEL_JOIN_S2C			= 0x50;
	/** 当前Session离开频道的事件 */
	final public static byte	EVENT_CHANNEL_LEAVE_S2C			= 0x51;

	
	public static class SystemMessageS2C extends MessageHeader
	{	
		private static final long serialVersionUID = 1L;
		final public byte event;
		public SystemMessageS2C(byte event) {
			this.event = event;
		}
	}
	
	
	public static class ServerStatusRequestC2S extends MessageHeader
	{
		private static final long serialVersionUID = 1L;
		public ServerStatusRequestC2S() {}
	}
	
	
	public static class ServerStatusResponseS2C extends MessageHeader
	{
		private static final long serialVersionUID = 1L;
		
		public long	ServerStartTime;
		
		public int	SessionCount;
		
		public long	ReceivedBytes;
		public long	SentBytes;
		
		public long	ReceivedMessageCount;
		public long	SentMessageCount;
		
		public long HeapAvaliable;
		public long HeapTotal;
		public long HeapMax;
		
		public ServerStatusResponseS2C(ServerImpl server) 
		{
			
			ServerStartTime			= server.getStartTime();
			
			SessionCount			= server.getSessionCount();
			
			ReceivedBytes			= server.getReceivedBytes();
			SentBytes				= server.getSentBytes();
			
			ReceivedMessageCount	= server.getReceivedMessageCount();
			SentMessageCount		= server.getSentMessageCount();		
			
			HeapAvaliable			= Runtime.getRuntime().freeMemory();
			HeapTotal				= Runtime.getRuntime().totalMemory();
			HeapMax					= Runtime.getRuntime().maxMemory();
		}
		
	}
	
}
