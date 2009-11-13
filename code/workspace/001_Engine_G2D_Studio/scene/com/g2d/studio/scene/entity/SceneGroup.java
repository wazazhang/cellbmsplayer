package com.g2d.studio.scene.entity;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTreeNode;

public class SceneGroup extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 1L;
	
	private String name ;
	
	public SceneGroup(String name) {
		super(name);
		this.name = name;
		this.children = new Vector();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void add(MutableTreeNode newChild) {
		if (newChild instanceof SceneGroup) {
			if (findChild(newChild.toString())!=null) {
				throw new IllegalStateException("duplicate child ! " + newChild);
			}
		}
		super.add(newChild);
	}
	
	public boolean setName(String name) {
		if (getParent()!=null) {
			Enumeration<?> childs = getParent().children();
			while (childs.hasMoreElements()) {
				Object obj = childs.nextElement();
				if (obj instanceof SceneGroup) {
					if (obj.toString().equals(name)) {
						System.err.println("duplicate tree node name !");
						return false;
					}
				}
			}
		}
		this.userObject = name;
		this.name = name;
		return true;
	}
	
	public SceneGroup findChild(String name) {
		for (Object obj : children) {
			if (obj instanceof SceneGroup) {
				if (obj.toString().equals(name)) {
					return (SceneGroup)obj;
				}
			}
		}
		return null;
	}
}
