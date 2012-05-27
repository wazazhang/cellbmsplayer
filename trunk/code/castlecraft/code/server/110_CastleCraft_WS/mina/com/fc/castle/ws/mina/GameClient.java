package com.fc.castle.ws.mina;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.net.io.MessageHeader;
import com.fc.castle.data.message.Messages;
import com.fc.castle.data.message.Messages.*;
import com.fc.castle.gs.game.GameManager;
import com.fc.castle.ws.impl.game.WSGameManager;
import com.net.Protocol;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;

public class GameClient implements ClientSessionListener
{
	private static final Logger log = LoggerFactory.getLogger(GameServer.class);
	
	final private GameManager game;
	
	public GameClient(GameManager game) 
	{
		this.game = game;
	}
	
	@Override
	public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message)
	{
		System.out.println("r : " + message);
		MessageHeader response = processMessage(message);
		if (response != null) {
			session.sendResponse(protocol, response);
		}
		else {
			log.info("no handle to process message : " + message);
		}
	}
	
	@Override
	public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message)
	{
		
	}

	@Override
	public void disconnected(ClientSession session) 
	{
		
	}
	
	protected MessageHeader processMessage(MessageHeader message) 
	{
		if (message instanceof LoginRequest) {
			return game.doLogin((LoginRequest)message);
		}
		else if (message instanceof GetUnitTemplateRequest) {
			return game.doGetUnitTemplate((GetUnitTemplateRequest)message);
		}
		else if (message instanceof BattleStartRequest) {
			return game.doBattleStart((BattleStartRequest)message);
		}
		return null;
	}
}
