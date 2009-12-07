package com.g2d.studio.quest.items;

import java.awt.Event;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.CIO;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.quest.Quest;
import com.cell.rpg.quest.QuestCondition;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.util.AbstractDialog;

public class QuestItemView extends JSplitPane implements TreeSelectionListener
{
	private Quest 			quest;
	
	private QuestItemTree	tree;
	
	private JScrollPane		left;
	private JScrollPane		right;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestItemView(Quest quest)
	{
		super(HORIZONTAL_SPLIT);
		
		this.quest	= quest;
		this.tree	= new QuestItemTree();
		
		this.left	= new JScrollPane(tree);
		this.right	= new JScrollPane();
		
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
		tree.addTreeSelectionListener(this);
		
		setQuest(quest);
	}
	
	void setQuest(Quest quest) 
	{
		tree.setQuest(quest);
	}
	
	public void save() {
		tree.save();
	}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getLastPathComponent() instanceof QuestItemNode) {
			QuestItemNode node = (QuestItemNode)e.getPath().getLastPathComponent();
			right.setViewportView(node.getEditComponent());
		}
	}
	
}
