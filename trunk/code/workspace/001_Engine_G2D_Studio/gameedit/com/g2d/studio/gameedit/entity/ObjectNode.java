package com.g2d.studio.gameedit.entity;

import java.awt.Component;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.ImageIcon;

import com.cell.CIO;
import com.cell.CUtil;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.swing.G2DTreeNode;
import com.thoughtworks.xstream.XStream;

public abstract class ObjectNode extends G2DTreeNode<ObjectNode>
{

	public ObjectNode() {

	}

	public abstract String getID();
	
	@Override
	protected ImageIcon createIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	
	public Component getEditComponent(){
		return null;
	}
	
	final public void load(ZipInputStream zip_in, ZipEntry entry)
	{			
		XStream xstream = new XStream();
		try {
			String xml = new String(CIO.readBytes(zip_in), "UTF-8");
			StringReader reader = new StringReader(xml);
			ObjectInputStream ois = xstream.createObjectInputStream(reader);
			readExternal(ois);
			ois.close();
			getIcon(true);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	final public ZipEntry save(ZipOutputStream zip_out, String dir)
	{
		ZipEntry entry = new ZipEntry(dir+"/"+getID()+".xml");
		XStream xstream = new XStream();
		try{
			zip_out.putNextEntry(entry);
			StringWriter writer = new StringWriter(1024);
			ObjectOutputStream oos = xstream.createObjectOutputStream(writer);
			writeExternal(oos);
			oos.close();
			zip_out.write(writer.toString().getBytes("UTF-8"));
		}catch(Exception err){
			err.printStackTrace();
		}
		return entry;
	}
	
	public static String getID(ZipEntry entry)
	{
		String[] split = CUtil.splitString(entry.getName(), "/");
		String xls_id = CUtil.replaceString(split[1], ".xml", "");
		return xls_id;
	}
	
	public static String getTypeName(ZipEntry entry)
	{
		String[] split = CUtil.splitString(entry.getName(), "/");
		return split[0];
	}
}
