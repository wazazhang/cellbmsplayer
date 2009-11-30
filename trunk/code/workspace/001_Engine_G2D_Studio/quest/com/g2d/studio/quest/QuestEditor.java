package com.g2d.studio.quest;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.util.TextEditor;

public class QuestEditor extends ObjectViewer<QuestNode> implements RPGSerializationListener
{	
	PanelDiscussion	panel_quest_discussion;
	PanelPurpose	panel_quest_purpose;
	PanelAward		panel_quest_award;
	
	public QuestEditor(QuestNode node) {
		super(node);
		if (node.getData().getRPGSerializationListeners() == null ||
			node.getData().getRPGSerializationListeners().contains(this)==false) {
			node.getData().addRPGSerializationListener(this);
		}
	}
	
	@Override
	protected void appendPages(JTabbedPane table) 
	{
		panel_quest_discussion	= new PanelDiscussion();
		panel_quest_purpose		= new PanelPurpose();
		panel_quest_award 		= new PanelAward();
		table.removeAll();
		table.addTab("任务内容", panel_quest_discussion);
		table.addTab("任务目标", panel_quest_purpose);
		table.addTab("任务奖励", panel_quest_award);
		table.addTab("附加属性", page_object_panel);
	}

	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {
	}
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {
		tobject.getData().discussion.text = panel_quest_discussion.getText();
		System.out.println(tobject.getData().discussion.text);
	}
	
	class PanelDiscussion extends TextEditor
	{		
		public PanelDiscussion() {
			setText(tobject.getData().discussion.text);
		}
	}
	
	class PanelPurpose extends JPanel
	{


	}
	
	class PanelAward extends JPanel
	{

		
	}
	
}
