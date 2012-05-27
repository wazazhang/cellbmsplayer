package com.fc.castle.gfx.battle.res
{
	import com.cell.gfx.game.CCD;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	
	import flash.geom.Point;

	public class UnitInfo
	{
		/**被导弹攻击的点*/
		public var missle_body_offset	: Point;
		/**导弹发射的起始点*/
		public var missle_lanch_rect	: CCD;
		
		/**攻击序列中的第几帧做伤害或发射判断事件，如果攻击速度过快，则以结束攻击动作时直接判断*/
		public var attack_check_frame 	: int;
		
		public var body_bounds 			: CCD;
		
		public function UnitInfo(spr:CSprite, name:String)
		{
			// 计算被导弹命中的点
			body_bounds = spr.getFrameCD(0, 0, CSprite.CD_TYPE_MAP, 0);
			if (body_bounds != null) {
				missle_body_offset = new Point(
					0, body_bounds.Y1+body_bounds.getHeight()/2
				);
			} else {
				trace("精灵["+name+"]没有指定被攻击判定！");
				body_bounds = CCD.createCDRect(0, -10, -spr.getVisibleHeight(), 20, spr.getVisibleHeight());
				missle_body_offset = new Point(0, -spr.getVisibleHeight()/2);
			}
			
			// 计算何时发射导弹或近战攻击目标运算
			var attack_anim : int = spr.findAnimateIndex(AnimateEnum.ACTOR_ATTACK);
			
			this.attack_check_frame = spr.getFrameCount(attack_anim) - 1;

			for (var af:int = spr.getFrameCount(attack_anim)-1; af>=0; af--) {
				if (spr.getFrameCDCount(attack_anim, af, CSprite.CD_TYPE_MAP)>0) {
					attack_check_frame = af;
					var lanch_cd : CCD = spr.getFrameCD(attack_anim, af, CSprite.CD_TYPE_MAP, 0);
					missle_lanch_rect = lanch_cd.clone();
					break;
				}
			}
			if (missle_lanch_rect == null) {
				missle_lanch_rect = CCD.createCDRect2Point(0, -8, -spr.getVisibleHeight()/4, 8, 0);
			}
		}
	}
}