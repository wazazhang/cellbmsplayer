package com.g2d.studio.gameedit;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.studio.Config;
import com.g2d.studio.gameedit.template.TemplateTreeNode;
import com.g2d.studio.swing.G2DTree;

public class ObjectTreeView<T extends TemplateTreeNode> extends JSplitPane implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;

	final public DefaultMutableTreeNode tree_root;
	
	final public Class<T> type;
	
	final JScrollPane right = new JScrollPane();
	
	public ObjectTreeView(String title, Class<T> type, File xls_path) 
	{
		this.type = type;
		this.tree_root = new DefaultMutableTreeNode(title);
		ArrayList<T> files = TemplateTreeNode.listXLSRows(
				new File(xls_path.getPath()+File.separatorChar+type.getSimpleName().toLowerCase()+Config.XLS_SUFFIX),
				type);
		for (T node : files) {
			node.load();
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
