package com.g2d.studio.swing;

import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;

import com.cell.DObject;
import com.cell.ExtObject;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;

public abstract class G2DTreeNode<C extends G2DTreeNode<?>> extends ExtObject implements MutableTreeNode
{
	
	Vector<C> 				childs = new Vector<C>();
	MutableTreeNode			parent;
	
	protected Object 		user_object	= "node";
	private ImageIcon 		icon_snapshot;
	
	public G2DTreeNode() 
	{
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	
	public void onDoubleClicked(JTree tree, MouseEvent e){
//		System.out.println("onDoubleClicked : " + getName());
	}
	
	public void onRightClicked(JTree tree, MouseEvent e){
//		System.out.println("onRightClicked : " + getName());
	}

	public void onClicked(JTree tree, MouseEvent e){
//		System.out.println("onClicked : " + getName());
	}
	
	public void onSelected(JTree tree){
//		System.out.println("onSelected : " + getName());
	}
	
	@Override
	protected void onRead(MarkedHashtable data) {}
	@Override
	protected void onWrite(MarkedHashtable data) {}
	
//	
//	----------------------------------------------------------------------------------------------------------------------------------

	abstract public		String		getName();

	abstract protected	ImageIcon	createIcon();
	
	public ImageIcon getIcon(boolean update){
		if (icon_snapshot==null || update) {
			icon_snapshot = createIcon();
		}
		return icon_snapshot;
	}

	public void resetIcon() {
		icon_snapshot = null;
	}

//	----------------------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {
		return getName();
	}

	public Vector<C> getChilds() {
		return childs;
	}
	
	public Enumeration<C> children() {
		return childs.elements();
	}
	
	public C getChildAt(int childIndex) {
		return childs.elementAt(childIndex);
	}
	
	public int getChildCount() {
		return childs.size();
	}
	
	public int getIndex(javax.swing.tree.TreeNode node) {
		return childs.indexOf(node);
	}
	
	public MutableTreeNode getParent() {
		return parent;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public boolean isLeaf() {
		return false;
	}
	public boolean getAllowsChildren() {
		return true;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public void addChild(C child) {
		if (checkAddChild(child)) {
			childs.addElement(child);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void insert(MutableTreeNode child, int index) {
		if (checkAddChild((C)child)) {
			childs.insertElementAt((C)child, index);
		}
	}
	
	protected boolean checkAddChild(C child) {
//		C pre = getChild(child.getName());
//		if (pre!=null) {
//			throw new IllegalStateException("duplicate element \"" + child.getName() + "\" !");
//		}
		return true;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------

	public void remove(int index) {
		childs.remove(index);
	}

	public void remove(MutableTreeNode node) {
		childs.remove(node);
	}

	public void removeFromParent() {
		if (parent!=null) {
			parent.remove(this);
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------------

	public void setParent(MutableTreeNode newParent) {
		parent = newParent;
	}
	
	@Override
	public void setUserObject(Object object) {
		user_object = object;
	}
	

}
