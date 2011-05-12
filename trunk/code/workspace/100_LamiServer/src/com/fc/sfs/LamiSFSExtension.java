package com.fc.sfs;

import java.io.IOException;

import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.Server;
import com.net.ExternalizableFactory;
import com.net.MessageHeader;
import com.net.Protocol;
import com.net.flash.FlashCrossdomainService;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.ServerExtenstion;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class LamiSFSExtension extends ServerExtenstion
{
	@Override
	public void init() 
	{
		CAppBridge.initNullStorage();
		LamiConfig.load(LamiConfig.class, super.getConfigProperties());

		super.init();
		
		trace(new Object[] { "Lami SFSExtension started" });
		
//		try {
//			FlashCrossdomainService.main(new String[]{});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
	}
	
	@Override
	protected ServerListener createListener() throws Exception {
		return new Server(this);
	}
	
	@Override
	public ExternalizableFactory createFactory() {	
		return new MessageFactory();
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
	}
}
