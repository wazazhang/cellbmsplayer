package com.g2d.studio.gameedit.dynamic;

import javax.swing.JTabbedPane;

import com.cell.rpg.template.TEffect;
import com.g2d.studio.gameedit.EffectEditor;
import com.g2d.studio.gameedit.ObjectViewer;

final public class DEffect extends DynamicNode<TEffect>
{
	public DEffect(IDynamicIDFactory<DEffect> factory, String name) {
		super(factory, name);
	}
	
	public DEffect(TEffect effect) {
		super(effect, Integer.parseInt(effect.id), effect.name);
	}
	
	@Override
	protected TEffect newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return new TEffect(getIntID(), name);
	}
	
	@Override
	public ObjectViewer<?> getEditComponent() {
		if (edit_component == null) {
			edit_component = new EffectViewer();
		}
		return edit_component;
	}
	
//	----------------------------------------------------------------------------------------------------------------------
	
	public class EffectViewer extends ObjectViewer<DEffect>
	{
		private static final long serialVersionUID = 1L;
		
		public EffectViewer() {
			super(DEffect.this);
			table.remove(page_object_panel);
			table.remove(page_abilities);
			table.addTab("粒子编辑器", new EffectEditor(getData()));
		}
		
		@Override
		protected void appendPages(JTabbedPane table) {}
	}
}
