package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.util.Map;
	import com.fc.castle.data.message.Messages.BuyShopItemRequest;
	import com.fc.castle.data.message.Messages.BuyShopItemResponse;
	import com.fc.castle.data.message.Messages.GetShopItemRequest;
	import com.fc.castle.data.message.Messages.GetShopItemResponse;
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
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	public class FormShop extends BaseDialogForm
	{
		private var cardPan : CardPanel;
		
		private var _shop_items : GetShopItemResponse;
		
		public function FormShop()
		{
			super(Screens.WIDTH, Screens.HEIGHT, true);
			super.setTitle(LanguageManager.getText("ui.shop.title"));

			shop_refresh();
		
		}
		
		
		protected function shop_refresh(e:Event=null) : void
		{
			Screens.client.sendRequest(new GetShopItemRequest(), shop_complete, data_error);
		}
		
		protected function shop_complete(e:CClientEvent) : void
		{
			_shop_items = e.response as GetShopItemResponse;
			if (_shop_items.result == GetShopItemResponse.RESULT_SUCCEED) {
				data_refresh();
			}
		}
		
		
		protected function data_refresh(e:Event=null) : void
		{
			DataManager.getShopItemTemplates(
				_shop_items.items.datas, 
				data_complete,
				data_error);
		}
		
		protected function data_complete(map:Map) : void
		{			
			cardPan = new CardPanel(width-40, height-120, 
				CLayoutManager.SOLDIER_BTN_W,
				CLayoutManager.SOLDIER_BTN_H, 
				_shop_items.items.datas);
			
			for each (var card:Card in cardPan.getCards()) {
				card.addEventListener(MouseEvent.CLICK, onMouseDownCard);
			}
			cardPan.setEnableScroll(false, true);
			cardPan.x = 20;
			cardPan.y = 30;
			addChild(cardPan);
			
			
		}
		
		protected function data_error(e:Event=null) : void
		{
			CAlert.showErrorNetwork();
			//.btnOK.addEventListener(MouseEvent.CLICK, data_refresh);
		}
		
		//		------------------------------------------------------------------
		
		private function onMouseDownCard(e:MouseEvent) : void
		{
			var card : Card = e.currentTarget as Card;
			
			BaseDialogForm.show(new BuyItemDialog(card));
		}
		
		
	}

}
import com.cell.ui.component.Alert;
import com.fc.castle.data.message.Messages.BuyShopItemRequest;
import com.fc.castle.data.message.Messages.BuyShopItemResponse;
import com.fc.castle.data.template.ItemTemplate;
import com.fc.castle.gfx.CAlert;
import com.fc.castle.gfx.LoadingManager;
import com.fc.castle.net.client.CClientEvent;
import com.fc.castle.screens.Screens;
import com.fc.castle.ui.BaseDialogForm;
import com.fc.castle.ui.card.Card;
import com.fc.castle.ui.demo.ItemInfo;
import com.fc.castle.ui.card.ItemDetailPan;
import com.fc.castle.ui.card.SyncCardInfoPan;
import com.fc.castlecraft.LanguageManager;

import flash.events.MouseEvent;

class BuyItemDialog extends BaseDialogForm
{
	private var card : Card;
	
	
	private var sit : ItemTemplate;
	
	public function BuyItemDialog(card:Card)
	{
		super(500, 400, true, true);
		super.setTitle(LanguageManager.getText("ui.shop.buydialog.title"));
		
		this.card = card;
		
		this.sit = card.template as ItemTemplate;
	
		var card_info : ItemDetailPan = new ItemDetailPan(sit, 460, 300);
		card_info.x = 20;
		card_info.y = 30;
		//card_info.setSize(180, height-110);
		addChild(card_info);
		
	}
	
	override protected function onMouseClickOK(e:MouseEvent):void
	{
		LoadingManager.showRoot();
		Screens.client.sendRequest(
			new BuyShopItemRequest(Screens.client.getPlayerID(), card.getIndex()), 
			onBuySucceed, 
			onBuyError);
	}
	
	private function onBuySucceed(e:CClientEvent) : void
	{
		LoadingManager.close();
		var req : BuyShopItemRequest  = e.request  as BuyShopItemRequest;
		var res : BuyShopItemResponse = e.response as BuyShopItemResponse;
		if (res.result == BuyShopItemResponse.RESULT_SUCCEED) {
			Alert.showAlertText("道具已购买","成功");
			this.removeFromParent();
		}
		else if (res.result == BuyShopItemResponse.RESULT_NEED_MORE_COIN) {
			Alert.showAlertText("没有足够金币","错误");
		}
		else {
			Alert.showAlertText("错误","错误");
		}
	}
	
	private function onBuyError(e:CClientEvent) : void
	{
		LoadingManager.close();
		CAlert.showErrorNetwork();
	}
}