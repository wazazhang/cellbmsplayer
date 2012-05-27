package com.fc.castle.ui.mail
{
	import com.cell.gfx.CellSprite;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Panel;
	import com.fc.castle.data.message.Messages.GetMailRequest;
	import com.fc.castle.data.message.Messages.GetMailResponse;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.screens.Screens;
	
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.text.TextField;
	
	import mx.controls.Text;
	import mx.states.AddChild;
	
	public class MailDetail extends Panel
	{
		private var label_sender:TextField = new TextField();
		private var label_title:TextField = new TextField();
		private var label_content:TextField = new TextField();
		private var text_sender:TextField = new TextField();
		private var text_title:TextField = new TextField();
		private var text_content:TextField = new TextField();
		
		public function MailDetail(width:Number=380,height:Number = 350)
		{
			super(width,height);	
			
			
			label_sender.x = 10;
			label_sender.y = 10;
			label_sender.text = "发送者:";
			label_sender.selectable = false;
			label_sender.textColor = 0xffffff;
			this.addPanelChild(label_sender);
			
			label_title.x = 10;
			label_title.y = 40;
			label_title.text = "标题:";
			label_title.selectable = false;
			label_title.textColor = 0xffffff;
			this.addPanelChild(label_title);
			
			label_content.x = 10;
			label_content.y = 70;
			label_content.text = "正文:";
			label_content.selectable = false;
			label_content.textColor = 0xffffff;
			this.addPanelChild(label_content);
			
			text_sender.x = 55;
			text_sender.y = 10;
			text_sender.text = "loading...";
			text_sender.textColor = 0xffffff;
			text_sender.width = width - 85;
			
			this.addPanelChild(text_sender);
			
			
			text_title.x = 55;
			text_title.y = 40;
			text_title.text = "loading..."
			text_title.textColor = 0xffffff;
			text_title.width = width - 85;
			
			this.addPanelChild(text_title);
			
			text_content.x = 55;
			text_content.y = 70;
			text_content.text = "loading..."
			text_content.height = 300;
			text_content.width = width - 85;
			text_content.wordWrap = true;
//			/text_content.border = true;
			text_content.multiline = true;
			text_content.textColor = 0xffffff;
			//this.addPanelChild(text_content);
		}
		
		public function setSender(str:String):void
		{
			text_sender.text = str;
		}
		
		public function setTitle(str:String):void
		{
			text_title.text = str;
		}
		
		public function setContent(str:String):void
		{
			text_content.text = str;
			text_content.height = text_content.textHeight + 10;
			//updateScroll(null);
			this.addPanelChild(text_content);
			trace("textHeight:"+ text_content.textHeight);
		}	
		
		
		public function load(mailID:int):void
		{
			var req:GetMailRequest = new GetMailRequest(Screens.client.getPlayerID(),mailID)
			Screens.client.sendRequest(req,getSuccess,getFailed);
		}
		
		private function getSuccess(e:CClientEvent):void
		{
			var res:GetMailResponse = e.response as GetMailResponse;
			if(res.result == GetMailResponse.RESULT_SUCCEED)
			{
				setSender(res.mail.senderName);
				setTitle(res.mail.title);
				setContent(res.mail.content);
			}
			else if(res.result == GetMailResponse.RESULT_FAILED_VALIDATE)
			{
				Alert.showAlertText("无效的邮件");
			}
			else if(res.result == GetMailResponse.RESULT_FAILED_UNKNOW)
			{
				Alert.showAlertText("未知的错误");	
			}
		}
		private function getFailed(e:CClientEvent):void
		{
			Alert.showAlertText("获取文章失败.");	
		}
	}
}