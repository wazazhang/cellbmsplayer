package com.cell.rpg.scene.ability;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.annotation.Property;

/**
 * 提供单位场景传送点的能力
 * @author WAZA
 *
 */
@Property("[单位能力] 场景传送点")
public class ActorTransport extends AbstractAbility  implements IActorAbility
{
	private static final long serialVersionUID = 1L;
	
	@Property("传送到目标场景名字")
	public String next_scene_id;
	
	@Property("传送到目标场景的特定单位名字")
	public String next_scene_object_id;
	
	
	
	/** NPC闲话  */
	@Property("NPC闲话")
	public	String		npc_talk;
	
	@Override
	public String getTalk() {
		return npc_talk;
	}
	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : scene=" + next_scene_id + " : unit=" + next_scene_object_id;
	}
}
