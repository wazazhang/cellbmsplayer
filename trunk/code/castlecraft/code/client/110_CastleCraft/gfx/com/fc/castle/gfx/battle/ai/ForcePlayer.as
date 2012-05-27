package com.fc.castle.gfx.battle.ai
{
	import com.cell.gameedit.object.WorldSet;
	import com.cell.gameedit.object.worldset.RegionObject;
	import com.cell.util.Map;
	import com.fc.castle.data.BattlePlayer;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.SkillTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.formual.FormualObject;
	import com.fc.castle.formual.FormualPlayer;
	import com.fc.castle.gfx.battle.BattleBuilding;
	import com.fc.castle.gfx.battle.BattleMapHoly;
	import com.fc.castle.gfx.battle.BattleMapObject;
	import com.fc.castle.gfx.battle.BattleSkill;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.BattleWorld;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.event.APTargetComplateEvent;
	import com.fc.castle.gfx.battle.event.BuildBuildingEvent;
	import com.fc.castle.gfx.battle.event.SkillLaunchEvent;
	import com.fc.castle.net.client.DataManager;
	
	import flash.events.EventDispatcher;
	import flash.geom.Rectangle;

	public class ForcePlayer extends EventDispatcher implements FormualPlayer
	{
		public static const FORCE_A	: int = 0;
		public static const FORCE_B	: int = 1;
		
		private var _force			: int;
		private var _force_enemy	: int;
		private var _battle		: StageBattle;
		private var _data 			: BattlePlayer;
	
		protected var _hp 			:  int;
		protected var _ap 			:  int;
		protected var _mp 			:  int;
		
		private var _region_data 	: Map;
		private var _region_rect 	: Rectangle;
		private var _holy_obj		: BattleMapHoly;

		private var _skillCoolDown	: Map;
		
		internal var maxHp:int;
		
		private var is_lock_ap:Boolean = false;
//		------------------------------------------------------------------------------------
		protected var canUserRoard:Array = new Array();
		
		public function ForcePlayer(data : BattlePlayer, force : int)
		{
			this._force = force;
			this._data 	= data;
			this._hp	= data.hp;
			this._ap	= data.ap;	
			this._mp 	= data.mp;
			this.maxHp = data.hp;
			
			if (force == FORCE_A) {
				_force_enemy = FORCE_B;
			} else {
				_force_enemy = FORCE_A;
			}
		}
		
		public function onBuildBuilding(building:BattleBuilding):void
		{
			
		}
		
		
		public function onRemoveBuilding(building:BattleBuilding):void
		{
			
		}
		
		public function battleInit(battle:StageBattle, res_world_data:WorldSet) : ForcePlayer
		{
			this._battle = battle;
			var world : BattleWorld = battle.getWorld();
			
			// init map region
			for each (var rg : RegionObject in res_world_data.Regions) {
				var rg_prop : Map = Map.readFromProperties(rg.Data);
				if (rg_prop.get("type") == BattleMapObject.REGION_BASE && rg_prop.contains("force")) {
					if (rg_prop.get("force") == force) {
						this._region_rect 	= new Rectangle(rg.X, rg.Y, rg.W, rg.H);
						this._region_data	= rg_prop;
						break;
					}
				}
			}
			if (_region_data == null) {
				throw Error("Player Region Data Error : 此地图中未能找到对应的玩家出生区域。");
			}
			for (var i:int = world.numChildren-1; i>=0; --i) {
				if (world.getChildAt(i) is BattleMapHoly) {
					var bmo : BattleMapHoly = world.getChildAt(i) as BattleMapHoly;
					if (bmo.getType() == BattleMapObject.TYPE_HOLY && bmo.getForce() == force) {
						this._holy_obj = bmo;
						this._holy_obj.bindPlayer(this);
						break;
					}
				}
			}
			if (_holy_obj == null) {
				throw Error("Player Region Data Error : 此地图中未能找到对应的玩家圣物。");
			}
			
			// init skill cool down
			this._skillCoolDown = new Map();
			for each (var sd:SkillData in data.skills.datas) {
				this._skillCoolDown.put(sd.skillType, new SkillState(sd, this));
			}
			
			//
			getCanUseRoad();
			return this;
		}
		
		private function getCanUseRoad():void
		{
			for(var i:int = region_bounds.y;i <region_bounds.y + region_bounds.height ;  i += this.battle.getWorld().getCellH() )
			{
				canUserRoard.push(i/this.battle.getWorld().getCellH());
			}
		}
		
//		------------------------------------------------------------------------------------

		
		
		final public function get force() : int
		{
			return _force;
		}
		
		final public function get force_enemy() : int
		{
			return _force_enemy;
		}
		
		final public function get data() : BattlePlayer
		{
			return _data;
		}
		
		final public function get battle() : StageBattle
		{
			return _battle;
		}
		
		final public function get hp() : int
		{
			return _hp;
		}
		
		final public function get mp() : int
		{
			return _mp;
		}
		
		final public function get ap() : int
		{
			return _ap;
		}
		
		final public function get region_data() : Map
		{
			return _region_data;
		}
		final public function get region_bounds() : Rectangle
		{
			return _region_rect;
		}
		final public function get holy() : BattleMapObject
		{
			return _holy_obj;
		}
		
		public function get active() : Boolean
		{
			return _hp > 0;
		}
		
//		------------------------------------------------------------------------------------
		
		public function lockAP(lock:Boolean):void
		{
			is_lock_ap = lock;
		}
		
		public function isLockAP():Boolean
		{
			return is_lock_ap;
		}
		
		public function addAP(v:int) : int
		{
			if (_ap + v < 0) {
				_ap = 0;
			} else {
				_ap += v;
			}
			return this._ap;
		}
		
		public function addMP(v:Number) : Number
		{
			if (_mp + v < 0) {
				_mp = 0;
			} else {
				_mp += v;
			}
			return this._mp;
		}
		
		public function addHP(v:Number) : Number
		{
			if (_hp + v < 0) {
				_hp = 0;
			} else {
				_hp += v;
			}
			return this._hp;
		}
		
		public function getHP() : Number
		{
			return _hp;
		}
		
//		------------------------------------------------------------------------------------
		
//		------------------------------------------------------------------------------------
		
		/** abstract */
		public function battleUpdate() : void
		{

		}
		
		
		
//		--------------------------------------------------------------------------------------------------
//		释放技能逻辑
//		--------------------------------------------------------------------------------------------------
		
		public function isSkillCoolDown(skillType:int) : Boolean
		{
			return getSkillState(skillType).isCD();
		}
		
		public function getSkillState(skillType:int) : SkillState
		{
			return _skillCoolDown.get(skillType);
		}
		
		public function launchSkill(sd:SkillData, worldX:int, worldY:int) : BattleSkill
		{
			var tp : SkillTemplate = DataManager.getSkillTemplate(sd.skillType);
			var state : SkillState = getSkillState(sd.skillType);
			if (tp.costMP <= this._mp && tp.costHP < this._hp && !state.isCD()) 
			{
				var skill : BattleSkill = new BattleSkill(battle, sd, tp, this, worldX, worldY);
				if (tp.aoe == 0 || tp.range == 0) {
					if (skill.pickUnit() == null) {
						return null;
					}
				}
				_battle.getWorld().addChild(skill);
				this.addMP(-tp.costMP);
				this.addHP(-tp.costHP);
				state.startCD();
				this.dispatchEvent(new SkillLaunchEvent(skill));
				return skill;
			}
			return null;
		}		
		
//		--------------------------------------------------------------------------------------------------
//		造兵逻辑
//		--------------------------------------------------------------------------------------------------
		
		public function buildBuilding(sd:SoldierData, worldX:int, worldY:int) : BattleBuilding
		{
			if (_battle.getWorld().isCanBuild(worldX, worldY, this)) {
				var tp : UnitTemplate = DataManager.getUnitTemplate(sd.unitType);
				if (tp.cost <= this._ap) {
					var building : BattleBuilding = new BattleBuilding(battle, sd, tp, this, worldX, worldY);
					_battle.getWorld().addChild(building);
					this.addAP(-tp.cost);
					dispatchEvent(new BuildBuildingEvent(building));
					return building;
				}
			}
			return null;
		}
		
		//mySoldier是否从自己的找 //是否是最近还是最远的
		public function getBattleSoldierBy(mySoldier:Boolean,nearest:Boolean):BattleSoldier
		{
			var cur:BattleSoldier;
			var maxX:Number = Number.MIN_VALUE;
			var minX:Number = Number.MAX_VALUE;
			for each(var trackY:int in canUserRoard)
			{
				var solids:Array;
				
				if(mySoldier)
					solids = this.battle.getWorld().getTrackSoldiers(this.force,trackY);
				else
					solids = this.battle.getWorld().getTrackEnemy(this.force,trackY);
				
				for each (var bs:BattleSoldier in solids)
				{
					var dis:Number = Math.abs( bs.x - this.holy.x);
					
					if(!nearest)
					{	 
						if(dis >maxX )
						{
							cur = bs;
							maxX =dis;
						}
					}
					else 
					{
						if(bs.x <minX )
						{
							cur = bs;
							minX =dis;
						}
					}
				}
			}
			return cur;
		}
		
	}
}