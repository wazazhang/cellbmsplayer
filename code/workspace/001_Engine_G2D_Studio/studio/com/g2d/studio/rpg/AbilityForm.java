package com.g2d.studio.rpg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;

import com.cell.rpg.ability.Abilities;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的窗口
 */
public class AbilityForm extends AbstractDialog implements PropertyCellEdit<Abilities>
{
	private static final long serialVersionUID = 1L;
	
	ObjectPropertyPanel property_panel;
	JButton				button;
	
	final AbilityPanel	ability_panel;
	final Abilities		abilities;
	
	public AbilityForm(Abilities abilities, Collection<AbilityCellEditAdapter<?>> adapters) 
	{
		this.abilities 		= abilities;
		this.ability_panel 	= new AbilityPanel(
				abilities, 
				adapters.toArray(new AbilityCellEditAdapter<?>[adapters.size()]));
		this.add(ability_panel);
		this.setTitle(abilities.toString());
		
		this.setSize(700, 400);
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
