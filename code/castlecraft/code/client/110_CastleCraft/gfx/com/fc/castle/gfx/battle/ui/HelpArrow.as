package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.events.Event;

	public class HelpArrow extends CellSprite
	{
		
		private var arrowY:Number
		private var timer:int;
		private var image:Bitmap = new Res.ui_arrow() as Bitmap;
		
		public function HelpArrow()
		{
			super();
			addChild(image);
		}
		
		override protected function update(e:Event):void
		{
			super.update(e);
			timer ++;
			super.y = arrowY - height - Math.sin(timer * 18 % 180  /180 * Math.PI ) * 20;	
		}
		
		override public function set y(val:Number):void
		{
			arrowY = val;
		}
		
		override public function get y():Number
		{
			return arrowY;
		}
	}
}