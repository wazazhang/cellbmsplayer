package com.fc.lami;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.LoginRequest;
import com.fc.lami.Messages.LoginResponse;
import com.fc.lami.Messages.PlayerData;
import com.fc.lami.Messages.RoomSnapShot;
import com.fc.lami.login.Login;
import com.fc.lami.login.LoginInfo;
import com.fc.lami.login.User;
import com.fc.lami.model.Hall;
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
	private com.net.server.Server server_instance;
	
	private ThreadPool 		services 		= new ThreadPool("Flash-Test");

	private AtomicInteger	channel_index	= new AtomicInteger(0);
	
	private Login			login_adapter;
	
	/**保证并发访问同步的MAP*/
	private ConcurrentHashMap<String, EchoClientSession> 
							client_list 	= new ConcurrentHashMap<String, EchoClientSession> ();
	
	private Hall		hall;
	
	final private Room		rooms[];
	private RoomSet rooms_set[];
	
	public LamiServerListener(InputStream table_xls) throws Exception
	{
		this.login_adapter = (Login)Class.forName(LamiConfig.LOGIN_CLASS).newInstance();
		String[][] table = readExcel(table_xls);
		int room_number = LamiConfig.ROOM_NUMBER;
		if (table!=null){
			for (int i = 0; i<table.length; i++){
				for (int j = 0; j<table[i].length; j++){
					System.out.print(table[i][j]+ "  ");
				}
				System.out.println();
			}
			
			rooms_set = new RoomSet[table.length-1];
			for (int i = 1; i<table.length; i++){
				rooms_set[i-1] = new RoomSet();
				rooms_set[i-1].id = Integer.parseInt(table[i][0]);
				rooms_set[i-1].name = table[i][1];
				rooms_set[i-1].desk_count = Integer.parseInt(table[i][2]);
				rooms_set[i-1].turn_time = Integer.parseInt(table[i][3]);
				rooms_set[i-1].operate_time = Integer.parseInt(table[i][4]);
				rooms_set[i-1].fastgame = Integer.parseInt(table[i][5]);
				rooms_set[i-1].startcard = Integer.parseInt(table[i][6]);
				rooms_set[i-1].default_desk_name = table[i][7];
			}
			
			for (int i = 0; i<rooms_set.length; i++){
				System.out.println(rooms_set[i]);
			}
			room_number = rooms_set.length;
		}
		this.rooms = new Room[room_number];
	}

	@Override
	public void init(com.net.server.Server server_instance) {
		this.server_instance = server_instance;
		this.hall = new Hall(this);
		for (int i = 0; i < rooms.length; i++) {
			if (rooms_set!=null){
				rooms[i] = new Room(this, i, services, rooms_set[i]);
			}else{
				rooms[i] = new Room(this, i, services, null);
			}
		}
	}
	
	@Override
	public void destory() {
		
	}
	
	static public String[][] readExcel(InputStream is)
	{
		try {
			Workbook rwb = Workbook.getWorkbook(is);
			Sheet st = rwb.getSheet("room_set");
			int c = st.getColumns();
			int r = st.getRows();
			String table[][] = new String[r][c];
			for (int i = 0; i<r; i++){
				for (int j = 0; j<c; j++){
					Cell cell = st.getCell(j, i);
					table[i][j] = cell.getContents().trim();
				}
			}
			rwb.close();
			return table;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
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
		public void receivedMessage(final ClientSession session, final Protocol protocol, final MessageHeader message) {
			if (message instanceof LoginRequest){
				LoginResponse res = processLoginRequest(session, protocol, (LoginRequest)message);
				session.sendResponse(protocol, res);
				if (res.result != LoginResponse.LOGIN_RESULT_SUCCESS) {
					session.disconnect(false);
				}
			} else if (logined_session != null) {
				// todo
				 logined_session.receivedMessage(session, protocol, message);
//				services.schedule(new TestTask(session, protocol, message), 1000);
				
			} else{
				session.disconnect(false);
			}
		}
		
		
		class TestTask implements Runnable
		{
			final ClientSession session; 
			final Protocol protocol; 
			final MessageHeader message;
			
			public TestTask(
					ClientSession session, 
					Protocol protocol,
					MessageHeader message) {
				super();
				this.session = session;
				this.protocol = protocol;
				this.message = message;
			}

			@Override
			public void run() {
				logined_session.receivedMessage(session, protocol, message);
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
					client_list.remove(logined_session.player.getUID());
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
		private LoginResponse processLoginRequest(
				ClientSession session, 
				Protocol protocol, 
				LoginRequest request) 
		{
			String version = server_instance.getMessageFactory().getMutualCodec().getVersion();
			if (!version.equals(request.version)) {
				return new LoginResponse(
						LoginResponse.LOGIN_RESULT_FAIL_BAD_VERSION, 
						null, 
						version, 
						"server version is [" + version + "]\n" +
						"client version is [" + request.version + "]");
			}
			synchronized (client_list) {
				if (request.platform_user_data == null) {
					return new LoginResponse(
							LoginResponse.LOGIN_RESULT_FAIL, 
							null, 
							version, 
							"request player is null");
				}
				String ruid = request.platform_user_data.getPlatformAddress();
				EchoClientSession old_session = client_list.get(ruid);
				if (old_session != null) {
					if (old_session.session.isConnected()) {
						return new LoginResponse(
								LoginResponse.LOGIN_RESULT_FAIL_ALREADY_LOGIN, 
								null, 
								version, 
								"[" + ruid + "] is already login");
					} else {
						client_list.remove(ruid);
					}
				}
				LoginInfo result = login_adapter.login(session, request);
				if (!result.isSuccess()) {
					return new LoginResponse(LoginResponse.LOGIN_RESULT_FAIL, 
							null, 
							version, 
							result.getReason());
				} else {
					logined_session = new EchoClientSession(
							session, LamiServerListener.this, request, result.getUser());
					client_list.put(result.getUser().getUID(), logined_session);
					LamiServerListener.this.hall.join(session);
				}
			}
			
			LoginResponse res = new LoginResponse(
					LoginResponse.LOGIN_RESULT_SUCCESS, 
					this.logined_session.player.getPlayerData(),
					version, "");
			res.rooms = getRoomList();
//			res.server_time = System.currentTimeMillis();
			return res;
		}
		
	}
	
	public Hall getHall(){
		return hall;
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
	
	public Room getFirstIdelRoom(){
		for (int i = 0; i < rooms.length; i++){
			if(rooms[i].getPlayerNumber()>0&&rooms[i].getPlayerNumber()<LamiConfig.PLAYER_NUMBER_MAX){
				return rooms[i];
			}
		}
		for (int i = 0; i < rooms.length; i++){
			if (rooms[i].getIdleDesk()!=null){
				return rooms[i];
			}
		}
		return null;
	}
	
	public Room getRandomRoom(){
		ArrayList<Room> roomlist = new ArrayList<Room>();
		for (int i = 0; i < rooms.length; i++){
			if(rooms[i].getPlayerNumber()>0&&rooms[i].getPlayerNumber()<LamiConfig.PLAYER_NUMBER_MAX){
				roomlist.add(rooms[i]);
			}
		}
		if (roomlist.size()>0){
			return roomlist.get(CUtil.getRandom(0, roomlist.size()));
		}
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
	
	public Player getPlayerByUID(String uid){
		for (EchoClientSession cs:client_list.values()){
			if (cs.player.getUID().equals(uid)){
				return cs.player;
			}
		}
		return null;
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
