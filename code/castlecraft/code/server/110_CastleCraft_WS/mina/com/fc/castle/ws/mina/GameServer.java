package com.fc.castle.ws.mina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CObject;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.mysql.SQLTypeComparerMySQL;
import com.cell.sql.SQMTypeManager;
import com.fc.castle.data.message.MessageFactory;
import com.fc.castle.gs.config.GameConfig;
import com.fc.castle.service.DataManager;
import com.fc.castle.ws.config.WSConfig;
import com.fc.castle.ws.impl.game.WSGameManager;
import com.fc.castle.ws.impl.persistance.WSPersistanceManager;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;

public class GameServer implements ServerListener
{
	private static final Logger log = LoggerFactory.getLogger(GameServer.class);
	
	private ServerImpl mina_server;

	public GameServer() 
	{
		mina_server = new ServerImpl(
				getClass().getClassLoader(),
				new MessageFactory(), 
				WSConfig.NET_MINA_IO_PROCESSOR, 
				WSConfig.NET_MINA_IDLE_TIME_SEC, 
				WSConfig.NET_MINA_IDLE_TIME_SEC, 
				0);
		
	}

	public void open() throws Exception
	{
		mina_server.open(WSConfig.NET_MINA_PORT, this);
	}
	
	@Override
	public void init(com.net.server.Server server) 
	{

	}
	
	@Override
	public ClientSessionListener connected(ClientSession session) 
	{
		return new GameClient(WSGameManager.getInstance());
	}
	
	@Override
	public void destory()
	{
		
	}
	
	
	public static void main(String[] args)
	{
		log.info("init CASTLE CRAFT Mina Server");
		try
		{
			System.out.println("===============================================");
			System.out.println("= init System");
			CObject.initSystem(new CStorage("castle_mina"), new CAppBridge());
			SQMTypeManager.setTypeComparer(new SQLTypeComparerMySQL());
			
			System.out.println("===============================================");
			System.out.println("= init DataManager");
			DataManager.init("/xls/xls_template.xls");
			
			System.out.println("===============================================");
			System.out.println("= init WSPersistanceManager");
			WSPersistanceManager.init();
			
//			System.out.println("===============================================");
//			System.out.println("= init WSHttpRequest");
//			WSHttpRequest.initStatic();

			System.out.println("===============================================");
			System.out.println("= init WSGameManager");
			WSGameManager.init();


			System.out.println("===============================================");
			System.out.println("= init GameServer");
			new GameServer().open();
			
			System.out.println("===============================================");
		}
		catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
		log.info("init CASTLE CRAFT Mina Server done");

	}
}
