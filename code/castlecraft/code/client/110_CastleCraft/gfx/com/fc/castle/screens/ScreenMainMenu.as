package com.fc.castle.screens
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.layout.UIRect;
	import com.fc.castle.data.PlayerData;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.data.message.Messages.GetExploreDataListRequest;
	import com.fc.castle.data.message.Messages.GetExploreDataListResponse;
	import com.fc.castle.data.message.Messages.GetPlayerQuestRequest;
	import com.fc.castle.gfx.battle.ui.BattleCompleteForm;
	import com.fc.castle.gfx.tutorial.GuideStep;
	import com.fc.castle.gfx.tutorial.GuideStepMap;
	import com.fc.castle.gfx.tutorial.Tutorial;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.Res;
	import com.fc.castle.ui.FormExploerData;
	import com.fc.castle.ui.FormReadyForBattle;
	import com.fc.castle.ui.HUDMain;
	import com.fc.castle.ui.mail.FormMailList;
	import com.fc.castle.ui.test.FormSelectEnemy;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.sendToURL;

	public class ScreenMainMenu extends CellScreen
	{
		private var stage_world :StageWorld;
		private var tutorial:Tutorial;
		private var hud:HUDMain;
		public function ScreenMainMenu()
		{
			
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{
			stage_world = new StageWorld();
			addChild(stage_world);
		
			var ui : UIRect = Res.createUIRect(Res.ui_panel, 16);
			var start_pve : ImageButton = ImageButton.createImageButtonScaleBitmap(ui.createBuffer(64, 64), 1.2);
			start_pve.x = 0;
			start_pve.y = Screens.HEIGHT - 64;
			start_pve.addEventListener(MouseEvent.CLICK, onStart);
			addChild(start_pve);
			
			//var playerData:PlayerData = Screens.client.getPlayer();
			var step:int = Screens.client.getPlayerQuest().guide_steps;
			var gsm:GuideStepMap = CClient.getGuideStepMap();
			if (gsm!=null){
				var gs:GuideStep = gsm.getGuideStep(step);
				if (gs!=null){
					if (Screens.client.getPlayer().level>=gs.level_condition){
						if (gs.type == 0){
							tutorial = Tutorial.getNormalTorial(step);
							if(tutorial!=null)
								addChild(tutorial);
							return;
						}else if (gs.type==1){
							var startRequest:BattleStartRequest = new BattleStartRequest(
								Screens.client.getPlayer().player_id,
								BattleStartRequest.TYPE_GUIDE);
							Screens.client.sendRequest(startRequest,onStartResponse,onError);
								
							return;
						}
					}
				}
			}

			// 用hud.visible 控制显示与否
			hud = new HUDMain(stage_world)
			hud.setPlayerData(Screens.client.getPlayer());
			addChild(hud);
			
			Screens.client.addEventListener(CClientEvent.RESPONSE, onAnyResponse);
		}
		
		override public function removed(root:CellScreenManager):void
		{
			Screens.client.removeEventListener(CClientEvent.RESPONSE, onAnyResponse);
		}
		
		override public function update():void
		{
			// 移到内部监听了
			//if(tutorial!=null)
			//	tutorial.update();
		}
		
		private function onAnyResponse(e:CClientEvent) : void
		{
			hud.setPlayerData(Screens.client.getPlayer());
		}
		
		private function onStart(e:Event) : void
		{
			addChild(new FormSelectEnemy());	
		}
		
		private function onStartResponse(e:CClientEvent) : void
		{
			var res : BattleStartResponse = e.response as BattleStartResponse;
			if (res.result == BattleStartResponse.RESULT_SUCCEED) {
				Screens.getRoot().changeScreen(Screens.SCREEN_BATTLE_LOADING, [res]);
			} else {
				Alert.showAlertText("无法开始战斗！", "错误", false, true);
				//addClickEvent();
			}
		}
		
		private function onError(e:CClientEvent) : void
		{
			Alert.showAlertText("无法开始战斗！", "网络错误", false, true);
			//addClickEvent();
		}
		
		public function getStageWorld():StageWorld
		{
			return stage_world;
		}
	}
}