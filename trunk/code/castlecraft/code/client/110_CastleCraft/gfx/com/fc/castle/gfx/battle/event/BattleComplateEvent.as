package com.fc.castle.gfx.battle.event
{
	import flash.events.Event;
	
	public class BattleComplateEvent extends Event
	{
		public static const TYPE:String = "BattleComplateEvent";
		
		public var failedForce:int
		public function BattleComplateEvent(failedForce:int)
		{
			super(TYPE, true, true);
			this.failedForce = failedForce;
		}
	}
}