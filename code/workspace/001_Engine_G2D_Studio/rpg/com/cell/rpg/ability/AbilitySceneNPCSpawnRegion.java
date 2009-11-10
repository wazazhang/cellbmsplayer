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
	public AbilitiesVector spawn_types = new AbilitiesVector(AbilitySceneNPCSpawn.class) 
	{
		private static final long serialVersionUID = 1L;
		public String toString() {
			StringBuffer sb = new StringBuffer();
			for (AbilitySceneNPCSpawn a : getAbilities(AbilitySceneNPCSpawn.class)) {
				if (a.xls_primary_key!=null) {
					sb.append(a.xls_primary_key.desc+",");
				}else{
					sb.append("null,");
				}
			}
			return sb.toString();
		}
	};

	@Override
	public boolean isMultiField() {
		return false;
	}
	
	@Override
	public String toString() {
		return super.toString() + " : " + unit_trig + " : max=" + spawn_unit_count + " : types=" + spawn_types;
	}
}
