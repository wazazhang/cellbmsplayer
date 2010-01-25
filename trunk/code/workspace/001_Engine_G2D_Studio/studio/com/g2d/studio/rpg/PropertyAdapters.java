package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.studio.Studio;
import com.g2d.studio.anno.PropertyAdapter;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.template.XLSUnit;

public enum PropertyAdapters
{
	UNIT_ID,
	;
	
	
	
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
		public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field) {
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			if (adapter != null && adapter.value().equals(UNIT_ID)) {
				try{
					ObjectSelectCellEditInteger<XLSUnit> combo_unit = new ObjectSelectCellEditInteger<XLSUnit>(XLSUnit.class);
					combo_unit.setSelectedItem(fieldValue);
					return combo_unit;
				}catch(Exception err){
					err.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		public Component getCellRender(ObjectPropertyPanel owner,
				Object editObject, Object fieldValue, Field field,
				DefaultTableCellRenderer src) {
			PropertyAdapter adapter = field.getAnnotation(PropertyAdapter.class);
			if (adapter != null && adapter.value().equals(UNIT_ID)) {
				try{
					XLSUnit node = Studio.getInstance().getObjectManager().getObject(XLSUnit.class, (Integer)fieldValue);
					if (node != null) {
						src.setText(node.getName());
						src.setIcon(node.getIcon(false));
					}
				}catch(Exception err){
					err.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		public Object getCellValue(Object editObject,
				PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			return null;
		}
		
	}
	
}
