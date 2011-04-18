package com.fc.sfs;

import java.io.IOException;

import com.fc.lami.LamiConfig;
import com.fc.lami.Server;
import com.net.flash.FlashCrossdomainService;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class LamiSFSExtension extends SFSExtension
{
	@Override
	public void init() 
	{
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
}
