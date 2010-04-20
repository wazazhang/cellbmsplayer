package com.g2d.studio.gameedit;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.entity.ObjectNode;


public class ObjectSelectCellEdit<T extends ObjectNode<?>> extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public ObjectSelectCellEdit(Class<T> object_type) 
	{
		super(Studio.getInstance().getObjectManager().getObjects(object_type));
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
}
