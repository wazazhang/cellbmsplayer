package com.cell.rpg.scene.script;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.Studio;

public abstract class SceneScriptManager 
{
	/**
	 * 得到所有事件类型。
	 * @return
	 */
	abstract public Collection<Class<? extends Event>> getEvents();
	
	/**
	 * 根据触发者类型，获取该触发者支持的事件类型
	 * @param trigger_type
	 * @return
	 */
	public Set<Class<? extends Event>> getEvents(Class<? extends Scriptable> trigger_type)
	{
		Set<Class<? extends Event>> ret = new HashSet<Class<? extends Event>>();
		for (Class<? extends Event> evt : Studio.getInstance().getSceneScriptManager().getEvents()) {
			if (asTriggeredObjectType(evt, trigger_type)) {
				ret.add(evt);
			}
		}
		return ret;
	}
	
	/**
	 * 检查该事件类型是否支持该触发者类型
	 * @param event_type	事件类型
	 * @param trigger_type	触发者类型
	 * @return
	 */
	public static boolean asTriggeredObjectType(Class<? extends Event> event_type, Class<? extends Scriptable> trigger_type) {
		EventType et = event_type.getAnnotation(EventType.class);
		for (Class<? extends Scriptable> st : et.trigger_type()) {
			if (st.isAssignableFrom(trigger_type)) {
				return true;
			}
		}
		return false;
	}
}
