package com.g2d.studio.quest;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.cell.rpg.quest.Quest;
import com.cell.rpg.template.TAvatar;
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.CPJResourceSelectDialog;
import com.g2d.studio.cpj.CPJResourceType;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.gameedit.AvatarTreeView;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;
import com.g2d.util.AbstractDialog;

public class QuestTreeView extends ObjectTreeViewDynamic<QuestNode, Quest>
{
	private static final long serialVersionUID = 1L;

	public QuestTreeView(File quest_list_file) {
		super("任务管理器", QuestNode.class, Quest.class, quest_list_file);
	}
	
	@Override
	protected ObjectGroup<QuestNode, Quest> createTreeRoot(String title) {
		return new QuestGroup(title);
	}
	
	

//	-------------------------------------------------------------------------------------------------------------------------------


	public class QuestGroup extends ObjectGroup<QuestNode, Quest>
	{
		private static final long serialVersionUID = 1L;

		public QuestGroup(String name) {
			super(name, 
					QuestTreeView.this.list_file,
					QuestTreeView.this.node_type, 
					QuestTreeView.this.data_type);
		}
		
		@Override
		protected G2DTreeNodeGroup<?> createGroupNode(String name) {
			return new QuestGroup(name);
		}
		
		@Override
		protected boolean createObjectNode(String key, Quest data) {
			try{
				addNode(this, new QuestNode(data));
				return true;
			}catch(Exception err){
				err.printStackTrace();
			}
			return false;
		}
		
		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new QuestRootMenu(this).show(
						getTree(),
						e.getX(),
						e.getY());
			}
		}
	}
	

//	-------------------------------------------------------------------------------------------------------------------------------
	class QuestRootMenu extends GroupMenu
	{
		private static final long serialVersionUID = 1L;
		
		QuestGroup root;
		JMenuItem add_quest = new JMenuItem("添加任务");
		
		public QuestRootMenu(QuestGroup root) {
			super(root, getTree(), getTree());
			this.root = root;
			add_quest.addActionListener(this);
			add(add_quest);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
			if (e.getSource() == add_quest) {
			}
		}
	}

//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
}
