package com.g2d.studio.quest.items;

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
import javax.swing.JTree;
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

public class QuestItemTree extends G2DTree
{
	private Quest quest ;

	final ConditionGroup group_trigger_condition;
	final ConditionGroup group_trigger_award;
	final ConditionGroup group_complete_condition;
	final ConditionGroup group_complete_award;
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	public QuestItemTree()
	{
		super(new DefaultMutableTreeNode("任务条件"));
		super.setDragEnabled(true);
		group_trigger_condition		= new ConditionGroup("接受条件");
		group_trigger_award			= new ConditionGroup("接受奖励");
		group_complete_condition	= new ConditionGroup("完成条件");
		group_complete_award		= new ConditionGroup("完成奖励");
		getRoot().add(group_trigger_condition);
		getRoot().add(group_trigger_award);
		getRoot().add(group_complete_condition);
		getRoot().add(group_complete_award);
	}
	
	void setQuest(Quest quest)
	{
		this.quest = quest;
		group_trigger_condition.removeAllChildren();		
		group_trigger_award.removeAllChildren();
		group_complete_condition.removeAllChildren();
		group_complete_award.removeAllChildren();
		
		group_trigger_condition.loadList(quest.accept_condition);
		group_trigger_award.loadList(quest.accept_award);
		group_complete_condition.loadList(quest.complete_condition);
		group_complete_award.loadList(quest.complete_award);
		
		reload();
	}
	
	public void save() {
		if (this.quest != null) {
			group_trigger_condition.saveList(quest.accept_condition);
			group_trigger_award.saveList(quest.accept_award);
			group_complete_condition.saveList(quest.complete_condition);
			group_complete_award.saveList(quest.complete_award);
		}
	}
	
	@Override
	protected boolean checkDrag(DropTarget evtSource, Object src, Object dst, int position) {
		MutableTreeNode src_node = (MutableTreeNode)src;
		MutableTreeNode dst_node = (MutableTreeNode)dst;
		if (dst_node == getRoot()) {
			return false;
		}
		if (src_node.getParent() == getRoot()) {
			return false;
		}
		if (dst_node.getParent() == getRoot() && position != 0) {
			return false;
		}
		// 任务奖励不能作为条件
		if (G2DTree.containsNode(group_trigger_award, src_node) ||
			G2DTree.containsNode(group_complete_award, src_node)) {
			if (dst == group_trigger_condition || G2DTree.containsNode(group_trigger_condition, dst_node)) {
				return false;
			}
			if (dst == group_complete_condition || G2DTree.containsNode(group_complete_condition, dst_node)) {
				return false;
			}
		}
		// 任务条件不能作为奖励
		if (G2DTree.containsNode(group_trigger_condition, src_node) ||
			G2DTree.containsNode(group_complete_condition, src_node)) {
			if (dst == group_trigger_award || G2DTree.containsNode(group_trigger_award, dst_node)) {
				return false;
			}
			if (dst == group_complete_award || G2DTree.containsNode(group_complete_award, dst_node)) {
				return false;
			}
		}
		return super.checkDrag(evtSource, src, dst, position);
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
	class ConditionGroup extends G2DTreeNodeGroup<QuestItemNode>
	{
		public ConditionGroup(String title) {
			super(title);
		}
		
		@Override
		protected boolean pathAddLeafNode(String name, int index, int length) {
			QuestItemManager items = Studio.getInstance().getQuestManager().getQuestItems();
			try{
				if (index == length - 1) {
					QuestItemNode node = items.get(Integer.parseInt(name));
					this.add(node);
					return true;
				}
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		protected G2DTreeNodeGroup<?> pathCreateGroupNode(String name) {
			return new ConditionGroup(name);
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new ConditionGroupMenu(this).show(tree, e.getX(), e.getY());
			}
		}
	
		private void saveList(QuestCondition condition)
		{
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)getParent();
			removeFromParent();
			try{
				condition.item_name_list.clear();
				for (QuestItemNode item : G2DTree.getNodesSubClass(this, QuestItemNode.class)) {
					condition.item_name_list.add(toPathString(item, "/") + item.getID());
				}
			} finally {
				parent.add(this);
			}
		}
		
		private void loadList(QuestCondition condition)
		{
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)getParent();
			removeFromParent();
			try{
				for (String line : condition.item_name_list) {
					try{
						loadPath(line);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} finally {
				parent.add(this);
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------------
	
	class ConditionGroupMenu extends GroupMenu
	{
		JMenuItem 	add_quest_item;
		boolean 	is_award;
		String 		name_type;
		
		public ConditionGroupMenu(ConditionGroup root) 
		{
			super(root, AbstractDialog.getTopWindow(QuestItemTree.this), QuestItemTree.this);
			if (root.getParent() == getRoot()) {
				super.remove(super.delete);
				super.remove(super.rename);
			}
			
			is_award 			= 
				group_trigger_award == root || G2DTree.containsNode(group_trigger_award, root) ||
				group_complete_award == root || G2DTree.containsNode(group_complete_award, root);
			name_type 			= (is_award?"奖励":"条件");

			add_quest_item		= new JMenuItem("添加" + name_type);
			add_quest_item.addActionListener(this);
			
			add(add_quest_item);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == add_quest_item) {
				String name = JOptionPane.showInputDialog(window, "输入" + name_type + "名字！", "未命名"+name_type);
				if (name!=null && name.length()>0) {
					QuestItemManager items = Studio.getInstance().getQuestManager().getQuestItems();
					QuestItemNode node = items.createQuestItem(name, is_award);
					root.add(node);
					g2d_tree.reload(root);
				}
			}
			else {
				super.actionPerformed(e);
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------
	
}
