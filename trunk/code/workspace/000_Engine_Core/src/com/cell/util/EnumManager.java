package com.cell.util;

import java.util.EnumSet;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class EnumManager
{
	public static interface ValueEnum <K>
	{
		public K getValue();
	}
	
	static HashMap<Class, HashMap> Types = new HashMap<Class, HashMap>();
	
	public static <K, V extends ValueEnum<K>> V getEnum(Class cls, K k) 
	{
		HashMap<K, V> map = Types.get(cls);
		if (map == null) {
			map = new HashMap<K, V>();
			for (Object s : EnumSet.allOf(cls)) {
				V sv = (V)s;
				map.put(sv.getValue(), sv);
			}
		}
		
		V v = map.get(k);
		
		if (v==null) {
			System.err.println(cls.getName() + " can not create from value \"" + k + "\"");
		}
		
		return v;
	}
	
	public static int getEnumCount(Class cls){
		HashMap map = Types.get(cls);
		return map.size();
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	static enum TT implements ValueEnum<Integer>
	{
		T1(1),
		T2(2),
		;
	
		final int Type ;
		TT(int type) {
			Type = type;
		}
		
		public Integer getValue() {
			return Type;
		}
		
	}
	
	public static void main(String[] args) 
	{
		System.out.println(getEnum(TT.class, 1));
	}
	
}
