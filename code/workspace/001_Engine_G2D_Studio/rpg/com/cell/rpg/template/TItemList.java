package com.cell.rpg.template;

import com.cell.rpg.template.ability.UnitDropItem;

public class TItemList extends TemplateNode 
{
	public TItemList(int id, String name) {
		super(id, name);
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class[] {
				UnitDropItem.class,
		};
	}
}
