package com.net.minaimpl.server.proxy;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.net.MessageHeader;
import com.net.minaimpl.server.ServerImpl;

public interface BridgeMessages 
{
	/** 当前Session加入频道的事件 */
	final public static byte	EVENT_SESSION_CONNECT		= 0x01;
	/** 当前Session离开频道的事件 */
	final public static byte	EVENT_SESSION_DISCONNECT	= 0x02;

	
	public static class BridgeMessageB2R extends MessageHeader
	{	
		private static final long serialVersionUID = 1L;
		final public byte event;
		final public long session_id;
		public BridgeMessageB2R(byte event, long session_id) {
			this.event = event;
			this.session_id = session_id;
		}
	}
	
	public static class BridgeMessageR2B extends MessageHeader
	{	
		private static final long serialVersionUID = 1L;
		public BridgeMessageR2B() {}
	}
}
