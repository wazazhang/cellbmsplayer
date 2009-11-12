package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.game.CSprite;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.Region;
import com.cell.rpg.scene.SceneUnit;
import com.g2d.Tools;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResource.WorldSet.SpriteObject;
import com.g2d.cell.game.Scene;
import com.g2d.cell.game.Scene.WorldMap;
import com.g2d.cell.game.Scene.WorldObject;
import com.g2d.cell.game.ui.ScenePanel;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;
import com.g2d.display.ui.Menu;
import com.g2d.editor.DisplayObjectPanel;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.Studio;
import com.g2d.studio.StudioResource;
import com.g2d.studio.cpj.entity.CPJWorld;

import com.g2d.studio.res.Res;
import com.g2d.studio.scene.entity.SceneNode;
import com.g2d.studio.scene.units.SceneActor;
import com.g2d.studio.scene.units.ScenePoint;
import com.g2d.studio.scene.units.SceneRegion;
import com.g2d.studio.scene.units.SceneUnitTag;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.util.AbstractFrame;
import com.g2d.util.Drawing;

public class SceneEditor extends AbstractFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

//	--------------------------------------------------------------------------------------------------------------
//	game

	final SceneNode			scene_node;
	final CPJWorld			scene_world;
	final StudioResource	scene_resource;
	
	DisplayObjectPanel		display_object_panel;
	SceneStage				scene_stage;
	ScenePanel				scene_panel;
	SceneContainer			scene_container;
	SceneMiniMap			scene_mini_map;

//	--------------------------------------------------------------------------------------------------------------
//	ui
	
	private G2DWindowToolBar			tool_bar;
	private JToggleButton				tool_selector	= new JToggleButton(Tools.createIcon(Res.icons_bar[0]), true);
	private JToggleButton				tool_addactor	= new JToggleButton(Tools.createIcon(Res.icons_bar[8]));
	
	private JTabbedPane					unit_page;
	private SceneUnitList<SceneActor>	page_actors;
	private SceneUnitList<SceneRegion>	page_regions;
	private SceneUnitList<ScenePoint>	page_points;
	
	

//	--------------------------------------------------------------------------------------------------------------
//	transient
	private SceneUnitTag<?> v_selected_unit;
	
//	--------------------------------------------------------------------------------------------------------------
	
	public SceneEditor(SceneNode scene)
	{
		super.setSize(AbstractFrame.getScreenWidth()-Studio.getInstance().getWidth(), Studio.getInstance().getHeight());
		super.setLocation(Studio.getInstance().getX()+Studio.getInstance().getWidth(), Studio.getInstance().getY());
		super.setIconImage(Res.icon_edit);		
		super.setTitle("场景 : " + scene.getName() + " (" + scene.getID() + ")");
		
		this.scene_node		= scene;
		this.scene_world	= scene_node.getWorldDisplay();
		this.scene_resource	= scene_world.getParent().getSetResource();
		
		// tool bar
		{
			tool_bar = new G2DWindowToolBar(this);
			tool_selector.setToolTipText("选择");
			tool_addactor.setToolTipText("添加");
			tool_bar.add(tool_selector);
			tool_bar.add(tool_addactor);
			ButtonGroup button_group = new ButtonGroup();
			button_group.add(tool_selector);
			button_group.add(tool_addactor);
			
		}
		this.add(tool_bar, BorderLayout.NORTH);
		
		
		// g2d stage
		{
			display_object_panel	= new DisplayObjectPanel();
			scene_stage				= new SceneStage();
			scene_container			= new SceneContainer();
			scene_panel				= new ScenePanel(scene_container);
			scene_mini_map			= new SceneMiniMap();
			display_object_panel.getCanvas().changeStage(scene_stage);
			load();
		}
		
		JSplitPane split_h = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// left
		{
			JSplitPane split_v = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			// top
			{
				split_v.setTopComponent(scene_mini_map);
			} 
			// bottom
			{
				unit_page		= new JTabbedPane();
				page_actors		= new SceneUnitList<SceneActor>(this, SceneActor.class);
				page_regions	= new SceneUnitList<SceneRegion>(this, SceneRegion.class);
				page_points		= new SceneUnitList<ScenePoint>(this, ScenePoint.class);
				unit_page.addTab("单位", page_actors);
				unit_page.addTab("区域", page_regions);
				unit_page.addTab("路点", page_points);
				split_v.setBottomComponent(unit_page);
			}
			split_h.setLeftComponent(split_v);
		}
		// right
		{
			split_h.setRightComponent(display_object_panel);
		}
		
		this.add(split_h, BorderLayout.CENTER); 
		
	}
	
	@SuppressWarnings("unchecked")
	private void load()
	{
		if (scene_node.getData().scene_units!=null) {
			for (SceneUnit unit : scene_node.getData().scene_units) {
				SceneUnitTag<?> unit_tag = null;
				try{
					if (unit instanceof Actor) {
						unit_tag = new SceneActor(this, (Actor)unit);
					} 
					else if (unit instanceof Region) {
						unit_tag = new SceneRegion(this, (Region)unit);
					} 
					else if (unit instanceof com.cell.rpg.scene.Point) {
						unit_tag = new ScenePoint(this, (com.cell.rpg.scene.Point)unit);
					}
					if (unit_tag != null) {
						scene_container.getWorld().addChild(unit_tag.getGameUnit());
					}
				}catch(Throwable err){
					err.printStackTrace();
				}
			}
		}
		scene_container.getWorld().processEvent();
		
		Vector<SceneUnitTag> list = scene_container.getWorld().getChildsSubClass(SceneUnitTag.class);
		for (SceneUnitTag tag : list) {
			tag.onReadComplete(list);
		}
	}
		
	
	@SuppressWarnings("unchecked")
	private void save()
	{
		Vector<SceneUnitTag> list = scene_container.getWorld().getChildsSubClass(SceneUnitTag.class);
		for (SceneUnitTag tag : list) {
			tag.onWriteReady(list);
		}
		
		scene_node.getData().scene_units.clear();
		
		for (SceneUnitTag tag : list) {
			try{
				scene_node.getData().scene_units.add(tag.onWrite());
			}catch(Throwable err){
				err.printStackTrace();
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if (b) {
			display_object_panel.start();
			scene_resource.initAllStreamImages();
		} else {
			display_object_panel.stop();
			scene_resource.destoryAllStreamImages();
			if (scene_mini_map != null){
				scene_mini_map.killSnapshot();
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tool_bar.save) {
			save();
			Studio.getInstance().getSceneManager().saveScene(scene_node);
		}
	}

//	-----------------------------------------------------------------------------------------------------------------------------
	
	public Scene getGameScene()
	{
		return scene_container;
	}
	
	public SceneUnitTag<?> getSelectedUnit() {
		return v_selected_unit;
	}
	
	public void selectUnit(SceneUnitTag<?> u, boolean updatelist){
		v_selected_unit = u;
		if (updatelist) {
			if (u instanceof SceneActor) {
				page_actors.setSelecte((SceneActor)u);
			} 
			else if (u instanceof SceneRegion) {
				page_regions.setSelecte((SceneRegion)u);
			} 
			else if (u instanceof ScenePoint) {
				page_points.setSelecte((ScenePoint)u);
			}
		}
	}
	
	public <T extends Unit> T getUnit(Class<T> type, Object id) {
		Vector<T> list = scene_container.getWorld().getChildsSubClass(type);
		for (T t : list) {
			if (t.getID().equals(id)) {
				return t;
			}
		}
		return null;
	}

	public void addUnit(SceneUnitTag<?> unit) {
		try{
			scene_container.getWorld().addChild(unit.getGameUnit());
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
	public void removeUnit(SceneUnitTag<?> unit) {
		try{
			scene_container.getWorld().removeChild(unit.getGameUnit());
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

	public void refreshAll() 
	{
		page_actors.repaint(500);
		page_regions.repaint(500);
		page_points.repaint(500);
		scene_mini_map.repaint(500);
	}
	
	public void refreshActor() {
		page_actors.repaint(500);
		scene_mini_map.repaint(500);
	}
	public void refreshRegion() {
		page_regions.repaint(500);
		scene_mini_map.repaint(500);
	}
	public void refreshPoint() {
		page_points.repaint(500);
		scene_mini_map.repaint(500);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------

//	@SuppressWarnings("unchecked")
//	public void sortName(Vector<SceneUnitTag> tunits) {
//		CUtil.sort(tunits, new CUtil.ICompare<SceneUnitTag, SceneUnitTag>() {
//			public int compare(SceneUnitTag a, SceneUnitTag b) {
//				return CUtil.getStringCompare().compare(a.getGameUnit().getID()+"", b.getGameUnit().getID()+"");
//			}
//		});
//	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	public boolean isToolSelect(){
		return tool_selector.isSelected();
	}
	
	public boolean isToolAdd(){
		return tool_addactor.isSelected();
	}
	
	public boolean isPageActor(){
		return unit_page.getSelectedComponent() == page_actors;
	}
	
	public boolean isPageRegion() {
		return unit_page.getSelectedComponent() == page_regions;
	}
	
	public boolean isPagePoint() {
		return unit_page.getSelectedComponent() == page_points;
	}

	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneStage extends Stage
	{
		public SceneStage(){}
		
		public void added(DisplayObjectContainer parent) {			
			addChild(scene_panel);
		}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {}

		public void update() {
			scene_panel.setSize(getWidth(), getHeight());
		}
		
	}
	
	
//	-----------------------------------------------------------------------------------------------------------------------------

	class SceneContainer extends Scene implements CUtil.ICompare<SceneUnitTag<?>, SceneUnitTag<?>>
	{
		Point			add_region_sp	= null;
		Point			add_region_dp	= new Point();
		
		Point2D.Double	pre_right_pos;
		Point2D.Double	pre_right_camera_pos;
		
//		DisplayObject	selected_unit		= null;

		ScenePoint 		pre_added_point;
		
		public SceneContainer() 
		{
			super(scene_resource, scene_world.name);
			this.enable_input				= true;
			this.getWorld().runtime_sort 	= false;
		}
		
		@Override
		protected WorldObject createWorldObject(CellSetResource set, SpriteObject worldSet) {
			return new EatWorldObject(set, worldSet);
		}
		
		@Override
		protected void onCameraChanged(double cx, double cy, double cw, double ch) {
			if (scene_mini_map != null) {			
				scene_mini_map.repaint(500);
			}
		}
		
		public int compare(SceneUnitTag<?> a, SceneUnitTag<?> b) {
			return CUtil.getStringCompare().compare(a.getGameUnit().getID()+"", b.getGameUnit().getID()+"");
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		添加单位时显示在鼠标上的单位
		
		@Override
		protected void renderAfter(Graphics2D g)
		{
			AffineTransform trans = g.getTransform();
			
			if (isToolAdd())
			{
				// 画Actor信息
				if (isPageActor())
				{
//					FormActorViewer actorv = view.studio.getSelectedNodeAsActor();
//					if (actorv!=null)
//					{
//						g.translate(getMouseX(), getMouseY());
//						actorv.getViewObject().render(g);
//						g.setColor(Color.WHITE);
//						Drawing.drawStringBorder(g, 
//								actorv.getViewObject().getSprite().getCurrentAnimate() + "/" + actorv.getViewObject().getSprite().getAnimateCount(), 
//								0, actorv.getViewObject().getSprite().getVisibleBotton() + 1, 
//								Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP
//								);
//					}
				}
				// 画Region信息
				else if (isPageRegion())
				{
					if (add_region_sp != null)
					{
						g.translate(-getCameraX(), -getCameraY());
						int sx = Math.min(add_region_sp.x, add_region_dp.x);
						int sy = Math.min(add_region_sp.y, add_region_dp.y);
						int sw = Math.abs(add_region_sp.x- add_region_dp.x);
						int sh = Math.abs(add_region_sp.y- add_region_dp.y);
						g.setColor(new Color(0x8000ff00,true));
						g.fillRect(sx, sy, sw, sh);
					}
				}
				// 画Point信息
				else if (isPagePoint())
				{
					g.setColor(Color.WHITE);
					g.fillRect(getMouseX()-4, getMouseY()-4, 8, 8);
				}
			}
			
			g.setTransform(trans);
		}
		
		public void update()
		{
			boolean catch_mouse = local_bounds.contains(getMouseX(), getMouseY());
			int worldx = getWorld().getMouseX();
			int worldy = getWorld().getMouseY();
			
			if (isToolSelect()) {
				updateSelectUnit(catch_mouse, worldx, worldy);
			}
			else if (catch_mouse && isToolAdd()) {
				updateAddUnit(catch_mouse, worldx, worldy);
			}
			
			updateLocateCamera(catch_mouse, worldx, worldy);
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 右键拖动摄像机
		void updateLocateCamera(boolean catch_mouse, int worldx, int worldy) 
		{
			if (catch_mouse && scene_panel.isCatchedScene() && getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos == null) {
					pre_right_pos = new Point2D.Double(getMouseX(), getMouseY());
					pre_right_camera_pos = new Point2D.Double(getCameraX(), getCameraY());
				}
			}
			else if (getRoot().isMouseHold(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				if (pre_right_pos != null) {
					double dx = pre_right_pos.x - getMouseX();
					double dy = pre_right_pos.y - getMouseY();
					scene_panel.locationCamera(
							pre_right_camera_pos.x+dx, 
							pre_right_camera_pos.y+dy);
				}
			}
			else if (getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) {
				pre_right_pos = null;
			}
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 选择单位
		
		void updateSelectUnit(boolean catch_mouse, int worldx, int worldy)
		{
			// 鼠标左/右键按下
			if (catch_mouse && scene_panel.isCatchedScene() && getRoot().isMouseDown(
					com.g2d.display.event.MouseEvent.BUTTON_LEFT,
					com.g2d.display.event.MouseEvent.BUTTON_RIGHT)) 
			{
				try
				{
					// actor
					if (isPageActor()) 
					{
						selectUnit(getWorld().getChildAtPos(worldx, worldy, SceneActor.class), true);
					}
					// region
					else if (isPageRegion())
					{
						selectUnit(getWorld().getChildAtPos(worldx, worldy, SceneRegion.class), true);
					}
					// point
					else if (isPagePoint())
					{
						// 选择节点
						if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) {
							selectUnit(getWorld().getChildAtPos(worldx, worldy, ScenePoint.class), true);
						}
						// 已选择节点并且右击了另一个
						else if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_RIGHT)){
							ScenePoint next = getWorld().getChildAtPos(worldx, worldy, ScenePoint.class);
							if (next != null && (next != getSelectedUnit()) && getSelectedUnit() instanceof ScenePoint) {
								try{
									Menu menu = ((ScenePoint)getSelectedUnit()).getLinkMenu(next);
									menu.show(scene_panel, getMouseX(), getMouseY());
								}catch(Exception err){
									err.printStackTrace();
								}
							}
						}
					}

					scene_mini_map.repaint(500);
					
				}catch (Throwable e) {}
			}
			// 鼠标右键松开
			else if (catch_mouse && getRoot().isMouseUp(com.g2d.display.event.MouseEvent.BUTTON_RIGHT))
			{
				if (getSelectedUnit()!=null && pre_right_pos!=null) {
					if ((pre_right_pos.x == getMouseX() && pre_right_pos.y == getMouseY())) {
						if (getSelectedUnit().getGameUnit().hitTestMouse()) {
							try{
								Menu menu = ((SceneUnitTag<?>)getSelectedUnit()).getEditMenu();
								menu.show(scene_panel, getMouseX(), getMouseY());
							}catch(Exception err){
								err.printStackTrace();
							}
						}
					}
				}
			}
			// 删除单位
			else if (getRoot().isKeyDown(java.awt.event.KeyEvent.VK_DELETE)) {
				if (getSelectedUnit() != null) {
					removeUnit(getSelectedUnit());
				}
			}
			// ctrl + c 复制名字
			else if (getRoot().isKeyHold(KeyEvent.VK_CONTROL) && getRoot().isKeyDown(KeyEvent.VK_C)) {
				if (getSelectedUnit() != null) {
					CObject.AppBridge.setClipboardText(getSelectedUnit().getGameUnit().getID()+"");
				}
			}
			
			pre_added_point = null;
			add_region_sp = null;
		}

//		-----------------------------------------------------------------------------------------------------------------------------
//		 添加单位
		void updateAddUnit(boolean catch_mouse, int worldx, int worldy) 
		{
			// 添加单位
			if (isPageActor())
			{
//				FormActorViewer actorv = view.studio.getSelectedNodeAsActor();
//				
//				if (actorv!=null)
//				{
//					if (getRoot().isMouseWheelUP()) {
//						actorv.getViewObject().getSprite().nextAnimate(-1);
//					}
//					if (getRoot().isMouseWheelDown()) {
//						actorv.getViewObject().getSprite().nextAnimate(1);
//					}
//
//					if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
//					{
//						SceneActor spr = new SceneActor(actorv, view);
//						spr.setLocation(worldx, worldy);
//						getWorld().addChild(spr);
//						getWorld().processEvent();
//						
//						view.tb_actors.repaint(500);
//						view.mini_map.repaint(500);
//					}
//				}
				pre_added_point = null;
				add_region_sp = null;
			}
			// 添加区域
			else if (isPageRegion())
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
							SceneRegion region = new SceneRegion(SceneEditor.this, new Rectangle(0, 0, sw, sh));
							region.setLocation(sx, sy);
							getWorld().addChild(region);
							getWorld().processEvent();
							
							refreshRegion();
						}
					}
					pre_added_point = null;
					add_region_sp = null;
				}
			}
			// 添加点
			else if (isPagePoint())
			{
				if (getRoot().isMouseDown(com.g2d.display.event.MouseEvent.BUTTON_LEFT)) 
				{
					ScenePoint spr = new ScenePoint(SceneEditor.this, worldx, worldy);
					spr.setLocation(worldx, worldy);
					getWorld().addChild(spr);
					getWorld().processEvent();
					
					// 添加新节点并自动链接
					if (getRoot().isKeyHold(KeyEvent.VK_SHIFT)) {
						if (pre_added_point!=null){
							pre_added_point.linkNext(spr);
							if (getRoot().isKeyHold(KeyEvent.VK_CONTROL)) {
								spr.linkNext(pre_added_point);
							}
						}
					}
					
					pre_added_point = spr;
					
					refreshPoint();
				}
			
				add_region_sp = null;
			}
		
		}
		
//		-----------------------------------------------------------------------------------------------------------------------------
//		场景单位
		class EatWorldObject extends WorldObject 
		{
			public EatWorldObject(CellSetResource set, SpriteObject worldSet) {
				super(set, worldSet);
			}
			@Override
			public synchronized void loaded(CellSetResource set, CSprite cspr,
					com.g2d.cell.CellSetResource.SpriteSet spr) {
				super.loaded(set, cspr, spr);
				if (spr.ImagesName.startsWith("jpg")) {
					this.priority = Integer.MIN_VALUE;
				}
			}
		}
		
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------
	
	class SceneMiniMap extends JPanel implements MouseMotionListener, MouseListener
	{
		private static final long serialVersionUID = 1L;
		
		BufferedImage				snapshot;
		
		public SceneMiniMap() 
		{			
			this.setMinimumSize(new Dimension(200, 200));
			this.addMouseMotionListener(this);
			this.addMouseListener(this);
		}
		
		@Override
		public void update(Graphics g) {
			paint(g);
		}
		
		@Override
		public void paint(Graphics sg)
		{
			Graphics2D g = (Graphics2D)sg;

			double rate_x = ((double)getWidth()) / scene_container.getWorld().getWidth();
			double rate_y = ((double)getHeight())/ scene_container.getWorld().getHeight();
			// draw bg
			if (snapshot == null || snapshot.getWidth() != getWidth() || snapshot.getHeight() != getHeight()){
				snapshot = scene_container.getWorld().createMiniMap(getWidth(), getHeight());
				Graphics2D mg = (Graphics2D)snapshot.getGraphics();
				Tools.setAlpha(mg, 0.3f);
				mg.setColor(Color.BLACK);
				mg.fillRect(0, 0, getWidth(), getHeight());
				mg.dispose();
//				System.out.println("create snapshot !");
			}
			g.drawImage(snapshot, 0, 0, this);
			// draw units
			{
				for (SceneUnitTag<?> u : scene_container.getWorld().getChildsSubClass(SceneUnitTag.class)) {
					if (u.getSnapColor() != null) {
						double tx = (u.getGameUnit().x * rate_x);
						double ty = (u.getGameUnit().y * rate_y);
						g.translate(tx, ty);
						if (getSelectedUnit() != u) {
							g.setColor(u.getSnapColor());
						} else {
							g.setColor(Color.WHITE);
						}
						g.fill(u.getSnapShape());
						g.translate(-tx, -ty);
					}
				}
			}
			// draw camera shape
			{
				Color camera_bounds_color = Color.WHITE;
				Rectangle2D.Double camera_bounds = new Rectangle2D.Double(
						scene_container.getCameraX() * rate_x,
						scene_container.getCameraY() * rate_y,
						scene_container.getCameraWidth() * rate_x,
						scene_container.getCameraHeight()* rate_y
				);
				g.setColor(camera_bounds_color);
				g.draw(camera_bounds);
			}
		}

		void killSnapshot() {
			snapshot = null;
		}
		
		void locateCamera(double x, double y) {
			double rate_x = ((double)scene_container.getWorld().getWidth()) / ((double)getWidth());
			double rate_y = ((double)scene_container.getWorld().getHeight())/ ((double)getHeight());
			scene_panel.locationCameraCenter(x * rate_x, y * rate_y);
		}
		
		public void mouseDragged(MouseEvent e) {
			locateCamera(e.getX(), e.getY());
		}
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				locateCamera(e.getX(), e.getY());
			}
		}
		public void mouseMoved(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	
	
	
	
	
	
}
