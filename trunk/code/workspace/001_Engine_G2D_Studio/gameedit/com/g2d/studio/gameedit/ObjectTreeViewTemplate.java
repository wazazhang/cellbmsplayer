package com.g2d.studio.gameedit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;


import com.cell.CIO;
import com.cell.CUtil;
import com.cell.exception.NotImplementedException;
import com.cell.rpg.RPGObject;
import com.cell.rpg.io.RPGObjectMap;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSFullRow;

import com.cell.util.IDFactoryInteger;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.dynamic.DAvatar;
import com.g2d.studio.gameedit.dynamic.DynamicNode;
import com.g2d.studio.gameedit.dynamic.IDynamicIDFactory;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.entity.ObjectNode;
import com.g2d.studio.gameedit.template.XLSItem;
import com.g2d.studio.gameedit.template.XLSSkill;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.gameedit.template.XLSUnit;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNodeGroup;

public class ObjectTreeViewTemplate<T extends XLSTemplateNode<D>, D extends TemplateNode> 
extends ObjectTreeView<T, D>
{
	private static final long serialVersionUID = 1L;
	
	final public File xls_file ;
	
	Map<String, XLSFullRow> xls_row_map;
	
	public ObjectTreeViewTemplate(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			File		list_file,
			File		xls_file,
			ProgressForm progress) 
	{
		super(title, node_type, data_type, list_file);
		this.xls_file = xls_file;
		this.xls_row_map = new TreeMap<String, XLSFullRow>();
		int i=0;		
		Collection<XLSFullRow> list = XLSFullRow.getXLSRows(xls_file, XLSFullRow.class);
		progress.setMaximum(title, list.size());
		for (XLSFullRow row : list) {
			xls_row_map.put(row.id, row);
			progress.setValue(title, i++);
		}
		getTreeRoot().loadList();
		for (XLSFullRow row : xls_row_map.values()) {
			if (getNode(Integer.parseInt(row.id))==null) {
				T node = createObjectFromRow(row.id, null);
				if (node != null) {
					addNode(getTreeRoot(), node);
				}
			}
		}
		reload();
	}
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	protected XLSGroup createTreeRoot(String title) {
		return new XLSGroup(title);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	private T createObjectFromRow(String row_key, TemplateNode data) 
	{
		T node = null;
		try{
			XLSFullRow row = xls_row_map.get(row_key);
			if (row!=null) {
				if (node_type.equals(XLSUnit.class)) {
					node = (node_type.cast(new XLSUnit(row.xls_file, row, data)));
				} else if (node_type.equals(XLSItem.class)) {
					node = (node_type.cast(new XLSItem(row.xls_file, row, data)));
				} else if (node_type.equals(XLSSkill.class)) {
					node = (node_type.cast(new XLSSkill(row.xls_file, row, data)));
				}
			} else {
				System.err.println(getTitle() + " : XML 行不存在 : " + row_key);
			}
		} catch (Exception err){
			err.printStackTrace();
		}
		return node;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------

	class XLSGroup extends ObjectGroup<T, D>
	{
		private static final long serialVersionUID = 1L;

		public XLSGroup(String name) {
			super(name, 
					ObjectTreeViewTemplate.this.list_file,
					ObjectTreeViewTemplate.this.node_type, 
					ObjectTreeViewTemplate.this.data_type);
		}
		
		protected boolean createObjectNode(String key, D data) {
			T node = createObjectFromRow(key, data);
			if (node!=null) {
				addNode(this, node);
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		protected XLSGroup createGroupNode(String name) {
			return new XLSGroup(name);
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	

	
	
	
	
	
	
	
	
	
	
}
