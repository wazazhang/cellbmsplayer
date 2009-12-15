package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JComboBox;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.MathMethod;
import com.cell.rpg.xls.XLSColumns;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;

public class MathMethodCellEdit extends JComboBox implements PropertyCellEdit<Method>
{
	public MathMethodCellEdit() {
		super(MathMethod.methods.values().toArray(new Method[MathMethod.methods.size()]));
	}
	public MathMethodCellEdit(String method_name) {
		this();
		Method mt = MathMethod.methods.get(method_name);
		if (mt!=null) {
			this.setSelectedItem(mt);
		}
	}
	
	@Override
	public Method getValue() {
		try{
			Method method = (Method)getSelectedItem();
			return method;
		} catch (Exception err){
			return null;
		}
	}
	
	@Override
	public Component getComponent(ObjectPropertyPanel panel) {
		return this;
	}
}
