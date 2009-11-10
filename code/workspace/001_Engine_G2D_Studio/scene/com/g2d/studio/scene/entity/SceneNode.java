package com.g2d.studio.scene.entity;

import java.util.ArrayList;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import com.cell.rpg.ability.AbstractAbility;
import com.g2d.studio.cpj.CPJIndex;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.swing.G2DTreeNode;

final public class SceneNode extends DynamicNode<SceneUnitNode>
{
	final public CPJIndex<CPJWorld> world_index;
	
	public SceneNode(IDynamicIDFactory<?> factory, String name, CPJIndex<CPJWorld> world_index) {
		super(factory, name);
		this.world_index = world_index;
	}

	@Override
	protected ImageIcon createIcon() {
		return null;
	}
	
	@Override
	public String getName() {
		return null;
	}

	@Override
	final public String getEntryName() {
		return "scene/"+getID()+".xml";
	}

}
