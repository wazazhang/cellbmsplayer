package com.g2d.studio.gameedit;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;


public class ObjectSelectCellEdit<T extends ObjectNode<?>> extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public ObjectSelectCellEdit(Class<T> object_type) 
	{
		this(object_type, null);
	}
	
	public ObjectSelectCellEdit(Class<T> object_type, ObjectSelectCellEditFillter<T> filter) 
	{
		super(getObjects(object_type, filter));
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {		
		this.panel = panel;
		return this;
	}
	
	public String getValue() {
		Object item = getSelectedItem();
		if (item instanceof ObjectNode<?>) {
			return ((ObjectNode<?>) item).getID();
		}
		return null;
	}
	
	public static<T extends ObjectNode<?>> Vector<T> getObjects(Class<T> object_type, ObjectSelectCellEditFillter<T> filter)
	{
		Vector<T> src = Studio.getInstance().getObjectManager().getObjects(object_type);
		if (filter == null) {
			return src;
		}
		Vector<T> ret = new Vector<T>(src.size());
		for (T d : src) {
			if (filter.accept(d)) {
				ret.add(d);
			}
		}
		return ret;
	}
}
