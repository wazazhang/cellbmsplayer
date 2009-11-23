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

import com.cell.CUtil;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.template.TUnit;
import com.cell.rpg.xls.XLSFullRow;
import com.cell.util.IDFactoryInteger;
import com.g2d.studio.gameedit.ObjectTreeViewTemplate.XLSGroup;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTree;

public abstract class ObjectTreeView<T extends ObjectNode<D>, D extends RPGObject> 
extends JSplitPane implements TreeSelectionListener, ChangeListener
{
	private static final long serialVersionUID = 1L;
	
	final public File						list_file;
	
	// data
	final public Class<T>					node_type;
	final public Class<D>					data_type;
//	final private RPGObjectMap<D>			data_map;
	final private ObjectGroup<T, D>			tree_root;

	// ui
	final private G2DTree 					g2d_tree;
	final private JScrollPane				left = new JScrollPane();
	transient private Component				old_right;

	protected IDFactoryInteger<T>			node_index	= new IDFactoryInteger<T>();

	
	protected ObjectTreeView(
			String title, 
			Class<T> node_type, 
			Class<D> data_type, 
			File list_file) 
	{
		this.list_file		= list_file;
		this.node_type		= node_type;
		this.data_type		= data_type;		
		this.tree_root		= createTreeRoot(title);
		this.g2d_tree 		= new G2DTree(tree_root);

		this.g2d_tree.addTreeSelectionListener(this);
		this.g2d_tree.setMinimumSize(new Dimension(200, 200));
		this.left.setViewportView(g2d_tree);
		this.setOrientation(HORIZONTAL_SPLIT);
		this.setLeftComponent(left);
		this.setRightComponent(new JPanel());		
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	abstract protected ObjectGroup<T, D> createTreeRoot(String title);

	public ObjectGroup<T, D> getTreeRoot() {
		return tree_root;
	}

	final public void saveAll() {
		tree_root.saveList();
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	final public void addNode(ObjectGroup<T, D> root, T node) {
		synchronized(node_index) {
			if (node_index.storeID(node.getIntID(), node)) {
				root.add(node);
				getTree().reload(root);
			}
		}
	}
	
	final public T getNode(int id) {
		synchronized(node_index) {
			return node_index.get(id);
		}
	}

	final public Vector<T> getAllObject() {
		synchronized(node_index) {
			return G2DTree.getNodesSubClass(tree_root, node_type);
		}
	}
	
	final public G2DTree getTree() {
		return g2d_tree;
	}
	
	final public void reload() {
		g2d_tree.reload();
	}

	final public String getTitle() {
		return getTreeRoot().getName();
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
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

//	-----------------------------------------------------------------------------------------------------------------------
	

}
