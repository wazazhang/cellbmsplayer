package com.cell.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.util.zip.ZipUtil;

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
	
	
	static public void deleteFiles(File png, String suffix) {
		File[] list = png.listFiles();
		if (list != null) {
			for (File _jpg : list) {
				if (_jpg.getName().toLowerCase().endsWith(suffix)) {
					_jpg.delete();
				}
			}
		}
	}
}













