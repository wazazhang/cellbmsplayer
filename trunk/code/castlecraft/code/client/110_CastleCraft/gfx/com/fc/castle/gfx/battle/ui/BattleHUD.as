package com.fc.castle.gfx.battle.ui
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.ImageNumber;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.ImageUtil;
	import com.cell.util.Util;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.BuildPlane;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.gfx.battle.ai.ForcePlayerComputer;
	import com.fc.castle.gfx.tutorial.BattleTutorial;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
//	import com.fc.castle.ui.ImageNumber2;
	
	import flash.display.BitmapData;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.geom.ColorTransform;
	import flash.text.TextField;

	public class BattleHUD extends CellSprite
	{
		internal var battle 	: StageBattle;
		
		private var player 		: ForcePlayer;
		private var computer 		: ForcePlayer;
		private var battleTorial	: BattleTutorial;
		public function BattleHUD(battle:StageBattle, force:ForcePlayer)
		{
			this.battle = battle;
			this.player	= force;
			computer = battle.getWorld().getEnemyForce(player.force);
				
			var res : BattleStartResponse = battle.getData();
			
			initForceInfo();
			
			if (player is ForcePlayerHuman) {
				initSoldierButton(res, player as ForcePlayerHuman);
				initSkillButton(res, player as ForcePlayerHuman);
			}
			
			if(battle.tutorial!=null)
				addChild(battle.tutorial)
		}
		
		override protected function update(e:Event):void
		{
			updateForceInfo();
		}
		
//		-----------------------------------------------------------------------------------------------------------------
		
		
		private function initSoldierButton(res : BattleStartResponse, human:ForcePlayerHuman) : void
		{			
			var pan_soldier_bg : BitmapData = CLayoutManager.ui_pan_soldier.createBuffer(
				CLayoutManager.SOLDIER_BTN_W, 
				CLayoutManager.SOLDIER_BTN_H);
			
			var color_trans : ColorTransform = new ColorTransform();
			color_trans.alphaMultiplier = 1;
			color_trans.redOffset 	= 100;
			color_trans.greenOffset = 100;
			color_trans.blueOffset 	= 0;
			var pan_soldier_hi : BitmapData = ImageUtil.colorTransform(pan_soldier_bg, color_trans);
			
			var pan_soldier_us : BitmapData = ImageUtil.toTurngrey(pan_soldier_bg, ImageUtil.CHANNEL_RED);
			
			var cost_number : ImageNumber = Res.number.copy(0, 10);
			cost_number.setColor(0xffffff00);
			var error_number : ImageNumber = Res.number.copy(0, 10);
			error_number.setColor(0xffff0000);
			
			for (var i:int=0; i<res.forceA.soldiers.datas.length; i++) {
				var sd : SoldierData = null;
				var bt : BtnSoldierBuild = null;
				sd = res.forceA.soldiers.datas[i];
				bt = new BtnSoldierBuild(battle, human, 
					pan_soldier_bg, pan_soldier_hi, 
					cost_number, error_number, 
					sd);
				
				bt.x = 2 + i * (bt.width + 2);
				bt.y = Screens.HEIGHT - 2 - bt.height;
				addChild(bt);
				
				if(battle.tutorial!=null)
					battle.tutorial.addBtnSoldierBuild(bt);	
			}
		}
		
		
		private function initSkillButton(res : BattleStartResponse, human:ForcePlayerHuman) : void
		{
			var pan_skill_bg : BitmapData = CLayoutManager.ui_pan_soldier.createBuffer(
				CLayoutManager.SKILL_BTN_W, 
				CLayoutManager.SKILL_BTN_H);
			
			var color_trans : ColorTransform = new ColorTransform();
			color_trans.alphaMultiplier = 1;
			color_trans.redOffset 	= 100;
			color_trans.greenOffset = 100;
			color_trans.blueOffset 	= 0;
			var pan_skill_hi : BitmapData = ImageUtil.colorTransform(pan_skill_bg, color_trans);
			
			var pan_skill_us : BitmapData = ImageUtil.toTurngrey(pan_skill_bg, ImageUtil.CHANNEL_RED);
			
			var cost_number : ImageNumber = Res.number.copy(0, 10);
			cost_number.setColor(0xffffff00);
			var error_number : ImageNumber = Res.number.copy(0, 10);
			error_number.setColor(0xffff0000);
			
			for (var i:int=0; i<res.forceA.skills.datas.length; i++) {
				var sd : SkillData = null;
				var bt : BtnSkillLaunch = null;
				sd = res.forceA.skills.datas[i];
				bt = new BtnSkillLaunch(battle, human, 
					pan_skill_bg, pan_skill_hi, 
					cost_number, error_number, 
					sd);
				
				
				
				bt.x = Screens.WIDTH - 2 - bt.width - i * (bt.width + 2);
				bt.y = Screens.HEIGHT - 2 - bt.height;
				addChild(bt);
				
				if(battle.tutorial!=null)
					battle.tutorial.addBtnSkill(bt);	
			}
		}
		
//		-----------------------------------------------------------------------------------------------------------------
		
		private var player_ap	: ImageNumber;
		private var player_mp	: ImageNumber;
		private var player_hp	: ImageNumber;
		
		
		private var computer_ap:ImageNumber;
		private var computer_mp:ImageNumber;
		private var computer_hp:ImageNumber;
		private var computer_plane:TextField;
		
		
		private function initForceInfo() : void
		{
			this.player_hp = Res.number.copy(player.hp);
			this.player_hp.setColor(0xff00ff00);
			this.player_hp.x = 2;
			this.player_hp.y = 2;
			addChild(player_hp);
			
			this.player_mp = Res.number.copy(player.mp);
			this.player_mp.setColor(0xff8080ff);
			this.player_mp.x = 2;
			this.player_mp.y = 22;
			addChild(player_mp);
			
			this.player_ap = Res.number.copy(player.ap);
			this.player_ap.setColor(0xffffff00);
			this.player_ap.x = 2;
			this.player_ap.y = 42;
			addChild(player_ap);
			
			
			this.computer_hp = Res.number.copy(player.hp);
			this.computer_hp.setColor(0xff00ff00);
			this.computer_hp.x = 100;
			this.computer_hp.y = 2;
			addChild(computer_hp);
			
			this.computer_mp = Res.number.copy(player.mp);
			this.computer_mp.setColor(0xff8080ff);
			this.computer_mp.x = 100;
			this.computer_mp.y = 22;
			addChild(computer_mp);
			
			this.computer_ap = Res.number.copy(player.ap);
			this.computer_ap.setColor(0xffffff00);
			this.computer_ap.x = 100;
			this.computer_ap.y = 42;
			addChild(computer_ap);
			
			this.computer_plane = new TextField();
			this.computer_plane.x = 100;
			this.computer_plane.y = 62;
			this.computer_plane.width = 200;
		//	this.computer_plane.text = "";
			addChild(computer_plane);
		}
		
		private function updateForceInfo() : void
		{
			this.player_hp.number = player.hp;
			this.player_mp.number = player.mp;
			this.player_ap.number = player.ap;
			
			this.computer_hp.number = computer.hp;
			this.computer_mp.number = computer.mp;
			this.computer_ap.number = computer.ap;
			
			var plane:BuildPlane = (this.computer as ForcePlayerComputer).getBestBuildPlane();
			if(plane!=null)
				this.computer_plane.text = "电脑即将建造:"+ DataManager.getUnitTemplate(plane.build.unitType).name +",在"+plane.Y+"道上.";
			else
				this.computer_plane.text = "电脑等待建造中...";
		}
		
	}
	
}