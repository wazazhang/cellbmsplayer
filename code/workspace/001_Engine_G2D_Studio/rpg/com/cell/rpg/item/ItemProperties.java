package com.cell.rpg.item;

import com.cell.rpg.RPGObject;

public class ItemProperties extends RPGObject
{
	final private int	tid;
	
	/** */
	public String		name;
	
	public ItemProperties(int tid, String name) {
		super(tid+"");
		this.tid	= tid;
		this.name	= name;
	}
	
	public int getIntID() {
		return tid;
	}
	
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return ItemPropertyTypes.getItemPropertyTypes();
	}
}
