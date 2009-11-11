package com.g2d.studio.gameedit;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.template.TUnit;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.swing.G2DTree;

public class ObjectTreeView<T extends ObjectNode<D>, D extends RPGObject> 
extends JSplitPane implements TreeSelectionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;

	// data
	final public Class<T>					node_type;
	final public Class<D>					data_type;
	final private RPGObjectMap<D>			data_map;
	final private DefaultMutableTreeNode	tree_root;

	// ui
	final private G2DTree 					g2d_tree;
	final private JScrollPane				left = new JScrollPane();
	transient private Component				old_right;
	
	
	protected ObjectTreeView(
			String title, 
			Class<T> node_type, 
			Class<D> data_type, 
			File zip_file) 
	{
		this.node_type		= node_type;
		this.data_type		= data_type;
		
		this.data_map 		= new RPGObjectMap<D>(data_type, zip_file);
		this.tree_root		= new DefaultMutableTreeNode(title);
		
		this.g2d_tree 		= new G2DTree(tree_root);
		
		this.g2d_tree.addTreeSelectionListener(this);
		this.g2d_tree.setMinimumSize(new Dimension(200, 200));
		this.left.setViewportView(g2d_tree);
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(left);
		this.setRightComponent(new JPanel());		
	}
	
	public ObjectTreeView(
			String title,
			Class<T> node_type, 
			Class<D> data_type, 
			File zip_file,
			File xls_file) 
	{
		this(title, node_type, data_type, zip_file);
		ArrayList<T> xls_rows = XLSTemplateNode.listXLSRows(xls_file, node_type, data_map);
		for (T node : xls_rows) {
			addNode(node);
		}
		g2d_tree.reload();
	}

	public void addNode(T node) {
		tree_root.add(node);
		data_map.put(node.getData().id, node.getData());
		g2d_tree.getTreeModel().reload();
	}
	
	public G2DTree getTree() {
		return g2d_tree;
	}
	
	
	public T getObject(String id)
	{
		Vector<T> list = G2DTree.getNodesSubClass(tree_root, node_type);
		for (T t : list) {
			if (t.getID().equals(id)) {
				return t;
			}
		}
		return null;
	}
	
	public DefaultMutableTreeNode getTreeRoot() {
		return tree_root;
	}

	public void saveAll() {
		Vector<T> list = G2DTree.getNodesSubClass(tree_root, node_type);
		for (T t : list) {
			data_map.put(t.getData().id, t.getData());
		}
		data_map.saveAll();
	}
	
	public RPGObjectMap<D> getDataMap() {
		return data_map;
	}
	
	public void reload() {
		g2d_tree.reload();
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (node_type.isInstance(e.getPath().getLastPathComponent())) {
			old_right = getRightComponent();
			T node = node_type.cast(e.getPath().getLastPathComponent());
			if (node.getEditComponent()!=null) {
				node.getEditComponent().setVisible(true);
				this.setRightComponent(node.getEditComponent());
			}
			if (old_right != null && old_right != node.getEditComponent()) {
				old_right.setVisible(false);
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() instanceof JTabbedPane) {
			JTabbedPane table = (JTabbedPane)e.getSource();
			if (old_right!=null) {
				if (table.getSelectedComponent()==this) {
					old_right.setVisible(true);
				} else {
					old_right.setVisible(false);
				}
			}
		}
	}
	
}
