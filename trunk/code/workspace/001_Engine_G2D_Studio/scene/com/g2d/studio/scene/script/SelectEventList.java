package com.g2d.studio.scene.script;

import java.awt.Component;
import java.util.Vector;

import javax.swing.JComboBox;

import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;

public class SelectEventList extends JComboBox implements PropertyCellEdit<Class<? extends Event>>
{
	private static final long serialVersionUID = 1L;

	ObjectPropertyEdit panel;
	
	public SelectEventList(
			Class<? extends Scriptable> trigger_unit_type, 
			Class<? extends Event> default_value) 
	{
		super();
		try{
			if (default_value!=null) {
				setSelectedItem(default_value);
			}
		}catch(Exception err){}
	}
	
	public Component getComponent(ObjectPropertyEdit panel) {		
		this.panel = panel;
		return this;
	}
	
	public Class<? extends Event> getValue() {
		Object item = getSelectedItem();
		if (item != null) {
			return ((EventTypeItem)item).type;
		}
		return null;
	}
	
	static Vector<EventTypeItem> getList(Class<? extends Scriptable> trigger_unit_type) {
		Vector<EventTypeItem> ret = new Vector<EventTypeItem>();
		for (Class<? extends Event> evt : Studio.getInstance().getSceneScriptManager().getEvents(trigger_unit_type)) {
			ret.add(new EventTypeItem(evt));
		}
		return ret;
	}
	
	static class EventTypeItem
	{
		Class<? extends Event> type;
		public EventTypeItem(Class<? extends Event> type) {
			this.type = type;
		}
		@Override
		public String toString() {
			EventType tp = type.getAnnotation(EventType.class);
			return tp.comment();
		}
	}
}

