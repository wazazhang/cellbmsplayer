package com.fc.castle.net.client.socket
{
	import com.cell.crypo.MD5;
	import com.fc.castle.data.message.MessageCodec;
	import com.fc.castle.data.message.Messages.BattleStartRequest;
	import com.fc.castle.data.message.Messages.GetUnitTemplateRequest;
	import com.fc.castle.data.message.Messages.LoginRequest;
	import com.fc.castle.net.client.CClient;
	import com.fc.castle.net.client.CClientEvent;
	import com.fc.castle.screens.Screens;
	import com.net.client.Client;
	import com.net.client.ClientEvent;
	import com.net.client.minaimpl.ServerSessionImpl;
	
	import flash.events.EventDispatcher;

	public class SocketClient extends CClient
	{
		public static var CONNECT_TIMEOUT = 60000;
		public static var MESSAGE_TIMEOUT = 10000;
		
		private var _client : com.net.client.Client;
		private var _host:String;
		private var _port:int;
		
		public function SocketClient(host:String, port:int)
		{
			_client = new com.net.client.Client(new ServerSessionImpl(message_factory));
//			_client.addEventListener(com.net.client.ClientEvent.CONNECTED, onConnected);
//			_client.addEventListener(com.net.client.ClientEvent.DISCONNECTED, onDisconnected);
//			_client.addEventListener(com.net.client.ClientEvent.MESSAGE_RESPONSE, onResponse);
//			_client.addEventListener(com.net.client.ClientEvent.REQUEST_TIMEOUT, onTimeout);
//			_client.addEventListener(com.net.client.ClientEvent.MESSAGE_NOTIFY, onNotify);
//			_client.addEventListener(com.net.client.ClientEvent.SEND_ERROR, onSendError);
			
			this._host = host;
			this._port = port;
			
		}
		
		public function getClient() : Client
		{
			return _client;
		}
		
		public function connect() : Boolean
		{
			if (_client.isConnected()) {
				return true;
			} else {
				return _client.connect(_host, _port, CONNECT_TIMEOUT);
			}
		}
		
		private function onConnected(e:com.net.client.ClientEvent) : void
		{
			trace("Connected");
		}
		private function onDisconnected(e:com.net.client.ClientEvent) : void
		{
			trace("Disconnected");
//			dispatchEvent(new CClientEvent(CClientEvent.ERROR, e));
			
//			Screens.getRoot().changeScreen(Screens.SCREEN_LOGIN_SOCKET);
		}
		private function onTimeout(e:com.net.client.ClientEvent) : void
		{
			trace("Timeout");
//			dispatchEvent(new CClientEvent(CClientEvent.ERROR, e));
		}
		private function onSendError(e:com.net.client.ClientEvent) : void
		{
			trace("SendError");
//			dispatchEvent(new CClientEvent(CClientEvent.ERROR, e));
		}
		private function onNotify(e:com.net.client.ClientEvent) : void
		{
			trace("Notify");
		}
		private function onResponse(e:com.net.client.ClientEvent) : void
		{
//			dispatchEvent(new CClientEvent(CClientEvent.RESPONSE, e, e.getResponse()));
		}
		
//		-----------------------------------------------------------------------------------------------------------
		
	}
}