package com.cell.rpg.scene;

import java.io.Serializable;

import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 触发器
 * @author WAZA
 */
abstract public class SceneTrigger implements Serializable
{
	private static final long serialVersionUID = 1L;
		
	private String	event_type_name;

	transient 
	private Class<? extends Event> event_type;
	
	public SceneTrigger(Class<? extends Event> event_type) {
		this.event_type_name		= event_type.getName();
	}

	@SuppressWarnings("unchecked")
	final public Class<? extends Event> getEventType() {
		if (event_type == null) {
			try {
				event_type = (Class<? extends Event>)Class.forName(event_type_name);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return event_type;
	}
	
	final public boolean asTriggeredObjectType(Class<? extends Scriptable> type) {
		EventType et = getEventType().getAnnotation(EventType.class);
		for (Class<? extends Scriptable> st : et.trigger_type()) {
			if (st.isAssignableFrom(type)) {
				return true;
			}
		}
		return false;
	}
}
