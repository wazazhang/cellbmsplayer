package com.g2d.studio.quest;

import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.quest.QuestItem;
import com.cell.rpg.quest.QuestItem.QuestItemAbility;
import com.cell.rpg.quest.QuestItem.TagItem;
import com.cell.rpg.quest.QuestItem.TagPlayerField;
import com.cell.rpg.quest.QuestItem.TagQuestItem;
import com.cell.rpg.quest.QuestItem.TagNPCField;
import com.cell.rpg.quest.QuestItem.TagUnitField;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.xls.XLSColumns;
import com.g2d.annotation.Property;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.XLSColumnSelectCellEdit;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.quest.items.QuestItemNode;
import com.g2d.studio.quest.items.QuestItemSelectCellEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.rpg.RPGObjectPanel.RPGObjectAdapter;
import com.g2d.util.AbstractDialog;

public class QuestCellEditAdapter {

	public static class QuestAccepterAdapter extends AbilityCellEditAdapter<QuestAccepter>
	{
		@Override
		public Class<QuestAccepter> getType() {
			return QuestAccepter.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(AbstractDialog.getTopWindow(owner));
				edit.showDialog();
				return edit;
			}
			return null;
		}
	
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id") && fieldValue!=null) {
				Integer quest_id = (Integer)fieldValue;
				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
				if (node != null) {
					src.setText(node.toString());
					src.setIcon(node.getIcon(false));
					return src;
				}
			}
			return null;
		}
	}

	public static class QuestPublisherAdapter extends AbilityCellEditAdapter<QuestPublisher>
	{
		@Override
		public Class<QuestPublisher> getType() {
			return QuestPublisher.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner, Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(AbstractDialog.getTopWindow(owner));
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_id") && fieldValue!=null) {
				Integer quest_id = (Integer)fieldValue;
				QuestNode node = Studio.getInstance().getQuestManager().getQuest(quest_id);
				if (node != null) {
					src.setText(node.toString());
					src.setIcon(node.getIcon(false));
					return src;
				}
			}
			return null;
		}
	}
	

	
//	-------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 任务条件，道具
	 * @author WAZA
	 */
	public static class QuestItemTagItem extends AbilityCellEditAdapter<QuestItemAbility>
	{
		@Override
		public Class<QuestItemAbility> getType() {
			return QuestItemAbility.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class);
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("titem_index")) {
				try{
					XLSItem item = Studio.getInstance().getObjectManager().getObject(XLSItem.class, (Integer)fieldValue);
					src.setText(item.getName());
					src.setIcon(item.getIcon(false));
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务条件，依赖的任务条件
	 * @author WAZA
	 */
	public static class QuestItemTagQuestItem extends AbilityCellEditAdapter<TagQuestItem>
	{	
		@Override
		public Class<TagQuestItem> getType() {
			return TagQuestItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_item_index")) {
				QuestItemSelectCellEdit quest_item_edit = new QuestItemSelectCellEdit(AbstractDialog.getTopWindow(owner));
				quest_item_edit.showDialog();
				return quest_item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("quest_item_index")) {
				try{
					QuestItemNode node = Studio.getInstance().getQuestManager().getQuestItems().get((Integer)fieldValue);
					src.setText(node.getName());
				}catch(Exception err){}
			}
			return null;
		}
	}
	
	/**
	 * 任务条件，依赖的角色字段
	 * @author WAZA
	 */
	public static class QuestItemTagUnitField extends AbilityCellEditAdapter<TagUnitField>
	{
		XLSColumns player_columns = Studio.getInstance().getObjectManager().getPlayerXLSColumns();
		XLSColumns unit_columns = Studio.getInstance().getObjectManager().getUnitXLSColumns();
		@Override
		public Class<TagUnitField> getType() {
			return TagUnitField.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
			Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("unit_filed_name")) {
				if (editObject instanceof TagPlayerField) {
					XLSColumnSelectCellEdit edit = new XLSColumnSelectCellEdit(player_columns);
					return edit;
				}
				else if (editObject instanceof TagNPCField) {
					XLSColumnSelectCellEdit edit = new XLSColumnSelectCellEdit(unit_columns);
					return edit;
				}
			}
			return null;
		}

		@Override
		public Component getCellRender(ObjectPropertyPanel owner, Object editObject,
			Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (field.getName().equals("unit_filed_name")) {
				if (fieldValue!=null) {
					if (editObject instanceof TagPlayerField) {
						String desc = player_columns.getDesc(fieldValue.toString());
						src.setText(desc + " (" + fieldValue + ")");
					}
					else if (editObject instanceof TagNPCField) {
						String desc = unit_columns.getDesc(fieldValue.toString());
						src.setText(desc + " (" + fieldValue + ")");
					}
				}
			}
			return null;
		}

	}
}
