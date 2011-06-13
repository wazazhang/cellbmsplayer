package com.fc.lami.net.sfs;

import java.io.IOException;

import com.cell.j2se.CAppBridge;
import com.fc.lami.LamiConfig;
import com.fc.lami.MessageFactory;
import com.fc.lami.LamiServerListener;

import com.net.ExternalizableFactory;
import com.net.server.ServerListener;
import com.net.sfsimpl.server.SFSServerAdapter;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.xingcloud.framework.intergation.sfs.XingCloudSFSExtension;

public class LamiSFSExtension extends XingCloudSFSExtension
{
	static private SFSServerAdapter adapter;
	
	@Override
	public void init() 
	{
		super.init();
		
//		if (adapter == null) 
		{
			trace("\n===================================================\n" +
					"= Lami SFSExtension initializing ...\n" +
					"===================================================\n");
					
			CAppBridge.initNullStorage();
			LamiConfig.load(LamiConfig.class, super.getConfigProperties());
			trace("\n" +LamiConfig.toProperties(LamiConfig.class));
			MessageFactory codec = new MessageFactory();
			try {
				adapter = new SFSServerAdapter(this, codec);
				adapter.open(0, new LamiServerListener());
			} catch (Exception e) {
				e.printStackTrace();
			}		
			trace("\n===================================================\n" +
					"= Lami SFSExtension started !\n" +
					"= default zone is : " + getParentZone().getName() + "\n" +
					"= codec version is : " + codec.getMutualCodec().getVersion() + "\n" +
					"===================================================\n");
		}
		
	}
	
	@Override
	public void handleClientRequest(String requestId, User sender, ISFSObject params) {
		adapter.handleClientRequest(requestId, sender, params);
		super.handleClientRequest(requestId, sender, params);
	}
	
	@Override
	public void destroy() {
		try {
			adapter.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.destroy();
	}
}
