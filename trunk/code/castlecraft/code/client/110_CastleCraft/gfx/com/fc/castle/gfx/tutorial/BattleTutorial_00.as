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
	
	public class BattleTutorial_00 extends BattleTutorial
	{

		private var timer:int;
		private var btn:BtnSoldierBuild;
		private var skillBtn:BtnSkillLaunch;
		private var arrow:HelpArrow = new HelpArrow();
		
		public function BattleTutorial_00(battle:StageBattle)
		{
			super(battle, "guide_1.properties");
			ID = Tutorial.BATTLE_FORCE_TUTORIAL;
		}
		
		override protected function onStart():void
		{
			super.onStart();				
			curDialog = new NpcDialog(Map.readFromProperties(loader.data));
			curDialog.setDialogY(80);
			humanForce.getBuild_hilight().addChild(arrow);
			battle.addEventListener(BattleComplateEvent.TYPE,complateAll);
			step1();
		}
		
		
		override protected function onUpdate():void
		{
		
		}
		
		
		private function step1():void
		{
			battle.passive_pause = true;
			btn = (btnSoldiers[0] as BtnSoldierBuild)
			arrow.x = btn.x + ( btn.width - arrow.width)*0.5 ;
			arrow.y = btn.y - 10;
			addChild(arrow);
			btn.isWink = true;
			addChild(curDialog);
			curDialog.next();
			battle.lockCamera = true;
			humanForce.addEventListener(BuildBtnDownEvent.TYPE,step1ComplateHandle);
		}
		
		private function step1ComplateHandle(event:Event):void
		{
			btn.isWink = false;
			humanForce.removeEventListener(BuildBtnDownEvent.TYPE,step1ComplateHandle);
			step2();
		}
		
		private function step2():void
		{
			curDialog.next();
			arrow.x = humanForce.region_bounds.x + 6.5 * battle.getWorld().getCellW() - arrow.width*0.5;
			arrow.y =  humanForce.region_bounds.y - 10  -  battle.getWorld().getCameraY(); 
			humanForce.addEventListener(BuildBuildingEvent.TYPE,step2ComplateHandle);
		}
		
		
		private function step2ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(BuildBuildingEvent.TYPE,step2ComplateHandle);
			battle.lockCamera = false;
			battle.passive_pause = false;
			this.visible = false;
//			humanForce.addEventListener(SoldierDeadEvent.TYPE,step3);
		}
		
		private function step3(event:Event):void
		{
			humanForce.removeEventListener(SoldierDeadEvent.TYPE,step3);
			
			this.visible = true;
			battle.passive_pause = true;
			battle.lockCamera = true;
			curDialog.next();
			skillBtn = (btnSkills[0] as BtnSkillLaunch);
			arrow.x = skillBtn.x +( skillBtn.width - arrow.width)*0.5 ;
			arrow.y = skillBtn.y -10;
			var bts:BattleSoldier = enemyForce.getBattleSoldierBy(true,false);
			battle.setCameraTartget(bts.getBodyMiddleX(),bts.getBodyMiddleY()-50);
			humanForce.addEventListener(SkillBtnDownEvent.TYPE,step3ComplateHandle);
		}
		
		private function step3ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(SkillBtnDownEvent.TYPE,step3ComplateHandle);		
			step4();
		}
		
		
		
		private function step4():void
		{
			curDialog.next();
			var bts:BattleSoldier = enemyForce.getBattleSoldierBy(true,false);
			arrow.x = bts.getBodyMiddleX() - battle.getWorld().camera.x - 0.5 *　arrow.width;
			arrow.y = bts.y - battle.getWorld().camera.y + bts.getBody().Y1 - 5;
			humanForce.addEventListener(SkillLaunchEvent.TYPE ,step4ComplateHandle);
		}
		
		private function step4ComplateHandle(event:Event):void
		{
			humanForce.removeEventListener(SkillLaunchEvent.TYPE ,step4ComplateHandle);
			this.visible = false;
			battle.passive_pause = false;
			enemyForce.addEventListener(SoldierDeadEvent.TYPE,step5);
		}
		
		private function step5(event:Event):void
		{
			enemyForce.removeEventListener(SoldierDeadEvent.TYPE,step5);
			arrow.visible = false;
			curDialog.next();
			this.visible = true;
			battle.passive_pause = true;
			this.addEventListener(MouseEvent.CLICK,step5ComplateHandle)
		}
		
		private function step5ComplateHandle(event:Event):void
		{
			this.removeEventListener(MouseEvent.CLICK,step5ComplateHandle);
			battle.passive_pause = false;
			battle.lockCamera = false;
			this.visible = false;
			
			
		//	complate();
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