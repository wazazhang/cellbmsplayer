package test;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;
import com.net.client.NetService;
import com.net.client.WaitingListener;
import com.net.minaimpl.client.ServerSessionImpl;
import com.net.minaimpl.monitor.ClientMonitor;


public class EchoClient
{
	static ThreadPool 			thread_pool = new ThreadPool("thread_pool", 1, 8, 16);
	static ServerSessionImpl 	session 	= new ServerSessionImpl();
	static NetService 			client 		= new NetService(session);
	
	
	public static void main(String[] args)
	{
		try {
			
			ClientMonitor monitor = new ClientMonitor(session);
			monitor.setVisible(true);
			
			BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
			
			textInput("") ;
			while (true) {
				try {
					String cmd = read.readLine();
					textInput(cmd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	static void textInput(String cmd) 
	{
		if (cmd.startsWith("send")) 
		{
			String[] 	_number 	= cmd.split("\\s");
			
			if (_number.length == 2)
			{
				Integer		number 				= Integer.parseInt(_number[1].trim());
				testSend(number);
				return;
			}
		}
		else if (cmd.startsWith("connect"))
		{
			String[] 	host_port 	= cmd.split("\\s");
			
			if (host_port.length == 3)
			{
				String		host				= host_port[1].trim();
				Integer		port				= Integer.parseInt(host_port[2].trim());
				client.connect(host, port);
				return;
			}
		}
		else if (cmd.startsWith("disconnect"))
		{
			client.disconnect(true);
			return;
		}
		else if (cmd.startsWith("exit"))
		{
			System.exit(1);
			return;
		}
		System.out.println("usage:");
		System.out.println("connect <host> <port>");
		System.out.println("disconnect");
		System.out.println("send <package-count>");
		System.out.println("exit");
	}
	
	
	
	public static void testSend(final Integer number)
	{
		for (int i = 0; i < number; i++) {
			thread_pool.executeTask(new Runnable() {
				public void run() {
					try {
						EchoMessage msg = new EchoMessage();
						client.sendRequest(msg, 1, new WaitingListener<MessageHeader, MessageHeader>() {
							public void response(NetService service, MessageHeader request, MessageHeader response) {}
							public void timeout(NetService service, MessageHeader request, long time) {}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
	}
}
