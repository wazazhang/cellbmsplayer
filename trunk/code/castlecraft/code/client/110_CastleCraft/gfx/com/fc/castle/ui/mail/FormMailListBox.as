package com.fc.castle.ui.mail
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Panel;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.util.ImageUtil;
	import com.fc.castle.data.MailSnap;
	import com.fc.castle.data.message.Messages.GetMailResponse;
	import com.fc.castle.data.message.Messages.GetMailSnapsRequest;
	import com.fc.castle.data.message.Messages.GetMailSnapsResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.Res;
	import com.fc.castle.screens.Screens;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.AntiAliasType;
	import flash.text.GridFitType;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import com.fc.castle.ui.BaseForm;
	
	public class FormMailListBox extends BaseForm
	{
		public static const WIDTH:int = 420;
		private var listPanel:Panel;
		
		private var close:ImageButton;
		private var send:ImageButton;
		private var returnBtn:ImageButton;
		private var mailDetail:MailDetail;
		public function FormMailListBox()
		{
			super(WIDTH, Screens.HEIGHT);
			this.x = (Screens.WIDTH - this.width)*0.5;
			this.y = (Screens.HEIGHT - this.height)*0.5;
		}
		
		protected override function added(e:Event):void
		{
			listPanel = new Panel()
			listPanel.width = 380;
			listPanel.height = 300;
			addChild(listPanel);
			listPanel.setCenter(this);
			
			
			//UILayoutManager.getInstance().
			close = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_bth_close).bitmapData);
			close.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			close.x = width -  close.width/2  - 20;
			close.y = height - close.height/2 - 20;
			addChild(close);
			
			send = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_btn_send).bitmapData);
			send.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			send.x = send.width/2  + 20;
			send.y = height - send.height/2 - 20;
			send.addEventListener(MouseEvent.CLICK, sendHandle);
			addChild(send);
			
			returnBtn = com.cell.ui.ImageButton.createImageButtonScaleBitmap(
				ImageUtil.combineImageClass(Anchor.ANCHOR_CENTER, Res.ui_btn_menu, Res.ui_btn_op_return).bitmapData);
			returnBtn.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			returnBtn.x = returnBtn.width*3/2  + 40;
			returnBtn.y = height - send.height/2 - 20;
			returnBtn.addEventListener(MouseEvent.CLICK, returnHandle);
			returnBtn.visible = false;
			addChild(returnBtn);
			
			
			var req:GetMailSnapsRequest = new GetMailSnapsRequest();
			Screens.client.sendRequest(req,getMailSuccess);
		}
		
		private function getMailSuccess(event:CClientEvent):void
		{
			close.addEventListener(MouseEvent.CLICK, closeHandle);
			var res:GetMailSnapsResponse = (event.response as GetMailSnapsResponse)
			
			if(res.result == GetMailSnapsResponse.RESULT_SUCCEED)
			{
				var top:int = 20;
				var i:int = 0;
				for each(var mailSnap:MailSnap in res.mailSnaps.mails)
				{
					var text:mailText = new mailText()
					text.text = mailSnap.title   + " 来自:"+ mailSnap.senderPlayerName + (mailSnap.readed?"":"(未读)") ;
					text.mailID = mailSnap.id;
					text.y = top + i*26;
					text.x = 20;
					text.width = 300;
					text.setTextFormat(new TextFormat(null,12,0xffffff));
					text.addEventListener(MouseEvent.CLICK,click);
					text.selectable = false;
					listPanel.addChild(text);
					i++;
				}
			}
			else if((res.result == GetMailSnapsResponse.RESULT_FAILED_UNKNOW))
			{
				Alert.showAlertText("发生未知错误");
			}
			
		}
		
		private function getMailFailed(event:CClientEvent):void
		{
			close.addEventListener(MouseEvent.CLICK, closeHandle);
			Alert.showAlertText("获取失败");
		}
		
		private function click(event:MouseEvent):void
		{
			var text:mailText = event.target as mailText;
			toMailDetail(text.mailID);
			//Alert.showAlertText("ID:"+text.mailID)	;
		}
		
		private function closeHandle(event:Event):void
		{
			this.removeFromParent();
		}
		
		private function sendHandle(event:Event):void
		{
			parent.addChild(new SendMailBox());
		}
		
		private function toMailDetail(id:int):void
		{
			mailDetail = new MailDetail();
			addChild(mailDetail);
			mailDetail.setCenter(this);
			mailDetail.y = 20;
			
			mailDetail.load(id);
			listPanel.visible = false;
			returnBtn.visible = true;
		}
		
		
		private function returnHandle(event:Event):void
		{
			listPanel.visible = true;
			returnBtn.visible = false;
			removeChild(mailDetail);
		}
	}	
}

import flash.text.TextField;
class mailText extends TextField
{
	public var mailID:int;
	public function mailText()
	{
		super();
	}
	
}