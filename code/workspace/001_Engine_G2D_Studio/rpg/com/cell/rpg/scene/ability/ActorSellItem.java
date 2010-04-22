package com.cell.rpg.scene.ability;

import com.cell.rpg.template.ability.ItemListID;
import com.g2d.annotation.Property;


@Property("[单位能力] 覆盖出售物品")
public class ActorSellItem extends ItemListID implements IActorAbility
{
	private static final long serialVersionUID = 1L;
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Override
	public String getTalk() {
		return npc_talk;
	}
	
}
