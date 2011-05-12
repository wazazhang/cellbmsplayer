package Class
{
	import Class.Model.Card;
	import Class.Model.Desk;
	import Class.Model.Room;
	
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
	import com.fc.lami.Messages.ExitRoomRequest;
	import com.fc.lami.Messages.ExitRoomResponse;
	import com.fc.lami.Messages.GameOverNotify;
	import com.fc.lami.Messages.GameOverToRoomNotify;
	import com.fc.lami.Messages.GameStartNotify;
	import com.fc.lami.Messages.GameStartToRoomNotify;
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
	import com.fc.lami.Messages.OperateCompleteNotify;
	import com.fc.lami.Messages.PlayerData;
	import com.fc.lami.Messages.ReadyNotify;
	import com.fc.lami.Messages.ReadyRequest;
	import com.fc.lami.Messages.RepealSendCardNotify;
	import com.fc.lami.Messages.SubmitRequest;
	import com.fc.lami.Messages.SubmitResponse;
	import com.fc.lami.Messages.SynchronizeRequest;
	import com.fc.lami.Messages.SynchronizeResponse;
	import com.fc.lami.Messages.TurnEndNotify;
	import com.fc.lami.Messages.TurnStartNotify;
	import com.net.client.ClientEvent;
	import com.net.client.ServerSession;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	
	[Bindable]
	public class Server
	{
		protected static var client:LamiClient;
		
		public static var player:PlayerData;
		
		public static var login_cpt:Login_Cpt;
		
		public static var room_cpt:Room_Cpt;

		private static var room:Room;
		
		public static var game:Game;
		
		public static var app:Application;	
		
		private static var request_time:Date;
		
		public static var timer:int;
		
		public static var timerStr:String;
			
		public static function getClient() : LamiClient {
			return client;
		}
		
		public static function init(ss : ServerSession):void
		{
			Server.client = new LamiClient(ss);
			client.addEventListener(ClientEvent.CONNECTED, 		client_connected);
			client.addEventListener(ClientEvent.DISCONNECTED,	client_disconnected);
			// 监听服务器主动发送过来的通知
			client.addNotifyListener(client_notify);
		}
		
		public static function getPlayer(player_id:int):PlayerData
		{
			return room.getPlayerFromPlayerList(player_id);
		}
		
		public static function getDesk(desk_id:int):Desk
		{
			return room.getDesk(desk_id);
		}
		
		public static function getPlayerDeskId(player_id:int):int
		{
			return room.getPlayerDeskId(player_id);
		}
		
		//与服务器连接
		protected static function client_connected(event:ClientEvent):void
		{
			//Alert.show("连接成功!");

			client.sendRequest(new LoginRequest(player.name),client_response)
		}
		
		protected static function client_disconnected(event:ClientEvent):void 
		{
			if (login_cpt != null) 
			login_cpt.visible = true;
			
			if (room_cpt != null) 
			room_cpt.visible = false;
			
			if(game!=null)
			game.lami.visible = false;
			
			if (login_cpt != null) 
			login_cpt.disLink()
		}
		
		
		
		protected static function client_notify(event:ClientEvent):void
		{
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
				game.gamer.getCards(cards);
				//Alert.show("notify : GetCardNotify");
			}
			
			//获得起始牌
			else if (ntf is GameStartNotify){
				
				var gsn : GameStartNotify = ntf as GameStartNotify;
				var cards2 : ArrayCollection = new ArrayCollection();
				for each(var cd2:CardData in gsn.cards){
					cards2.addItem(Card.createCardByData(cd2) );
				}
				game.start(cards2);
			}
			
			else if (ntf is EnterRoomNotify){
				var ern : EnterRoomNotify = ntf as EnterRoomNotify;	
				
				if(room_cpt!=null)
				room_cpt.enterRoom(ern.player);					
			}
			
			else if (ntf is ExitRoomNotify){
				var exrn : ExitRoomNotify = ntf as ExitRoomNotify;
				room_cpt.leaveRoom(exrn.player_id);
				
			}
			
			else if (ntf is EnterDeskNotify){
				
				//Alert.show("ss");
				var edn : EnterDeskNotify = ntf as EnterDeskNotify;
				room.getDesk(edn.desk_id).sitDown(edn.player_id, edn.seatID);
				room_cpt.enterDesk(edn.player_id, edn.desk_id, edn.seatID);
				
				if(game!=null)
					game.lami.enterPlayer(edn.player_id, edn.desk_id, edn.seatID);
				
			}
			
			else if (ntf is LeaveDeskNotify){
				var ldn : LeaveDeskNotify = ntf as LeaveDeskNotify;

				room.getDesk(ldn.desk_id).leaveDesk(ldn.player_id);
				room_cpt.leaveDesk(ldn.player_id, ldn.desk_id);
				if(game!=null)
					game.lami.leavePlayer(ldn.player_id, ldn.desk_id);

				
			}
			
			else if (ntf is MainMatrixChangeNotify ){
				var mmcn : MainMatrixChangeNotify = ntf as MainMatrixChangeNotify;
				game.publicCardChange(mmcn.is_hardhanded, mmcn.cards);
				//game_cpt.leavePlayer(ldn);
			}
			
			else if (ntf is ReadyNotify){
				var rn : ReadyNotify = ntf as ReadyNotify;
				
				game.lami.onPlayerReady(rn.player_id,rn.isReady);
				
				game.cleanMatrix();
				game.gamer.cleanMatrix();
			}
			
			else if (ntf is TurnStartNotify){
				//TODO 轮到自己行动
				var tsn:TurnStartNotify = ntf as TurnStartNotify;
				game.setAllCardIssend();
				game.leftCard = tsn.stack_num;
				game.playerTurnStart(tsn.player_id);

			}
			else if (ntf is OperateCompleteNotify){
				game.timeCtr.reset();
			}
			else if (ntf is TurnEndNotify){
				// TODO 自己回合结束
				game.turnOver();
				//Alert.show("行动结束");
			}
			
			else if (ntf is GameOverNotify){
				// TODO 此处添加游戏结果的代码
				var gon:GameOverNotify = ntf as GameOverNotify;
				game.lami.onGameOver(gon);
				// TODO 重置各个玩家的准备按钮
			
			}
			
			else if (ntf is OpenIceNotify){
				var oin:OpenIceNotify = ntf as OpenIceNotify;
				game.lami.onPlayerPoBing(oin.player_id)	
				//Alert.show("玩家破冰");
			}
			else if (ntf is RepealSendCardNotify){
				var rscn:RepealSendCardNotify = ntf as RepealSendCardNotify;
				
				if (player.player_id == rscn.player_id){
					var cards3 : ArrayCollection = new ArrayCollection();
					for each(var cd3:CardData in rscn.cds){
						cards3.addItem(Card.createCardByData(cd3) );
					}
					game.gamer.getCards(cards3);
				}
			}
			
			else if (ntf is GameStartToRoomNotify)
			{
				var gstrn:GameStartToRoomNotify = ntf as GameStartToRoomNotify
				room_cpt.onGameStart(gstrn.desk_id);
			}
				
			else if (ntf is GameOverToRoomNotify)
			{
				var gotrn:GameOverToRoomNotify = ntf as GameOverToRoomNotify
				room_cpt.onGameOver(gotrn.desk_id);
				
			}
			
		}
		
		protected static function client_response(event:ClientEvent):void
		{
			var res:Object = event.getResponse()
				
			//响应登陆成功
			if (res is LoginResponse) {
				var login : LoginResponse = res as LoginResponse;
				
				if(login.result == LoginResponse.LOGIN_RESULT_SUCCESS) 
				// TODO
				// 不要直接用数字，用常量
				// 需要处理，如果没有成功，需要提示用户为什么没成功，或重新输入，不能一旦失败，游戏就卡住。
				{
					player = login.player;
					login_cpt.rooms = login.rooms;
					login_cpt.linkSunccess();
				}
				return;
			}
			
			//响应进入房间
			if(res is EnterRoomResponse)
			{
				var enterRoom : EnterRoomResponse =res as EnterRoomResponse;
			    
				if(enterRoom.result==0) 
				{	
					room = new Room(enterRoom.room);
					room_cpt = new Room_Cpt(); 
					room_cpt.setStyle("verticalCenter","0");
					room_cpt.setStyle("horizontalCenter","0");	
					app.addChild(room_cpt);
					room_cpt.room =	room;
					room_cpt.init(enterRoom.room)
					login_cpt.visible = false;
					
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
			
			else if(res is ExitRoomResponse){
				var err : ExitRoomResponse =res as ExitRoomResponse;
				app.removeChild(room_cpt);
				login_cpt.visible = true;
			}
			
			//响应进入房间
			if(res is EnterDeskResponse){
				var enterdesk : EnterDeskResponse =res as EnterDeskResponse;
				var response:Date = new Date();
				var delay:int = response.getTime() - request_time.getTime();
				if(enterdesk.result==0)
				{
					room_cpt.visible = false;

					game = new Game();
					app.addChild(game.lami);
					
					game.timeCtr.sumTimerSet(enterdesk.turn_interval-delay);
					game.timeCtr.oprTimerSet(enterdesk.operate_time);
					
					room.getDesk(enterdesk.desk_id).sitDown(player.player_id, enterdesk.seat);
					game.lami.initDesk(room.getDesk(enterdesk.desk_id));
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
				
				game.publicCardChange(true, syn.matrix);
				game.gamer.myCardChange(syn.player_card);
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
		
		
		//请求退出房间
		public static function ExitRoom():void
		{
			client.sendRequest(new ExitRoomRequest(),client_response)
		}
		
		
		
		public static function enterDesk(deskid:int,seat:int):void
		{
			client.sendRequest(new EnterDeskRequest(deskid,seat),client_response);
			request_time = new Date();
		}
		
		public static function leaveDesk(deskid:int):void
		{
			client.sendRequest(new LeaveDeskRequest(player.player_id,deskid),client_response);
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
			res.cards = game.getPublicCards;
			client.sendRequest(res, client_response);
		}

		//连接服务器
		public static function linkToServer(name:String, host:String, port:int):void
		{
			player = new PlayerData(0,name);
			if (!client.isConnected()) {
				//txt_messages.text = txt_messages.text +"connecting...\n";
				client.connect(host, port);
			}
		}
		

	}
}