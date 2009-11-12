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
public class RPGObjectMap<T extends RPGObject> extends Hashtable<String, T> implements ZipStreamFilter
{
	private static final long serialVersionUID = 1L;
	
	final public Class<T>	type;
	final public File		zip_file;
	
	
	public RPGObjectMap(Class<T> type, File zip_file) 
	{
		this.type 		= type;
		this.zip_file	= zip_file;
		loadAll();
	}
	
	@Override
	public ZipNode readNode(ByteArrayInputStream bais, Class<? extends ZipNode> type) throws Exception {
		XStream xstream = new XStream();
		String xml = new String(CIO.readBytes(bais), "UTF-8");
		StringReader reader = new StringReader(xml);
		ObjectInputStream ois = xstream.createObjectInputStream(reader);
		try{
			return type.cast(ois.readObject());
		}finally{
			ois.close();
		}
	}
	
	@Override
	public void writeNode(ByteArrayOutputStream baos, ZipNode node) throws Exception {
		XStream xstream = new XStream();
		StringWriter writer = new StringWriter(1024);
		ObjectOutputStream oos = xstream.createObjectOutputStream(writer);
		try{
			oos.writeObject(node);
		}finally{
			oos.close();
		}
		String xml = writer.toString();
		baos.write(xml.getBytes("UTF-8"));
	}
	
//	-----------------------------------------------------------------------------------------------------------------------

	
	public void loadAll()
	{
		if (zip_file.exists()) 
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(com.cell.io.File.readData(zip_file));
			try {
				for (T t : ZipNodeManager.loadAll(bais, type, this)) {
					put(t.id, t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveAll()
	{
		if (!zip_file.exists()) {
			zip_file.getParentFile().mkdirs();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024*20);
		try {
			ZipNodeManager.saveAll(baos, values(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.cell.io.File.wirteData(zip_file, baos.toByteArray());
	}


//	------------------------------------------------------------------------------------------------------------------------------

	
}
