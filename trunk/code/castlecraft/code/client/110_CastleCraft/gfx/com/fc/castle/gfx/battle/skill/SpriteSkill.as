package com.fc.castle.gfx.battle.skill
{
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.formual.Formual;
	import com.fc.castle.gfx.battle.BattleSkill;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.EffectInfo;
	import com.fc.castle.gfx.battle.res.SkillBehavior;

	public class SpriteSkill extends SkillBehavior
	{
		private var meta		: CellCSprite;
		
		private var effect_info : EffectInfo;
		
		private static const STATE_BEGIN		: int = 0x00;
		private static const STATE_RUNNING 		: int = 0x01;
		private static const STATE_END			: int = 0x02;
		private var state : int;
		
		override public function onInit() : void
		{
			this.effect_info = battle.getRes().getEffectInfo(skill.skillTemplate.spriteEffect);
			this.meta = new CellCSpriteGraphics(battle.getRes().getEffectSprite(skill.skillTemplate.spriteEffect));
		
			if (effect_info.blend!=null) {
				meta.blendMode = effect_info.blend;
			}
			
			if (meta.getCSprite().findAnimateIndex(AnimateEnum.SKILL_BEGIN) >= 0) {
				AnimateEnum.setSkillAnimate(meta, AnimateEnum.SKILL_BEGIN);
				this.state = STATE_BEGIN;
			} else {
				AnimateEnum.setSkillAnimate(meta, AnimateEnum.SKILL_RUN);
				this.state = STATE_RUNNING;
			}	
			
			skill.addChild(meta);
		}
		
		override public function onUpdate() : void
		{
			meta.renderSelf();
			
			if (effect_info.hangOver == skill.timer) {
				skill.doSkill();
				battle.getWorld().startEarthQuake(5, 4);
			}
			
			switch(state)
			{
				case STATE_BEGIN:
					if (meta.nextFrame() == 0) {
						AnimateEnum.setSkillAnimate(meta, AnimateEnum.SKILL_RUN);
						state = STATE_RUNNING;
					}
					break;
				case STATE_RUNNING:
					if (meta.nextFrame() == 0) {
						AnimateEnum.setSkillAnimate(meta, AnimateEnum.SKILL_END);
						state = STATE_END;
					}
					break;
				case STATE_END:
					if (meta.nextFrame() == 0) {
						skill.close();
					}
					break;
			}
			
		}
		

	}
	
}