package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.anno.PropertyAdapter;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DItemList;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.quest.QuestSelectCellEdit;
import com.g2d.studio.scene.editor.SceneListCellEditInteger;
import com.g2d.studio.scene.entity.SceneNode;

public class PropertyAdapters
{
	public static class UnitTypeAdapter implements CellEditAdapter<Object>
	{
		@Override
		public Class<Object> getType() {
			return Object.class;
		}
		
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue,
				Field field) {
			return false;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field) {
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			try{
				if (adapter != null) {
					switch (adapter.value()) {
					case UNIT_ID: 
						return new ObjectSelectCellEditInteger<XLSUnit>(XLSUnit.class, fieldValue);
					case ITEM_ID: 
						return new ObjectSelectCellEditInteger<XLSItem>(XLSItem.class, fieldValue);
					case ITEM_LIST_ID:
						return new ObjectSelectCellEditInteger<DItemList>(DItemList.class, fieldValue);
					case QUEST_ID:
						QuestSelectCellEdit dialog = new QuestSelectCellEdit(owner.getComponent(), false,
								(Integer)fieldValue);
						dialog.showDialog();
						return dialog;
					case SKILL_ID:
						return new ObjectSelectCellEditInteger<XLSSkill>(XLSSkill.class, fieldValue);
					case SCENE_ID:
						return new SceneListCellEditInteger();
					case AVATAR_ID:
						return new ObjectSelectCellEditInteger<DAvatar>(DAvatar.class, fieldValue);
					}
				}
			}catch(Exception err){
				err.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) 
		{
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			try {
				if (adapter != null) {
					switch (adapter.value()) {
					case UNIT_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSUnit.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case ITEM_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSItem.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case SKILL_ID: {
						XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(
								XLSSkill.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case ITEM_LIST_ID: {
						DItemList node = Studio.getInstance().getObjectManager().getObject(
								DItemList.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case QUEST_ID: {
						QuestNode node = Studio.getInstance().getQuestManager().getQuest((Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case SCENE_ID: {
						SceneNode node = Studio.getInstance().getSceneManager().getSceneNode((Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
					case AVATAR_ID: {
						DynamicNode<?> node = Studio.getInstance().getObjectManager().getObject(
								DAvatar.class, (Integer) fieldValue);
						if (node != null) {
							src.setText(node.getName());
							src.setIcon(node.getIcon(false));
						}
						break;
					}
						
					}
				}
			} catch (Exception err) {
				err.printStackTrace();
			}
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}
	
}
