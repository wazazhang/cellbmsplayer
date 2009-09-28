package com.g2d.studio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;


import com.cell.gui.Button;
import com.cell.rpg.ability.Abilities;
import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.ability.AbilityXLS;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.xls.XLSFile;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.scene.FormSceneViewer;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的窗口
 */
public class AbilityForm extends AbstractDialog implements PropertyCellEdit<Abilities>
{
	private static final long serialVersionUID = 1L;
	
	ObjectPropertyPanel property_panel;
	JButton button;
	
	final AbilityPanel ability_panel;
	final Abilities abilities;
	
	public AbilityForm(Abilities abilities) 
	{
		this.abilities 		= abilities;
		this.ability_panel 	= new AbilityPanel(abilities);
		this.add(ability_panel);
		this.setTitle(abilities.toString());
		
		this.setSize(500, 400);
		this.setCenter();
		
		button = new JButton(abilities.toString());
		button.setActionCommand("ok");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityForm.this.setVisible(true);
			}
		});
	}
	
	public void beginEdit(ObjectPropertyPanel panel) {
		property_panel = panel;
	}
	
	public Component getComponent() {	
		button.setText(abilities.toString());
		return button;
	}
	
	public Abilities getValue() {
		return ability_panel.abilities;
	}
	
	
	
	
}
