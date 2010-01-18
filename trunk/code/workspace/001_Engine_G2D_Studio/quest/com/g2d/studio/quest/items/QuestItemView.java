package com.g2d.studio.quest.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JPanel;
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
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.util.AbstractDialog;

public class QuestItemView extends JSplitPane implements TreeSelectionListener
{
	private QuestNode		quest;
	
	private QuestItemTree	tree;
	
	private JScrollPane		left;
	private JPanel			right;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestItemView(QuestNode quest)
	{
		super(HORIZONTAL_SPLIT);
		super.setMinimumSize(new Dimension(200, 200));
		this.quest	= quest;
		this.tree	= new QuestItemTree(quest);
		
		this.left	= new JScrollPane(tree);
		this.right	= new JPanel(new BorderLayout());
		left.setMinimumSize(new Dimension(200, 200));
		right.setMinimumSize(new Dimension(200, 200));
		
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
		tree.addTreeSelectionListener(this);
		
	}
	
//	void setQuest(Quest quest) 
//	{
//		tree.reload();
//		tree.expandAll();
//	}
	
	public void save() {
		tree.save();
	}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (e.getPath().getLastPathComponent() instanceof QuestItemNode) {
			QuestItemNode node = (QuestItemNode)e.getPath().getLastPathComponent();
			this.setRightComponent(node.getEditComponent());
		} else {
			this.setRightComponent(right);
		}
	}
	
}
