package com.cell.rpg.scene.ability;

import com.cell.CUtil;
import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.g2d.annotation.Property;

/**
 * @author WAZA
 */
@Property("[区域能力] NPC产生区域")
public class NPCSpawnRegion extends AbstractAbility 
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
	public AbilitiesVector spawn_types = new AbilitiesVector(NPCSpawn.class);

	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + unit_trig + " : max=" + spawn_unit_count + " : types=" + spawn_types;
	}
	
	

	/**
	 * [区域能力] NPC产生区域单位类型 
	 * 该对象放置在 {@link NPCSpawnRegion}
	 * @author WAZA
	 * @see NPCSpawnRegion
	 */
	@Property("[区域能力] NPC产生区域单位类型")
	public static class NPCSpawn extends AbstractAbility 
	{
		private static final long serialVersionUID = 1L;
		
		/** 产生百分比，即该区域在生成NPC时，生成到该类型的几率 */
		@Property("产生百分比，即该区域在生成NPC时，生成到该类型的几率")
		public float 		spawn_percent;
		
		/** 对应actor对象名 */
		@Property("对应actor对象名")
		public	String		template_unit_id;
		
		@Override
		public boolean isMultiField() {
			return true;
		}
	}
}
