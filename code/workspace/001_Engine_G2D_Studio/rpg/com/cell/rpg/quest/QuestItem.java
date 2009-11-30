package com.cell.rpg.quest;

import com.cell.rpg.RPGObject;

/**
 * 用于任务触发条件
 * @author WAZA
 */
public class QuestItem extends RPGObject
{
	final private Integer 			int_id;
	
	public QuestItem(Integer id) {
		super(id.toString());
		this.int_id = id;
	}
	
	public int getIntID() {
		return int_id;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		// TODO Auto-generated method stub
		return null;
	}
}
