package com.fc.castle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PlatformService
{
	final protected Logger log = LoggerFactory.getLogger(getClass());
	
	private static PlatformService instance;

	public static PlatformService getInstance() {
		return instance;
	}
	
	public PlatformService() {
		instance = this;
	}
	
	public abstract void login(String user, String auth, LoginListener listener);
	
	public static interface LoginListener
	{
		public void onComplete(String user, String response_text);

		public void onError(String user, String reson);
		
	}
}
