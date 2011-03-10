package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChatResponseS2C extends MessageHeader
	{
		public function ChatResponseS2C()
		{
			super(EatMessages.CHAT_RESPONSE_S2C);
		}

	}
}