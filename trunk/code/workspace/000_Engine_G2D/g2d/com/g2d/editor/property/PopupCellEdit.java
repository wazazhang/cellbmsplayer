package com.g2d.editor.property;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.ui.layout.ImageUILayout;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.display.ui.layout.UILayout.ImageStyle;


public abstract class PopupCellEdit<T> extends JButton implements ActionListener, PropertyCellEdit<T>
{
	private static final long serialVersionUID = Version.VersionG2D;

	protected static final String EDIT = "edit";

	protected T	current_value;
	
	protected ObjectPropertyPanel	sender;
	
	public void beginEdit(ObjectPropertyPanel panel) {}
	
	public PopupCellEdit() 
	{
		this.setActionCommand(EDIT);
		this.addActionListener(this);
		this.setBorderPainted(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (EDIT.equals(e.getActionCommand())) {
			onOpenEditor();
		} 
	}

	abstract public void onOpenEditor();

	public Component getComponent() {
		return this;
	}
	
	public T getValue() {
		return current_value;
	}
	
	public void completeEdit(){
		sender.rows_table.editingStopped(null);
	}
	
	public void setValue(T value, ObjectPropertyPanel comp) {
		current_value = value;
		sender = comp;
	}
	
	


	
}
