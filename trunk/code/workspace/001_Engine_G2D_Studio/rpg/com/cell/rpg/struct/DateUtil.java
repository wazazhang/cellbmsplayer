package com.cell.rpg.struct;

import com.cell.util.EnumManager.ValueEnum;

public class DateUtil
{
	
	public static enum WeekDay implements ValueEnum<Byte>
	{
		SUNDAY(6), 
		MONDAY(0), 
		TUESDAY(1), 
		WEDNESDAY(2), 
		THURSDAY(3), 
		FRIDAY(4), 
		SATURDAY(5),
		;
		
		final Byte value;
		private WeekDay(Integer value) {
			this.value = value.byteValue();
		}
		@Override
		public Byte getValue() {
			return value;
		}
		@Override
		public String toString() {
			switch(this) {
			case SUNDAY:	return "星期日";
			case MONDAY:	return "星期一";
			case TUESDAY:	return "星期二";
			case WEDNESDAY:	return "星期三";
			case THURSDAY:	return "星期四";
			case FRIDAY:	return "星期五";
			case SATURDAY:	return "星期六";
			}
			return super.toString();
		}
	}
	
	public static enum YearMonth implements ValueEnum<Byte> 
	{
		JANUARY(1), 
		FEBRUARY(2),
		MARCH(3), 
		APRIL(4), 
		MAY(5), 
		JUNE(6), 
		JULY(7), 
		AUGUST(8), 
		SEPTEMBER(9), 
		OCTOBER(10),
		NOVEMBER(11), 
		DECEMBER(12), ;

		final Byte value;
		private YearMonth(Integer value) {
			this.value = value.byteValue();
		}
		@Override
		public Byte getValue() {
			return value;
		}
		@Override
		public String toString() {
			switch(this) {
			case JANUARY:	return "一月";
			case FEBRUARY:	return "二月";
			case MARCH:		return "三月";
			case APRIL:		return "四月";
			case MAY:		return "五月";
			case JUNE:		return "六月";
			case JULY:		return "七月";
			case AUGUST:	return "八月";
			case SEPTEMBER:	return "九月";
			case OCTOBER:	return "十月";
			case NOVEMBER:	return "十一月";
			case DECEMBER:	return "十二月";
			}
			return super.toString();
		}
	}
}
