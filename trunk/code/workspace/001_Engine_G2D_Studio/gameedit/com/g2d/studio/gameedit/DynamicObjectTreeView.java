package com.g2d.studio.gameedit;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;

public class DynamicObjectTreeView<T extends DynamicNode> extends ObjectTreeView<T> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;

	AtomicInteger data_index = new AtomicInteger();
	ConcurrentHashMap<Integer, T> data_map = new ConcurrentHashMap<Integer, T>(1000);
	
	public DynamicObjectTreeView(String title, Class<T> type) 
	{
		super(title, type, new ArrayList<T>());
		g2d_tree.setMinimumSize(new Dimension(200,200));
	}
	
	public void addNode(T node) {
		synchronized(data_index) {
			int id = node.getIntID();
			if (!data_map.containsKey(id)) {
				if (data_index.get() < id) {
					data_index.set(id);
				}
				tree_root.add(node);
				data_map.put(id, node);
			}
		}
		g2d_tree.getTreeModel().reload();
//		g2d_tree.scrollPathToVisible(new TreePath(new Object[]{tree_root, node}));
	}
	
	public T getNode(int id) {
		synchronized(data_index) {
			return data_map.get(id);
		}
	}
	
	@Override
	public int createID() {
		return data_index.incrementAndGet();
	}
	
}
