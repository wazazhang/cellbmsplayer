package com.net.minaimpl.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ChannelManager;
import com.net.server.Server;

public class ChannelManagerImpl implements ChannelManager
{
	final private ConcurrentHashMap<Integer, Channel> Channels = new ConcurrentHashMap<Integer, Channel>(1000);
	
	final Server server;
	
	ChannelManagerImpl(Server server){
		this.server = server;
	}
	
	synchronized public Channel createChannel(int id, ChannelListener listener) {
		ChannelImpl channel = new ChannelImpl(listener, id, server);
		Channels.put(id, channel);
		return Channels.get(id);
	}
	
	synchronized public Channel getChannel(int id) {
		return Channels.get(id);
	}
	
	synchronized public Iterator<Channel> getChannels() {
		return Channels.values().iterator();
	}
	
	@Override
	synchronized public Channel removeChannel(int id) {
		return Channels.remove(id);
	}
}
