package com.fc.lami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.*;
import com.fc.lami.model.Desk;
import com.fc.lami.model.Game;
import com.fc.lami.model.Player;
import com.fc.lami.model.Room;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.flash.message.FlashMessageFactory;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class Server extends ServerImpl implements ServerListener
{
	ThreadPool services = new ThreadPool("Flash-Test");
	ArrayList<EchoClientSession> client_list = new ArrayList<EchoClientSession>();
	static int ids = 0;

	Room rooms[];
	
	public Server(FlashMessageFactory factory) {
		super(CIO.getAppBridge().getClassLoader(), factory, 10, 600, 600, 0);
		int room_number = LamiConfig.ROOM_NUMBER;
		rooms = new Room[room_number];
		for (int i = 0; i<room_number; i++){
			rooms[i] = new Room(i, services, LamiConfig.THREAD_INTERVAL);
		}
	}

	public void open(int port) throws IOException {
		super.open(port, this);
	}
	
	@Override
	public ClientSessionListener connected(ClientSession session) {
		log.info("connected " + session.getRemoteAddress());
		EchoClientSession c = new EchoClientSession(session);
		client_list.add(c);
		return c;
	}
	
	class EchoClientSession implements ClientSessionListener
	{
		ClientSession session;
//		ScheduledFuture<?> task;
		
		Player player;
		public EchoClientSession(ClientSession session) {
			this.session = session;
//			// 每10秒向客户端发送个消息
//			this.task = services.scheduleAtFixedRate(this, 1000, 10000);
		}
		@Override
		public void disconnected(ClientSession session) {
			
			log.info("disconnected " + session.getRemoteAddress());
			if (player.cur_room!=null){
				
				player.cur_room.onPlayerLeave(player.player_id);
				
			}
			client_list.remove(this);
//			this.task.cancel(false);
		}
		@Override
		public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {}
		
		@Override
		public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (message instanceof EchoRequest) {
//				session.send(new EchoResponse("xxxxxx"));//<--这句代码发送到客户端，是不会触发客户端response监听方法的，只会触发notify方法。
				session.sendResponse(protocol, new EchoResponse("xxxxxx"+session.getName()));
			    broadcast(message);
			}

			if (message instanceof EchoResponse) {
				broadcast(message);
			}
			else if (message instanceof GetTimeRequest) {
				session.sendResponse(protocol, new GetTimeResponse(new Date().toString()));
			}
			//登陆请求
			else if (message instanceof LoginRequest){
				LoginRequest request = (LoginRequest)message;
				this.player = new Player(session, request.name, ids++);
				LoginResponse res = new LoginResponse(LoginResponse.LOGIN_RESULT_SUCCESS,this.player.getPlayerData());
				res.rooms = new RoomData[rooms.length];
				for (int i = 0; i<rooms.length; i++){
					res.rooms[i] = rooms[i].getRoomData();
				}
				session.sendResponse(protocol, res);
			}
			//退出请求
			else if (message instanceof LogoutRequest){
				disconnected(session);
			}
			//请入房间请求
			else if (message instanceof EnterRoomRequest){
				EnterRoomRequest request = (EnterRoomRequest)message;
				if (request.room_no<rooms.length){
					Room r = rooms[request.room_no];
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
			//退出房间
			else if (message instanceof ExitRoomRequest){
				if (player.cur_room!=null){
					player.cur_room.onPlayerLeave(player.player_id);
					session.sendResponse(protocol, new ExitRoomResponse());
				}
			}
			//进入桌子请求
			else if (message instanceof EnterDeskRequest){
				EnterDeskRequest request = (EnterDeskRequest)message;
				if (player.cur_room!=null){
					Desk d = player.cur_room.desks[request.desk_No];
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
			//退出桌子请求
			else if (message instanceof LeaveDeskRequest){
				//LeaveDeskRequest request = (LeaveDeskRequest)message;
				player.cur_desk.leavePlayer(player);
				session.sendResponse(protocol, new LeaveDeskResponse());
			}
			else if (message instanceof ReadyRequest){
				ReadyRequest req = (ReadyRequest)message;
				if (player.cur_desk!=null){
					player.is_ready = req.isReady;
					player.cur_desk.onPlayerReady(player, req.isReady);
				}
				session.sendResponse(protocol, new ReadyResponse());
			}
			/** 把卡放到桌面上 */
			else if (message instanceof SendCardRequest){
				SendCardRequest req = (SendCardRequest)message;
				Game game = player.getGame();
				if (game !=null && game.getCurPlayer() == player){
					session.sendResponse(protocol, new SendCardResponse(game.putCardToDesk(req.cards, req.x, req.y)));
				}
			}
			/** 把卡从桌面上取回 */
			else if (message instanceof RetakeCardRequest){
				RetakeCardRequest req = (RetakeCardRequest)message;
				Game game = player.getGame();
				if (game!=null && game.getCurPlayer() == player){
					session.sendResponse(protocol, new RetakeCardResponse(game.takeCardFromDesk(req.cards)));
				}
			}
			/** 移动桌面上的牌 */
			else if (message instanceof MoveCardRequest){
				MoveCardRequest req = (MoveCardRequest)message;
				Game game = player.getGame();
				if (game !=null && game.getCurPlayer() == player){
					session.sendResponse(protocol, new MoveCardResponse(game.MoveCard(req.cards, req.nx, req.ny)));
				}
			}
			/** 结束放牌 */
			else if (message instanceof SubmitRequest){
				Game game = player.getGame();
				if (game!=null && game.getCurPlayer() == player){
						// TODO 检测打出的牌是否正确
					session.sendResponse(protocol, new SubmitResponse(game.submit()));
				}
			}
			/** 摸牌 */
			else if (message instanceof GetCardRequest){
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
			else if (message instanceof MainMatrixChangeRequest){
				MainMatrixChangeRequest req = (MainMatrixChangeRequest)message;
				Game game = player.getGame();
				if (game!=null && game.getCurPlayer() == player){
					if (game.MainMatrixChange(req.cards)){
						session.sendResponse(protocol, new MainMatrixChangeResponse(MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_SUCCESS));
					}else{
						session.sendResponse(protocol, new MainMatrixChangeResponse(MainMatrixChangeResponse.MAIN_MATRIX_CHANGE_RESULT_FAIL));
					}
				}
			}
			
			else if (message instanceof SynchronizeRequest){
				Game game = player.getGame();
				if (game!=null){
					session.sendResponse(protocol, game.SynchronizePlayerCard(player.player_id));
				}
			}
			
			System.out.println(message.toString());
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.init();
			MessageFactory factory = new MessageFactory();
			

			int port = 19821;
			if (args.length > 0) {
				LamiConfig.load(args[0]);
//				try {
//					port = Integer.parseInt(args[0]);
//				} catch (Exception err) {
//					System.err.println("use default port " + port);
//				}
			}else{
//				LamiConfig.load("F:/javap/100_LamiServer/config/config.properties");
			}
			Server server = new Server(factory);
			port = LamiConfig.SERVER_PORT;
			server.open(port);
			
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
