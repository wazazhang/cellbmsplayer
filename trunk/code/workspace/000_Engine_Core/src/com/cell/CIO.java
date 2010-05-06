
package com.cell;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

import com.cell.security.MD5;

//import javax.microedition.lcdui.Image;
//import javax.microedition.rms.RecordStore;

/**
 * util IO function.</br> 
 * @author WAZA
 * @since 2006-11-28 
 * @version 1.0
 */
public class CIO extends CObject
{
//	------------------------------------------------------------------------------------------------------------------------

	static public int LoadingTimeOut = 20000; //ms
	
	static public long LoadedBytes = 0;
	
	static public int LoadRetryCount = 5;
	
//	------------------------------------------------------------------------------------------------------------------------

	/**
	 * load a InputStream res to byte[]<br>
	 * this will auto close InputStream
	 * @param is
	 * @return 
	 */
	public static byte[] readStream(InputStream is) {
		if (is != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
				int count = 0;
				while (is.available() > 0) {
					byte[] data = new byte[is.available()];
					int read_bytes = is.read(data);
					if (read_bytes <= 0) {
						break;
					} else {
						baos.write(data, 0, read_bytes);
						count += read_bytes;
					}
				}
				return baos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	/**
	 * @param path URL or class path or file
	 * @return
	 */
	public static byte[] loadData(String path)
	{
		path = path.trim();
		byte[] data = null;
		try
		{
			// load from url
			try {
				URL url = new URL(path);
				data = loadURLData(url, LoadingTimeOut, LoadRetryCount);
				if (data != null) {
					return data;
				}
			} catch (MalformedURLException err) {}


			// load from file
			if (data == null) {
				File file = new File(path);
				if (file.exists()) {
					data = readStream(new FileInputStream(file));
					if (data != null) {
						return data;
					}
				}
			}
			
			// load from jar
			{
				data = readStream(getAppBridge().getResource(path));
				if (data != null) {
					return data;
				}
			}

		} catch(Exception err) {
			err.printStackTrace();
		} finally {
			if (data != null) {
				LoadedBytes += data.length;
			}
		}
		return data;
	}
	


	public static byte[] loadURLData(URL url, int timeout, int retry_count)
	{
		URLConnection c = null;
		InputStream is = null;
		for (int i = Math.max(1, retry_count); i > 0; --i) {
			try {
				c = url.openConnection();
				c.setConnectTimeout(timeout);
				c.setReadTimeout(timeout);
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
					return data;
				} else if (len == 0) {
					return new byte[0];
				} else {
					return null;
				}
			} catch (IOException err) {
				err.printStackTrace();
				System.out.println("retry load url data : " + url);
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
//	------------------------------------------------------------------------------------------------------------------------
	
	public static ByteArrayInputStream loadStream(String path) {
		byte[] data = loadData(path);
		if (data != null) {
			return new ByteArrayInputStream(data);
		}else{
			System.err.println("CIO.loadStream : null : " + path);
		}
		return null;
	}
	
	
	public static String readAllText(String file)
	{
		try{
			return new String(CIO.loadData(file), CObject.getEncoding());
		}catch(Exception err){
			err.printStackTrace();
			return "";
		}
	}
	
	public static String readAllText(String file, String encoding)
	{
		try{
			return new String(CIO.loadData(file), encoding);
		}catch(Exception err){
			err.printStackTrace();
			return "";
		}
	}
	
	public static String[] readAllLine(String file)
	{
		try{
			String src = new String(CIO.loadData(file), CObject.getEncoding());
			String[] ret = CUtil.splitString(src, "\n");
			for(int i=ret.length-1;i>=0;i--){
				int ld = ret[i].lastIndexOf('\r');
				if(ld>=0){
					ret[i] = ret[i].substring(0,ld);
				}
			}
			return ret;
		}catch(Exception err){
			err.printStackTrace();
			return new String[]{""};
		}
	}
	
	public static String[] readAllLine(String file, String encoding)
	{
		try{
			String src = new String(CIO.loadData(file), encoding);
			String[] ret = CUtil.splitString(src, "\n");
			for(int i=ret.length-1;i>=0;i--){
				int ld = ret[i].lastIndexOf('\r');
				if(ld>=0){
					ret[i] = ret[i].substring(0,ld);
				}
			}
			return ret;
		}catch(Exception err){
			err.printStackTrace();
			return new String[]{""};
		}
	}
	
	@SuppressWarnings("unchecked")
	public static<T> T cloneObject(T src)
	{
		if (src == null) {
			return src;
		}
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.flush();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			T ret = (T)ois.readObject();
			
			ois.close();
			oos.close();
			
			ois = null;
			oos = null;
			bais = null;
			baos = null;
			
			return ret;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getPathDir(String file_path)
	{
		file_path		= file_path.replace('\\', '/');
		String dir 		= file_path.substring(0, file_path.lastIndexOf("/")+1);
		return dir;
	}
	
	public static String getPathName(String file_path)
	{
		file_path		= file_path.replace('\\', '/');
		String name		= file_path.substring(file_path.lastIndexOf("/")+1);
		return name;
	}

//	------------------------------------------------------------------------------------------------------------------------

	public static URL getResourceURL(String resource)
	{
		return CObject.getAppBridge().getClassLoader().getResource(resource);
	}
	
	public static InputStream getInputStream(String path)
	{
		path = path.trim();
		try {
			if (path.startsWith("http://")) {
				return getInputStream(new URL(path));
			} else if (path.startsWith("/")) {
				return getAppBridge().getResource(path);
			} else {
				return new FileInputStream(new File(path));
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public static URLInputStream getInputStream(URL url) {
		return getInputStream(url, LoadingTimeOut);
	}
	
	public static URLInputStream getInputStream(URL url, int timeOut)
	{
		try {
			return new URLInputStream(url, timeOut);
		} catch (IOException err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public static class URLInputStream extends InputStream
	{
		final private URLConnection c;
		final private InputStream is;
		private int length ;
		
		public URLInputStream(URL url, int timeout) throws IOException 
		{
			this.c = url.openConnection();
			this.c.setConnectTimeout(timeout);
			this.c.setReadTimeout(timeout);
			this.c.connect();
			this.length = c.getContentLength();
			this.is = c.getInputStream();
		}
		
		@Override
		public int available() throws IOException {
			return length;
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			return is.read(b, off, len);
		}

		@Override
		public int read(byte[] b) throws IOException {
			return is.read(b);
		}

		@Override
		public void close() throws IOException {
			is.close();
		}

		@Override
		public void mark(int readlimit) {}

		@Override
		public boolean markSupported() {
			return false;
		}

	}
}