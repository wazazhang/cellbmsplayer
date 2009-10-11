package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.scene.FormSceneViewer;
import com.g2d.studio.scene.SceneUnitTag;

public class SceneUnitListComboBox extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public SceneUnitListComboBox(FormSceneViewer scene_viewer) 
	{
		for (SceneUnitTag<?> unit : scene_viewer.getViewObject().getWorld().getChildsSubClass(SceneUnitTag.class)) {
			super.addItem(unit);
		}
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
		if (item != null) {
			return item.toString();
		}
		return null;
	}
}
