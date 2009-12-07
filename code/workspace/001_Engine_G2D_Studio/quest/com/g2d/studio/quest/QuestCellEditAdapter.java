package com.g2d.studio.quest;

import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.quest.QuestItem;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.rpg.RPGObjectPanel.RPGObjectAdapter;

public class QuestCellEditAdapter {

	public static class QuestAccepterAdapter extends AbilityCellEditAdapter<QuestAccepter>
	{
		Window owner;
		public QuestAccepterAdapter(Window owner) {
			this.owner = owner;
		}
		
		@Override
		public Class<QuestAccepter> getType() {
			return QuestAccepter.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(owner);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(Object editObject, Object fieldValue,
				Field field, DefaultTableCellRenderer src) {
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
		Window owner;
		public QuestPublisherAdapter(Window owner) {
			this.owner = owner;
		}
		
		@Override
		public Class<QuestPublisher> getType() {
			return QuestPublisher.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("quest_id")) {
				QuestSelectCellEdit edit = new QuestSelectCellEdit(owner);
				edit.showDialog();
				return edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(Object editObject, Object fieldValue,
				Field field, DefaultTableCellRenderer src) {
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
	

	/**
	 * 编辑任务标志
	 * @author WAZA
	 *
	 */
	public static class QuestItemAdapter extends RPGObjectAdapter<QuestItem>
	{
		@Override
		public Class<QuestItem> getType() {
			return QuestItem.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(Object editObject, Object fieldValue, Field field) {
			if (field.getName().equals("titem_index")) {
				ObjectSelectCellEditInteger<XLSItem> item_edit = new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class);
				return item_edit;
			}
			return null;
		}
		
		@Override
		public Component getCellRender(Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
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
}
