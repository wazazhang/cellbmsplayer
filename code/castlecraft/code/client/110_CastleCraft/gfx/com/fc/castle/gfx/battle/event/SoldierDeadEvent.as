package com.fc.castle.gfx.battle.event
{
	import com.fc.castle.gfx.battle.BattleSoldier;
	
	import flash.events.Event;
	
	public class SoldierDeadEvent extends Event
	{
		static public  const TYPE:String = "SoldierDeadEvent";
		
		public var soldier:BattleSoldier
		public function SoldierDeadEvent(soldier:BattleSoldier)
		{
			super(TYPE, true, true);
			this.soldier = soldier;
		}
	}
}