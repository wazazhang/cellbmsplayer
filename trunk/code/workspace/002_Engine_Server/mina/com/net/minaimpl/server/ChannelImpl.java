package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.net.MessageHeader;
import com.net.minaimpl.SystemMessages;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;

public class ChannelImpl implements Channel
{
	final ChannelListener Listener;
	
	final int ID;
	
	final Server server;
	
	final private ConcurrentHashMap<ClientSession, ClientSession> Sessions = new ConcurrentHashMap<ClientSession, ClientSession>();
	
	ChannelImpl(ChannelListener listener, int id, Server server) {
		Listener = listener;
		ID = id;
		this.server = server;
	}
	
	public int getID() {
		return ID;
	}
	
	synchronized public Iterator<ClientSession> getSessions() {
		return Sessions.keySet().iterator();
	}
	
	synchronized public int getSessionCount(){
		return Sessions.size();
	}
	
	synchronized public boolean hasSessions() {
		return Sessions.isEmpty();
	}
	
	synchronized public boolean hasSession(ClientSession session){
		return Sessions.containsKey(session);
	}
	
	synchronized public boolean join(ClientSession session) {
		if (!Sessions.containsKey(session)) {
			Sessions.put(session, session);
			MessageHeader message = new SystemMessages.SystemMessageS2C();
			message.Protocol = MessageHeader.PROTOCOL_CHANNEL_JOIN_S2C;
			message.ChannelID = getID();
			((ClientSessionImpl)session).write(message);
			Listener.sessionJoined(this, session);
			return true;
		}
		return false;
	}
	
	synchronized public boolean leave(ClientSession session) {
		if (Sessions.remove(session)!=null){
			MessageHeader message = new SystemMessages.SystemMessageS2C();
			message.Protocol = MessageHeader.PROTOCOL_CHANNEL_LEAVE_S2C;
			message.ChannelID = getID();
			((ClientSessionImpl)session).write(message);
			Listener.sessionLeaved(this, session);
			return true;
		}
		return false;
	}
	
	synchronized public int join(Set<ClientSession> sessions) {
		int count = 0;
		for (ClientSession session : sessions) {
			if (join(session)) {
				count ++;
			}
		}
		return count;
	}

	synchronized public int leave(Set<ClientSession> sessions) {
		int count = 0;
		for (ClientSession session : new ArrayList<ClientSession>(sessions)) {
			if (leave(session)) {
				count ++;
			}
		}
		return count;
	}
	
	public int leaveAll() {
		return leave(Sessions.keySet());
	}
	
	synchronized int write(ClientSession sender, MessageHeader message)
	{
		message.ChannelID	= this.getID();
		if (sender != null) {
			message.ChannelSesseionID = sender.getID();
		}
		int count = 0;
		for (ClientSession session : Sessions.keySet()) {	
			((ClientSessionImpl)session).write(message);
			count ++;
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
	
	
	
	protected ChannelListener getChannelListener() {
		return Listener;
	}
	
	public Server getServer() {
		return server;
	}
}
