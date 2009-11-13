package com.g2d.studio.rpg;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.cell.rpg.RPGObject;
import com.g2d.editor.property.ObjectPropertyPanel;

public class RPGObjectPanel  extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final RPGObject 			current_unit;
	
	final ObjectPropertyPanel	unit_property;
	
	public RPGObjectPanel(RPGObject unit)
	{
		this.setLayout(new BorderLayout());
		this.current_unit = unit;
		this.unit_property = new ObjectPropertyPanel(unit);
		this.add(unit_property, BorderLayout.CENTER);
	}
	
	@Override
	public String toString(){
		return "RPG Unit";
	}
	
	
	
}
