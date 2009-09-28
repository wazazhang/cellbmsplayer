package com.cell;

import java.io.InputStream;
import java.util.Collection;

import com.cell.gfx.IImage;


public interface IAppBridge 
{
	
	public Thread 		createTempThread();
	public Thread 		createTempThread(Runnable run);
	public Thread 		createServiceThread();
	public Thread 		createServiceThread(Runnable run);
	
	public InputStream	getResource(String file);
	
	public IImage 		createImage(String filename);
	public IImage 		createImage(InputStream is);
	public IImage 		createImage(int w, int h);
	public IImage 		createImage(int argb, int w, int h);
	
	public void 		setClipboardText(String str);
	public String 		getClipboardText();
	
	public void 		openBrowser(String url);
	public String 		getMacAddr();
	public int 			getPing(String host, int bufferSize);

	public String 		getAppProperty(String key);
	public String		setAppProperty(String key, String value);
	
	public ClassLoader	getClassLoader();
	
//	interface IAppStateListener{
//		public void appletRefresh();
//		public void appExited();
//	}
	
//	public void			addAppStateListener(IAppStateListener listener);
//	public void			removeAppStateListener(IAppStateListener listener);
//	
//	public Collection<IAppStateListener> getAppListeners();
	
}
