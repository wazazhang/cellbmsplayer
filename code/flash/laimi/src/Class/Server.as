package Class
{
	import Class.Model.Card;
	import Class.Model.Desk;
	import Class.Model.Room;
	
	import Component.Lami;
	import Component.Login_Cpt;
	import Component.Room2_Cpt;
	import Component.Room_Cpt;
	
	import com.fc.lami.LamiClient;
	import com.fc.lami.Messages.AutoEnterRequest;
	import com.fc.lami.Messages.AutoEnterResponse;
	import com.fc.lami.Messages.CardData;
	import com.fc.lami.Messages.DeskData;
	import com.fc.lami.Messages.EchoNotify;
	import com.fc.lami.Messages.EchoRequest;
	import com.fc.lami.Messages.EchoResponse;
	import com.fc.lami.Messages.EnterDeskAsVisitorRequest;
	import com.fc.lami.Messages.EnterDeskAsVisitorResponse;
	import com.fc.lami.Messages.EnterDeskNotify;
	import com.fc.lami.Messages.EnterDeskRequest;
	import com.fc.lami.Messages.EnterDeskResponse;
	import com.fc.lami.Messages.EnterRoomNotify;
	import com.fc.lami.Messages.EnterRoomRequest;
	import com.fc.lami.Messages.EnterRoomResponse;
	import com.fc.lami.Messages.ExitRoomNotify;
	import com.fc.lami.Messages.ExitRoomRequest;
	import com.fc.lami.Messages.ExitRoomResponse;
	import com.fc.lami.Messages.FreshRoomListNotify;
	import com.fc.lami.Messages.GameOverNotify;
	import com.fc.lami.Messages.GameOverToRoomNotify;
	import com.fc.lami.Messages.GameResetNotify;
	import com.fc.lami.Messages.GameResetRequest;
	import com.fc.lami.Messages.GameStartNotify;
	import com.fc.lami.Messages.GameStartToRoomNotify;
	import com.fc.lami.Messages.GetCardNotify;
	import com.fc.lami.Messages.GetCardRequest;
	import com.fc.lami.Messages.GetCardResponse;
	import com.fc.lami.Messages.GetPlayerDataRequest;
	import com.fc.lami.Messages.GetPlayerDataResponse;
	import com.fc.lami.Messages.GetTimeRequest;
	import com.fc.lami.Messages.GetTimeResponse;
	import com.fc.lami.Messages.LeaveDeskNotify;
	import com.fc.lami.Messages.LeaveDeskRequest;
	import com.fc.lami.Messages.LeaveDeskResponse;
	import com.fc.lami.Messages.LoginRequest;
	import com.fc.lami.Messages.LoginResponse;
	import com.fc.lami.Messages.MainMatrixChangeNotify;
	import com.fc.lami.Messages.MainMatrixChangeRequest;
	import com.fc.lami.Messages.MainMatrixChangeResponse;
	import com.fc.lami.Messages.OpenIceNotify;
	import com.fc.lami.Messages.OperateCompleteNotify;
	import com.fc.lami.Messages.PlatformUserData;
	import com.fc.lami.Messages.PlayerData;
	import com.fc.lami.Messages.ReadyNotify;
	import com.fc.lami.Messages.ReadyRequest;
	import com.fc.lami.Messages.RepealSendCardNotify;
	import com.fc.lami.Messages.RepealSendCardRequest;
	import com.fc.lami.Messages.ResultPak;
	import com.fc.lami.Messages.SpeakToPublicNotify;
	import com.fc.lami.Messages.SpeakToPublicRequest;
	import com.fc.lami.Messages.SubmitRequest;
	import com.fc.lami.Messages.SubmitResponse;
	import com.fc.lami.Messages.SynchronizeRequest;
	import com.fc.lami.Messages.SynchronizeResponse;
	import com.fc.lami.Messages.TimeOutNotify;
	import com.fc.lami.Messages.TurnEndNotify;
	import com.fc.lami.Messages.TurnStartNotify;
	import com.net.client.ClientEvent;
	import com.net.client.ServerSession;
	import com.smartfoxserver.v2.requests.ManualDisconnectionRequest;
	import com.smartfoxserver.v2.requests.SpectatorToPlayerRequest;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.events.CloseEvent;
	
	[Bindable]
	public class Server
	{
		protected static var client:LamiClient;
		
		public static var player:PlayerData;
		
		public static var login_cpt:Login_Cpt;
		
		public static var room_cpt:Room2_Cpt;

		private static var room:Room;
		
		public static var game:Game;
		
		public static var app:Application;	
		
		private static var request_time:Date;
		
		public static var timer:int;
		
		public static var timerStr:String;
		
		public static var isAutoEnter:Boolean = false;
		
		private static var platform_user : PlatformUserData;
		private static var host : String;
		private static var port : int;
		
		private static var is_visitor:Boolean = false;
		
		
		
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
		
		public static function initUserLogin(
			platform_user:PlatformUserData, 
			host:String,
			port:String) : void
		{
			Server.host 			= host;
			Server.port 			= int(port);
			Server.platform_user 	= platform_user;
		}
		
		public static function checkConnection() : Boolean
		{
			return platform_user != null;
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
			var version : String = client.getSession().getMessageFactory().getVersion();
			trace("client version is : " + version);
			client.sendRequest(
				new LoginRequest(
					platform_user, 
					version, 
					version), 
				client_response);
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
		
		
		private static var DESK_ID:int;
		
		private static function enterDeskHandler(evt:CloseEvent):void {
			if (evt.detail == Alert.YES) {
				client.sendRequest(new EnterDeskAsVisitorRequest(DESK_ID), client_response);
			}
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
			}
			
			//获得起始牌
			else if (ntf is GameStartNotify){
				
				var gsn : GameStartNotify = ntf as GameStartNotify;
				var cards2 : ArrayCollection = new ArrayCollection();
				for each(var cd2:CardData in gsn.cards){
					cards2.addItem(Card.createCardByData(cd2) );
				}
				game.start(cards2,gsn.is_can_reset);
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
				
				var edn : EnterDeskNotify = ntf as EnterDeskNotify;
				if(room==null)
					return
				
				
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
				
//				{
//					var cards_old:Array = game.getPublicCards;
//					var comap : Dictionary = new Dictionary();
//					var cnmap : Dictionary = new Dictionary();
//					for each(var cn : CardData in mmcn.cards){
//						cnmap[cn.id] = cn;
//					}
//					
//					for each(var co: CardData in cards_old){
//						comap[co.id] = co;
//					}
//				
//					for each(var cn : CardData in mmcn.cards){
//						var co : CardData = comap[cn.id] as CardData;
//						if (co==null){
//							// 玩家打出牌
//							game.lami.addInfo(Server.getPlayerName(getPlayer(mmcn.player_id))+"打出了"+Card.cardToString(cn));
//						}else if(cn.x!=co.x || cn.y!=co.y){
//							// 玩家移动了牌
//							game.lami.addInfo(Server.getPlayerName(getPlayer(mmcn.player_id))+"移动了"+Card.cardToString(cn));
//						}
//					}
//					
//					for each(var co:CardData in cards_old){
//						if (cnmap[co.id]==null){
//							//玩家取回了牌
//							game.lami.addInfo(Server.getPlayerName(getPlayer(mmcn.player_id))+"取回了"+Card.cardToString(co));
//						}
//					}
//				}
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
				SynchronizeCard();
			
			}
			else if (ntf is OperateCompleteNotify){
				
				var ocn:OperateCompleteNotify = ntf as OperateCompleteNotify;
				
				if(ocn.player_id == Server.player.player_id ) 
				{
					game.timeCtr.reset();
				}
				else
				{
					game.lami.getOtherPlayerCptByPlayerId(ocn.player_id).timeReset();
				}
				
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
				
				for each(var rp:ResultPak in gon.result_pak){
					if (rp!=null){
						getPlayerFromServer(rp.player_id);
					}
				}
				// TODO 重置各个玩家的准备按钮
			
			}
			
			else if (ntf is SpeakToPublicNotify){
				var stpn:SpeakToPublicNotify = ntf as SpeakToPublicNotify;
				if(game!=null)
					game.lami.addTalkInfo(stpn.player_name+':'+stpn.message);
				else
					room_cpt.addRoomInfo(stpn.player_name+':'+stpn.message);
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
				
				game.lami.addInfo(Server.getPlayerName(getPlayer(rscn.player_id))+"撤销出牌");
			}
			else if (ntf is TimeOutNotify){
				var ton:TimeOutNotify = ntf as TimeOutNotify;
				game.lami.addInfo(Server.getPlayerName(getPlayer(ton.player_id))+"超时");
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
			
			else if (ntf is GameResetNotify)
			{
				var grn:GameResetNotify = ntf as GameResetNotify
				game.lami.onPlayerReset(getPlayer(grn.player_id));	
			}
			else if (ntf is FreshRoomListNotify){
				var frln:FreshRoomListNotify = ntf as FreshRoomListNotify;
				login_cpt.rooms = frln.rooms;
			}
			
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
					player = login.player;
					login_cpt.rooms = login.rooms;
					login_cpt.linkSunccess();
				}
				else if (login.result == LoginResponse.LOGIN_RESULT_FAIL_ALREADY_LOGIN)
				{
					Alert.show("已在游戏中");
				}
				else if (login.result == LoginResponse.LOGIN_RESULT_FAIL_BAD_VERSION)
				{
					Alert.show("版本错误");
				}
				else if (login.result == LoginResponse.LOGIN_RESULT_FAIL)
				{
					Alert.show("result=" + login.result + " : reason="+login.reason);
				}
				return;
			}
			
			//响应进入房间
			else if(res is EnterRoomResponse)
			{
				var enterRoom : EnterRoomResponse =res as EnterRoomResponse;
			    
				if(enterRoom.result==EnterRoomResponse.ENTER_ROOM_RESULT_SUCCESS) 
				{	
					room = new Room(enterRoom.room);
					room_cpt = new Room2_Cpt(); 
					room_cpt.setStyle("verticalCenter","0");
					room_cpt.setStyle("horizontalCenter","0");	
					app.addChild(room_cpt);
					room_cpt.room =	room;
					room_cpt.init(enterRoom.room)
					login_cpt.visible = false;
					
					if(isAutoEnter)
					{
						isAutoEnter = false;
						enterDesk(-1,-1);
					}
				}
				else if(enterRoom.result == EnterRoomResponse.ENTER_ROOM_RESULT_FAIL_ROOM_FULL)
				{
					Alert.show("房间已满");
				}
				else if (enterRoom.result == EnterRoomResponse.ENTER_ROOM_RESULT_FAIL_ROOM_NOT_EXIST)
				{
					Alert.show("房间不存在");
				}
				
			}
			else if (res is AutoEnterResponse){
				var aer:AutoEnterResponse = res as AutoEnterResponse;
				if (aer.result == AutoEnterResponse.AUTO_ENETR_RESULT_SUCCESS){
					room = new Room(aer.room);
					room_cpt = new Room2_Cpt(); 
					room_cpt.setStyle("verticalCenter","0");
					room_cpt.setStyle("horizontalCenter","0");	
					app.addChild(room_cpt);
					room_cpt.room =	room;
					room_cpt.init(aer.room)
					login_cpt.visible = false;
					enterDesk(-1,-1);
				}else if (aer.result == AutoEnterResponse.AUTO_ENTER_RESULT_FAIL_NO_IDLE_SEAT){
					Alert.show("没有空余座位");
				}
			}
			else if(res is ExitRoomResponse){
				
				var err:ExitRoomResponse = res as ExitRoomResponse
				
				app.removeChild(room_cpt);
				login_cpt.rooms = err.rooms;
				login_cpt.visible = true;
			}
			
			//响应进入房间
			else if(res is EnterDeskResponse){
				var enterdesk : EnterDeskResponse =res as EnterDeskResponse;
				var response:Date = new Date();
				var delay:int = response.getTime() - request_time.getTime();
				
				
				if(enterdesk.result==EnterDeskResponse.ENTER_DESK_RESULT_SUCCESS)
				{
					is_visitor = false;
					room_cpt.visible = false;
					game = new Game();
					app.addChild(game.lami);
					
					game.timeCtr.sumTimerSet(enterdesk.turn_interval-delay);
					game.timeCtr.oprTimerSet(enterdesk.operate_time);
					
					room.getDesk(enterdesk.desk_id).sitDown(player.player_id, enterdesk.seat);
					
					game.lami.initDesk(room.getDesk(enterdesk.desk_id));
					
				}
				else if(enterdesk.result == EnterDeskResponse.ENTER_DESK_RESULT_FAIL_NO_IDLE_DESK)
				{
					Alert.show("是否进入围观", "该桌子已满", Alert.YES|Alert.NO, null, enterDeskHandler);
				}
				else if(enterdesk.result == EnterDeskResponse.ENTER_DESK_RESULT_FAIL_GAME_STARTED)
				{
					Alert.show("是否进入围观", "该桌游戏已经开始", Alert.YES|Alert.NO, null, enterDeskHandler);
				}
				else
				{
					Alert.show("尚未进入房间");
				}
			}
			else if (res is EnterDeskAsVisitorResponse){
				var edav : EnterDeskAsVisitorResponse = res as EnterDeskAsVisitorResponse;
				if (edav.result ==  EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_SUCCESS){
					is_visitor = true;
					room_cpt.visible = false;
					
					game = new Game();
					app.addChild(game.lami);
					
					game.timeCtr.sumTimerSet(edav.turn_interval);
					game.timeCtr.oprTimerSet(edav.operate_time);
					
					game.lami.initDesk(room.getDesk(edav.desk_id));
					SynchronizeCard();
				}
				else if (edav.result == EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_ALREADY_IN_DESK){
					Alert.show("已经在当前桌子里了");
				}
				else if (edav.result == EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_NO_DESK){
					Alert.show("没有找到桌子");
				}
				else if (edav.result == EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_NO_ROOM){
					Alert.show("要先进入房间");
				}
			}
			else if (res is GetCardResponse){
				var edr : GetCardResponse =res as GetCardResponse;
			}
			else if (res is MainMatrixChangeResponse){
				var mmcr : MainMatrixChangeResponse = res as MainMatrixChangeResponse;
				if (mmcr.result!=MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_SUCCESS){
					SynchronizeCard();
				}
				
			}
			else if(res is SubmitResponse)
			{
				var sr : SubmitResponse = res as SubmitResponse;

				
				if (sr.result == SubmitResponse.SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH){
					Alert.show("有不成立的牌组");
					SynchronizeCard();
				}
				else if (sr.result == SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NO_SEND){
					Alert.show("没有破冰");
					SynchronizeCard();
				}
				else if (sr.result == SubmitResponse.SUBMIT_RESULT_FAIL_CARD_NO_SEND){
					Alert.show("没有出牌");
					SynchronizeCard();
				}
			}
			
			else if (res is SynchronizeResponse)
			{
				var syn : SynchronizeResponse = res as SynchronizeResponse;
				
				game.publicCardChange(true, syn.matrix);
				
				game.gamer.myCardChange(syn.player_card);
			}
			
			else if (res is LeaveDeskResponse){
				var ldr : LeaveDeskResponse = res as LeaveDeskResponse;
				if (ldr.result == LeaveDeskResponse.LEAVE_DESK_RESULT_SUCCESS){
					
					game.lami.stopTime();
					app.removeChild(game.lami);
					room_cpt.visible = true;
					game = null;
					//game_cpt.visible = true;
					
				}else if (ldr.result == LeaveDeskResponse.LEAVE_DESK_RESULT_FAIL_GAMING){
					Alert.show("正在游戏中不能退出");
				}
			}
			else if (res is GetPlayerDataResponse)
			{
				var gpdr : GetPlayerDataResponse = res as GetPlayerDataResponse;
				if (gpdr.player!=null){
					Server.room.updateToPlayerList(gpdr.player);
					if (player.player_id == gpdr.player.player_id){
						player.lose = gpdr.player.lose;
						player.score = gpdr.player.score;
						player.win = gpdr.player.win;
					}
				}
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
			if (deskid!=-1){
				DESK_ID = deskid;
			}
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
		
		public static function getPlayerFromServer(pid:int):void
		{
			client.sendRequest(new GetPlayerDataRequest(pid), client_response);
		}
		
		//发送公共主牌区
		public static function sendPublicMatrix():void
		{
			var res:MainMatrixChangeRequest = new MainMatrixChangeRequest();
			res.cards = game.getPublicCards;
			client.sendRequest(res, client_response);
		}
		
		
		public static function sendTalkMessage(str:String):void
		{
			var res:SpeakToPublicRequest = new SpeakToPublicRequest(str);
			client.sendRequest(res,client_response);
		}
		
		
		public static function sendResetRequest():void
		{
			var res:GameResetRequest = new GameResetRequest()
			client.sendRequest(res,client_response);
		}
		
		
		public static function sendAutoEnter():void
		{
			var res:AutoEnterRequest = new AutoEnterRequest();
			client.sendRequest(res,client_response);
			request_time = new Date();
		}
		
		public static function sendRepealRequest():void
		{
			client.sendRequest(new RepealSendCardRequest(), client_response);
		}

		//连接服务器
		public static function linkToServer():void
		{
			if (client.isConnected()) {
				client.disconnect();
			}
			if (!client.isConnected()) {
				//txt_messages.text = txt_messages.text +"connecting...\n";
				client.connect(host, port);
			}
		}
		
		public static function get isVisitor():Boolean
		{
			return is_visitor;
		}
		
		public static function getPlayerName(p:PlayerData):String
		{
			return p.player_name;
		}

	}
}