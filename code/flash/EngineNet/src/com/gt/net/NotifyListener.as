package com.gt.net 
{

	import com.gt.net.MessageHeader;
	
	public interface NotifyListener
	{
		function notify(notify : MessageHeader) : void ;
	}

}