package com.g2d.studio.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import com.cell.CObject;
import com.cell.rpg.entity.Actor;
import com.cell.rpg.entity.Region;
import com.cell.rpg.entity.Unit;
import com.cell.rpg.io.Util;
import com.g2d.cell.game.Scene;
import com.g2d.cell.game.SceneUnit;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Sprite;
import com.g2d.display.Stage;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.studio.AFormDisplayObjectViewer;
import com.g2d.studio.ATreeNodeLeaf;
import com.g2d.studio.Studio;
import com.g2d.studio.Version;
import com.g2d.studio.Studio.SetResource;
import com.g2d.studio.actor.FormActorViewer;
import com.g2d.studio.actor.ResActorBox;
import com.g2d.studio.swing.SceneUnitPanel;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Drawing;
public class FormSceneViewer extends AFormDisplayObjectViewer<Scene> 
{
	private static final long serialVersionUID = Version.VersionGS;
	
	
	static class LocationCursor extends Sprite
	{
		public double target_x;
		public double target_y;
		
		public void render(Graphics2D g) {
			priority = Integer.MAX_VALUE;
			g.setColor(Color.GREEN);
			g.fillRect(-10, -10, 20, 20);
			int dx = (int)target_x - getX();
			int dy = (int)target_y - getY();
			g.drawLine(0, 0, dx, dy);
			Drawing.drawStringBorder(g, getX() + "," + getY(), 0, -12, Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_BOTTON);
		}
	};
	
	LocationCursor location = new LocationCursor();
	
	class SceneStage extends Stage
	{
		public SceneStage() {
			addChild(view_object);
			view_object.getWorld().addChild(location);
		}
		
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {}
		
		
		protected void afterRender(Graphics2D g)
		{
			AffineTransform trans = g.getTransform();
			
			if (tool_addactor.isSelected()) 
			{
				if (isSelectedActorBox()) 
				{
					FormActorViewer actorv = studio.getSelectedNodeAsActor();
					if (actorv!=null) 
					{
						g.translate(mouse_x, mouse_y);
						actorv.getViewObject().render(g);
						g.setColor(Color.WHITE);
						Drawing.drawStringBorder(g, 
								actorv.getViewObject().getSprite().getCurrentAnimate() + "/" + actorv.getViewObject().getSprite().getAnimateCount(), 
								0, actorv.getViewObject().getSprite().getVisibleBotton() + 1, 
								Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP
								);
					}
				}
				else if (isSelectedRegionBox())
				{
					if (add_region_sp!=null) 
					{
						g.translate(-view_object.getCameraX(), -view_object.getCameraY());
						
						int sx = Math.min(add_region_sp.x, add_region_dp.x);
						int sy = Math.min(add_region_sp.y, add_region_dp.y);
						int sw = Math.abs(add_region_sp.x- add_region_dp.x);
						int sh = Math.abs(add_region_sp.y- add_region_dp.y);
						g.setColor(new Color(0x8000ff00,true));
						g.fillRect(sx, sy, sw, sh);
					}
				}
				
			}
			
			g.setTransform(trans);
		}
		
		public void update()
		{
			int worldx = view_object.getWorld().getMouseX();
			int worldy = view_object.getWorld().getMouseY();
			
			if (getRoot().isMouseDown(MouseEvent.BUTTON3)) {
				//group.showOverview();
			}
			
			// locate camera
			{
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) 
				{
					location.target_x = worldx;
					location.target_y = worldy;
				}
				location.moveTo(location.target_x, location.target_y, 32);
				view_object.locateCameraCenter(location.getX(), location.getY());
			}
			
			// 选择单位 -----------------------------------------------------------
			if (tool_selector.isSelected())
			{
				
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					try
					{
						selected_unit = view_object.getWorld().getChildAtPos(worldx, worldy, SceneActor.class);
						
						if (selected_unit==null) {
							selected_unit = view_object.getWorld().getChildAtPos(worldx, worldy, SceneRegion.class);
							if (selected_unit!=null) {
								tb_regions.setSelecte((SceneRegion)selected_unit);
							}
						}else{
							tb_actors.setSelecte((SceneActor)selected_unit);
						}
						
						if (getRoot().isMouseDoubleDown(500L, com.g2d.display.event.MouseEvent.BUTTON_LEFT)) {
							if (selected_unit!=null) {
								DisplayObjectEditor<?> editor = selected_unit.getEditorForm();
								editor.setCenter();
								editor.setAlwaysOnTop(true);
								editor.setVisible(true);
							}
						}
						
					}catch (Throwable e) {}
				}
				else if (getRoot().isKeyDown(java.awt.event.KeyEvent.VK_DELETE)) // 删除单位
				{
					if (selected_unit != null)
					{
						view_object.getWorld().removeChild(selected_unit);
						view_object.getWorld().processEvent();
						
						tb_actors.repaint(500);
						tb_regions.repaint(500);
					}
				}
				// ctrl + c 复制名字
				else if (getRoot().isKeyHold(KeyEvent.VK_CONTROL) && getRoot().isKeyDown(KeyEvent.VK_C)) 
				{
					if (selected_unit != null) {
						CObject.AppBridge.setClipboardText(selected_unit.getName());
					}
				}
			}
			
			// 添加单位 -----------------------------------------------------------
			else if (tool_addactor.isSelected()) 
			{
				if (isSelectedActorBox())// 添加单位
				{
					FormActorViewer actorv = studio.getSelectedNodeAsActor();
					
					if (actorv!=null)
					{
						if (getRoot().isMouseWheelUP()) {
							actorv.getViewObject().getSprite().nextAnimate(-1);
						}
						if (getRoot().isMouseWheelDown()) {
							actorv.getViewObject().getSprite().nextAnimate(1);
						}

						if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
						{
							SceneActor spr = new SceneActor(actorv, FormSceneViewer.this);
							spr.setLocation(worldx, worldy);
							view_object.getWorld().addChild(spr);
							view_object.getWorld().processEvent();
							
							tb_actors.repaint(500);
						}
					}
					
					add_region_sp = null;
				}
				else if (isSelectedRegionBox())// 添加区域
				{
					add_region_dp.x = worldx; 
					add_region_dp.y = worldy;
					
					if (getRoot().isMouseDown(MouseEvent.BUTTON1)) 
					{
						add_region_sp = new Point();
						add_region_sp.x = worldx; 
						add_region_sp.y = worldy;
					}
					else if (getRoot().isMouseUp(MouseEvent.BUTTON1)) 
					{
						if (add_region_sp!=null)
						{
							int sx = Math.min(add_region_sp.x, add_region_dp.x);
							int sy = Math.min(add_region_sp.y, add_region_dp.y);
							int sw = Math.abs(add_region_sp.x- add_region_dp.x);
							int sh = Math.abs(add_region_sp.y- add_region_dp.y);
							
							if (sw>0 && sh>0)
							{
								SceneRegion region = new SceneRegion(new Rectangle(0, 0, sw, sh), FormSceneViewer.this);
								region.setLocation(sx, sy);
								view_object.getWorld().addChild(region);
								view_object.getWorld().processEvent();
								
								tb_regions.repaint(500);
							}
						}
						
						add_region_sp = null;
					}
				
				}
			
				
			}
			
			
			
			
		}
		
	}
//	-------------------
	
//
	final SetResource set_resource;
	
	
//	ui compontent
	Point			add_region_sp	= null;
	Point			add_region_dp	= new Point();
	
	public SceneUnit	selected_unit	= null;
//
	JSplitPane		split_h			= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//	
	JToggleButton	tool_selector	= new JToggleButton("选择", true);
	JToggleButton	tool_addactor	= new JToggleButton("添加");
	
//	
	JTabbedPane 				tabel			=	new JTabbedPane();
	ResActorBox					tb_actor_res;
	SceneUnitPanel<SceneActor>	tb_actors;
	SceneUnitPanel<SceneRegion>	tb_regions;
//	PanelTerrain				tb_terrain;
	
	
	public FormSceneViewer(ATreeNodeLeaf<FormSceneViewer> leaf, SetResource set, String worldID)
	{
		super(leaf, new Scene(set, worldID));
		
		set_resource = set;
		
		
		ButtonGroup 	tool_group 	= new ButtonGroup();
		{
			super.addToolButton(tool_selector, "select", 				tool_group);
			super.addToolButton(tool_addactor, "add an unit to scene", 	tool_group);
		}

//		table
		{
			// actors
			{
				tb_actor_res 	= new ResActorBox(studio.group_actor);
				tb_actors		= new SceneUnitPanel<SceneActor>(this, SceneActor.class);
				JSplitPane unit_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tb_actor_res, tb_actors);
				tabel.add("unit", unit_split);
			}
			
			// region
			{
				tb_regions		= new SceneUnitPanel<SceneRegion>(this, SceneRegion.class);
				tabel.add("region",	tb_regions);
			}
			
			// terrain
			{
//				tb_terrain		= new PanelTerrain();
//				tabel.add("terrain",tb_terrain);
			}
			
			split_h.setLeftComponent(tabel);
		}
		
		
		{
			this.remove(canvas);
			
			canvas.getCanvasAdapter().setStage(new SceneStage());
			
			canvas.addComponentListener(new ComponentListener(){
				public void componentResized(ComponentEvent e) {
					view_object.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
				}
				public void componentHidden(ComponentEvent e) {}
				public void componentMoved(ComponentEvent e) {}
				public void componentShown(ComponentEvent e) {}
			});

			split_h.setRightComponent(canvas);
		}
	
		this.add(split_h, BorderLayout.CENTER);
		
		this.setTitle("scene " + worldID);
		this.setSize(
				AbstractFrame.getScreenWidth() - Studio.getInstance().getWidth(),
				Studio.getInstance().getHeight());
		
	}
	
	public void locationCamera(int x, int y) {
		location.target_x = x;
		location.target_y = y;
	}
	
	public boolean isSelectedActorBox(){
		return tabel.getSelectedIndex() == 0;
	}
	
	public boolean isSelectedRegionBox() {
		return tabel.getSelectedIndex() == 1;
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b){
			set_resource.initAllStreamImages();
		}else{
			set_resource.destoryAllStreamImages();
		}
	}
	
	
//	-------------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void loadObject(ObjectInputStream ois) throws IOException, ClassNotFoundException 
	{
		if (view_object!=null) 
		{
			Collection<Unit>	tobjects	= Util.readObjects(ois);
			
			for (Unit obj : tobjects)
			{
				try {
					if (obj instanceof Actor) {
						view_object.getWorld().addChild(
								new SceneActor((Actor) obj, this));
					} else if (obj instanceof Region) {
						view_object.getWorld().addChild(
								new SceneRegion((Region) obj, this));
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(this.getCpjObjectID() + " : " + obj.name);
				}
			}
			
		}
	}
	
	@Override
	public void saveObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException 
	{
		if (view_object!=null) 
		{
			Vector<Unit>		tobjects		= new Vector<Unit>();
			
			for (SceneActor actor : view_object.getWorld().getChilds(SceneActor.class)) {
				tobjects.add(actor.onWrite());
			}
			for (SceneRegion region : view_object.getWorld().getChilds(SceneRegion.class)) {
				tobjects.add(region.onWrite());
			}
			
			Util.wirteObjects(tobjects, oos);
		
		}
	}
	
	
	
	

	
}
