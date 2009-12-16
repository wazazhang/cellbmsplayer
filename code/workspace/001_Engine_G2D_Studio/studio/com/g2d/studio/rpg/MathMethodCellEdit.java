package com.g2d.studio.rpg;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.cell.rpg.ability.Abilities;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.MathMethod;
import com.cell.rpg.xls.XLSColumns;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.editor.property.ObjectPropertyPanel.CellEditAdapter;

public class MathMethodCellEdit extends JComboBox implements PropertyCellEdit<Method>
{
	public MathMethodCellEdit(Collection<Method> methods) {
		super(methods.toArray(new Method[methods.size()]));
		setRenderer(new CellRender());
	}
	
	public MathMethodCellEdit(Collection<Method> methods, String method_name) {
		this(methods);
		Method mt = MathMethod.getMethods().get(method_name);
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
	
	static class CellRender extends DefaultListCellRenderer
	{
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Method method = (Method)value;
			String text = method.getReturnType().getSimpleName() + " " + method.getName() + " ( ";
			Class<?>[] params = method.getParameterTypes();
			for (int i=0; i<params.length; i++) {
				text += params[i].getSimpleName();
				if (i!=params.length-1) {
					text += " , ";
				}
			}
			text += " )";
			super.setText(text);
			return this;
		}
	}
	
	
	
	
}
