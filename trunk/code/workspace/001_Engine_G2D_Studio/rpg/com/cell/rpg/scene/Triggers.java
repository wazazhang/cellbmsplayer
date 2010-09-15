package com.cell.rpg.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import com.cell.CUtil;

public class Triggers implements Serializable, Comparator<String>
{
	private static final long serialVersionUID = 1L;

	private TreeMap<String, SceneTrigger> triggers = new TreeMap<String, SceneTrigger>(this);
	
	public Triggers() {}

	public boolean addTrigger(SceneTrigger st) {
		if (!triggers.containsKey(st.getName())) {
			triggers.put(st.getName(), st);
			return true;
		}
		return false;
	}

	public SceneTrigger getTrigger(String name) {
		return triggers.get(name);
	}
	
	public SceneTrigger removeTrigger(String name) {
		return triggers.remove(name);
	}
	
	public ArrayList<SceneTrigger> getTriggers(){
		return new ArrayList<SceneTrigger>(triggers.values());
	}
	
	public int getTriggerCount(){
		return triggers.size();
	}
	
	@Override
	public int compare(String o1, String o2) {
		return CUtil.getStringCompare().compare(o2, o1);
	}
}
