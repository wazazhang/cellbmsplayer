package com.cell.rpg.template;

public class TSkill extends TemplateNode
{
	public TSkill(int id, String name) {
		super(id, name);
	}

	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[] {};
	}
	
}
