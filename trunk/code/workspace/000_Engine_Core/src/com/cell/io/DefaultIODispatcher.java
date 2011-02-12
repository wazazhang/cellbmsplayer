package com.cell.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.cell.CIO;

public class DefaultIODispatcher implements IODispatcher
{
	private int						url_loading_time_out	= 20000; //ms
	private int						url_loading_retry_count	= 5;
	private AtomicLong				loaded_bytes			= new AtomicLong(0);

	
	/**
	 * 覆盖获得JAR包内容
	 * @param path
	 * @return
	 */
	protected InputStream getJarResource(String path) 
	{
		return getClass().getClassLoader().getResourceAsStream(path);
	}
	
	/**
	 * 覆盖获得 res: 协议资源内容
	 * @param path
	 * @param timeout
	 * @return
	 */
	protected ResInputStream getRemoteResource(String path) throws IOException
	{
		return null;
	}
	
	/**
	 * 覆盖获得 ftp: 协议资源内容
	 * @param path
	 * @param timeout
	 * @return
	 */
	protected InputStream getFTPResource(String path) throws IOException
	{
		return null;
	}
	

	protected byte[] loadRemoteData(String file)
	{
		ResInputStream is = null;
		for (int i = Math.max(1, url_loading_retry_count); i > 0; --i) {
			try {
				is = getRemoteResource(file);
				int len = is.getLength();
				if (len > 0) {
					int actual = 0;
					int bytesread = 0;
					byte[] data = new byte[len];
					while ((bytesread != len) && (actual != -1)) {
						actual = is.read(data, bytesread, len - bytesread);
						bytesread += actual;
					}
					loaded_bytes.addAndGet(data.length);
					return data;
				} else if (len == 0) {
					return new byte[0];
				} else {
					return null;
				}
			} catch (SocketTimeoutException err) {
				System.err.println("timeout retry load url data : " + file);
			}  catch (IOException err) {
				err.printStackTrace();
				System.err.println("retry load url data : " + file);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {}
				}
			}
		}
		
		return null;
	}

	protected byte[] loadURLData(URL url)
	{
		URLConnection c = null;
		InputStream is = null;
		for (int i = Math.max(1, url_loading_retry_count); i > 0; --i) {
			try {
				c = url.openConnection();
				c.setConnectTimeout(url_loading_time_out);
				c.setReadTimeout(url_loading_time_out);
				c.connect();
				is = c.getInputStream();
				int len = c.getContentLength();
				if (len > 0) {
					int actual = 0;
					int bytesread = 0;
					byte[] data = new byte[len];
					while ((bytesread != len) && (actual != -1)) {
						actual = is.read(data, bytesread, len - bytesread);
						bytesread += actual;
					}
					loaded_bytes.addAndGet(data.length);
					return data;
				} else if (len == 0) {
					return new byte[0];
				} else {
					return null;
				}
			} catch (SocketTimeoutException err) {
				System.err.println("timeout retry load url data : " + url);
			}  catch (IOException err) {
				err.printStackTrace();
				System.err.println("retry load url data : " + url);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {}
				}
//				if (c instanceof HttpURLConnection) {
//					((HttpURLConnection)c).disconnect();
//				}
			}
		}
		
		return null;
	}
	
	protected byte[] loadFTPData(String path)
	{
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------
	
	@Override
	public InputStream getInputStream(String path) 
	{
		path = path.trim();
		
		try
		{
			if (path.startsWith("/")) 
			{
				return getJarResource(path);
			} 
			else if (path.startsWith("res://"))
			{
				return new RemoteResInputStream(getRemoteResource(path));
			} 
			else if (path.startsWith("ftp://")) 
			{
				return getFTPResource(path);
			}
			else if (path.startsWith("http://"))
			{
				try {
					URL url = new URL(path);
					return new URLInputStream(url, url_loading_time_out);
				} catch (MalformedURLException err) {}
			} 
			else if (path.startsWith("file:///"))
			{
				File file = new File(path.substring(8));
				if (file.exists()) {
					return new FileInputStream(file);
				}
			} 
			else
			{
				File file = new File(path);
				if (file.exists()) {
					return new FileInputStream(file);
				}
			}
		}
		catch(Exception err) {
			System.err.println(path);
			err.printStackTrace();
		}
		return null;
	}
	
	@Override
	public byte[] loadData(String path)
	{
		path = path.trim();
		byte[] data = null;
		try
		{
			// load from jar
			if (path.startsWith("/")) {
				return CIO.readStream(getJarResource(path));
			}
			// user define resource
			else if (path.startsWith("res://")) {
				return loadRemoteData(path);
			}
			// load from http
			else if (path.startsWith("ftp://")) {
				return loadFTPData(path);
			}
			// load from http
			else if (path.startsWith("http://")) {
				try {
					return loadURLData(new URL(path));
				} catch (MalformedURLException err) {
				}
			}
			// load from file
			else if (path.startsWith("file:///"))
			{
				File file = new File(path.substring(8));
				if (file.exists()) {
					return CIO.readStream(new FileInputStream(file));
				}
			} 
			// load from file
			else {
				File file = new File(path);
				if (file.exists()) {
					return CIO.readStream(new FileInputStream(file));
				}
			}
		} catch(Throwable err) {
			System.err.println(path);
			err.printStackTrace();
		}
		return data;
	
	}

	/**
	 * 获得已从CIO读取的字节数
	 * @return
	 */
	public long getLoadedBytes() {
		return loaded_bytes.get();
	}

	/**读取数据的超时时间*/
	public void setLoadingTimeOut(int loadingTimeOut) {
		url_loading_time_out = loadingTimeOut;
	}
	
	/**读取数据的超时时间*/
	public int getLoadingTimeOut() {
		return url_loading_time_out;
	}

	/**读取数据的重复次数*/
	public void setLoadRetryCount(int loadRetryCount) {
		url_loading_retry_count = Math.max(1, loadRetryCount);
	}
	
	/**读取数据的重复次数*/
	public int getLoadRetryCount() {
		return url_loading_retry_count;
	}
	
	
//	------------------------------------------------------------------------------------------------------------------------
	

	abstract public class LengthInputStream<T extends InputStream> extends InputStream
	{
		protected T 			src;
		
		protected AtomicInteger	readed = new AtomicInteger(0);	
				
		abstract public int getLength();
		
		@Override
		public int available() throws IOException {
			synchronized (readed) {
				return getLength() - readed.get();
			}
		}

		@Override
		public int read() throws IOException {
			synchronized (readed) {
				int b = src.read();
				if (b >= 0) {
					readed.incrementAndGet();
					loaded_bytes.incrementAndGet();
				}
				return b;
			}
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			synchronized (readed) {
				int count = src.read(b, off, len);
				if (count >= 0) {
					readed.addAndGet(count);
					loaded_bytes.addAndGet(count);
				}
				return count;
			}
		}
		
		@Override
		public void close() throws IOException {
			src.close();
		}

		@Override
		public void mark(int readlimit) {}

		@Override
		public boolean markSupported() {
			return false;
		}
	}

	
	
//	------------------------------------------------------------------------------------------------------------------------

	public class RemoteResInputStream extends LengthInputStream<ResInputStream>
	{
		public RemoteResInputStream(ResInputStream res) throws IOException {
			this.src = res;
		}
		
		@Override
		public int getLength() {
			return src.getLength();
		}
	}

//	-----------------------------------------------------------------------------------------------------------------

	public class URLInputStream extends LengthInputStream<InputStream>
	{
		protected URLConnection	connection;
		
		private AtomicInteger	length;
		
		public URLInputStream(URL url, int timeout) throws IOException 
		{
			this.connection = url.openConnection();
			this.connection.setConnectTimeout(timeout);
			this.connection.setReadTimeout(timeout);
			this.connection.connect();
			this.src = connection.getInputStream();
		}
		
		@Override
		public int getLength() {
			synchronized (readed) {
				if (length == null) {
					length = new AtomicInteger(connection.getContentLength());
				}
				return length.get();
			}
		}
		
	}

	
}
