package com.g2d.studio.gameedit;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTree;

import com.cell.rpg.template.TemplateNode;
import com.cell.rpg.xls.XLSColumns;
import com.cell.rpg.xls.XLSFullRow;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.gameedit.entity.ObjectGroup;
import com.g2d.studio.gameedit.template.XLSTemplateNode;
import com.g2d.studio.swing.G2DTreeNodeGroup.GroupMenu;

public class ObjectTreeViewTemplateXLS<T extends XLSTemplateNode<D>, D extends TemplateNode> 
extends ObjectTreeView<T, D>
{
	private static final long serialVersionUID = 1L;
	
	final public File 		xls_file ;
	final public XLSColumns	xls_columns;
	Map<String, XLSFullRow> xls_row_map;
	
	public ObjectTreeViewTemplateXLS(
			String		title, 
			Class<T>	node_type, 
			Class<D>	data_type, 
			String		objects_dir,
			File		xls_file,
			ProgressForm progress) 
	{
		super(title, node_type, data_type, ObjectManager.toListFile(objects_dir, data_type));
		
		this.xls_file = xls_file;
		this.xls_row_map = new TreeMap<String, XLSFullRow>();
		int i=0;
		
		xls_columns = XLSColumns.getXLSColumns(xls_file);
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
		getTree().setDragEnabled(true);
	}
	
	
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	protected XLSGroup createTreeRoot(String title) {
		return new XLSGroup(title);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	private T createObjectFromRow(String row_key, TemplateNode data) {
		return ObjectManager.createXLSObjectFromRow(node_type, xls_row_map, row_key, data);
	}

//	-----------------------------------------------------------------------------------------------------------------------------------

	class XLSGroup extends ObjectGroup<T, D>
	{
		private static final long serialVersionUID = 1L;

		public XLSGroup(String name) {
			super(name, 
					ObjectTreeViewTemplateXLS.this.list_file,
					ObjectTreeViewTemplateXLS.this.node_type, 
					ObjectTreeViewTemplateXLS.this.data_type);
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
		protected XLSGroup pathCreateGroupNode(String name) {
			return new XLSGroup(name);
		}

		@Override
		public void onClicked(JTree tree, MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				new XLSGroupMenu(this).show(
						tree,
						e.getX(),
						e.getY());
			}
		}
	}
	
	
	class XLSGroupMenu extends GroupMenu
	{
		XLSGroup root;
		
		public XLSGroupMenu(XLSGroup root) {
			super(root, ObjectTreeViewTemplateXLS.this, getTree());
			this.root = root;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == delete) {
				if (root.getChildCount()>0) {
					JOptionPane.showMessageDialog(ObjectTreeViewTemplateXLS.this, "不能删除该节点，过滤器不为空！");
					return;
				}
			}
			super.actionPerformed(e);
		}
	}
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	

	
	
	
	
	
	
	
	
	
	
}
