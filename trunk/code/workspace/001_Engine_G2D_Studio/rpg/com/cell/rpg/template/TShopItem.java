package com.cell.rpg.template;

import java.util.ArrayList;

import com.g2d.annotation.Property;

@Property("商品")
public class TShopItem extends TemplateNode
{
	@Property("道具ID")
	public int item_template_id;
	
	public TShopItem(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
	
}
