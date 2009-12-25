package com.cell.util.task;

import com.cell.util.Pair;
import com.cell.util.DateUtil.DayOfWeek;
import com.cell.util.DateUtil.MonthOfYear;

public class CronExpression 
{
	public static enum DateType
	{
		DAY_OF_MONTH, 
//		WEEK_OF_YEAR,
		WEEK_OF_MONTH,
		;
		public String toString() {
			switch(this){
			case DAY_OF_MONTH:
				return "日期时间";
//			case WEEK_OF_YEAR:
//				return "年的第几周";
			case WEEK_OF_MONTH:
				return "月的第几周";
			}
			return super.toString();
		}
	}

	public DateType			type			= DateType.DAY_OF_MONTH;
	
	/** the year minus 1900.*/
	final public Pair<Short, Boolean> year;
	
	
	/** the month between 1-12.*/
	final public Pair<Byte, Boolean> 	month;
	/** the day of the month between 1-31.*/
	final public Pair<Byte, Boolean> 	day_of_month;
	

//	/** the week between 1-54.*/
//	final public Pair<Byte, Boolean> 	week_of_year;

	/** the week of month between 1-4.*/
	final public Pair<Byte, Boolean> 	week_of_month;

	/** the day of the week between 1-7. (SUNDAY is 1) */
	final public Pair<Byte, Boolean> 	day_of_week;
	
	/** the hours between 0-23.*/
	final public Pair<Byte, Boolean> 	hour;
	/** the minutes between 0-59.*/
	final public Pair<Byte, Boolean> 	minute;
	/** the seconds between 0-59.*/
	final public Pair<Byte, Boolean> 	second;
	
	
	public CronExpression() {
		year			= new Pair<Short, Boolean>((short)2009, true);
		month 			= new Pair<Byte, Boolean>(MonthOfYear.JANUARY.getValue(), false);
		day_of_month 	= new Pair<Byte, Boolean>((byte)1, false);
//		week_of_year 	= new Pair<Byte, Boolean>((byte)1, true);
		week_of_month	= new Pair<Byte, Boolean>((byte)1, true);
		day_of_week 	= new Pair<Byte, Boolean>(DayOfWeek.SUNDAY.getValue(), false);
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
		
		switch (type) {
		case DAY_OF_MONTH:
			if (month.getValue()) {
				sb.append("每月");
			} else {
				sb.append(MonthOfYear.fromValue(month.getKey()));
			}
			sb.append(" ");
			
			if (day_of_month.getValue()) {
				sb.append("每日");
			} else {
				sb.append(day_of_month.getKey()+"日");
			}
			sb.append(" ");
			break;
//		case WEEK_OF_YEAR:
//			if (week_of_year.getValue()) {
//				sb.append("每周");
//			} else {
//				sb.append("第"+week_of_year.getKey()+"周");
//			}
//			sb.append(" ");
//			
//			if (day_of_week.getValue()) {
//				sb.append("每星期");
//			} else {
//				sb.append(DayOfWeek.fromValue(day_of_week.getKey()));
//			}
//			sb.append(" ");
//			break;
		case WEEK_OF_MONTH:
			if (month.getValue()) {
				sb.append("每月");
			} else {
				sb.append(MonthOfYear.fromValue(month.getKey()));
			}
			sb.append(" ");
			if (week_of_month.getValue()) {
				sb.append("每周");
			} else {
				sb.append("第"+week_of_month.getKey()+"周");
			}
			sb.append(" ");
			if (day_of_week.getValue()) {
				sb.append("每星期");
			} else {
				sb.append(DayOfWeek.fromValue(day_of_week.getKey()));
			}
			sb.append(" ");
			break;
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

	

	public String toCronExpression() 
	{
		CronExpression cron = this;
		
		StringBuffer sb = new StringBuffer();
		
		if (cron.second.getValue())				sb.append("* ");
		else 									sb.append(cron.second.getKey() + " ");
		
		if (cron.minute.getValue())				sb.append("* ");
		else 									sb.append(cron.minute.getKey() + " ");
		
		if (cron.hour.getValue()) 				sb.append("* ");
		else 									sb.append(cron.hour.getKey() + " ");
		
		switch (cron.type) {
		case DAY_OF_MONTH:
			if (cron.day_of_month.getValue())	sb.append("* ");
			else 								sb.append(cron.day_of_month.getKey() + " ");
			
			if (cron.month.getValue()) 			sb.append("* ");
			else 								sb.append(cron.month.getKey() + " ");
			
												sb.append("? ");
			break;
		case WEEK_OF_MONTH:
												sb.append("? ");
												
			if (cron.month.getValue()) 			sb.append("* ");
			else 								sb.append(cron.month.getKey() + " ");
			
			
			if (cron.day_of_week.getValue())	sb.append("*");
			else								sb.append(cron.day_of_week.getKey());
			if (cron.week_of_month.getValue())	sb.append(" ");
			else								sb.append("#"+cron.week_of_month.getKey()+" ");
			
			
			break;
		}
		
		if (cron.year.getValue()) {
			sb.append("* ");
		} else {
			sb.append(cron.year.getKey() + " ");
		}

		return sb.toString();
	}
	
}
