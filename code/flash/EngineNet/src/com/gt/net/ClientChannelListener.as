package com.gt.net {

	import com.gt.net.MessageHeader;
	
	public interface ClientChannelListener {
	
	    function leftChannel( channel : ClientChannel) : void;
	
	    function receivedChannelMessage( channel : ClientChannel,  message : MessageHeader) : void ;
	
	}
	
	
}