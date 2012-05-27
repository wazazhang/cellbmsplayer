package com.fc.castle.gfx.tutorial
{
	import com.cell.ui.component.Alert;
	import com.cell.util.Map;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class Tutorial_00 extends Tutorial
	{
		public function Tutorial_00()
		{
			super("guide_0.properties");
			ID = Tutorial.BEGIN_TUTORIAL;
		}
		
		override protected function onStart():void
		{
			super.onStart();
			curDialog = new NpcDialog(Map.readFromProperties(loader.data));
			curDialog.setDialogY(180);
			curDialog.lock = true;
			addChild(curDialog);
			curDialog.next();
			this.addEventListener(MouseEvent.CLICK,clickHandle);
		}
		
		private function clickHandle(event:MouseEvent):void
		{
			if(curDialog.isEnd)
			{
				this.removeEventListener(MouseEvent.CLICK,clickHandle);
				complate();
			}
			else
			{
				curDialog.next();
			}
		}
		
		override public function complate():void
		{
			super.complate();
			var startRequest:BattleStartRequest = new BattleStartRequest(
				Screens.client.getPlayer().player_id,
				BattleStartRequest.TYPE_GUIDE);
			Screens.client.sendRequest(startRequest,onStartResponse,onError);
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
		
		private function addClickEvent():void
		{
			this.addEventListener(MouseEvent.CLICK,clickHandle)
		}
		private function resend():void
		{
			this.removeEventListener(MouseEvent.CLICK,clickHandle);
			var startRequest:BattleStartRequest = new BattleStartRequest(
				Screens.client.getPlayer().player_id,
				BattleStartRequest.TYPE_GUIDE);
			Screens.client.sendRequest(startRequest,onStartResponse,onError)
		}
	}
}