package Class
{
	import Class.Model.Card;
	
	import Component.Lami;
	import Component.Login_Cpt;
	import Component.Room_Cpt;
	
	import com.fc.lami.LamiClient;
	import com.fc.lami.Messages.CardData;
	import com.fc.lami.Messages.DeskData;
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
	import com.fc.lami.Messages.GameOverNotify;
	import com.fc.lami.Messages.GameStartNotify;
	import com.fc.lami.Messages.GetCardNotify;
	import com.fc.lami.Messages.GetCardRequest;
	import com.fc.lami.Messages.GetCardResponse;
	import com.fc.lami.Messages.GetTimeRequest;
	import com.fc.lami.Messages.GetTimeResponse;
	import com.fc.lami.Messages.LeaveDeskNotify;
	import com.fc.lami.Messages.LeaveDeskRequest;
	import com.fc.lami.Messages.LeaveDeskResponse;
	import com.fc.lami.Messages.LoginRequest;
	import com.fc.lami.Messages.LoginResponse;
	import com.fc.lami.Messages.MainMatrixChangeNotify;
	import com.fc.lami.Messages.MainMatrixChangeRequest;
	import com.fc.lami.Messages.OpenIceNotify;
	import com.fc.lami.Messages.PlayerData;
	import com.fc.lami.Messages.ReadyNotify;
	import com.fc.lami.Messages.ReadyRequest;
	import com.fc.lami.Messages.SubmitRequest;
	import com.fc.lami.Messages.SubmitResponse;
	import com.fc.lami.Messages.SynchronizeRequest;
	import com.fc.lami.Messages.SynchronizeResponse;
	import com.fc.lami.Messages.TurnEndNotify;
	import com.fc.lami.Messages.TurnStartNotify;
	import com.net.client.ClientEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	[Bindable]
	public class Server
	{
		
		protected static var client:LamiClient = new LamiClient();
		public static var player:PlayerData;
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
			
			//得牌
			if (ntf is GetCardNotify){
				var gcn : GetCardNotify = ntf as GetCardNotify;
				var cards : ArrayCollection = new ArrayCollection();
				for each(var cd:CardData in gcn.cards){
					if (cd!=null){
						cards.addItem(Card.createCardByData(cd));
					}
				}
				Game.gamer.getCards(cards);
				//Alert.show("notify : GetCardNotify");
			}
			
			//获得起始牌
			else if (ntf is GameStartNotify){
				
				var gsn : GameStartNotify = ntf as GameStartNotify;
				var cards2 : ArrayCollection = new ArrayCollection();
				for each(var cd2:CardData in gsn.cards){
					cards2.addItem(Card.createCardByData(cd2) );
				}
				Game.start(cards2);
			}
			
			
			else if (ntf is EnterRoomNotify){
				var ern : EnterRoomNotify = ntf as EnterRoomNotify;	
				room_cpt.enterRoom(ern);					
			}
			
			else if (ntf is ExitRoomNotify){
				var exrn : ExitRoomNotify = ntf as ExitRoomNotify;
				room_cpt.leaveRoom(exrn);
			}
			
			else if (ntf is EnterDeskNotify){
				var edn : EnterDeskNotify = ntf as EnterDeskNotify;
				room_cpt.enterDesk(edn);
				game_cpt.enterPlayer(edn);
			}
			
			else if (ntf is LeaveDeskNotify){
				var ldn : LeaveDeskNotify = ntf as LeaveDeskNotify;
				room_cpt.leaveDesk(ldn);
				game_cpt.leavePlayer(ldn);
				
			}
			
			else if (ntf is MainMatrixChangeNotify ){
				var mmcn : MainMatrixChangeNotify = ntf as MainMatrixChangeNotify;
				Game.publicCardChange(mmcn.cards);
				//game_cpt.leavePlayer(ldn);
			}
			
			else if (ntf is ReadyNotify){
				var rn : ReadyNotify = ntf as ReadyNotify;
				game_cpt.onPlayerReady(rn.player_id,rn.isReady);
			}
			
			else if (ntf is TurnStartNotify){
				//TODO 轮到自己行动
				var tsn:TurnStartNotify = ntf as TurnStartNotify;
				Game.setAllCardIssend();
				
				if (tsn.player_id == player.player_id){
					Game.turnStart();
				}else{
					Game.otherPlayerStart(tsn.player_id);
				}
			
				//Alert.show("轮到行动");
			}
			else if (ntf is TurnEndNotify){
				// TODO 自己回合结束
				Game.turnOver();
				//Alert.show("行动结束");
			}
			else if (ntf is GameOverNotify){
				// TODO 此处添加游戏结果的代码
				var gon:GameOverNotify = ntf as GameOverNotify;
				Game.app.onGameOver();
			}
			else if (ntf is OpenIceNotify){
				var oin:OpenIceNotify = ntf as OpenIceNotify;
				Game.app.onPlayerPoBing(oin.player_id)	
				//Alert.show("玩家破冰");
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
					player = login.player;
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
					game_cpt.initDesk(enterdesk.desk);
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
				var edr : GetCardResponse =res as GetCardResponse;
			}
			
			else if(res is SubmitResponse)
			{
				var sr : SubmitResponse = res as SubmitResponse;
				
				if (sr.result != 0){
					Alert.show("提交错误 代码："+sr.result);
					SynchronizeCard();
				}
				else
				{
					sendPublicMatrix();
				}
			}
			
			else if (res is SynchronizeResponse)
			{
				var syn : SynchronizeResponse = res as SynchronizeResponse;
				
				Game.publicCardChange(syn.matrix);
				Game.gamer.myCardChange(syn.player_card);
			}
			
			else if (res is LeaveDeskResponse){
				room_cpt.visible = true;
				//game_cpt.visible = true;
			}
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
		
		public static function leaveDesk(desk:DeskData,seatID:int):void
		{
			client.sendRequest(new LeaveDeskRequest(player,desk,seatID),client_response);
		}
		
		public static function ready(ready:Boolean):void
		{
			client.sendRequest(new ReadyRequest(ready),client_response);
		}
		
		public static function getCard():void
		{
			client.sendRequest(new GetCardRequest(), client_response);
		}
		
		public static function submit():void
		{
			client.sendRequest(new SubmitRequest(), client_response);
		}
		
		public static function SynchronizeCard():void
		{
			client.sendRequest(new SynchronizeRequest(), client_response);
		}
		
		//发送公共主牌区
		public static function sendPublicMatrix():void
		{
			var res:MainMatrixChangeRequest = new MainMatrixChangeRequest();
			res.cards = Game.getPublicCards;
			client.sendRequest(res, client_response);
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