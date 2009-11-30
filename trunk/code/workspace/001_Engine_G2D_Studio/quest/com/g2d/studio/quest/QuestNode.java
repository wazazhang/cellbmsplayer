package com.g2d.studio.quest;

import com.cell.rpg.quest.Quest;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;

public class QuestNode extends DynamicNode<Quest>
{
	public QuestNode(IDynamicIDFactory<DAvatar> factory, String name) {
		super(factory, name);
	}

	@Override
	protected Quest newData(IDynamicIDFactory<?> factory, String name) {
		return new Quest(getIntID());
	}
	
	public QuestNode(Quest data) {
		super(data, data.getIntID(), data.name);
	}
}
