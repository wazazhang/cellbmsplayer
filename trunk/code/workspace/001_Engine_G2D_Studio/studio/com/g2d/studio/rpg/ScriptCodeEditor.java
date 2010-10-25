package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.CellEditor;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.struct.ScriptCode;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractOptionDialog;

@SuppressWarnings("serial")
public class ScriptCodeEditor extends AbstractOptionDialog<ScriptCode> implements PropertyCellEdit<ScriptCode>
{
	JTextPane 	text_pane 	= new JTextPane();

	JLabel		label		= new JLabel();
	
	public ScriptCodeEditor(ScriptCode old) 
	{
		super.setTitle(ScriptCode.class.getCanonicalName());
		super.add(new JScrollPane(text_pane), BorderLayout.CENTER);
		
		if (old != null) {
			text_pane.setText(old.script+"");
		}
	}
	
	@Override
	protected boolean checkOK() {
		return true;
	}
	
	@Override
	public Component getComponent(ObjectPropertyEdit panel) {
		label.setText(text_pane.getText());
		return label;
	}

	@Override
	protected ScriptCode getUserObject() {
		ScriptCode data = new ScriptCode();
		data.script = text_pane.getText();
		return data;
	}

	@Override
	public ScriptCode getValue() {
		return selected_object;
	}
	
}
