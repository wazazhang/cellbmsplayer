package com.fc.lami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.LoginResponse;
import com.fc.lami.Messages.RoomData;
import com.fc.lami.Messages.RoomSnapShot;
import com.fc.lami.login.Login;
import com.fc.lami.login.User;
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
	private ThreadPool 		services 		= new ThreadPool("Flash-Test");
	
	private Login			login_adapter;
	
	/**保证并发访问同步的MAP*/
	private ConcurrentHashMap<String, EchoClientSession> 
							client_list 	= new ConcurrentHashMap<String, EchoClientSession> ();

	private Room 			rooms[];
	
	public Server(FlashMessageFactory factory) throws Exception
	{
		super(CIO.getAppBridge().getClassLoader(), factory, 10, 600, 600, 0);
		
		this.login_adapter = (Login)Class.forName(LamiConfig.LOGIN_CLASS).newInstance();
		
		int room_number = LamiConfig.ROOM_NUMBER;
		this.rooms = new Room[room_number];
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
		return new AnySession();
	}
	
	/**
	 * 任意的链接，一旦非验证链接发送数据，立即断开，防止被黑。
	 * @author WAZA
	 */
	private class AnySession implements ClientSessionListener
	{
		private EchoClientSession logined_session;
		
		@Override
		public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (message instanceof LoginRequest){
				LoginResponse res = processLoginRequest(session, protocol, (LoginRequest)message);
				session.sendResponse(protocol, res);
				if (res.result != LoginResponse.LOGIN_RESULT_SUCCESS) {
					session.disconnect(false);
				}
			} else if (logined_session != null) {
				logined_session.receivedMessage(session, protocol, message);
			} else{
				session.disconnect(false);
			}
		}
		
		@Override
		public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (logined_session != null) {
				logined_session.sentMessage(session, protocol, message);
			}
		}

		@Override
		public void disconnected(ClientSession session) {
			System.out.println("disconnected " + session.getRemoteAddress());
			
			if (logined_session != null) {
				synchronized (client_list) {
					client_list.remove(logined_session.player.getName());
				}
				logined_session.disconnected(session);
			}
		}
		
		/**
		 * 登陆请求
		 * @param session
		 * @param protocol
		 * @param request
		 */
		private LoginResponse processLoginRequest(ClientSession session, Protocol protocol, LoginRequest request) {
			synchronized (client_list) {
				if (request.name == null) {
					return new LoginResponse(LoginResponse.LOGIN_RESULT_FAIL, null);
				}
				EchoClientSession old_session = client_list.get(request.name);
				if (old_session != null) {
					if (old_session.session.isConnected()) {
						return new LoginResponse(LoginResponse.LOGIN_RESULT_FAIL_ALREADY_LOGIN, null);
					} else {
						client_list.remove(request.name);
					}
				}
				User user = login_adapter.login(request.name, request.validate);
				if (user == null) {
					return new LoginResponse(LoginResponse.LOGIN_RESULT_FAIL, null);
				} else {
					logined_session = new EchoClientSession(session, Server.this, user);
					client_list.put(user.getName(), logined_session);
				}
			}
			
			LoginResponse res = new LoginResponse(
					LoginResponse.LOGIN_RESULT_SUCCESS, 
					this.logined_session.player.getPlayerData());
			res.rooms = new RoomSnapShot[Server.this.getRoomList().length];
			for (int i = 0; i < getRoomList().length; i++) {
				res.rooms[i] = getRoomList()[i].getRoomSnapShot();
			}
			return res;
		}
		
	}
	
	public Room[] getRoomList(){
		return rooms;
	}
	
	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.init();
			MessageFactory factory = new MessageFactory();
			int port = 19821;
			if (args.length > 0) {
				LamiConfig.load(args[0]);
				port = LamiConfig.SERVER_PORT;
			}
			Server server = new Server(factory);
			server.open(port);
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
