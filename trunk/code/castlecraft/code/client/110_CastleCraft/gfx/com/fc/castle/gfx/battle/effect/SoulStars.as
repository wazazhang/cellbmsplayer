package com.fc.castle.gfx.battle.effect
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.CCD;
	import com.cell.util.CMath;
	import com.cell.util.Util;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.BattleUnit;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;
	
	import flash.geom.Rectangle;

	public class SoulStars extends BattleObject
	{
		private var target : BattleSoldier;
		private var timerMax : Number = 10;
		private var timer : int = 0;
		private var ccd : CCD;
		
		public function SoulStars(battle:StageBattle, target:BattleSoldier, timerMax:int)
		{
			super(battle);
			this.timerMax = timerMax;
			this.target = target;
			this.ccd = target.getSpriteInfo().body_bounds;
			this.priority = target.priority;
			this.x = target.x;
			this.y = target.y;
			
//			this.graphics.beginFill(0xffffff);
//			this.graphics.drawRect(-10, -10, 20, 20);
//			this.graphics.endFill();
		}
		
		override protected function onUpdate():void
		{
			if (timer > timerMax) {
				if (numChildren == 0 && parent != null) {
					parent.removeChild(this);
//					trace("soul stars removed");
				}
			} else {
				var child : SoulStar = new SoulStar(battle.getRes().effect_soulstar);
				child.x = Util.getRandom(ccd.X1, ccd.X2);
				child.y = Util.getRandom(ccd.Y1, ccd.Y2);
				addChild(child);
			}
			timer ++; 
		}
		
		
	}
	
}
