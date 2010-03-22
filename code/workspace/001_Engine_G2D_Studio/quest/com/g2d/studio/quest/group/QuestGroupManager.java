package com.g2d.studio.quest.group;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;

import com.g2d.Tools;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.quest.QuestNode;
import com.g2d.studio.quest.QuestTreeView;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DWindowToolBar;

public class QuestGroupManager extends ManagerForm implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	G2DWindowToolBar	tool_bar;
	
	File 				quest_group_dir;
	File 				quest_group_list;
	QuestGroupTreeView	quest_group_view;
	
	public QuestGroupManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "任务编组管理器", Res.icon_quest_group);

		tool_bar 			= new G2DWindowToolBar(this);
		this.add(tool_bar, BorderLayout.NORTH);
		
		quest_group_dir		= new File(studio.project_save_path, "questgroup");
		quest_group_list	= new File(quest_group_dir, "questgroup.list");
		quest_group_view	= new QuestGroupTreeView("任务编组管理器", quest_group_list);
		this.add(quest_group_view, BorderLayout.CENTER);
		
		this.setSize(getWidth()-100, getHeight()-100);
		this.setLocation(getX()+100, getY()+100);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			try {
				this.saveAll();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public void saveAll() throws Throwable {
		quest_group_view.saveAll();
	}
	
	public Vector<DQuestGroup> getQuestGroups() {
		return quest_group_view.getAllObject();
	}
	
	public DQuestGroup getQuestGroup(int id) {
		return quest_group_view.getNode(id);
	}



}
