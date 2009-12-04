package com.g2d.studio.quest;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.ObjectAdapters.QuestItemAdapter;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.util.TextEditor;

public class QuestEditor extends ObjectViewer<QuestNode> implements RPGSerializationListener
{	
	PanelDiscussion		panel_quest_discussion;
	JScrollPane			panel_quest_data;
	QuestConditionTree	conditions;
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
		conditions 				= new QuestConditionTree(tobject.getData());
		panel_quest_discussion	= new PanelDiscussion();
		panel_quest_data		= new JScrollPane(conditions);		
		table.removeAll();
		table.addTab("任务内容", panel_quest_discussion);
		table.addTab("任务数据", panel_quest_data);
		table.addTab("附加属性", page_object_panel);
	}

	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {
	}
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {
		conditions.save();
		tobject.getData().setDiscussion(panel_quest_discussion.getText());
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
