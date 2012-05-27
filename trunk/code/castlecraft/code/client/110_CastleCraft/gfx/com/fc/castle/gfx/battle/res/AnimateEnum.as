package com.fc.castle.gfx.battle.res
{
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.fc.castle.gfx.battle.BattleMissle;
	import com.fc.castle.gfx.battle.BattleSoldier;
	
	import flash.display.DisplayObject;

	public class AnimateEnum
	{
		public static const ACTOR_STAND 	: String = "std_r";
		public static const ACTOR_WALK 		: String = "wlk_r";
		public static const ACTOR_ATTACK 	: String = "atk_r";
		public static const ACTOR_DEAD 		: String = "std_r";
		
		public static function setActorAnimate(bs:CellCSprite, anim:String) : void
		{
			bs.setCurrentAnimateName(anim);
			bs.setCurrentFrame(0, true);
		}
		
		public static function setUnitDirect(o:DisplayObject, dx:Number, dy:Number=0) : void
		{
			if (dx > 0) {
				o.scaleX = -1;
			} else {
				o.scaleX = 1;
			}
		}
		
		
		public static const MISSLE_RUN	 	: String = "run";
		public static const MISSLE_EXP	 	: String = "exp";
		public static const MISSLE_TRACK 	: String = "trk";
		public static const MISSLE_HURT		: String = "hrt";
		
		public static function setMissleAnimate(eff:CellCSprite, anim:String) : void
		{
			eff.setCurrentAnimateName(anim);
			eff.setCurrentFrame(0, true);
		}

		
		public static const SLASH_RUN		: String = "sla";
		public static const SLASH_HURT		: String = "hrt";
		
		public static function setSlashAnimate(eff:CellCSprite, anim:String) : void
		{
			eff.setCurrentAnimateName(anim);
			eff.setCurrentFrame(0, true);
		}
		
		public static const SKILL_RUN	 	: String = "run";
		public static const SKILL_BEGIN	 	: String = "beg";
		public static const SKILL_END	 	: String = "end";
		public static const SKILL_BODY	 	: String = "body";

		public static function setSkillAnimate(eff:CellCSprite, anim:String) : void
		{
			eff.setCurrentAnimateName(anim);
			eff.setCurrentFrame(0, true);
		}
	}
}