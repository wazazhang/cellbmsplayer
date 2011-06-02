package com.fc.lami.net.sfs;

import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.LamiServerListener;

import com.net.ExternalizableFactory;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.ServerExtenstion;

public class LamiSFSExtension extends ServerExtenstion
{
	@Override
	public void init() 
	{
		CAppBridge.initNullStorage();
		LamiConfig.load(LamiConfig.class, super.getConfigProperties());
		LamiConfig.LOGIN_CLASS = "com.fc.lami.login.xingcloud.LoginXingCloud";
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
		return new LamiServerListener(this);
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
