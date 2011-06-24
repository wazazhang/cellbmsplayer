package com.fc.lami.net.sfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.io.ConsoleRedirectTask;
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

public class LamiSFSExtension extends SFSServerAdapter
{
	static private XingCloud xingcloud;
	
	static private AtomicInteger init_count = new AtomicInteger();
	
	private MessageFactory codec = new MessageFactory();
	
	@Override
	public void init() 
	{
		xingcloud = new XingCloud(this);
		
//		if (adapter == null) 
		{
			trace("\n===================================================\n" +
					"= Lami SFSExtension initializing ...\n" +
					"=    init_count = " + init_count.get() + "\n" +
					"= CurrentFolder = " + getCurrentFolder() + "\n" +
					"===================================================\n");
			try {
				trace("root dir is " + new File(".").getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			CObject.initSystem(
					new CObject.NullStorage(), 
					new CAppBridge(getClass().getClassLoader(), getClass()));
			
			try {		
				LamiConfig.load(LamiConfig.class, 
						getExtResource("/config/lami_config.properties"));
				trace("\n" +LamiConfig.toProperties(LamiConfig.class));
				super.open(0, new LamiServerListener(
						getExtResource(LamiConfig.ROOM_SET_CONFIG_XLS)));
			} catch (Exception e) {
				e.printStackTrace();
			}		
			trace("\n===================================================\n" +
					"= Lami SFSExtension started !\n" +
					"=  default zone is : " + getParentZone().getName() + "\n" +
					"= codec version is : " + codec.getMutualCodec().getVersion() + "\n" +
					"===================================================\n");
			StringBuilder sb = new StringBuilder();
			Properties properties = System.getProperties();
			for (Object key : properties.keySet()) {
				CUtil.toStatusLine(key, properties.get(key), sb);
			}
			trace("\nSystem Properties: \n" + sb + "\n");
		}
		
	}
	
	@Override
	public ExternalizableFactory getMessageFactory() {
		return codec;
	}
	
	public InputStream getExtResource(String sub_path) throws IOException {
		File file = new File(".", getCurrentFolder() + "/" + sub_path);
		trace("getExtResource: " + file.getPath());
		return new FileInputStream(file);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		xingcloud.destroy();
	}
}
