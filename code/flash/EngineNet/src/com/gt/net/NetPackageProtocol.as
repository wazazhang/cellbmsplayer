package com.gt.net {

	import flash.utils.ByteArray;
	
	public class NetPackageProtocol 
	{
		static public const PACKAGE_MAX_SIZE : int 			= 65500;
		
		static public const CHAR_SET : String 				= "UTF-8";
		
		//----------------------------------------------------------------------------------------------------------------		
		
		static public const PROTOCOL_SESSION_MESSAGE: int	= 0x30;
		
		static public const PROTOCOL_CHANNEL_JOIN_S2C: int	= 0x50;
	
		static public const PROTOCOL_CHANNEL_LEAVE_S2C: int	= 0x51;
		
		static public const PROTOCOL_CHANNEL_MESSAGE:int	= 0x52;
		
		//----------------------------------------------------------------------------------------------------------------		
		
		static public const HeadFixedHeaderSize : int = 8 + 4 + 1 + 4 + 4 + 4;
		static public const HeadFixedTailSize : int = 8;
		static public const HeadFixedSize : int = HeadFixedHeaderSize + HeadFixedTailSize;
		
		//----------------------------------------------------------------------------------------------------------------		
		
		/*************************************************************/
		// fixed protocol head 
		static internal const MagicStart : Array = [1,9,8,2,0,9,1,0];	// [8]
		/************************************************************/
		internal var Size : int; 										// [4]
		internal var Protocol : int; 									// [1]
		internal var ChannelSize : int ; 								// [4]
		internal var ChannelSenderSize : int; 							// [4]
		internal var MessageSize : int; 								// [4]
		/*************************************************************/
		// channel info
		internal var ChannelName : String; 								// [ChannelSize]
		internal var ChannelSender : String; 							// [ChannelSenderSize]
		// message info
		internal var Message : MessageHeader; 							// [MessageSize]
		/************************************************************/
		// fixed protocol end
		static internal const MagicEnd : Array = [1,9,8,5,0,5,1,6];		// [8]
		/************************************************************/
		
		//----------------------------------------------------------------------------------------------------------------		
		
		public function NetPackageProtocol() {}
		
		public function getProtocol() : int {
			return Protocol;
		}
		public function getChannelName() : String {
			return ChannelName;
		}
		public function getChannelSender() : String {
			return ChannelSender;
		}
		public function getMessage() : MessageHeader {
			return Message;
		}
		
		public function toString() : String {
			return "NetPackageProtocol" +
					" Size=" + Size + 
					" Protocol=0x" + Protocol.toString(16) + 
					" ChannelName=\"" + ChannelName + "\"("+ChannelSize+")" +
					" ChannelSender=\"" + ChannelSender + "\"("+ChannelSenderSize+")" +
					" Message=\"" + Message + "\"("+MessageSize+")" +
					"";
		}
		
		//----------------------------------------------------------------------------------------------------------------		
		
		public static function  createChannelJoin( channelName: String) : NetPackageProtocol {
			var  ret: NetPackageProtocol = new NetPackageProtocol();
			ret.Protocol 		= PROTOCOL_CHANNEL_JOIN_S2C;
			ret.ChannelName 	= channelName;
			ret.ChannelSender 	= "";
			ret.Message			= null;
			return ret;
		}
		
		
		public static function  createChannelLeave( channelName: String) : NetPackageProtocol {
			var  ret: NetPackageProtocol = new NetPackageProtocol();
			ret.Protocol 		= PROTOCOL_CHANNEL_LEAVE_S2C;
			ret.ChannelName 	= channelName;
			ret.ChannelSender 	= "";
			ret.Message			= null;
			return ret;
		}
		
		
		public static function  createChannelMessage(channelName: String,  senderName : String,  message : MessageHeader) : NetPackageProtocol {
			var  ret: NetPackageProtocol = new NetPackageProtocol();
			ret.Protocol 		= PROTOCOL_CHANNEL_MESSAGE;
			ret.ChannelName 	= channelName;
			ret.ChannelSender 	= senderName;
			ret.Message			= message;
			return ret;
		}
	
		public static function  createChannelMessage2(channelName: String,  message: MessageHeader) : NetPackageProtocol {
			var  ret: NetPackageProtocol = new NetPackageProtocol();
			ret.Protocol 		= PROTOCOL_CHANNEL_MESSAGE;
			ret.ChannelName 	= channelName;
			ret.ChannelSender 	= "";
			ret.Message			= message;
			return ret;
		}
		
		public static function  createSessionMessage( message: MessageHeader) : NetPackageProtocol {
			var  ret: NetPackageProtocol = new NetPackageProtocol();
			ret.Protocol = PROTOCOL_SESSION_MESSAGE;
			ret.ChannelName 	= "";
			ret.ChannelSender 	= "";
			ret.Message			= message;
			return ret;
		}
		
		//----------------------------------------------------------------------------------------------------------------		
		
		static public function validate( protocol : NetPackageProtocol) : Boolean {
			if (protocol.Size == HeadFixedSize + protocol.ChannelSize + protocol.ChannelSenderSize + protocol.MessageSize) {
				return true;
			}
			return false;
		}
	}

}