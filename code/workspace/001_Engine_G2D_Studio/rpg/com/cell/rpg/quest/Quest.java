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
	public	QuestDiscussion			discussion	= new QuestDiscussion();
	/** 任务完成条件 */
	public	ArrayList<QuestPurpose>	purposes	= new ArrayList<QuestPurpose>();
	/** 任务奖励 */
	public	ArrayList<QuestAward>	awards		= new ArrayList<QuestAward>();
	
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
}
