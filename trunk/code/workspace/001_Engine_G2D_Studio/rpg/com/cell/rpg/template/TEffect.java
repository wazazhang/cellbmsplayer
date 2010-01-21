package com.cell.rpg.template;


public class TEffect extends TemplateNode
{	
	public TEffect(int id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
}
