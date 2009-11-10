package com.cell.util.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;


final public class ZipNodeManager
{
	public static int ZIP_LEVEL = ZipOutputStream.STORED;

//	---------------------------------------------------------------------------------------------------------------
	
	public static <T extends ZipNode> void saveAll(
			OutputStream outputstream,
			Collection<T> list, 
			ZipStreamFilter filter) throws Exception
	{
		ZipOutputStream zip_out = new ZipOutputStream(outputstream);
		try {
			zip_out.setLevel(ZIP_LEVEL);
			for (T object : list) {
				save(zip_out, object, filter);
			}
		} catch(Throwable ex) {
			ex.printStackTrace();
		} finally{
			try {
				zip_out.close();
			} catch (IOException e) {}
		}
	}

	public static ZipEntry save(
			ZipOutputStream zip_out, 
			ZipNode node, 
			ZipStreamFilter filter)
	{
		ZipEntry entry = new ZipEntry(node.getEntryName());
		try{
			zip_out.putNextEntry(entry);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oos = filter.createObjectOutputStream(baos);
			try{
				if (node instanceof ZipExtNode) {
					((ZipExtNode) node).writeExternal(oos);
				} else {
					oos.writeObject(node);
				}
			}finally{
				oos.close();
				zip_out.write(baos.toByteArray());
			}
		} catch(Exception err){
			err.printStackTrace();
		}
		return entry;
	}
	
//	---------------------------------------------------------------------------------------------------------------
	
	public static <T extends ZipNode> Vector<T> loadAll(
			InputStream inputstream,
			Class<T> type, 
			ZipStreamFilter filter) throws Exception
	{
		Vector<T> ret = new Vector<T>(20);
		ZipInputStream zip_in = new ZipInputStream(inputstream);
		try{
			ZipEntry entry =  null;
			while ((entry = zip_in.getNextEntry()) != null) {
				T object = load(zip_in, entry, type, filter);
				if (object != null) {
					ret.add(object);
				}
			}
		}finally{
			try {
				zip_in.close();
			} catch (IOException e) {}
		}
		return ret;
	}
	
	final static public <T extends ZipNode> T load(
			ZipInputStream zip_in,
			ZipEntry entry, 
			Class<T> type, 
			ZipStreamFilter filter)
	{
		T ret = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(CIO.readBytes(zip_in));
			ObjectInputStream ois = filter.createObjectInputStream(bais);
			try{
				if (ZipExtNode.class.isAssignableFrom(type)) {
					ZipExtNode ext = filter.createExtNode(entry, type);
					ext.readExternal(ois);
				} else {
					ret = type.cast(ois.readObject());
				}
			}finally{
				ois.close();
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return ret;
	}

	
}
