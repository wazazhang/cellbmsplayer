package com.fc.lami;

import java.util.Date;

import com.fc.lami.Messages.EchoRequest;
import com.fc.lami.Messages.EchoResponse;
import com.fc.lami.Messages.EnterDeskNotify;
import com.fc.lami.Messages.EnterDeskRequest;
import com.fc.lami.Messages.EnterDeskResponse;
import com.fc.lami.Messages.EnterRoomRequest;
import com.fc.lami.Messages.EnterRoomResponse;
import com.fc.lami.Messages.ExitRoomRequest;
import com.fc.lami.Messages.ExitRoomResponse;
import com.fc.lami.Messages.GetCardRequest;
import com.fc.lami.Messages.GetCardResponse;
import com.fc.lami.Messages.GetTimeRequest;
import com.fc.lami.Messages.GetTimeResponse;
import com.fc.lami.Messages.LeaveDeskRequest;
import com.fc.lami.Messages.LeaveDeskResponse;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.LoginResponse;
import com.fc.lami.Messages.LogoutRequest;
import com.fc.lami.Messages.MainMatrixChangeRequest;
import com.fc.lami.Messages.MainMatrixChangeResponse;
import com.fc.lami.Messages.MoveCardRequest;
import com.fc.lami.Messages.MoveCardResponse;
import com.fc.lami.Messages.ReadyRequest;
import com.fc.lami.Messages.ReadyResponse;
import com.fc.lami.Messages.RepealSendCardRequest;
import com.fc.lami.Messages.RepealSendCardResponse;
import com.fc.lami.Messages.RetakeCardRequest;
import com.fc.lami.Messages.RetakeCardResponse;
import com.fc.lami.Messages.RoomData;
import com.fc.lami.Messages.SendCardRequest;
import com.fc.lami.Messages.SendCardResponse;
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
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;

public class EchoClientSession implements ClientSessionListener
{
	final public ClientSession 	session;
	final public Server 		server;
	final public Player 		player;
	
	public EchoClientSession(ClientSession session, Server server, User user) {
		this.session = session;
		this.server = server;
		this.player = new Player(session, user);
	}
	
	@Override
	public void disconnected(ClientSession session) {
		if (player.cur_room!=null){
			player.cur_room.onPlayerLeave(player.player_id);
		}
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
		else if (message instanceof ReadyRequest){
			ReadyRequest request = (ReadyRequest)message;
			processReadyRequest(session, protocol, request);
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
		
		System.out.println(message.toString());
	}
	
	/** 进入房间请求*/ 
	private void processEnterRoomRequest(ClientSession session, Protocol protocol, EnterRoomRequest request){
		if (request.room_no<server.getRoomList().length){
			Room r = server.getRoomList()[request.room_no];
			if (r.onPlayerEnter(player)){
				EnterRoomResponse res = new EnterRoomResponse(EnterRoomResponse.ENTER_ROOM_RESULT_SUCCESS);
				res.room = r.getRoomData();
				session.sendResponse(protocol, res);
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
		if (player.cur_room!=null){
			player.cur_room.onPlayerLeave(player.player_id);
			session.sendResponse(protocol, new ExitRoomResponse());
		}
	}
	
	/** 进入桌子请求 */
	private void processEnterDeskRequest(ClientSession session, Protocol protocol, EnterDeskRequest request){
		if (player.cur_room!=null){
			Desk d = player.cur_room.getDesk(request.desk_No);
			boolean result = false;
			
			switch (request.seat){
			case 0:
				result = d.setPlayerN(player);
				break;
			case 1:
				result = d.setPlayerW(player);
				break;
			case 2:
				result = d.setPlayerS(player);
				break;
			case 3:
				result = d.setPlayerE(player);
				break;
			}
			
			if (result){
				player.cur_desk = d;
				EnterDeskNotify edn = new EnterDeskNotify(player.getPlayerData(),player.cur_desk.getDeskData(),request.seat);
				player.cur_room.notifyAll(edn);
				session.sendResponse(protocol, new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_SUCCESS,d.getDeskData()));
			}else{
				session.sendResponse(protocol, new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_PLAYER_EXIST,null));
			}
		}else{
			// TODO 要先进房间
			session.sendResponse(protocol, new EnterDeskResponse(EnterDeskResponse.ENTER_DESK_RESULT_FAIL_NOT_HAVE_ROOM,null));
		}
	}
	
	/** 退出桌子请求 */
	private void processLeaveDeskRequest(ClientSession session, Protocol protocol, LeaveDeskRequest request){
		player.cur_desk.leavePlayer(player);
		session.sendResponse(protocol, new LeaveDeskResponse());
	}
	
	/** 准备好了 */
	private void processReadyRequest(ClientSession session, Protocol protocol, ReadyRequest request){
		if (player.cur_desk!=null){
			player.is_ready = request.isReady;
			player.cur_desk.onPlayerReady(player, request.isReady);
		}
		session.sendResponse(protocol, new ReadyResponse());
	}
	
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
				// TODO 检测打出的牌是否正确
			session.sendResponse(protocol, new SubmitResponse(game.submit()));
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
}
