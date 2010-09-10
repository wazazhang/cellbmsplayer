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

public abstract class SceneUnit extends RPGObject implements TriggerGenerator
{
	/** scene graph 结构的视图 */
	public String 						name	= "no name";
	public float						x;
	public float						y;
	public float						z;

	@Property("任务依赖显示条件 (覆盖TUnit)")
	public QuestStateDisplayOR 			quest_display = null;
	
	private ArrayList<SceneTrigger>		scene_triggers = new ArrayList<SceneTrigger>();
	
	public SceneUnit(String id) {
		super(id);
	}

	@Override
	protected void init_transient() {
		super.init_transient();
		if (scene_triggers == null) {
			scene_triggers = new ArrayList<SceneTrigger>();
		}
	}

	public String getName() {
		return name;
	}
	
	public String getTriggerObjectName(){
		return name;
	}

//	------------------------------------------------------------------------------------------------------------------
	
	abstract public Class<? extends com.cell.rpg.scene.script.entity.SceneUnit>	getTriggerObjectType();

	public void addTrigger(SceneTrigger st) {
		scene_triggers.add(st);
	}
	@Override
	public void removeTrigger(SceneTrigger st) {
		scene_triggers.remove(st);
	}
	public int getTriggerCount(){
		return scene_triggers.size();
	}
	
	public ArrayList<SceneTrigger> getTriggers(){
		return scene_triggers;
	}
	
//	------------------------------------------------------------------------------------------------------------------
	
}
