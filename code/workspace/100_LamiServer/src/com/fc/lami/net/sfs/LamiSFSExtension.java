package com.fc.lami.net.sfs;

import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.LamiServerListener;

import com.net.ExternalizableFactory;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.SFSServerAdapter;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.xingcloud.framework.intergation.sfs.XingCloudSFSExtension;

public class LamiSFSExtension extends XingCloudSFSExtension
{
	SFSServerAdapter 	adapter;
	LamiServerListener 	server_listener;
	@Override
	public void init() 
	{
		trace("\n" +
		"===================================================\n" +
		"= Lami SFSExtension initializing ...\n" +
		"===================================================\n");
		
		super.init();
		
		CAppBridge.initNullStorage();
		LamiConfig.load(LamiConfig.class, super.getConfigProperties());
//		LamiConfig.LOGIN_CLASS = com.fc.lami.login.xingcloud.LoginXingCloud.class.getCanonicalName();
		LamiConfig.LOGIN_CLASS = com.fc.lami.login.test.LoginDefault.class.getCanonicalName();
		try {
			this.adapter = new SFSServerAdapter(this, new MessageFactory());
			this.adapter.open(0, new LamiServerListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
		trace("\n" +
		"===================================================\n" +
		"= Lami SFSExtension started !\n" +
		"= default zone is : " + getParentZone().getName() + "\n" +
		"===================================================\n");
	}

	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) {
		System.out.println("handleClientRequest ex: " + sender + " : " + params);
		super.handleClientRequest(requestId, sender, params);
		adapter.handleClientRequest(requestId, sender, params);
	}
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws Exception {
		System.out.println("handleServerEvent ex: " + event.toString());
		super.handleServerEvent(event);
	}
	
	@Override
	public Object handleInternalMessage(String cmdName, Object params) {
		System.out.println("handleInternalMessage ex: " + cmdName + " : " + params);
		return super.handleInternalMessage(cmdName, params);
	}
	
	@Override
	public void destroy() 
	{
		super.destroy();
	}
}
