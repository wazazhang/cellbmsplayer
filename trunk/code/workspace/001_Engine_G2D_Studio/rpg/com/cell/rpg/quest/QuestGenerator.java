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
import com.cell.rpg.struct.DateUtil.TimeObject;
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
			final public Pair<Short, Boolean> year;
			
			
			/** the month between 1-12.*/
			final public Pair<Byte, Boolean> 	month;
			/** the day of the month between 1-31.*/
			final public Pair<Byte, Boolean> 	day_of_month;
			
			
			/** the week between 1-54.*/
			final public Pair<Byte, Boolean> 	week_of_year;
			/** the day of the week between 1-7. (SUNDAY is 1) */
			final public Pair<Byte, Boolean> 	day_of_week;
			
			
			/** the hours between 0-23.*/
			final public Pair<Byte, Boolean> 	hour;
			/** the minutes between 0-59.*/
			final public Pair<Byte, Boolean> 	minute;
			/** the seconds between 0-59.*/
			final public Pair<Byte, Boolean> 	second;
			
			
			public FestivalDate() {
				year			= new Pair<Short, Boolean>((short)2009, true);
				month 			= new Pair<Byte, Boolean>(YearMonth.JANUARY.getValue(), false);
				day_of_month 	= new Pair<Byte, Boolean>((byte)1, false);
				week_of_year 	= new Pair<Byte, Boolean>((byte)1, true);
				day_of_week 	= new Pair<Byte, Boolean>(WeekDay.SUNDAY.getValue(), false);
				hour 			= new Pair<Byte, Boolean>((byte)0, false);
				minute 			= new Pair<Byte, Boolean>((byte)0, false);
				second 			= new Pair<Byte, Boolean>((byte)0, false);
			}
			
			@Override
			public String toString() 
			{
				StringBuffer sb = new StringBuffer();
				
				if (year.getValue()) {
					sb.append("每年");
				} else {
					sb.append(year.getKey() + "年");
				}
				sb.append(" ");
				
				
				if (type == FestivalType.DAY_OF_MONTH) 
				{
					if (month.getValue()) {
						sb.append("每月");
					} else {
						sb.append(YearMonth.fromValue(month.getKey()));
					}
					sb.append(" ");
					
					if (day_of_month.getValue()) {
						sb.append("每日");
					} else {
						sb.append(day_of_month.getKey()+"日");
					}
					sb.append(" ");
				} 
				else 
				{
					if (week_of_year.getValue()) {
						sb.append("每周");
					} else {
						sb.append("第"+week_of_year.getKey()+"周");
					}
					sb.append(" ");
					
					if (day_of_week.getValue()) {
						sb.append("每星期");
					} else {
						sb.append(WeekDay.fromValue(day_of_week.getKey()));
					}
					sb.append(" ");
					
				}
				
				if (hour.getValue()) {
					sb.append("每小时");
				} else {
					sb.append(hour.getKey()+"时");
				}
				sb.append(" ");
				
				if (minute.getValue()) {
					sb.append("每分钟");
				} else {
					sb.append(minute.getKey()+"分");
				}
				sb.append(" ");
				
				if (second.getValue()) {
					sb.append("每秒");
				} else {
					sb.append(second.getKey()+"秒");
				}
				sb.append(" ");
				
				return sb.toString();
			}
		}
		
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
