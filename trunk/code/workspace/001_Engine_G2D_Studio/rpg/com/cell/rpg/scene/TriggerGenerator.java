package com.cell.rpg.scene;

import java.util.ArrayList;

import com.cell.CUtil;
import com.cell.rpg.scene.script.trigger.Event;

public interface TriggerGenerator 
{
	public String getTriggerObjectName();
	
	public SceneTrigger addTrigger(SceneTrigger st);
	
	public SceneTrigger getTrigger(Class<? extends Event> event_type);
	
	public ArrayList<SceneTrigger> getTriggers();
}
