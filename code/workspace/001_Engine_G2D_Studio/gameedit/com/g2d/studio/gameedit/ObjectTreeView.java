package com.g2d.studio.gameedit;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.studio.gameedit.template.ObjectNode;
import com.g2d.studio.swing.G2DTree;

public class ObjectTreeView<T extends ObjectNode> extends JSplitPane implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;

	final public DefaultMutableTreeNode tree_root;
	
	final public Class<T> type;
	
	final JScrollPane right = new JScrollPane();
	
	public ObjectTreeView(String title, Class<T> type, ArrayList<T> files) 
	{
		this.type = type;
		this.tree_root = new DefaultMutableTreeNode(title);
		for (T node : files) {
			tree_root.add(node);
		}
		G2DTree tree = new G2DTree(tree_root);
		tree.addTreeSelectionListener(this);
		tree.setMinimumSize(new Dimension(200, 200));
		JScrollPane scroll = new JScrollPane(tree);	
		scroll.setVisible(true);
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(tree);
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
