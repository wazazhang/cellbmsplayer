package com.cell.util.pack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import com.cell.io.CFile;
import com.cell.util.zip.ZipUtil;

public class Packer 
{

	
	
	
	
	
	
	
	public static byte[] pakFiles(File dir)
	{
		try {
			ArrayList<File> packs = new ArrayList<File>();
			for (File file : dir.listFiles()) {
//				if (file.getName().toLowerCase().endsWith(suffix)) {
//					packs.add(file);
//				}
			}
			if (!packs.isEmpty()) {
				ByteArrayOutputStream baos = ZipUtil.packFiles(packs);
				return baos.toByteArray();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
