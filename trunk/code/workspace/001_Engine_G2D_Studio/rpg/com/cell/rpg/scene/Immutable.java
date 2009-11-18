package com.cell.rpg.scene;

import com.cell.rpg.display.UnitNode;
import com.cell.rpg.scene.ability.ActorTransport;

public class Immutable extends SceneSprite
{
	final UnitNode		display_node;
	
	public Immutable(String id, String cpj_name, String set_name) 
	{	
		super(id);
		display_node = new UnitNode(cpj_name, set_name);
	}

	public UnitNode getDisplayNode() {
		return display_node;
	}
	
	@Override
	public Class<?>[] getSubAbilityTypes() {
		return new Class<?>[]{				
				ActorTransport.class,	
		};
	}
	
}
