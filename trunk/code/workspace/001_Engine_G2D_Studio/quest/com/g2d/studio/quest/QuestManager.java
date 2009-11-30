package com.g2d.studio.quest;

import java.io.File;

import com.g2d.Tools;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.AvatarTreeView;
import com.g2d.studio.res.Res;

public class QuestManager extends ManagerForm
{
	private static final long serialVersionUID = 1L;

	QuestTreeView tree_view;

	public QuestManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "任务管理器");
		
		File quest_dir	= new File(studio.project_save_path, "quests");
		File quest_list	= new File(quest_dir, "quest.list");
		tree_view = new QuestTreeView(quest_list);
		this.add(tree_view);
	}
	
	@Override
	public void saveAll() throws Throwable {
		tree_view.saveAll();
	}
	
}
