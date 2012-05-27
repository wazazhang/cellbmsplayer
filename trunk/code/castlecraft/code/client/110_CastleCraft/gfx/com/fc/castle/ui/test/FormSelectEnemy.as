package com.fc.castle.ui.test
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
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.BattleStartResponse;
	import com.fc.castle.gfx.world.StageWorld;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.CResourceManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.BaseDialogForm;
	import com.fc.castle.ui.BaseForm;
	import com.fc.castle.ui.FormReadyForBattle;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardPanel;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castle.ui.card.CardSlot;
	
	import flash.events.Event;
	import flash.events.MouseEvent;


	public class FormSelectEnemy extends BaseDialogForm
	{
		private var soldierSel 	: CardSelectPanel;
		
		private var skillSel 	: CardSelectPanel;
		
		public function FormSelectEnemy()
		{
			super(Screens.WIDTH, Screens.HEIGHT, true, true);
			super.setTitle("<font size=\"12\" face=\"Verdana\" color=\"#000000\">设置敌人士兵</font>");
			
			
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
		
		
//		--------------------------------------------------------------------------------------------------------------------------
//		battle start
//		--------------------------------------------------------------------------------------------------------------------------
		
		public static var forceB_soldiers 	: SoldierDatas;
		
		public static var forceB_skills 	: SkillDatas;
		
		
		
		override protected function onMouseClickOK(e:MouseEvent) : void
		{
			
			if (soldierSel.getSlotCardsCount())
			{
				forceB_soldiers = new SoldierDatas(soldierSel.getSlotCardsData());
				forceB_skills	= new SkillDatas(skillSel.getSlotCardsData());
				this.removeFromParent();
				
				Screens.getRoot().getCurrentScreen().addChild(
					new FormReadyForBattle(BattleStartRequest.TYPE_TEST, null, 0));	
				
			} else {
				Alert.showAlertText("没有携带敌军士兵！", "无法开始战斗", false, true);
			}
			
		}
	}
}




