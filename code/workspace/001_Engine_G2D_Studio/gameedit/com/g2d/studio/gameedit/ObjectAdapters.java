package com.g2d.studio.gameedit;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.template.TItem;
import com.cell.rpg.template.ability.ItemListID;
import com.cell.rpg.template.ability.UnitBattleTeam;
import com.cell.rpg.template.ability.UnitDropItem;
import com.cell.rpg.template.ability.UnitDropItem.DropItemNode;
import com.cell.rpg.template.ability.UnitDropItem.DropItems;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.item.ItemPropertiesNode;
import com.g2d.studio.item.ItemPropertiesSelectCellEdit;
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
		
		@Override
		public PropertyCellEdit<?> getCellEdit(
				ObjectPropertyPanel owner,
				Object editObject, 
				Object fieldValue, Field field) {
			if (field.getName().equals("item_types")){
				DropItemEditor editor = new DropItemEditor(owner, (DropItems)fieldValue);
				editor.setVisible(true);
				return editor;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("item_types")){
				if (fieldValue!=null) {
					DropItemEditor editor = new DropItemEditor(owner, (DropItems)fieldValue);
					return editor.getComponent(null);
				}
			}
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("item_types")){
				DropItems types = (DropItems)fieldSrcValue;
				if (types != null) {
					StringBuffer sb = new StringBuffer();
					for (DropItemNode n : types) {
						XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, n.titem_id);
						if (item != null) {
							sb.append("[" + item.getData().getName() + "(" + n.drop_rate_percent + "%)" + "]");
						} else {
							sb.append("[" + n.titem_id               + "(" + n.drop_rate_percent + "%)" + "]");
						}
						sb.append("  ·  ");
					}
					return sb.toString();
				}
			}
			return null;
		}
	}
	
	public static class ItemListIDSelectAdapter extends AbilityCellEditAdapter<ItemListID>
	{
		@Override
		public Class<ItemListID> getType() {
			return ItemListID.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("item_list_id")) {
				ObjectSelectCellEditInteger<DItemList> item_list = 
					new ObjectSelectCellEditInteger<DItemList>(DItemList.class);
				item_list.setSelectedItem(fieldValue);
				return item_list;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("item_list_id")) {
				DItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DItemList.class,
						(Integer)fieldValue);
				if (src != null) {
					src.setText(item_list.getData().name);
				}
				return src;
			}
			return null;
		}
		
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			if (field.getName().equals("item_list_id")) {
				DItemList item_list = Studio.getInstance().getObjectManager().getObject(
						DItemList.class,
						(Integer)fieldSrcValue);
				return item_list.getData().name;
			}
			return null;
		}
	}
	

	public static class ItemPropertiesSelectAdapter extends RPGObjectAdapter<TItem>
	{
		@Override
		public Class<TItem> getType() {
			return TItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field) {
			if (editObject instanceof TItem) {
				if (field.getName().equals("item_properties_id")) {
					ItemPropertiesSelectCellEdit edit = new ItemPropertiesSelectCellEdit(owner);
					edit.setValue((Integer)fieldValue);
					edit.showDialog();
					return edit;
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (editObject instanceof TItem) {
				if (field.getName().equals("item_properties_id") && fieldValue!=null) {
					ItemPropertiesNode node = Studio.getInstance().getItemManager().getNode((Integer)fieldValue);
					if (node != null) {
						src.setText(node.toString());
						src.setIcon(node.getIcon(false));
					}
					return src;
				}
			}
			return null;
		}
		
	}
	

	
	
}
