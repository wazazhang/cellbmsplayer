package com.fc.lami;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.LoginResponse;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.RoomSnapShot;
import com.fc.lami.login.Login;
import com.fc.lami.login.User;
import com.fc.lami.model.Player;
import com.fc.lami.model.Room;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class LamiServerListener implements ServerListener
{
	final private com.net.server.Server server_instance;
	
	private ThreadPool 		services 		= new ThreadPool("Flash-Test");

	private AtomicInteger	channel_index	= new AtomicInteger(0);
	
	private Login			login_adapter;
	
	/**保证并发访问同步的MAP*/
	private ConcurrentHashMap<String, EchoClientSession> 
							client_list 	= new ConcurrentHashMap<String, EchoClientSession> ();

	final private Room		rooms[];
	
	public LamiServerListener(com.net.server.Server server_instance) throws Exception
	{
		this.server_instance = server_instance;
		
		this.login_adapter = (Login)Class.forName(LamiConfig.LOGIN_CLASS).newInstance();
		
		int room_number = LamiConfig.ROOM_NUMBER;
		this.rooms = new Room[room_number];
		for (int i = 0; i < room_number; i++) {
			rooms[i] = new Room(this, i, services, LamiConfig.THREAD_INTERVAL);
		}
	}
	
	public Channel createChannel(ChannelListener cl) {
		return server_instance.getChannelManager().createChannel(channel_index.incrementAndGet(), cl);
	}
	
	public Channel getChannel(int channel_id){
		return server_instance.getChannelManager().getChannel(channel_id);
	}
	
	@Override
	public ClientSessionListener connected(ClientSession session) {
//		log.info("connected " + session.getRemoteAddress());
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
			String version = server_instance.getMessageFactory().getMutualCodec().getVersion();
			if (!version.equals(request.version)) {
				return new LoginResponse(
						LoginResponse.LOGIN_RESULT_FAIL_BAD_VERSION, 
						null, 
						version);
			}
			synchronized (client_list) {
				if (request.name == null) {
					return new LoginResponse(
							LoginResponse.LOGIN_RESULT_FAIL, 
							null, 
							version);
				}
				EchoClientSession old_session = client_list.get(request.name);
				if (old_session != null) {
					if (old_session.session.isConnected()) {
						return new LoginResponse(
								LoginResponse.LOGIN_RESULT_FAIL_ALREADY_LOGIN, 
								null, 
								version);
					} else {
						client_list.remove(request.name);
					}
				}
				User user = login_adapter.login(request.name, request.validate);
				if (user == null) {
					return new LoginResponse(LoginResponse.LOGIN_RESULT_FAIL, 
							null, 
							version);
				} else {
					logined_session = new EchoClientSession(
							session, LamiServerListener.this, user);
					client_list.put(user.getName(), logined_session);
				}
			}
			
			LoginResponse res = new LoginResponse(
					LoginResponse.LOGIN_RESULT_SUCCESS, 
					this.logined_session.player.getPlayerData(),
					version);
			res.rooms = getRoomList();
//			res.server_time = System.currentTimeMillis();
			return res;
		}
		
	}
	
//	public Room[] getRoomList(){
//		return rooms;
//	}
	
	public Room getRoom(int id) {
		if (id >= 0 && id < rooms.length) {
			return rooms[id];
		}
		return null;
	}
	
	public Room getRandomRoom(){
		ArrayList<Room> roomlist = new ArrayList<Room>();
		for (int i = 0; i < rooms.length; i++){
			if (rooms[i].getIdleDesk()!=null){
				roomlist.add(rooms[i]);
			}
		}
		return roomlist.get(CUtil.getRandom(0, roomlist.size()));
	}
	
	public RoomSnapShot[] getRoomList(){
		RoomSnapShot[] rss = new RoomSnapShot[rooms.length];
		for (int i = 0; i < rooms.length; i++) {
			rss[i] = rooms[i].getRoomSnapShot();
		}
		return rss;
	}
	
	public Player getPlayerByName(String name){
		for (EchoClientSession cs:client_list.values()){
			if (cs.player.getName().equals(name)){
				return cs.player;
			}
		}
		return null;
	}
	
	public PlayerData getPlayerByID(int pid){
		for (EchoClientSession cs:client_list.values()){
			if (cs.player.player_id == pid){
				return cs.player.getPlayerData();
			}
		}
		return null;
	}
}
