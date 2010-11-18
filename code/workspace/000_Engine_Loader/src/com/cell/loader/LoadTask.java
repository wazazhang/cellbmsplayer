package com.cell.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Vector;

import com.cell.classloader.jcl.CC;

public class LoadTask extends Thread
{
//	-------------------------------------------------------------------------------------------------------------------------------

	public static interface LoadTaskListener
	{
		public void loadBegin(URL[] files);
		
		public void loadJarBegin(URL file, int current, int total);
		public void loadJarStart(URL file, int current, int total, int length);
		public void loadJarProgress(URL file, int current, int total, int actual);
		public void loadJarComplete(URL file, int current, int total, byte[] data);
		
		public void loadAllComplete(Vector<byte[]> datas);
		public void loadError(Throwable cause);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------

	public static int LoadRetryTime		= 5;
	
	public static int LoadTimeOut		= 5000;
	
	public static URLConnection openURL(URL url) throws IOException 
	{
		for (int i=0; i<LoadRetryTime; i++) 
		{
			try{
				URLConnection c = url.openConnection();
				c.setConnectTimeout(LoadTimeOut);
				c.setReadTimeout(LoadTimeOut);
				c.connect();
				return c;
			}
			catch (Throwable e) {
				e.printStackTrace();
				System.err.println("openURL timeout, retry " + (i+1) + "/" + LoadRetryTime);
			}
		}
		throw new IOException("openURL timeout : " + url.getPath());
	}
	
	private static byte[] loadURL(URLConnection c, int current, int total, LoadTaskListener listener) throws IOException 
	{
		for (int i=0; i<LoadRetryTime; i++) 
		{
			InputStream	is = null;
			try
			{
				is = c.getInputStream();
				int len = c.getContentLength();
				byte[] data = new byte[len];
				
				if (len > 0) {
					int actual = 0;
					int bytesread = 0;
					while ((bytesread != len) && (actual != -1)) {
						actual = is.read(data, bytesread, len - bytesread);
						bytesread += actual;
						if (listener!=null) {
							listener.loadJarProgress(c.getURL(), current, total, actual);
						}
					}
				}
				return data;
			}
			catch (Throwable e) {
				e.printStackTrace();
				System.err.println("loadURL error, retry " + (i+1) + "/" + LoadRetryTime);
			}
			finally {
				try {
					if (is != null) {
						is.close();
					}
				} catch (Exception err) {
				}
			}
		}
		
		throw new IOException("loadURL error : " + c.getURL().getPath());
	}

	public static byte[] loadURL(URLConnection c) throws IOException 
	{
		return loadURL(c, 1, 1, null);
	}
	
	public static String getVK(InputStream is)
	{
		try {
			byte[] vk_data = new byte[is.available()];
			is.read(vk_data);
			is.close();
			return new String(new CC("gametiler").dd(CC.h2b(new String(vk_data))));
		} catch (Exception err) {
			return null;
		}
	}

	public static byte[] load(String path) throws Exception
	{
		URL url = new URL(path);
		URLConnection c = openURL(url);
		return loadURL(c);
	}
	
	public static byte[] load(File file) throws Exception
	{
		FileInputStream fis = new FileInputStream(file);
		try {
			byte[] data = new byte[fis.available()];
			fis.read(data);
			return data;
		} finally {
			fis.close();
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------
	
	LoadTaskListener	Listener;
	URL[]				JarFiles;
	
	public LoadTask(LoadTaskListener listener, String[] jarFiles) throws MalformedURLException
	{
		System.out.println("LoadRetryTime="+LoadRetryTime);
		System.out.println("LoadTimeOut="+LoadTimeOut);
		
		Listener	= listener;
		JarFiles	= new URL[jarFiles.length];
		for (int i=0; i<jarFiles.length; i++) {
			JarFiles[i] = new URL(jarFiles[i]);
		}
		
		start();
	}
	
	public void run() 
	{
		System.out.println("start loading jars !");
		
		Listener.loadBegin(JarFiles);
		
		try 
		{
			Vector<byte[]> datas = new Vector<byte[]>();
			
			//读取所有文件
			for (int i=0; i<JarFiles.length; i++) 
			{
				URL url = JarFiles[i];
				
				System.out.println("open : " + url);
				Listener.loadJarBegin(url, i, JarFiles.length);
				
				URLConnection c = openURL(url);
				Listener.loadJarStart(url, i, JarFiles.length, c.getContentLength());
				
				byte[] data = loadURL(c, i, JarFiles.length, Listener);
				datas.addElement(data);
				
				System.out.println("loaded : " + url.getFile() + "(" + data.length + ")");
				Listener.loadJarComplete(url, i, JarFiles.length, data);
				
				Thread.yield();
			}
			
			System.out.println("loaded complete !");
			Listener.loadAllComplete(datas);

		}
		catch (Exception err)
		{
			err.printStackTrace();
			Listener.loadError(err);
		}

		System.out.println("load task thread exited !");
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------

}
