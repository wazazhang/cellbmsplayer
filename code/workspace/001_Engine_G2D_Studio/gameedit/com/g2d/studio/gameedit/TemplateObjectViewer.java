package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cell.rpg.RPGObject;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.TemplateNode;
import com.g2d.util.AbstractFrame;
import com.thoughtworks.xstream.XStream;

public class TemplateObjectViewer<T extends TemplateNode> extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final  T tobject;
	
	protected JTabbedPane table = new JTabbedPane();
	
	protected JPanel page_properties;
	protected JTable page_xls_list;
	
	
	public TemplateObjectViewer(T object) 
	{
		this.tobject = object;
		this.setLayout(new BorderLayout());
		
		
		{
			page_properties = new JPanel();
			table.addTab("属性", page_properties);
		}
		{
			Vector<String> c0 = new Vector<String>(getRPGObject().getXLSRow().getColumns());
			String[][] datas = new String[c0.size()][];
			for (int i=0; i<c0.size(); i++) {
				String c = c0.get(i);
				datas[i] = new String[]{
						object.getXLSRow().getDesc(c),
						object.getXLSRow().getValue(c),
						};
			}
			page_xls_list = new JTable(
					datas,
					new String[]{"字段名","字段值"});
			table.addTab("XLS数据", new JScrollPane(page_xls_list));
		}
		
		this.add(table);
		
	}
	
	public T getRPGObject() {
		return tobject;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	
}
