package com.cell.rpg.quest;

import com.cell.rpg.ability.AbilitiesSingle;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

public class QuestType extends AbilitiesSingle
{
	public QuestType() {
		super.addAbility(new Story());
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				Story.class,
				Repeatable.class,
		};
	}
//	-----------------------------------------------------------------------------------
	static abstract class QuestTypeAbility extends AbstractAbility {
		@Override
		public boolean isMultiField() {
			return false;
		}
	}
//	-----------------------------------------------------------------------------------
	/**
	 * [任务类型] 主线任务，不能重复做
	 * @author WAZA
	 */
	@Property("[任务类型] 主线任务，不能重复做")
	public static class Story extends QuestTypeAbility {
		
	}
	
//	-----------------------------------------------------------------------------------
	/**
	 * [任务类型] 可重复接，即可在完成后，一段时间内重新做
	 * @author WAZA
	 */
	@Property("[任务类型] 可重复接，即可在完成后，一段时间内重新做")
	public static class Repeatable extends QuestTypeAbility 
	{
		/** 完成该任务多少时间内刷新为可做(秒) */
		@Property("完成该任务多少时间内刷新为可做(秒)")
		public long repeat_time_sec	= 0;
		
		@Property("可以接多少次，0代表无限制")
		public int repeat_count	= 0;

	}
//	-----------------------------------------------------------------------------------

//	-----------------------------------------------------------------------------------

}
