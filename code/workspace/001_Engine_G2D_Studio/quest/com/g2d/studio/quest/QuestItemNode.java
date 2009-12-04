package com.g2d.studio.quest;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import com.cell.rpg.quest.QuestItem;
import com.g2d.Tools;
import com.g2d.studio.gameedit.ObjectAdapters;
import com.g2d.studio.gameedit.ObjectViewer;
import com.g2d.studio.gameedit.ObjectAdapters.QuestItemAdapter;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.icon.IconFile;
import com.g2d.studio.res.Res;

public class QuestItemNode extends DynamicNode<QuestItem>
{
	public QuestItemNode(IDynamicIDFactory<QuestItemNode> factory, String name) {
		super(factory, name);
	}
	
	public QuestItemNode(QuestItem item) {
		super(item, item.getType(), item.name);
	}
	
	@Override
	protected QuestItem newData(IDynamicIDFactory<?> factory, String name) {
		return new QuestItem(this.getIntID(), name);
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		if (edit_component == null) {
			edit_component = new DQuestItemViewer();
		}
		return super.getEditComponent();
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	public class DQuestItemViewer extends ObjectViewer<QuestItemNode>
	{
		private static final long serialVersionUID = 1L;
		
		public DQuestItemViewer() {
			super(QuestItemNode.this, new ObjectAdapters.QuestItemAdapter());
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
	}
}
