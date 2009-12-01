package com.g2d.studio.gameedit.dynamic;

import javax.swing.JTabbedPane;

import com.cell.rpg.quest.QuestItem;
import com.g2d.studio.gameedit.ObjectViewer;

public class DQuestItem extends DynamicNode<QuestItem>
{
	public DQuestItem(IDynamicIDFactory<DQuestItem> factory, String name) {
		super(factory, name);
	}
	
	public DQuestItem(QuestItem item) {
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
	
	public class DQuestItemViewer extends ObjectViewer<DQuestItem>
	{
		private static final long serialVersionUID = 1L;
		
		public DQuestItemViewer() {
			super(DQuestItem.this);
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
	}
}
