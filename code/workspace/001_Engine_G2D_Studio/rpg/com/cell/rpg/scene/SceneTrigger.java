package com.cell.rpg.scene;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.cell.CUtil;
import com.cell.rpg.scene.script.SceneScriptManager;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 触发器
 * @author WAZA
 */
abstract public class SceneTrigger implements Serializable, Comparator<Class<?>>
{
	private static final long serialVersionUID = 1L;
	
	private String 	name;
	
	public boolean 	enable = true;
	
	public String	comment = "comment here ....";
	
//	------------------------------------------------------------------------------------------------------
	
	transient private TreeSet<Class<? extends Event>> event_types;
	
	private TreeSet<String> event_types_name = new TreeSet<String>(CUtil.getStringCompare());

//	------------------------------------------------------------------------------------------------------
	
	public SceneTrigger(TriggerGenerator parent, String name) throws Exception
	{
		if (!setName(parent, name) ) {
			throw new Exception("duplicate name in : " + parent.getTriggerObjectName());
		}
	}

	public String getName() {
		return name;
	}

	public boolean setName(TriggerGenerator parent, String name) 
	{
		for (SceneTrigger st : parent.getTriggers()) {
			if (st != this && st.getName().equals(name)) {
				return false;
			}
		}
		this.name = name;
		return true;
	}
	
	public boolean addTriggerEvent(Class<? extends Event> event) {
		event_types = null;
		return event_types_name.add(event.getName());
	}
	
	public boolean removeTriggerEvent(Class<? extends Event> event) {
		event_types = null;
		return event_types_name.remove(event.getName());
	}
	
	@SuppressWarnings("unchecked")
	public SortedSet<Class<? extends Event>> getEventTypes() {
		if (event_types == null) {
			event_types = new TreeSet<Class<? extends Event>>(this);
			for (String tn : event_types_name) {
				try {
					event_types.add((Class<? extends Event>)Class.forName(tn));
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
		}
		return event_types;
	}

	@Override
	public int compare(Class<?> o1, Class<?> o2) {
		return CUtil.getStringCompare().compare(o1.getName(), o2.getName());
	}
}
