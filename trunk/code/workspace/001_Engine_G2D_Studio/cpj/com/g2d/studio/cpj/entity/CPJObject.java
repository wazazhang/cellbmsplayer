package com.g2d.studio.cpj.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.g2d.Tools;
import com.g2d.cell.CellSetResource.CellSetObject;
import com.g2d.display.DisplayObject;
import com.g2d.studio.old.swing.G2DTreeNode;

public abstract class CPJObject <T extends CellSetObject> extends G2DTreeNode<CPJObject<?>>
{
	final protected CPJFile parent;
	
	final protected String	name;
	
	final protected T		set_object;
	
	protected BufferedImage	snapshoot;
	
	public CPJObject(CPJFile parent, String name, Class<T> type) {
		this.parent = parent;
		this.name	= name;
		this.set_object	= parent.getSetResource().getSetObject(type, name);
	}
	
	abstract public DisplayObject getDisplayObject();

	public BufferedImage getSnapShoot(){
		return snapshoot;
	}
	
	public T getSetObject() {
		return set_object;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public ImageIcon getIcon(boolean update) {
		if (icon_snapshot == null) {
			if (getSnapShoot()!=null) {
				icon_snapshot = Tools.createIcon(getSnapShoot());
			}
		}
		return super.getIcon(update);
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}
	
	@Override
	public boolean getAllowsChildren() {
		return false;
	}
	


}
