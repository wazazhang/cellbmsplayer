package com.fc.castle.screens
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.net.http.HttpRequest;
	import com.cell.ui.component.Alert;
	import com.cell.ui.component.Pan;
	import com.fc.castle.data.message.Messages.LoginRequest;
	import com.fc.castle.data.message.Messages.LoginResponse;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.res.Res;
	import com.fc.castle.ui.FormLogin;
	import com.fc.castlecraft.AutoLogin;
	
	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class ScreenLogin extends CellScreen
	{
		private var loginPan : FormLogin;
		
		private var bg : Bitmap;
		
		public function ScreenLogin()
		{
			bg = Res.createBitmap(Res.gfx_title);
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{
			addChild(bg);
			loginPan = new FormLogin();
			loginPan.setCenter(root);
		}
		
		override public function removed(root:CellScreenManager):void
		{
			
		}
		
		
		override public function transitionCompleted():void
		{
			addChild(loginPan);
		}
		
		override public function update():void
		{

		}
		
	}
}