package com.slg.sanguosha.net.mina;

import java.io.IOException;

import com.cell.CIO;
import com.cell.j2se.CAppBridge;
import com.net.flash.FlashCrossdomainService;
import com.net.minaimpl.server.ServerImpl;
import com.slg.sanguosha.MessageFactory;
import com.slg.sanguosha.SanguoshaConfig;
import com.slg.sanguosha.SanguoshaServerListener;

public class SanguoshaServer extends ServerImpl {

	public SanguoshaServer(MessageFactory factory) 
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
				SanguoshaConfig.load(args[0]);
				port = SanguoshaConfig.SERVER_PORT;
			}
			SanguoshaServer server = new SanguoshaServer(factory);
			server.open(port, new SanguoshaServerListener(server));
		} catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}
	}
}
