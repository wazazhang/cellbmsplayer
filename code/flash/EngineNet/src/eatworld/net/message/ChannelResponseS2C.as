package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChannelResponseS2C extends MessageHeader
	{
		public function ChannelResponseS2C()
		{
			super(EatMessages.CHANNEL_RESPONSE_S2C);
		}

	}
}