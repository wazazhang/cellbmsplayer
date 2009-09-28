package com.g2d.editor.property;

import java.awt.Component;

import javax.swing.JTextField;

public class TextCellEdit extends JTextField implements PropertyCellEdit<String>
{
	private static final long serialVersionUID = 1L;
	
	public void beginEdit(ObjectPropertyPanel panel) {}

	public String getValue() {
		return super.getText();
	}
	
	public Component getComponent() {
		return this;
	}

}
