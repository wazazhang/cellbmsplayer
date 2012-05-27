package com.fc.castle.gfx.battle.effect
{
	import com.cell.gfx.CellSprite;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.gfx.battle.BattleObject;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.display.Sprite;
	import flash.events.Event;

	public class Transport extends BattleObject
	{
		private var rot : Sprite;
		
		public function Transport(battle:StageBattle, data:BitmapData, blend:String=BlendMode.SCREEN)
		{
			super(battle);
			this.priority = BattleWorld.LAYER_GROUND;
			
			var bitmap : Bitmap = new Bitmap(data);
			bitmap.x = - data.width/2;
			bitmap.y = - data.height/2;
			
			rot = new Sprite();
			rot.addChild(bitmap);
			this.blendMode = blend;
			
			this.scaleY = 0.5;
			this.addChild(rot);
		}
		
		override protected function onUpdate():void
		{
			rot.rotation += 1;
			
		}
		
		
	}
}