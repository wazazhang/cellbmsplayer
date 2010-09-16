package com.g2d.studio.instancezone;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import com.g2d.studio.gameedit.ObjectViewer;

@SuppressWarnings("serial")
public class InstanceZoneEditor  extends ObjectViewer<InstanceZoneNode> implements ActionListener
{	
	JToolBar			toolbar = new JToolBar();
	
	JSplitPane			page_data;
	
//	-------------------------------------------------------------------------------------
	
	public InstanceZoneEditor(InstanceZoneNode node) 
	{
		super(node);

		this.add(toolbar, BorderLayout.NORTH);
		
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{

	}
	
//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
}