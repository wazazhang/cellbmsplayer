package com.net.minaimpl.server.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.minaimpl.NetPackageCodec;

/**
 * 将大量客户端链接，通过该服务器，转发到另外一个服务器上，只建立很少量的链接。<br>
 * BridgeServer <-> Client
 * @author WAZA
 */
public abstract class BridgeServerB2C extends IoHandlerAdapter// implements Proxy
{
	final protected Logger 		log = LoggerFactory.getLogger(getClass().getName());
	
	final private ClassLoader				class_loader;
	final private ExternalizableFactory		ext_factory;
	
	/** 处理线程的数目 */
	final private int 			IoProcessCount;
	final private int			SessionReadIdleTimeSeconds;
	final private int			SessionWriteIdleTimeSeconds;

	private NetPackageCodec 	Codec;
	private long 				StartTime;

	IoAcceptor 					Acceptor;
	
//	----------------------------------------------------------------------------------------------------------------------

	/**
	 * @param cl ClassLoader
	 * @param ef ExternalizableFactory
	 * @param ioProcessCount IO处理线程数
	 * @param sessionWriteIdleTimeSeconds	多长时间内没有发送数据，断掉链接
	 * @param sessionReadIdleTimeSeconds	多长时间内没有接受数据，断掉链接
	 * @param server_connector
	 */
	public BridgeServerB2C(
			ClassLoader cl,
			ExternalizableFactory ef,
			int ioProcessCount, 
			int sessionWriteIdleTimeSeconds,
			int sessionReadIdleTimeSeconds) 
	{	
		this.class_loader					= cl;
		this.ext_factory					= ef;
		this.IoProcessCount					= ioProcessCount;
		this.SessionWriteIdleTimeSeconds	= sessionWriteIdleTimeSeconds;
		this.SessionReadIdleTimeSeconds		= sessionReadIdleTimeSeconds;
		this.Codec							= new NetPackageCodec(cl, ef);
	}	
	
//	----------------------------------------------------------------------------------------------------------------------

	final public long getStartTime() {
		return StartTime;
	}
	
	synchronized public void open(int localPort, String remoteHost, int remotePort) throws IOException 
	{
		if (Acceptor == null)
		{
			log.info("starting server at port : " + localPort);
			
			StartTime 	= System.currentTimeMillis();
			Acceptor	= new NioSocketAcceptor(IoProcessCount);
			
			Acceptor.getSessionConfig().setReaderIdleTime(SessionReadIdleTimeSeconds);
			Acceptor.getSessionConfig().setWriterIdleTime(SessionWriteIdleTimeSeconds);
			Acceptor.setHandler(this);
			Acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			
			Acceptor.bind(new InetSocketAddress(localPort));
			
			Runtime.getRuntime().addShutdownHook(new ServerCleanTask(Acceptor));
			
			log.info("server started !");
		}
		else {
			log.info("Server already open !");
		}
	}
	
	synchronized public void close() throws IOException
	{
		if (Acceptor != null)
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

//	-----------------------------------------------------------------------------------------------------------------------

	private void removeAllSession() {
		for (IoSession session : Acceptor.getManagedSessions().values()) {
			session.close(false);
		}
	}

	protected IoSession getClientSession(long session_id) {
		if (Acceptor != null) {
			return Acceptor.getManagedSessions().get(session_id);
		}
		return null;
	}

	abstract protected ServerSessionB2S getServerSession();
	
	abstract protected void sendClient(MessageHeader message);

	abstract protected void sendServer(MessageHeader message);
	
//	-----------------------------------------------------------------------------------------------------------------------


	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.error(cause.getMessage() + "\n" + session, cause);
		session.close(false);
	}
	
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.debug("sessionIdle : " + session + " : " + status);
		session.close(false);
	}

	public void sessionOpened(IoSession session) throws Exception {
		log.debug("sessionOpened : " + session);
	}
	
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("sessionClosed : " + session);
	}
	
	public void messageReceived(IoSession session, Object message) throws Exception 
	{
		if (message instanceof MessageHeader) {
			MessageHeader header = (MessageHeader) message;
			header.SessionID			= session.getId();		
			header.ChannelSesseionID	= session.getId();
			sendServer(header);
		} else {
			log.error("bad message type : " + session + " : " + message);
		}
	}
	

	protected class ServerSessionB2S extends IoHandlerAdapter
	{
		private String	c_host; 
		private int		c_port; 
		private long	c_timeout;
		
		IoSession			Session;
		IoConnector			Connector;
		NetPackageCodec		Codec;
		
		public ServerSessionB2S()
		{
			Codec		= new NetPackageCodec(class_loader, ext_factory);
			Connector 	= new NioSocketConnector();
			Connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(Codec));
			Connector.setHandler(this);
		}
		
		synchronized public boolean connect(String host, int port, long timeout) throws IOException 
		{
			if (Session != null)
			{
				this.c_host		= host; 
				this.c_port		= port; 
				this.c_timeout	= timeout;
				
				SocketAddress address = new InetSocketAddress(host, port);
				
				ConnectFuture future1 = Connector.connect(address);
				future1.awaitUninterruptibly(timeout);
				if (!future1.isConnected()) {
					return false;
				}
				Session = future1.getSession();

				if (Session != null && Session.isConnected()) {
					Runtime.getRuntime().addShutdownHook(new ClientCleanTask(Session));
					return true;
				} else {
					log.error("not connect : " + address.toString());
				}
			} else {
				log.error("Already connected !");
			}
			return false;
		}
		
		public void sessionClosed(IoSession session) throws Exception {
			try {
				log.error("server is broken, reconnec to server !");
				connect(c_host, c_port, c_timeout);
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
			}
		}
		
		public void messageReceived(IoSession iosession, Object message) throws Exception 
		{
			if (message instanceof MessageHeader) {
				MessageHeader header = (MessageHeader) message;
				sendClient(header);
			} else {
				log.error("messageReceived : bad message type : " + message);
			}
		}
	}
	
	private static class ClientCleanTask extends Thread
	{
		final IoSession session;
		
		public ClientCleanTask(IoSession session) {
			this.session = session;
		}
		
		public void run() {
			try {
				session.close(false);
			} catch (Exception e) {}
		}
	}
	
	private static class ServerCleanTask extends Thread
	{
		final IoAcceptor acceptor;
		
		public ServerCleanTask(IoAcceptor acceptor) {
			this.acceptor = acceptor;
		}
		
		public void run() {
			try {
				acceptor.unbind();
			} catch (Exception e) {}
			try {
				acceptor.dispose();
			} catch (Exception e) {}
		}
	}
}


