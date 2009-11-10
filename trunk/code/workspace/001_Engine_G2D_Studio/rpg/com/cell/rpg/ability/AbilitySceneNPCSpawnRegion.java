package com.cell.rpg.ability;

import com.g2d.annotation.Property;

/**
 * @author WAZA
 */
@Property("[区域能力] NPC产生区域")
public class AbilitySceneNPCSpawnRegion extends AbstractAbility 
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 生成该区域单位的触发条件
	 */
	public static enum UnitTrig {
		VISIBLE,
		HIDE,
	}
	
	@Property("产生的单位触发条件")
	public UnitTrig unit_trig;
	
	@Property("产生的单位最大数量")
	public int spawn_unit_count;
	
	@Property("产生的单位")
	public AbilitiesVector spawn_types = new AbilitiesVector(AbilitySceneNPCSpawn.class);

	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + unit_trig + " : max=" + spawn_unit_count + " : types=" + spawn_types;
	}
}
