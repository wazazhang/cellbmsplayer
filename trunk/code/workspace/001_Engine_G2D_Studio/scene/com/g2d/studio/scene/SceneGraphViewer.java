package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.cell.CUtil;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.scene.graph.SceneGraphNode;
import com.cell.rpg.scene.graph.SceneGraphNode.LinkInfo;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.display.ui.Window;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.util.AbstractDialog;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Drawing;

public class SceneGraphViewer extends AbstractDialog
{
	DisplayObjectPanel display_object_panel = new DisplayObjectPanel();
	
	public SceneGraphViewer(Component owner) 
	{
		super(owner);
		super.setTitle("场景图查看器");
		super.setIconImage(Res.icon_scene_graph);
		super.setLocation(Studio.getInstance().getIconManager().getLocation());
		super.setSize(Studio.getInstance().getIconManager().getSize());
		super.add(display_object_panel, BorderLayout.CENTER);
		
		refreshSceneGraph(Studio.getInstance().getSceneManager().createSceneGraph());
	}
	
	public void refreshSceneGraph(SceneGraph sg)
	{
		SceneGraphStage stage = new SceneGraphStage(sg);
		display_object_panel.getCanvas().changeStage(stage);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			display_object_panel.start();
		} else {
			display_object_panel.stop();
		}
		super.setVisible(b);
	}
//	-------------------------------------------------------------------------------------------------------------
	

	static class SceneGraphStage extends Stage
	{
		HashMap<Integer, SceneFrame> all_nodes = new HashMap<Integer, SceneFrame>();
		
		public SceneGraphStage(SceneGraph sg) {
			for (SceneGraphNode node : sg.getAllNodes()) {
				SceneFrame scene_frame = new SceneFrame(node);
				all_nodes.put(node.scene_id, scene_frame);
				this.addChild(scene_frame);
			}
			for (SceneFrame sf : all_nodes.values()) {
				for (Integer next_id : sf.node.next_links.keySet()) {
					sf.nexts.add(all_nodes.get(next_id));
				}
			}
		}

		public void added(com.g2d.display.DisplayObjectContainer parent) {
			getRoot().setFPS(Config.DEFAULT_FPS);
		}

		public void removed(DisplayObjectContainer parent){}

		public void update()
		{
		}
		
		public void render(Graphics2D g) 
		{
			g.setColor(Color.BLACK);
			g.fill(local_bounds);
			g.setColor(Color.WHITE);
			Drawing.drawString(g, "fps="+getRoot().getFPS(), 1, 1);
		}
		
//		-------------------------------------------------------------------------------------------------------------
		class SceneFrame extends Sprite
		{
			Point2D.Double	pre_right_root_pos;		
			Point2D.Double	pre_right_camera_pos;

			SceneGraphNode	node ;
			
			SceneNode 		snode;
			Image			snapshot;
			
			ArrayList<SceneFrame> 
							nexts;
			
			public SceneFrame(SceneGraphNode node) 
			{
				this.node 			= node;
				
				this.snode 			= Studio.getInstance().getSceneManager().getSceneNode(node.scene_id);
				this.snapshot		= snode.getIcon(false).getImage();
				
				this.nexts 			= new ArrayList<SceneFrame>(node.getNexts().size());
				
				this.setLocation(node.x, node.y);
				this.setSize(
						snapshot.getWidth(this) + 4, 
						snapshot.getHeight(this) + 24);

				this.local_bounds.x = -this.local_bounds.width / 2;
				this.local_bounds.y = -this.local_bounds.height / 2;
				
				this.enable 		= true;
				this.enable_input	= true;
				this.enable_focus	= true;
				this.enable_drag	= true;
			}

			@Override
			public void update() 
			{
				double mx = getRoot().getMouseX();
				double my = getRoot().getMouseY();
				
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
					pre_right_root_pos 		= new Point2D.Double(mx, my);
					pre_right_camera_pos 	= new Point2D.Double(x, y);
				}
				else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
					if (pre_right_root_pos != null) {
						double dx = pre_right_root_pos.x - mx;
						double dy = pre_right_root_pos.y - my;
						x = pre_right_camera_pos.x-dx;
						y = pre_right_camera_pos.y-dy;
					}
				}
				else if (getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
					pre_right_root_pos = null;
				}
				
				snode.getData().scene_node.x = (float)x;
				snode.getData().scene_node.y = (float)y;
			}
			
			
			
			@Override
			public void render(Graphics2D g) 
			{
				if (isPickedMouse()) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.RED);
				}
				for (SceneFrame next : nexts) {
					int dx = (int)(next.x - x);
					int dy = (int)(next.y - y);
					g.drawLine(0, 0, dx, dy);
				}

				g.setColor(Color.BLACK);
				g.fill(local_bounds);
				
				g.drawImage(snapshot, 
						local_bounds.x + 2, 
						local_bounds.y + 2, 
						null);
				
				if (isPickedMouse()) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.RED);
				}
				g.draw(local_bounds);
				Drawing.drawString(g, "场景(" + node.scene_id + ")", 
						local_bounds.x + 2, 
						local_bounds.y + local_bounds.height-18);
			}
		}
	}
	

	
//	static class SceneGraphPanel extends JDesktopPane
//	{
//		HashMap<SceneFrame, SceneGraphNode> frames = new HashMap<SceneFrame, SceneGraphNode>();
//		
//		public SceneGraphPanel(SceneGraph sg) 
//		{
//			super.setAutoscrolls(true);
//			
//			for (SceneGraphNode node : sg.getAllNodes())
//			{
//				SceneFrame scene_frame = new SceneFrame(node);
//				this.add(scene_frame);
//				frames.put(scene_frame, node);
//			}
//		}
//		
//		private void refreshSize() {
//			int mx = 0;
//			int my = 0;
//			for (JInternalFrame scene_frame : frames.keySet()) {
//				int dx = scene_frame.getX() + scene_frame.getWidth();
//				int dy = scene_frame.getY() + scene_frame.getHeight();
//				mx = Math.max(mx, dx);
//				my = Math.max(my, dy);
//			}
//			Dimension size = new Dimension(mx, my);
//			this.setPreferredSize(size);
//			this.setMinimumSize(size);
//			this.setSize(size);
//		}
//		
//		class SceneFrame extends JInternalFrame implements MouseListener
//		{
//			public SceneFrame(SceneGraphNode node) 
//			{
//				super(node.scene_name+"(" + node.scene_id + ")", 
//						false, false, false, false);
//				
//				super.setLocation(CUtil.getRandom(0, 1000), CUtil.getRandom(0, 1000));
//				super.setSize(120, 120);
//				
////				super.setLocation((int)node.x, (int)node.y);
////				super.setSize(d)
//				
//				super.setVisible(true);
//				super.setAutoscrolls(true);
//				
//				super.addMouseListener(this);
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {}
//			@Override
//			public void mouseEntered(MouseEvent e) {}
//			@Override
//			public void mouseExited(MouseEvent e) {}
//			@Override
//			public void mousePressed(MouseEvent e) {}
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				refreshSize();
//			}
//
//		}
//		
//	
//	}
}
