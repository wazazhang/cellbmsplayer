package com.cell.rpg.quest;

import java.util.ArrayList;

import com.cell.rpg.RPGObject;

/**
 * 一个任务的全部描述
 * @author WAZA
 */
public class Quest extends RPGObject
{
	/** 任务id */
	final public Integer 			int_id;
	/** 任务名字 */
	public String					name;
	
	/** 任务内容 */
	public	QuestDiscussion			discussion;
	/** 任务完成条件 */
	public	ArrayList<QuestPurpose>	purposes;
	/** 任务奖励 */
	public	ArrayList<QuestAward>	awards;
	
	public Quest(Integer id) {
		super(id.toString());
		this.int_id = id;
	}
	
	public int getIntID() {
		return int_id;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}
}
