package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChannelNotifyS2C extends MessageHeader
	{
		public function ChannelNotifyS2C()
		{
			super(EatMessages.CHANNEL_NOTIFY_S2C);
		}

	}
}
