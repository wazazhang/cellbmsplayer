package com.g2d.studio.gameedit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.cell.util.zip.ZipExtNode;
import com.cell.util.zip.ZipNodeManager;
import com.cell.util.zip.ZipStreamFilter;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.TItem;
import com.g2d.studio.gameedit.template.TNpc;
import com.g2d.studio.gameedit.template.TSkill;
import com.g2d.studio.gameedit.template.TemplateNode;
import com.thoughtworks.xstream.XStream;

public class ObjectStreamFilter implements ZipStreamFilter
{
	ArrayList<TNpc>		npcs		= TemplateNode.listXLSRows(TNpc.class);
	ArrayList<TItem>	items		= TemplateNode.listXLSRows(TItem.class);
	ArrayList<TSkill>	skills		= TemplateNode.listXLSRows(TSkill.class);
	
	ObjectStreamFilter()
	{
		
	}
	
	
	@Override
	public ObjectInputStream createObjectInputStream(InputStream is) throws IOException {
		XStream xstream = new XStream();
		return xstream.createObjectInputStream(is);
	}
	
	@Override
	public ObjectOutputStream createObjectOutputStream(OutputStream os) throws IOException {
		XStream xstream = new XStream();
		return xstream.createObjectOutputStream(os);
	}
	
	@Override
	public ZipExtNode createExtNode(ZipEntry entry, Class<?> type) {
		return null;
	}
	

	public static <T extends ObjectNode<?>> void saveAll(Enumeration<T> enums, File zip_file)
	{
		Vector<T> list = new Vector<T>();
		while (enums.hasMoreElements()) {
			list.add(enums.nextElement());
		}
		saveAll(list, zip_file);
	}
	
	public static <T extends ObjectNode<?>> void saveAll(Collection<T> list, File zip_file)
	{
		if (!zip_file.exists()) {
			zip_file.getParentFile().mkdirs();
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024*20);
		try {
			ZipNodeManager.saveAll(baos, list, new ObjectStreamFilter());
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.cell.io.File.wirteData(zip_file, baos.toByteArray());
	}
	
	public static <T extends ObjectNode<?>> Vector<T> loadAll(File zip_file, Class<T> type)
	{
		if (zip_file.exists()) 
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(com.cell.io.File.readData(zip_file));
			try {
				return ZipNodeManager.loadAll(bais, type, new ObjectStreamFilter());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new Vector<T>();
	}
	
	
}
