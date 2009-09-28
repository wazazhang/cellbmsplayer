package com.net.client;

import com.net.MessageHeader;

public interface NotifyListener<N extends MessageHeader>
{
	public void notify(NetService service, N notify);
}
