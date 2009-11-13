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
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.cell.CIO;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFile;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.rpg.xls.XLSRow;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.TemplateObjectViewer;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.thoughtworks.xstream.XStream;

public abstract class XLSTemplateNode<T extends TemplateNode> extends ObjectNode<T>
{
	final protected T	template_data;
	final XLSFile		xls_file;
	final XLSFullRow	xls_fullrow;
	
	transient TemplateObjectViewer<?> edit_component;;
	
	@SuppressWarnings("unchecked")
	XLSTemplateNode(XLSFile xls_file, XLSFullRow xls_row, TemplateNode data) 
	{
		this.xls_file		= xls_file;
		this.xls_fullrow	= xls_row;
		this.template_data	= (data == null) ? newData(xls_file, xls_row) : (T)data;
//		System.out.println("read a xls row : " + xls_file.xls_file + " : " + xls_fullrow.id + " : " + xls_fullrow.desc);
	}
	
	/**
	 * 根据xls创建新的数据对象，当构造函数无法得到数据对象时自动创建
	 * @param xls_file
	 * @param xls_row
	 * @return
	 */
	abstract protected T newData(XLSFile xls_file, XLSFullRow xls_row) ;
	
	/**
	 * 获得当前实时数据对象
	 * @return
	 */
	public T getData() {
		return template_data;
	}
	
	final public XLSFile getXLSFile() {
		return xls_file;
	}
	
	final public XLSFullRow getXLSRow() {
		return xls_fullrow;
	}
	
	@Override
	final public String getName() {
		return getXLSRow().desc;
	}

	@Override
	final public String getID() {
		return getXLSRow().id;
	}

	public TemplateObjectViewer<?> getEditComponent(){
		if (edit_component==null) {
			edit_component = new TemplateObjectViewer<XLSTemplateNode<?>>(this);
		}
		return edit_component;
	}

//	----------------------------------------------------------------------------------------------------------------------
	
	static public <D extends ObjectNode<?>> ArrayList<D> listXLSRows(File file, Class<D> cls, RPGObjectMap<?> data_map)
	{
		ArrayList<D> ret = new ArrayList<D>();
		for (XLSFullRow row : XLSFullRow.getXLSRows(file, XLSFullRow.class)) 
		{
			TemplateNode data = (TemplateNode)data_map.get(row.id);
			if (cls.equals(XLSUnit.class)) {
				ret.add(cls.cast(new XLSUnit(row.xls_file, row, data)));
			} else if (cls.equals(XLSItem.class)) {
				ret.add(cls.cast(new XLSItem(row.xls_file, row, data)));
			} else if (cls.equals(XLSSkill.class)) {
				ret.add(cls.cast(new XLSSkill(row.xls_file, row, data)));
			}
		}
		return ret;
	}
	
}
