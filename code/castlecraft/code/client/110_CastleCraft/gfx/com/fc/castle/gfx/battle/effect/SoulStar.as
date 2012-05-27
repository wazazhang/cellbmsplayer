package com.fc.castle.gfx.battle.effect
{
	import com.cell.gfx.CellSprite;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.events.Event;

	public class SoulStar extends CellSprite
	{
		private var timerMax : int = 10;
		private var timer : Number = 0;
		
		public function SoulStar(data:BitmapData)
		{
			var bitmap : Bitmap = new Bitmap(data);
			bitmap.x = -data.width  / 2;
			bitmap.y = -data.height / 2;
			addChild(bitmap);
//			this.blendMode = BlendMode.SCREEN;
		}
		
		override protected function update(e:Event):void
		{
			if (timer < timerMax) {
				this.alpha = 1 - timer / timerMax;
				this.y -= 1;
			} else {
				if (parent != null) {
					parent.removeChild(this);
				}
			}
			timer ++;
		}
		
	
	}
}