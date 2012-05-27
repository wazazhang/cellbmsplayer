package com.fc.castle.gfx.battle.effect
{
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteBuffer;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	
	import flash.display.BlendMode;
	import flash.events.Event;

	public class BuffEffect extends CellSprite
	{
		public var meta : CellCSprite;
		
		private var timer_max : int;
		private var timer : int;
		
		public function BuffEffect(spr:*, animName:String, sx:Number, sy:Number,
								   time:int = 0, 
								   blend:String=BlendMode.NORMAL)
		{
			if (spr is CSpriteBuffer) {
				meta = new CellCSpriteBuffer(spr.copy());
			} else {
				meta = new CellCSpriteGraphics(spr.copy());
			}
			meta.setCurrentAnimateName(animName);
			meta.setCurrentFrame(0);
			meta.x = sx;
			meta.y = sy;
			meta.blendMode = blend;
			this.addChild(meta);
			if (time < 1) {
				time = meta.getCSprite().getFrameCount(meta.getCurrentAnimate());
			}
			this.timer = 0;
			this.timer_max = time;
		}
		
		override protected function update(e:Event):void
		{
			meta.renderSelf();
			meta.nextCycFrame();
			if (timer == timer_max) {
				if (parent != null) {
					parent.removeChild(this);
				}
			}
			timer++;
		}
		
	}
}