package com.slg.net.mina;

import java.io.IOException;

import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.net.flash.FlashCrossdomainService;
import com.net.minaimpl.server.ServerImpl;
import com.slg.net.messages.MessageFactory;
import com.slg.net.server.SlgConfig;
import com.slg.net.server.SlgServerListener;

public class SlgServer extends ServerImpl {

	public SlgServer(MessageFactory factory) 
	{
		super(CIO.getAppBridge().getClassLoader(), factory, 10, 600, 600, 0);	
	}

	public static void main(String[] args) throws IOException
	{
		try {
			CAppBridge.initNullStorage();
			FlashCrossdomainService.main(new String[]{});
			MessageFactory factory = new MessageFactory();
			int port = 19830;
			if (args.length > 0) {
				SlgConfig.load(args[0]);
				port = SlgConfig.SERVER_PORT;
			}
			SlgServer server = new SlgServer(factory);
			server.open(port, new SlgServerListener(server));
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
