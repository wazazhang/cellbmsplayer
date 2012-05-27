package com.fc.castle.gfx.battle
{
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gameedit.object.MapSet;
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.MapObject;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.gameedit.object.worldset.SpriteObject;
	import com.cell.gfx.game.CCD;
	import com.cell.gfx.game.CMap;
	import com.cell.gfx.game.CMapView;
	import com.cell.gfx.game.CSpriteBuffer;
	import com.cell.gfx.game.ICamera;
	import com.cell.gfx.game.worldcraft.CellMapCamera;
	import com.cell.gfx.game.worldcraft.CellMapWorld;
	import com.cell.gfx.game.worldcraft.CellUnit;
	import com.cell.gfx.game.worldcraft.CellWorld;
	import com.cell.gfx.game.worldcraft.CellWorldCamera;
	import com.cell.util.Arrays;
	import com.cell.util.CMath;
	import com.cell.util.Map;
	import com.cell.util.Util;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.effect.BuffEffect;
	import com.fc.castle.gfx.battle.effect.EffectNumber;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.gfx.battle.res.CBattleResource;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.Res;
	import com.fc.castlecraft.Config;
	
	import flash.display.BlendMode;
	import flash.display.DisplayObject;
	import flash.geom.Rectangle;

	public class BattleWorld extends CellWorld
	{
		public static const LAYER_MAP		: int = -30000;
		public static const LAYER_MAP_HI	: int = -20000;
		public static const LAYER_GROUND	: int = -10000;
		public static const LAYER_LAND		: int = 0;
		public static const LAYER_SKY		: int = 10000;
		
		public static var CELLH			: int;
		
		private var _map 				: CMap ;
		private var _map_view 			: BattleMap;
		
		internal var battle				: StageBattle;
		internal var res_world_data 	: WorldSet;
		internal var res_map_data		: MapObject;
		
		/**[gridx][gridy] = BattleBuilding*/
		internal var map_builded		: Array;
		/**[force] = BattleSoldier[gridy][index]*/
		internal var map_soldier_track 	: Map;
		
		/**[force] = ForcePlayer*/
		internal var force_player		: Map;
		
		private var _earth_quake_time	: int;
		private var _earth_quake_level	: int;
		
		
		public function BattleWorld(battle:StageBattle, 
									viewWidth:int, 
									viewHeight:int, 
									battleRes:CBattleResource)
		{
			trace("Init BattleWorld Map: " + battle.getData().cmap_id);
			
			// init data
			this.battle 		= battle;
			this.res_world_data	= battleRes.getBattleSceneSet();
			this.res_map_data	= res_world_data.Maps.values()[0]
			this.map_builded	= Arrays.newArray2D(res_world_data.GridXCount, res_world_data.GridYCount);
			
			CELLH = res_world_data.GridH;
			
			// init map
			this._map = battleRes.getResMap(res_map_data);
			this._map_view = new BattleMap(_map, viewWidth, viewHeight);
			super(viewWidth, viewHeight);
			addChild(_map_view);
		}
		
		public function init(players:Vector.<ForcePlayer>) : void
		{
			// init map object
			for each (var ws : SpriteObject in res_world_data.Sprs) {
				var bto : BattleMapObject = BattleMapObject.craeteMapObject(battle, ws);
				addChild(bto);
			}
			
			// init player data
			this.force_player = new Map();
			for each (var fp : ForcePlayer in players) {
				fp.battleInit(battle, res_world_data);
				force_player.put(fp.force, fp);
			}
			
			// init soldier data
			this.map_soldier_track = new Map();
			for each (var p : ForcePlayer in players) {
				map_soldier_track[p.force] = Arrays.newArray2D(res_world_data.GridYCount, 0);
			}
		}
		
//		override protected function createCamera(w:int, h:int) : ICamera
//		{
//			return new CellWorldCamera(w, h, new Rectangle(0, 0, res_world_data.Width, res_world_data.Height));
//		}
//		
		override protected function createCamera(w:int, h:int) : ICamera
		{
			return new CellMapCamera(_map_view, w, h, new Rectangle(0, 0, res_world_data.Width, res_world_data.Height));
		}

		
		override public function render() : void
		{					
			if (_earth_quake_time > 0) {
				_earth_quake_time --;
				var time : Number = _earth_quake_time * CMath.PI_2;
				this.camera.addVectorY(Math.sin(time) * _earth_quake_level);
			}
			_map_view.render();
			_map_view.x = _map_view.getCamera().getX();
			_map_view.y = _map_view.getCamera().getY();
			super.render();
			
		}
		
		override protected function onUpdate():void
		{
			if (timer % battle.getData().ap_upgrade_time == 0) {
				for each (var p : ForcePlayer in force_player) {
					if (!p.isLockAP()){
						p.addAP(battle.getData().ap_upgrade_count);
					}
				}
			}
			for each (var p : ForcePlayer in force_player) {
				p.battleUpdate();
			}
		}
		
		public function getCellW() : int
		{
			return res_world_data.GridW;
		}
		public function getCellH() : int
		{
			return res_world_data.GridH;
		}
		
		public function getGridXCount() : int
		{
			return res_world_data.GridXCount;
		}
		public function getGridYCount() : int
		{
			return res_world_data.GridYCount;
		}
		

		public function getWorldWidth() : int
		{
			return res_world_data.Width;
		}
		public function getWorldHeight() : int
		{
			return res_world_data.Height;
		}
		
		public function sortUnits() : void
		{
			Util.sortChildsPop(this, compareUnit);
		}
		
		private function compareUnit(a:*, b:*) : int
		{
			return (a.y + a.priority) - (b.y + b.priority);
//			return a.y - b.y;
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		
//		-------------------------------------------------------------------------------------------------------------------------
		
		/**得到己方主基地*/
		public function getForcePlayer(force:int) : ForcePlayer
		{
			return force_player[force];
		}
		
		/**得到非己方主基地*/
		public function getEnemyForce(force:int) : ForcePlayer
		{
			for each (var rg : ForcePlayer in force_player) {
				if (rg.force != force) {
					return rg;
				}
			}
			return null;
		}
		
		
//		-------------------------------------------------------------------------------------------------------------------------
//		Effect
//		-------------------------------------------------------------------------------------------------------------------------

		public function startEarthQuake(time:int, level:int) : void
		{
			this._earth_quake_time = time;
			this._earth_quake_level = level;
		}
		
//		public function spawnEffect(spr:CSpriteBuffer, x:Number, y:Number, anim:String, direct:Number=0, priority:int=0) : BattleEffect
//		{
//			var hurt : BattleEffect = new BattleEffect(
//				battle, spr, 
//				x, y, anim, direct, priority);
//			this.addChild(hurt);
//			return hurt;
//		}
		
		public function spawnBodyEffect(spr:*, target:BattleUnit, anim:String,
										time:int = 0, 
										blend:String=BlendMode.NORMAL) : BuffEffect
		{
			var ret : BuffEffect = new BuffEffect(spr, anim,
				0, 
				target.getCSprite().getVisibleYCenter(),
				time, blend);
			target.addChild(ret);
			return ret;
			
		}
		
		public function spawnAttackNumber(value:Number, x:Number, y:Number, critical:Boolean=false) : void
		{
			if (Config.ENABLE_SPAWN_TEXT) {
				value = Math.abs(value);
				var vval : Number = Math.floor(value);
				if (vval == 0) {
					vval = 1;
				}
				var eft : EffectNumber = new EffectNumber(battle, battle.getRes().text_number_y, vval, critical);
				eft.x = x;
				eft.y = y;
				addChild(eft);
			}
		}
		
		public function spawnRecoverNumber(value:Number, x:Number, y:Number, critical:Boolean=false) : void
		{
			if (Config.ENABLE_SPAWN_TEXT) {
				value = Math.abs(value);
				var vval : Number = Math.floor(value);
				if (vval == 0) {
					vval = 1;
				}
				var eft : EffectNumber = new EffectNumber(battle, battle.getRes().text_number_g, vval, critical);
				eft.x = x;
				eft.y = y;
				addChild(eft);
			}
		}
		
//		-------------------------------------------------------------------------------------------------------------------------
//		MapBlock
//		-------------------------------------------------------------------------------------------------------------------------

		/**格子上是否已经建造*/
		public function isBuilded(gridX:int, gridY:int) : Boolean
		{
			return map_builded[gridX][gridY] != null;
		}
		/**得到某格子的建筑*/
		public function getBuilding(gridX:int, gridY:int) : BattleBuilding
		{
			return map_builded[gridX][gridY];
		}
		/**测试是否可以建造*/
		public function isCanBuild(worldx:int, worldy:int, player:ForcePlayer) : Boolean
		{
			if (player.region_bounds.contains(worldx, worldy)) {
				var gx:int = worldx/getCellW();
				var gy:int = worldy/getCellH();
				var mask:int = res_world_data.getTerrainCell(gx, gy);
				return (mask & 0x0000ff)!=0 && !isBuilded(gx, gy);
			}
			return false;
		}
		
		// 对于已添加的单位予以处理
		override protected function onAddedUnit(u:CellUnit):void
		{
			if (u is BattleBuilding) 
			{
				var bd : BattleBuilding = u as BattleBuilding;
				map_builded[bd.trackX][bd.trackY] = u;
				sortUnits();
				for each (var player:ForcePlayer in force_player )
				{
					player.onBuildBuilding(bd);
				}
				battle.addBattleBuildingEvent(bd);
				trace("Added Building : force=" + bd.force + ", type=" + bd.getUnitTemplate().name);
			}
			else if (u is BattleSoldier) 
			{
				var bs : BattleSoldier = u as BattleSoldier;
				map_soldier_track[bs.force][bs.trackY].push(bs);
				sortUnits();
				battle.addBattleSoldierEvent(bs);
//				trace("Added Soldier : force=" + bs.force + ", type=" + bs.getUnitTemplate().name);
			}
			else if (u is BattleMissle) {
				sortUnits();
			}
			else if (u is BattleSkill) {
				sortUnits();
				battle.addBattleSkillEvent(u as BattleSkill);
			}
		}
		
		override protected function onRemovedUnit(u:CellUnit):void
		{
			if (u is BattleBuilding)
			{
				var bd : BattleBuilding = u as BattleBuilding;
				map_builded[bd.trackX][bd.trackY] = null;
				for each (var player:ForcePlayer in force_player )
				{
					player.onRemoveBuilding(bd);
				}
				battle.addUnBattleBuildingEvent(bd);
				trace("Removed Building : force=" + bd.force + ", type=" + bd.getUnitTemplate().name);
			}
			else if (u is BattleSoldier)
			{
				var bs : BattleSoldier = u as BattleSoldier;
				Arrays.arrayRemove(map_soldier_track[bs.force][bs.trackY], bs);
//				trace("Removed Soldier : force=" + bs.force + ", type=" + bs.getUnitTemplate().name);
			}
		}
		

//		
//		public function removeBuilding(bd:BattleBuilding) : void 
//		{
//			if (removeChild(bd) != null) {
//				
//			}
//		}
//		
		
		public function addSoldier(player:ForcePlayer, sd:SoldierData, worldX:int, worldY:int) : BattleSoldier
		{
			var soldier : BattleSoldier = new BattleSoldier(battle, sd, player, worldX, worldY);
			addChild(soldier);
			return soldier;
		}
		
//		-------------------------------------------------------------------------------------------------------------------------
	
		/**得到某方指定轨道上的所有士兵*/
		public function getTrackSoldiers(force:int, trackY:int) : Array
		{
			return map_soldier_track[force][trackY];
		}
		
		public function getTrackSoldierCount(force:int, trackY:int) : int
		{
			var array : Array = map_soldier_track[force][trackY];
			return array.length;
		}
		
		/**得到非己方方指定轨道上的所有士兵*/
		public function getTrackEnemy(force:int, trackY:int) : Array
		{
			var fp : ForcePlayer = getEnemyForce(force);
			return map_soldier_track[fp.force][trackY];
		}
		
		
		public function pickForceRandomSoldier(force:int) : BattleSoldier
		{
			var ret : Array = new Array();
			for (var i:int=numChildren-1; i>=0; --i) {
				var c : DisplayObject = getChildAt(i);
				if (c is BattleSoldier) {
					var s : BattleSoldier = c as BattleSoldier;
					if (s.force == force) {
						ret.push(s);
					}
				}
			}
			return Arrays.getRandom(ret)
		}
		
		
		
		
		public function pickSoldier(worldX:int, worldY:int) : BattleSoldier
		{
			sortUnits();
			for (var i:int=numChildren-1; i>=0; --i) {
				var c : DisplayObject = getChildAt(i);
				if (c is BattleSoldier) {
					var s : BattleSoldier = c as BattleSoldier;
					var cd : CCD = s.getBody();
					if (CMath.includeRectPoint(
						s.x + cd.X1,
						s.y + cd.Y1,
						s.x + cd.X2,
						s.y + cd.Y2,
						worldX, worldY
					)) {
						return s;
					}
				}
			}
			return null;
		}
		
		
		public function pickForceSoldier(force:int, worldX:int, worldY:int) : BattleSoldier
		{
			sortUnits();
			for (var i:int=numChildren-1; i>=0; --i) {
				var c : DisplayObject = getChildAt(i);
				if (c is BattleSoldier) {
					var s : BattleSoldier = c as BattleSoldier;
					var cd : CCD = s.getBody();
					if (s.force == force && CMath.includeRectPoint(
						s.x + cd.X1,
						s.y + cd.Y1,
						s.x + cd.X2,
						s.y + cd.Y2,
						worldX, worldY
					)) {
						return s;
					}
				}
			}
			return null;
		}
		
		public function pickSoldiers(worldX:int, worldY:int, radius:Number, limitCount:int) : Array
		{
			sortUnits();
			var pr : Number = radius * radius;
			var pw : Number;
			var ph : Number;
			var ret : Array = new Array();
			for (var i:int=numChildren-1; i>=0; --i) {
				if (ret.length < limitCount) {
					var c : DisplayObject = getChildAt(i);
					if (c is BattleSoldier) {
						pw = worldX - c.x;
						ph = worldY - c.y;
						if (pw*pw + ph*ph <= pr) {
							ret.push(c);
						}
					}
				} else {
					return ret;
				}
			}
			return ret;
		}
		
		public function pickForceSoldiers(force:int, worldX:int, worldY:int, radius:Number, limitCount:int) : Array
		{
			sortUnits();
			var pr : Number = radius * radius;
			var pw : Number;
			var ph : Number;
			var ret : Array = new Array();
			for (var i:int=numChildren-1; i>=0; --i) {
				if (ret.length < limitCount) {
					var c : DisplayObject = getChildAt(i);
					if (c is BattleSoldier) {
						var s : BattleSoldier = c as BattleSoldier;
						if (s.force == force) {
							pw = worldX - s.x;
							ph = worldY - s.y;
							if (pw*pw + ph*ph <= pr) {
								ret.push(c);
							}
						}
					}
				} else {
					return ret;
				}
			}
			return ret;
		}

//		-------------------------------------------------------------------------------------------------------------------------

	}
}