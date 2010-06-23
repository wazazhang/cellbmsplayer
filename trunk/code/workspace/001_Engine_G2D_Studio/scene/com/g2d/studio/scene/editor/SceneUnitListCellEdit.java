package com.g2d.studio.scene.editor;

import java.awt.Component;

import javax.swing.JComboBox;

import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitListCellEdit extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public SceneUnitListCellEdit(SceneEditor scene_viewer, Class<? extends SceneUnitTag<?>> cls) 
	{
		for (SceneUnitTag<?> unit : scene_viewer.getGameScene().getWorld().getChildsSubClass(cls)) {
			super.addItem(unit);
		}
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {
		this.panel = panel;
		return this;
	}
	
	public String getValue() {
		Object item = getSelectedItem();
		if (item instanceof SceneUnitTag<?>) {
			return ((SceneUnitTag<?>) item).getGameUnit().getID()+"";
		}
		return null;
	}
}
