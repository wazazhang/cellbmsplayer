package com.cell.reflect;

public class Parser 
{
	@SuppressWarnings("unchecked")
	public static <T> T stringToObject(String str, Class<T> return_type) {
		try {
			// 基础类型
			if (return_type.equals(Byte.class) || return_type.equals(byte.class)) {
				return (T)(new Byte(str));
			} 
			if (return_type.equals(Boolean.class) || return_type.equals(boolean.class)) {
				return (T)(new Boolean(str));
			} 
			if (return_type.equals(Short.class) || return_type.equals(short.class)) {
				return (T)(new Short(str));
			} 
			if (return_type.equals(Character.class) || return_type.equals(char.class)) {
				return (T)(new Character(str.charAt(0)));
			} 
			if (return_type.equals(Integer.class) || return_type.equals(int.class)) {
				return (T)(new Integer(str));
			} 
			if (return_type.equals(Long.class) || return_type.equals(long.class)) {
				return (T)(new Long(str));
			} 
			if (return_type.equals(Float.class) || return_type.equals(float.class)) {
				return (T)(new Float(str));
			} 
			if (return_type.equals(Double.class) || return_type.equals(double.class)) {
				return (T)(new Double(str));
			}
			// 字符
			else if (return_type.equals(String.class)) {
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
	public static <T> T castNumber(Object obj, Class<T> return_type)
	{
		// 基础类型
		if (obj instanceof Number) {
			Number num = (Number)obj;
			if (return_type.equals(Byte.class) || return_type.equals(byte.class)) {
				return (T)(new Byte(num.byteValue()));
			} 
			if (return_type.equals(Short.class) || return_type.equals(short.class)) {
				return (T)(new Short(num.shortValue()));
			} 
			if (return_type.equals(Integer.class) || return_type.equals(int.class)) {
				return (T)(new Integer(num.intValue()));
			} 
			if (return_type.equals(Long.class) || return_type.equals(long.class)) {
				return (T)(new Long(num.longValue()));
			} 
			if (return_type.equals(Float.class) || return_type.equals(float.class)) {
				return (T)(new Float(num.floatValue()));
			} 
			if (return_type.equals(Double.class) || return_type.equals(double.class)) {
				return (T)(new Double(num.doubleValue()));
			}
		}
		return null;
	}
	
	public static boolean isNumber(Class<?> cls)
	{
		// 基础类型
		if (Number.class.isAssignableFrom(cls)) {
			return true;
		}
		if (cls.equals(byte.class)) {
			return true;
		} 
		if (cls.equals(short.class)) {
			return true;
		} 
		if (cls.equals(int.class)) {
			return true;
		} 
		if (cls.equals(long.class)) {
			return true;
		} 
		if (cls.equals(float.class)) {
			return true;
		} 
		if (cls.equals(double.class)) {
			return true;
		}
		return false;
	}
}
