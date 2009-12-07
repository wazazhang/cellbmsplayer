package com.g2d.studio.quest;

import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;

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
	
	
}
