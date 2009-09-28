package com.cell.net.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.cell.CIO;
import com.cell.security.MD5;

public class HttpQuery implements Runnable
{	
	final public String 		url;
	
	final HttpQueryListener 	listener;

	public String				charset	= "UTF-8";
	public String 				result;
	int 						timeout = 20000;
	
	public HttpQuery(String url, int timeout, HttpQueryListener listener) {
		this.url = url;
		this.timeout = timeout;
		this.listener = listener;
	}
	
	public HttpQuery(String url, int timeout, HttpQueryListener listener, String charset) {
		this.url		= url;
		this.timeout	= timeout;
		this.listener	= listener;
		this.charset	= charset;
	}
	
	public HttpQuery(String url, int timeout) {
		this.url = url;
		this.timeout = timeout;
		this.listener = null;
	}
	
	public void run() 
	{
		try
        {
        	URLConnection c;
        	
            InputStream is = null;
            
            URL Url = new URL(url);
          	 
            c = Url.openConnection();
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.setDoInput(true);
            c.connect();
            
            Thread.yield();
            
            is = c.getInputStream();
            	
            byte[] data = CIO.readStream(is);
            
            result = new String(data, charset);
            
            if (listener!=null) {
            	listener.response(url, result);
            }
        }
		catch(Throwable err)
		{
			err.printStackTrace();
			
			listener.timeout(url);
		}
		
	}
	
	public static String blockQuery(String url, int timeout)
	{
		HttpQuery query = new HttpQuery(url, timeout);
		query.run();
		return query.result;
	}
	
	public static void query(String url, HttpQueryListener listener)
	{
		query(url, 20000, listener);
	}

	public static void query(String url, int timeout, HttpQueryListener listener)
	{
		new Thread(new HttpQuery(url, timeout, listener)).start();
      
	}
	
	public static void main(String[] args) 
	{
//		{
//			String q1 = "http://download.java.net/media/joal/webstart/joal.jnlp";
//			query(q1, 10000, new HttpQueryListener() {
//				public void response(String url, String result) {
//					System.out.println(result);
//				}
//				public void timeout(String url) {
//					System.out.println("timeout from : " + url);
//				}
//			});
//		}
		{
			String q2 = "http://download.java.net/media/gluegen/webstart/gluegen-rt.jnlp";
			query(q2, 10000, new HttpQueryListener() {
				public void response(String url, String result) {
					System.out.println(result);
				}
				public void timeout(String url) {
					System.out.println("timeout from : " + url);
				}
			});
		}
	}
	
}
