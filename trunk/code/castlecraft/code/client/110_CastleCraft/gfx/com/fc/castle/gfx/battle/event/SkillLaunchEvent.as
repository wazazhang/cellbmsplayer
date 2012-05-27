package com.fc.castle.gfx.battle.event
{
	import com.fc.castle.gfx.battle.BattleSkill;
	
	import flash.events.Event;
	
	public class SkillLaunchEvent extends Event
	{
		public static const TYPE:String = "SkillLaunchEvent";
		
		public var skill : BattleSkill
		public function SkillLaunchEvent(skill : BattleSkill)
		{
			super(TYPE, true, true);
			this.skill = skill;
		}
	}
}