package com.cell.rpg.quest;

import java.util.concurrent.TimeUnit;

import com.cell.rpg.ability.AbilitiesTypeMap;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.QuestFlags.Repeatable;
import com.cell.rpg.quest.QuestFlags.Story;
import com.g2d.annotation.Property;

public class QuestGenerator extends AbilitiesTypeMap
{
	public QuestGenerator() {}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				System.class,
				Scheduled.class,
		};
	}
	
//	-----------------------------------------------------------------------------------
	static abstract class QuestGeneratorAbility extends AbstractAbility {
		@Override
		public boolean isMultiField() {
			return false;
		}
	}
	
//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 系统任务", "满足条件后，立即可以做"}
	 * @author WAZA
	 */
	@Property({"[任务类型] 系统任务", "满足条件后，立即可以做"})
	public static class System extends QuestGeneratorAbility {
		
	}

//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 定时任务", "每隔一定时间后，可重新做"}
	 * @author WAZA
	 */
	@Property({"[任务类型] 定时任务", "每隔一定时间后，可重新做"})
	public static class Scheduled extends QuestGeneratorAbility
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

}
