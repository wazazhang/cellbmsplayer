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
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.util.AbstractFrame;
import com.thoughtworks.xstream.XStream;

public abstract class ObjectViewer<T extends ObjectNode<?>> extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final protected T tobject;
	
	protected JTabbedPane 		table = new JTabbedPane();
	protected RPGObjectPanel	page_object_panel;
	protected AbilityPanel		page_abilities;
	
	public ObjectViewer(T object, CellEditAdapter<?> ... adapters) 
	{
		this.tobject = object;
		this.setLayout(new BorderLayout());
		{
//			page_properties = new JPanel();
//			table.addTab("属性", page_properties);
		} {
			page_object_panel = new RPGObjectPanel(object.getData(), adapters);
			table.addTab("RPG属性", page_object_panel);
		} {
			page_abilities = new AbilityPanel(object.getData(), adapters);
			table.addTab("能力", page_abilities);
		}
		
		appendPages(table);
		
		this.add(table);
		
	}
	
	final public JTabbedPane getPages() {
		return table;
	}
	
	final public T getRPGObject() {
		return tobject;
	}
	
	abstract protected void appendPages(JTabbedPane table) ;
	

//	-------------------------------------------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	
}
