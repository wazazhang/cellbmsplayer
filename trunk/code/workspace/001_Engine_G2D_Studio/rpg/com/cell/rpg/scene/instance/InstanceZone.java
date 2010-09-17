package com.cell.rpg.scene.instance;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.Triggers;
import com.cell.rpg.scene.TriggersPackage;
import com.g2d.annotation.Property;


@Property("副本")
public class InstanceZone extends RPGObject implements NamedObject, TriggersPackage
{
	private String name;
	
	final private int id;

//	-------------------------------------------------------------------------------
	
	@Property("进入此副本的最大人数")
	public int player_count 	= 10;

	
	
//	-------------------------------------------------------------------------------
	

	private String 				discussion			= "";

	private Triggers			triggers_package 	= new Triggers();
	
	private TriggerGenerator	binded_triggers 	= new TriggerGenerator();

	private HashMap<Integer, BindedScene> scenes	= new HashMap<Integer, BindedScene>();
	
//	-------------------------------------------------------------------------------
	
	public InstanceZone(int id) {
		super(id+"");
		this.id = id;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (triggers_package == null) {
			triggers_package = new Triggers();
		}
		if (binded_triggers == null) {
			binded_triggers = new TriggerGenerator();
		}
		if (scenes == null) {
			scenes	= new HashMap<Integer, BindedScene>();
		}
	}
	
	public int getIntID() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}

//	-------------------------------------------------------------------------------
	
	public void setDiscussion(String discussion) {
		this.discussion = discussion;
	}

	public String getDiscussion() {
		return discussion;
	}
	
	public Triggers getTriggersPackage() {
		return triggers_package;
	}

	public TriggerGenerator getBindedTriggers() {
		return binded_triggers;
	}

	public HashMap<Integer, BindedScene> getScenes() {
		return scenes;
	}

//	-------------------------------------------------------------------------------
	
	
	
	public static class BindedScene implements Serializable
	{
		private static final long serialVersionUID = 1L;
		
		final public int 	scene_id;
		
		public int 			edit_x = 0;
		
		public int 			edit_y = 0;
		
		public BindedScene(Scene scene) {
			this.scene_id = scene.getIntID();
		}
	}
	
}
