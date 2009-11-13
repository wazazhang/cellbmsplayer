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

public class SceneListCellEdit extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public SceneListCellEdit() 
	{
		super(new Vector<SceneNode>(Studio.getInstance().getSceneManager().getAllScenes()));
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
	
	public String getValue() {
		Object item = getSelectedItem();
		if (item instanceof SceneNode) {
			return ((SceneNode) item).getID();
		}
		return null;
	}
}
