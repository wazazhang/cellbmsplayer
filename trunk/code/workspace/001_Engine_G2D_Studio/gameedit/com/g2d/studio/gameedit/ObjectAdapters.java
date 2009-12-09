package com.g2d.studio.gameedit;

import java.awt.Color;
import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.QuestItem;
import com.cell.rpg.scene.ability.RegionSpawnNPC.NPCSpawn;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.cell.rpg.template.ability.UnitDropItem;
import com.cell.rpg.template.ability.UnitBattleTeam.TeamNode;
import com.cell.rpg.template.ability.UnitDropItem.DropItemNode;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.rpg.RPGObjectPanel.RPGObjectAdapter;

public class ObjectAdapters 
{
	
	/**
	 * 编辑战斗队伍的工具
	 * @author WAZA
	 */
	public static class UnitBattleTeamNodeAdapter extends AbilityCellEditAdapter<UnitBattleTeam.TeamNode>
	{
		@Override
		public Class<UnitBattleTeam.TeamNode> getType() {
			return UnitBattleTeam.TeamNode.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("template_unit_id")){
				return new ObjectSelectCellEdit<XLSUnit>(XLSUnit.class);
			}
			return null;
		}
		
		@Override
		public Component getCellRender(
				ObjectPropertyPanel owner,
				Object editObject,
				Object fieldValue,
				Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("template_unit_id")){
				XLSUnit unit = null;
				if (fieldValue != null) {
					String tid = (String)fieldValue;
					unit = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, tid);
				}
				if (fieldValue != null && unit != null) {
					src.setText(unit.getName());
				} else {
//					src.setForeground(Color.RED);
					src.setText("null");
				}
			}
			return src;
		}
	}
	
	/**
	 * 编辑掉落道具的工具
	 * @author WAZA
	 */
	public static class UnitDropItemNodeAdapter extends AbilityCellEditAdapter<UnitDropItem>
	{
		@Override
		public Class<UnitDropItem> getType() {
			return UnitDropItem.class;
		}
		
//		@Override
//		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
//			if (field.getName().equals("drop_types")) {
//				
//			}
//			return null;
//		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("drop_types")){
				DropItemEditor editor = new DropItemEditor((DropItemNode[])fieldValue);
				editor.setVisible(true);
				return editor;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("drop_types")){
				if (fieldValue!=null) {
					DropItemEditor editor = new DropItemEditor((DropItemNode[])fieldValue);
					return editor.getComponent(null);
				}
			}
			return null;
		}
	}

	
}
