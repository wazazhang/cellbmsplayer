package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChannelRequestC2S extends MessageHeader
	{
		public function ChannelRequestC2S()
		{
			super(EatMessages.CHANNEL_REQUEST_C2S);
		}

	}
}