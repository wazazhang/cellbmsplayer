package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChatNotifyS2C extends MessageHeader
	{
		public function ChatNotifyS2C()
		{
			super(EatMessages.CHAT_NOTIFY_S2C);
		}

	}
}