package com.cell.rpg.template.ability;

import com.cell.rpg.ability.AbilitiesVector;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.g2d.annotation.Property;

@Property("[单位能力] 掉落物品")
public class UnitDropItem extends AbstractAbility
{
	private static final long serialVersionUID = 1L;

	@Property("产生的单位")
	public DropItemNode[] drop_types;
	
	public UnitDropItem() {}
	
	@Override
	public boolean isMultiField() {
		return true;
	}

	@Property("[单位能力] 掉落物品")
	public static class DropItemNode extends AbstractAbility
	{
		private static final long serialVersionUID = 1L;
		
		@Property("物品")
		public int		titem_id;
		@Property("百分比")
		public float	drop_rate_percent;
		
		public DropItemNode(){}
		
		public DropItemNode(int titem_id, float percent) 
		{
			this.titem_id = titem_id;
			this.drop_rate_percent = percent;
		}

		@Override
		public boolean isMultiField() {
			return true;
		}
	}
}
