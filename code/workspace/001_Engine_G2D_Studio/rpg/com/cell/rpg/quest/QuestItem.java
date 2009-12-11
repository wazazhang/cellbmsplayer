package com.cell.rpg.quest;

import java.io.File;
import java.lang.ref.Reference;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.script.QuestScript;
import com.cell.rpg.struct.Comparison;
import com.g2d.annotation.Property;

/**
 * 用于任务的状态存储<br>
 * 如果 isResult() == true，则代表该标志是一个结果<br>
 * 如果 isResult() == false，则代表该标志是一个条件<br>
 * 例如：
 * 玩家完成一个任务后或触发一段剧情后，获得若干QuestItem，
 * QuestItem存储到当前玩家的状态，当玩家触发了另一个任务时，
 * 如果该任务的接受条件包含玩家身上的QuestItem，
 * 那么触发的新任务就被激活，激活的任务可能消耗掉玩家身上的道具，
 * 也可能给予玩家另外的QuestItem。<br>
 * QuestItem也可以理解为任务标志或者任务令牌。<br>
 * 如果QuestItem是一个条件，则一组QuestItem为或的条件<br>
 * 如果QuestItem是一个奖励，则由玩家选择一项<br>
 * @author WAZA
 */
public class QuestItem extends RPGObject implements Cloneable
{
	final private Integer	type;

	final private boolean	is_result;
	
	public String 			name;
	
//	--------------------------------------------------------------------------------------
	/**该字段由文件存储*/
	transient
	public String			script;
	
//	--------------------------------------------------------------------------------------
	
	public QuestItem(Integer type, String name, boolean isresult) {
		super(type.toString());
		this.type = type;
		this.is_result = isresult;
		this.name = name;
	}
	
	/**
	 * 可以理解为标志的唯一id
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 是否是一个结果，就是QuestAward里的值，
	 * 否则就是一个条件
	 * @return
	 */
	public boolean isResult() {
		return is_result;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		if (is_result) {
			return new Class<?>[]{
					AwardItem.class,
				};
		} else {
			return new Class<?>[]{
					TagItem.class,
					TagQuest.class,
					TagQuestItem.class,
					TagPlayerField.class,
					TagNPCField.class,
				};
		}
		
	}
	
//	--------------------------------------------------------------------------------------
	
	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {
		File txt_file = new File(xmlFile+".script");
		if (txt_file.exists()) {
			this.script = CFile.readText(txt_file, "UTF-8");
		} else {
			this.script = "";
		}
	}
	
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {
		File txt_file = new File(xmlFile+".script");
		CFile.writeText(txt_file, this.script, "UTF-8");
	}
	
//	--------------------------------------------------------------------------------------
	
	public static abstract class QuestItemAbility extends AbstractAbility
	{}

//	--------------------------------------------------------------------------------------

	public static interface Tag{}
	
	/**
	 * [标志] 物品
	 * @author WAZA
	 */
	@Property("[标志] 依赖的物品")
	public static class TagItem extends QuestItemAbility implements Tag
	{
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public int				titem_count			= 1;
		@Property("物品-是否消耗掉")
		public boolean			is_expense			= true;
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	/**
	 * [标志] 依赖已完成的任务
	 * @author WAZA
	 */
	@Property("[标志] 依赖已完成的任务")
	public static class TagQuest extends QuestItemAbility implements Tag
	{
		@Property("依赖已完成的任务ID")
		public int				quest_id	= -1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	/**
	 * [标志] 依赖的任务奖励
	 * @author WAZA
	 */
	@Property("[标志] 依赖的任务奖励")
	public static class TagQuestItem extends QuestItemAbility implements Tag
	{
		@Property("依赖的任务奖励ID")
		public int				quest_item_index	= -1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}

	public static abstract class TagUnitField extends QuestItemAbility implements Tag
	{
		@Property("单位字段")
		public String			unit_filed_name;
		@Property("单位字段值")
		public String			unit_filed_value;
		@Property("比较器")
		public Comparison		unit_field_comparison	= Comparison.EQUAL;
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	/**
	 * [标志] 依赖的角色字段
	 */
	@Property("[标志] 依赖的主角字段")
	public static class TagPlayerField extends TagUnitField implements Tag
	{
	}
	
	/**
	 * [标志] 依赖的角色字段
	 */
	@Property("[标志] 依赖的被触发单位字段")
	public static class TagNPCField extends TagUnitField implements Tag
	{
	}

//	--------------------------------------------------------------------------------------
	
	public static interface Result{}
	
	/**
	 * [奖励] 物品
	 * @author WAZA
	 */
	@Property("[奖励] 奖励的物品")
	public static class AwardItem extends QuestItemAbility implements Result
	{
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public int				titem_count			= 1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
}
