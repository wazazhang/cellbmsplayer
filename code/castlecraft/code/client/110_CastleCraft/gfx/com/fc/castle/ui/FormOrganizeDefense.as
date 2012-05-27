package com.fc.castle.ui
{
	import com.cell.ui.component.Alert;
	import com.cell.util.Map;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.OrganizeDefenseRequest;
	import com.fc.castle.data.message.Messages.OrganizeDefenseResponse;
	import com.fc.castle.gfx.CAlert;
	import com.fc.castle.gfx.LoadingManager;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castlecraft.LanguageManager;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class FormOrganizeDefense extends BaseDialogForm
	{
		private var soldierSel 	: CardSelectPanel;
		private var skillSel 	: CardSelectPanel;
		
		public function FormOrganizeDefense()
		{
			super(Screens.WIDTH, Screens.HEIGHT, true, true);
			super.setTitle(LanguageManager.getText("ui.organizedefense.title"));
			
			btnOK.visible = false;
			
			data_refresh();
			
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
				
				if (Screens.client.getPlayer().organizeDefense != null) 
				{
					for each (var ud:SoldierData in Screens.client.getPlayer().organizeDefense.soldiers.datas) {
						soldierSel.selectCardType(ud.unitType);
					}
					for each (var sd:SkillData in Screens.client.getPlayer().organizeDefense.skills.datas) {
						skillSel.selectCardType(sd.skillType);
					}
				}
				
			}
		}
		
		protected function data_error() : void
		{
			removeFromParent();
			CAlert.showErrorNetwork();
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
		
		
		//		--------------------------------------------------------------------------------------------------------------------------
		//		battle start
		//		--------------------------------------------------------------------------------------------------------------------------
		
		override protected function onMouseClickOK(e:MouseEvent) : void
		{
			onStart();
		}
		
		private function onStart() : void
		{
			var selected_soldiers 	: Array = soldierSel.getSlotCardsData();
			
			var selected_skills		: Array = skillSel.getSlotCardsData();
			
			Screens.client.sendRequest(
				new OrganizeDefenseRequest(
					Screens.client.getPlayerID(), 
					new SoldierDatas(selected_soldiers), 
					new SkillDatas(selected_skills)),
				onDefenseResponse,
				onError);
		}
		
		
		private function onDefenseResponse(e:CClientEvent) : void
		{
			LoadingManager.close();
			var res : OrganizeDefenseResponse = e.response as OrganizeDefenseResponse;
			if (res.result == OrganizeDefenseResponse.RESULT_SUCCEED) {
				removeFromParent();
			} else {
				Alert.showAlertText("无法布防！", "错误", false, true);
			}
		}
		
		private function onError(e:CClientEvent) : void
		{
			LoadingManager.close();
			CAlert.showErrorNetwork();
		}
		
	}
}