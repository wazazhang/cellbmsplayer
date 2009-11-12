package com.cell.rpg.scene;

import java.util.Vector;

import com.cell.rpg.RPGObject;
import com.cell.rpg.display.SceneNode;

public class Scene extends RPGObject
{
	public String				name;
	
	public SceneNode			scene_node;
	
	final public Vector<SceneUnit> 	scene_units = new Vector<SceneUnit>();
	
	public Scene(String id, String name) {
		super(id);
		this.name = name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}
	
}
