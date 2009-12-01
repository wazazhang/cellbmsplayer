package com.cell.rpg.io;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.rpg.RPGObject;
import com.cell.rpg.template.TemplateNode;
import com.cell.util.zip.ZipExtNode;
import com.cell.util.zip.ZipNode;
import com.cell.util.zip.ZipNodeManager;
import com.cell.util.zip.ZipStreamFilter;

import com.thoughtworks.xstream.XStream;

/**
 * 在构造时将所有对象读入，运行时动态你的添加删除对象，最后调用saveAll方法将所有对象存储到文件
 * @author WAZA
 * @param <T>
 */
public class RPGObjectMap<T extends RPGObject> extends Hashtable<String, T> //implements ZipStreamFilter
{
	private static final long serialVersionUID = 1L;
	
	final public Class<T>	type;
	final public File		zip_dir;
	final public File		zip_info;
	
	public RPGObjectMap(Class<T> type, File zip_dir) 
	{
		this.type 		= type;
		this.zip_dir	= zip_dir;
		this.zip_info	= new File(zip_dir, type.getSimpleName().toLowerCase()+".list");
		
		loadAll();
	}
	
//	@Override
//	public ZipNode readNode(ByteArrayInputStream bais, Class<? extends ZipNode> type) throws Exception {
//		XStream xstream = new XStream();
//		String xml = new String(CIO.readBytes(bais), "UTF-8");
//		StringReader reader = new StringReader(xml);
//		ObjectInputStream ois = xstream.createObjectInputStream(reader);
//		try{
//			return type.cast(ois.readObject());
//		}finally{
//			ois.close();
//		}
//	}
//	
//	@Override
//	public void writeNode(ByteArrayOutputStream baos, ZipNode node) throws Exception {
//		XStream xstream = new XStream();
//		StringWriter writer = new StringWriter(1024);
//		ObjectOutputStream oos = xstream.createObjectOutputStream(writer);
//		try{
//			oos.writeObject(node);
//		}finally{
//			oos.close();
//		}
//		String xml = writer.toString();
//		baos.write(xml.getBytes("UTF-8"));
//	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	
	synchronized public void loadAll()
	{
		if (zip_dir.exists()) 
		{
			String[] entrys = CIO.readAllLine(zip_info.getPath(), "UTF-8");
			
			for (String entry : entrys) {
				File f = new File(zip_dir, entry.trim());
				if (f.exists()) {
					T t = readNode(f.getPath(), type);
					if (t!=null) {
						put(t.id, t);
					}
				}
			}
		}
	}
	
	synchronized public void saveAll()
	{
		if (!zip_dir.exists()) {
			zip_dir.mkdirs();
		}
		
		Vector<String> keys = new Vector<String>(keySet());
		CUtil.sort(keys, CUtil.getStringCompare());
		StringBuffer info = new StringBuffer();
		for (String k : keys) {
			T v = get(k);
			if (writeNode(v, new File(zip_dir, v.getEntryName()))) {
				info.append(v.getEntryName()+"\n");
			}
		}
		
		com.cell.io.CFile.writeText(zip_info, info.toString(), "UTF-8");
	}


//	------------------------------------------------------------------------------------------------------------------------------

	public static<T extends RPGObject> T readNode(String xml_file, Class<T> type) {
		try{
			String xml = CIO.readAllText(xml_file, "UTF-8");
			StringReader reader = new StringReader(xml);
			ObjectInputStream ois = new XStream().createObjectInputStream(reader);
			try{
				T ret = type.cast(ois.readObject());
				if (ret != null) {
					ret.onReadComplete(ret, xml_file);
					Vector<RPGSerializationListener> rlisteners = ret.getRPGSerializationListeners();
					if (rlisteners != null) {
						for (RPGSerializationListener l : rlisteners) {
							l.onReadComplete(ret, xml_file);
						}
					}
				}
				return ret;
			}finally{
				ois.close();
			}
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static<T extends RPGObject> boolean writeNode(T node, File xml_file) {
		try{
			if (!xml_file.exists()) {
				xml_file.getParentFile().mkdirs();
			}
			StringWriter writer = new StringWriter(1024);
			ObjectOutputStream oos = new XStream().createObjectOutputStream(writer);
			try{
				Vector<RPGSerializationListener> wlisteners = node.getRPGSerializationListeners();
				if (wlisteners!=null) {
					for (int i=wlisteners.size()-1; i>=0; --i) {
						wlisteners.get(i).onWriteBefore(node, xml_file.getPath());
					}
				}
				node.onWriteBefore(node, xml_file.getPath());
				oos.writeObject(node);
			}finally{
				oos.close();
			}
			String xml = writer.toString();
			com.cell.io.CFile.writeText(xml_file, xml, "UTF-8");
			return true;
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
