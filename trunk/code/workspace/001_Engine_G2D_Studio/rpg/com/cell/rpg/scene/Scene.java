package com.cell.rpg.scene;

import java.util.Vector;

import com.cell.rpg.RPGObject;
import com.cell.rpg.display.SceneNode;
import com.g2d.annotation.Property;

public class Scene extends RPGObject
{
	transient private int int_id;

	public String		name;
	
	public SceneNode	scene_node;
	
	final public Vector<SceneUnit> 	scene_units = new Vector<SceneUnit>();

//	------------------------------------------------------------------------------------------------------------------
	
	@Property("场景背景音乐")
	public String		bgm_sound_name;
	
	
	
//	------------------------------------------------------------------------------------------------------------------
	
	public Scene(int id, String name) {
		super(id+"");
		this.int_id	= id;
		this.name	= name;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (id!=null) {
			this.int_id = Integer.parseInt(id);
		}
	}
	
	final public int getIntID() {
		return int_id;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}
	
}
