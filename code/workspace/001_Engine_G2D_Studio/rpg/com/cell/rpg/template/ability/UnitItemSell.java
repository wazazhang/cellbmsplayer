package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;


@Property("[单位能力] 出售物品")
public class UnitItemSell extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	
	
	@Override
	final public boolean isMultiField() {
		return true;
	}
}
