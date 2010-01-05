package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;


import com.cell.io.CFile;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.cell.rpg.quest.script.QuestScript;
import com.g2d.Tools;
import com.g2d.studio.Studio;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.quest.events.QuestEventView;
import com.g2d.studio.quest.items.QuestItemView;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.util.AbstractFrame;
import com.g2d.util.TextEditor;

public class ItemPropertiesEditor extends ObjectViewer<ItemPropertiesNode> implements ActionListener
{	
	JToolBar			toolbar = new JToolBar();

	
//	-------------------------------------------------------------------------------------
	
	public ItemPropertiesEditor(ItemPropertiesNode node) {
		super(node);
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	@Override
	protected void appendPages(JTabbedPane table) {
		table.removeAll();
		table.addTab("道具属性", 	page_abilities);
		table.addTab("附加属性", page_object_panel);
		table.setSelectedComponent(page_abilities);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}
	
//	-------------------------------------------------------------------------------------
	
}
