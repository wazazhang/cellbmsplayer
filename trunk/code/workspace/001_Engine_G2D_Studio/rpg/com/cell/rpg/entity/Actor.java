package com.cell.rpg.entity;

import com.cell.rpg.ability.AbilitySceneNPC;
import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.display.UnitNode;

public class Actor extends Unit
{
	public Actor(String cpj_name, String cpj_sprite_id) 
	{
		display_node = new UnitNode(cpj_name, cpj_sprite_id);
		
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				AbilitySceneTransport.class,	
				AbilitySceneNPC.class,
			};
	}
}
