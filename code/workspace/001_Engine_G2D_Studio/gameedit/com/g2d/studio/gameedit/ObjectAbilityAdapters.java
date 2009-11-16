package com.g2d.studio.gameedit;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.cell.rpg.template.ability.UnitBattleTeam.TeamNode;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;

public class ObjectAbilityAdapters 
{

	public static class UnitBattleTeamNodeAdapter extends AbilityCellEditAdapter<UnitBattleTeam.TeamNode>
	{
		@Override
		public Class<UnitBattleTeam.TeamNode> getType() {
			return UnitBattleTeam.TeamNode.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				Object editObject,
				Object fieldValue, 
				Field field) {
			if (field.getName().equals("template_unit_id")){
				return new ObjectSelectCellEdit<XLSUnit>(XLSUnit.class);
			}
			return null;
		}
		
		@Override
		public Component getCellRender(
				Object editObject,
				Object fieldValue,
				Field field,
				DefaultTableCellRenderer src) {
			if (field.getName().equals("template_unit_id")){
				XLSUnit unit = null;
				if (fieldValue != null) {
					String tid = (String)fieldValue;
					unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, tid);
				}
				if (fieldValue != null && unit != null) {
					src.setText(unit.getName()+"(" + unit.getID() + ")");
				} else {
//					src.setForeground(Color.RED);
					src.setText("null");
				}
			}
			return src;
		}
	}

}
