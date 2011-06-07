package com.slg.net.server;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.CUtil;
import com.cell.util.concurrent.ThreadPool;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.server.Channel;
import com.net.server.ChannelListener;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;
import com.slg.IWorld;
import com.slg.entity.Player;
import com.slg.net.impl.PlayerImpl;
import com.slg.net.impl.WorldImpl;
import com.slg.net.messages.Messages.LoginRequest;
import com.slg.net.messages.Messages.LoginResponse;

public class SlgServerListener implements ServerListener
{
	final private com.net.server.Server server_instance;
	
	private ThreadPool 		services 		= new ThreadPool("Sanguosha-Test");

	private AtomicInteger	channel_index	= new AtomicInteger(0);
	
	private IWorld	world;
	
	/**保证并发访问同步的MAP*/
	private ConcurrentHashMap<String, EchoClientSession> 
							client_list 	= new ConcurrentHashMap<String, EchoClientSession> ();

	public SlgServerListener(com.net.server.Server server_instance) throws Exception
	{
		this.server_instance = server_instance;
		
		world = new WorldImpl();
	}
	
	public IWorld getWorld(){
		return world;
	}
	
	public Channel createChannel(ChannelListener cl) {
		return server_instance.getChannelManager().createChannel(channel_index.incrementAndGet(), cl);
	}
	
	public Channel getChannel(int channel_id){
		return server_instance.getChannelManager().getChannel(channel_id);
	}
	
	@Override
	public ClientSessionListener connected(ClientSession session) {
//		log.info("connected " + session.getRemoteAddress());
		return new AnySession();
	}
	
	/**
	 * 任意的链接，一旦非验证链接发送数据，立即断开，防止被黑。
	 * @author WAZA
	 */
	private class AnySession implements ClientSessionListener
	{
		private EchoClientSession logined_session;
		
		@Override
		public void receivedMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (message instanceof LoginRequest){
				LoginResponse res = processLoginRequest(session, protocol, (LoginRequest)message);
				session.sendResponse(protocol, res);
				if (res.result != LoginResponse.LOGIN_RESULT_SUCCESS) {
					session.disconnect(false);
				}
			} else if (logined_session != null) {
				logined_session.receivedMessage(session, protocol, message);
			} else{
				session.disconnect(false);
			}
		}
		
		@Override
		public void sentMessage(ClientSession session, Protocol protocol, MessageHeader message) {
			if (logined_session != null) {
				logined_session.sentMessage(session, protocol, message);
			}
		}

		@Override
		public void disconnected(ClientSession session) {
			System.out.println("disconnected " + session.getRemoteAddress());
			
			if (logined_session != null&&logined_session.player!=null) {
				synchronized (client_list) {
					client_list.remove(logined_session.player.getPlayerName());
				}
				logined_session.disconnected(session);
			}
		}
		
		/**
		 * 登陆请求
		 * @param session
		 * @param protocol
		 * @param request
		 */
		private LoginResponse processLoginRequest(ClientSession session, Protocol protocol, LoginRequest request) {
			String version = server_instance.getMessageFactory().getMutualCodec().getVersion();
			if (!version.equals(request.version)) {
				System.out.println("版本号不对");
				return new LoginResponse(
						LoginResponse.LOGIN_RESULT_FAIL_BAD_VERSION, 
						null,
						version);
			}
			synchronized (client_list) {
				if (request.name == null) {
					return new LoginResponse(
							LoginResponse.LOGIN_RESULT_FAIL, 
							null,
							version);
				}
				EchoClientSession old_session = client_list.get(request.name);
				if (old_session != null) {
					if (old_session.session.isConnected()) {
						return new LoginResponse(
								LoginResponse.LOGIN_RESULT_FAIL_ALREADY_LOGIN, 
								null,
								version);
					} else {
						client_list.remove(request.name);
					}
				}

				// 如果角色不存在，则新建一个角色
				if (world.getPlayer(request.name) == null){
					PlayerImpl newp = new PlayerImpl(world, new Player(request.name));
					world.addPlayer(newp);
				}
				logined_session = new EchoClientSession(
						session, SlgServerListener.this, world.getPlayer(request.name));
				client_list.put(request.name, logined_session);
				System.out.println("登陆成功");
			}
			
			LoginResponse res = new LoginResponse(
					LoginResponse.LOGIN_RESULT_SUCCESS, 
					this.logined_session.player.getPlayer(),
					version);
//			res.server_time = System.currentTimeMillis();
			return res;
		}
		
	}
	
}
