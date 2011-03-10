package eatworld.net
{
	
	import com.gt.net.MessageHeader;
	import com.gt.net.MessageManager;
	
	import eatworld.net.message.*;

	public class EatMessages implements MessageManager
	{
		// type enum
		public static const LOGIN_REQUEST_C2S : int		= (20001);
		public static const LOGIN_RESPONSE_S2C : int	= (20002);
		
		public static const CHAT_REQUEST_C2S : int		= (20011);
		public static const CHAT_RESPONSE_S2C : int		= (20012);
		public static const CHAT_NOTIFY_S2C : int		= (20013);
		
		public static const CHANNEL_REQUEST_C2S : int	= (20021);
		public static const CHANNEL_RESPONSE_S2C : int	= (20022);
		public static const CHANNEL_NOTIFY_S2C : int	= (20023);
		

		public function createMessage(type: int): MessageHeader 
		{
			switch (type)
			{
			case LOGIN_REQUEST_C2S:
				return new LoginRequestC2S();
			case LOGIN_RESPONSE_S2C:
				return new LoginResponseS2C();
					
			case CHAT_REQUEST_C2S: 
				return new ChatRequestC2S();
			case CHAT_RESPONSE_S2C:
				return new ChatResponseS2C();
			case CHAT_NOTIFY_S2C:
				return new ChatNotifyS2C();
					
			case CHANNEL_REQUEST_C2S:
				return new ChannelRequestC2S();
			case CHANNEL_RESPONSE_S2C:
				return new ChannelResponseS2C();
			case CHANNEL_NOTIFY_S2C:
				return new ChannelNotifyS2C();
			}
			
			return null;
		}
	
		public function getMessageResponsePair(type: int) : int
		{
			switch (type)
			{
			case LOGIN_REQUEST_C2S:
				return LOGIN_RESPONSE_S2C;
			case CHAT_REQUEST_C2S: 
				return CHAT_RESPONSE_S2C;
			case CHANNEL_REQUEST_C2S:
				return CHANNEL_RESPONSE_S2C;
			}
			return 0;
		}
		
		
	}
}

