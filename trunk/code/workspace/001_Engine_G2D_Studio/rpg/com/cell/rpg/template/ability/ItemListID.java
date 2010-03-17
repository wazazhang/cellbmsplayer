package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@Property("道具列表ID")
public abstract class ItemListID extends AbstractAbility
{
	private static final long serialVersionUID = 1L;
	
	/** 道具列表ID */
	@Property("道具列表ID")
	public int item_list_id = -1;
	
	@Override
	final public boolean isMultiField() {
		return false;
	}
}
