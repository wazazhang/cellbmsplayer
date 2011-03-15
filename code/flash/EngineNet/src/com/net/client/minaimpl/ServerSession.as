package com.net.client.minaimpl
{	
	import com.net.client.Message;
	import com.net.client.MessageFactory;
	import com.net.client.Protocol;
	import com.net.client.ServerSession;
	import com.net.client.ServerSessionListener;
	
	import flash.errors.*;
	import flash.events.*;
	import flash.net.*;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	import flash.utils.IDataInput;
	
	public class ServerSession implements com.net.client.ServerSession
	{
		/** 当前远程地址*/
		private var serveraddr 			: String; 
		
		/** SOCKET套接字*/
		private var connector 			: Socket;
		
		/** 服务器监听器*/
		private var listener 			: ServerSessionListener;
		
		/** 消息类型管理器*/
		private var message_factory		: MessageFactory;
		
		/** 未解析完的数据*/
		private var undecoded_buffer	: ByteArray;
		
		/** 未解析完的包*/
		private var uncomplete_package	: Protocol;
		
		
		public function ServerSession(factory : MessageFactory, listener : ServerSessionListener) 
		{
			this.message_factory = factory;
			this.connector = new Socket();
			this.connector.addEventListener(Event.CLOSE, 						closeHandler);
			this.connector.addEventListener(Event.CONNECT, 						connectHandler);
			this.connector.addEventListener(IOErrorEvent.IO_ERROR, 				ioErrorHandler);
			this.connector.addEventListener(SecurityErrorEvent.SECURITY_ERROR,	securityErrorHandler);
			this.connector.addEventListener(ProgressEvent.SOCKET_DATA, 			socketDataHandler);
			this.listener = listener;
		}
		
		public function connect(host : String,  port : int) : Boolean
		{
			this.serveraddr = host + ":" + port;
			this.connector.connect(host, port);
			return true;
		}
		
		
		public function getRemoteAddress() : String
		{
			return this.serveraddr;
		}
		
		public function toString() : String 
		{
			return this.connector.toString() + "," + this.serveraddr;
		}
		
		public function isConnected() : Boolean
		{
			return this.connector.connected;
		}
		
		public function disconnect(force: Boolean) : Boolean
		{
			this.connector.close();
			return true;
		}
		
		public function send(message: Message) : Boolean
		{
			var protocol : Protocol = new Protocol();
			var stream : ByteArray = encode(protocol);
			connector.writeBytes(stream);
			connector.flush();
			return true;
		}
		
		public function sendRequest() : Boolean
		{
			return true;
		}
		
//		---------------------------------------------------------------------------------------------------------

		public function getSentMessageCount():Number {
			return 0;
		}
		
		public function getReceivedMessageCount () :Number {
			return 0;
		}
		
		public function getSentBytes():Number {
			return 0;
		}
		
		public function getReceivedBytes():Number {
			return 0;
		}
		
		public function getHeartBeatSent():Number {
			return 0;
		}
		
		public function getHeartBeatReceived():Number {
			return 0;
		}
		
//		---------------------------------------------------------------------------------------------------------
		
		
		private function closeHandler(event:Event):void {
			trace("closeHandler: " + event);
			this.listener.disconnected(this, true, event.toString());
		}
		
		private function connectHandler(event:Event):void {
			trace("connectHandler: " + event);
			this.listener.connected(this);
		}
		
		private function ioErrorHandler(event:IOErrorEvent):void {
			trace("ioErrorHandler: " + event);
			this.disconnect(true);
			this.listener.disconnected(this, false, event.toString());
		}
		
		private function securityErrorHandler(event:SecurityErrorEvent):void {
			trace("securityErrorHandler: " + event);
			this.disconnect(true);
			this.listener.disconnected(this, false, event.toString());
		}
		
		private function socketDataHandler(event:ProgressEvent):void 
		{
			//trace("socketDataHandler: " + event);
			try
			{
				// 先将socket中的数据读入到 ByteArray
				var avaliable : int = this.connector.bytesAvailable;
				var buf : ByteArray = new ByteArray();
				this.connector.readBytes(buf, 0, avaliable);
				
				// 如果有未解析完的数据，则将新数据插入到后面
				if (this.undecoded_buffer!=null) {
					this.undecoded_buffer.writeBytes(buf, 0, buf.bytesAvailable);
					buf = undecoded_buffer;
				}
				
				buf.position = 0;
				
				while (true) 
				{
					// 记录当前位置
					var oldPos : int = buf.position;
					
					// 试图解析一个包
					var decoded : Boolean = decode(buf);
					
					// 如果有数据被解析
					if (decoded)
					{
						// 如果没有数据被读取
						if (buf.position == oldPos){
							try{
								throw new Error("decode() can't return true when buffer is not consumed.");
							}catch(err:Error){
								trace(err + "\n" + err.getStackTrace());
							}
						}
						
						// 若流中无数据，则跳出
						if (!buf.bytesAvailable>0) {
							break;
						}
						
					} else {
						// 若未成功解析，则跳出，等待下一次解析
						break;
					}
				}
				
				if (buf.bytesAvailable>0) {
					// 把未解析完的数据存入状态
					this.undecoded_buffer = new ByteArray();
					this.undecoded_buffer.writeBytes(buf, buf.position, buf.length - buf.position);
				} else {
					// 如果无数据可以解析，则清空状态
					this.undecoded_buffer = null;
				}
			}
			catch(err:EOFError) 
			{
				trace("socketDataHandler error  : " + err);  		
			}
		}
		
		
//		---------------------------------------------------------------------------------------------------------
		
		var			PACKAGE_DEFAULT_SIZE : int			= 2048;
		/** 心跳请求头*/
		const 		heart_beat_req : ByteArray			= new ByteArray(); 
		/** 心跳回馈头*/
		const		heart_beat_rep : ByteArray 			= new ByteArray(); 
		/** 消息头*/
		const		protocol_start : ByteArray 			= new ByteArray(); 
		/** 消息头固定尺寸*/
		var			protocol_fixed_size : int 			= 4 + 4;
		
		
		public function setProtocolStart(
				header : ByteArray, 
				hb_req : ByteArray, 
				hb_rep : ByteArray)
		{
			protocol_start.clear();
			heart_beat_req.clear();
			heart_beat_rep.clear();
			
			protocol_start.writeBytes(header);
			heart_beat_req.writeBytes(hb_req);
			heart_beat_rep.writeBytes(hb_rep);
			
		//	[-1, 0, 0, 6]
		//	[-2, 0, 0, 6]
		// [ 2, 0, 0, 6, ];
		}
		
//		-----------------------------------------------------------------------------------------

		/**
		 * 如果有数据被解析，返回true
		 */
		function decode(buffer : IDataInput) : Boolean
		{
			//得到上次的状态
			var protocol : Protocol = this.uncomplete_package;
			
			try
			{
				// 如果上次无状态，则生成新的状态
				if (protocol == null)
				{
					// 有足够的数据
					if (buffer.bytesAvailable >= Protocol.HeadFixedHeaderSize)
					{
						// 判断是否是有效的数据包头
						var head : ByteArray = new ByteArray();
						buffer.readBytes(head, 0, NetPackageProtocol.MagicStart.length);
						for (var h:int=head.length-1; h>=0; --h){
							if (head[h] != NetPackageProtocol.MagicStart[h]){
								// 丢弃掉非法字节
								trace("bad head, drop data : " + head);
								return true;
							}
						}
						
						// 生成新的状态
						protocol = _uncompleteProtocol = new NetPackageProtocol();
						protocol.Size				= buffer.readInt();
						protocol.Protocol			= buffer.readByte();
						protocol.ChannelSize		= buffer.readInt();
						protocol.ChannelSenderSize	= buffer.readInt();
						protocol.MessageSize		= buffer.readInt();
					}
					else
					{
						// 没有足够的数据时,返回 false
						return false;
					}
				}
				
				// 继续解析包内容
				if (protocol != null)
				{
					// 如果有足够的数据
					if (buffer.bytesAvailable >= protocol.Size - NetPackageProtocol.HeadFixedHeaderSize) 
					{
						// channel name
						if (protocol.ChannelSize > 0){
							protocol.ChannelName = buffer.readUTFBytes(protocol.ChannelSize);
						}
						
						// channel sender
						if (protocol.ChannelSenderSize > 0){
							protocol.ChannelSender = buffer.readUTFBytes(protocol.ChannelSenderSize);
						}
						
						// message
						if (protocol.MessageSize > 0){
							var input : ByteArray = new ByteArray();
							buffer.readBytes(input, 0, protocol.MessageSize);
							var type : int = input.readInt();
							var paknum : int = input.readInt();
							protocol.Message = _messageManager.createMessage(type);
							if (protocol.Message!=null) {
								protocol.Message.Type = type;
								protocol.Message.PacketNumber = paknum;
								protocol.Message.deserialize(input);
							}
						}
						
						// 验证包的尺寸合法性
						if (NetPackageProtocol.validate(protocol))
						{
							// 判断是否是有效的数据包尾
							var tail : ByteArray = new ByteArray();
							buffer.readBytes(tail, 0, NetPackageProtocol.MagicEnd.length);
							for (var t:int=tail.length-1; t>=0; --t){
								if (tail[t] != NetPackageProtocol.MagicEnd[t]){
									// 丢弃掉非法字节
									trace("bad end, drop data : " + tail + " : " + protocol.toString());
									return true;
								}
							}
							
							recivedMessage(protocol);
							trace("decoded <- : " + protocol.toString());
						}
						else
						{
							trace("bad protocol message : " + protocol.toString());
						}
						
						// 清空当前的解包状态
						this.uncomplete_package = null;
						
						return true;
					}
					else
					{
						return false;
					}
				}
				
				return false;
			}
			catch(err : Error)
			{
				trace("decode error  : " + err + "\n" + err.getStackTrace());
				if (protocol != null) {
					trace("drop and clean decode state !\n");
					this.uncomplete_package = null;
				}
			}
			
			return true;
		}
		
		
		function encode(protocol : Protocol) : ByteArray
		{
			try
			{
				protocol.Size = NetPackageProtocol.HeadFixedSize;
				
				var channelnamedata : ByteArray = new ByteArray();
				var channelsenderdata : ByteArray = new ByteArray();
				protocol.ChannelSize = 0;
				protocol.ChannelSenderSize = 0;
				if (protocol.Protocol == NetPackageProtocol.PROTOCOL_CHANNEL_JOIN_S2C ||
					protocol.Protocol == NetPackageProtocol.PROTOCOL_CHANNEL_LEAVE_S2C ||
					protocol.Protocol == NetPackageProtocol.PROTOCOL_CHANNEL_MESSAGE)
				{
					if (protocol.ChannelName!=null){
						IOSerialize.putString(channelnamedata, protocol.ChannelName);
						protocol.Size += channelnamedata.length;
						protocol.ChannelSize = channelnamedata.length;
					}
					if (protocol.ChannelSender!=null){
						IOSerialize.putString(channelsenderdata, protocol.ChannelSender);
						protocol.Size += channelsenderdata.length;
						protocol.ChannelSenderSize = channelsenderdata.length;
					}
				}
				
				var msgdata : ByteArray = new ByteArray();
				if (protocol.Message != null) 
				{
					msgdata.writeInt(protocol.Message.Type);
					msgdata.writeInt(protocol.Message.PacketNumber);
					protocol.Message.serialize(msgdata);
					protocol.Size += msgdata.length;
					protocol.MessageSize = msgdata.length;
				}
				
				var buffer: ByteArray = new ByteArray();
				
				// fixed data region
				buffer.writeByte(NetPackageProtocol.MagicStart[0]);
				buffer.writeByte(NetPackageProtocol.MagicStart[1]);
				buffer.writeByte(NetPackageProtocol.MagicStart[2]);
				buffer.writeByte(NetPackageProtocol.MagicStart[3]);
				buffer.writeByte(NetPackageProtocol.MagicStart[4]);
				buffer.writeByte(NetPackageProtocol.MagicStart[5]);
				buffer.writeByte(NetPackageProtocol.MagicStart[6]);
				buffer.writeByte(NetPackageProtocol.MagicStart[7]);
				buffer.writeInt(protocol.Size);
				buffer.writeByte(protocol.Protocol);
				buffer.writeInt(protocol.ChannelSize);
				buffer.writeInt(protocol.ChannelSenderSize);
				buffer.writeInt(protocol.MessageSize);
				
				// appends data region
				buffer.writeBytes(channelnamedata);
				buffer.writeBytes(channelsenderdata);
				buffer.writeBytes(msgdata);
				
				// data ends
				buffer.writeByte(NetPackageProtocol.MagicEnd[0]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[1]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[2]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[3]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[4]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[5]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[6]);
				buffer.writeByte(NetPackageProtocol.MagicEnd[7]);
				
				trace("encoded -> " + protocol.toString());
				
				return buffer;
			}
			catch(err : Error) 
			{
				trace("encode error  : " + err + "\n" + err.getStackTrace());  		
			}
			
			return null;
		}
		
		
		function recivedMessage(decoded : Protocol) : void
		{
			// 判断是否是联盟消息，协议消息等
			switch (decoded.getProtocol()) {
				case Protocol.PROTOCOL_CHANNEL_JOIN_S2C:{
					break;
				}
				case Protocol.PROTOCOL_CHANNEL_LEAVE_S2C:{
					break;
				}
				case Protocol.PROTOCOL_CHANNEL_MESSAGE:{
					this.listener.receivedMessage(this, decoded, decoded.getMessage());
					break;
				}
				case Protocol.PROTOCOL_SESSION_MESSAGE:
					this.listener.receivedMessage(this, decoded, decoded.getMessage());
					break;
				default:
					if (decoded.getMessage() != null) {
						this.listener.receivedMessage(this, decoded, decoded.getMessage());
					}
			}
			
		}
		
		
	}
	
	
}