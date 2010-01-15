package com.g2d.studio.scene.editor;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;

import com.g2d.studio.Studio;
import com.g2d.studio.scene.entity.SceneNode;

public class SceneListCellEditInteger extends JComboBox implements PropertyCellEdit<Integer>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public SceneListCellEditInteger() 
	{
		super(new Vector<SceneNode>(Studio.getInstance().getSceneManager().getAllScenes()));
		this.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (panel != null) {
					panel.fireEditingStopped();
				}
			}
		});
	}
	
	public Component getComponent(ObjectPropertyPanel panel) {		
		this.panel = panel;
		return this;
	}
	
	public Integer getValue() {
		Object item = getSelectedItem();
		if (item instanceof SceneNode) {
			return ((SceneNode) item).getIntID();
		}
		return null;
	}
}
