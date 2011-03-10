package com.gt.net
{
	import com.gt.net.MessageHeader;
	import com.gt.net.WaitingListener;
	import com.gt.net.MessageManager;
	import com.gt.util.Util;
	
	public class NetServiceRequest
	{
		static public var DropRequestTimeOut : uint = 60000;
		
		static private var SendedPacks : uint = 0;
		
		internal var Message : MessageHeader ;
		internal var ResponseType : int ;
		internal var Listener : WaitingListener ;
		internal var Time : uint;
		
		public function NetServiceRequest(msg : MessageHeader, responsetype : int, listener : WaitingListener)
		{
			msg.PacketNumber = SendedPacks ++ ;
	    	
			this.Message = msg;
			this.ResponseType = responsetype;
			this.Listener = listener;
			this.Time = Util.runTimeMillis();
		}
		
		public function messageResponsed(response : MessageHeader) : void
		{
			Listener.response(Message, response);
		}
		
		public function isDroped() : Boolean
		{
			return Util.runTimeMillis() - Time > DropRequestTimeOut;
		}
		
		public function toString() : String
		{
			return "ResponseType=" + ResponseType + " Message=" + Message;
		}
		
	}

}