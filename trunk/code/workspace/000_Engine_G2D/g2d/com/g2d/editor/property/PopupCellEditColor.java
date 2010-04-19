package com.g2d.editor.property;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * @author WAZA
 * 负责在JTable里更改颜色
 */
public class PopupCellEditColor extends PopupCellEdit<Color>
{
	JColorChooser 		colorChooser;
	
	public PopupCellEditColor() 
	{
		// Set up the dialog that the button brings up.
		colorChooser = new JColorChooser();
	}

	@Override
	public void onOpenEditor() 
	{
		// The user has clicked the cell, so
		// bring up the dialog.
		setBackground(current_value);
		colorChooser.setColor(current_value);
		
		JDialog dialog = JColorChooser.createDialog(
				sender.getComponent(), 
				"Pick a Color",
				true, // modal
				colorChooser, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						current_value = colorChooser.getColor();
						setBackground(current_value);
						sender.fireEditingStopped();
						completeEdit();
					}
				}, // OK button handler
				null); // no CANCEL button handler
		
		dialog.setVisible(true);
	}
	
	
}
