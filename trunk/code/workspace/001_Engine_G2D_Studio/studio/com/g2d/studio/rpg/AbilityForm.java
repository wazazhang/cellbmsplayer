package com.g2d.studio.rpg;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.cell.rpg.ability.Abilities;
import com.g2d.editor.property.CellEditAdapter;
import com.g2d.editor.property.ObjectPropertyEdit;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractDialog;


/**
 * @author WAZA
 * 可编辑多个能力的窗口
 */
public class AbilityForm extends AbstractDialog implements PropertyCellEdit<Abilities>
{
	private static final long serialVersionUID = 1L;
	
	ObjectPropertyEdit	property_panel;
	JButton				button;
	
	final AbilityPanel	ability_panel;
	final Abilities		abilities;
	
	public AbilityForm(ObjectPropertyEdit owner, Abilities abilities, CellEditAdapter<?> ... adapters) 
	{
		super(owner.getComponent());
		this.abilities 		= abilities;
		this.ability_panel = new AbilityPanel(abilities, adapters);
		this.add(ability_panel);
		this.setTitle(abilities.toString());
		this.setIconImage(Res.icon_edit);
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
	
	public Component getComponent(ObjectPropertyEdit panel) {	
		property_panel = panel;
		button.setText(abilities.toString());
		return button;
	}
	
	public Abilities getValue() {
		return ability_panel.abilities;
	}
	
	
	
	
}
