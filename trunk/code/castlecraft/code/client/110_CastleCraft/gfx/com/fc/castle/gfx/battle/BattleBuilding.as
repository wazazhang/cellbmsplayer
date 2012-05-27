package com.fc.castle.gfx.battle
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.SimpleProgress;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.effect.Transport;
	import com.fc.castle.gfx.battle.res.AnimateEnum;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CResourceManager;
	
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.events.Event;

	public class BattleBuilding extends BattleUnit
	{
		private var _uuid			: int;
		private var sd				: SoldierData;
		private var tp				: UnitTemplate;
		
		private var _build_timer	: int;
		private var _player 		: ForcePlayer;
		private var _force			: int;
		private var _trackY 		: int;
		private var _trackX 		: int;

		private var _magic_tp		: Transport;
		
		private var dbg_progress : SimpleProgress;
		
		private var target_player	: ForcePlayer;
		
		
		public function BattleBuilding(battle:StageBattle, 
									   sd:SoldierData,
									   tp:UnitTemplate, 
									   player:ForcePlayer,
									   worldX:int, worldY:int)
		{
			this._uuid		= battle.getUniqueIndex();
			this._player 	= player;
			this._force 	= player.force;
			this.sd 		= sd;
			this.tp 		= tp;
			
			super(battle, battle.battleRes.getActorSprite(tp));
			
			var cw : int = battle.world.battle.world.getCellW();
			var ch : int = battle.world.battle.world.getCellH();
			var gx : int = int(worldX/cw);
			var gy : int = int(worldY/ch);
			
			this.x = gx*cw + cw/2;
			this.y = gy*ch + ch/2;
			this._trackX = gx;
			this._trackY = gy;
			this._build_timer = 0;
			this.priority = BattleWorld.LAYER_LAND;
			
			this.dbg_progress = new SimpleProgress(40, 5, 0, 1);
			this.dbg_progress.x = -20;
			this.dbg_progress.y = getCSprite().getVisibleTop();
			this.addChild(dbg_progress);
			
			this.target_player = battle.world.getEnemyForce(force);
		
			AnimateEnum.setUnitDirect(this, target_player.holy.x - worldX);
			
			this._magic_tp = new Transport(battle, battle.getRes().gfx_transport, BlendMode.NORMAL);
			this._magic_tp.x = x;
			this._magic_tp.y = y;
			
			this.addEventListener(Event.ADDED_TO_STAGE, added);
			this.addEventListener(Event.REMOVED_FROM_STAGE, removed);
		}
		
		public function get uuid():int
		{
			return _uuid;
		}

		private function added(e:Event) : void
		{
			battle.world.addChild(_magic_tp);
		}
		
		private function removed(e:Event) : void
		{
			battle.world.removeChild(_magic_tp);
		}
		
		
		override protected function render(buff:BitmapData, anim:int, frame:int, x:Number, y:Number):void
		{
			this._bitmap.x = x;
			this._bitmap.y = y;
			this._bitmap.bitmapData = buff;
		}

//		-------------------------------------------------------------------------------------------------------------
		
		public function getSoldierData() : SoldierData
		{
			return sd;
		}
		
		public function getUnitTemplate() : UnitTemplate
		{
			return tp;
		}
		
		public function get force() : int
		{
			return _force;
		}
		
		public function get trackY() : int 
		{
			return _trackY;
		}
		
		public function get trackX() : int 
		{
			return _trackX;
		}
		
//		-------------------------------------------------------------------------------------------------------------
		
		override protected function onUpdate():void
		{
			dbg_progress.percent = (_build_timer % tp.trainingTime) / Number(tp.trainingTime);
			if (_build_timer == tp.trainingTime) {
				onStartSoldier();
				_build_timer = 0;
			}
			_build_timer ++;
			_bitmap.alpha = 0.5 + dbg_progress.percent/2;
		}
		
		private function onStartSoldier() : void
		{
			battle.world.addSoldier(this._player, sd, x, y);
		}
		
		
	}
}