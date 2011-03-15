package com.net.client
{
	public interface ServerSessionListener
	{
		
		function connected( session : ServerSession);
		
		function disconnected( session : ServerSession,  graceful : Boolean,  reason:String);
		
		function sentMessage( session : ServerSession,  protocol : Protocol,  message:Message);
		
		function receivedMessage( session : ServerSession,  protocol : Protocol,  message:Message);
		
	}
}