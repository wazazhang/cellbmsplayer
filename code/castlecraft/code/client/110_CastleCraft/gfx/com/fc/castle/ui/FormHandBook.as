package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.util.Map;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardPanel;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castle.ui.card.SoldierDetailPan;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class FormHandBook extends BaseDialogForm
	{
		private var soldierSel 	:CardPanel;
		
		private var soldierPanel :SoldierDetailPan;
		
		public function FormHandBook()
		{
			super(Screens.WIDTH, Screens.HEIGHT, true, false);
			super.setTitle("<font size=\"12\" face=\"Verdana\" color=\"#000000\">图鉴</font>");
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
			/*	initSkills();
				
				loadStep ++;
				_ok = UILayoutManager.getInstance().alertCreateOK();
				_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
				_ok.addEventListener(MouseEvent.CLICK, onMouseClickOK);
				_ok.x = width -  _ok.width/2  - 20;
				_ok.y = height - _ok.height/2 - 20;
				this.addChild(_ok);*/
			}
			
		}
		
		protected function data_error() : void
		{
			Alert.showAlertText("网络超时","错误").btnOK.addEventListener(MouseEvent.CLICK, data_refresh);
		}
		
		//		--------------------------------------------------------------------------------------------------------------------------
		//		--------------------------------------------------------------------------------------------------------------------------
		
		//		------------------------------------------------------------------------------------------------------
		//		士兵配给
		//		------------------------------------------------------------------------------------------------------
		
		private function initSoldiers() : void
		{
			//soldierSel = new CardPanel(340, 340,
			
			soldierSel = new CardPanel(524, 350, 
				CLayoutManager.SOLDIER_BTN_W,
				CLayoutManager.SOLDIER_BTN_H, 
				Screens.client.getPlayer().soldiers.datas
			);
			soldierSel.x = 20;
			soldierSel.y = 40;
			addChild(soldierSel);
			for each (var card:Card in soldierSel.getCards()) {
				card.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDownSrcCard);
			}
			
			soldierPanel = new SoldierDetailPan(card.template.type, 226, 350);
			soldierPanel.x = 560;
			soldierPanel.y = 40;
			addChild(soldierPanel);
		}
		
		private function onMouseDownSrcCard(e:MouseEvent) : void
		{
			var card : Card = e.currentTarget as Card;
			soldierPanel.setSoldier(card.template.type);
		}
		
		override protected function onMouseClickCancel(e:MouseEvent):void
		{
			removeFromParent();
		}
		
	}
}
