package com.cell.rpg.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.cell.rpg.item.anno.ItemType;

public class ItemPropertyTypes 
{
	
//	-------------------------------------------------------------------------------------
	
	private static HashMap<Integer, Class<?>> 
								item_property_types_map = new HashMap<Integer, Class<?>>();
	
	private static Class<?>[]	item_property_types;
	
	public static void setItemPropertyTypes(Class<?>[] types) {
		for (Class<?> type : types) {						
			ItemType itype = type.getAnnotation(ItemType.class);
			if (itype!=null) {
				if (ItemPropertyTemplate.class.isAssignableFrom(type)) {
					if (item_property_types_map.containsKey(itype.value())) {
//						System.err.println(type.getName() + " is already exist !");
					} else {
						item_property_types_map.put(itype.value(), type);
//						System.out.println("add item property : " + type + " : " + type.getClassLoader());
					}
				}
			}
		}
		item_property_types = item_property_types_map.values().toArray(new Class<?>[item_property_types_map.size()]);
	}
	
	public static void setItemPropertyTypes(Collection<Class<?>> types) {
		setItemPropertyTypes(types.toArray(new Class<?>[types.size()]));
	}

	public static Class<?> getItemPropertyType(int type) {
		return item_property_types_map.get(type);
	}
	
	public static Class<?>[] getItemPropertyTypes() {
		return item_property_types;
	}
	
//	-------------------------------------------------------------------------------------
}
