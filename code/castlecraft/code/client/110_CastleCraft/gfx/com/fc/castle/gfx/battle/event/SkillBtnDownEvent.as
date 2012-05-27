package com.fc.castle.gfx.battle.event
{
	import com.fc.castle.gfx.battle.ui.BtnSkillLaunch;
	
	import flash.events.Event;
	
	public class SkillBtnDownEvent extends Event
	{
		
		public static const TYPE:String = "SkillBtnDownEvent";
		public var btnSkill:BtnSkillLaunch
		public function SkillBtnDownEvent(btnSkill:BtnSkillLaunch)
		{
			super(TYPE, true, true);
			this.btnSkill  = btnSkill;
		}
		
		
	}
}