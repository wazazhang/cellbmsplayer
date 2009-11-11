package com.cell.rpg.scene;

import com.cell.rpg.RPGObject;
import com.cell.rpg.display.SceneNode;

public class Scene extends RPGObject
{
	public String		name;
	
	public SceneNode	scene_node;
	
	public Scene(String id, String name) {
		super(id);
		this.name = name;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}
	
}
