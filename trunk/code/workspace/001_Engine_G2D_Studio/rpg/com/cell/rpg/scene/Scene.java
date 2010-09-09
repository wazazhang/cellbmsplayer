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

public class Scene extends RPGObject implements NamedObject, Comparator<Class<? extends Event>>, TriggerGenerator
{
	transient private int 				int_id;

	public String						name;
	
	public SceneNode					scene_node;
	
	final public Vector<SceneUnit> 		scene_units = new Vector<SceneUnit>();

	final private TreeMap<Class<? extends Event>, SceneTrigger>	
										scene_triggers = new TreeMap<Class<? extends Event>, SceneTrigger>(this);

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
	
	public SceneTrigger addTrigger(SceneTrigger st) {
		if (st.asTriggeredObjectType(com.cell.rpg.scene.script.entity.Scene.class)) {
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
}
