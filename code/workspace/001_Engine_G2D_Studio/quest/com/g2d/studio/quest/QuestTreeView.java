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
		return new QuestNodeGroup(title, this);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------


//	-------------------------------------------------------------------------------------------------------------------------------

//	-------------------------------------------------------------------------------------------------------------------------------

	
	
}
