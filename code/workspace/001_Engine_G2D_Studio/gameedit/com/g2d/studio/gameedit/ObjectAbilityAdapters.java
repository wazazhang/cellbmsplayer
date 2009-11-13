package com.g2d.studio.gameedit;

import java.lang.reflect.Field;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;

public class ObjectAbilityAdapters 
{

	public static class UnitBattleTeamNodeAdapter extends AbilityCellEditAdapter<UnitBattleTeam.TeamNode>
	{
		@Override
		public Class<UnitBattleTeam.TeamNode> getAbilityType() {
			return UnitBattleTeam.TeamNode.class;
		}
		
		@Override
		public PropertyCellEdit<?> getAbilityCellEdit(Abilities abilities,
				AbstractAbility ability, Field field, Object value) {
			if (field.getName().equals("template_unit_id")){
				return new ObjectSelectCellEdit<XLSUnit>(XLSUnit.class);
			}
			return null;
		}
	}

}
