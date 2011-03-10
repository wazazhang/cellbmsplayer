package eatworld.net.message
{
	import eatworld.net.EatMessages;
	import com.gt.net.MessageHeader;
	
	public class ChatRequestC2S extends MessageHeader
	{
		public function ChatRequestC2S()
		{
			super(EatMessages.CHAT_REQUEST_C2S);
		}

	}
}