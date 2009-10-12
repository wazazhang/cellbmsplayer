package com.g2d.editor.property;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EnumSet;
import java.util.Vector;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;

/**
 * 将枚举值列举在ComboBox中
 * @author WAZA
 * @param <T>
 */
public class ListEnumEdit<T extends Enum<T>> extends JComboBox implements PropertyCellEdit<T>
{
	private static final long serialVersionUID = 1L;
	
	Class<T> type;
	ObjectPropertyPanel panel;
	
	public ListEnumEdit(Class<T> cls) 
	{
		super(new Vector<T>(EnumSet.allOf(cls)));
		this.type = cls;
		this.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				panel.fireEditingStopped();
			}
		});
	}
	
	public void beginEdit(ObjectPropertyPanel panel) {
		this.panel = panel;
	}
	
	public Component getComponent() {
		return this;
	}
	
	public T getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			try {
				T ret = type.cast(item);
				return ret;
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
		return null;
	}
}
