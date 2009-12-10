package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;

import javax.swing.table.DefaultTableCellRenderer;

import com.cell.rpg.RPGObject;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbstractAbility;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;
import com.g2d.util.AbstractDialog;

public class AbilityAdapter implements CellEditAdapter<Abilities>
{
	@Override
	public Class<Abilities> getType() {
		return Abilities.class;
	}
	
	@Override
	public PropertyCellEdit<?> getCellEdit(ObjectPropertyPanel owner,
			Object editObject, Object fieldValue, Field field) {
		// 测试是否是集合
		try {
			field.getType().asSubclass(Abilities.class);
			System.out.println("field is Abilities");
			if (fieldValue == null) {
				fieldValue = field.getType().newInstance();
			}
			return new AbilityForm(
					owner,
					(Abilities) fieldValue,
					owner.getAdapters());
		} catch (Exception e) {}
		return null;
	}
	
	@Override
	public Component getCellRender(ObjectPropertyPanel owner,
			Object editObject, Object fieldValue, Field field,
			DefaultTableCellRenderer src) {
		return null;
	}
	
	@Override
	public Object getCellValue(Object editObject,
			PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
		return null;
	}

	@Override
	public boolean fieldChanged(Object editObject, Object fieldValue,
			Field field) {
		return false;
	}
	
}
