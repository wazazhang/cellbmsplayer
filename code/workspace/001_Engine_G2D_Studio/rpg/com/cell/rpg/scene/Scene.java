package com.cell.rpg.scene;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.display.SceneNode;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.annotation.Property;

public class Scene extends RPGObject implements NamedObject, TriggerGenerator
{
	transient private int 				int_id;

	public String						name;
	
	public SceneNode					scene_node;
	
	final public Vector<SceneUnit> 		scene_units = new Vector<SceneUnit>();

	private ArrayList<SceneTrigger>		scene_triggers = new ArrayList<SceneTrigger>();

//	------------------------------------------------------------------------------------------------------------------
	
	@Property("场景背景音乐")
	public String						bgm_sound_name;
	
	@Property("场景编组号码")
	public Integer						group;
	
	@Property("场景事件冷冻时间(毫秒)")
	public Integer						event_freezing_time_ms	= 5000;
	
//	------------------------------------------------------------------------------------------------------------------
	
	public Scene(int id, String name) {
		super(id+"");
		this.int_id	= id;
		this.name	= name;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (id != null) {
			this.int_id = Integer.parseInt(id);
		}
		if (group == null) {
			this.group = 0;
		}
		if (scene_triggers == null) {
			scene_triggers = new ArrayList<SceneTrigger>();
		}
	}
	
	final public int getIntID() {
		return int_id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		SceneAbilityManager sam = SceneAbilityManager.getManager();
		if (sam != null) {
			Set<Class<?>> set = sam.getAllTypes();
			if (set != null) {
				return set.toArray(new Class<?>[set.size()]);
			}
		}
		return null;
	}

//	------------------------------------------------------------------------------------------------------------------
	
	public String getTriggerObjectName() {
		return name + "(" + id + ")";
	}
	
	public boolean addTrigger(SceneTrigger st) {
		for (SceneTrigger s : getTriggers()) {
			if (s.getName().equals(st.getName())) {
				return false;
			}
		}
		scene_triggers.add(st);
		return true;
	}
	
	@Override
	public boolean removeTrigger(SceneTrigger st) {
		return scene_triggers.remove(st);
	}
	
	
	public ArrayList<SceneTrigger> getTriggers(){
		return scene_triggers;
	}
	
	public int getTriggerCount(){
		return scene_triggers.size();
	}
}
