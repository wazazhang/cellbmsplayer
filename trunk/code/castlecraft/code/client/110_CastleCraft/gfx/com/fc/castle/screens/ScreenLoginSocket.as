package com.fc.castle.screens
{
	import com.cell.gfx.CellScreen;
	import com.cell.gfx.CellScreenManager;
	import com.cell.net.http.HttpRequest;
	import com.cell.ui.component.Alert;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.net.client.socket.SocketClient;
	import com.net.client.ClientEvent;
	
	import flash.events.Event;
	import flash.events.MouseEvent;

	public class ScreenLoginSocket extends CellScreen
	{
		var client : SocketClient;
		
		var alert : Alert;
		
		public function ScreenLoginSocket()
		{
			client = Screens.client as SocketClient;
		}
		
		override public function added(root:CellScreenManager, args:Array):void
		{
			client.getClient().addEventListener(ClientEvent.CONNECTED, onConnected);
			client.getClient().addEventListener(ClientEvent.DISCONNECTED, onError);
			
			client.connect();
		}
		
		override public function removed(root:CellScreenManager):void
		{
			client.getClient().removeEventListener(ClientEvent.CONNECTED, onConnected);
			client.getClient().removeEventListener(ClientEvent.DISCONNECTED, onError);
		}
		
		override public function update():void
		{

		}
		
		private function onConnected(e:ClientEvent) : void
		{
			getParent().changeScreen(Screens.SCREEN_LOGIN);
		}
		
		private function onError(e:ClientEvent) : void
		{
			alert = Alert.showAlertText("无法链接到服务器！\n是否重新链接？", "错误", false, true);
			alert.btnOK.addEventListener(MouseEvent.CLICK, onReconnect);
		}
		
		private function onReconnect(e:Event) : void
		{
			client.connect();
		}
	}
}