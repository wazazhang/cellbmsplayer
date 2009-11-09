package com.g2d.studio.gameedit;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;

public class ObjectTreeView<T extends ObjectNode> extends JSplitPane implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;

	final public DefaultMutableTreeNode tree_root;
	final public G2DTree g2d_tree;
	final public Class<T> type;

	final JScrollPane left = new JScrollPane();
	final JScrollPane right = new JScrollPane();
	
	public ObjectTreeView(String title, Class<T> type, ArrayList<T> files) 
	{
		this.type = type;
		this.tree_root = new DefaultMutableTreeNode(title);
		for (T node : files) {
			tree_root.add(node);
		}
		g2d_tree = new G2DTree(tree_root);
		g2d_tree.addTreeSelectionListener(this);
		g2d_tree.setMinimumSize(new Dimension(200, 200));
		left.setViewportView(g2d_tree);
		
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(left);
		this.setRightComponent(right);
		
		
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (type.isInstance(e.getPath().getLastPathComponent())) {
			T node = type.cast(e.getPath().getLastPathComponent());
			right.setViewportView(node.getEditComponent());
		}
	}
	
}
