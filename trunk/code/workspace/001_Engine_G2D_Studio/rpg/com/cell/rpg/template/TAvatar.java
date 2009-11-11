package com.cell.rpg.template;

import java.util.ArrayList;

import com.cell.rpg.display.UnitNode;


public class TAvatar extends TemplateNode
{
	public UnitNode body;
	
	public TAvatar(String id, String name) {
		super(id, name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return null;
	}
}
