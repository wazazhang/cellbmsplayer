package com.cell.rpg.scene;

import java.io.Serializable;
import java.util.ArrayList;

public class Triggers implements Serializable, TriggerGenerator
{
	private static final long serialVersionUID = 1L;

	private ArrayList<SceneTrigger>		triggers = new ArrayList<SceneTrigger>();
	
	private String name;
	
	public Triggers(String name) {
		this.name = name;
	}
	
	public String getTriggerObjectName() {
		return name;
	}
	
	public boolean addTrigger(SceneTrigger st) {
		for (SceneTrigger s : getTriggers()) {
			if (s.getName().equals(st.getName())) {
				return false;
			}
		}
		triggers.add(st);
		return true;
	}
	
	@Override
	public boolean removeTrigger(SceneTrigger st) {
		return triggers.remove(st);
	}
	
	public ArrayList<SceneTrigger> getTriggers(){
		return triggers;
	}
	
	public int getTriggerCount(){
		return triggers.size();
	}

}
