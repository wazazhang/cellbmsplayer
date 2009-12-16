package com.cell.reflect;

public class Parser 
{
	@SuppressWarnings("unchecked")
	public static <T> T stringToObject(String str, Class<T> cls) {
		try {
			// 基础类型
			if (cls.equals(Byte.class) || cls.equals(byte.class)) {
				return (T)(new Byte(str));
			} 
			if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
				return (T)(new Boolean(str));
			} 
			if (cls.equals(Short.class) || cls.equals(short.class)) {
				return (T)(new Short(str));
			} 
			if (cls.equals(Character.class) || cls.equals(char.class)) {
				return (T)(new Character(str.charAt(0)));
			} 
			if (cls.equals(Integer.class) || cls.equals(int.class)) {
				return (T)(new Integer(str));
			} 
			if (cls.equals(Long.class) || cls.equals(long.class)) {
				return (T)(new Long(str));
			} 
			if (cls.equals(Float.class) || cls.equals(float.class)) {
				return (T)(new Float(str));
			} 
			if (cls.equals(Double.class) || cls.equals(double.class)) {
				return (T)(new Double(str));
			}
			// 字符
			else if (cls.equals(String.class)) {
				return (T)(str);
			}
		} catch (Exception e) {
		}

		return null;
	}

	public static String objectToString(Object obj) {
		return obj.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T castNumber(Object obj, Class<T> cls)
	{
		// 基础类型
		if (obj instanceof Number) {
			Number num = (Number)obj;
			if (cls.equals(Byte.class) || cls.equals(byte.class)) {
				return (T)(new Byte(num.byteValue()));
			} 
			if (cls.equals(Short.class) || cls.equals(short.class)) {
				return (T)(new Short(num.shortValue()));
			} 
			if (cls.equals(Integer.class) || cls.equals(int.class)) {
				return (T)(new Integer(num.intValue()));
			} 
			if (cls.equals(Long.class) || cls.equals(long.class)) {
				return (T)(new Long(num.longValue()));
			} 
			if (cls.equals(Float.class) || cls.equals(float.class)) {
				return (T)(new Float(num.floatValue()));
			} 
			if (cls.equals(Double.class) || cls.equals(double.class)) {
				return (T)(new Double(num.doubleValue()));
			}
		}
		return null;
	}
}
