package com.fc.castle.ui
{
	import com.cell.ui.Anchor;
	import com.cell.ui.ImageButton;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Lable;
	import com.cell.ui.component.Pan;
	import com.cell.ui.component.TextBox;
	import com.cell.ui.component.TextInput;
	import com.cell.ui.layout.UILayoutManager;
	import com.fc.castle.data.message.Messages.LoginRequest;
	import com.fc.castle.data.message.Messages.LoginResponse;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.CLayoutManager;
	import com.fc.castle.screens.Screens;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.SharedObject;
	

	public class FormLogin extends BaseForm
	{
		private var alert 			: Alert;
		
		private var lbl_server_addr	: Lable;
		private var lbl_res_addr 	: Lable;
		private var lbl_user	 	: Lable;
		private var lbl_pswd	 	: Lable;
		
		private var txt_server_addr	: TextInput;
		private var txt_res_addr 	: TextInput;
		private var txt_user	 	: TextInput;
		private var txt_pswd	 	: TextInput;
		
		private var _ok 			: ImageButton;
		
		public function FormLogin()
		{
			super(500, 300);
		
			super.setTitle("<font size=\"12\" face=\"Verdana\" color=\"#000000\">测试登录</font>");
			
			var sh : int = 34;
			
			this.lbl_server_addr	= new Lable("<font size=\"12\" face=\"Verdana\" color=\"#000000\">ServerAddr</font>");
			this.lbl_res_addr 		= new Lable("<font size=\"12\" face=\"Verdana\" color=\"#000000\">ResRoot</font>");
			this.lbl_user	 		= new Lable("<font size=\"12\" face=\"Verdana\" color=\"#000000\">User</font>");
			this.lbl_pswd	 		= new Lable("<font size=\"12\" face=\"Verdana\" color=\"#000000\">Password</font>");
			
			this.lbl_server_addr	.setLocation(20, 40 + sh*0);
			this.lbl_res_addr		.setLocation(20, 40 + sh*1);
			this.lbl_user			.setLocation(20, 40 + sh*2);
			this.lbl_pswd			.setLocation(20, 40 + sh*3);
			
			this.lbl_server_addr	.textAnchor = Anchor.ANCHOR_VCENTER | Anchor.ANCHOR_RIGHT;
			this.lbl_res_addr		.textAnchor = Anchor.ANCHOR_VCENTER | Anchor.ANCHOR_RIGHT;
			this.lbl_user			.textAnchor = Anchor.ANCHOR_VCENTER | Anchor.ANCHOR_RIGHT;
			this.lbl_pswd			.textAnchor = Anchor.ANCHOR_VCENTER | Anchor.ANCHOR_RIGHT;
			
			addChild(lbl_server_addr);
			addChild(lbl_res_addr);
			addChild(lbl_user);
			addChild(lbl_pswd);
			
			this.txt_server_addr	= new TextInput(AutoLogin.SERVER_URL);
			this.txt_res_addr 		= new TextInput(AutoLogin.RES_ROOT);
			this.txt_user	 		= new TextInput(AutoLogin.LOGIN_USER);
			this.txt_pswd	 		= new TextInput(AutoLogin.LOGIN_PSWD);
			
			this.txt_pswd			.getTextField().displayAsPassword = true;
			
			
			this.txt_server_addr	.setSize(300,  txt_server_addr.height);  
			this.txt_res_addr		.setSize(300,  txt_server_addr.height);  
			this.txt_user			.setSize(300,  txt_server_addr.height);  
			this.txt_pswd			.setSize(300,  txt_server_addr.height);  
			
			this.txt_server_addr	.setLocation(130,  40 + sh*0);  
			this.txt_res_addr		.setLocation(130,  40 + sh*1);  
			this.txt_user			.setLocation(130,  40 + sh*2);  
			this.txt_pswd			.setLocation(130,  40 + sh*3);  
			                                                        
			addChild(txt_server_addr);
			addChild(txt_res_addr);
			addChild(txt_user);
			addChild(txt_pswd);

			_ok = CLayoutManager.alertCreateOK();
			_ok.anchor = Anchor.ANCHOR_HCENTER | Anchor.ANCHOR_VCENTER;
			_ok.addEventListener(MouseEvent.CLICK, onMouseClick);
			_ok.x = width -  _ok.width/2  - 20;
			_ok.y = height - _ok.height/2 - 20;
			this.addChild(_ok);
			
			try {
				var saved : SharedObject = SharedObject.getLocal("login.username");
				if (saved != null) {
					txt_user.setText(saved.data.username);
				}
			} catch (e:Error) {}
		}
		
		override protected function added(e:Event):void
		{
		}
		
		override protected function removed(e:Event):void
		{
			
		}
		
		
		private function onMouseClick(e:MouseEvent) : void
		{
			if (e.currentTarget == _ok)
			{
				startLogin();
			} 
		}
		
		private function startLogin() : void
		{
			AutoLogin.SERVER_URL	= txt_server_addr.getText();
			AutoLogin.RES_ROOT		= txt_res_addr.getText();
			AutoLogin.LOGIN_USER	= txt_user.getText();
			AutoLogin.LOGIN_PSWD	= txt_pswd.getText();
//			Screens.platform.login(AutoLogin.LOGIN_USER, AutoLogin.LOGIN_PSWD, onPlatformLoginOK, onPlatformLoginError);
			Screens.client.sendLogin(AutoLogin.LOGIN_USER, AutoLogin.LOGIN_PSWD, true, onResponse, onError);
			CClient.getGuideStepMap();
		}
		
		private function onPlatformLoginOK(e:Event) : void
		{
			Screens.client.sendLogin(AutoLogin.LOGIN_USER, AutoLogin.LOGIN_PSWD, true, onResponse, onError);
		}
		
		private function onPlatformLoginError(e:Event) : void
		{
			alert = Alert.showAlertText("无法验证您的帐号！", "错误", false, true);
		}
		
		private function onResponse(e:CClientEvent) : void
		{
			var res : LoginResponse = e.response as LoginResponse;
			
			switch(res.result)
			{
				case LoginResponse.RESULT_SUCCEED:
					Screens.getRoot().changeScreen(Screens.SCREEN_LOADING);
					
					try {
						var saved : SharedObject = SharedObject.getLocal("login.username");
						if (saved != null) {
							saved.clear();
							saved.data.username = res.login_account;
							saved.flush();
						}
					} catch (e:Error) {}
					
					break;
				case LoginResponse.RESULT_FAILED_USER_SIGN:
					alert = Alert.showAlertText("用户验证错误！", "错误", false, true);
//					alert.btnOK.addEventListener(MouseEvent.CLICK, onReconnect);
					break;
				case LoginResponse.RESULT_FAILED_USER_NOT_EXIST:
					alert = Alert.showAlertText("用户不存在！", "错误", false, true);
//					alert.btnOK.addEventListener(MouseEvent.CLICK, onReconnect);
					break;
				case LoginResponse.RESULT_FAILED_UNKNOW:
				default:
					alert = Alert.showAlertText("未知错误！", "错误", false, true);
//					alert.btnOK.addEventListener(MouseEvent.CLICK, onReconnect);	
					break;
			}
		}
		
		private function onError(e:CClientEvent) : void
		{
			alert = Alert.showAlertText("无法链接到服务器！", "错误", false, true);
		}
		
		
	}
}