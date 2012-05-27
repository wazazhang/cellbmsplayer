package com.fc.castle.gfx.battle.event
{
	import com.fc.castle.gfx.battle.BattleBuilding;
	
	import flash.events.Event;
	
	public class BuildBuildingEvent extends Event
	{
		public static const TYPE:String = "BuildBuildingEvent";
		
		public var  battleBuilding:BattleBuilding;
		public function BuildBuildingEvent(bb:BattleBuilding)
		{
			super(TYPE, true, true);
			battleBuilding = bb
		}
		
	}
}