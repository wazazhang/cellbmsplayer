package com.g2d.studio.gameedit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.Tools;
import com.g2d.studio.Config;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.gameedit.template.TItem;
import com.g2d.studio.gameedit.template.TNpc;
import com.g2d.studio.gameedit.template.TemplateTreeNode;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ObjectManager extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	G2DWindowToolBar toolbar = new G2DWindowToolBar(this);
	
	final ObjectTreeView<TNpc> 	tree_units_view;
	final ObjectTreeView<TItem> tree_items_view;
	
	public ObjectManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("物体编辑器");
		super.setIconImage(Res.icon_edit);

		this.add(toolbar, BorderLayout.NORTH);
		
		
		File xls_path = Studio.getInstance().getFile(Config.XLS_ROOT);

		JTabbedPane table = new JTabbedPane();
		// TNPC
		{
			tree_units_view = new ObjectTreeView<TNpc>("NPC模板", TNpc.class, xls_path);
			table.addTab("NPC", Tools.createIcon(Res.icon_res_2), tree_units_view);
		}
		// TItem
		{
			tree_items_view = new ObjectTreeView<TItem>("道具模板", TItem.class, xls_path);
			table.addTab("物品", Tools.createIcon(Res.icon_res_4), tree_items_view);
		}
		
		
		this.add(table, BorderLayout.CENTER);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.save) {
			saveAll();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void saveAll()
	{
		Enumeration<TNpc> npcs = tree_units_view.tree_root.children();
		while (npcs.hasMoreElements()) {
			TNpc npc = npcs.nextElement();
			npc.save();
		}
		Enumeration<TItem> items = tree_items_view.tree_root.children();
		while (items.hasMoreElements()) {
			TItem item = items.nextElement();
			item.save();
		}
		System.out.println(getClass().getSimpleName() + " : save all");
	}
	

	
	
}
