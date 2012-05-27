package com.fc.castle.gfx.world
{
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.fc.castle.data.PlayerFriend;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.MouseEvent;

	public class WorldUnitBase extends WorldUnit
	{
		private var player : PlayerFriend;
		
		public function WorldUnitBase(ws:WorldScene, wd:SpriteObject, pf:PlayerFriend, csprite:CSpriteBuffer)
		{
			super(ws, wd, csprite);
			
			this.player = pf;
			
			if (pf.playerID != Screens.client.getPlayerID()) {
				setBottomText(LanguageManager.getText("scene.owenscene", pf.playerName));
			} else {
				setBottomText(LanguageManager.getText("scene.owenscene", pf.playerName));
			}
			
			this.mouseEnabled = true;
			
			addEventListener(MouseEvent.MOUSE_DOWN, downHandle);
			addEventListener(MouseEvent.CLICK,clickHandle);
		}
		
		private var mouse_down_x;
		private var mouse_down_y;
		
		private function downHandle(event:MouseEvent):void
		{
			mouse_down_x = event.stageX;
			mouse_down_y = event.stageY;
		}
		
		private function clickHandle(event:MouseEvent):void
		{
			// 进入城内
			if (this.scene.isInside()){
				var stage:StageWorld = (StageWorld)(this.scene.parent);
				stage.goWorld();
			}else{
				var stage:StageWorld = (StageWorld)(this.scene.parent);
				stage.goHome();
			}
		}
		
	}
}