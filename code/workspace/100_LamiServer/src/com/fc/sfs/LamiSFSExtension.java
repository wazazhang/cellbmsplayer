package com.fc.sfs;

import java.io.IOException;

import com.fc.lami.LamiConfig;
import com.fc.lami.Server;
import com.net.flash.FlashCrossdomainService;
import com.net.server.ClientSession;
import com.net.server.ClientSessionListener;
import com.net.sfsimpl.server.ServerExtenstion;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class LamiSFSExtension extends ServerExtenstion
{
	@Override
	public void init() 
	{
		super.init();
		
		trace(new Object[] { "Lami SFSExtension started" });
		
		try {
			LamiConfig.load(LamiConfig.class, super.getConfigProperties());
			Server.main(new String[]{});
			FlashCrossdomainService.main(new String[]{});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
		
		
	}
	
	@Override
	public ClientSessionListener connected(ClientSession session) {
		// TODO Auto-generated method stub
		return null;
	}
}
