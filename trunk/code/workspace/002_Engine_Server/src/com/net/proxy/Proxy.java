package com.net.proxy;

import java.io.IOException;


public interface Proxy 
{
	public void open(int local_port, String remote_host, int remote_port);

	public void close();
	
}
