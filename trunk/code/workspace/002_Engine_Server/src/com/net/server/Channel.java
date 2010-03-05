package com.net.server;

import java.util.Iterator;
import java.util.Set;

import com.net.MessageHeader;

public interface Channel 
{
	public int getID();
	
	public Iterator<ClientSession> getSessions();
	
	public int getSessionCount();
	
	public boolean hasSessions();
	
	public boolean hasSession(ClientSession session);
	
	public boolean join(ClientSession session);
	
//	public int join(Set<ClientSession> sessions);
	
	public boolean leave(ClientSession session);
	
//	public int leave(Set<ClientSession> sessions);
	
	public int leaveAll();
	
	public int send(MessageHeader message);
	
	public int send(ClientSession sender, MessageHeader message);
	
	public int send(ClientSession sender, MessageHeader request, MessageHeader response);
	
	public Server	getServer();
}
