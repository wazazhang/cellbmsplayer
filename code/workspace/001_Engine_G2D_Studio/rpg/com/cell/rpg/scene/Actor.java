package com.cell.rpg.scene;

import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.ability.ActorBank;
import com.cell.rpg.scene.ability.ActorDropItem;
import com.cell.rpg.scene.ability.ActorJobTrainer;
import com.cell.rpg.scene.ability.ActorPathStart;
import com.cell.rpg.scene.ability.ActorSellItem;
import com.cell.rpg.scene.ability.ActorSkillTrainer;
import com.cell.rpg.scene.ability.ActorTalk;
import com.cell.rpg.scene.ability.ActorTransport;
import com.g2d.annotation.Property;

public class Actor extends SceneSprite
{
	final public String	template_unit_id;
	
	@Property("警戒范围")
	public int			look_range		= 300;

	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	public Actor(String id, String template_unit_id) 
	{	
		super(id);
		this.template_unit_id = template_unit_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{
				ActorTalk.class,
				
				ActorDropItem.class,
				ActorSellItem.class,
				ActorBank.class,
				
				ActorJobTrainer.class,
				ActorSkillTrainer.class,
				
				ActorPathStart.class,
				ActorTransport.class,
				
				QuestAccepter.class,
				QuestPublisher.class,
				
			};
	}
}
