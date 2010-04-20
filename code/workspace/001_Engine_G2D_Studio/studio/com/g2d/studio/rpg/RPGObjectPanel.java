package com.g2d.studio.rpg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.RPGObject;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.template.TemplateNode;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.icon.IconSelectCellEdit;
import com.g2d.studio.scene.editor.SceneAbilityAdapters;
import com.g2d.util.AbstractDialog;

public class RPGObjectPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final RPGObject 			current_unit;
	
	final ObjectPropertyPanel	unit_property;

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public RPGObjectPanel(RPGObject unit, CellEditAdapter<?> ... adapters)
	{
		CellEditAdapter<?>[] dadapters;
		if (adapters!=null && adapters.length>0) {
			dadapters = new CellEditAdapter<?>[adapters.length+3];
			System.arraycopy(adapters, 0, dadapters, 0, adapters.length);
			dadapters[dadapters.length-1] = new IconSelectAdapter();
			dadapters[dadapters.length-2] = new AbilityAdapter();
			dadapters[dadapters.length-3] = new SceneAbilityAdapters.NPCTalkAdapter();
		} else {
			dadapters = new CellEditAdapter<?>[]{
				new IconSelectAdapter(),
				new AbilityAdapter(),
				new SceneAbilityAdapters.NPCTalkAdapter(),
			};
		}
		this.setLayout(new BorderLayout());
		this.current_unit = unit;
		this.unit_property = new ObjectPropertyPanel(unit, dadapters);
		this.add(unit_property, BorderLayout.CENTER);
	}

	@Override
	public String toString() {
		return "RPG Unit";
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	abstract public static class RPGObjectAdapter<T extends RPGObject> implements CellEditAdapter<T>
	{
		@Override
		public boolean fieldChanged(Object editObject, Object fieldValue, Field field) {
			return false;
		}

		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field) {
			return null;
		}

		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject, Object fieldValue, Field field, DefaultTableCellRenderer src) {
			return null;
		}

		@Override
		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		@Override
		public String getCellText(Object editObject, Field field, Object fieldSrcValue) {
			return null;
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public class IconSelectAdapter extends RPGObjectAdapter<RPGObject>
	{
		IconFile icon;
		
		@Override
		public Class<RPGObject> getType() {
			return RPGObject.class;
		}
		
		@Override
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyEdit owner,
				Object editObject, Object fieldValue, Field field) {
			if (editObject instanceof TemplateNode ||
				editObject instanceof Quest) {
//				System.out.println("IconSelectAdapter getCellEdit");
				if (field.getName().equals("icon_index")) {
					IconSelectCellEdit edit = new IconSelectCellEdit(
							AbstractDialog.getTopWindow(RPGObjectPanel.this));
					edit.showDialog();
					return edit;
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyEdit owner, Object editObject,
				Object fieldValue, Field field, DefaultTableCellRenderer src) {
			if (editObject instanceof TemplateNode ||
				editObject instanceof Quest) {
//				System.out.println("IconSelectAdapter getCellRender");
				if (field.getName().equals("icon_index") && fieldValue!=null) {
					String icon_name = fieldValue.toString();
					if (this.icon == null ||
						this.icon.icon_file_name.equals(icon_name) == false) {
						this.icon = Studio.getInstance().getIconManager().getIcon(icon_name);
					} 
					if (icon!=null) {
						src.setText(icon_name);
						src.setIcon(icon.getListIcon(false));
					}
					return src;
				}
			}
			return null;
		}
		
	}
	

//	-----------------------------------------------------------------------------------------------------------------------------
	
//	public static class UnitNodeSelectAdapter extends RPGObjectAdapter<TUnit>
//	{
//		CPJIndex<CPJObject<?>>
//		
//		@Override
//		public Class<TUnit> getType() {
//			return TUnit.class;
//		}
//		
//		@Override
//		public PropertyCellEdit<?> getCellEdit(Object editObject,
//				Object fieldValue, Field field) {
//			if (editObject instanceof TUnit) {
//				if (field.getName().equals("display_node")) {
//					CPJResourceSelectCellEdit<UnitNode> edit = new CPJResourceSelectCellEdit<UnitNode>(UnitNode.class);
//				}
//			}
//			return null;
//		}
//		
//		@Override
//		public Component getCellRender(Object editObject, Object fieldValue,
//				Field field, DefaultTableCellRenderer src) {
//			if (editObject instanceof TUnit) {
//				
//			}
//			return null;
//		}
//		
//	}
	
}
