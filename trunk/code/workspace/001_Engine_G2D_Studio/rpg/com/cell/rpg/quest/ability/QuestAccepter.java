package com.cell.rpg.quest.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

@Property("[任务能力] 任务接收者")
public class QuestAccepter extends AbstractAbility
{
	@Property("任务ID")
	public Integer quest_id	= -1;
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
	
}
