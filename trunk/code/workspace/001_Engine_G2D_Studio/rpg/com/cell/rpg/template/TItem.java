package com.cell.rpg.template;

public class TItem extends TemplateNode
{
	public TItem(String id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
}
