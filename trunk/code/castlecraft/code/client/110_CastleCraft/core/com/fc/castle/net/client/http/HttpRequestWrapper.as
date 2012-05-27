package com.fc.castle.net.client.http
{
	import com.cell.net.http.HttpRequest;
	import com.cell.net.io.MutualMessage;
	import com.fc.castle.net.client.CClientEvent;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	
	[Event(name=CClientEvent.ERROR, 	type="com.fc.castle.net.client.CClientEvent")]  
	[Event(name=CClientEvent.RESPONSE,	type="com.fc.castle.net.client.CClientEvent")]  
	public class HttpRequestWrapper extends EventDispatcher
	{
		private var complete	:Function;
		
		private var error		:Function;
		
		internal var client		:HttpClient;
		
		internal var request	:HttpRequest;
		
		internal var message	:MutualMessage;
		
		public function HttpRequestWrapper(l:Function, er:Function, c:HttpClient, r:HttpRequest, msg:MutualMessage)
		{
			this.complete 	= l;
			this.error 		= er;
			this.client 	= c;
			this.request 	= r;
			this.message 	= msg;
			request.loader.addEventListener(Event.COMPLETE, onResponse);
			request.loader.addEventListener(IOErrorEvent.IO_ERROR, onError);
		}
		
		public function onResponse(e:Event) : void
		{
			trace("------------------------------------------------");
			trace("- HttpRequestWrapper Complete : ");
			trace("------------------------------------------------");
			trace(e.target.data);
			trace("------------------------------------------------");
			
			var res:MutualMessage = client.decode(e.target.data);
			
			if (res != null) 
			{	
				var evt : CClientEvent = new CClientEvent(CClientEvent.RESPONSE, e, message, res);
				
				client.syncResponseWrapper(evt);
				
				if (complete != null) {
					complete.call(null, evt);
				}
			}
			else
			{
				onError(e);
			}
		}
		
		public function onError(e:Event) : void
		{
			if (error != null) {
				error.call(null, new CClientEvent(CClientEvent.ERROR, e, null, null));
			} else {
				trace(e);
			}
		}
		
	}
}