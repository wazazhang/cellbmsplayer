
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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

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
	private static int				url_loading_time_out	= 20000; //ms
	private static int				url_loading_retry_count	= 5;
	private static AtomicLong		loaded_bytes			= new AtomicLong(0);
	

//	------------------------------------------------------------------------------------------------------------------------

	/**
	 * load a InputStream res to byte[]<br>
	 * this will auto close InputStream<br>
	 * 只要InputStream里有数据，该方法都将阻塞，直到available=0，所以该方法不适合读取动态流。
	 * @param is
	 * @return 
	 */
	public static byte[] readStream(InputStream is) {
		if (is != null) {
			try {
				int available = is.available();
				int count = 0;
				ByteArrayOutputStream baos = new ByteArrayOutputStream(available);
				while (available > 0) {
					byte[] data = new byte[available];
					int read_bytes = is.read(data);
					if (read_bytes <= 0) {
						break;
					} else {
						baos.write(data, 0, read_bytes);
						count += read_bytes;
						available = is.available();
					}
				}
				loaded_bytes.addAndGet(baos.size());
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


//	------------------------------------------------------------------------------------------------------------------------

	public static InputStream getInputStream(String path)
	{
		path = path.trim();

		try
		{
			// load from jar
			InputStream is = getAppBridge().getResource(path);
			if (is != null) {
				return is;
			}
			
			// load from file
			File file = new File(path);
			if (file.exists()) {
				return new FileInputStream(file);
			}

			// load from url
			try {
				URL url = new URL(path);
				return new URLInputStream(url, url_loading_time_out);
			} catch (MalformedURLException err) {}

		} catch(Exception err) {
			err.printStackTrace();
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
			// load from jar
			data = readStream(getAppBridge().getResource(path));
			if (data != null) {
				return data;
			}

			// load from file
			File file = new File(path);
			if (file.exists()) {
				data = readStream(new FileInputStream(file));
				if (data != null) {
					return data;
				}
			}

			// load from url
			try {
				URL url = new URL(path);
				data = loadURLData(url, url_loading_time_out, url_loading_retry_count);
				if (data != null) {
					return data;
				}
			} catch (MalformedURLException err) {}

		} catch(Exception err) {
			err.printStackTrace();
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
				if (c instanceof HttpURLConnection) {
					((HttpURLConnection)c).disconnect();
				}
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

//	-----------------------------------------------------------------------------------------------------------------

	public static URL getResourceURL(String resource)
	{
		return CObject.getAppBridge().getClassLoader().getResource(resource);
	}
	
	public static class URLInputStream extends InputStream
	{
		final private URLConnection	connection;
		final private InputStream 	inputstream;
		
		private AtomicInteger	length;
		private AtomicInteger	readed;			
		
		public URLInputStream(URL url, int timeout) throws IOException 
		{
			this.readed 		= new AtomicInteger(0);
			this.connection		= url.openConnection();
			this.connection.setConnectTimeout(timeout);
			this.connection.setReadTimeout(timeout);
			this.connection.connect();
			this.inputstream	= connection.getInputStream();
		}
		
		@Override
		public int available() throws IOException {
			synchronized (readed) {
				if (length == null) {
					length = new AtomicInteger(connection.getContentLength());
					return length.get();
				} else {
					return length.get() - readed.get();
				}
			}
		}

		@Override
		public int read() throws IOException {
			synchronized (readed) {
				int b = inputstream.read();
				if (b >= 0) {
					readed.incrementAndGet();
				}
				return b;
			}
		}

		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			synchronized (readed) {
				int count = inputstream.read(b, off, len);
				if (count >= 0) {
					readed.addAndGet(count);
				}
				return count;
			}
		}
		
		@Override
		public void close() throws IOException {
			inputstream.close();
		}

		@Override
		public void mark(int readlimit) {}

		@Override
		public boolean markSupported() {
			return false;
		}

	}

	public static URLInputStream getInputStream(URL url) {
		return getInputStream(url, url_loading_time_out);
	}
	
	public static URLInputStream getInputStream(URL url, int timeOut) {
		try {
			return new URLInputStream(url, timeOut);
		} catch (IOException err) {
			err.printStackTrace();
		}
		return null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------

	/**
	 * load a InputStream res to byte[]<br>
	 * this will auto close InputStream<br>
	 * 只要InputStream里有数据，该方法都将阻塞，直到available=0，所以该方法不适合读取动态流。
	 * @param is
	 * @param 进度
	 * @return 
	 */
	public static byte[] readStream(InputStream is, AtomicReference<Float> percent) throws IOException
	{
		if (is != null) {
			int available = is.available();
			ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
			do {
				byte[]	data	= new byte[available];
				int		count	= 0;
				while (count < available) {
					int actual = is.read(data, count, available - count);
					if (actual > 0) {
						baos.write(data, count, actual);
						count += actual;
						percent.set(count / (float) available);
					} else {
						break;
					}
				}
				loaded_bytes.addAndGet(data.length);
				available = is.available();
			} while (available > 0);
			return baos.toByteArray();
		}
		return null;
	}

//	------------------------------------------------------------------------------------------------------------------------



	/**
	 * 获得已从CIO读取的字节数
	 * @return
	 */
	public static long getLoadedBytes() {
		return loaded_bytes.get();
	}

	/**读取数据的超时时间*/
	public static void setLoadingTimeOut(int loadingTimeOut) {
		url_loading_time_out = loadingTimeOut;
	}
	/**读取数据的超时时间*/
	public static int getLoadingTimeOut() {
		return url_loading_time_out;
	}

	/**读取数据的重复次数*/
	public static void setLoadRetryCount(int loadRetryCount) {
		url_loading_retry_count = Math.max(1, loadRetryCount);
	}
	/**读取数据的重复次数*/
	public static int getLoadRetryCount() {
		return url_loading_retry_count;
	}

}