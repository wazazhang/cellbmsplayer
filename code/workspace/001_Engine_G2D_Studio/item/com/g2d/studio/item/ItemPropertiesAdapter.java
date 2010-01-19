package com.g2d.studio.item;

import java.awt.Component;
import java.awt.FlowLayout;
import java.lang.reflect.Field;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;

import com.cell.CUtil;
import com.cell.rpg.item.ItemPropertyTemplate;
import com.cell.rpg.item.ItemPropertyTemplate.ArgTemplate;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.TextCellEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;

public class ItemPropertiesAdapter
{
	public static class ValueRangeAdapter extends AbilityCellEditAdapter<ItemPropertyTemplate>
	{
		@Override
		public Class<ItemPropertyTemplate> getType() {
			return ItemPropertyTemplate.class;
		}
		
		@Override
		public Object getCellValue(Object editObject, PropertyCellEdit<?> fieldEdit, Field field, Object fieldSrcValue) {
			if (ArgTemplate.class.isAssignableFrom(field.getType())) {
				if (fieldEdit instanceof TextCellEdit) {
					TextCellEdit edit = ((TextCellEdit)fieldEdit);
					try{
						String[] lr = CUtil.splitString(edit.getValue(), "-");
						ArgTemplate range = fieldSrcValue==null ? (ArgTemplate)fieldSrcValue : new ArgTemplate(
								Double.parseDouble(lr[0].trim()), 
								Double.parseDouble(lr[1].trim()));
						return range;
					}catch(Exception err){
						JOptionPane.showMessageDialog(edit, 
								"格式错误！\n"
								+ err.getClass().getName() + "\n"
								+ err.getMessage(), 
								"格式错误！",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			return super.getCellValue(editObject, fieldEdit, field, fieldSrcValue);
		}
	}

}
