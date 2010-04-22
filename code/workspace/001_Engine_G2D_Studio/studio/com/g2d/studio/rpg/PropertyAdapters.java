package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.anno.PropertyAdapter;
import com.cell.rpg.anno.PropertyType;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectSelectCellEditInteger;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;

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
					Class<? extends XLSTemplateNode<?>> type = null;
					switch (adapter.value()) {
					case UNIT_ID: type = XLSUnit.class; break;
					case ITEM_ID: type = XLSItem.class; break;
					}
					XLSTemplateNode<?> node = Studio.getInstance().getObjectManager().getObject(type, (Integer) fieldValue);
					if (node != null) {
						src.setText(node.getName());
						src.setIcon(node.getIcon(false));
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
