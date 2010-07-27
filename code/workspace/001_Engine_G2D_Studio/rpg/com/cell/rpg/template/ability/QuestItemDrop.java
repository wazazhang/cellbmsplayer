package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.g2d.annotation.Property;


@Property("[单位能力] 任务掉落物品")
public class QuestItemDrop extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	@Property("任务ID")
	@PropertyAdapter(PropertyType.QUEST_ID)
	public int quest_id		= -1;
	
	/** 掉落道具列表ID */
	@Property("掉落道具列表ID")
	@PropertyAdapter(PropertyType.ITEM_LIST_ID)
	public int item_list_id = -1;

	@Override
	final public boolean isMultiField() {
		return true;
	}
}
