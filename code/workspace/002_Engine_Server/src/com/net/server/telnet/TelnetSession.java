package com.net.server.telnet;

import com.net.AbstractSession;

public interface TelnetSession extends AbstractSession
{
	public void 					send(String message);
	
	public TelnetServer				getServer();
	
	public TelnetSessionListener	getListener();

}
