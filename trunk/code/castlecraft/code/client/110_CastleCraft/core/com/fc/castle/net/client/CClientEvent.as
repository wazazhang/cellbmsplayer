package com.fc.castle.net.client
{
	import com.cell.net.io.MutualMessage;
	
	import flash.events.Event;
	
	public class CClientEvent extends Event
	{		
		public static const ERROR		: String = "ERROR"; 
		
		public static const RESPONSE 	: String = "RESPONSE"; 

		
		public var super_event	: Event;
		
		public var response		: MutualMessage;
		
		public var request		: MutualMessage;
		
		public function CClientEvent(type : String,
									 super_event : Event, 
									 request: MutualMessage,
									 response : MutualMessage)
		{
			super(type);
			this.super_event = super_event;
			this.request = request;
			this.response = response;
		}
		
		
	}
}