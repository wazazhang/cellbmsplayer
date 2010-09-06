package com.cell.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.util.Pair;

public class ZipUtil 
{
	static public ByteArrayOutputStream packFiles(ArrayList<File> files)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			for (File file : files) {
				byte[] data = CFile.readData(file);
				if (data != null) {
					ZipEntry entry = new ZipEntry(file.getName());
					try{
						zip_out.putNextEntry(entry);
						zip_out.write(data);
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	static public ByteArrayOutputStream packFile(File file)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			byte[] data = CFile.readData(file);
			if (data != null) {
				ZipEntry entry = new ZipEntry(file.getName());
				try{
					zip_out.putNextEntry(entry);
					zip_out.write(data);
				} catch(Exception err){
					err.printStackTrace();
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}

	static public ByteArrayOutputStream packStreams(ArrayList<Pair<InputStream, String>> files)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			for (Pair<InputStream, String> file : files) {
				byte[] data = CIO.readStream(file.getKey());
				if (data != null) {
					ZipEntry entry = new ZipEntry(file.getValue());
					try{
						zip_out.putNextEntry(entry);
						zip_out.write(data);
					} catch(Exception err){
						err.printStackTrace();
					}
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	static public ByteArrayOutputStream packStream(InputStream is, String name)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zip_out = new ZipOutputStream(baos);
		try{
			byte[] data = CIO.readStream(is);
			if (data != null) {
				ZipEntry entry = new ZipEntry(name);
				try{
					zip_out.putNextEntry(entry);
					zip_out.write(data);
				} catch(Exception err){
					err.printStackTrace();
				}
			}
		} finally {
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
		return baos;
	}
	
	static public Map<String, ByteArrayInputStream> unPackFile(InputStream input)
	{
		HashMap<String, ByteArrayInputStream> inputs = new HashMap<String, ByteArrayInputStream>();
		
		ZipInputStream zip_in = new ZipInputStream(input);
		
		try{
			while (true) {
				try {
					ZipEntry entry =  zip_in.getNextEntry();
					if (entry != null) {
						ByteArrayInputStream bais = new ByteArrayInputStream(readBytes(zip_in));
						inputs.put(entry.getName(), bais);
					} else {
						break;
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
					break;
				}
			}
		}finally{
			try {
				zip_in.close();
			} catch (IOException e) {}
		}
		
		return inputs;
	}
	
	static byte[] readBytes(InputStream is) throws IOException {
		ByteArrayOutputStream data = new ByteArrayOutputStream(is.available());
		byte[] buffer = new byte[8192];
		int size;
		while (is.available() > 0) {
			size = is.read(buffer);
			if (size > 0) {
				data.write(buffer, 0, size);
			}
		}
		return data.toByteArray();
	}
}
