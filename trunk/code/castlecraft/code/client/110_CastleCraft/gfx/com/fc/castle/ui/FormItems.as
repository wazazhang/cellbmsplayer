package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.util.Map;
	import com.fc.castle.data.message.Messages.UseItemRequest;
	import com.fc.castle.data.message.Messages.UseItemResponse;
	import com.fc.castle.gfx.CAlert;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.DataManager;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.card.Card;
	import com.fc.castle.ui.card.CardPanel;
	import com.fc.castlecraft.LanguageManager;
	import com.net.client.ClientEvent;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class FormItems extends BaseForm
	{
		private var cardPan : CardPanel;
		
		private var _ok 	: ImageButton;
		
		public function FormItems()
		{
			super(Screens.WIDTH, Screens.HEIGHT);
			super.setTitle(LanguageManager.getText("ui.items.title"));
		
			data_refresh();
		}
		
		protected function data_refresh(e:Event=null) : void
		{
			DataManager.getItemTemplates(
				Screens.client.getPlayer().items.datas, 
				data_complete,
				data_error);
		}
		
		protected function data_complete(map:Map) : void
		{			
			cardPan = new CardPanel(width-40, height-120, 
				CLayoutManager.SOLDIER_BTN_W,
				CLayoutManager.SOLDIER_BTN_H, 
				Screens.client.getPlayer().items.datas);
			
			for each (var card:Card in cardPan.getCards()) {
				card.addEventListener(MouseEvent.CLICK, onMouseDownCard);
			}
			cardPan.setEnableScroll(false, true);
			cardPan.x = 20;
			cardPan.y = 30;
			addChild(cardPan);
			
			_ok = CLayoutManager.alertCreateOK();
			_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_ok.addEventListener(MouseEvent.CLICK, onMouseClickOK);
			_ok.x = width -  _ok.width/2  - 20;
			_ok.y = height - _ok.height/2 - 20;
			this.addChild(_ok);
			
		}
		
		protected function data_error() : void
		{
			CAlert.showErrorNetwork();
			//.btnOK.addEventListener(MouseEvent.CLICK, data_refresh);
		}
		
		private function onMouseClickOK(e:Event) : void
		{
			removeFromParent();
		}
		
//		------------------------------------------------------------------
		
		private function onMouseDownCard(e:MouseEvent) : void
		{
			var card : Card = e.currentTarget as Card;
			
			BaseDialogForm.show(new UseItemDialog(card, cardPan));
			
		}
	
	}
}

import com.cell.ui.component.Alert;
import com.fc.castle.data.message.Messages.UseItemRequest;
import com.fc.castle.data.message.Messages.UseItemResponse;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.gfx.CAlert;
import com.fc.castle.gfx.LoadingManager;
import com.fc.castle.net.client.CClientEvent;
import com.fc.castle.screens.Screens;
import com.fc.castle.ui.BaseDialogForm;
import com.fc.castle.ui.card.Card;
import com.fc.castle.ui.card.CardPanel;
import com.fc.castle.ui.card.ItemDetailPan;
import com.fc.castlecraft.LanguageManager;

import flash.events.MouseEvent;

class UseItemDialog extends BaseDialogForm
{
	private var card : Card;
	
	private var card_pan : CardPanel;
	
	private var sit : ItemTemplate;
		
	public function UseItemDialog(card:Card, card_pan:CardPanel)
	{
		super(500, 400, true, true);
		super.setTitle(LanguageManager.getText("ui.items.usedialog.title"));
		
		this.card = card;
		this.card_pan = card_pan;
		
		this.sit = card.template as ItemTemplate;
		
		var card_info : ItemDetailPan = new ItemDetailPan(sit, 460, 300);
		card_info.x = 20;
		card_info.y = 30;
		addChild(card_info);
	}
	
	override protected function onMouseClickOK(e:MouseEvent):void
	{
		LoadingManager.showRoot();
		
		Screens.client.sendRequest(
			new UseItemRequest(Screens.client.getPlayerID(), card.getIndex(), 1),
			onUseSucceed,
			onUseError);
	}
	
	
	private function onUseSucceed(e:CClientEvent) : void
	{
		LoadingManager.close();
		var req : UseItemRequest  = e.request  as UseItemRequest;
		var res : UseItemResponse = e.response as UseItemResponse;
		if (res.result == UseItemResponse.RESULT_SUCCEED) {
			Alert.showAlertText("道具已使用","成功");
			card_pan.getCard(req.indexOfItems).reset(res.item_slot);
			Screens.client.refreshPlayerData();
			removeFromParent();
		}
		else if (res.result == UseItemResponse.RESULT_FAILED_NOT_ENOUGH) {
			Alert.showAlertText("数量错误","错误");
		}
		else if (res.result == UseItemResponse.RESULT_FAILED) {
			Alert.showAlertText("道具使用错误","错误");
		}
	}
	
	private function onUseError(e:CClientEvent) : void
	{
		LoadingManager.close();
		CAlert.showErrorNetwork();
	}
}