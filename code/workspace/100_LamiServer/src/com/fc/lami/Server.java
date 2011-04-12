package com.fc.lami;

import java.io.IOException;
import java.util.ArrayList;
import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.model.Room;
import com.net.flash.message.FlashMessageFactory;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class Server extends ServerImpl implements ServerListener
{
	ThreadPool services = new ThreadPool("Flash-Test");
	ArrayList<EchoClientSession> client_list = new ArrayList<EchoClientSession>();

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
		EchoClientSession c = new EchoClientSession(session, this);
		client_list.add(c);
		return c;
	}
	
	public ArrayList<EchoClientSession> getClientList(){
		return client_list;
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
