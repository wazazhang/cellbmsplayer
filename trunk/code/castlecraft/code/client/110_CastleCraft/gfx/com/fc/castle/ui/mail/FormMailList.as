package com.fc.castle.ui.mail
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.ListView;
	import com.cell.ui.component.listview.DefaultListViewItem;
	import com.cell.util.ImageUtil;
	import com.fc.castle.data.MailSnap;
	import com.fc.castle.data.message.Messages.GetMailSnapsRequest;
	import com.fc.castle.data.message.Messages.GetMailSnapsResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	import com.fc.castle.ui.BaseForm;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class FormMailList extends BaseForm
	{		
		private var _ok : ImageButton;
		private var send:ImageButton;
		private var returnBtn:ImageButton;
		private var mailDetail:MailDetail;
		private var list : ListView;
		
		public function FormMailList()
		{
			super(600, Screens.HEIGHT);
		
			
			
			_ok = CLayoutManager.alertCreateOK();
			_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_ok.addEventListener(MouseEvent.CLICK, onClose);
			_ok.x = width -  _ok.width/2  - 20;
			_ok.y = height - _ok.height/2 - 20;
			this.addChild(_ok);
			
			

			returnBtn = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_btn_op_return).bitmapData);
			returnBtn.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			returnBtn.x = returnBtn.width*3/2  + 40;
			returnBtn.y = height - returnBtn.height/2 - 20;
			returnBtn.addEventListener(MouseEvent.CLICK, returnHandle);
			returnBtn.visible = false;
			addChild(returnBtn);
			
			
			send = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_btn_send).bitmapData);
			send.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			send.x = send.width/2  + 20;
			send.y = height - send.height/2 - 20;
			send.addEventListener(MouseEvent.CLICK, sendHandle);
			addChild(send);
			
			
			var req:GetMailSnapsRequest = new GetMailSnapsRequest();
			Screens.client.sendRequest(req, onMailSuccess, onMailFailed);
		}
		
		override protected function added(e:Event):void
		{
			super.added(e);
			setCenter(parent);
		}
		
		
		private function onClose(e:MouseEvent) : void
		{
			removeFromParent();
		}
		
		private function onMailSuccess(event:CClientEvent):void
		{
			var res:GetMailSnapsResponse = (event.response as GetMailSnapsResponse)
				
			if(res.result == GetMailSnapsResponse.RESULT_SUCCEED)
			{
				initMailList(res);
			}
			else
			{
				Alert.showAlertText("发生未知错误");
			}
		}
		
		private function onMailFailed(event:CClientEvent):void
		{
			Alert.showAlertText("获取失败");
		}
		
		protected function initMailList(res:GetMailSnapsResponse) : void
		{
			list = new ListView(width-40, height-100);
			list.x = 20;
			list.y = 20;
			list.addColumn("日期",  	0.25);
			list.addColumn("发件人", 	0.25);
			list.addColumn("标题", 	0.50);
			var i:int = 0;
			for each(var mailSnap:MailSnap in res.mailSnaps.mails)
			{
				var item : MailItem = new MailItem(mailSnap);
				item.setSize(list.getBaseWidth(), 40);
				item.addEventListener(MouseEvent.CLICK,clickHandle);
				list.addItem(item);
				i++;
			}
			this.addChild(list);
		}
		
		private function sendHandle(event:Event):void
		{
			parent.addChild(new SendMailBox());
		}
		
		private function toMailDetail(id:int):void
		{
			mailDetail = new MailDetail(width-40, height-100);
			mailDetail.x = 20;
			mailDetail.y = 20;
			addChild(mailDetail);
		//	mailDetail.setCenter(this);

			mailDetail.load(id);
			list.visible = false;
			returnBtn.visible = true;
		}
		
		private function clickHandle(event:MouseEvent):void
		{
			var mail:MailItem = event.target as MailItem;
			toMailDetail(mail.mailSnap.id);
			//Alert.showAlertText("ID:"+text.mailID)	;
		}
		
		
		private function returnHandle(event:Event):void
		{
			list.visible = true;
			returnBtn.visible = false;
			removeChild(mailDetail);
		}
	}
}

import com.cell.ui.component.Lable;
import com.cell.ui.component.listview.DefaultListViewItem;
import com.cell.ui.layout.UIRect;
import com.fc.castle.data.MailSnap;
import com.fc.castle.res.CLayoutManager;

class MailItem extends DefaultListViewItem
{
	public var mailSnap:MailSnap;
	
	public function MailItem(mailSnap:MailSnap)
	{
		super(CLayoutManager.ui_lable_mail.copy());
		
		this.mailSnap = mailSnap;
		mouseEnabled = true;
	
		this.setColumnItems([
			new Lable(mailSnap.id+""),
			new Lable(mailSnap.senderPlayerName),
			new Lable(mailSnap.title),
		]);
	}
}
