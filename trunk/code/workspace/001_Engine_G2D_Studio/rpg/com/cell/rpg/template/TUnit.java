package com.cell.rpg.template;

import com.cell.rpg.display.UnitNode;
import com.cell.rpg.template.ability.UnitBattleTeam;



public class TUnit extends TemplateNode
{
	UnitNode		display_node;
	
	UnitBattleTeam	battle_team		= new UnitBattleTeam();
	
	public TUnit(
			String id, 
			String name) 
	{
		super(id, name);
	}
	
	public void setDisplayNode(String cpj_name, String set_name) {
		display_node = new UnitNode(cpj_name, set_name);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				UnitBattleTeam.class,
		};
	}
}
