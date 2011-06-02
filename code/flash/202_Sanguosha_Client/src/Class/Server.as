package Class
{
	import mx.core.Application;
	
	import Component.Login_Cpt;
	
	import com.net.client.ClientEvent;
	import com.net.client.ServerSession;
	import com.slg.entity.Player;
	import com.slg.sanguosha.Messages.LoginRequest;
	import com.slg.sanguosha.Messages.LoginResponse;
	
	[Bindable]
	public class Server
	{
		public static var app:Application;
		
		protected static var client:SanguoshaClient;
		public static var login_cpt:Login_Cpt;
		
		private static var user_name:String;
		private static var password:String;
		
		public static var player:Player;
		
		public static function getClient() : SanguoshaClient {
			return client;
		}
		
		public static function init(ss : ServerSession):void
		{
			Server.client = new SanguoshaClient(ss);
			client.addEventListener(ClientEvent.CONNECTED, 		client_connected);
			client.addEventListener(ClientEvent.DISCONNECTED,	client_disconnected);
			// 监听服务器主动发送过来的通知
			client.addNotifyListener(client_notify);
		}
		
		//与服务器连接
		protected static function client_connected(event:ClientEvent):void
		{
			//Alert.show("连接成功!");
			var version : String = client.getSession().getMessageFactory().getVersion();
			trace("client version is : " + version);
			client.sendRequest(new LoginRequest(user_name, password, version),client_response)
		}
		
		protected static function client_disconnected(event:ClientEvent):void 
		{
			if (login_cpt != null) 
				login_cpt.visible = true;
			
			
			if (login_cpt != null) 
				login_cpt.disLink()
		}
		
		protected static function client_notify(event:ClientEvent):void
		{
			var ntf : Object = event.getNotify();
		}
		
		protected static function client_response(event:ClientEvent):void
		{
			var res:Object = event.getResponse()
			
			//响应登陆成功
			if (res is LoginResponse) {
				var login : LoginResponse = res as LoginResponse;
				trace("server version is : " + login.version);
				if(login.result == LoginResponse.LOGIN_RESULT_SUCCESS) 
					// TODO
					// 不要直接用数字，用常量
					// 需要处理，如果没有成功，需要提示用户为什么没成功，或重新输入，不能一旦失败，游戏就卡住。
				{
					player = login.player_data;
					login_cpt.linkSunccess();
				}
				else 
				{
					trace();
				}
				return;
			}
		}
		
		//连接服务器
		public static function linkToServer(name:String, pswd:String, host:String, port:int):void
		{
			user_name = name;
			password = pswd;
			if (!client.isConnected()) {
				//txt_messages.text = txt_messages.text +"connecting...\n";
				client.connect(host, port);
			}
		}
	}
}