package com.fc.castle.gfx
{
	import com.cell.gfx.CellSprite;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.events.Event;

	public class Loading extends CellSprite
	{
		private var image:Bitmap = new Res.ui_loading() as Bitmap
		public function Loading()
		{
			this.graphics.beginFill(0x000000,0.5);
			this.addChild(image);
			addEventListener(Event.ADDED_TO_STAGE,setCenter);
			this.mouseChildren = false;
			this.mouseEnabled = true;
		}
		
		private function setCenter(event:Event):void
		{
			var width:Number = parent.width;
			var height:Number = parent.height;
			
			this.graphics.drawRect(0,0,width,height);
			this.width = width;	
			this.height = height;
			
			image.x = (width - image.width)*0.5;
			image.y = (height - image.height)*0.5;
		}
	}
}