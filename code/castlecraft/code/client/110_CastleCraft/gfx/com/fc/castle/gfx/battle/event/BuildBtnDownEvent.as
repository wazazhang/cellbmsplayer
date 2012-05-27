package com.fc.castle.gfx.battle.event
{
	import com.fc.castle.gfx.battle.ui.BtnSoldierBuild;
	
	import flash.events.Event;
	
	public class BuildBtnDownEvent extends Event
	{
		public static const TYPE:String = "SkillBtnDownEvent";
		public var btn:BtnSoldierBuild;
		public function BuildBtnDownEvent(btn:BtnSoldierBuild)
		{
			super(TYPE, true, true);
			this.btn = btn;
		}
		
	}
}