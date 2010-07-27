package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@Property("[单位能力] 单位掉落物品")
public class UnitItemDrop extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;

	/** 掉落道具列表ID */
	@Property("掉落道具列表ID")
	public int item_list_id = -1;

	@Override
	final public boolean isMultiField() {
		return true;
	}
}
