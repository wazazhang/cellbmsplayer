package com.cell.rpg.item;

import com.cell.rpg.RPGObject;
import com.cell.rpg.item.ItemPropertyTemplate.ArgTemplate;
import com.g2d.annotation.Property;

public class ItemProperties extends RPGObject
{
	final private int	tid;
	
	/** */
	public String				name;
	
	@Property({"获得的属性范围"})
	public ArgTemplate<Integer>	properties_range = new ArgTemplate<Integer>(1);

	public ItemProperties(int tid, String name) {
		super(tid+"");
		this.tid	= tid;
		this.name	= name;
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		if (properties_range==null) {
			properties_range = new ArgTemplate<Integer>(1);
		}
	}
	
	public int getIntID() {
		return tid;
	}
	
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return ItemPropertyTypes.getItemPropertyTypes();
	}
}
