package com.cell.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.cell.CObject;

public class CFile 
{

	public static String readText(java.io.File file, String encoding)
	{
		String ret = null;
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			ret = new String(data, encoding);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (Exception e2) {}
		}
		return ret;
	}
	
	public static void writeText(java.io.File file, String text, String encoding)
	{
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] data = text.getBytes(encoding);
			FileOutputStream fos = new FileOutputStream(file);
			try{
				fos.write(data);
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				fos.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readText(java.io.File file)
	{
		return readText(file, CObject.ENCODING);
	}
	
	public static void writeText(java.io.File file, String text)
	{
		writeText(file, text, CObject.ENCODING);
	}
	
	public static byte[] readData(java.io.File file)
	{
		byte[] data = null;
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			data = new byte[fis.available()];
			fis.read(data);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (Exception e2) {}
		}
		return data;
	}
	
	
	public static void wirteData(java.io.File file, byte[] data)
	{
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			try{
				fos.write(data);
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				fos.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void copyFile(java.io.File src, java.io.File dst)
	{
		byte[] data = readData(src);
		wirteData(dst, data);
	}
}













