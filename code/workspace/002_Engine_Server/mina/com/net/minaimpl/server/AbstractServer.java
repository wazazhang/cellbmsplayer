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
	final protected Logger 			log = LoggerFactory.getLogger(getClass().getName());
	
	final protected NetPackageCodec Codec;
	final protected IoAcceptor 		Acceptor;
	
	protected ServerListener 		SrvListener;
	protected long 					StartTime;
	protected boolean				CloseOnError = true;
	
//	----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public AbstractServer(
			ClassLoader 			cl,
			ExternalizableFactory 	ef,
			int 					ioProcessCount, 
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds) 
	{
		this(cl, ef, ioProcessCount, sessionWriteIdleTimeSeconds, sessionReadIdleTimeSeconds, true);
	}	
	
	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 */
	public AbstractServer(
			ClassLoader 			cl,
			ExternalizableFactory 	ef,
			int 					ioProcessCount, 
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds,
			boolean					close_on_error) 
	{		
		this.CloseOnError	= close_on_error;
		this.Codec			= new NetPackageCodec(cl, ef);
		this.Acceptor		= new NioSocketAcceptor(ioProcessCount);
		this.Acceptor.getSessionConfig().setReaderIdleTime(sessionWriteIdleTimeSeconds);
		this.Acceptor.getSessionConfig().setWriterIdleTime(sessionReadIdleTimeSeconds);
		this.Acceptor.setHandler(this);
		this.Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	final public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int port, ServerListener listener) throws IOException 
	{
		if (!Acceptor.isActive()) {
			this.SrvListener	= listener;
			this.StartTime		= System.currentTimeMillis();
			log.info("starting server at port : " + port);
			Acceptor.bind(new InetSocketAddress(port));
			log.info("server started !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (!Acceptor.isDisposed()) {
			log.info("server closing...");
			Acceptor.unbind();
			Acceptor.dispose();
			log.info("server closed !");
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------

	final public long getSentMessageCount() {
		return Codec.getSendedMessageCount();
	}
	
	final public long getReceivedMessageCount () {
		return Codec.getReceivedMessageCount();
	}
	
	final public long getSentBytes(){
		return Codec.getTotalSentBytes();
	}
	
	final public long getReceivedBytes(){
		return Codec.getTotalReceivedBytes();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------


	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session, cause);
		if (CloseOnError) {
			session.close(false);
		}
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		if (CloseOnError) {
			session.close(false);
		}
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
