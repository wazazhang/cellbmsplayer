package com.fc.castle.gfx.battle.skill
{
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.EffectInfo;
	import com.fc.castle.gfx.battle.res.SkillBehavior;

	public class SpriteBodySkill extends SkillBehavior
	{
		private var meta		: CellCSprite;
		
		private var effect_info : EffectInfo;
		
		
		override public function onInit() : void
		{
			this.effect_info = battle.getRes().getEffectInfo(skill.skillTemplate.spriteEffect);
			
			this.meta = new CellCSpriteGraphics(battle.getRes().getEffectSprite(skill.skillTemplate.spriteEffect));
			
			if (effect_info.blend!=null) {
				meta.blendMode = effect_info.blend;
			}
			
			AnimateEnum.setSkillAnimate(meta, AnimateEnum.SKILL_RUN);
			
			skill.addChild(meta);
		}
		
		override public function onUpdate() : void
		{
			meta.renderSelf();
			
			if (effect_info.hangOver == skill.timer) {
				skill.doSkill();
				for each (var o:BattleSoldier in skill.targets) {
					battle.getWorld().spawnBodyEffect(
						meta.getCSprite(), 
						o, 
						AnimateEnum.SKILL_BODY,
						0,
						meta.blendMode);
				}
			}
			
			if (meta.nextFrame() == 0) {
				skill.close();
			}
		}
		
	
		
	}
	

}