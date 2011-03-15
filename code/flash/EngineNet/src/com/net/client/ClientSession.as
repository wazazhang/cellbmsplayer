package com.net.client
{	
	import flash.errors.*;
	import flash.events.*;
	import flash.net.*;
	import flash.utils.ByteArray;
	import flash.utils.Dictionary;
	import flash.utils.IDataInput;
	
	public class ClientSession 
	{
		protected var _serveraddr : String; 
		
		protected var _connector : Socket;
		
		protected var _listener: ServerSessionListener;
		
		protected var _channels : Dictionary = new Dictionary();
		
		protected var _messageManager : MessageManager;
		
		// 未解析完的数据
		private var _undecodedBuffer : ByteArray;
		
		// 未解析完的包
		private var _uncompleteProtocol : NetPackageProtocol;
		
		
		//-----------------------------------------------------------------------------------------------
		
		public function ClientSession(manager : MessageManager) {
			_messageManager = manager;
			_connector = new Socket();
			configureListeners();
		}
		
		public function toString() : String 
		{
			return _connector.toString() + "," + _serveraddr;
		}
		
		public function isConnected() : Boolean
		{
			return _connector.connected;
		}
		
		public function connect( host: String,  port: int,  listener: ServerSessionListener) : Boolean
		{
			_serveraddr = host + ":" + port;
			_listener = listener;
			_connector.connect(host, port);
			return true;
		}
		
		public function disconnect( force: Boolean) : Boolean
		{
			_connector.close();
			return true;
		}
		
		public function send( message: MessageHeader) : Boolean
		{
			var protocol : NetPackageProtocol = NetPackageProtocol.createSessionMessage(message);
			var stream : ByteArray = encode(protocol);
			_connector.writeBytes(stream);
			_connector.flush();
			return true;
		}
		
		public function sendChannel( message: MessageHeader,  channel: ClientChannel) : Boolean 
		{
			var protocol : NetPackageProtocol = NetPackageProtocol.createChannelMessage2(channel.getName(), message);
			var stream : ByteArray = encode(protocol);
			_connector.writeBytes(stream);
			_connector.flush();
			return true;
		}
		
		
		private function configureListeners():void {
			_connector.addEventListener(Event.CLOSE, closeHandler);
			_connector.addEventListener(Event.CONNECT, connectHandler);
			_connector.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
			_connector.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
			_connector.addEventListener(ProgressEvent.SOCKET_DATA, socketDataHandler);
		}
		
		private function closeHandler(event:Event):void {
			trace("closeHandler: " + event);
			_listener.disconnected(true, event.toString());
		}
		
		private function connectHandler(event:Event):void {
			trace("connectHandler: " + event);
			_listener.connected(this);
		}
		
		private function ioErrorHandler(event:IOErrorEvent):void {
			trace("ioErrorHandler: " + event);
			this.disconnect(true);
			_listener.disconnected(true, event.toString());
		}
		
		private function securityErrorHandler(event:SecurityErrorEvent):void {
			trace("securityErrorHandler: " + event);
			this.disconnect(true);
			_listener.disconnected(true, event.toString());
		}
		
		private function recivedMessage(decoded : NetPackageProtocol) : void
		{
			// 判断是否是联盟消息，协议消息等
			switch (decoded.getProtocol()) {
				case NetPackageProtocol.PROTOCOL_CHANNEL_JOIN_S2C:{
					var channelj : ClientChannel = new ClientChannel(this, decoded.ChannelName);
					var listenerj : ClientChannelListener = _listener.joinedChannel(channelj);
					channelj.Listener = listenerj;
					_channels[decoded.ChannelName] = channelj;
					break;
				}
				case NetPackageProtocol.PROTOCOL_CHANNEL_LEAVE_S2C:{
					var channell : ClientChannel = _channels[decoded.ChannelName];
					if (_channels!=null) {
						delete _channels[decoded.ChannelName];
						var listenerl : ClientChannelListener = channell.Listener;
						if (listenerl!=null) {
							listenerl.leftChannel(channell);
							channell.Listener = null;
						}
					}
					break;
				}
				case NetPackageProtocol.PROTOCOL_CHANNEL_MESSAGE:{
					var channelm : ClientChannel = _channels[decoded.ChannelName];
					if (channelm!=null && decoded.Message != null) {
						var listenerm : ClientChannelListener = channelm.Listener;
						if (listenerm!=null) {
							listenerm.receivedChannelMessage(channelm, decoded.Message);
						}
					}
					break;
				}
				case NetPackageProtocol.PROTOCOL_SESSION_MESSAGE:
				default:
					if (decoded.Message != null) {
						_listener.receivedMessage(decoded.Message);
					}
			}
			
		}
		
		private function socketDataHandler(event:ProgressEvent):void 
		{
			//trace("socketDataHandler: " + event);
			try
			{
				// 先将socket中的数据读入到 ByteArray
				var avaliable : int = _connector.bytesAvailable;
				var buf : ByteArray = new ByteArray();
				_connector.readBytes(buf, 0, avaliable);
				
				// 如果有未解析完的数据，则将新数据插入到后面
				if (_undecodedBuffer!=null) {
					_undecodedBuffer.writeBytes(buf, 0, buf.bytesAvailable);
					buf = _undecodedBuffer;
				}
				
				buf.position = 0;
				
				while (true) 
				{
					// 记录当前位置
					var  oldPos : int = buf.position;
					
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
						
						if (!buf.bytesAvailable>0) {
							// 若流中无数据，则跳出
							break;
						}
						
					} else {
						// 若未成功解析，则跳出，等待下一次解析
						break;
					}
				}
				
				if (buf.bytesAvailable>0) {
					// 把未解析完的数据存入状态
					_undecodedBuffer = new ByteArray();
					_undecodedBuffer.writeBytes(buf, buf.position, buf.length - buf.position);
				} else {
					// 如果无数据可以解析，则清空状态
					_undecodedBuffer = null;
				}
				
			}
			catch(err:EOFError) 
			{
				trace("socketDataHandler error  : " + err);  		
			}
			
			
		}
		
		
		
		
		
		/**
		 * 如果有数据被解析，返回true
		 */
		private function decode(buffer : IDataInput) : Boolean
		{
			//得到上次的状态
			var protocol : NetPackageProtocol = _uncompleteProtocol;
			
			try
			{
				// 如果上次无状态，则生成新的状态
				if (protocol == null)
				{
					// 有足够的数据
					if (buffer.bytesAvailable >= NetPackageProtocol.HeadFixedHeaderSize)
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
						_uncompleteProtocol = null;
						
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
					trace("drop and clean decode state !\n" + protocol.toString());
					_uncompleteProtocol = null;
				}
			}
			
			return true;
		}
		
		
		private function encode(protocol : NetPackageProtocol) : ByteArray
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
		
	}
	
}