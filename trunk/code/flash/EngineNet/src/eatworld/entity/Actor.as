package eatworld.entity
{
	import com.gt.net.*;
	
	import eatworld.net.*;
	
	public class Actor implements WaitingListener, NotifyListener
	{
		private var service : EatNetService = new EatNetService();
		
		public function Actor()
		{
			service.connect("game.lordol.com", 17001);
			
		}
		
		public function getNetService() : EatNetService 
		{
			return service;
		}

		public function response(request : MessageHeader, response : MessageHeader) : void
		{
			trace("response [" + request + "] [" + response + "]");
		}
		
		public function timeout(request : MessageHeader, time : uint) : void
		{
			trace("timeout [" + request + "] [" + time + "]");
		}
		
		public function notify(notify : MessageHeader) : void 
		{
			trace("notify [" + notify + "]");
			
		}

	}
}