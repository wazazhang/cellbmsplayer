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
import com.cell.rpg.scene.ability.ActorTransportCraft;
import com.g2d.annotation.Property;

public class Actor extends SceneSprite
{
	final public String	template_unit_id;
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Property("警戒范围")
	public int			look_range		= 300;
	
	public Actor(String id, String template_unit_id) 
	{	
		super(id);
		this.template_unit_id = template_unit_id;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[] {
				// quests
				QuestAccepter.class,
				QuestPublisher.class,
				// flags
				ActorTalk.class,
				ActorSellItem.class,
				ActorBank.class,
				ActorJobTrainer.class,
				ActorSkillTrainer.class,
				ActorTransportCraft.class,
				// path
				ActorDropItem.class,
				ActorPathStart.class,
			};
	}
}
