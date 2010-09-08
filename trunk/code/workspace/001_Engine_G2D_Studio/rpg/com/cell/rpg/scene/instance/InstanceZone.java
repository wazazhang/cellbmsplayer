package com.cell.rpg.scene.instance;

import com.cell.rpg.NamedObject;
import com.cell.rpg.RPGObject;
import com.g2d.annotation.Property;


@Property("副本")
public class InstanceZone extends RPGObject implements NamedObject
{
	private String name;
	
	final private int id;

//	-------------------------------------------------------------------------------
	
	@Property("进入此副本的最大人数")
	public int player_count 	= 5;
	
	
	

//	-------------------------------------------------------------------------------
	
	public InstanceZone(int id) {
		super(id+"");
		this.id = id;
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
}
