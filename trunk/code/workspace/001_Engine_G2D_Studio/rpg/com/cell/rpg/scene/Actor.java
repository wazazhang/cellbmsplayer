package com.cell.rpg.scene;

import com.cell.rpg.display.UnitNode;
import com.cell.rpg.scene.ability.ActorDropItem;
import com.cell.rpg.scene.ability.ActorPathStart;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.struct.TPosition;
import com.g2d.annotation.Property;

public class Actor extends SceneSprite
{
	final public String	template_unit_id;

	@Property("警戒范围")
	public int			look_range		= 300;
	
	public Actor(String id, String template_unit_id) 
	{	
		super(id);
		this.template_unit_id = template_unit_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				ActorPathStart.class,
				ActorDropItem.class,
			};
	}
}
