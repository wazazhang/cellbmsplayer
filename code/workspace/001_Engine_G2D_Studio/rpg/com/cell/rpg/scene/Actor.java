package com.cell.rpg.scene;

import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.ability.ActorPathStart;
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
//				ActorDropItem.class,
//				ActorSellItem.class,
				QuestAccepter.class,
				QuestPublisher.class,
			};
	}
}
