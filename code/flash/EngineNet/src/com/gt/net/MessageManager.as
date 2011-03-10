package com.gt.net {
	
	import flash.net.*;
	import flash.errors.*;
	import flash.events.*;

	
	public interface MessageManager {
		
		function createMessage(type: int): MessageHeader;
	
		function getMessageResponsePair(messagtype: int) : int;
		
	}
}