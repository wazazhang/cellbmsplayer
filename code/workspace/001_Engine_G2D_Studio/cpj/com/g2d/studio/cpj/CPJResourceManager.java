package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.g2d.SimpleCanvasNoInternal;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.Config;
import com.g2d.studio.ManagerForm;
import com.g2d.studio.Studio.ProgressForm;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.cpj.entity.CPJSprite;
import com.g2d.studio.cpj.entity.CPJWorld;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.res.Res;
import com.g2d.util.AbstractFrame;

public class CPJResourceManager extends ManagerForm implements MouseListener
{
	private static final long serialVersionUID = 1L;

//	----------------------------------------------------------------------------------------------------------------------------

//	CPJSpriteViewer sprite_viewer = new CPJSpriteViewer();
	
	DefaultMutableTreeNode unit_root;
	DefaultMutableTreeNode avatar_root;
	DefaultMutableTreeNode effect_root;
	DefaultMutableTreeNode scene_root;
	
	public CPJResourceManager(Studio studio, ProgressForm progress) 
	{
		super(studio, progress, "资源管理器");
		
		String path = Studio.getInstance().project_path.getPath();
		
		JTabbedPane table = new JTabbedPane();
		// actors
		{
			unit_root = new DefaultMutableTreeNode("单位模板");
			ArrayList<CPJFile> files = CPJFile.listFile(path, Config.RES_ACTOR_ROOT, CPJResourceType.ACTOR);
			for (CPJFile file : files) {
				unit_root.add(file);
			}
			G2DTree tree = new G2DTree(unit_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), scroll);
		}
		// avatars
		{
			avatar_root = new DefaultMutableTreeNode("AVATAR模板");
			ArrayList<CPJFile> files = CPJFile.listFile(path, Config.RES_AVATAR_ROOT, CPJResourceType.AVATAR);
			for (CPJFile file : files) {
				avatar_root.add(file);
			}
			G2DTree tree = new G2DTree(avatar_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), scroll);
		}
		// effect
		{
			effect_root = new DefaultMutableTreeNode("特效模板");
			ArrayList<CPJFile> files = CPJFile.listFile(path, Config.RES_EFFECT_ROOT, CPJResourceType.EFFECT);
			for (CPJFile file : files) {
				effect_root.add(file);
			}
			G2DTree tree = new G2DTree(effect_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("魔法效果/特效", Tools.createIcon(Res.icon_res_3), scroll);
		}
		// scenes
		{
			scene_root = new DefaultMutableTreeNode("场景模板");
			ArrayList<CPJFile> files = CPJFile.listFile(path, Config.RES_SCENE_ROOT, CPJResourceType.WORLD);
			for (CPJFile file : files) {
				scene_root.add(file);
			}
			G2DTree tree = new G2DTree(scene_root);
			tree.addMouseListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("场景", Tools.createIcon(Res.icon_res_1), scroll);
		}
		
		this.add(table, BorderLayout.CENTER);
		
		saveAll();
	}
	
	public <T extends CPJObject<?>> CPJIndex<T> getNodeIndex(T node)
	{
		return new CPJIndex<T>(node.res_type, node.parent.getName(), node.getName());
	}
	
	public <T extends CPJObject<?>> CPJIndex<T> getNode(CPJResourceType type, String cpj, String set)
	{
		CPJIndex<T> index = new CPJIndex<T>(type, cpj, set);
		getNode(index);
		if (index.object!=null) {
			return index;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends CPJObject<?>> T getNode(CPJIndex<T> index)
	{
		try{
			G2DTreeNode<?> node = null;
			switch(index.res_type)
			{
			case ACTOR:
				node = G2DTree.getNode(unit_root, index.cpj_file_name, index.set_object_name);
				break;
			case EFFECT:
				node = G2DTree.getNode(effect_root, index.cpj_file_name, index.set_object_name);
				break;
			case WORLD:
				node = G2DTree.getNode(scene_root, index.cpj_file_name, index.set_object_name);
				break;
			case AVATAR:
				node = G2DTree.getNode(avatar_root, index.cpj_file_name, index.set_object_name);
				break;
			}
			if (node != null) {
				index.object = (T)node;
				return (T)node;
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return null;	
	}
	
	public <T extends CPJObject<?>> Vector<T> getNodes(CPJResourceType type, Class<T> clazz)
	{
		switch(type)
		{
		case ACTOR:
			return G2DTree.getNodesSubClass(unit_root, clazz);
		case EFFECT:
			return G2DTree.getNodesSubClass(effect_root, clazz);
		case WORLD:
			return G2DTree.getNodesSubClass(scene_root, clazz);
		case AVATAR:
			return G2DTree.getNodesSubClass(avatar_root, clazz);
		}
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
//	public CPJSpriteViewer getSpriteViewer() {
//		return sprite_viewer;
//	}
//	
//	public class CPJSpriteViewer extends JFrame
//	{
//		private static final long serialVersionUID = 1L;
//		
//		DisplayObjectPanel viewer = new DisplayObjectPanel();
//		
//		CPJSpriteViewer() {
//			super.setSize(400, 400);
//			add(viewer);
//		}
//		
//		public DisplayObjectPanel getViewer() {
//			return viewer;
//		}
//	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (e.getSource() instanceof G2DTree) {
				G2DTree tree = (G2DTree)e.getSource();
				try{
					DefaultMutableTreeNode root = (DefaultMutableTreeNode)tree.getModel().getRoot();
					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (tree.getSelectedNode() == root &&
						path.getLastPathComponent() == root) {
						new RootMenu(tree, root).show(tree, e.getX(), e.getY());
					}
				}catch(Exception err){}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

//	----------------------------------------------------------------------------------------------------------------------------

	public CPJResourceList<?> createObjectsPanel(CPJResourceType type)
	{
		switch(type)
		{
		case ACTOR:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, unit_root);
		case EFFECT:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, effect_root);
		case WORLD:
			return new CPJResourceList<CPJWorld>(CPJWorld.class, scene_root);
		case AVATAR:
			return new CPJResourceList<CPJSprite>(CPJSprite.class, avatar_root);
		}
		return null;
	}
	

//	----------------------------------------------------------------------------------------------------------------------------
	
	private String getList(Vector<? extends CPJObject<?>> objs) {
		StringBuffer list = new StringBuffer();
		for (CPJObject<?> spr : objs){
			list.append(
					spr.parent.getResourcePath() + ";" +
					spr.parent.getName() + ";" + 
					spr.getName() + "\n");
		}
		return list.toString();
	}
	
	public void saveAll()
	{
		File save_dir = new File(Studio.getInstance().project_save_path.getPath() + File.separatorChar +"resources");
		save_dir.mkdirs();
		
		String actor_list = getList(G2DTree.getNodesSubClass(unit_root, CPJSprite.class));
		com.cell.io.CFile.writeText(new File(save_dir, "actor_list.list"), actor_list, "UTF-8");
		
		String effect_list = getList(G2DTree.getNodesSubClass(effect_root, CPJSprite.class));
		com.cell.io.CFile.writeText(new File(save_dir, "effect_list.list"), effect_list, "UTF-8");
		
		String avatar_list = getList(G2DTree.getNodesSubClass(avatar_root, CPJSprite.class));
		com.cell.io.CFile.writeText(new File(save_dir, "avatar_list.list"), avatar_list, "UTF-8");
		
		String scene_list = getList(G2DTree.getNodesSubClass(scene_root, CPJWorld.class));
		com.cell.io.CFile.writeText(new File(save_dir, "scene_list.list"), scene_list, "UTF-8");

	}

//	----------------------------------------------------------------------------------------------------------------------------
	
	class RootMenu extends JPopupMenu implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		G2DTree tree;
		DefaultMutableTreeNode root;
		
		JMenuItem	refresh_all		= new JMenuItem("刷新所有");
		JMenuItem	build_all		= new JMenuItem("编译所有");
		
		public RootMenu(G2DTree tree, DefaultMutableTreeNode root) {
			this.tree = tree;
			this.root = root;
			refresh_all.addActionListener(this);
			build_all.addActionListener(this);
			this.add(refresh_all);
			this.add(build_all);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == refresh_all) {
				Enumeration<?> childs = root.children();
				while (childs.hasMoreElements()) {
					Object obj = childs.nextElement();
					if (obj instanceof CPJFile) {
						CPJFile file = (CPJFile)obj;
						file.refresh();
					}
				}
				tree.reload(root);
			}
			else if (e.getSource() == build_all) {
				Enumeration<?> childs = root.children();
				while (childs.hasMoreElements()) {
					Object obj = childs.nextElement();
					if (obj instanceof CPJFile) {
						CPJFile file = (CPJFile)obj;
						file.rebuild();
					}
				}
			}
		}
	}
	
	

//	----------------------------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
