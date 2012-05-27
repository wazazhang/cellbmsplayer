package com.fc.castle.ui
{
	import com.cell.gfx.game.CSprite;
	import com.cell.gfx.game.worldcraft.CellCSpriteGraphics;
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.Panel;
	import com.cell.ui.component.TextBox;
	import com.cell.util.ImageUtil;
	import com.cell.util.Map;
	import com.fc.castle.data.SkillData;
	import com.fc.castle.data.SkillDatas;
	import com.fc.castle.data.SoldierData;
	import com.fc.castle.data.SoldierDatas;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.GetExploreStateResponse;
	import com.fc.castle.data.template.EventTemplate;
	import com.fc.castle.data.template.UnitTemplate;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardPanel;
	import com.fc.castle.ui.card.CardSelectPanel;
	import com.fc.castle.ui.card.SoldierDetailPan;
	import com.fc.castlecraft.LanguageManager;
	import com.smartfoxserver.v2.core.PacketHeader;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.ui.Mouse;

	public class FormExploerData extends BaseDialogForm
	{
		private var _res:GetExploreStateResponse
		
		private var eventTemplate:EventTemplate;
		
		private var playTempPanel:SoldierDetailPan;
		
		private var unitName:String;
		
		private var spr : CellCSpriteGraphics;
		
		private var targetPlayerID : int;
		
		public function FormExploerData(res:GetExploreStateResponse, unitName:String, cspr:CSprite, targetPlayerID:int)
		{
			super(650, 450, true, true);

			this._res = res;
			this.eventTemplate = res.event;
			this.unitName = unitName;
			this.targetPlayerID = targetPlayerID;
			
			var name:Lable = new Lable(this.eventTemplate.name);
			name.width = 300;
			name.height = 50;
			name.y = 30;
			name.x = 30;
			name.mouseEnabled = false;
			addChild(name);
			
			var discrpt:TextBox = new TextBox();
			discrpt.setText(this.eventTemplate.desc);
			discrpt.width = 300;
			discrpt.height = 100;
			discrpt.mouseEnabled = false;
			discrpt.y = 280;
			discrpt.x = 30;
			addChild(discrpt);
			
			spr = new CellCSpriteGraphics(cspr);
			spr.y = 180;
			spr.x = 30 + 140;
			addChild(spr);
			
			var tip:TextField = new TextField();
			var t:String = LanguageManager.getText("battleEnter.text");
			if (t!=null){
				tip.htmlText = t;
			}else{
				tip.htmlText = "";
			}
			tip.height = 20;
			tip.width = this.width;
			tip.y = this.height - 50;
			tip.mouseEnabled = false;
			addChild(tip);
			
			
			
			data_refresh();

		}
		
		private function data_refresh():void
		{
			DataManager.getUnitTemplates(
				eventTemplate.soldiers.datas, 
				init,
				error);
		}
		
		private function init(map:Map):void
		{
			var cardPanel:CardPanel = new CardPanel(280, 90,
				CLayoutManager.SOLDIER_BTN_W,
				CLayoutManager.SOLDIER_BTN_H, 
				this.eventTemplate.soldiers.datas
			)
			cardPanel.x = 340;	
			cardPanel.y = 290;	
			addChild(cardPanel);
			for each (var card:Card in cardPanel.getCards()) {
				card.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDownSrcCard);
			}
			
			playTempPanel = new SoldierDetailPan(card.template.type, 280, 260);
			playTempPanel.x = 340;	
			playTempPanel.y = 20;	
			addChild(playTempPanel);
		}
		
		private function onMouseDownSrcCard(e:MouseEvent) : void
		{
			var card : Card = e.currentTarget as Card;
			playTempPanel.setSoldier(card.template.type);
		}
		
		
		private function error(event:Event):void
		{
			Alert.showAlertText("出错");
		}
		
		override protected function update(e:Event):void
		{
			super.update(e);
			
			spr.nextCycFrame();
			spr.renderSelf();
		}
		
		override protected function onMouseClickOK(e:MouseEvent):void
		{
			parent.addChild(new FormReadyForBattle(BattleStartRequest.TYPE_EXPLORE, unitName, targetPlayerID));
		}
		
		
		
		
	}
}