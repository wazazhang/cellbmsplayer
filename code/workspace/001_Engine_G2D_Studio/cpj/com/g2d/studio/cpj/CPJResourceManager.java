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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.SimpleCanvasNoInternal;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.Config;
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

public class CPJResourceManager extends AbstractFrame
{
	private static final long serialVersionUID = 1L;

//	----------------------------------------------------------------------------------------------------------------------------

	CPJSpriteViewer sprite_viewer = new CPJSpriteViewer();
	
	DefaultMutableTreeNode unit_root;
	DefaultMutableTreeNode avatar_root;
	DefaultMutableTreeNode effect_root;
	DefaultMutableTreeNode scene_root;
	
	public CPJResourceManager(ProgressForm progress) 
	{
		super.setSize(800, Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setTitle("资源管理器");
		super.setIconImage(Res.icon_edit);
		
		String path = Studio.getInstance().project_path.getPath();
		
		JTabbedPane table = new JTabbedPane();
		// actors
		{
			unit_root = new DefaultMutableTreeNode("单位模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_CHARACTER, 
					Config.RES_ACTOR_, 
					Config.RES_ACTOR_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				unit_root.add(file);
			}
			G2DTree tree = new G2DTree(unit_root);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), scroll);
		}
		// avatars
		{
			avatar_root = new DefaultMutableTreeNode("AVATAR模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_AVATAR, 
					Config.RES_AVATAR_, 
					Config.RES_AVATAR_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				avatar_root.add(file);
			}
			G2DTree tree = new G2DTree(avatar_root);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), scroll);
		}
		// effect
		{
			effect_root = new DefaultMutableTreeNode("特效模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_EFFECT, 
					Config.RES_EFFECT_, 
					Config.RES_EFFECT_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				effect_root.add(file);
			}
			G2DTree tree = new G2DTree(effect_root);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("魔法效果/特效", Tools.createIcon(Res.icon_res_3), scroll);
		}
		// scenes
		{
			scene_root = new DefaultMutableTreeNode("场景模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_SCENE, 
					Config.RES_SCENE_, 
					Config.RES_SCENE_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllWorld();
				scene_root.add(file);
			}
			G2DTree tree = new G2DTree(scene_root);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("场景", Tools.createIcon(Res.icon_res_1), scroll);
		}

		this.add(table, BorderLayout.CENTER);
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

//	----------------------------------------------------------------------------------------------------------------------------
	
	public CPJSpriteViewer getSpriteViewer() {
		return sprite_viewer;
	}
	
	public class CPJSpriteViewer extends JFrame
	{
		private static final long serialVersionUID = 1L;
		
		DisplayObjectPanel viewer = new DisplayObjectPanel();
		
		CPJSpriteViewer() {
			super.setSize(400, 400);
			add(viewer);
		}
		
		public DisplayObjectPanel getViewer() {
			return viewer;
		}
	}
	
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
	
	public class CPJResourceList<T extends CPJObject<?>> extends JScrollPane implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		
		Hashtable<JToggleButton, T> icons = new Hashtable<JToggleButton, T>();
		ButtonGroup button_group = new ButtonGroup();
		final Class<T> type;
		T selected;
		JLabel selected_info = new JLabel();
		
		
		@SuppressWarnings("unchecked")
		CPJResourceList(Class<T> type, DefaultMutableTreeNode root) 
		{
			this.type = type;
			int count = 0;
			int wcount = 12;

			JPanel panel = new JPanel();
			Enumeration<G2DTreeNode<?>> childs = root.children();
			while (childs.hasMoreElements()) {
				G2DTreeNode<?> cpj = childs.nextElement();
				for (G2DTreeNode<?> set : cpj.getChilds()) {
					if (type.isInstance(set)) {
						T t = type.cast(set);
						ImageIcon imgicon = t.getIcon(true);
						JToggleButton icon = new JToggleButton(imgicon);
						icon.setToolTipText(t.getName());
						icon.setMinimumSize(new Dimension(
								imgicon.getIconWidth()+4, 
								imgicon.getIconHeight()+4));
						icon.addActionListener(this);
						icons.put(icon, t);
						button_group.add(icon);
						panel.add(icon);
						count ++;
					}
				}
			}
			panel.setLayout(new GridLayout(count/wcount+1, wcount, 4, 4));
			panel.setMinimumSize(panel.getSize());
			this.setViewportView(panel);
			this.setAutoscrolls(true);
		}
		
		public T getSelectedObject() {
			return selected;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			selected = icons.get(e.getSource());
			if (selected!=null) {
				selected_info.setText(selected.getName());
			}
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
}
