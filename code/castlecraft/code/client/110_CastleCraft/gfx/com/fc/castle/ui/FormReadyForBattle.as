package com.fc.castle.ui
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Pan;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.UIComponent;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.ui.layout.UIRect;
	import com.cell.util.Map;
	import com.fc.castle.data.PlayerFriend;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.LoadingManager;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardPanel;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castle.ui.card.CardSlot;
	import com.fc.castle.ui.test.FormSelectEnemy;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	 
	public class FormReadyForBattle extends BaseDialogForm
	{
		private var soldierSel 	: CardSelectPanel;
		private var skillSel 	: CardSelectPanel;
		
		private var battleType		: int;
		private var sceneUnitName 	: String;
		private var targetPlayerID	: int;
		
		public function FormReadyForBattle(battleType:int, unitName:String, targetPlayerID:int)
		{
			super(Screens.WIDTH, Screens.HEIGHT, true, true);
			super.setTitle(LanguageManager.getText("ui.readyforbattle.title"));
			
			this.battleType 	= battleType;
			
			this.sceneUnitName 	= unitName;
			this.targetPlayerID	= targetPlayerID;
			
			btnOK.visible = false;
			
			data_refresh();
			
		}
		
		override protected function added(e:Event):void
		{
			super.added(e);
		}
		
		
//		--------------------------------------------------------------------------------------------------------------------------
//		Data
//		--------------------------------------------------------------------------------------------------------------------------
		
		private var loadStep : int = 0;
		
		protected function data_refresh(e:Event=null) : void
		{
			if (loadStep == 0)
			{
				DataManager.getUnitTemplates(
					Screens.client.getPlayer().soldiers.datas, 
					data_complete,
					data_error);
			}
			else if (loadStep == 1)
			{
				DataManager.getSkillTemplates(
					Screens.client.getPlayer().skills.datas, 
					data_complete,
					data_error
				);
			}
		}
		
		protected function data_complete(map:Map) : void
		{			
			if (loadStep == 0)
			{
				initSoldiers();
				
				loadStep ++;
				data_refresh();
			}
			else if (loadStep == 1)
			{
				initSkills();
				
				loadStep ++;
				
				btnOK.visible = true;
			}
		}
		
		protected function data_error() : void
		{
			Alert.showAlertText("网络超时","错误").btnOK.addEventListener(MouseEvent.CLICK, data_refresh);
		}
		
//		--------------------------------------------------------------------------------------------------------------------------

//		------------------------------------------------------------------------------------------------------
//		士兵配给
//		------------------------------------------------------------------------------------------------------
		
		function initSoldiers() : void
		{
			soldierSel = new CardSelectPanel(340, 340, 
				CLayoutManager.SOLDIER_BTN_W,
				CLayoutManager.SOLDIER_BTN_H, 
				Screens.client.getPlayer().soldiers.datas,
				Screens.client.getPlayer().battle_soldier_count
			);
			soldierSel.x = 20;
			soldierSel.y = 30;
			addChild(soldierSel);
		}
		
		
		
//		------------------------------------------------------------------------------------------------------
//		技能配给
//		------------------------------------------------------------------------------------------------------
				
		function initSkills() : void
		{
			skillSel = new CardSelectPanel(340, 340, 
				CLayoutManager.SKILL_BTN_W,
				CLayoutManager.SKILL_BTN_H, 
				Screens.client.getPlayer().skills.datas,
				Screens.client.getPlayer().battle_skill_count
			);
			skillSel.x = width/2 + 20;
			skillSel.y = 30;
			addChild(skillSel);
		}
		
		public function getAllSoldiers():Array
		{
			if (soldierSel==null){
				return null;
			}
			return soldierSel.getAllCards();
		}
		
		public function getAllSkills():Array
		{
			if (skillSel==null){
				return null;
			}
			return skillSel.getAllCards();
		}
//		--------------------------------------------------------------------------------------------------------------------------
//		battle start
//		--------------------------------------------------------------------------------------------------------------------------
		
		override protected function onMouseClickOK(e:MouseEvent) : void
		{
			onStart();
		}

		private function onStart() : void
		{
			var selected_soldiers : Array = soldierSel.getSlotCardsData();
			if (selected_soldiers.length > 0)
			{
				var start:BattleStartRequest = new BattleStartRequest(
					Screens.client.getPlayer().player_id, 
					battleType);
				
				start.battleType 			= this.battleType;
				start.scene_unit_name 		= this.sceneUnitName;
				start.targetPlayerID		= this.targetPlayerID;
				
				start.soldiers 				= new SoldierDatas(selected_soldiers);
				start.skills 				= new SkillDatas(skillSel.getSlotCardsData());
				
				if (battleType == BattleStartRequest.TYPE_TEST) 
				{
					start.test_forceB_soldiers 	= FormSelectEnemy.forceB_soldiers;
					start.test_forceB_skills 	= FormSelectEnemy.forceB_skills;
				}
				
				Alert.showAlertText("开始战斗?","", true, true).
					btnOK.addEventListener(MouseEvent.CLICK,
					function onStartBattle(e:Event) : void {
						Screens.client.sendRequest(start, onStartResponse,onError);
						LoadingManager.show(parent);
					});
			} else {
				Alert.showAlertText("没有携带士兵！", "无法开始战斗", false, true);
			}
		}
		
		
		private function onStartResponse(e:CClientEvent) : void
		{
			LoadingManager.close();
			var res : BattleStartResponse = e.response as BattleStartResponse;
			if (res.result == BattleStartResponse.RESULT_SUCCEED) {
				Screens.getRoot().changeScreen(Screens.SCREEN_BATTLE_LOADING, [res]);
			} else {
				Alert.showAlertText("无法开始战斗！", "错误", false, true);
			}
		}
		
		private function onError(e:CClientEvent) : void
		{
			LoadingManager.close();
			Alert.showAlertText("无法开始战斗！", "网络错误", false, true);
		}
		
		
	}
}




