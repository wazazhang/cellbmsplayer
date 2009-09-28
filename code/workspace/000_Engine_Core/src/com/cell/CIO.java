
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
	
//	public static InputStream openStream(String fileName) {
//		InputStream is = null;
//		try {
//			if(fileName!=null){
//				is = AppBridge.getResource(fileName); //fileName.getClass().getResourceAsStream(fileName);
//			}else{
//				System.err.println("File name is null -_-! ");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return is;
//	}

//	------------------------------------------------------------------------------------------------------------------------
//	
	static public int LoadingTimeOut = 20000; //ms
	
	static public long LoadedBytes = 0;
	
	static public int LoadRetryCount = 5;
	
	public static ByteArrayInputStream loadStream(String path) {
		byte[] data = loadData(path);
		if (data != null) {
			return new ByteArrayInputStream(data);
		}else{
			System.err.println("CIO.loadStream : null : " + path);
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
			if (path.startsWith("http://")) 
			{
				for (int i = 0; i < LoadRetryCount; i++)
				{
					data = CObject.File.syncReadBytesFromURL(path, LoadingTimeOut);
					if (data!=null){
						break;
					}
					System.err.println("load retry " + i + " : " + path);
				}
			}
			else if (path.startsWith("/"))
			{
				InputStream is = AppBridge.getResource(path);
				data = readStream(is);
			}
			else
			{
				InputStream is = new FileInputStream(new File(path));
				data = readStream(is);
			}
			
			if (data!=null){
				LoadedBytes += data.length;
			}
		}
		catch(Exception err){
			System.err.println("CIO.loadData error : " + path + " : " + err.getMessage());
		}
		return data;
	}
	
//	/**
//	 * load a file form class path to byte[]
//	 * @param fileName
//	 * @return 
//	 */
//	public static byte[] loadFile(String fileName) {
//		byte[] data = null; 
//		InputStream is = null;
//		try {
//			if(fileName!=null)
//			{
//				is = AppBridge.getResource(fileName); 
//				int dataSize = is.available();
//				int count = 0, i;		
//				data = new byte[dataSize];
//				while (true) 
//				{
//				    i = is.read(data, count, dataSize-count);
//				    if (i <= 0) break;
//				    count += i;
//				}
//				is.close();
//				is = null;
//			}else{
//				System.err.println("File name is null -_-! ");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		if(data!=null){
////			println("Loaded File '" + fileName + "' " + data.length + " bytes ^_^!");
//		}else{
//			System.err.println("Can not Load File '" + fileName + "' -_-!");
//		}
//		return data;
//	}
	
	/**
	 * load a file form res to byte[]
	 * @param fileName
	 * @return 
	 */
	public static byte[] readStream(InputStream is) {
		byte[] data = null; 
		try {
			if(is!=null){
				int dataSize = is.available();
				int count = 0, i;		
				data = new byte[dataSize];
				while (true) 
				{
				    i = is.read(data, count, dataSize-count);
				    if (i <= 0) break;
				    count += i;
				}
				is.close();
				is = null;
			}else{
				System.err.println("InputStream is null -_-! ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(data!=null){
//			println("Loaded File '" + fileName + "' " + data.length + " bytes ^_^!");
		}else{
			System.err.println("Can not Load InputStream -_-!");
		}
		return data;
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
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
	
}