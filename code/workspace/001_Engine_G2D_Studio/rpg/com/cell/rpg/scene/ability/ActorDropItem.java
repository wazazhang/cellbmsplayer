package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.template.ability.ItemListID;
import com.cell.rpg.template.ability.UnitDropItem;
import com.g2d.annotation.Property;


@Property("[单位数据] 覆盖掉落物品")
public class ActorDropItem extends ItemListID implements IActorAbility
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
