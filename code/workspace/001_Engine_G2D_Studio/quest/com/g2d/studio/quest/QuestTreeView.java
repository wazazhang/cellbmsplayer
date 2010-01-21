package com.g2d.studio.quest;

import java.io.File;

import com.cell.rpg.quest.Quest;
import com.g2d.studio.gameedit.ObjectTreeViewDynamic;
import com.g2d.studio.gameedit.entity.ObjectGroup;

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
