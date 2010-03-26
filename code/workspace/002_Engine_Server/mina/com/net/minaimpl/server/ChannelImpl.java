package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.net.MessageHeader;
import com.net.minaimpl.SystemMessages;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;

public class ChannelImpl implements Channel
{
	final ChannelListener 	Listener;
	
	final int 				ID;
	
	final Server 			server;
	
	final ConcurrentHashMap<ClientSession, ClientSession>
							sessions = new ConcurrentHashMap<ClientSession, ClientSession>();
	
	ChannelImpl(ChannelListener listener, int id, Server server) {
		this.Listener	= listener;
		this.ID 		= id;
		this.server 	= server;
	}
	
	public int getID() {
		return ID;
	}
	
	public Iterator<ClientSession> getSessions() {
		return sessions.values().iterator();
	}
	
	public int getSessionCount(){
		return sessions.size();
	}
	
	public boolean hasSessions() {
		return sessions.isEmpty();
	}
	
	public boolean hasSession(ClientSession session){
		return sessions.contains(session);
	}
	
	public boolean join(ClientSession session) {
		ClientSession old = sessions.putIfAbsent(session, session);
		if (old == null) {
			MessageHeader message = new SystemMessages.SystemMessageS2C();
			message.Protocol = MessageHeader.PROTOCOL_CHANNEL_JOIN_S2C;
			message.ChannelID = getID();
			((ClientSessionImpl)session).write(message);
			Listener.sessionJoined(this, session);
			return true;
		}
		return false;
	}
	
	public boolean leave(ClientSession session) {
		ClientSession old = sessions.remove(session);
		if (old != null) {
			MessageHeader message = new SystemMessages.SystemMessageS2C();
			message.Protocol = MessageHeader.PROTOCOL_CHANNEL_LEAVE_S2C;
			message.ChannelID = getID();
			((ClientSessionImpl)session).write(message);
			Listener.sessionLeaved(this, session);
			return true;
		}
		return false;
	}
	
	public int leaveAll() {
		int count = 0;
		for (Iterator<ClientSession> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSession session = it.next();
			if (leave(session)) {
				count ++;
			}
		}
		return count;
	}
	
	int write(ClientSession sender, MessageHeader message)
	{
		message.ChannelID	= this.getID();
		if (sender != null) {
			message.ChannelSesseionID = sender.getID();
		}
		int count = 0;
		for (Iterator<ClientSession> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSession session = it.next();
			((ClientSessionImpl)session).write(message);
		}
		return count;
	}
	
	public int send(MessageHeader message) {
		message.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return write(null, message);
	}
	
	public int send(ClientSession sender, MessageHeader message) {
		message.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return write(sender, message);
	}
	
	public int send(ClientSession sender, MessageHeader request, MessageHeader response) {
		response.PacketNumber	= request.PacketNumber;
		response.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return write(sender, response);
	}
	
	public ChannelListener getChannelListener() {
		return Listener;
	}
	
	public Server getServer() {
		return server;
	}
}
