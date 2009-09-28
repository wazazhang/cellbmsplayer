package com.cell.rpg.ability;

import com.cell.CUtil;

import com.g2d.annotation.Property;

/**
 * [区域能力] NPC产生区域单位类型 
 * 该对象放置在 {@link AbilitySceneNPCSpawnRegion}
 * @author WAZA
 * @see AbilitySceneNPCSpawnRegion
 */
@Property("[区域能力] NPC产生区域单位类型")
public class AbilitySceneNPCSpawn extends AbilityXLS 
{
	private static final long serialVersionUID = 1L;
	
	/** 产生百分比，即该区域在生成NPC时，生成到该类型的几率 */
	@Property("产生百分比，即该区域在生成NPC时，生成到该类型的几率")
	public float 		spawn_percent;
	
	/** 对应actor对象名 */
	@Property("对应actor对象名")
	public	String		cpj_actor_name;
	
	/** 绑定的NPC队伍 */
	@Property("绑定的NPC队伍")
	public AbilitySceneNPCTeam team = new AbilitySceneNPCTeam();
	
	/**
	 * 获得 ActorSet 名
	 * @return
	 */
	public String getActorCPJName() {
		return CUtil.splitString(cpj_actor_name, "/")[0];
	}
	
	/**
	 * 获得 ActorSet 里的 ActorID
	 * @return
	 */
	public String getActorCPJSpriteName() {
		return CUtil.splitString(cpj_actor_name, "/")[1];
	}
	
	@Override
	public boolean isMultiField() {
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + 
		Float.toString(spawn_percent) + "% : " + 
		cpj_actor_name;
	}
}
