package com.cell.rpg.entity.scene;

import com.cell.rpg.ability.AbilitySceneNPC;
import com.cell.rpg.ability.AbilitySceneNPCPathPoint;
import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.display.UnitNode;
import com.cell.rpg.entity.Unit;
import com.cell.rpg.entity.struct.TPosition;
import com.g2d.annotation.Property;

public class Actor extends SceneUnit
{
	public UnitNode		display_node;

	@Property("警戒范围")
	public int			look_range		= 300;
	
	@Property("触碰范围")
	public int			touch_range		= 30;

	
	public Actor(String cpj_name, String cpj_sprite_id) 
	{
		display_node = new UnitNode(cpj_name, cpj_sprite_id);
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				AbilitySceneTransport.class,	
				AbilitySceneNPC.class,
				AbilitySceneNPCPathPoint.class,
			};
	}
}
