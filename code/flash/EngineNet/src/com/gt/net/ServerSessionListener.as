package com.gt.net{

	import com.gt.net.MessageHeader;
	
	
	public interface ServerSessionListener
	{
		function connected( session: ServerSession) : void ;
		
		function disconnected( graceful: Boolean,  reason: String) : void;
		
		function receivedMessage( message: MessageHeader) : void;
		
	    function joinedChannel( channel: ClientChannel) : ClientChannelListener;
		
	}
}