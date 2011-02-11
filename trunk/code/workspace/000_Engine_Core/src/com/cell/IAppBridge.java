package com.cell;

import java.io.IOException;
import java.io.InputStream;

import com.cell.io.ResInputStream;


public interface IAppBridge 
{
	public Thread 			createTempThread();
	public Thread 			createTempThread(Runnable run);
	public Thread 			createServiceThread();
	public Thread 			createServiceThread(Runnable run);
	
	public InputStream		getResource(String file);
	
	public ResInputStream	getRemoteResource(String file, int timeout) throws IOException;
	
	public void 			setClipboardText(String str);
	public String 			getClipboardText();
	
	public String 			getMacAddr();
	public int 				getPing(String host, int bufferSize);

	public String 			getAppProperty(String key);
	public String			setAppProperty(String key, String value);
	
	public ClassLoader		getClassLoader();

	public void 			openBrowser(String url);
}
