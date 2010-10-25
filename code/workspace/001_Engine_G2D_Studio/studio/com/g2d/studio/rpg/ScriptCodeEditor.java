package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.CellEditor;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.struct.ScriptCode;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

public class ScriptCodeEditor extends AbstractOptionDialog<ScriptCode> implements PropertyCellEdit<ScriptCode>
{
	JTextPane text_pane = new JTextPane();

	public ScriptCodeEditor() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ScriptCode getUserObject() {
		return null;
	}

	protected Object[] getUserObjects() {
		return null;
	}

	@Override
	public ScriptCode getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
