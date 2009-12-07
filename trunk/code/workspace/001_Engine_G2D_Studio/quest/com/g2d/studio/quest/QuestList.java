package com.g2d.studio.quest;

import com.g2d.studio.Studio;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.swing.G2DList;

public class QuestList extends G2DList<QuestNode>
{
	public QuestList() {
		super(Studio.getInstance().getQuestManager().getQuests());
	}
}
