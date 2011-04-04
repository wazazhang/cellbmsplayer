package com.fc.lami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import com.cell.CIO;

import com.cell.j2se.CAppBridge;
import com.cell.util.concurrent.ThreadPool;
import com.fc.lami.Messages.*;
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
	
	final public static int room_number = 1;
	Room rooms[];
	
	public Server(FlashMessageFactory factory) {
		super(CIO.getAppBridge().getClassLoader(), factory, 10, 600, 600, 0);
		rooms = new Room[room_number];
		for (int i = 0; i<room_number; i++){
			rooms[i] = new Room();
			services.scheduleAtFixedRate(rooms[i], 1000, 1000);
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
	
	class EchoClientSession implements ClientSessionListener, Runnable
	{
		ClientSession session;
		ScheduledFuture<?> task;
		public EchoClientSession(ClientSession session) {
			this.session = session;
			// 每10秒向客户端发送个消息
			this.task = services.scheduleAtFixedRate(this, 1000, 10000);
		}
		@Override
		public void disconnected(ClientSession session) {
			log.info("disconnected " + session.getRemoteAddress());
			client_list.remove(this);
			this.task.cancel(false);
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
			System.out.println(message.toString());

		}
		public void run() {
			
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.init();
			MessageFactory factory = new MessageFactory();
			Server server = new Server(factory);
			int port = 19821;
			if (args.length > 0) {
				try {
					port = Integer.parseInt(args[0]);
				} catch (Exception err) {
					System.err.println("use default port " + port);
				}
			}
			server.open(port);
			
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
