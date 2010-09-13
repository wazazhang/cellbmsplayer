package com.cell.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.cell.CIO;
import com.cell.CObject;
import com.cell.CUtil;
import com.cell.util.zip.ZipUtil;

public class CFile 
{

	public static String readText(java.io.File file, String encoding)
	{
		try{
			FileInputStream fis = new FileInputStream(file);
			return CIO.stringDecode(CIO.readStream(fis), encoding);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeText(java.io.File file, String text, String encoding)
	{
		try{
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] data = CIO.stringEncode(text, encoding);
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
	
	
	/**
	 * 删除目录下指定后缀的所有文件
	 * @param png
	 * @param suffix
	 */
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
	
	/**
	 * 递归删除file，如果file是个目录，则递归其子目录全部删除
	 * @param file
	 */
	static public void deleteIfExists(File file) {
		if (file != null && file.exists()) {
			if (file.isDirectory()) {
				for (File sub : file.listFiles()) {
					deleteIfExists(sub);
				}
			}
			file.delete();
		}
	}
}













