package com.gt.net 
{
	import flash.utils.Dictionary;

	public class NetService implements ServerSessionListener , ClientChannelListener
	{
		private var NotifyListeners : Dictionary = new Dictionary();
		
		private var WaitingListeners : Dictionary = new Dictionary();
	
		protected var Session : ServerSession;
		
		protected var CurMessageManager : MessageManager;
		
		private var ServerHost : String;
		
		private var ServerPort : int;
		
		//-----------------------------------------------------------------------------------------------------------
		
		public function NetService(manager : MessageManager, session : ServerSession) 
		{
			CurMessageManager = manager;
			Session = session;
		}
	
		final public function getSession() : ServerSession
		{
			return Session;
		}
	
		final public function connect(host : String, port : int) : Boolean
		{
	    	if (!Session.isConnected()) 
	    	{
	    		trace("connecting... " + host + ":" + port);
	    		try {
	    			ServerHost = host;
	    			ServerPort = port;
	    			Session.connect(host, port, this);
	    			return true;
	    		} catch (err : Error) {
	    			trace(err.message + " : " + err.getStackTrace())
	    			return false;
	    		}
	    	}
	    	return true;
		}
    
		final public function reconnect() : Boolean 
		{
	    	if (!Session.isConnected())
	    	{
				try {
					Session.connect(ServerHost, ServerPort, this);
					return true;
				} catch (err : Error) {
	    			trace(err.message + " : " + err.getStackTrace())
	    			return false;
	    		}
			}
	    	return true;
		}
    
	    final public function isConnected() : Boolean 
	    {
	    	if (Session.isConnected()) {
	    		return Session.isConnected();
			}else{
				printNotConnectError();
			}
	    	return false;
	    }
    
		final public function disconnect(force : Boolean) : void
	    {
	    	if (Session.isConnected()) {
	    		Session.disconnect(force);
			}else{
				printNotConnectError();
			}
	    }
    
	    final public function send(message : MessageHeader) : void
	    {
	    	if (Session.isConnected()) {
	    		try {
	    			Session.send(message);
	    		} catch (err : Error) {
	    			trace(err.message + " : " + err.getStackTrace())
	    		}
			}else{
				printNotConnectError();
			}
	    }

		final public function sendRequest(message : MessageHeader, listener : WaitingListener) : void
		{
	    	try{
	    		// drop timeout request
				for (var key:Object in WaitingListeners) 
				{
					var req : NetServiceRequest = WaitingListeners[key];
					if (req.isDroped()) {
						delete WaitingListeners[key];
						req.Listener.timeout(req.Message, req.Time);
					}
				}
	    	} catch (err : Error) {
	    		trace(err.message + " : " + err.getStackTrace())
	    	}
	    	
			var request : NetServiceRequest = new NetServiceRequest(
					message, 
					CurMessageManager.getMessageResponsePair(message.Type), 
					listener);
			WaitingListeners[request.Message.PacketNumber] = request;
			send(message);
		}
	
		final public function registNotifyListener(type : int, listener : NotifyListener) : void
		{
			if (listener == null) {
				delete WaitingListeners[type];
			}
			else {
				NotifyListeners[type] = listener;
			}
		}
		
		
		public static function printNotConnectError() : void
		{
			trace("session is not connect, please call connect(String host, String port) first !");
		}
	
	
		//-----------------------------------------------------------------------------------------------------------
	
	
	
	
		final public function connected(session: ServerSession) : void
		{
			trace("connected : " + session + "\n");
		}
		
		final public function disconnected(graceful: Boolean,  reason: String) : void
		{
			trace("disconnected : " + reason + " : " + graceful?"good":"exception" + "\n");
		}
		
		final public function receivedMessage(message: MessageHeader) : void 
		{
			//trace("receivedMessage : " + message + "\n");
			
			if (message != null)
	    	{
		    	var notify : NotifyListener = NotifyListeners[message.Type];
		    	delete WaitingListeners[message.Type];
		    	
		    	if (notify != null) {
		    		notify.notify(message);
		    	}
		    	else {
			    	var request : NetServiceRequest = WaitingListeners[message.PacketNumber];
			    	if (request != null) {
			    		request.messageResponsed(message);
			    	}
			    	else {
			    		trace("handle unknow message : " + message);
			    	}
		    	}
	    	}
	    	else
	    	{
	    		trace("handle null message !");
	    	}
		}
		
	    final public function joinedChannel(channel : ClientChannel) : ClientChannelListener
	    {
	    	trace("joinedChannel : " + channel + "\n");
			return this;
		}
		
		final public function leftChannel(channel : ClientChannel) : void 
		{
			trace("leftChannel : " + channel + "\n");
		}
	
	    final public function receivedChannelMessage(channel : ClientChannel, message : MessageHeader) : void 
	    {
	    	//trace("receivedChannelMessage : " + channel + " : " + message + "\n");
	    	
	    	if (message != null) 
		    {
			    var notify : NotifyListener = NotifyListeners[message.Type];
			    
			    if (notify!=null) {
			    	notify.notify(message);
			    }
			    else {
			    	trace("handle unknow channel message : " + message);
			    }
		    }
			else
			{
		    	trace("handle null channel message !");
		    }
	    }
	
	}
}
	

