package com.net.client
{
	
	public interface ServerSession
	{

		/** 获取Session的对端地址 */
		function		getRemoteAddress() : String;
		

		
		function		isConnected() : Boolean;
		
		function 		disconnect( force : Boolean) : Boolean;
		
		function 		send( message : Message):Boolean;
		
		function 		sendRequest( pnum: int, message : Message):Boolean;
		
		function 		connect( host : String,  port : int,  timeout : Number,  listener : ServerSessionListener) :Boolean;
		
		
		
		
		
		function  		getSentMessageCount():Number ;
		
		function  		getReceivedMessageCount () :Number;
		
		function  		getSentBytes():Number;
		
		function  		getReceivedBytes():Number;
		
		function 		getHeartBeatSent():Number;
		
		function 		getHeartBeatReceived():Number;
		
	}
}