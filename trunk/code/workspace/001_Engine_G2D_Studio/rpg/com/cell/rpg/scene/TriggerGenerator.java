package com.cell.rpg.scene;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

import com.cell.CUtil;
import com.cell.rpg.scene.script.trigger.Event;

public class TriggerGenerator implements Serializable, Comparator<String>
{
	private static final long serialVersionUID = 1L;
	
	private TreeSet<String> binded_triggers = new TreeSet<String>();
	
	public boolean bindTrigger(SceneTrigger st){
		if (binded_triggers.contains(st.getName())) {
			return false;
		} else {
			binded_triggers.add(st.getName());
			return true;
		}
	}

	public boolean unbindTrigger(SceneTrigger st) {
		if (binded_triggers.contains(st.getName())) {
			binded_triggers.remove(st.getName());
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<SceneTrigger> getTriggers(Triggers triggers) {
		ArrayList<SceneTrigger> ret = new ArrayList<SceneTrigger>(binded_triggers.size());
		for (String name : new ArrayList<String>(binded_triggers)) {
			SceneTrigger st = triggers.getTrigger(name);
			if (st != null) {
				ret.add(st);
			} else {
				binded_triggers.remove(name);
			}
		}
		return ret;
	}

	public ArrayList<String> getTriggerNames() {
		return new ArrayList<String>(binded_triggers);
	}

	public int getTriggerCount() {
		return binded_triggers.size();
	}
	
	
	@Override
	public int compare(String o1, String o2) {
		return CUtil.getStringCompare().compare(o2, o1);
	}

}
