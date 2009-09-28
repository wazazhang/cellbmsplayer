package com.g2d.studio.actor;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import com.g2d.studio.ATreeNodeSet;
import com.g2d.studio.Studio;

public class FormActorsPan extends JFrame
{
	static FormActorsPan instance;
	
	public static void showFormActorsPan(FormActorViewerGroup group){
		if (instance==null) {
			instance = new FormActorsPan(group);
		}
		instance.setVisible(true);
	}
	
	public FormActorsPan(FormActorViewerGroup group)
	{
		this.setLocation(group.studio.getX()+group.studio.getWidth(), group.studio.getY());
		this.setSize(32*5+8, 600);
		this.setAlwaysOnTop(true);
		this.add(new ResActorBox(group));
	}

	
}
