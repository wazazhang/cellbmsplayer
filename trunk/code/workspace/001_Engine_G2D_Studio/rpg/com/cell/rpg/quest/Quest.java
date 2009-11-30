package com.cell.rpg.quest;

import java.util.ArrayList;

import com.cell.rpg.RPGObject;
import com.g2d.annotation.Property;

/**
 * 一个任务的全部描述
 * @author WAZA
 */
public class Quest extends RPGObject
{
//	----------------------------------------------------------------------------------------------------------------
//	base
	
	/** 任务id */
	final private Integer 			int_id;
	/** 任务名字 */
	public String					name;
	
//	----------------------------------------------------------------------------------------------------------------
//	quest
	
	/** 任务内容 */
	QuestDiscussion				discussion			= new QuestDiscussion();
	
	/** 任务触发条件 */
	ArrayList<QuestCondition>	trigger_conditions	= new ArrayList<QuestCondition>();
	
	/** 任务完成条件 */
	ArrayList<QuestCondition>	complete_conditions	= new ArrayList<QuestCondition>();
	
	/** 任务奖励 */
	ArrayList<QuestAward>		awards				= new ArrayList<QuestAward>();
	
//	----------------------------------------------------------------------------------------------------------------
//	extend

	/** 任务图标 */ @Property("任务图标")
	public String					icon_index;
	
//	----------------------------------------------------------------------------------------------------------------
	public Quest(Integer id, String name) {
		super(id.toString());
		this.int_id = id;
		this.name	= name;
	}
	
	public int getIntID() {
		return int_id;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{};
	}
//	----------------------------------------------------------------------------------------------------------------

	public QuestDiscussion getDiscussion() {
		return discussion;
	}
	
	public ArrayList<QuestCondition> getTriggerConditions() {
		return trigger_conditions;
	}

	public ArrayList<QuestCondition> getCompleteConditions() {
		return complete_conditions;
	}

	public ArrayList<QuestAward> getAwards() {
		return awards;
	}

}
