package com.g2d.studio.gameedit;

import java.io.File;


import com.cell.CIO;
import com.cell.exception.NotImplementedException;
import com.cell.rpg.RPGObject;

import com.cell.util.IDFactoryInteger;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.ObjectGroup;

public abstract class ObjectTreeViewDynamic<T extends DynamicNode<D>, D extends RPGObject> 
extends ObjectTreeView<T, D> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;
	
	public ObjectTreeViewDynamic(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			File		list_file) 
	{
		super(title, node_type, data_type, list_file);
		getTreeRoot().loadList();
		reload();
		getTree().setDragEnabled(true);
	}
	
	@Override
	public Integer createID() {
		return node_index.createID();
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
	
}
