package eatworld.net
{
	import com.gt.net.MessageManager;
	import com.gt.net.NetService;
	import com.gt.net.ServerSession;
	import com.gt.net.WaitingListener;
	
	import eatworld.net.EatMessages;
	import eatworld.net.message.*;
	
	
	
	public class EatNetService extends NetService
	{
		public static const manager : MessageManager = new EatMessages();
		
		public function EatNetService(){
			super(manager, new ServerSession(manager));
		}

		
		public function request_login(listener : WaitingListener, user : String, pwd : String) : void {
			sendRequest(LoginRequestC2S.create_Login(user, pwd), listener);
		}
		

	}
}