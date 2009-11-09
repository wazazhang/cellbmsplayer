package com.g2d.studio.gameedit.template;

import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cell.CIO;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.rpg.xls.XLSRow;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.thoughtworks.xstream.XStream;

public abstract class TemplateNode extends ObjectNode
{
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;
	
	transient TemplateObjectViewer<?> edit_component;;
	
	TemplateNode(XLSFile xls_file, XLSFullRow xls_row) {
		this.xls_file		= xls_file;
		this.xls_fullrow	= xls_row;
		System.out.println("read a xls row : " + xls_file.xls_file + " : " + xls_fullrow.id + " : " + xls_fullrow.desc);
	}
	
	public TemplateObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new TemplateObjectViewer<TemplateNode>(this);
		}
		return edit_component;
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
	public String getID() {
		return getXLSRow().id;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	public static <T extends TemplateNode> ArrayList<T> listXLSRows(Class<T> type) 
	{
		ArrayList<T> files = TemplateNode.listXLSRows(
				new File(
						Studio.getInstance().root_xls_path.getPath()+
						File.separatorChar+type.getSimpleName().toLowerCase()+
						Config.XLS_SUFFIX),
				type);
		return files;
	}

	public static <T extends TemplateNode> ArrayList<T> listXLSRows(File file, Class<T> cls)
	{
		ArrayList<T> ret = new ArrayList<T>();
		for (XLSFullRow row : XLSFullRow.getXLSRows(file, XLSFullRow.class)) {
			if (cls.equals(TNpc.class)) {
				TNpc npc = new TNpc(row.xls_file, row);
				ret.add(cls.cast(npc));
			} else if (cls.equals(TItem.class)) {
				TItem item = new TItem(row.xls_file, row);
				ret.add(cls.cast(item));
			} else if (cls.equals(TSkill.class)) {
				TSkill skill = new TSkill(row.xls_file, row);
				ret.add(cls.cast(skill));
			} 
		}
		return ret;
	}
	
}
