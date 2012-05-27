package com.fc.castle.gfx.battle.ui
{
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Pan;
	import com.cell.ui.component.TextButton;
	import com.fc.castle.data.BattleEvent;
	import com.fc.castle.data.BattleLog;
	import com.fc.castle.data.message.Messages.CommitBattleResultRequest;
	import com.fc.castle.data.message.Messages.CommitBattleResultResponse;
	import com.fc.castle.gfx.battle.StageBattle;
	import com.fc.castle.gfx.battle.ai.ForcePlayer;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.BaseForm;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;

	public class BattleCompleteForm extends BaseForm
	{
		private var btn_confim : TextButton;
		private var failedForce:int;
		
		
		private var log:Array;
		
		
		private var A_buildCount:int;
		private var B_buildCount:int;
		
		private var A_soilderCount:int;
		private var B_soilderCount:int;
		
		private var A_skillCount:int;
		private var B_skillCount:int;
		
		private var A_deadCount:int;
		private var B_deadCount:int;
		
		private var battle:StageBattle;
		
		private var cardPanel:CardSelectPanel;
		
		public function BattleCompleteForm(battle:StageBattle)
		{
			super(500, 300);
			this.battle = battle;
			super.setTitle(LanguageManager.getText("ui.battle.win"));
		}
		
		public function set(log:Array,failedForce:int):void
		{
			this.failedForce = failedForce;
			if (failedForce == ForcePlayer.FORCE_B){
				super.setTitle(LanguageManager.getText("ui.battle.win"));
			}else{
				super.setTitle(LanguageManager.getText("ui.battle.lose"));
			}
			this.log = log;
			
			A_buildCount =0;
			B_buildCount=0;
			
			A_soilderCount=0;
			B_soilderCount=0;
			
			A_skillCount=0;
			B_skillCount=0;
			
			A_deadCount=0;
			B_deadCount=0;
			
		
			for each (var battleEvent:BattleEvent in log)
			{
				if(battleEvent.event == BattleEvent.EVENT_BUILD)
				{
					if(battleEvent.force == ForcePlayer.FORCE_A)
						A_buildCount++;
					else if(battleEvent.force == ForcePlayer.FORCE_B)
						B_buildCount++;
				}
				else if(battleEvent.event == BattleEvent.EVENT_LAUNCH_SKILL)
				{
					if(battleEvent.force == ForcePlayer.FORCE_A)
						A_skillCount++;
					else if(battleEvent.force == ForcePlayer.FORCE_B)
						B_skillCount++;
				}
				else if(battleEvent.event == BattleEvent.EVENT_SOLDIER_SPAWN)
				{
					if(battleEvent.force == ForcePlayer.FORCE_A)
						A_soilderCount++;
					else if(battleEvent.force == ForcePlayer.FORCE_B)
						B_soilderCount++;
				}
				else if(battleEvent.event == BattleEvent.EVENT_SOLDIER_DIED)
				{
					if(battleEvent.force == ForcePlayer.FORCE_A)
						A_deadCount++;
					else if(battleEvent.force == ForcePlayer.FORCE_B)
						B_deadCount++;
				}
			}
			
			var textA_buildCount:TextField = new TextField();
			textA_buildCount.htmlText = LanguageManager.getText("battleComplate.build", A_buildCount);
			textA_buildCount.y = 20;
			textA_buildCount.x = 60;
			addChild(textA_buildCount);
			
			var textB_buildCount:TextField = new TextField();
			textB_buildCount.htmlText = LanguageManager.getText("battleComplate.build", B_buildCount);
			textB_buildCount.y = 20;
			textB_buildCount.x = 300;
			addChild(textB_buildCount);
			
			var textA_soilderCount:TextField = new TextField();
			textA_soilderCount.htmlText = LanguageManager.getText("battleComplate.soldier", A_soilderCount);
			textA_soilderCount.y = 40;
			textA_soilderCount.x = 60;
			addChild(textA_soilderCount);
			
			var textB_soilderCount:TextField = new TextField();
			textB_soilderCount.htmlText = LanguageManager.getText("battleComplate.soldier", B_soilderCount);
			textB_soilderCount.y = 40;
			textB_soilderCount.x = 300;
			addChild(textB_soilderCount);
			
			var textA_skillCount:TextField = new TextField();
			textA_skillCount.htmlText = LanguageManager.getText("battleComplate.skill", A_skillCount);
			textA_skillCount.y = 60;
			textA_skillCount.x = 60;
			addChild(textA_skillCount);
			
			var textB_skillCount:TextField = new TextField();
			textB_skillCount.htmlText = LanguageManager.getText("battleComplate.skill", B_skillCount);
			textB_skillCount.y = 60;
			textB_skillCount.x = 300;
			addChild(textB_skillCount);
			
			var textA_deadCount:TextField = new TextField();
			textA_deadCount.htmlText = LanguageManager.getText("battleComplate.dead", A_deadCount);
			textA_deadCount.y = 80;
			textA_deadCount.x = 60;
			addChild(textA_deadCount);
			
			var textB_deadCount:TextField = new TextField();
			textB_deadCount.htmlText = LanguageManager.getText("battleComplate.dead", B_deadCount);
			textB_deadCount.y = 80;
			textB_deadCount.x = 300;
			addChild(textB_deadCount);
			
			
			btn_confim = new TextButton(
				super.getTitle(), 200, 30);
			btn_confim.x = width/2 - btn_confim.width/2;
			btn_confim.y = height - btn_confim.height - 20;
			btn_confim.addEventListener(MouseEvent.CLICK,btnClick);
			addChild(btn_confim);			
			
//			this.addEventListener(MouseEvent.CLICK,btnClick);
		}
		
		
	    private function btnClick(event:Event):void
		{
			var req:CommitBattleResultRequest = new CommitBattleResultRequest(
				Screens.client.getPlayerID(),new BattleLog(battle.getData(),log),
				battle.getData().scene_unit_name,
				(failedForce == ForcePlayer.FORCE_B)
				);
			Screens.client.sendRequest(req,sendSuccess,sendfailed);
		}
		
		private function sendSuccess(event:CClientEvent):void
		{
			var res:CommitBattleResultResponse = event.response as CommitBattleResultResponse
				
			if (res.result == CommitBattleResultResponse.RESULT_SUCCEED)
			{
				Screens.client.refreshPlayerData(sendRefreshSuccess, sendRefreshError);
			}
			else
			{
				Alert.showAlertText("发生未知错误");
			}
		}
		
		private function sendfailed(event:CClientEvent):void
		{
			Alert.showAlertText("上传战报失败");
		}
		
		private function sendRefreshSuccess(event:CClientEvent) : void
		{
			Screens.getRoot().changeScreen(Screens.SCREEN_MAIN_MENU);
		}
		
		private function sendRefreshError(event:CClientEvent) : void
		{
			Alert.showAlertText("无法刷新玩家数据");
		}
	}
}