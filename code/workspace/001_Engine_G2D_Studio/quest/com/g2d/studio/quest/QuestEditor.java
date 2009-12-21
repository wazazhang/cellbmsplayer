package com.g2d.studio.quest;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.quest.events.QuestEventView;
import com.g2d.studio.quest.items.QuestItemView;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.util.TextEditor;

public class QuestEditor extends ObjectViewer<QuestNode> implements RPGSerializationListener
{	
	PanelDiscussion		page_quest_discussion;
	JSplitPane			page_quest_data;

	QuestEventView		data_event;
	QuestItemView		data_quest;
	
//	-------------------------------------------------------------------------------------
	
	public QuestEditor(QuestNode node) {
		super(node);
		if (node.getData().getRPGSerializationListeners() == null ||
			node.getData().getRPGSerializationListeners().contains(this)==false) {
			node.getData().addRPGSerializationListener(this);
		}
	}
	
	@Override
	protected void appendPages(JTabbedPane table) {

		data_event			= new QuestEventView(tobject.getData());
		data_quest			= new QuestItemView(tobject.getData());
		page_quest_discussion	= new PanelDiscussion();
		page_quest_data			= new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, 
				data_event, 
				data_quest);
		
		table.removeAll();
		table.addTab("任务内容", page_quest_discussion);
		table.addTab("任务数据", page_quest_data);
		table.addTab("附加属性", page_object_panel);
	}

	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {
	}
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {
		data_event.save();
		data_quest.save();
		tobject.getData().setDiscussion(page_quest_discussion.getText());
	}
	
//	-------------------------------------------------------------------------------------
	
	class PanelDiscussion extends TextEditor
	{		
		public PanelDiscussion() {
			setText(tobject.getData().getDiscussion());
		}
	}

//	-------------------------------------------------------------------------------------
	
//	-------------------------------------------------------------------------------------
	
}
