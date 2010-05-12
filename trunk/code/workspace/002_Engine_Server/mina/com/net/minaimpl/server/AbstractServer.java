package com.net.minaimpl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.SessionAttributeKey;
import com.net.minaimpl.SystemMessages;
import com.net.minaimpl.SystemMessages.*;
import com.net.server.Channel;
import com.net.server.ChannelManager;
import com.net.server.ClientSession;
import com.net.server.Server;
import com.net.server.ServerListener;


public abstract class AbstractServer extends IoHandlerAdapter implements Server
{
	final protected Logger 		log = LoggerFactory.getLogger(getClass().getName());
	
	/** 处理线程的数目 */
	final private int 			IoProcessCount;
	final private int			SessionReadIdleTimeSeconds;
	final private int			SessionWriteIdleTimeSeconds;

	private NetPackageCodec 	Codec;
	private long 				StartTime;

	// 
	ServerListener 				SrvListener;
	IoAcceptor 					Acceptor;
	
//	----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public AbstractServer(
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds) 
	{
		this.IoProcessCount					= ioProcessCount;
		this.SessionWriteIdleTimeSeconds	= sessionWriteIdleTimeSeconds;
		this.SessionReadIdleTimeSeconds		= sessionReadIdleTimeSeconds;
		this.Codec							= new NetPackageCodec(cl, ef);
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	final public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int port, ServerListener listener) throws IOException 
	{
		if (Acceptor==null)
		{
			log.info("starting server at port : " + port);
			
			StartTime 	= System.currentTimeMillis();
			SrvListener	= listener;
			Acceptor	= new NioSocketAcceptor(IoProcessCount);
			
			Acceptor.getSessionConfig().setReaderIdleTime(SessionReadIdleTimeSeconds);
			Acceptor.getSessionConfig().setWriterIdleTime(SessionWriteIdleTimeSeconds);
//			Acceptor.setCloseOnDeactivation(true);
			Acceptor.setHandler(this);
			Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			
			Acceptor.bind(new InetSocketAddress(port));
			
			log.info("server started !");
		}
		else {
			log.info("Server already open !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (Acceptor!=null) 
		{
			log.info("server closing...");
			Acceptor.unbind();
			removeAllSession();
			Acceptor.dispose();
			Acceptor = null;
			log.info("server closed !");
		}
		else{
			log.info("Server is not open !");
		}
	}

	private void removeAllSession() {
		for (IoSession session : Acceptor.getManagedSessions().values()) {
			session.close(false);
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	final public long getSentMessageCount() {
		return Codec.SendedMessageCount;
	}
	
	final public long getReceivedMessageCount () {
		return Codec.ReceivedMessageCount;
	}
	
	final public long getSentBytes(){
		return Codec.TotalSentBytes;
	}
	
	final public long getReceivedBytes(){
		return Codec.TotalReceivedBytes;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------


	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session, cause);
		session.close(false);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		session.close(false);
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	protected void write(
			IoSession session, 
			MessageHeader message,
			short	protocol, 
			int		channel_id, 
			long	channel_sender_id,
			int		packnumber)
	{
		if (session.isConnected()) {
			message.SessionID			= session.getId();
			message.ChannelID			= channel_id;
			message.ChannelSesseionID	= channel_sender_id;
			message.Protocol			= protocol;
			message.PacketNumber		= packnumber;
			session.write(message);
		}
	}
	
}
