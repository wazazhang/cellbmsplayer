package com.cell.rpg.scene;

import java.util.ArrayList;

import com.cell.CUtil;
import com.cell.rpg.scene.script.trigger.Event;

public interface TriggerGenerator 
{
	public String getTriggerObjectName();

	public boolean removeTrigger(SceneTrigger st);
		
	public boolean addTrigger(SceneTrigger st);
		
	public ArrayList<SceneTrigger> getTriggers();
	
	public int getTriggerCount();
}
