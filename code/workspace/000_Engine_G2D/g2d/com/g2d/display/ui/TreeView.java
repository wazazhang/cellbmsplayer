package com.g2d.display.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.CIO;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.tree.DefaultTreeAdapter;
import com.g2d.display.tree.TreeAdapter;
import com.g2d.display.tree.TreeNode;
import com.g2d.display.tree.TreeNodeListener;
import com.g2d.display.ui.event.ActionEvent;
import com.g2d.display.ui.event.ActionListener;

public class TreeView extends Container
{
	final private TreeAdapter	adapter;
	
	final private TreeNodeActionListener
								action_listener = new TreeNodeActionListener();
	
	final private HashSet<TreeNodeListener> 
								tree_node_listeners = new HashSet<TreeNodeListener>();
	
//	------------------------------------------------------------------------------------------
	
	/**父节点x和子节点x相差的距离*/
	private int			border_w	= 20;
	/**没行间距*/
	private int			border_h	= 2;
	
	private TreeNode	root;
	
	private Hashtable<TreeNode, UIComponent> 
						nodes = new Hashtable<TreeNode, UIComponent>();
	
	private HashSet<TreeNode>
						expaneds = new HashSet<TreeNode>();
	
//	------------------------------------------------------------------------------------------
	
	public TreeView() {
		this(null, new DefaultTreeAdapter());
	}

	public TreeView(TreeNode root) {
		this(root, new DefaultTreeAdapter());
	}
	
	public TreeView(TreeAdapter adapter) {
		this(null, adapter);
	}
	
	public TreeView(TreeNode root, TreeAdapter adapter) {
		this.adapter = adapter;
		this.enable_drag = false;
		this.enable_drag_resize = false;
		this.setTreeRoot(root);
	}
	
	@Override
	public void setSize(int w, int h) {
		super.setMinimumSize(w, h);
	}
	
//	------------------------------------------------------------------------------------------
	
	synchronized public void setTreeRoot(TreeNode root) {
		this.root = root;
		this.expaneds.clear();
		this.nodes.clear();
		this.expaneds.add(root);
		this.initTreeNode(root, 0);
		this.refresh();
	}
	
	void initTreeNode(TreeNode node, int deep)
	{
		UIComponent ui = adapter.getComponent(node, this) ;
		ui.addEventListener(action_listener);
		this.nodes.put(node, ui);
		if (node.getChildCount() > 0) {
			for (int cr = 0; cr < node.getChildCount(); cr++) {
				TreeNode cn = node.getChildAt(cr);
				initTreeNode(cn, deep + 1);
			}
		}
	}

	synchronized public void refresh() {
		removeChilds(nodes.values());
		if (root != null) {
			resetTreeNode(root, 0, new AtomicInteger(border_h), new AtomicInteger(border_h));
		}
		int mw = getMinimumWidth();
		int mh = getMinimumHeight();
		for (UIComponent ui : nodes.values()) {
			if (contains(ui)) {
				mw = Math.max(mw, ui.getX() + ui.getWidth()  + border_h);
				mh = Math.max(mh, ui.getY() + ui.getHeight() + border_h);
			}
		}
		super.setSize(mw, mh);
	}
	

	void resetTreeNode(TreeNode node, int deep, AtomicInteger x, AtomicInteger y) 
	{
		UIComponent ui = nodes.get(node);
		this.addComponent(ui, x.get(), y.get());
		y.addAndGet(ui.getHeight() + border_h);
		
		if (node.getChildCount() > 0) {
			if (isExpanded(node)) {
				try {
					x.addAndGet(border_w);
					for (int cr = 0; cr < node.getChildCount(); cr++) {
						TreeNode cn = node.getChildAt(cr);
						resetTreeNode(cn, deep + 1, x, y);
					}
				} finally {
					x.addAndGet(-border_w);
				}
			}
		}
	}
	
	
//	------------------------------------------------------------------------------------------

	public TreeNode getTreeRoot() {
		return root;
	}
	
//	------------------------------------------------------------------------------------------
	
	public boolean isExpanded(TreeNode node) {
		return expaneds.contains(node);
	}
	
	public void setExpand(TreeNode node, boolean expand) {
		if (expand) {
			expaneds.add(node);
		} else {
			expaneds.remove(node);
		}
		refresh();
	}

	public void expandAll(boolean expand) {
		if (expand) {
			expaneds.addAll(nodes.keySet());
		} else {
			expaneds.clear();
		}
		refresh();
	}
	
//	------------------------------------------------------------------------------------------
	
	@Override
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof TreeNodeListener) {
			tree_node_listeners.add((TreeNodeListener)listener);
		}
	}
	
	@Override
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof TreeNodeListener) {
			tree_node_listeners.remove((TreeNodeListener)listener);
		}
	}
	
	private TreeNode getTreeNode(UIComponent comp) {
		for (TreeNode tn : nodes.keySet()) {
			UIComponent ui = nodes.get(tn);
			if (ui == comp) {
				return tn;
			}
		}
		return null;
	}
	
	class TreeNodeActionListener implements ActionListener {
		@Override
		public void itemAction(UIComponent item, ActionEvent event) {
			for (TreeNodeListener tnl : tree_node_listeners) {
				TreeNode tn = getTreeNode(item);
				tnl.onTreeNodeClick(tn, item, TreeView.this);
			}
		}
	}
	
}
