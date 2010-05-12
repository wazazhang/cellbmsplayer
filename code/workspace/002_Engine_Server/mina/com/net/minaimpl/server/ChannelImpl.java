package com.net.minaimpl.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.net.MessageHeader;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.*;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.Server;

public class ChannelImpl implements Channel
{
	final ChannelListener 	Listener;
	
	final int 				ID;
	
	final AbstractServer	server;
	
	final ConcurrentHashMap<ClientSession, ClientSessionImpl>
							sessions = new ConcurrentHashMap<ClientSession, ClientSessionImpl>();
	
	ChannelImpl(ChannelListener listener, int id, AbstractServer server) {
		this.Listener	= listener;
		this.ID 		= id;
		this.server 	= server;
	}
	
	public int getID() {
		return ID;
	}
	
	public Iterator<ClientSession> getSessions() {
		return sessions.keySet().iterator();
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
		if (session instanceof ClientSessionImpl) {
			ClientSessionImpl impl = (ClientSessionImpl)session;
			ClientSession old = sessions.putIfAbsent(session, impl);
			if (old == null) {
				broadcast(impl, new SystemMessageS2C(SystemMessages.EVENT_CHANNEL_JOIN_S2C));
				Listener.sessionJoined(this, session);
				return true;
			}
		}
		return false;
	}
	
	public boolean leave(ClientSession session) {
		ClientSessionImpl old = sessions.remove(session);
		if (old != null) {
			broadcast(old, new SystemMessageS2C(SystemMessages.EVENT_CHANNEL_LEAVE_S2C));
			Listener.sessionLeaved(this, session);
			return true;
		}
		return false;
	}
	
	public int leaveAll() {
		int count = 0;
		for (Iterator<ClientSessionImpl> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSession session = it.next();
			if (leave(session)) {
				count ++;
			}
		}
		return count;
	}
	
	int broadcast(ClientSession sender, MessageHeader message)
	{
		long sender_id = (sender != null ? sender.getID() : 0);
		int  count = 0;
		for (Iterator<ClientSessionImpl> it = sessions.values().iterator(); it.hasNext(); ) {
			ClientSessionImpl session = it.next();
			server.write(session.Session, message, MessageHeader.PROTOCOL_CHANNEL_MESSAGE, getID(), sender_id, 0);
		}
		return count;
	}
	
	public int send(MessageHeader message) {
		message.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return broadcast(null, message);
	}
	
	public int send(ClientSession sender, MessageHeader message) {
		message.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return broadcast(sender, message);
	}
	
	public int send(ClientSession sender, MessageHeader request, MessageHeader response) {
		response.PacketNumber	= request.PacketNumber;
		response.Protocol		= MessageHeader.PROTOCOL_CHANNEL_MESSAGE;
		return broadcast(sender, response);
	}
	
	public ChannelListener getChannelListener() {
		return Listener;
	}
	
	public Server getServer() {
		return server;
	}
}
