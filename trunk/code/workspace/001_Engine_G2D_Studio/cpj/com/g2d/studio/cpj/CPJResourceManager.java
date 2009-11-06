package com.g2d.studio.cpj;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.ScrollPane;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.g2d.SimpleCanvasNoInternal;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.editor.DisplayObjectViewer;
import com.g2d.studio.Config;
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
	
	DefaultMutableTreeNode unit_root;
	DefaultMutableTreeNode avatar_root;
	DefaultMutableTreeNode effect_root;
	DefaultMutableTreeNode scene_root;
	
	public CPJResourceManager() 
	{
		super.setSize(800, 600);
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

}
