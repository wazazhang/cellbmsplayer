package com.fc.castle.gfx.world
{
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.MapObject;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.ICamera;
	import com.cell.gfx.game.worldcraft.CellMapCamera;
	import com.cell.gfx.game.worldcraft.CellMapWorld;
	import com.cell.gfx.game.worldcraft.CellUnit;
	import com.cell.gfx.game.worldcraft.CellWorld;
	import com.cell.gfx.game.worldcraft.CellWorldCamera;
	import com.cell.ui.component.Alert;
	import com.cell.util.Map;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.ExploreData;
	import com.fc.castle.data.message.Messages.GetExploreDataListRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.screens.Screens;
	
	import flash.geom.Rectangle;
	
	import flashx.textLayout.events.DamageEvent;

	public class WorldScene extends CellWorld
	{
		protected var res : ResourceLoader;
		
		protected var sceneData : WorldSet;
		
		protected var mapData : MapObject;
		
		protected var response : GetExploreDataListResponse;
		
		private var base : WorldUnitBase ;
		
		private var sprMap:Map = new Map();
		
		private var is_inside:Boolean;
		
		/**
		 * 可能是自己也可能是别的玩家
		 */
		public function WorldScene(viewW:int, viewH:int, res:ResourceLoader, response:GetExploreDataListResponse, isInside:Boolean)
		{
			trace(
			"------------------------------------------------------------------------------\n" +
			"- " + response.targetPlayerHomeScene + "\n" +
			"------------------------------------------------------------------------------");
			this.res = res;
			this.response  = response;
			if (isInside){
//				this.sceneData = res.getSetWorld(
//					StringUtil.splitString(response.targetPlayerHomeScene, "/")[1]);
				this.sceneData = res.getSetWorld("home");
			}else{
				this.sceneData = res.getSetWorld("world");
			}
			is_inside = isInside;
			
			for each(var spr:SpriteObject in sceneData.Sprs)
			{
				sprMap.put(spr.UnitName,spr);
			}
			
			for each (var md : MapObject in sceneData.Maps) 
			{
				mapData = md;
				break;
			}
			
			super(viewW, viewH);
//			super(viewW, viewH);
			
			this.mouseChildren = true;
			this.mouseEnabled = true;
			
			for each (var ud : SpriteObject in sceneData.Sprs) {
				var ws : CellUnit = createStaticUnit(ud);
				if (ws != null) {
					addChild(ws);
				}
			}
			for each (var ed:ExploreData in response.explores)
			{
				if (ed!=null){
					var cs:SpriteObject = sprMap.get(ed.UnitName); 
					if (cs!=null) {
						var wu : CellUnit = createDynamicUnit(cs, ed);
						if (wu != null) {
							addChild(wu);
						}	
					}
				}
			}	
			
			if (this.base != null) {
				this.locateCameraCenter(base.x, base.y);
			}
			
		}
		
		override protected function createCamera(w:int, h:int) : ICamera
		{
			return new CellWorldCamera(w, h, new Rectangle(0, 0, sceneData.Width, sceneData.Height));
		}
		
	   	public function getPlayerID() : int
		{
			return response.targetPlayer.playerID;
		}
		
		public function isInside():Boolean
		{
			return is_inside;
		}
		
//		--------------------------------------------------------------------------------------------------------
		
		protected function createStaticUnit(ud : SpriteObject) : CellUnit
		{
			if (WorldUnit.isStaticUnit(ud)) {
				if (ud.UnitName == "bg") {
					return new WorldUnit(this, ud, res.getSpriteBuffer(ud.SprID));
				} 
				else if (ud.SprID == "base") {
					this.base = new WorldUnitBase(this, ud, response.targetPlayer, res.getSpriteBuffer(ud.SprID));
					return base;
				} 
				else {
					return new WorldUnit(this, ud, res.getSpriteBuffer(ud.SprID));
				}
			}	
			
			return null;
		}
		
		protected function createDynamicUnit(ud : SpriteObject, ed : ExploreData) : CellUnit
		{
			return new WorldUnitExplore(this, ud, res.getSpriteBuffer(ud.SprID), ed);
		}
		
		public function getWorldUnit(unit_name:String):WorldUnit
		{
			for each (var wu:WorldUnit in this.units){
				if (wu!=null && wu.getUnitName() == unit_name){
					return wu;
				}
			}
			return null;
		}
		
//		override protected function createCamera(w:int, h:int) : ICamera
//		{
//			return new CellMapCamera(new CMapView(res.getMap(mapData.MapID), w, h), w, h, new Rectangle(0, 0, sceneData.Width, sceneData.Height));
//		}
		

	}
}