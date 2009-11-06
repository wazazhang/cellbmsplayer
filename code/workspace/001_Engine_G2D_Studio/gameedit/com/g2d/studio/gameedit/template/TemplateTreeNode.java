package com.g2d.studio.gameedit.template;

import java.awt.Component;
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

import javax.swing.JComponent;
import javax.swing.JPanel;

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
	
	final public File getSaveFile()
	{
		return new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +
			"template" + File.separatorChar +
			getClass().getSimpleName().toLowerCase() + File.separatorChar +
			xls_fullrow.id+".xml");
	}
	
	public ObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new ObjectViewer<TemplateTreeNode>(this);
		}
		return edit_component;
	}
	
	final public void load()
	{
		File xls_file = getSaveFile();
		try {
			if (xls_file.exists()) {
				byte[] data = com.cell.io.File.readData(xls_file);
				if (data!=null) {
					String text_data = new String(data, "UTF-8");
					Reader reader = new StringReader(text_data);
					XStream xstream = new XStream();
					ObjectInputStream in = xstream.createObjectInputStream(reader);
					try{
						readExternal(in);
					}finally{
						in.close();
					}
				}
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}
	
	final public void save()
	{
		File xls_file = getSaveFile();
		try {
			if (!xls_file.exists()) {
				xls_file.getParentFile().mkdirs();
			}
			Writer writer = new StringWriter(1024);
			try{
				XStream xstream = new XStream();
				ObjectOutputStream out = xstream.createObjectOutputStream(writer);
				try{
					writeExternal(out);
				}finally{
					out.close();
				}
				writer.flush();
				String text_data = writer.toString();
				com.cell.io.File.wirteData(xls_file, text_data.getBytes("UTF-8"));
			}finally{
				writer.close();
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
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
			}
		}
		return ret;
	}
	
}
