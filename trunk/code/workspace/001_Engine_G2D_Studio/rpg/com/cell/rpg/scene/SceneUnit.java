package com.cell.rpg.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.rpg.RPGObject;
import com.cell.rpg.scene.script.trigger.Event;
import com.cell.rpg.struct.QuestStateDisplayOR;
import com.g2d.annotation.Property;

public abstract class SceneUnit extends RPGObject implements Comparator<Class<? extends Event>>, TriggerGenerator
{
	/** scene graph 结构的视图 */
	public String 						name	= "no name";
	public float						x;
	public float						y;
	public float						z;

	@Property("任务依赖显示条件 (覆盖TUnit)")
	public QuestStateDisplayOR 			quest_display = null;
	
	final private TreeMap<Class<? extends Event>, SceneTrigger>	
										scene_triggers = new TreeMap<Class<? extends Event>, SceneTrigger>(this);
	
	public SceneUnit(String id) {
		super(id);
	}


	public String getName() {
		return name;
	}
	
	public String getTriggerObjectName(){
		return name;
	}

//	------------------------------------------------------------------------------------------------------------------
	
	abstract public Class<? extends com.cell.rpg.scene.script.entity.SceneUnit>	getEventType();

	public SceneTrigger addTrigger(SceneTrigger st) {
		if (st.asTriggeredObjectType(getEventType())) {
			return scene_triggers.put(st.getEventType(), st);
		}
		return null;
	}
	
	public SceneTrigger getTrigger(Class<? extends Event> event_type){
		return scene_triggers.get(event_type);
	}
	
	public ArrayList<SceneTrigger> getTriggers(){
		return new ArrayList<SceneTrigger>(scene_triggers.values());
	}
	
	@Override
	public int compare(Class<? extends Event> o1, Class<? extends Event> o2) {
		return CUtil.getStringCompare().compare(o1.getName(), o2.getName());
	}
//	------------------------------------------------------------------------------------------------------------------
	
}
