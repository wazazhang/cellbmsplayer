package com.cell.reflect;

public class Parser 
{

	@SuppressWarnings("unchecked")
	public static <T> T stringToObject(String str, Class<T> cls) {
		try {
			// 基础类型
			if (cls.equals(Byte.class) || cls.equals(byte.class)) {
				return (T) new Byte(str);
			} else if (cls.equals(Boolean.class) || cls.equals(boolean.class)) {
				return (T) new Boolean(str);
			} else if (cls.equals(Short.class) || cls.equals(short.class)) {
				return (T) new Short(str);
			} else if (cls.equals(Character.class) || cls.equals(char.class)) {
				return (T) new Character(str.charAt(0));
			} else if (cls.equals(Integer.class) || cls.equals(int.class)) {
				return (T) new Integer(str);
			} else if (cls.equals(Long.class) || cls.equals(long.class)) {
				return (T) new Long(str);
			} else if (cls.equals(Float.class) || cls.equals(float.class)) {
				return (T) new Float(str);
			} else if (cls.equals(Double.class) || cls.equals(double.class)) {
				return (T) new Double(str);
			}
			// 字符
			else if (cls.equals(String.class)) {
				return (T) (str);
			}
		} catch (Exception e) {
		}

		return null;
	}

	public static String objectToString(Object obj) {
		return obj.toString();
	}

}
