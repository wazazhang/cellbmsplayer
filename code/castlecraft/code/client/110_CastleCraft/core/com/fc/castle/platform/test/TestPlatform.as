package com.fc.castle.platform.test
{
	import com.fc.castle.platform.Platform;
	
	import flash.events.Event;

	public class TestPlatform implements Platform
	{
		private var token;
		
		public function TestPlatform()
		{
			
		}
		
		public function login(user:String, password:String, complete:Function, error:Function):void
		{
			complete.call(null, new Event(Event.COMPLETE));
		}
	}
}