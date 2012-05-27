package com.fc.castle.gfx.battle.res
{
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.gfx.battle.BattleSkill;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.skill.SpriteSkill;
	
	import flash.utils.getDefinitionByName;
	
	import org.as3commons.lang.ClassUtils;
	
	
	
	public class SkillBehavior
	{
		protected var skill 	: BattleSkill;
		protected var battle	: StageBattle;
		
		final public function init(bs:BattleSkill) : void
		{
			this.battle = bs.battle;
			this.skill = bs;	
		}
		
		public function onInit() : void
		{
			
		}
		
		public function onUpdate() : void
		{
			
		}
		
		public static function loadClasses() : void
		{
			ClassUtils.getName(com.fc.castle.gfx.battle.skill.SpriteSkill);
			ClassUtils.getName(com.fc.castle.gfx.battle.skill.SpriteBodySkill);
		}
		
		public static function createBehavior(name:String) : SkillBehavior
		{
			trace("Create Skill Behavior : " + name);
			var behaviorType : Class = ClassUtils.forName("com.fc.castle.gfx.battle.skill."+name) as Class;
			return ClassUtils.newInstance(behaviorType);;
		}
	}
}