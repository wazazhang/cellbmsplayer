package com.cell.rpg.item;

import java.util.Collection;

public class ItemPropertyTypes 
{
	
//	-------------------------------------------------------------------------------------
	private static Class<?>[] item_property_types;

	public static void setItemPropertyTypes(Collection<Class<?>> types) {
		item_property_types = types.toArray(new Class<?>[types.size()]);
	}
	
	static Class<?>[] getItemPropertyTypes() {
		return item_property_types;
	}
//	-------------------------------------------------------------------------------------
}
