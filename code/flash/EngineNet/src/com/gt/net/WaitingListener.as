package com.gt.net{

	import com.gt.net.MessageHeader;
	
	public interface WaitingListener
	{
		function response(request : MessageHeader, response : MessageHeader) : void;
		
		function timeout(request : MessageHeader, time : uint) : void;
	}

}