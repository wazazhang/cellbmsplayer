package com.g2d.studio;

import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.util.AbstractFrame;

public abstract class ATreeNodeGroup <V extends AFormDisplayObjectViewer<?>> extends TreeNode<Object, DefaultMutableTreeNode, ATreeNodeSet<V>>
{
	final public Studio studio; 
	
	public ATreeNodeGroup(Studio studio, String name, String child_key)  throws Exception
	{	
		super(name, null, null);
		
		this.studio 	= studio;
		
		File dir 		= studio.createFile(name);
		
		// 枚举 character 文件夹
		for (File subfile : dir.listFiles())
		{
			// 枚举 actor_xxxxxx 文件夹
			if (subfile.isDirectory() && subfile.getName().startsWith(child_key)) 
			{
				try{
					ATreeNodeSet<V> subs = new ATreeNodeSet<V>(subfile.getName(), this);

					ATreeNodeLeaf<V>[] viewers = createViewers(subs);
					
					// 枚举 actor 里的所有精灵单位 or 场景中的 scene
					for (ATreeNodeLeaf<V> viewer : viewers){
						subs.addChild(viewer);
					}

					addChild(subs);
				}
				catch (Exception e) {
					System.err.println("load node error : " + subfile.getName());
					//e.printStackTrace();
				}
			}
		}
	}
	
	abstract public ATreeNodeLeaf<V>[] createViewers(ATreeNodeSet<V> parent) throws Exception;
	
	
//	-----------------------------------------------------------------------------------------------------------------------------------------
	

	
//	-----------------------------------------------------------------------------------------------------------------------------------------
	
	public void onParentDoubleClicked(JTree tree, ATreeNodeSet<?> parent, MouseEvent e) {}
	public void onParentRightClicked(JTree tree, ATreeNodeSet<?> parent, MouseEvent e) {}
	public void onParentSelected(JTree tree, ATreeNodeSet<?> parent, MouseEvent e) {}
	
	public void onDoubleClicked(JTree tree, MouseEvent e) {}
	public void onRightClicked(JTree tree, MouseEvent e) {}
	public void onSelected(JTree tree, MouseEvent e) {}
	
//	-----------------------------------------------------------------------------------------------------------------------------------------

	public V getNode(String parent, String leaf)
	{
		ATreeNodeSet<V> nparent = (ATreeNodeSet<V>)getChild(parent);
		if (parent!=null) {
			ATreeNodeLeaf<V> nleaf = (ATreeNodeLeaf<V>)nparent.getChild(leaf);
			return nleaf.user_data;
		}
		return null;
	}
	
	public void saveAll() 
	{
		for (ATreeNodeSet<V> group : this.childs) {
			for (ATreeNodeLeaf<V> view : group.childs) {
				((ATreeNodeLeaf<V>)view).user_data.save();
			}
		}
	}
	
	public void loadAll() 
	{
		for (ATreeNodeSet<V> group : this.childs) {
			for (ATreeNodeLeaf<V> view : group.childs) {
				if (((ATreeNodeLeaf<V>)view).user_data!=null) {
					((ATreeNodeLeaf<V>)view).user_data.load();
				}
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------------

}
