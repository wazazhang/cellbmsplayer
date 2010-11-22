package com.cell.util.pack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.cell.io.CFile;
import com.cell.util.zip.ZipUtil;

public class Packer 
{

	
	
	
	
	
//	
//	
//	public static byte[] pakFiles(File dir)
//	{
//		try {
//
//
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//		return null;
//	}
//	
//	
//	
//	
//	public static void main(String[] args)
//	{
//		try {
//			File file = new File("C:\\Documents and Settings\\WAZA\\桌面\\test\\a.txt");
//		
//			byte[] data = CFile.readData(file);
//			
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			ZipOutputStream zos = new ZipOutputStream(baos);
//			zos.setComment("");
//			zos.setLevel(9);
//			zos.setMethod(ZipOutputStream.DEFLATED);
//			ZipEntry e = new ZipEntry("a.txt");
//			e.setTime(0);
//			zos.putNextEntry(e);
//			zos.write(data);
//			zos.close();
//			
//			CFile.wirteData(new File(file.getParentFile(), System.currentTimeMillis() + ".pak"), baos.toByteArray());
//			
//		} catch (Exception err) {
//			err.printStackTrace();
//		}
//	}
//	
}
