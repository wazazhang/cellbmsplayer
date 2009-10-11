package com.cell.rpg.ability;


import com.g2d.annotation.Property;

/**
 * 一般指单独编辑到场景里的单位
 * @author WAZA
 */
@Property("[单位能力] NPC数据绑定")
public class AbilitySceneNPC extends AbilityXLS 
{
	private static final long serialVersionUID = 1L;
	
	/** 绑定的NPC队伍 */
	@Property("绑定的NPC队伍")
	public AbilitySceneNPCTeam team = new AbilitySceneNPCTeam();
	
	@Override
	public boolean isMultiField() {
		return false;
	}
	
}
