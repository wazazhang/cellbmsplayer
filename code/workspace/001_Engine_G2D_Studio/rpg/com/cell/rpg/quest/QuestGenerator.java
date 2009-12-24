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
import com.cell.rpg.struct.DateUtil.WeekDay;
import com.cell.rpg.struct.DateUtil.YearMonth;
import com.cell.util.Pair;
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
		@Property("每隔多长时间刷新")
		public long 		refresh_time	= 1;
		
		@Property("时间单位")
		public TimeUnit 	refresh_time_unit	= TimeUnit.DAYS;
		
		public long getRefreshTimeMillis() {
			return refresh_time_unit.toMillis(refresh_time);
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
		public static enum FestivalType
		{
			DAY_OF_MONTH, 
			DAY_OF_WEEK,
			;
			public String toString() {
				switch(this){
				case DAY_OF_MONTH:
					return "日期时间";
				case DAY_OF_WEEK:
					return "星期时间";
				}
				return super.toString();
			}
		}
		
		public static class FestivalDate implements Serializable
		{
			public FestivalType			type			= FestivalType.DAY_OF_MONTH;
			
			/** the year minus 1900.*/
			public Pair<Short, Boolean> year			= new Pair<Short, Boolean>((short)2009, true);
			
			
			/** the month between 0-11.*/
			public Pair<Byte, Boolean> 	month 			= new Pair<Byte, Boolean>(YearMonth.JANUARY.getValue(), false);
			/** the day of the month between 1-31.*/
			public Pair<Byte, Boolean> 	day_of_month 	= new Pair<Byte, Boolean>((byte)1, false);
			
			
			/** the week between 0-53.*/
			public Pair<Byte, Boolean> 	week_of_year 	= new Pair<Byte, Boolean>((byte)0, true);
			/** the day of the week between 0-6. (SUNDAY is 6) */
			public Pair<Byte, Boolean> 	day_of_week 	= new Pair<Byte, Boolean>(WeekDay.SUNDAY.getValue(), false);
			
			
			/** the hours between 0-23.*/
			public Pair<Byte, Boolean> 	hour 			= new Pair<Byte, Boolean>((byte)0, false);
			/** the minutes between 0-59.*/
			public Pair<Byte, Boolean> 	minute 			= new Pair<Byte, Boolean>((byte)0, false);
			/** the seconds between 0-59.*/
			public Pair<Byte, Boolean> 	second 			= new Pair<Byte, Boolean>((byte)0, false);
		}
		
		@Property("日期")
		public FestivalDate		date		= new FestivalDate();
		
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
