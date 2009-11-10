package com.g2d.studio.scene.entity;

import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;

public class SceneUnitNode extends DynamicNode<SceneUnitNode> {

	public SceneUnitNode(IDynamicIDFactory<?> factory, String name) {
		super(factory, name);
	}
	
	@Override
	public String getEntryName() {
		return null;
	}
}
