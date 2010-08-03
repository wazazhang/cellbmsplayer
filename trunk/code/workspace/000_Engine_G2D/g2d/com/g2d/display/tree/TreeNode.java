package com.g2d.display.tree;

import java.util.Collection;


/**
 * 定义可以用作 TreeView 中树节点的对象所需的要求。 
 * @author WAZA
 */
public interface TreeNode
{
	/**
	 * 以 Enumeration 的形式返回接收者的子节点。
	 */
	public Collection<TreeNode> children();

	/** 
	 * 返回索引 childIndex 位置的子 TreeNode。
	 */
	public TreeNode getChildAt(int childIndex);

	/** 
	 * 返回接收者包含的子 TreeNode 数。 
	 */
	public int getChildCount();

	/**
	 * 返回接收者子节点中的 node 的索引。 
	 */
	public int getIndex(TreeNode node);

	/** 
	 * 返回接收者的父 TreeNode。 
	 */
	public TreeNode getParent();

}
