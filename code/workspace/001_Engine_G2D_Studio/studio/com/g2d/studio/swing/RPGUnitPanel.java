package com.g2d.studio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cell.rpg.ability.AbilitySceneTransport;
import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.entity.Unit;
import com.g2d.cell.game.Scene;
import com.g2d.cell.game.SceneUnit;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.editor.property.ObjectPropertyPanel;
import com.g2d.editor.property.PropertyCellEdit;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.scene.FormSceneViewer;
import com.g2d.studio.swing.AbilityPanel.AbilityPropertyPanel;
import com.g2d.studio.swing.AbilityPanel.AddAbilityForm;
import com.g2d.studio.swing.AbilityPanel.AbilityPropertyForm;
import com.g2d.util.AbstractDialog;

public class RPGUnitPanel  extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	final Unit current_unit;
	
	final ObjectPropertyPanel unit_property;
	
	public RPGUnitPanel(Unit unit)
	{
		this.setLayout(new BorderLayout());
		
		current_unit = unit;
	
		{
			this.unit_property = new ObjectPropertyPanel(unit);
			this.add(unit_property, BorderLayout.CENTER);
		}
	}
	
	@Override
	public String toString(){
		return "RPG Unit";
	}
	
	
	
}
