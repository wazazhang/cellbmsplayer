package com.cell.rpg.quest;

import java.io.File;
import java.lang.ref.Reference;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.script.QuestScript;
import com.g2d.annotation.Property;

/**
 * 用于任务的状态存储<br>
 * 例如：
 * 玩家完成一个任务后或触发一段剧情后，获得若干QuestItem，
 * QuestItem存储到当前玩家的状态，当玩家触发了另一个任务时，
 * 如果该任务的接受条件包含玩家身上的QuestItem，
 * 那么触发的新任务就被激活，激活的任务可能消耗掉玩家身上的道具，
 * 也可能给予玩家另外的QuestItem。<br>
 * QuestItem也可以理解为任务标志或者任务令牌。
 * @author WAZA
 */
public class QuestItem extends RPGObject implements Cloneable
{
	final private Integer	type;

	public String 			name;
	
//	--------------------------------------------------------------------------------------
	/**该字段由文件存储*/
	transient
	public String			script;
	
//	--------------------------------------------------------------------------------------
	
	public QuestItem(Integer type, String name) {
		super(type.toString());
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
			TagItem.class,
			TagQuestItem.class,
			TagUnitField.class,
		};
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
	
	/**
	 * [标志] 物品
	 * @author WAZA
	 */
	@Property("[标志] 物品")
	public static class TagItem extends AbstractAbility
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
	
	/**
	 * [标志] 依赖的任务条件
	 * @author WAZA
	 */
	@Property("[标志] 依赖的任务条件")
	public static class TagQuestItem extends AbstractAbility
	{
		@Property("依赖的任务条件ID")
		public int				quest_item_index	= -1;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	/**
	 * [标志] 依赖的角色字段
	 */
	@Property("[标志] 依赖的角色字段")
	public static class TagUnitField extends AbstractAbility
	{
		@Property("角色字段")
		public String			player_filed_name;
		@Property("角色字段值")
		public String			player_filed_value;
		
		@Property("触发单位字段")
		public String			trigger_unit_filed_name;
		@Property("触发单位字段值")
		public String			trigger_unit_filed_value;
		
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
}
