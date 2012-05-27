package com.fc.castle.gfx.battle.event
{
	import flash.events.Event;
	
	public class APTargetComplateEvent extends Event
	{
		
		public static const TYPE:String = "APTargetComplateEvent";
		
		public function APTargetComplateEvent()
		{
			super(TYPE, true, true);
		}
	}
}