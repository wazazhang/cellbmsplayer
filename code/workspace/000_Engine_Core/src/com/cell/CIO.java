
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
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cell.io.ResInputStream;
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
	private static Logger					log						= Logger.getLogger("CIO");
	
//	------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * <pre>
	 * <b>得到流用于异步动态读取</b>
	 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。
	 * 比如: http:// ftp:// file:///
	 * </pre>
	 * @param path
	 * @return
	 */
	public static InputStream getInputStream(String path)
	{
		return getAppBridge().getIO().getInputStream(path);
	}
	
	/**
	 * <pre>
	 * <b>一次性同步读取所有资源</b>
	 * 获取字符串对应的资源，可能是网络资源，也可能是本地资源或文件。
	 * 比如: http:// ftp:// file:///
	 * </pre>
	 * @param path
	 * @return
	 */
	public static byte[] loadData(String path)
	{
		for (int i = Math.max(1, getLoadRetryCount()); i > 0; --i) 
		{
			InputStream is = getInputStream(path);
			if (is == null) {
				return null;
			}
			try {
				int available = is.available();
				if (is instanceof ResInputStream){
					available = ((ResInputStream)is).getLength();
				}
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
				return baos.toByteArray();
			} catch (SocketTimeoutException err) {
				System.err.println("timeout retry load data [" + is.getClass().getSimpleName() + "] : " + path);
			}  catch (IOException err) {
				err.printStackTrace();
				System.err.println("retry load data [" + is.getClass().getSimpleName() + "] : " + path);
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

	public static ByteArrayInputStream loadStream(String path) {
		byte[] data = loadData(path);
		if (data != null) {
			return new ByteArrayInputStream(data);
		} else {
			System.err.println("CIO.loadStream : null : " + path);
		}
		return null;
	}

//	------------------------------------------------------------------------------------------------------------------------
	
//	------------------------------------------------------------------------------------------------------------------------

//	------------------------------------------------------------------------------------------------------------------------
	
	public static String stringDecode(byte[] data, String encoding) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		try {
			CharsetDecoder decoder = Charset.forName(encoding).newDecoder();  
			CharBuffer cb = decoder.decode(bb);
			try {
				char[] ret = new char[cb.remaining()];
				cb.get(ret);
				return new String(ret);
			} finally {
				cb.clear();
			}
		} catch (Exception err) {
			return null;
		} finally {
			bb.clear();
		}
	}
	
	public static byte[] stringEncode(String src, String encoding) {
		CharBuffer cb = CharBuffer.wrap(src);
		try {
			CharsetEncoder encoder = Charset.forName(encoding).newEncoder();  
			ByteBuffer bb = encoder.encode(cb);
			try {
				byte[] ret = new byte[bb.remaining()];
				bb.get(ret);
				return ret;
			} finally {
				bb.clear();
			}
		} catch (Exception err) {
			return null;
		} finally {
			cb.clear();
		}
	}
	
	public static String readAllText(String file)
	{
		return readAllText(file, CObject.getEncoding());
	}
	
	public static String readAllText(String file, String encoding)
	{
		InputStream is = getInputStream(file);
		if (is != null) {
			return readAllText(is, encoding);
		} else {
			System.err.println("file not found : "+file);
		}
		return null;
	}
	
	public static String[] readAllLine(String file)
	{
		return readAllLine(file, CObject.getEncoding());
	}
	
	public static String[] readAllLine(String file, String encoding)
	{
		InputStream is = getInputStream(file);
		if (is != null) {
			return readAllLine(is, encoding);
		} else {
			System.err.println(file);
			return new String[] { "" };
		}
	}

	public static String readAllText(InputStream is)
	{
		return readAllText(is, CObject.getEncoding());
	}
	
	public static String readAllText(InputStream is, String encoding)
	{
		byte[] data = CIO.readStream(is);
		if (data != null) {
			return stringDecode(data, encoding);
		}
		return null;
	}

	public static String[] readAllLine(InputStream is) {
		return readAllLine(is, CObject.getEncoding());
	}
	
	public static String[] readAllLine(InputStream is, String encoding)
	{
		try {
			String src = readAllText(is, encoding);
			String[] ret = CUtil.splitString(src, "\n");
			for (int i = ret.length - 1; i >= 0; i--) {
				int ld = ret[i].lastIndexOf('\r');
				if (ld >= 0) {
					ret[i] = ret[i].substring(0, ld);
				}
			}
			return ret;
		} catch (Exception err) {
			err.printStackTrace();
			return new String[] { "" };
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------
	
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
	
	public static byte[] objectToBin(Object src)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			oos.flush();
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object binToObject(byte[] data)
	{
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object ret = ois.readObject();
			ois.close();
			return ret;
		} catch (Exception e) {
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

//	-----------------------------------------------------------------------------------------------------------------

	/**
	 * 读取一个流的所有数据到字节序。<br>
	 * 只要InputStream里有数据，该方法都将阻塞，直到available=0，所以该方法不适合读取动态流。<br>
	 * <b>该方法将自动关闭流。</b>
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
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到available=0，
	 * 所以该方法不适合读取动态流。
	 * @param is
	 * @param percent 预计的进度 0~1
	 * @param block_size 每次读取的块大小
	 * @return 
	 */
	public static byte[] readStream(InputStream is, AtomicReference<Float> percent, int block_size) throws IOException
	{
		if (is != null) {
			int available = is.available();
			ByteArrayOutputStream baos = new ByteArrayOutputStream(is.available());
			do {
				byte[]	data	= new byte[available];
				int		count	= 0;
				while (count < available) {
					int block = Math.min(block_size, available - count);
					int actual = is.read(data, count, block);
					if (actual > 0) {
						baos.write(data, count, actual);
						count += actual;
						percent.set(count / (float) available);
//						try{
//							Thread.sleep(100);
//							System.out.println(percent.get());
//						}catch(Exception err){}
					} else {
						break;
					}
				}
//				loaded_bytes.addAndGet(data.length);
				available = is.available();
			} while (available > 0);
			return baos.toByteArray();
		}
		return null;
	}

	/**
	 * 只要InputStream里有数据，
	 * 该方法都将阻塞，直到available=0，
	 * 所以该方法不适合读取动态流。
	 * @param is
	 * @param 预计的进度 0~1
	 * @return 
	 */
	public static byte[] readStream(InputStream is, AtomicReference<Float> percent) throws IOException
	{
		return readStream(is, percent, Integer.MAX_VALUE);
	}
	
//	------------------------------------------------------------------------------------------------------------------------



	/**
	 * 获得已从CIO读取的字节数
	 * @return
	 */
	public static long getLoadedBytes() {
		return getAppBridge().getIO().getLoadedBytes();
	}

	/**读取数据的超时时间*/
	public static void setLoadingTimeOut(int loadingTimeOut) {
		getAppBridge().getIO().setLoadingTimeOut(loadingTimeOut);
	}
	
	/**读取数据的超时时间*/
	public static int getLoadingTimeOut() {
		return getAppBridge().getIO().getLoadingTimeOut();
	}

	/**读取数据的重复次数*/
	public static void setLoadRetryCount(int loadRetryCount) {
		getAppBridge().getIO().setLoadRetryCount(loadRetryCount);
	}
	/**读取数据的重复次数*/
	public static int getLoadRetryCount() {
		return getAppBridge().getIO().getLoadRetryCount();
	}

}