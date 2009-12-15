package com.cell.reflect;

public class Parser 
{

	public static <T> T stringToObject(String str, Class<T> cls) {
		try {
			// 基础类型
			if (cls.equals(Byte.class) || cls.equals(byte.class)) {
				return cls.cast(new Byte(str));
			} else if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
				return cls.cast(new Boolean(str));
			} else if (cls.equals(Short.class) || cls.equals(short.class)) {
				return cls.cast(new Short(str));
			} else if (cls.equals(Character.class) || cls.equals(char.class)) {
				return cls.cast(new Character(str.charAt(0)));
			} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
				return cls.cast(new Integer(str));
			} else if (cls.equals(Long.class) || cls.equals(long.class)) {
				return cls.cast(new Long(str));
			} else if (cls.equals(Float.class) || cls.equals(float.class)) {
				return cls.cast(new Float(str));
			} else if (cls.equals(Double.class) || cls.equals(double.class)) {
				return cls.cast(new Double(str));
			}
			// 字符
			else if (cls.equals(String.class)) {
				return cls.cast(str);
			}
		} catch (Exception e) {
		}

		return null;
	}

	public static String objectToString(Object obj) {
		return obj.toString();
	}
	
	public static <T> T castNumber(Object obj, Class<T> cls)
	{
		// 基础类型
		if (obj instanceof Number) {
			Number num = (Number)obj;
			if (cls.equals(Byte.class) || cls.equals(byte.class)) {
				return cls.cast(num.byteValue());
			} else if (cls.equals(Short.class) || cls.equals(short.class)) {
				return cls.cast(num.shortValue());
			} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
				return cls.cast(num.intValue());
			} else if (cls.equals(Long.class) || cls.equals(long.class)) {
				return cls.cast(num.longValue());
			} else if (cls.equals(Float.class) || cls.equals(float.class)) {
				return cls.cast(num.floatValue());
			} else if (cls.equals(Double.class) || cls.equals(double.class)) {
				return cls.cast(num.doubleValue());
			}
		}
		return null;
	}
}
