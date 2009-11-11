package com.g2d.studio.scene.entity;

import javax.swing.ImageIcon;

import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTreeNode;

public class SceneGroup extends G2DTreeNode<SceneNode>
{
	final public String name;
	
	public SceneGroup(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}	
	
	@Override
	protected ImageIcon createIcon() {
		return null;
	}
	
}
