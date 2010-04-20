package com.g2d.editor.property;

import java.awt.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.cell.reflect.Parser;
import com.g2d.editor.property.ObjectPropertyRowPanel.ColumnFiller;

@SuppressWarnings("serial")
public class ColumnFillerNumber extends JPanel implements ColumnFiller
{
	@Override
	public boolean validateFill(Field columnField) {
		if (Parser.isNumber(columnField.getType())) {
			return true;
		}
		return false;
	}
	
	@Override
	public Component startFill(
			ObjectPropertyRowPanel<?> panel,
			Field columnType,
			int startRow,
			ArrayList<?> rowDatas,
			ArrayList<Object> 
			rowColumnDatas) 
	{
		
		return this;
	}
	

}
