package com.cell.rpg.scene.script;

import java.util.Collection;

import com.cell.rpg.scene.script.trigger.Event;

public interface SceneScriptManager 
{
	/**
	 * 得到所有事件类型。
	 * @return
	 */
	public Collection<Class<? extends Event>> getEvents();
}
