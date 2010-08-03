package com.g2d.display.tree;

import java.util.Collection;
import java.util.Vector;

public class DefaultTreeNode implements TreeNode
{
	private static final long serialVersionUID = 1L;

	private TreeNode 			parent;
	
	private Vector<TreeNode>	sub_childs = new Vector<TreeNode>();
	
	private Object				data;
	
	public DefaultTreeNode(Object data) {
		this.data = data;
	}
	
	public DefaultTreeNode() {
		this.data = "tree node";
	}
	
	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return data + "";
	}
	
	
	public void addChild(TreeNode c) {
		sub_childs.add(c);
		if (c instanceof DefaultTreeNode) {
			((DefaultTreeNode) c).parent = this;
		}
	}
	
	@Override
	public Collection<TreeNode> children() {
		return sub_childs;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return sub_childs.get(childIndex);
	}

	@Override
	public int getChildCount() {
		return sub_childs.size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return sub_childs.indexOf(node);
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

}
