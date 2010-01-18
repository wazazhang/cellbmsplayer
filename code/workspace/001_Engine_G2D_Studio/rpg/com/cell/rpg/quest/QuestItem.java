package com.cell.rpg.quest;

import java.io.File;
import java.lang.ref.Reference;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Arithmetic;
import com.cell.rpg.formula.ObjectProperty;
import com.cell.rpg.formula.Value;
import com.cell.rpg.quest.formula.TriggerUnitProperty;
import com.cell.rpg.quest.script.QuestScript;
import com.cell.rpg.struct.BooleanCondition;
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
public class QuestItem extends RPGObject
{	
//	--------------------------------------------------------------------------------------
	
	final private Integer	type;

	final private boolean	is_result;
	
	public String 			name;
	
//	--------------------------------------------------------------------------------------
	
	public QuestItem(Integer type, String name, boolean isresult) {
		super(type.toString());
		this.type 		= type;
		this.is_result	= isresult;
		this.name		= name;
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
					AwardTeleport.class,
					AwardBattle.class,
					AwardAddUnitProperty.class,
					AwardSetUnitProperty.class,
					DropItem.class,
				};
		} else {
			return new Class<?>[]{
					TagItem.class,
					TagQuest.class,
					TagQuestItem.class,
					TagQuestStateKillMonsterComparison.class,
					TagValueComparison.class,
					TagEveryUnitComparison.class,
					TagOneMoreUnitComparison.class,
					TagUnitGroupCountComparison.class,
				};
		}
		
	}
	
//	--------------------------------------------------------------------------------------
	
//	--------------------------------------------------------------------------------------
	
	public static abstract class QuestItemAbility extends AbstractAbility
	{}

//	--------------------------------------------------------------------------------------

	public static abstract class Tag extends QuestItemAbility
	{
		@Property("该标志的布尔条件(默认true)")
		public BooleanCondition boolean_condition = BooleanCondition.TRUE;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
		
		public boolean getBooleanConditionValue() {
			if (boolean_condition == BooleanCondition.FALSE) {
				return false;
			}
			return true;
		}
	}
	
	/**
	 * [条件] 物品
	 * @author WAZA
	 */
	@Property("[条件] 依赖的物品")
	final public static class TagItem extends Tag
	{
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("比较器")
		public Comparison 		comparison			= Comparison.EQUAL_OR_GREATER_THAN;
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
		@Property("物品-是否消耗掉")
		public boolean			is_expense			= true;
	}
	
	/**
	 * [条件] 依赖已完成的任务
	 * @author WAZA
	 */
	@Property("[条件] 依赖已完成的任务")
	final public static class TagQuest extends Tag
	{
		@Property("已完成的任务ID")
		public int				quest_id	= -1;
	}
	
	/**
	 * [条件] 依赖的任务奖励
	 * @author WAZA
	 */
	@Property("[条件] 依赖的任务奖励")
	final public static class TagQuestItem extends Tag
	{
		@Property("任务ID")
		public int				quest_id			= -1;
		
		@Property("依赖的任务奖励ID")
		public int				quest_item_index	= -1;
	}
	
	/**
	 * [条件] 依赖的任务状态
	 * @author WAZA
	 */
	@Property({"[条件] 依赖当前的任务状态(杀死敌人数)", "(不能用作接受条件)"})
	final public static class TagQuestStateKillMonsterComparison extends Tag
	{
		transient private int					quest_id		= -1;

		@Property("杀死的单位模板ID")
		public Integer				kill_unit_id	= -1;

		@Property("比较器")
		public Comparison 			comparison		= Comparison.EQUAL;
		
		@Property("杀死的单位数量")
		public AbstractValue		kill_count		= new Value(1);
	}

	/**
	 * [条件] 比较条件
	 * @author WAZA
	 */
	@Property("[条件] 比较条件")
	public static class TagValueComparison extends Tag
	{
		@Property("原值")
		public AbstractValue		src_value	= new Value(1);
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
	}
	
	@Property("[条件] 每个单位属性")
	public static class TagEveryUnitComparison extends Tag
	{
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
	}
	
	@Property("[条件] 至少一个单位属性")
	public static class TagOneMoreUnitComparison extends Tag
	{
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();
		
		@Property("比较器")
		public Comparison 			comparison	= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
	}
	
	@Property("[条件] 单位组数量")
	public static class TagUnitGroupCountComparison extends Tag
	{
		@Property("单位类型")
		public TriggerUnitType 		group_unit_type	= TriggerUnitType.PLAYER_GROUP;
		
		@Property("比较器")
		public Comparison 			comparison		= Comparison.EQUAL;
		
		@Property("目标值")
		public AbstractValue		dst_value		= new Value(1);
	}
	
//	--------------------------------------------------------------------------------------
	
	public static abstract class Result extends QuestItemAbility
	{
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	
	/**
	 * [奖励] 物品
	 * @author WAZA
	 */
	@Property("[结果] 奖励物品")
	final public static class AwardItem extends Result
	{
//		@Property("单位类型")
//		public TriggerUnitType trigger_unit_type	= TriggerUnitType.PLAYER;
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
		@Property("物品-得到道具后，自动使用掉")
		public boolean			titem_auto_use		= false;
	}
	
	/**
	 * [奖励] 传送到某场景
	 * @author WAZA
	 */
	@Property("[结果] 传送到某场景")
	final public static class AwardTeleport extends Result
	{
		@Property("场景ID")
		public String			scene_id;
		
		@Property("场景内特定单位名字")
		public String 			scene_object_id;
	}
	
	/**
	 * [奖励] 开始战斗
	 * @author WAZA
	 */
	@Property("[结果] 开始战斗")
	final public static class AwardBattle extends Result
	{
		@Property("和指定单位类型战斗")
		public Integer			unit_id			= -1;
	}
	
	
	@Property("[结果] 增加单位属性")
	final public static class AwardAddUnitProperty extends Result
	{
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();

		@Property("增量")
		public AbstractValue		increment	= new Value(1);
	}
	
	@Property("[结果] 设置单位属性")
	final public static class AwardSetUnitProperty extends Result
	{
		@Property("原单位属性")
		public TriggerUnitProperty	src_value	= new TriggerUnitProperty();

		@Property("目标值")
		public AbstractValue		dst_value	= new Value(1);
	}
	
	@Property("[结果] 强制丢弃物品")
	final public static class DropItem extends Result
	{
		@Property("物品-类型")
		public int				titem_index			= -1;
		@Property("物品-数量")
		public AbstractValue	titem_count			= new Value(1);
	}
}
