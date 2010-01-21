package com.g2d.studio.item;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.g2d.studio.gameedit.ObjectViewer;

public class ItemPropertiesEditor extends ObjectViewer<ItemPropertiesNode> implements ActionListener
{	
	JToolBar			toolbar = new JToolBar();

	
//	-------------------------------------------------------------------------------------
	
	public ItemPropertiesEditor(ItemPropertiesNode node) {
		super(node, new ItemPropertiesAdapter.ValueRangeAdapter());
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
