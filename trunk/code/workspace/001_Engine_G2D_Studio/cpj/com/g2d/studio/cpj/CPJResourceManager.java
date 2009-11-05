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
import com.g2d.studio.Studio;
import com.g2d.studio.cpj.entity.CPJFile;
import com.g2d.studio.cpj.entity.CPJObject;
import com.g2d.studio.res.Res;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.AbstractFrame;

public class CPJResourceManager extends AbstractFrame implements TreeSelectionListener
{
	private static final long serialVersionUID = 1L;

//	----------------------------------------------------------------------------------------------------------------------------
	
	ObjectViewer object_viewer;
	
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
			DefaultMutableTreeNode unit_root = new DefaultMutableTreeNode("单位模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_CHARACTER, 
					Config.RES_ACTOR_, 
					Config.RES_ACTOR_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				unit_root.add(file);
			}
			G2DTree tree = new G2DTree(unit_root);
			tree.addTreeSelectionListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("单位", Tools.createIcon(Res.icon_res_2), scroll);
		}
		// avatars
		{
			DefaultMutableTreeNode avatar_root = new DefaultMutableTreeNode("AVATAR模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_AVATAR, 
					Config.RES_AVATAR_, 
					Config.RES_AVATAR_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				avatar_root.add(file);
			}
			G2DTree tree = new G2DTree(avatar_root);
			tree.addTreeSelectionListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("AVATAR", Tools.createIcon(Res.icon_res_4), scroll);
		}
		// effect
		{
			DefaultMutableTreeNode effect_root = new DefaultMutableTreeNode("特效模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_EFFECT, 
					Config.RES_EFFECT_, 
					Config.RES_EFFECT_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllSprite();
				effect_root.add(file);
			}
			G2DTree tree = new G2DTree(effect_root);
			tree.addTreeSelectionListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("魔法效果/特效", Tools.createIcon(Res.icon_res_3), scroll);
		}
		// scenes
		{
			DefaultMutableTreeNode scene_root = new DefaultMutableTreeNode("场景模板");
			ArrayList<CPJFile> files = CPJFile.listFile(
					path + "/" + Config.ROOT_SCENE, 
					Config.RES_SCENE_, 
					Config.RES_SCENE_OUTPUT);
			for (CPJFile file : files) {
				file.loadAllWorld();
				scene_root.add(file);
			}
			G2DTree tree = new G2DTree(scene_root);
			tree.addTreeSelectionListener(this);
			JScrollPane scroll = new JScrollPane(tree);	
			scroll.setVisible(true);
			table.addTab("场景", Tools.createIcon(Res.icon_res_1), scroll);
		}

//		object_viewer = new ObjectViewer();
//		JSplitPane split_res_viewer = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
//				table, object_viewer);
//		this.add(split_res_viewer, BorderLayout.CENTER);
//		this.addWindowListener(new WindowListener());
		
		this.add(table, BorderLayout.CENTER);
	}
	
	
	public void valueChanged(TreeSelectionEvent e) {
		if (object_viewer!=null) {
			Object value = e.getPath().getLastPathComponent();
			if (value instanceof CPJObject<?>){
				CPJObject<?> node = ((CPJObject<?>)value);
				object_viewer.clearCPJObject();
				object_viewer.addCPJObject(node);
				System.out.println("show object " + e);
			}
		}
	}

//	----------------------------------------------------------------------------------------------------------------------------
	
	class WindowListener extends WindowAdapter
	{
		@Override
		public void windowClosing(WindowEvent e) {
			object_viewer.stop();
		}
	}
	
	class ObjectViewer extends JPanel implements Runnable
	{
		private static final long serialVersionUID = 1L;
		
		final SimpleCanvasNoInternal canvas = new SimpleCanvasNoInternal(100, 100);

		public ObjectViewer() 
		{
			this.setLayout(new BorderLayout());
			this.canvas.getCanvasAdapter().setStage(new ObjectStage());
			this.add(canvas);
			this.setVisible(true);
			canvas.setVisible(true);	
			canvas.addComponentListener(new ComponentListener(){
				public void componentResized(ComponentEvent e) {
					canvas.getCanvasAdapter().setStageSize(canvas.getWidth(), canvas.getHeight());
				}
				public void componentHidden(ComponentEvent e) {}
				public void componentMoved(ComponentEvent e) {}
				public void componentShown(ComponentEvent e) {}
			});
			new Thread(this).start();
		}
		
		public void addCPJObject(CPJObject<?> object)
		{
			this.canvas.getCanvasAdapter().getStage().addChild(object.getDisplayObject());
		}
		
		public void clearCPJObject() 
		{
			this.canvas.getCanvasAdapter().getStage().clearChilds();
		}

		public void stop()
		{
			this.canvas.getCanvasAdapter().exit();
		}

		public void run()
		{
			while (!canvas.getCanvasAdapter().isExit())
			{
				canvas.getCanvasAdapter().repaint_game();
				//System.out.print("repaint");
				try {
					Thread.sleep(canvas.getCanvasAdapter().getFrameDelay());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		class ObjectStage extends Stage
		{
			@Override
			public void added(DisplayObjectContainer parent) {
				getRoot().setFPS(40);
			}
			@Override
			public void removed(DisplayObjectContainer parent) {}
			@Override
			public void render(Graphics2D g) {
				g.setColor(Color.GREEN);
				g.fill(local_bounds);
				
			}
			@Override
			public void update() {}
		}
	}
	
}
