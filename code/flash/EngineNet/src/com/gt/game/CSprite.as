package com.gt.game
{
	import com.gt.loader.ResourceLoader;
	import com.gt.loader.ResourceLoaderListener;
	
	import flash.display.DisplayObject;
	import flash.display.Graphics;
	import flash.utils.ByteArray;
	
	public class CSprite extends ALayer implements ResourceLoaderListener
	{
		public function CSprite()
		{
			var loader : ResourceLoader = new ResourceLoader("data/swf/test/test.swf", this);
		}

		public function getParent() : ALayer 
		{
			return parent as ALayer;
		}
		
		override public function update() : void 
		{
			x = parent.mouseX;
			y = parent.mouseY;
		}
		
		override public function render(g:Graphics) : void
		{
			if (IsDebugMode) 
			{
				g.beginFill(0xFFCC00, 1);
				g.drawRect(0, 0, width, height);
				g.endFill();
			}
		}
			
		public function response(url:String, content:Object, result:int) : void
		{
			if (content is DisplayObject)
			{
				var anim : DisplayObject = content as DisplayObject;
				
				addChild(anim);
				
			}
			
			if (content is ByteArray)
			{
				
			}
		}

	}
}