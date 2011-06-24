package com.fc.lami.net.sfs;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.xingcloud.framework.bean.BeanConfig;
import com.xingcloud.framework.context.Context;
import com.xingcloud.framework.context.application.XingCloudApplication;
import com.xingcloud.framework.intergation.sfs.XingCloudSFSExtension;
import com.xingcloud.framework.intergation.sfs.protocol.SFSServiceProtocol;
import com.xingcloud.framework.service.ServiceContext;

public class XingCloud 
{
	private List<Context> contexts;
	private static final Logger LOGGER = Logger
			.getLogger(XingCloudSFSExtension.class);

	public XingCloud(SFSExtension ext)
	{
		this.contexts = new CopyOnWriteArrayList<Context>();
	
		XingCloudApplication application = XingCloudApplication.getInstance();
		application.setBasePath(super.getClass().getClassLoader()
				.getResource(".").getPath()
				+ ext.getCurrentFolder());
		application.start();
		if (!(application.hasParameter("security.service.provider"))) {
			application.setParameter("security.service.provider",
			"com.xingcloud.framework.security.SessionAuthenticationProvider");
		}
		try {
			Map<?, ?> map = (Map<?, ?>) XingCloudApplication.getInstance()
					.getParameter("sfs.entry");
			if (map != null) {
				for (Entry<?, ?> entry : map.entrySet()) {
					BeanConfig beanConfig = (BeanConfig) entry.getValue();
					Context context = (Context) Class.forName(
							beanConfig.getClassName()).newInstance();
					context.start();
					this.contexts.add(context);
				}
			}
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	public void destroy() {
		for (Context context : this.contexts) {
			context.close();
		}
		this.contexts.clear();
		this.contexts = null;
		XingCloudApplication.getInstance().close();
	}

}
