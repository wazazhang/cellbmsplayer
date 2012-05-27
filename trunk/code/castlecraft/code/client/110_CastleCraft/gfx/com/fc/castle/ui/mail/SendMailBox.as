package com.fc.castle.ui.mail
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.TextInput;
	import com.cell.ui.layout.UILayoutManager;
	import com.cell.util.StringUtil;
	import com.fc.castle.data.Mail;
	import com.fc.castle.data.message.Messages.SendMailRequest;
	import com.fc.castle.data.message.Messages.SendMailResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.net.client.ClientEvent;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	import mx.controls.Text;
	import com.fc.castle.ui.BaseForm;

	public class SendMailBox extends BaseForm
	{
		
		public static const WIDTH:int = 380;
		
		private var text_send:TextInput;
		private var label_send:TextField;
		
		private var label_title:TextField;
		private var text_title:TextInput;
		
		private var label_content:TextField;
		private var text_content:TextInput;
		
		
		private var _ok : ImageButton; 
		private var _exit: ImageButton;
		
		public function SendMailBox()
		{
			super(WIDTH, Screens.HEIGHT);	
			this.x = (Screens.WIDTH - this.width)*0.5;
			this.y = (Screens.HEIGHT - this.height)*0.5;

			
		}
		
		override protected function added(e:Event):void
		{
			//setCenter(parent);
			
			text_send =  new TextInput("");
			text_send.x = 70;
			text_send.y = 40;
			text_send.width = 200;
			addChild(text_send);
			
			label_send = new TextField();
			label_send.text = "收件人:";
			label_send.x = 20;
			label_send.y = 40;
			label_send.mouseEnabled = false;
			addChild(label_send);
			
			text_title =  new TextInput("");
			text_title.x = 70;
			text_title.y = 70;
			text_title.width = 200;
			text_title.mouseEnabled = false;
			addChild(text_title);
			
			label_title = new TextField();
			label_title.text = "主题:";
			label_title.x = 20;
			label_title.y = 70;
			label_title.mouseEnabled =false;
			addChild(label_title);
			
			label_content = new TextField();
			label_content.text = "正文:";
			label_content.x = 20;
			label_content.y = 100;
			label_content.mouseEnabled =false;
			addChild(label_content);
			
			
			text_content =  new TextInput("");
			text_content.x = 70;
			text_content.y = 100;
			text_content.width = 260;
			text_content.height = 280;
			text_content.getTextField().wordWrap = true;
			text_content.getTextField().multiline = true;
			addChild(text_content);
			
			_ok = CLayoutManager.alertCreateOK();
			_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_ok.addEventListener(MouseEvent.CLICK, onMouseClickOK);
			_ok.x = width -  _ok.width/2  - 20;
			_ok.y = height - _ok.height/2 - 20;
			this.addChild(_ok);
			
			_exit = CLayoutManager.alertCreateCancel();
			_exit.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_exit.addEventListener(MouseEvent.CLICK, exit);
			_exit.x =   _exit.width/2  + 20;
			_exit.y = height - _exit.height/2 - 20;
			this.addChild(_exit);
		}
		
		private function onMouseClickOK(e:MouseEvent) : void
		{
			if (e.currentTarget == _ok)
			{
				sendMail();
			}
		}
		
		private function sendMail():void
		{
			
			if(StringUtil.trim(text_send.getText()).length == 0)
			{
				com.cell.ui.component.Alert.showAlertText("请输入收件人名子");
				return;
			}
			
			if(StringUtil.trim(text_title.getText()).length == 0)
			{
				com.cell.ui.component.Alert.showAlertText("请输入邮件标题");
				return;
			}
			
			if(StringUtil.trim(text_content.getText()).length == 0)
			{
				com.cell.ui.component.Alert.showAlertText("请输入邮件正文");
				return;
			}
			
			var mail:Mail 		= new Mail();
			mail.content 		= text_content.getText();
			mail.title 			= text_title.getText();
			mail.receiverName	= StringUtil.trim(text_send.getText());
			mail.senderName		= Screens.client.getPlayer().player_name;
			
			/////////////////////
			mail.receiverPlayerID = Screens.client.getPlayer().player_id;
			mail.senderPlayerID = Screens.client.getPlayer().player_id;
			///////////////////////
			
			_ok.mouseEnabled = false;
			var sendReq:SendMailRequest = new SendMailRequest(Screens.client.getPlayer().player_id,mail);
			Screens.client.sendRequest(sendReq,sendMailComplate,error);
		}
		
		private function sendMailComplate(e:CClientEvent):void
		{
			var rst:SendMailResponse = (e.response as SendMailResponse);
			
			if(rst.result == SendMailResponse.RESULT_SUCCEED)
			{
				removeFromParent();
				com.cell.ui.component.Alert.showAlertText("发送成功!");
			}	
			else if(rst.result == SendMailResponse.RESULT_FAILED_UNKNOW)
			{
				_ok.mouseEnabled = true;
				com.cell.ui.component.Alert.showAlertText("发生未知错误!");
			}
			else if(rst.result == SendMailResponse.RESULT_FAILED_RECEIVER_IS_NULL)
			{
				_ok.mouseEnabled = true;
				com.cell.ui.component.Alert.showAlertText("没有找到收件人!");
			}
		}
		
		private function error(e:CClientEvent):void
		{
			_ok.mouseEnabled = true;
			com.cell.ui.component.Alert.showAlertText("发送失败!");
		}
		
		private function exit(e:Event):void
		{
			removeFromParent();
		}
		
		
		
	}
}