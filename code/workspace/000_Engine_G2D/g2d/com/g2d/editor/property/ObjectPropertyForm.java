package com.g2d.editor.property;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;

import com.g2d.annotation.Property;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.Util;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;


/**
 * 该编辑器可将一个对象内所有的 {@link Property}标注的字段，显示在该控件内，并可以编辑。<br>
 * 用户可以自己写{@link PropertyCellEdit}接口来实现自定义的字段编辑器
 * @author WAZA
 *
 */
public class ObjectPropertyForm extends AbstractFrame
{
	private static final long serialVersionUID = 1L;

	public ObjectPropertyForm(BaseObjectPropertyPanel panel) {
		super.setLayout(new BorderLayout());
		super.add(panel);
	}
}
