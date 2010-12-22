package com.net.server.telnet;

import java.io.IOException;

public interface TelnetServer 
{
	public void	open(int port, TelnetServerListener listener) throws IOException ;
	
	public void	close() throws IOException;
		
}
