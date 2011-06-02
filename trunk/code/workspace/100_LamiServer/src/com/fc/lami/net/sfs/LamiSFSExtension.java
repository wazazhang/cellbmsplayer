package com.fc.lami.net.sfs;

import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.LamiServerListener;

import com.net.ExternalizableFactory;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.SFSServerAdapter;

public class LamiSFSExtension extends SFSServerAdapter
{
	@Override
	public void init() 
	{
		trace("\n" +
		"===================================================\n" +
		"= Lami SFSExtension initializing ...\n" +
		"===================================================\n");
		
		CAppBridge.initNullStorage();
		LamiConfig.load(LamiConfig.class, super.getConfigProperties());
		LamiConfig.LOGIN_CLASS = "com.fc.lami.login.xingcloud.LoginXingCloud";
		super.init();
		
		trace("\n" +
		"===================================================\n" +
		"= Lami SFSExtension started !\n" +
		"===================================================\n");

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
