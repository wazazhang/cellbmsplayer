package com.net.client;

import com.net.MessageHeader;

public interface WaitingListener<REQ extends MessageHeader, RSP extends MessageHeader>
{
	public void response(NetService service, REQ request, RSP response);
	
	/**
	 * @param service
	 * @param request
	 * @param send_time 该消息的发送时间
	 */
	public void timeout(NetService service, REQ request, long send_time);
}
