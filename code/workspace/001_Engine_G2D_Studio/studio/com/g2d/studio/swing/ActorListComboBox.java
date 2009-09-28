package com.g2d.studio.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JList;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.actor.FormActorViewer;
import com.g2d.studio.scene.FormSceneViewer;

public class ActorListComboBox extends JComboBox implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyPanel panel;
	
	public ActorListComboBox() 
	{
		super(new Vector<FormActorViewer>(Studio.getInstance().getActors()));
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
