package com.cell.rpg.template;

import com.cell.rpg.ability.AbilitySceneNPCTeam;
import com.cell.rpg.display.UnitNode;
import com.g2d.annotation.Property;

public class TUnit extends TemplateNode
{
	public UnitNode				display_node;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public TUnit(
			String id, 
			String name, 
			String cpj_name, 
			String cpj_set_id) 
	{
		super(id, name);

		this.display_node = new UnitNode(cpj_name, cpj_set_id);
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{
				AbilitySceneNPCTeam.class,
		};
	}
}
