package com.g2d.studio.gameedit.template;

import java.awt.Component;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.cell.CIO;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.rpg.xls.XLSRow;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.swing.G2DTreeNode;
import com.thoughtworks.xstream.XStream;

public abstract class TemplateTreeNode extends G2DTreeNode<G2DTreeNode<?>>
{
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;
	
	transient ObjectViewer<?> edit_component;;
	
	TemplateTreeNode(XLSFile xls_file, XLSFullRow xls_row) {
		this.xls_file		= xls_file;
		this.xls_fullrow	= xls_row;
		System.out.println("read a xls row : " + xls_file.xls_file + " : " + xls_fullrow.id + " : " + xls_fullrow.desc);
	}

	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new ObjectViewer<TemplateTreeNode>(this);
		}
		return edit_component;
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
		ZipEntry entry = new ZipEntry(dir+"/"+getXLSRow().id+".xml");
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
	
	public XLSFile getXLSFile() {
		return xls_file;
	}
	
	public XLSFullRow getXLSRow() {
		return xls_fullrow;
	}
	
	@Override
	public String getName() {
		return getXLSRow().desc;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public static <T extends TemplateTreeNode> ArrayList<T> listXLSRows(File file, Class<T> cls)
	{
		ArrayList<T> ret = new ArrayList<T>();
		for (XLSFullRow row : XLSFullRow.getXLSRows(file, XLSFullRow.class)) {
			if (cls.equals(TNpc.class)) {
				TNpc npc = new TNpc(row.xls_file, row);
				ret.add(cls.cast(npc));
			} else if (cls.equals(TItem.class)) {
				TItem item = new TItem(row.xls_file, row);
				ret.add(cls.cast(item));
			} 
		}
		return ret;
	}
	
}
