package com.g2d.studio.gameedit;

import java.awt.Component;

import javax.swing.JComboBox;

import com.cell.rpg.xls.XLSColumns;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;

public class XLSColumnSelectCellEdit extends JComboBox implements PropertyCellEdit<String>
{
	XLSColumns columns;
	
	public XLSColumnSelectCellEdit(XLSColumns columns) {
		super(columns.getColumns().toArray(new String[columns.size()]));
		this.columns = columns;
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		return this;
	}
	
	@Override
	public String getValue() {
		Object ret = getSelectedItem();
		if (ret==null) {
			return null;
		} else {
			return ret.toString();
		}
	}
}
