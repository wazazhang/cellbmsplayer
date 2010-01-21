package com.g2d.studio.gameedit.dynamic;

import com.cell.rpg.template.TEffect;

final public class DEffect extends DynamicNode<TEffect>
{
	public DEffect(IDynamicIDFactory<DAvatar> factory, String name) {
		super(factory, name);
	}
	
	public DEffect(TEffect effect) {
		super(effect, Integer.parseInt(effect.id), effect.name);
	}
	
	@Override
	protected TEffect newData(IDynamicIDFactory<?> factory, String name, Object... args) {
		return null;
	}
	
}
