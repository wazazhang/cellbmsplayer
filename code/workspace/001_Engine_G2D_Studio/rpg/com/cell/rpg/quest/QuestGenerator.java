package com.cell.rpg.quest;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cell.rpg.ability.AbilitiesList;
import com.cell.rpg.ability.AbilitiesTypeMap;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.QuestFlags.Repeatable;
import com.cell.rpg.quest.QuestFlags.Story;
import com.cell.util.Pair;
import com.cell.util.DateUtil.TimeObject;
import com.cell.util.task.CronExpression;
import com.g2d.annotation.Property;

public class QuestGenerator extends AbilitiesList
{
	public QuestGenerator() {
		addAbility(new System());
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				System.class,
				Scheduled.class,
				Festival.class,
		};
	}
	
//	-----------------------------------------------------------------------------------
	static abstract class QuestGeneratorAbility extends AbstractAbility {

	}
	
//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 系统任务", "满足条件后，立即可以做"}
	 * @author WAZA
	 */
	@Property({"[任务触发] 系统任务", "满足条件后，立即可以做"})
	public static class System extends QuestGeneratorAbility {
		@Override
		public boolean isMultiField() {
			return false;
		}
	}

//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 定时任务", "每隔一定时间后，可重新做"}
	 * @author WAZA
	 */
	@Property({"[任务触发] 定时任务", "每隔一定时间后，可重新做"})
	public static class Scheduled extends QuestGeneratorAbility
	{
		@Property("刷新时间")
		public TimeObject	refresh_time		= new TimeObject(1, TimeUnit.DAYS);
		
		@Property("持续时间")
		public TimeObject	persist_time		= new TimeObject(1, TimeUnit.DAYS);
		
		public long getRefreshTimeMillis() {
			return refresh_time.time_unit.toMillis(refresh_time.time_value);
		}
		@Override
		public boolean isMultiField() {
			return false;
		}
	}


//	-----------------------------------------------------------------------------------
	
	/**
	 * {"[任务类型] 节日任务", "在某个时间段内，重复刷新"}<br>
	 * @author WAZA
	 */
	@Property({"[任务类型] 节日任务", "在某个时间段内，重复刷新"})
	public static class Festival extends QuestGeneratorAbility
	{
		public static class FestivalDate extends CronExpression{}
		
		@Property("日期")
		public FestivalDate	date_time		= new FestivalDate();

		@Property("持续时间")
		public TimeObject	persist_time	= new TimeObject(1, TimeUnit.DAYS);
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
	

//	-----------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		Date date = new Date(java.lang.System.currentTimeMillis());
		Calendar ca = DateFormat.getDateInstance().getCalendar();
		java.lang.System.out.println(ca.get(Calendar.DAY_OF_WEEK));
	}
}
