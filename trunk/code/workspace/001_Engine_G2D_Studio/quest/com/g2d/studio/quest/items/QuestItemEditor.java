package com.g2d.studio.quest.items;

import javax.swing.JTabbedPane;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGSerializationListener;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.quest.QuestCellEditAdapter.*;

public class QuestItemEditor extends ObjectViewer<QuestItemNode> implements RPGSerializationListener
{
	QuestItemScriptPanel script_panel;
	
	public QuestItemEditor(QuestItemNode node) {
		super(node, 
				new QuestItemTagQuest(),
				new QuestItemTagItem(),
				new QuestItemTagQuestItem(),
				new QuestItemTagUnitField(),
				new QuestItemAwardItem(),
				new QuestItemAwardTeleport()
		);
		if (node.getData().getRPGSerializationListeners() == null ||
			node.getData().getRPGSerializationListeners().contains(this)==false) {
			node.getData().addRPGSerializationListener(this);
		}
	}

	@Override
	protected void appendPages(JTabbedPane table) {
//		script_panel	= new QuestItemScriptPanel(tobject.getData());
//		table.insertTab("脚本", null, script_panel, null, 0);
		table.setSelectedComponent(page_abilities);
	}
	
	@Override
	public void onReadComplete(RPGObject object, String xmlFile) {}
	
	@Override
	public void onWriteBefore(RPGObject object, String xmlFile) {
//		tobject.getData().script = script_panel.getScript();
	}
}
