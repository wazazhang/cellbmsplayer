package com.g2d.editor.property;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cell.reflect.Parser;
import com.g2d.editor.property.ObjectPropertyRowPanel.ColumnFiller;

@SuppressWarnings("serial")
public class ColumnFillerAdapters 
{
//	-----------------------------------------------------------------------------------------------------
	
	public static class ColumnFillerBoolean extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充Boolean";
		
		JCheckBox checkbox = new JCheckBox("value");
		
		public ColumnFillerBoolean() {
			this.add(checkbox);
		}
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (Boolean.class.isAssignableFrom(columnField.getType()) ||
				boolean.class.isAssignableFrom(columnField.getType())) {
				try {
					Object obj = columnField.get(row_data);
					checkbox.setSelected((Boolean)obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			for (int i = 0; i < rowDatas.size(); i++) {
				ret.add(checkbox.isSelected());
			}
			return ret;
		}
	}

//	-----------------------------------------------------------------------------------------------------
	

	public static class ColumnFillerString extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充字符串";
		
		JTextField text = new JTextField();
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (String.class.isAssignableFrom(columnField.getType())) {
				try {
					Object obj = columnField.get(row_data);
					text.setText(obj + "");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(ObjectPropertyRowPanel<?> panel, Field columnType, ArrayList<?> rowDatas) {
			ArrayList<Object> ret = new ArrayList<Object>(rowDatas.size());
			for (int i = 0; i < rowDatas.size(); i++) {
				ret.add(text.getText());
			}
			return ret;
		}
	}

//	-----------------------------------------------------------------------------------------------------
	
	public static class ColumnFillerNumber extends JPanel implements ColumnFiller
	{
		public static String TITLE = "填充数字";
		
		@Override
		public String getCommand(Object row_data, Field columnField) {
			if (Parser.isNumber(columnField.getType())) {
				return TITLE;
			}
			return null;
		}
		
		@Override
		public Component startFill(
				ObjectPropertyRowPanel<?> panel,
				Field columnType,
				ArrayList<?> rowDatas) 
		{
			
			return this;
		}
		
		@Override
		public ArrayList<Object> getValues(
				ObjectPropertyRowPanel<?> panel,
				Field columnType,
				ArrayList<?> rowDatas) 
		{

			return null;
		}

	}
	
	
}
