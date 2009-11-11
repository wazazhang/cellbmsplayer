package com.g2d.studio.gameedit;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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

import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.swing.G2DTree;

public class ObjectTreeViewDynamic<T extends DynamicNode<D>, D extends RPGObject> 
extends ObjectTreeView<T, D> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;

	AtomicInteger 					node_index	= new AtomicInteger();
	ConcurrentHashMap<Integer, T>	nodes_map	= new ConcurrentHashMap<Integer, T>(1000);
	 
	public ObjectTreeViewDynamic(String title, Class<T> node_type, Class<D> data_type, File zip_file) 
	{
		super(title, node_type, data_type, zip_file);
		
		for (D data : getDataMap().values()) {
			T node = DynamicNode.createNode(node_type, data);
			if (node!=null) {
				addNode(node);
			}
		}
	}
	
	public void addNode(T node) {
		synchronized(node_index) {
			int id = node.getIntID();
			if (!nodes_map.containsKey(id)) {
				if (node_index.get() < id) {
					node_index.set(id);
				}
				super.addNode(node);
				nodes_map.put(id, node);
			}
		}
	}
	
	public T getNode(int id) {
		synchronized(node_index) {
			return nodes_map.get(id);
		}
	}
	
	@Override
	public int createID() {
		return node_index.incrementAndGet();
	}
	
}
