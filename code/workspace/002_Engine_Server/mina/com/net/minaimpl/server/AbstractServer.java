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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
import org.apache.mina.core.service.IoProcessor;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.service.IoServiceStatistics;
import org.apache.mina.core.service.SimpleIoProcessorPool;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.session.IoSessionDataStructureFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
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
	
	final NetPackageCodec 			Codec;
	final IoAcceptor 				Acceptor;

	final ExecutorService 			AcceptorPool;
	final ExecutorService 			IoProcessorPool;
	
	ServerListener					SrvListener;
	long							StartTime;
	boolean							CloseOnError = true;
	
//	----------------------------------------------------------------------------------------------------------------------
	/**
	 * @param class_loader
	 * @param externalizable_factory
	 * @param acceptor_pool
	 * @param io_processor_pool
	 * @param io_processor_count
	 * @param sessionWriteIdleTimeSeconds 多长时间内没有发送数据，断掉链接(秒)
	 * @param sessionReadIdleTimeSeconds  多长时间内没有接受数据，断掉链接(秒)
	 * @param close_on_error
	 */
	public AbstractServer(
			ClassLoader 			class_loader,
			ExternalizableFactory 	externalizable_factory,
			Executor 				acceptor_pool,
			Executor 				io_processor_pool,
			int						io_processor_count,
			int 					sessionWriteIdleTimeSeconds,
			int 					sessionReadIdleTimeSeconds,
			boolean					close_on_error) 
	{
		if (acceptor_pool == null) {
			this.AcceptorPool = Executors.newCachedThreadPool();
			acceptor_pool = this.AcceptorPool;
		} else {
			this.AcceptorPool = null;
		}

		if (io_processor_pool == null) {
			this.IoProcessorPool = Executors.newCachedThreadPool();
			io_processor_pool = this.IoProcessorPool;
		} else {
			this.IoProcessorPool = null;
		}

		this.Codec = new NetPackageCodec(class_loader, externalizable_factory);
		this.Acceptor = new NioSocketAcceptor(acceptor_pool, 
				new SimpleIoProcessorPool<NioSession>(
						NioProcessor.class, 
						io_processor_pool, 
						io_processor_count));
		this.Acceptor.getSessionConfig().setReaderIdleTime(sessionWriteIdleTimeSeconds);
		this.Acceptor.getSessionConfig().setWriterIdleTime(sessionReadIdleTimeSeconds);
		this.Acceptor.setHandler(this);
		this.Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
		
		this.CloseOnError = close_on_error;
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

	synchronized public void dispose() throws IOException
	{
		if (!Acceptor.isDisposed()) 
		{
			log.info("server closing...");
			try {
				Acceptor.unbind();
				Acceptor.dispose();
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
			try {
				if (this.AcceptorPool != null) {
					this.AcceptorPool.awaitTermination(1, TimeUnit.SECONDS);
					this.AcceptorPool.shutdownNow();
				}
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
			try {
				if (this.IoProcessorPool != null) {
					this.IoProcessorPool.awaitTermination(1, TimeUnit.SECONDS);
					this.IoProcessorPool.shutdownNow();
				}
			} catch (Throwable err) {
				log.error(err.getMessage(), err);
			}
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
	
	protected boolean write(
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
			return true;
		} else {
			return false;
		}
	}

//	----------------------------------------------------------------------------------------------------------------------

	
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public String getStats()
	{
		StringBuilder lines = new StringBuilder();
		lines.append("Mina Server Implements\n");
		CUtil.toStatusLine("Active",					Acceptor.isActive(), lines);
		CUtil.toStatusLine("ActivationTime",			CUtil.timesliceToStringHour(Acceptor.getActivationTime()), lines);
		CUtil.toStatusLine("Address",					Acceptor.getLocalAddresses(), lines);
		CUtil.toStatusLine("SessionCount",				getSessionCount(), lines);
		CUtil.toStatusLine("ScheduledWriteBytes",		CUtil.toBytesSizeString(Acceptor.getScheduledWriteBytes()), lines);
		CUtil.toStatusLine("ScheduledWriteMessages",	Acceptor.getScheduledWriteMessages(), lines);
		CUtil.toStatusLine("ReceivedBytes",				CUtil.toBytesSizeString(getReceivedBytes()), lines);
		CUtil.toStatusLine("ReceivedMessageCount",		getReceivedMessageCount(), lines);
		CUtil.toStatusLine("SentBytes",					CUtil.toBytesSizeString(getSentBytes()), lines);
		CUtil.toStatusLine("SentMessageCount",			getSentMessageCount(), lines);
		CUtil.toStatusLine("StartTime",					CUtil.timeToString(getStartTime()), lines);
		if (AcceptorPool instanceof ThreadPoolExecutor) {
			lines.append("[AcceptorPool]\n");
			lines.append(ThreadPool.getStatus((ThreadPoolExecutor)AcceptorPool));
		}
		if (IoProcessorPool instanceof ThreadPoolExecutor) {
			lines.append("[IoProcessorPool]\n");
			lines.append(ThreadPool.getStatus((ThreadPoolExecutor)IoProcessorPool));
		}
		CUtil.toStatusSeparator(lines);
		return lines.toString();
	}
	



}
