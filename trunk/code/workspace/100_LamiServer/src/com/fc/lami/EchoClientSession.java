package com.fc.lami;

import com.fc.lami.Messages.AutoEnterRequest;
import com.fc.lami.Messages.AutoEnterResponse;
import com.fc.lami.Messages.EnterDeskAsVisitorRequest;
import com.fc.lami.Messages.EnterDeskAsVisitorResponse;
import com.fc.lami.Messages.EnterDeskRequest;
import com.fc.lami.Messages.EnterDeskResponse;
import com.fc.lami.Messages.EnterRoomRequest;
import com.fc.lami.Messages.EnterRoomResponse;
import com.fc.lami.Messages.ExitRoomRequest;
import com.fc.lami.Messages.ExitRoomResponse;
import com.fc.lami.Messages.FreshRoomListNotify;
import com.fc.lami.Messages.GameResetNotify;
import com.fc.lami.Messages.GameResetRequest;
import com.fc.lami.Messages.GameResetResponse;
import com.fc.lami.Messages.GetCardRequest;
import com.fc.lami.Messages.GetCardResponse;
import com.fc.lami.Messages.GetPlayerDataRequest;
import com.fc.lami.Messages.GetPlayerDataResponse;
import com.fc.lami.Messages.LeaveDeskRequest;
import com.fc.lami.Messages.LeaveDeskResponse;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.LogoutRequest;
import com.fc.lami.Messages.MainMatrixChangeRequest;
import com.fc.lami.Messages.MainMatrixChangeResponse;
import com.fc.lami.Messages.MoveCardRequest;
import com.fc.lami.Messages.MoveCardResponse;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.PlayerState;
import com.fc.lami.Messages.ReadyRequest;
import com.fc.lami.Messages.ReadyResponse;
import com.fc.lami.Messages.RepealSendCardRequest;
import com.fc.lami.Messages.RepealSendCardResponse;
import com.fc.lami.Messages.RetakeCardRequest;
import com.fc.lami.Messages.RetakeCardResponse;
import com.fc.lami.Messages.SendCardRequest;
import com.fc.lami.Messages.SendCardResponse;
import com.fc.lami.Messages.SpeakToChannelNotify;
import com.fc.lami.Messages.SpeakToChannelRequest;
import com.fc.lami.Messages.SpeakToChannelResponse;
import com.fc.lami.Messages.SpeakToPrivateNotify;
import com.fc.lami.Messages.SpeakToPrivateRequest;
import com.fc.lami.Messages.SpeakToPrivateResponse;
import com.fc.lami.Messages.SpeakToPublicNotify;
import com.fc.lami.Messages.SpeakToPublicRequest;
import com.fc.lami.Messages.SpeakToPublicResponse;
import com.fc.lami.Messages.SubmitRequest;
import com.fc.lami.Messages.SubmitResponse;
import com.fc.lami.Messages.SynchronizeRequest;
import com.fc.lami.login.User;
import com.fc.lami.model.Desk;
import com.fc.lami.model.Game;
import com.fc.lami.model.Player;
import com.fc.lami.model.Room;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;

public class EchoClientSession implements ClientSessionListener
{
	final public ClientSession 	session;
	final public LamiServerListener 		server;
	final public Player 		player;
	
	public EchoClientSession(
			ClientSession session, 
			LamiServerListener server, 
			LoginRequest req, 
			User user) 
	{
		this.session = session;
		this.server = server;
		this.player = new Player(session, user);
	}
	
	@Override
	public void disconnected(ClientSession session) {
		if (player.cur_room!=null){
			player.cur_room.onPlayerLeave(player.player_id);
		}else{
			server.getHall().leave(session);
		}
		server.getHall().broadcast(new FreshRoomListNotify(server.getRoomList()));
	}
	
	@Override
	public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {}
	
	@Override
	public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) 
	{
		//退出请求
		if (message instanceof LogoutRequest){
			session.disconnect(false);
//			disconnected(session); // 这方法不能直接用，这是后台检查到链接断开后的call back
		}
		//进入房间请求
		else if (message instanceof EnterRoomRequest){
			EnterRoomRequest request = (EnterRoomRequest)message;
			processEnterRoomRequest(session, protocol, request);
		}
		//退出房间
		else if (message instanceof ExitRoomRequest){
			ExitRoomRequest request = (ExitRoomRequest)message;
			processExitRoomRequest(session, protocol, request);
		}
		//进入桌子请求
		else if (message instanceof EnterDeskRequest){
			EnterDeskRequest request = (EnterDeskRequest)message;
			processEnterDeskRequest(session, protocol, request);
		}
		//退出桌子请求
		else if (message instanceof LeaveDeskRequest){
			LeaveDeskRequest request = (LeaveDeskRequest)message;
			processLeaveDeskRequest(session, protocol, request);
		}
		else if (message instanceof EnterDeskAsVisitorRequest){
			EnterDeskAsVisitorRequest request = (EnterDeskAsVisitorRequest)message;
			processEnterDeskAsVisitorRequest(session, protocol, request);
		}
		else if (message instanceof ReadyRequest){
			ReadyRequest request = (ReadyRequest)message;
			processReadyRequest(session, protocol, request);
		}
		/** 自动找房子，自动找桌子坐下 */
		else if (message instanceof AutoEnterRequest){
			AutoEnterRequest request = (AutoEnterRequest)message;
			processAutoEnterRequest(session, protocol, request);
		}
		/** 把卡放到桌面上 */
		else if (message instanceof SendCardRequest){
			SendCardRequest req = (SendCardRequest)message;
			processSendCardRequest(session, protocol, req);
		}
		/** 把卡从桌面上取回 */
		else if (message instanceof RetakeCardRequest){
			RetakeCardRequest req = (RetakeCardRequest)message;
			processRetakeCardRequest(session, protocol, req);
		}
		/** 移动桌面上的牌 */
		else if (message instanceof MoveCardRequest){
			MoveCardRequest req = (MoveCardRequest)message;
			processMoveCardRequest(session, protocol, req);
		}
		/** 结束放牌 */
		else if (message instanceof SubmitRequest){
			SubmitRequest req = (SubmitRequest)message;
			processSubmitRequest(session, protocol, req);
		}
		/** 摸牌 */
		else if (message instanceof GetCardRequest){
			GetCardRequest request = (GetCardRequest)message;
			processGetCardRequest(session, protocol, request);
		}
		/** 客户端发出桌面牌改变的请求  */
		else if (message instanceof MainMatrixChangeRequest){
			MainMatrixChangeRequest req = (MainMatrixChangeRequest)message;
			processMainMatrixChangeRequest(session, protocol, req);
		}
		/** 撤销出牌 */
		else if (message instanceof RepealSendCardRequest){
			RepealSendCardRequest request = (RepealSendCardRequest)message;
			processRepealSendCardRequest(session, protocol, request);
		}
		/** 同步桌面的牌和自己的手牌 */
		else if (message instanceof SynchronizeRequest){
			SynchronizeRequest request = (SynchronizeRequest)message;
			processSynchronizeRequest(session, protocol, request);
		}
		else if (message instanceof GameResetRequest){
			GameResetRequest request = (GameResetRequest)message;
			processGameResetRequest(session, protocol, request);
		}
		else if (message instanceof GetPlayerDataRequest){
			GetPlayerDataRequest request = (GetPlayerDataRequest)message;
			processGetPlayerDataRequest(session, protocol, request);
		}
		else if (message instanceof SpeakToPublicRequest){
			SpeakToPublicRequest request = (SpeakToPublicRequest)message;
			processSpeakToPublicRequest(session, protocol, request);
		}
		else if (message instanceof SpeakToPrivateRequest){
			SpeakToPrivateRequest request = (SpeakToPrivateRequest)message;
			processSpeakToPrivateRequest(session, protocol, request);
		}
		else if (message instanceof SpeakToChannelRequest){
			SpeakToChannelRequest request = (SpeakToChannelRequest)message;
			processSpeakToChannelRequest(session, protocol, request);
		}
		System.out.println(message.toString());
	}
	
	/** 进入房间请求*/ 
	private void processEnterRoomRequest(ClientSession session, Protocol protocol, EnterRoomRequest request) {
		if (player.cur_room != null) {
			player.cur_room.onPlayerLeave(player.player_id);
		}
		Room r = server.getRoom(request.room_no);
		if (r != null) {
			if (r.onPlayerEnter(player)){
				EnterRoomResponse res = new EnterRoomResponse(EnterRoomResponse.ENTER_ROOM_RESULT_SUCCESS);
				res.room = r.getRoomData();
				session.sendResponse(protocol, res);
				server.getHall().leave(session);
				server.getHall().broadcast(new FreshRoomListNotify(server.getRoomList()));
				//session.sendResponse(protocol, new EnterRoomResponse(EnterRoomResponse.ENTER_ROOM_RESULT_SUCCESS));
			}else{
				session.sendResponse(protocol, new EnterRoomResponse(EnterRoomResponse.ENTER_ROOM_RESULT_FAIL_ROOM_FULL));
			}
		}else{
			session.sendResponse(protocol, new EnterRoomResponse(EnterRoomResponse.ENTER_ROOM_RESULT_FAIL_ROOM_NOT_EXIST));
		}
	}
	
	/** 退出房间 */
	private void processExitRoomRequest(ClientSession session, Protocol protocol, ExitRoomRequest request){
		if (player.cur_room != null) {
			player.cur_room.onPlayerLeave(player.player_id);
			session.sendResponse(protocol, new ExitRoomResponse(server.getRoomList()));
			server.getHall().join(session);
			server.getHall().broadcast(new FreshRoomListNotify(server.getRoomList()));
		}
	}
	
	/** 进入桌子请求 */
	private void processEnterDeskRequest(ClientSession session, Protocol protocol, EnterDeskRequest request){
		if (player.cur_desk != null) {
			player.cur_desk.leaveDesk(player);
		}
		if (player.cur_room != null) {
			Desk d = null;
			if (request.desk_No == -1){
				d = player.cur_room.getIdleDesk();
			}else{
				d = player.cur_room.getDesk(request.desk_No);
			}
			if (d != null) {
				if (d.getGame()!=null){
					session.sendResponse(protocol, 
							new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_GAME_STARTED));
					return;
				}
				int seat = request.seat;
				if (request.seat == -1){
					seat = d.getIdleSeat();
				}
				if (seat == -1){
					session.sendResponse(protocol, 
							new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_NO_IDLE_SEAT));
				}
				else if (d.joinDesk(player, seat)){
					player.cur_desk = d;
//					EnterDeskNotify edn = new EnterDeskNotify(player.getPlayerData().player_id,d.desk_id,request.seat);
//					player.cur_room.broadcast(edn);
					Player players[] = player.cur_desk.getPlayerList();
					PlayerState[] ps = new PlayerState[players.length];
					for (int i = 0; i <ps.length; i++){
						ps[i] = new PlayerState();
						ps[i].player_id = players[i].player_id;
						ps[i].is_ready = players[i].is_ready;
						ps[i].is_openice = players[i].isOpenIce;
					}
					session.sendResponse(protocol, 
							new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_SUCCESS,
									d.getDeskData().desk_id, seat, LamiConfig.TURN_INTERVAL, LamiConfig.OPERATE_TIME,
									ps));
				} else {
					session.sendResponse(protocol, 
							new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_PLAYER_EXIST));
				}
			} else {
				session.sendResponse(protocol, 
						new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_NO_IDLE_DESK));
			}
		}else{
			// TODO 要先进房间
			session.sendResponse(protocol, new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_NOT_HAVE_ROOM));
		}
	}
	
	/** 进入桌子围观请求 */
	private void processEnterDeskAsVisitorRequest(ClientSession session, Protocol protocol, EnterDeskAsVisitorRequest request){
		if (player.cur_desk != null) {
			player.cur_desk.leaveDesk(player);
		}
		if (player.cur_room != null) {
			Desk d = player.cur_room.getDesk(request.desk_id);
			if (d != null) {
				if (d.addVisitor(player)){
					Player players[] = player.cur_desk.getPlayerList();
					PlayerState[] ps = new PlayerState[players.length];
					for (int i = 0; i <ps.length; i++){
						ps[i] = new PlayerState();
						ps[i].player_id = players[i].player_id;
						ps[i].is_ready = players[i].is_ready;
						ps[i].is_openice = players[i].isOpenIce;
					}
					session.sendResponse(protocol, 
							new EnterDeskAsVisitorResponse(EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_SUCCESS, 
									request.desk_id,
									LamiConfig.TURN_INTERVAL, LamiConfig.OPERATE_TIME, ps));
				}else{
					session.sendResponse(protocol, new EnterDeskAsVisitorResponse(EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_ALREADY_IN_DESK));
				}
			}else{
				session.sendResponse(protocol, new EnterDeskAsVisitorResponse(EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_NO_DESK));
			}
		}else{
			session.sendResponse(protocol, new EnterDeskAsVisitorResponse(EnterDeskAsVisitorResponse.ENTER_DESK_VISITOR_RESULT_FAIL_NO_ROOM));
		}
	}
	
	/** 退出桌子请求 */
	private void processLeaveDeskRequest(ClientSession session, Protocol protocol, LeaveDeskRequest request){
		if (player.cur_desk != null) {
			if (player.cur_desk.getGame()!=null && !player.isVisitor()){
				session.sendResponse(protocol, new LeaveDeskResponse(LeaveDeskResponse.LEAVE_DESK_RESULT_FAIL_GAMING));
			}else{
				player.cur_desk.leaveDesk(player);
				session.sendResponse(protocol, new LeaveDeskResponse(LeaveDeskResponse.LEAVE_DESK_RESULT_SUCCESS));
			}
		}
	}
	
	/** 准备好了 */
	private void processReadyRequest(ClientSession session, Protocol protocol, ReadyRequest request){
		if (player.cur_desk!=null&&player.cur_desk.getGame() == null){
			player.cur_desk.onPlayerReady(player, request.isReady);
		}
		session.sendResponse(protocol, new ReadyResponse());
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
//
//	-----------------------------------------------------------------------------------------------------------------------------
	
	/** 把卡放到桌面上(废弃中) */
	private void processSendCardRequest(ClientSession session, Protocol protocol, SendCardRequest request){
		Game game = player.getGame();
		if (game !=null && game.getCurPlayer() == player){
			session.sendResponse(protocol, new SendCardResponse(game.putCardToDesk(request.cards, request.x, request.y)));
		}
	}
	
	/** 把卡从桌面上取回(废弃中) */
	private void processRetakeCardRequest(ClientSession session, Protocol protocol, RetakeCardRequest req){
		Game game = player.getGame();
		if (game!=null && game.getCurPlayer() == player){
			session.sendResponse(protocol, new RetakeCardResponse(game.takeCardFromDesk(req.cards)));
		}
	}
	
	/** 移动桌面上的牌(废弃中) */
	private void processMoveCardRequest(ClientSession session, Protocol protocol, MoveCardRequest req){
		Game game = player.getGame();
		if (game !=null && game.getCurPlayer() == player){
			session.sendResponse(protocol, new MoveCardResponse(game.MoveCard(req.cards, req.nx, req.ny)));
		}
	}
	
	/** 结束放牌 */
	private void processSubmitRequest(ClientSession session, Protocol protocol, SubmitRequest req){
		Game game = player.getGame();
		if (game!=null && game.getCurPlayer() == player){
			SubmitResponse res = new SubmitResponse(game.submit());
			if (res.result == SubmitResponse.SUBMIT_RESULT_FAIL_CARD_COMBI_NO_MATCH){
				res.fail_cards = game.getFailedCard();
			}
			session.sendResponse(protocol, res);
		}
	}
	
	/** 摸牌 */
	private void processGetCardRequest(ClientSession session, Protocol protocol, GetCardRequest request){
		Game game = player.getGame();
		if (game!=null){
			if (game.getCurPlayer() == player){
				if (game.playerGetCard()){
					session.sendResponse(protocol, new GetCardResponse(GetCardResponse.GET_CARD_RESULT_SUCCESS));
				}else{
					session.sendResponse(protocol, new GetCardResponse(GetCardResponse.GET_CARD_RESULT_FAIL_SEND_CARD));
				}
			}else{
				session.sendResponse(protocol, new GetCardResponse(GetCardResponse.GET_CARD_RESULT_FAIL_NOT_TURN));
			}
		}
	}
	
	/** 客户端发出桌面牌改变的请求  */
	private void processMainMatrixChangeRequest(ClientSession session, Protocol protocol, MainMatrixChangeRequest req){
		Game game = player.getGame();
		if (game!=null && game.getCurPlayer() == player){
			if (game.MainMatrixChange(req.cards)){
				session.sendResponse(protocol, new MainMatrixChangeResponse(MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_SUCCESS));
			}else{
				session.sendResponse(protocol, new MainMatrixChangeResponse(MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL));
			}
		}
	}
	
	/** 同步桌面的牌和自己的手牌 */
	private void processSynchronizeRequest(ClientSession session, Protocol protocol, SynchronizeRequest request){
		Game game = player.getGame();
		if (game!=null){
			session.sendResponse(protocol, game.SynchronizePlayerCard(player.player_id));
		}
	}
	
	/** 撤销出牌 */
	private void processRepealSendCardRequest(ClientSession session, Protocol protocol, RepealSendCardRequest request){
		Game game = player.getGame();
		if (game!=null){
			game.PlayerRepeal();
			session.sendResponse(protocol, new RepealSendCardResponse());
		}
	}
	
	/** 聊天  当前频道喊话*/
	private void processSpeakToPublicRequest(ClientSession session, Protocol protocol, SpeakToPublicRequest request){
		SpeakToPublicNotify notify = new SpeakToPublicNotify(player.getUID(), request.message);
		player.getCurChannel().send(notify);
		session.sendResponse(protocol, new SpeakToPublicResponse());
	}
	
	/** 聊天  私聊 */
	private void processSpeakToPrivateRequest(ClientSession session, Protocol protocol, SpeakToPrivateRequest request){
		SpeakToPrivateNotify notify = new SpeakToPrivateNotify(player.getUID(), request.message);
		Player p = server.getPlayerByUID(request.uid);
		if (p!=null){
			p.session.send(notify);
			session.sendResponse(protocol, new SpeakToPrivateResponse(SpeakToPrivateResponse.SPEAK_TO_PRIVATE_RESULT_SUCCESS));
		}else{
			session.sendResponse(protocol, new SpeakToPrivateResponse(SpeakToPrivateResponse.SPEAK_TO_PRIVATE_RESULT_FAIL_PLAYER_NOEXIST));
		}
	}
	
	/** 聊天  指定频道喊话 */
	private void processSpeakToChannelRequest(ClientSession session, Protocol protocol, SpeakToChannelRequest request){
		SpeakToChannelNotify notify = new SpeakToChannelNotify(player.getUID(), request.message);
		Channel channel = server.getChannel(request.channel);
		if (channel!=null){
			channel.send(notify);
			session.sendResponse(protocol, new SpeakToChannelResponse(SpeakToChannelResponse.SPEAK_TO_CHANNEL_RESULT_SUCCESS));
		}else{
			session.sendResponse(protocol, new SpeakToChannelResponse(SpeakToChannelResponse.SPEAK_TO_CHANNEL_RESULT_FAIL_CHANNEL_NOEXIST));
		}
	}
	
	/** 处理玩家要求重新发牌的请求 */
	private void processGameResetRequest(ClientSession session, Protocol protocol, GameResetRequest request){
		if (player.getGame()!=null){
			if (player.getGame().isStartTime()){
				if (player.isCanResetGame()){
					player.getGame().gameInit();
					player.cur_desk.broadcast(new GameResetNotify(player.player_id));
					session.sendResponse(protocol, new GameResetResponse(GameResetResponse.GAME_RESET_RESULT_SUCCESS));
				}else{
					session.sendResponse(protocol, new GameResetResponse(GameResetResponse.GAME_RESET_RESULT_FAIL_CANT_RESET));
				}
			}else{
				session.sendResponse(protocol, new GameResetResponse(GameResetResponse.GAME_RESET_RESULT_FAIL_TIMEOUT));
			}
		}
	}
	
	private void processAutoEnterRequest(ClientSession session, Protocol protocol, AutoEnterRequest request){
		if (player.cur_room != null) {
			player.cur_room.onPlayerLeave(player.player_id);
		}
		AutoEnterResponse res = new AutoEnterResponse();
		Room r = server.getRandomRoom();
		
		if (r != null) {
			if (r.onPlayerEnter(player)){
				res.room = r.getRoomData();
				session.sendResponse(protocol, res);
				server.getHall().leave(session);
				server.getHall().broadcast(new FreshRoomListNotify(server.getRoomList()));
			}else{
				res.result = AutoEnterResponse.AUTO_ENTER_RESULT_FAIL_NO_IDLE_SEAT;
				session.sendResponse(protocol, res);
			}
		}else{
			res.result = AutoEnterResponse.AUTO_ENTER_RESULT_FAIL_NO_IDLE_SEAT;
			session.sendResponse(protocol, res);
		}
	}
	
	private void processGetPlayerDataRequest(ClientSession session, Protocol protocol, GetPlayerDataRequest request){
		PlayerData p = player.cur_room.getPlayer(request.player_id);
		session.sendResponse(protocol, new GetPlayerDataResponse(p));
	}
}
