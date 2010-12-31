package com.net.minaimpl.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.mina.core.IoUtil;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.filterchain.IoFilterChainBuilder;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.service.IoServiceStatistics;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.session.IoSessionDataStructureFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.NetPackageCodec;
import com.net.minaimpl.ProtocolImpl;
import com.net.minaimpl.ProtocolPool;
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
		log.error(cause.getMessage() + " : " + session, cause);
		if (CloseOnError) {
			if (session.isConnected() && !session.isClosing()) {
				session.close(false);
			}
		}
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		if (CloseOnError) {
			if (session.isConnected() && !session.isClosing()) {
				session.close(false);
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	protected void write(
			IoSession 		session, 
			MessageHeader 	message,
			byte			protocol, 
			int				channel_id, 
			long			channel_sender_id,
			int				packnumber)
	{
		if (session.isConnected()) {
			ProtocolImpl p = ProtocolPool.getInstance().createProtocol();
			p.SessionID			= session.getId();
			p.Protocol			= protocol;
			p.PacketNumber		= packnumber;
			p.message			= message;			
			p.ChannelID			= channel_id;
			p.ChannelSesseionID	= channel_sender_id;
			session.write(p);
		}
	}

//	----------------------------------------------------------------------------------------------------------------------
	
	public String getStats()
	{
		StringBuilder lines = new StringBuilder();
		
		lines.append("Mina Server Implements\n");
		{
			lines.append(" |-                 Active : " + Acceptor.isActive() + "\n");
			lines.append(" |-         ActivationTime : " + CUtil.timesliceToStringHour(Acceptor.getActivationTime()) + "\n");
			lines.append(" |-                Address : ");
			boolean first = true;
			for (SocketAddress addr : Acceptor.getLocalAddresses()) {
			if (first) {lines.append(addr.toString()+"\n");first = false;} else {
			lines.append("                             " + addr.toString()+"\n");}}
			lines.append(" |-          SessionCount  : "+getSessionCount()+"\n");
			lines.append(" |-    ScheduledWriteBytes : "+CUtil.toBytesSizeString(Acceptor.getScheduledWriteBytes())+"\n");
			lines.append(" |- ScheduledWriteMessages : "+Acceptor.getScheduledWriteMessages()+"\n");
			
			lines.append(" |-          ReceivedBytes : "+CUtil.toBytesSizeString(getReceivedBytes())+"\n");
			lines.append(" |-   ReceivedMessageCount : "+getReceivedMessageCount()+"\n");
			lines.append(" |-              SentBytes : "+CUtil.toBytesSizeString(getSentBytes())+"\n");
			lines.append(" |-       SentMessageCount : "+getSentMessageCount()+"\n");
			lines.append(" |-              StartTime : "+CUtil.timeToString(getStartTime())+"\n");		
			lines.append(" |---------------------------\n");

		}
		
		return lines.toString();
	}
	



}
