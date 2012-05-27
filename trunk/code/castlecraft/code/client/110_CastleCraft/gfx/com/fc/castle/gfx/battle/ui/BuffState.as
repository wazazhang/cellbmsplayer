package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.io.UrlManager;
	import com.cell.ui.ImageBox;
	import com.cell.util.ImageUtil;
	import com.cell.util.Map;
	import com.cell.util.Util;
	import com.fc.castle.data.template.BuffTemplate;
	import com.fc.castle.res.CResourceManager;
	
	import flash.display.Bitmap;
	import flash.display.DisplayObject;
	import flash.display.Loader;
	import flash.events.Event;

	public class BuffState extends CellSprite
	{
		private var buffs : Map = new Map();
		
		public function BuffState()
		{
		}
		
		public function addBuff(data:BuffTemplate) : void
		{
			if (!buffs.contains(data.type)) 
			{
				var bt : Bitmap = CResourceManager.getIcon(data.icon);
				bt.scaleX = 12 / bt.width;
				bt.scaleY = 12 / bt.height;
				addChild(bt);
				buffs.put(data.type, bt);
				reset();
			}
		}
		
		public function removeBuff(type:int) : void
		{
			var hd : DisplayObject = buffs.remove(type);
			if (hd != null) {
				removeChild(hd);
			}
		}
		
		
		private function reset() : void
		{
			var sx : int = - buffs.size() * 12 / 2;
			for each (var o:DisplayObject in buffs) {
				o.x = sx;
				o.y = -12;
				sx += 12;
			}
		}
	}
}