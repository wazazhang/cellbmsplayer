package com.fc.castle.gfx.battle
{
	import com.cell.gameedit.ResourceLoader;
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellSprite;
	import com.cell.gfx.game.worldcraft.CellMapWorld;
	import com.cell.math.MathVector;
	import com.cell.util.Map;
	import com.fc.castle.data.BattleEvent;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.ai.ForcePlayerComputer;
	import com.fc.castle.gfx.battle.event.BattleComplateEvent;
	import com.fc.castle.gfx.battle.res.CBattleResource;
	import com.fc.castle.gfx.battle.res.SkillBehavior;
	import com.fc.castle.gfx.battle.ui.BattleCompleteForm;
	import com.fc.castle.gfx.battle.ui.BattleHUD;
	import com.fc.castle.gfx.battle.ui.ForcePlayerHuman;
	import com.fc.castle.gfx.battle.ui.SystemMenu;
	import com.fc.castle.gfx.tutorial.BattleTutorial;
	import com.fc.castle.gfx.tutorial.Tutorial;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.ScreenBattle;
	import com.fc.castle.screens.Screens;
	
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;

	public class StageBattle extends CellSprite
	{
//		--------------------------------------------------------------------------------------------------
		
		private var unique_index	: int = 0;
		
		private var tickTimer		: uint;
		
		internal var data 			: BattleStartResponse;
		
		internal var battleRes		: CBattleResource;
		
		internal var world			: BattleWorld;
		
		private var _is_pause 		: Boolean;
		private var _is_over		: Boolean;
		
//		--------------------------------------------------------------------------------------------
		
		public var passive_pause 	: Boolean;
		public var tutorial			: BattleTutorial;
		
//		--------------------------------------------------------------------------------------------
		
		
		public function StageBattle(data:BattleStartResponse, 
									battleRes:CBattleResource, 
									force_players:Vector.<ForcePlayer>)
		{
			SkillBehavior.loadClasses();
			this.data = data;
			this.tickTimer = 0;
			this.battleRes = battleRes;
			this.world = new BattleWorld(this,
				Screens.WIDTH, 
				Screens.HEIGHT, 
				battleRes);
			this.world.init(force_players);
			
			
			if (data.battleType == BattleStartRequest.TYPE_GUIDE) 
			{
				var step:int = Screens.client.getPlayerQuest().guide_steps;
				this.tutorial = Tutorial.getBattleTorial(step, this);
			}
		}
		
		override protected function added(e:Event):void
		{
			addChild(world);
			initCameraMotion();
		}
		
		override protected function removed(e:Event):void
		{

		}
		
		override protected function update(e:Event):void
		{
			if (!_is_pause) {
				if (!passive_pause && !_is_over) {
					world.update();
					tickTimer++;
				}
				// 移到内部监听了
//				if (this.tutorial!=null) {
//					this.tutorial.update();
//				}
			}
			
			if (cameraTartget!=null) {
				if(MathVector.moveTo(this.world.getCamera(),cameraTartget.x,cameraTartget.y,25)) {
					cameraTartget = null;
				}
			}
			
			world.render();
		}
		
		/**游戏是否暂停状态*/
		public function isPause() : Boolean
		{
			return _is_pause;
		}
		
		/**从开始到现在，一共经历了多少帧*/
		public function getTimer() : uint
		{
			return tickTimer;
		}
		
		public function isOver() : Boolean
		{
			return _is_over;
		}
		
		public function setPause(pause:Boolean) : void
		{
			this._is_pause = pause;
		}
		
		public function getUniqueIndex() : int
		{
			this.unique_index ++;
			return this.unique_index;
		}
		
//		--------------------------------------------------------------------------------------------------
		
		public function getRes() : CBattleResource
		{
			return battleRes;
		}
		
		public function getData() : BattleStartResponse
		{
			return data;
		}
		
		public function getWorld() : BattleWorld
		{
			return world;
		}
///		---------------------------------	
//		战斗记录		
///		---------------------------------		
		
		private var logBattleEvent:Array = new Array();
		
		private function addBattleEvent(event:BattleEvent):void
		{
			logBattleEvent.push(event);
		}
		
		
		public function addBattleBuildingEvent(battleBuilding:BattleBuilding):void
		{
			var event:BattleEvent = new BattleEvent(this.tickTimer,BattleEvent.EVENT_BUILD,battleBuilding.force,
				[battleBuilding.uuid,battleBuilding.getSoldierData().unitType]
			)	
			addBattleEvent(event);
		}
		
		public function addUnBattleBuildingEvent(battleBuilding:BattleBuilding):void
		{
			var event:BattleEvent = new BattleEvent(this.tickTimer,BattleEvent.EVENT_UNBUILD,battleBuilding.force,
				[battleBuilding.uuid,battleBuilding.getSoldierData().unitType]
			)	
			addBattleEvent(event);
		}
		
		public function addBattleSoldierEvent(battleSoldier:BattleSoldier):void
		{
			var event:BattleEvent = new BattleEvent(this.tickTimer,BattleEvent.EVENT_SOLDIER_SPAWN,battleSoldier.force,
				[battleSoldier.uuid,battleSoldier.getSoldierData().unitType]
			)	
			addBattleEvent(event);	
		}
		
		public function addBattleSoldierDeadEvent(battleSoldier:BattleSoldier):void
		{
			var event:BattleEvent = new BattleEvent(this.tickTimer,BattleEvent.EVENT_SOLDIER_DIED,battleSoldier.force,
				[battleSoldier.uuid,battleSoldier.getSoldierData().unitType]
			)	
			addBattleEvent(event);	
		}
		
		public function addBattleSkillEvent(battleSkill:BattleSkill):void
		{
			var event:BattleEvent = new BattleEvent(this.tickTimer,BattleEvent.EVENT_LAUNCH_SKILL,battleSkill.forcePlayer.force,
				[battleSkill.uuid ,battleSkill.skillData.skillType]
			)	
			addBattleEvent(event);	
		}
		
		/*public function addBattleSkillEvent(battleSkillEvent:BattleSkill):void
		{
			
		}*/
		
		
//		--------------------------------------------------------------------------------------------------
//		摄像机移动逻辑
//		--------------------------------------------------------------------------------------------------
		
		
		
		
		private var mouse_drag_start : Point = null;
		private var mouse_camera_start : Point = null;
		public var lockCamera:Boolean = false;
		private var cameraTartget: Point = null
		
		private function initCameraMotion() : void
		{
			addEventListener(MouseEvent.MOUSE_DOWN,	onCameraStart);
			addEventListener(MouseEvent.MOUSE_UP, 	onCameraOver);
			addEventListener(MouseEvent.MOUSE_OUT,	onCameraOver);
			addEventListener(MouseEvent.MOUSE_MOVE,	onCameraMove);
			
			addEventListener(MouseEvent.MOUSE_WHEEL, onCameraScale);
		}
		
		private function onCameraScale(e:MouseEvent) : void
		{
			if(lockCamera)
				return;
			
			if(cameraTartget!=null)
				return;
					
			world.z += e.delta;
		}
		
		private function onCameraStart(e:MouseEvent) : void
		{
			if(lockCamera)
				return;
			
			if(cameraTartget!=null)
				return;
			
			mouse_drag_start = new Point(mouseX, mouseY);
			mouse_camera_start = new Point(world.camera.x, world.camera.y);
		}
		private function onCameraOver(e:MouseEvent) : void
		{
			if(lockCamera)
				return;
			
			if(cameraTartget!=null)
				return;
			
			mouse_drag_start = null;
		}
		private function onCameraMove(e:MouseEvent) : void
		{
			if(lockCamera)
				return;
			
			if(cameraTartget!=null)
				return;
			
			if (mouse_drag_start != null) {
				world.locateCamera(
					mouse_camera_start.x + (mouse_drag_start.x - mouseX), 
					mouse_camera_start.y + (mouse_drag_start.y - mouseY));
			}
		}
		
		public function setCameraTartget(x:Number,y:Number):void
		{
			
			var setX:Number = x - world.getCameraWidth()*0.5;
			var setY:Number = y - world.getCameraHeight()*0.5;
			
			if(setX<0)
			{
				setX = 0
			}
			var dis:Number  = world.getWorldWidth() - world.getCameraWidth();
			if(setX> dis)
			{
				setX = dis;
			}
			
			if(setY<0)
			{
				setY = 0;
			}
			
			dis = world.getWorldHeight() - world.getCameraHeight()
			if(setY>dis)
			{
				setY =dis;
			}
			
			
			cameraTartget = new Point(setX,setY);
		}
		
		
		public function holyDesotyed(holy:BattleMapHoly) : void
		{
			if (!this._is_over) {
				this._is_over = true;
				//holy.
				this.setCameraTartget(holy.x, holy.y);
				var complete : BattleCompleteForm = new BattleCompleteForm(this);
				complete.setCenter(parent);
				parent.addChild(complete);
				this.dispatchEvent(new BattleComplateEvent(holy.getForce()));
				complete.set(logBattleEvent,holy.getForce());
			}
			
		}
		
		
		
	}
}