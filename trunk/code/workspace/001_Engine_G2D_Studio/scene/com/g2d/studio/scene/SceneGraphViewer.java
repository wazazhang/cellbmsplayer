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
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.cell.CUtil;
import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorTransport;
import com.cell.rpg.scene.graph.SceneGraph;
import com.cell.rpg.scene.graph.SceneGraphNode;
import com.cell.rpg.scene.graph.SceneGraphNode.LinkInfo;
import com.cell.util.Pair;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.InteractiveObject;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.Container;
import com.g2d.display.ui.Panel;
import com.g2d.display.ui.UIComponent;
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
		public SceneGraphStage(SceneGraph sg) {
			addChild(new SceneGraphPanel(sg));
		}
		public void added(com.g2d.display.DisplayObjectContainer parent) {
			getRoot().setFPS(Config.DEFAULT_FPS);
		}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {
			g.setColor(Color.BLACK);
			g.fill(local_bounds);
			g.setColor(Color.WHITE);
			Drawing.drawString(g, "fps="+getRoot().getFPS(), 1, 1);
		}
		public void update() {}
	}
	
	static class SceneGraphPanel extends Panel
	{
		Point2D.Double	pre_right_pos;
		Point2D.Double	pre_right_camera_pos;
		
		HashMap<Integer, SceneFrame> all_nodes = new HashMap<Integer, SceneFrame>();
		
		public SceneGraphPanel(SceneGraph sg) {
			super.setContainer(new ScenePanelContainer());
			for (SceneGraphNode node : sg.getAllNodes()) {
				SceneFrame scene_frame = new SceneFrame(node);
				all_nodes.put(node.scene_id, scene_frame);
				this.getContainer().addChild(scene_frame);
			}
			for (SceneFrame sf : all_nodes.values()) {
				for (LinkInfo link : sf.node.next_links.values()) {
					sf.addNext(all_nodes.get(link.next_scene_id), link);
				}
			}
		}
		
		@Override
		protected void updateChilds()
		{
			if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos 			= new Point2D.Double(getMouseX(), getMouseY());
				pre_right_camera_pos 	= new Point2D.Double(getHScrollBar().getValue(), getVScrollBar().getValue());
			}
			else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) 
			{
				if (pre_right_pos != null) 
				{
					double dx = pre_right_camera_pos.x + pre_right_pos.x - getMouseX();
					double dy = pre_right_camera_pos.y + pre_right_pos.y - getMouseY();
					
					double ox = dx + getHScrollBar().getValueLength() - getHScrollBar().getMax();
					double oy = dy + getVScrollBar().getValueLength() - getVScrollBar().getMax();
					
					if (ox > 0) {
						getContainer().local_bounds.width += ox;
						getHScrollBar().setMax(getContainer().local_bounds.width);
					}
					if (oy > 0) {
						getContainer().local_bounds.height += oy;
						getVScrollBar().setMax(getContainer().local_bounds.height);
					}
					
					getHScrollBar().setValue(dx);
					getVScrollBar().setValue(dy);
				}
			}
			else if (getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos = null;
			}
			super.updateChilds();
		}
		
		public void render(Graphics2D g) {
			super.setSize(getParent().getWidth(), getParent().getHeight());
		}
		
		class ScenePanelContainer extends PanelContainer
		{
			public void update() {
				int mx = getWidth();
				int my = getHeight();
				for (SceneFrame sf : all_nodes.values()) {
					mx = (int)Math.max(mx, sf.x + sf.getWidth() + 50);
					my = (int)Math.max(my, sf.y + sf.getHeight() + 50);
				}
				setSize(mx, my);
			}
			
			
		}
		
//		-------------------------------------------------------------------------------------------------------------
		class SceneFrame extends Sprite
		{
			SceneGraphNode	node ;
			
			SceneNode 		snode;
			Image			snapshot;
			
			HashMap<String, LinkTP>
							tp_map;
			
			ArrayList<Pair<LinkTP, Pair<SceneFrame, LinkTP>>> 
							nexts;

			double			scalex, scaley;
			
			public SceneFrame(SceneGraphNode node) 
			{
				this.node 			= node;
				
				this.snode 			= Studio.getInstance().getSceneManager().getSceneNode(node.scene_id);
				
				this.snapshot		= snode.getIcon(false).getImage();
				
				this.setLocation(node.x, node.y);
				this.setSize(
						snapshot.getWidth(this) + 4, 
						snapshot.getHeight(this) + 24);

				this.scalex			= snapshot.getWidth(this) / node.width;
				this.scaley			= snapshot.getHeight(this) / node.height;

				this.enable 		= true;
				this.enable_input	= true;
				this.enable_focus	= true;
				this.enable_drag	= true;

				
				this.tp_map			= new HashMap<String, LinkTP>(node.getNexts().size());
				
				for (SceneUnit unit : snode.getData().scene_units) {
					ActorTransport tp = unit.getAbility(ActorTransport.class);
					if (tp!=null) {
						LinkTP v = new LinkTP(unit, tp);
						tp_map.put(unit.id, v);
						this.addChild(v);
					}
				}

				this.nexts 			= new ArrayList<Pair<LinkTP, Pair<SceneFrame, LinkTP>>>(node.getNexts().size());
				
				
			}

			private void addNext(SceneFrame next_sf, LinkInfo link) 
			{
				nexts.add(new Pair<LinkTP, Pair<SceneFrame, LinkTP>>(
						tp_map.get(link.this_scene_obj_name), 
						new Pair<SceneFrame, LinkTP>(
								next_sf, 
								next_sf.tp_map.get(link.next_scene_obj_name))));
			}
			
			@Override
			public void update() 
			{
				if (this.x < 0)this.x = 0;
				if (this.y < 0)this.y = 0;
				
				snode.getData().scene_node.x = (float)x;
				snode.getData().scene_node.y = (float)y;
			}
			
			
			
			@Override
			public void render(Graphics2D g) 
			{
				g.setColor(Color.BLACK);
				g.fill(local_bounds);
				g.drawImage(snapshot, 
						local_bounds.x + 2, 
						local_bounds.y + 2, 
						null);
				
				// color refer
				{
					if (isPickedMouse()) {
						getStage().setTip(node.scene_name + "(" + node.scene_id + ")");
						g.setColor(Color.WHITE);
					} else {
						g.setColor(Color.RED);
					}
					for (Pair<LinkTP, Pair<SceneFrame, LinkTP>> next : nexts) {
						int sx = (int)next.getKey().getVectorX();
						int sy = (int)next.getKey().getVectorY();
						int dx = (int)((next.getValue().getKey().x - x) + next.getValue().getValue().getVectorX());
						int dy = (int)((next.getValue().getKey().y - y) + next.getValue().getValue().getVectorY());
						g.drawLine(sx, sy, dx, dy);
					}
					g.draw(local_bounds);
					
					Drawing.drawString(g, "场景(" + node.scene_id + ")", 
							local_bounds.x + 2, 
							local_bounds.y + local_bounds.height-18);
				}
			}
			
//			-------------------------------------------------------------------------------------------------------------
			
			class LinkTP extends Sprite
			{
				ActorTransport 	tp;
				SceneUnit 		unit;
				
				public LinkTP(SceneUnit unit, ActorTransport tp) {
					this.unit 			= unit;
					this.tp				= tp;
					this.setVectorX(2 + unit.x * scalex);
					this.setVectorY(2 + unit.y * scaley);
					this.setSize(6, 6);	
					this.local_bounds.x = -local_bounds.width / 2;
					this.local_bounds.y = -local_bounds.height / 2;
					this.enable 		= true;
					this.enable_input	= true;
					this.enable_focus	= true;
				}
				
				@Override
				public void render(Graphics2D g) {
					if (isPickedMouse()) {
						getStage().setTip(unit.id + " -> " + tp.next_scene_id + ":" + tp.next_scene_object_id);
						g.setColor(Color.WHITE);
					} else {
						g.setColor(Color.GREEN);
					}
					g.drawRect(this.local_bounds.x, this.local_bounds.y, this.local_bounds.width-1, this.local_bounds.height-1);
				}
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
