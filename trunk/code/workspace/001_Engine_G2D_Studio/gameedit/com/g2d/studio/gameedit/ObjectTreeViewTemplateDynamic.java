package com.g2d.studio.gameedit;

import java.io.File;

import com.cell.rpg.RPGObject;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;

public abstract class ObjectTreeViewTemplateDynamic<T extends DynamicNode<D>, D extends RPGObject> 
extends ObjectTreeView<T, D> implements IDynamicIDFactory<T>
{
	private static final long serialVersionUID = 1L;
	
	public ObjectTreeViewTemplateDynamic(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			String		objects_dir) 
	{
		super(title, node_type, data_type, ObjectManager.toListFile(objects_dir, data_type));
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
