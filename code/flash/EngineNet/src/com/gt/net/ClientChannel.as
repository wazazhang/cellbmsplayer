package com.gt.net {

	import com.gt.net.MessageHeader;
	
	public class ClientChannel {
	
		private var Session: ServerSession;
		
		private var Name: String;
		
		internal var Listener : ClientChannelListener;
		
		public function ClientChannel(session: ServerSession,  name: String) {
			Session = session;
			Name = name;
		}
		
		public function getName() : String {
			return Name;
		}
	
		public function send( message: MessageHeader) : void {
			Session.sendChannel(message, this);
		}
		
		public function toString() : String {
			return Name;
		}
	}

}