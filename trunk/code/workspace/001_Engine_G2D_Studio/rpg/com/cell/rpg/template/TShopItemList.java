package com.cell.rpg.template;

import java.util.ArrayList;

import com.g2d.annotation.Property;

public class TShopItemList extends TemplateNode 
{
	@Property("出售列表中的商品")
	public ArrayList<Integer>	shopitems_id = new ArrayList<Integer>();
	
	public TShopItemList(int id, String name) {
		super(id, name);
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class[] {};
	}

}
