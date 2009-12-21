package com.cell.rpg.quest;

import java.util.concurrent.TimeUnit;

import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbilitiesSingle;
import com.cell.rpg.ability.AbilitiesTypeMap;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

public class QuestFlags extends AbilitiesTypeMap
{
	public QuestFlags() {
		super.addAbility(new System());
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				System.class,
				Story.class,
				Repeatable.class,
				Scheduled.class,
		};
	}
	
//	-----------------------------------------------------------------------------------
	static abstract class QuestFlagAbility extends AbstractAbility {
		@Override
		public boolean isMultiField() {
			return false;
		}
	}
//	-----------------------------------------------------------------------------------

	

//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 系统任务", "满足条件后，立即可以做"}
	 * @author WAZA
	 */
	@Property({"[任务类型] 系统任务", "满足条件后，立即可以做"})
	public static class System extends QuestFlagAbility {
		
	}

//	-----------------------------------------------------------------------------------
	/**
	 * [任务类型] 主线任务
	 * @author WAZA
	 */
	@Property({"[任务类型] 主线任务", "由剧情需要，只能做一次的任务"})
	public static class Story extends QuestFlagAbility {
		
	}

//	-----------------------------------------------------------------------------------
	/**
	 * {"[任务类型] 重复任务", "即在完成后，一段时间后可重新做"}
	 * @author WAZA
	 */
	@Property({"[任务类型] 重复任务", "即在完成后，一段时间后可重新做"})
	public static class Repeatable extends QuestFlagAbility 
	{
		@Property("可重复的次数，0表示无限制")
		public int			repeat_count		= 0;
		
		@Property("完成该任务多少时间内刷新为可做")
		public long 		refresh_time		= 1;
		
		@Property("时间单位")
		public TimeUnit 	refresh_time_unit	= TimeUnit.DAYS;
		
		public long getRefreshTimeMillis() {
			return refresh_time_unit.toMillis(refresh_time);
		}
	}
	
//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 定时任务", "每隔一定时间后，可重新做"}
	 * @author WAZA
	 */
	@Property({"[任务类型] 定时任务", "每隔一定时间后，可重新做"})
	public static class Scheduled extends QuestFlagAbility
	{
		@Property("每隔多长时间刷新")
		public long 		refresh_time	= 1;
		
		@Property("时间单位")
		public TimeUnit 	refresh_time_unit	= TimeUnit.DAYS;
		
		public long getRefreshTimeMillis() {
			return refresh_time_unit.toMillis(refresh_time);
		}
	}

	
//	-----------------------------------------------------------------------------------
	
	
//	-----------------------------------------------------------------------------------
	
	
//	-----------------------------------------------------------------------------------

}
