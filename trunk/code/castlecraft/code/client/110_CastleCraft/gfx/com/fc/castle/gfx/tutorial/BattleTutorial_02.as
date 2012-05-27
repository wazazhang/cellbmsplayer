package com.fc.castle.gfx.tutorial
{
	import com.cell.util.Map;
	import com.fc.castle.gfx.battle.BattleSoldier;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.event.BattleComplateEvent;
	import com.fc.castle.gfx.battle.event.BuildBtnDownEvent;
	import com.fc.castle.gfx.battle.event.BuildBuildingEvent;
	import com.fc.castle.gfx.battle.event.SkillBtnDownEvent;
	import com.fc.castle.gfx.battle.event.SkillLaunchEvent;
	import com.fc.castle.gfx.battle.event.SoldierDeadEvent;
	import com.fc.castle.gfx.battle.ui.BtnSkillLaunch;
	import com.fc.castle.gfx.battle.ui.BtnSoldierBuild;
	import com.fc.castle.gfx.battle.ui.ForcePlayerHuman;
	import com.fc.castle.gfx.battle.ui.HelpArrow;
	import com.fc.castle.res.Res;
	
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.ByteArray;
	
	import mx.states.OverrideBase;
	
	public class BattleTutorial_02 extends BattleTutorial
	{

		private var timer:int;
		private var btn1:BtnSoldierBuild;
		private var skillBtn:BtnSkillLaunch;
		private var arrow:HelpArrow = new HelpArrow();
		
		public function BattleTutorial_02(battle:StageBattle)
		{
			super(battle, "guide_3.properties");
			ID = Tutorial.BATTLE_FORCE_TUTORIAL3;
		}
		
		override protected function onStart():void
		{
			super.onStart();
			curDialog = new NpcDialog(Map.readFromProperties(loader.data));
			curDialog.setDialogY(80);
			battle.addEventListener(BattleComplateEvent.TYPE,complateAll);
			addChild(curDialog);
			curDialog.next();
			btn1 = (btnSoldiers[0] as BtnSoldierBuild)
			this.addEventListener(MouseEvent.CLICK, step1);
		} 
		
		
		override protected function onUpdate():void
		{
		
		}
		
		
		private function step1(event:MouseEvent):void		//设定敌人在上路出兵
		{
			this.removeEventListener(MouseEvent.CLICK, step1);
			curDialog.visible = false;
			humanForce.is_can_build = false;
			enemyForce.setBuildPlane(0);
			enemyForce.addEventListener(BuildBuildingEvent.TYPE, step1ComplateHandle);
		}
		
		private function step1ComplateHandle(event:Event):void
		{
			enemyForce.removeEventListener(BuildBuildingEvent.TYPE,step1ComplateHandle);
			battle.setCameraTartget(enemyForce.holy.x, enemyForce.holy.y);
			this.addEventListener(MouseEvent.CLICK, step2);
			curDialog.next();
			curDialog.visible = true;
			battle.passive_pause = true;
		}
		
		private function step2(event:MouseEvent):void  // 指示玩家在上路出兵
		{
			this.removeEventListener(MouseEvent.CLICK, step2);
			battle.setCameraTartget(humanForce.holy.x, humanForce.holy.y);
			arrow.x = humanForce.region_bounds.x + 6.5 * battle.getWorld().getCellW() - arrow.width*0.5;
			arrow.y = humanForce.region_bounds.y - 10  -  battle.getWorld().getCameraY(); 
			addChild(arrow);
			battle.lockCamera = true;
			humanForce.is_can_build = true;
			humanForce.addEventListener(BuildBuildingEvent.TYPE,step2ComplateHandle);
		}
		
		private function step2ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(BuildBuildingEvent.TYPE,step2ComplateHandle);
			battle.lockCamera = false;
			curDialog.visible = false;
			battle.passive_pause = false;
			arrow.visible = false;
			step3();
		}
		
		private function step3():void //设定电脑在中路出兵
		{
			humanForce.is_can_build = false;
			enemyForce.setBuildPlane(1);
			enemyForce.addEventListener(BuildBuildingEvent.TYPE, step3ComplateHandle);
		}
		
		private function step3ComplateHandle(event:Event):void
		{
			enemyForce.removeEventListener(BuildBuildingEvent.TYPE,step3ComplateHandle);
			battle.setCameraTartget(enemyForce.holy.x, enemyForce.holy.y);
			this.addEventListener(MouseEvent.CLICK, step4);
			curDialog.next();
			curDialog.visible = true;
			battle.passive_pause = true;
		}
		
		private function step4(event:MouseEvent):void  // 指示玩家在中路出兵
		{
			this.removeEventListener(MouseEvent.CLICK, step4);
			battle.setCameraTartget(humanForce.holy.x, humanForce.holy.y);
			arrow.x = humanForce.region_bounds.x + 6.5 * battle.getWorld().getCellW() - arrow.width*0.5;
			arrow.y =  humanForce.region_bounds.y - 10+63  -  battle.getWorld().getCameraY(); 
			arrow.visible =true;
			battle.lockCamera = true;
			humanForce.is_can_build = true;
			humanForce.addEventListener(BuildBuildingEvent.TYPE,step4ComplateHandle);
		}
		
		private function step4ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(BuildBuildingEvent.TYPE,step4ComplateHandle);
			battle.lockCamera = false;
			curDialog.visible = false;
			battle.passive_pause = false;
			arrow.visible = false;
			step5();
		}
		
		private function step5():void
		{
			humanForce.is_can_build = false;
			enemyForce.setBuildPlane(2);
			enemyForce.addEventListener(BuildBuildingEvent.TYPE, step5ComplateHandle);
		}
		
		private function step5ComplateHandle(event:Event):void
		{
			enemyForce.removeEventListener(BuildBuildingEvent.TYPE,step5ComplateHandle);
			battle.setCameraTartget(enemyForce.holy.x, enemyForce.holy.y);
			this.addEventListener(MouseEvent.CLICK, step6);
			curDialog.next();
			curDialog.visible = true;
			battle.passive_pause = true;
		}
		
		private function step6(event:MouseEvent):void //指定玩家在下路出兵
		{
			this.removeEventListener(MouseEvent.CLICK, step6);
			battle.setCameraTartget(humanForce.holy.x, humanForce.holy.y);
			arrow.x = humanForce.region_bounds.x + 6.5 * battle.getWorld().getCellW() - arrow.width*0.5;
			arrow.y =  humanForce.region_bounds.y - 10 +126  -  battle.getWorld().getCameraY(); 
			arrow.visible = true;
			battle.lockCamera = true;
			humanForce.is_can_build = true;
			humanForce.addEventListener(BuildBuildingEvent.TYPE,step6ComplateHandle);
		}
		
		private function step6ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(BuildBuildingEvent.TYPE,step6ComplateHandle);
			battle.lockCamera = false;
			curDialog.visible = false;
			battle.passive_pause = false;
			arrow.visible = false;
		}
		
		
		private function complateAll(event:Event):void
		{
			complate();
		}
		
		
		
		override public function complate():void
		{
		
			this.battle = null
			super.complate();
		}
		
		
	}
}