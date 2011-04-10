package Class
{
	import Class.Model.Card;
	
	import Component.Lami;
	import Component.Login_Cpt;
	import Component.Room_Cpt;
	
	import com.fc.lami.LamiClient;
	import com.fc.lami.Messages.CardData;
	import com.fc.lami.Messages.EchoNotify;
	import com.fc.lami.Messages.EchoRequest;
	import com.fc.lami.Messages.EchoResponse;
	import com.fc.lami.Messages.EnterDeskNotify;
	import com.fc.lami.Messages.EnterDeskRequest;
	import com.fc.lami.Messages.EnterDeskResponse;
	import com.fc.lami.Messages.EnterRoomNotify;
	import com.fc.lami.Messages.EnterRoomRequest;
	import com.fc.lami.Messages.EnterRoomResponse;
	import com.fc.lami.Messages.ExitRoomNotify;
	import com.fc.lami.Messages.GameStartNotify;
	import com.fc.lami.Messages.GetCardNotify;
	import com.fc.lami.Messages.GetCardRequest;
	import com.fc.lami.Messages.GetCardResponse;
	import com.fc.lami.Messages.GetTimeRequest;
	import com.fc.lami.Messages.GetTimeResponse;
	import com.fc.lami.Messages.LeaveDeskNotify;
	import com.fc.lami.Messages.LoginRequest;
	import com.fc.lami.Messages.LoginResponse;
	import com.fc.lami.Messages.OverRequest;
	import com.fc.lami.Messages.PlayerData;
	import com.fc.lami.Messages.ReadyNotify;
	import com.fc.lami.Messages.ReadyRequest;
	import com.fc.lami.Messages.TurnEndNotify;
	import com.fc.lami.Messages.TurnStartNotify;
	import com.net.client.ClientEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	public class Server
	{
		
		protected static var client:LamiClient = new LamiClient();
		protected static var player:PlayerData;
		public static var login_cpt:Login_Cpt;
		public static var room_cpt:Room_Cpt;
		public static var game_cpt:Lami;
		
		
		public function Server()
		{
			
			
		}
		
		public static function init():void
		{
			client.addEventListener(ClientEvent.CONNECTED, 		client_connected);
			client.addEventListener(ClientEvent.DISCONNECTED,	client_disconnected);
			// 监听服务器主动发送过来的通知
			client.addNotifyListener(client_notify);
		}
		
		//与服务器连接
		protected static function client_connected(event:ClientEvent):void
		{
			//Alert.show("连接成功!");

			client.sendRequest(new LoginRequest(player.name),client_response)
		}
		
		protected static function client_disconnected(event:ClientEvent):void 
		{
			
		}
		
		
		
		protected static function client_notify(event:ClientEvent):void
		{
			/*
			if (event.getResponse() is EchoNotify) {
				var notify : EchoNotify = event.getNotify() as EchoNotify;
				txt_messages.text = txt_messages.text +"notify : " + notify.message + "\n";
			}
			else if(event.getResponse() is PlayerData)
			{
				var player:PlayerData =   event.getNotify() as PlayerData;
				addInfo(player.name+"进入了游戏");
			}
			*/
			var ntf : Object = event.getNotify();
			if (ntf is GetCardNotify){
				var gcn : GetCardNotify = ntf as GetCardNotify;
				var cards : ArrayCollection = new ArrayCollection();
				for each(var cd:CardData in gcn.cards){
					cards.addItem(new Card(cd.point, cd.type));
				}
				Game.gamer.getCards(cards);
				Alert.show("notify : GetCardNotify");
			}
			else if (ntf is GameStartNotify){
				var gsn : GameStartNotify = ntf as GameStartNotify;
				var cards2 : ArrayCollection = new ArrayCollection();
				for each(var cd2:CardData in gsn.cards){
					cards2.addItem(new Card(cd2.point, cd2.type));
				}
				Game.gamer.getStartCard(cards2);
				Alert.show("notify : GameStartNotify");
			}
			else if (ntf is EnterRoomNotify){
				var ern : EnterRoomNotify = ntf as EnterRoomNotify;
				room_cpt.addRoomInfo("玩家"+ern.player.name+"进入房间")
			}
			else if (ntf is ExitRoomNotify){
				var exrn : EnterRoomNotify = ntf as EnterRoomNotify;
				room_cpt.addRoomInfo("玩家"+ern.player.name+"离开房间")
			}
			else if (ntf is EnterDeskNotify){
				var edn : EnterDeskNotify = ntf as EnterDeskNotify;
				Alert.show("notify : 玩家"+edn.player.name+"进入桌子");
			}
			else if (ntf is LeaveDeskNotify){
				var ldn : LeaveDeskNotify = ntf as LeaveDeskNotify;
				Alert.show("notify : 玩家"+ldn.player.name+"离开桌子");
			}
			else if (ntf is ReadyNotify){
				var rn : ReadyNotify = ntf as ReadyNotify;
				Alert.show("notify : 玩家"+rn.player_id+"准备好了");
			}
			else if (ntf is TurnStartNotify){
				//TODO 轮到自己行动

				Game.turnStart();
				Alert.show("轮到行动");
			}
			else if (ntf is TurnEndNotify){
				// TODO 自己回合结束
				Game.turnOver();
				Alert.show("行动结束");
			}
			
		}
		
		protected static function client_response(event:ClientEvent):void
		{
			var res:Object = event.getResponse()
			
				
			
				
			//响应登陆成功
			if (res is LoginResponse) {
				var login : LoginResponse = res as LoginResponse;
				
				if(login.result == 0)
				{
					login_cpt.rooms = login.rooms;
					
					login_cpt.linkSunccess();
				}
				return;
			}
			
			//响应进入房间
			if(res is EnterRoomResponse){
				var enterRoom : EnterRoomResponse =res as EnterRoomResponse;
				

				
				if(enterRoom.result==0)
				{	
					
					room_cpt.init(enterRoom.room)
					login_cpt.visible = false;
					room_cpt.visible = true;
				}
				else if(enterRoom.result == 1)
				{
					Alert.show("房间已满");
				}
				else
				{
					Alert.show("进入失败");
				}
				
			}
			
			//响应进入房间
			if(res is EnterDeskResponse){
				var enterdesk : EnterDeskResponse =res as EnterDeskResponse;
				
				if(enterdesk.result==0)
				{
					room_cpt.visible = false;
					game_cpt.visible = true;
					game_cpt.init();
				}
				else if(enterdesk.result == 1)
				{
					Alert.show("此游戏已满");
				}
				else
				{
					Alert.show("尚未进入房间");
				}
				
			}
			
			else if (res is GetCardResponse){
				
			}
			
			/*
			
			if (event.getResponse() is EchoResponse) {
				var response1 : EchoResponse = event.getResponse() as EchoResponse;
				
				txt_messages.text = txt_messages.text + "response : " + response1.message + "\n";
				
			}
			else if (event.getResponse() is GetTimeResponse) {
				var response2 : GetTimeResponse = event.getResponse() as GetTimeResponse;
				
				txt_messages.text = txt_messages.text + response2.time + "\n";
			}
			else if(event.getResponse() is EnterRoomResponse){
				var res :EnterRoomResponse = event.getResponse() as EnterRoomResponse;
				
				if(res.result == 0)
				{
					txt_messages.text = txt_messages.text + "成功进入房间" + "\n";
				}
				else
				{
					txt_messages.text = txt_messages.text + "房间已满" + "\n";
				}	
			}
			
			*/
		}
		
		//请求进入房间
		public static function enterRoom(roomid:int):void
		{
			client.sendRequest(new EnterRoomRequest(roomid),client_response)
		}
		
		
		public static function enterDesk(deskid:int,seat:int):void
		{
			client.sendRequest(new EnterDeskRequest(deskid,seat),client_response);
		}
		
		public static function ready():void
		{
			client.sendRequest(new ReadyRequest(),client_response);
		}
		
		public static function getCard():void
		{
			if (Game.gamer.canOpearation && !Game.gamer.isSendCard){
				client.sendRequest(new GetCardRequest(), client_response);
			}
		}
		
		public static function submit():void
		{
			if (Game.gamer.isSendCard)
			{	
				client.sendRequest(new OverRequest(), client_response);
			}
		}
		//获得服务器端得初始牌
		public static function receiveStartCard():void
		{
			//---------------------
			var startCard:ArrayCollection = new ArrayCollection();
			for(var i:int=0;i<startCard.length;i++)
			{
				startCard.addItem(Game.getCardFromCard());
			}
			//模拟服务器拿牌
			
			
			
			Game.gamer.getStartCard(startCard);
		}
		
		
		//连接服务器
		public static function linkToServer(name:String):void
		{
			player = new PlayerData(0,name);
			if (!client.isConnected()) {
				//txt_messages.text = txt_messages.text +"connecting...\n";
				client.connect('127.0.0.1', 19821);
			}
		}
		
		
	}
}