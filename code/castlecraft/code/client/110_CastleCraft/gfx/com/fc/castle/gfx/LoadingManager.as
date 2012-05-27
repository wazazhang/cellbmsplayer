package com.fc.castle.gfx
{
	import com.fc.castle.screens.Screens;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.display.Sprite;

	public class LoadingManager
	{
		private static var loading:Loading;
		
		public function LoadingManager()
		{
			
		}
		
		public static function showRoot():void
		{
			show(Screens.getRoot());
		}
		
		public static function show(parent:DisplayObjectContainer):void
		{
			if (loading == null) {
				loading = new Loading();
				parent.addChild(loading);
			}
		}
		
		public static function close():void
		{
			if (loading!=null) {
				loading.removeFromParent();
				loading = null;
			}
		}
		
	}
}