package test;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.cell.CUtil;
import com.cell.j2se.awt.ConsoleFrame;
import com.cell.j2se.awt.ConsoleFrame.ConsoleListener;

import com.net.MessageHeader;
import com.net.client.NetService;
import com.net.client.WaitingListener;
import com.net.minaimpl.client.ServerSessionImpl;
import com.net.minaimpl.monitor.ServerMonitor;
import com.net.minaimpl.server.ServerImpl;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;


public class EchoServer extends ServerImpl implements ServerListener
{
//	------------------------------------------------------------------------------------------------------------------

	static class NullClientSessionListener implements ClientSessionListener
	{
		public void disconnected(ClientSession session) {}
		
		//接收到一个null message,将data+1回馈给发送者
		public void receivedMessage(ClientSession session, MessageHeader message) {
			EchoMessage msg = (EchoMessage)message;
			session.send(msg);
		}
		@Override
		public void sentMessage(ClientSession session, MessageHeader message) {}
	}
	
	
//	------------------------------------------------------------------------------------------------------------------
	
	public EchoServer() {}
	
	public ClientSessionListener connected(ClientSession session) {
		return new NullClientSessionListener();
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		String	server_host	= "localhost";
		Integer	server_port	= 2300;
		
		try
		{
			if (args.length >= 2) {
				server_host = args[0].trim();
				server_port = Integer.parseInt(args[1].trim());
			}
			
			//
			final EchoServer server = new EchoServer();
			server.open(server_port, server);
			
			// 监控程序
			final ServerMonitor monitor = new ServerMonitor(server_host, server_port);
			monitor.setVisible(true);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
