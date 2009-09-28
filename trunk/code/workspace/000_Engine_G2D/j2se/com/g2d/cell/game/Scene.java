package com.g2d.cell.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Vector;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.cell.CMath;
import com.cell.game.CCD;
import com.cell.game.CSprite;
import com.cell.game.CWayPoint;
import com.cell.game.ai.Astar;
import com.cell.game.ai.Astar.WayPoint;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.cell.CellSetResource.WORLD;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.DisplayShape;
import com.g2d.display.InteractiveObject;
import com.g2d.util.Drawing;

public class Scene extends InteractiveObject 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public class World extends DisplayObjectContainer implements Astar.AstarMap
	{
		public class TerrainViewer extends DisplayObjectContainer
		{
			public TerrainViewer() {
				priority = 10000;
			}
			public void added(DisplayObjectContainer parent) {}
			public void removed(DisplayObjectContainer parent) {}
			public void update() {}	
			
			public void render(Graphics2D g) 
			{
				for (int y=world_grid_y_size-1; y>=0; --y){
					for (int x=world_grid_x_size-1; x>=0; --x){
						if (grid_matrix[x][y] != 0){
							g.setColor(new Color(grid_matrix[x][y], true));
							g.drawRect(x*gridW, y*gridH, gridW, gridH);
							Drawing.drawString(g, x+","+y, x*gridW, y*gridH);
						}
					}
				}
			}
		}
		
		protected CellSetResource 		set_resource;
		protected CellSetResource.WORLD set_world;
		protected TerrainViewer 		grid_viewer 		= new TerrainViewer();
		
		protected int 					gridW, gridH;
		protected int 					world_grid_x_size, world_grid_y_size;
		protected int[][] 				grid_matrix;
		
		transient protected Astar 		astar_path_finder;
		
//		---------------------------------------------------------------------------------------------------------------------------

		@Override
		protected void init_transient() 
		{
			super.init_transient();
			
			if (set_resource!=null && set_world!=null)
			{
				astar_path_finder = new Astar(this);
			}
		}
		
		public World(CellSetResource resource, String worldname) 
		{
			init(resource, worldname);
			
			astar_path_finder = new Astar(this);

		}
		
		protected void init(CellSetResource resource, String worldname) 
		{
			set_resource 	= resource;
			set_world		= set_resource.getSetWorld(worldname);
			
			{

				local_bounds.x = 0;
				local_bounds.y = 0;
				local_bounds.width	= set_world.Width;
				local_bounds.height	= set_world.Height;
				gridW = set_world.GridW;
				gridH = set_world.GridH;
				//System.out.println("init world : " + set_world.WorldID + " : " + local_bounds.toString());
				
				world_grid_x_size = set_world.GridXCount;
				world_grid_y_size = set_world.GridYCount;
				grid_matrix = set_world.Terrian;
				
				
				for (int i=set_world.Sprs.size()-1; i>=0; --i){
					WORLD.SPR wspr = set_world.Sprs.elementAt(i);
					WorldObject cs = createWorldObject(set_resource, wspr);
					addChild(cs);
				}
				
//				world_grid_x_size = CMath.roundMod(set_world.Width,  gridW);
//				world_grid_y_size = CMath.roundMod(set_world.Height, gridH);
//				grid_matrix = new byte[world_grid_y_size][world_grid_x_size];
//				System.out.println("world size ("+set_world.Width+","+set_world.Height+")");
//				System.out.println("grid cell ("+gridW+","+gridH+")");
//				System.out.println("grid matrix ("+world_grid_x_size+","+world_grid_y_size+")");
//				
//				try
//				{
//					Vector<Shape> shapes = new Vector<Shape>();
//					
//					CWayPoint[] waypoints = set_resource.getWorldWayPoints(set_world.WorldID);
//					for (CWayPoint waypoint : waypoints){
//						Rectangle rect = new Rectangle(waypoint.X, waypoint.Y, 1, 1);
//						shapes.add(rect);
//						for (int i=0; i<waypoint.getNextCount(); i++) {
//							CWayPoint point = waypoint.getNextPoint(i);
//							Line2D line = new Line2D.Double(waypoint.X, waypoint.Y, point.X, point.Y);
//							shapes.add(line);
//						}
//					}
//					
//					CCD[] regions = set_resource.getWorldRegions(set_world.WorldID);
//					for (CCD region : regions){
//						Rectangle rect = new Rectangle(region.X1, region.Y1, region.getWidth(), region.getHeight());
//						shapes.add(rect);
//					}
//					
//					for (Shape shape : shapes) {
//						DisplayShape s = new DisplayShape(shape, Color.MAGENTA);
//						//System.out.println(s);
//						grid_viewer.addChild(s);
//					}
//					
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
			
				
				
			}
			
			showDebugGrid(debug);
			
		}
		
		public CellSetResource getSetResource(){
			return set_resource;
		}
		
		public CellSetResource.WORLD getSetWorld() {
			return set_world;
		}
		
		public Astar.WayPoint findPath(int sx, int sy, int dx, int dy) 
		{
			return astar_path_finder.findPath(sx, sy, dx, dy);
		}
		
		@Override
		public void setDebug(boolean d) {
			super.setDebug(d);
			showDebugGrid(d);
		}
		
		public void showDebugGrid(boolean show) {
			if (show) {
				if (!contains(grid_viewer)) {
					this.addChild(grid_viewer);
				}
			}else{
				if (contains(grid_viewer)) {
					this.removeChild(grid_viewer);
				}
			}
		}
		
		public BufferedImage createMiniMap(double width, double height) 
		{
			BufferedImage buffer = Tools.createImage((int)width, (int)height);
			Graphics2D g2d = (Graphics2D)buffer.getGraphics();
			
			if (set_resource.isStreamingResource()) {
				
//				g2d.setColor(new Color(0xff808080));
//				g2d.fillRect(0, 0, (int)width, (int)height);
//				g2d.setColor(new Color(0xffffffff));
//				Drawing.drawStringBorder(g2d, "loading", 0, 0, (int)width, (int)height, 
//						Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_VCENTER);
				return null;
			} 
			else 
			{
				double scalew = width / getWidth();
				double scaleh = height / getHeight();
				
				g2d.scale(scalew, scaleh);
				CGraphics cg = new CGraphics(g2d);
				
				for (int i=set_world.Sprs.size()-1; i>=0; --i){
					WORLD.SPR wspr = set_world.Sprs.elementAt(i);
					CSprite csprite = set_resource.getSprite(wspr.SprID);
					csprite.render(cg, wspr.X, wspr.Y, wspr.Anim, wspr.Frame);
				}
			}
			
			return buffer;
		}
		
//		---------------------------------------------------------------------------------------------------------------------------

		public int localToGridX(int x) {
			x = CMath.cycMod(x, gridW);
			x = Math.min(x, world_grid_x_size-1);
			x = Math.max(x, 0);
			return x;
		}
		public int localToGridY(int y) {
			y = CMath.cycMod(y, gridH);
			y = Math.min(y, world_grid_y_size-1);
			y = Math.max(y, 0);
			return y;
		}

		public int gridXToLocal(int x) {
			x *= gridW;
			x += gridW/2;
			return x;
		}
		public int gridYToLocal(int y) {
			y *= gridH;
			y += gridH/2;
			return y;
		}
		
		public int getCellH() {
			return gridW;
		}
		public int getCellW() {
			return gridH;
		}
		public int getFlag(int bx, int by) {
			return grid_matrix[bx][by];
		}
		public int getXCount() {
			return world_grid_x_size;
		}
		public int getYCount() {
			return world_grid_y_size;
		}
		
		public boolean touchMap(Shape shape)
		{
			Rectangle srect = shape.getBounds();
			
			int sx = localToGridX(srect.x);
			int sy = localToGridY(srect.y);
			int dx = localToGridX(srect.x+srect.width);
			int dy = localToGridY(srect.y+srect.height);
			
			for (int y=sy; y<=dy; ++y){
				for (int x=sx; x<=dx; ++x){
					if (grid_matrix[x][y]!=0) {
						Rectangle rect = new Rectangle(x*gridW, y*gridH, gridW, gridH);
						if (shape instanceof Rectangle2D) {
							if (rect.intersects((Rectangle2D)shape)) {
								return true;
							}
						}
						else if (shape instanceof Line2D) {
							if (rect.intersectsLine((Line2D)shape)) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}
		
		public WayPoint optimizePath(WayPoint root)
		{
			if (root!=null)
			{
				WayPoint current = root;
				WayPoint prev = root.Next;
				WayPoint next = root.Next;
				
				while (next!=null)
				{
					Line2D.Double line = new Line2D.Double(current.X, current.Y, next.X, next.Y);
					
					if (!touchMap(line)) {
						current.Next = next;
					}else{
						current = prev;
					}
					
					prev = next;
					next = next.Next;
					
				}
			}
			
			return root;
		}
		

		
		public void update() {
			super.sort();
		}
		public void added(DisplayObjectContainer parent) {
			for (int i=0; i<getChildCount(); i++) {
				DisplayObject child = getChildAt(i);
				if (child instanceof SceneUnit) {
					((SceneUnit) child).owner_scene = Scene.this;
					((SceneUnit) child).owner_world = this;
				}
			}
		}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {}
		
		
		
	}
	

	public static class WorldObject extends SceneSprite
	{
		final protected CellSetResource.WORLD.SPR 		set_world_sprite;
		
		public WorldObject(CellSetResource set, CellSetResource.WORLD.SPR world_set) 
		{
			super(set, world_set.SprID);
			set_world_sprite = world_set;
			setLocation(world_set.X, world_set.Y);
		}
		
		@Override
		synchronized public void loaded(CellSetResource set, CSprite cspr, com.g2d.cell.CellSetResource.SPR spr) {
			super.loaded(set, cspr, spr);
			while (set_world_sprite==null) {}
			csprite.setCurrentFrame(set_world_sprite.Anim, set_world_sprite.Frame);
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------------------
//	
	
	protected World				world;
	
	protected int 				camera_x;
	protected int 				camera_y;
	
//	---------------------------------------------------------------------------------------------------------------------------
	public Scene() 
	{
		this.enable_focus = false;
		this.setSize(640, 480);
	}
	
	public Scene(CellSetResource resource, String worldname) 
	{
		this.enable_focus = false;
		this.setSize(640, 480);
		
		init(resource, worldname);
	}
	
	protected void init(CellSetResource resource, String worldname)
	{
		this.addChild(new World(resource, worldname));
	}
	protected void init(World world)
	{
		this.addChild(world);
	}

	
	/**
	 * 请勿将世界精灵添加到该层。
	 * @see getWorld().addChild(child);
	 */
	@Override	
	@Deprecated
	public void addChild(DisplayObject child) {
		if (child instanceof World && world==null){
			this.world = (World)child;
			super.addChild(child);
			return;
		}
		throw new NotImplementedException();
	}
	
	/**
	 * 请勿将世界精灵添加到该层。
	 * @see getWorld().removeChild(child)
	 */
	@Override
	@Deprecated
	public void removeChild(DisplayObject child) {
		throw new NotImplementedException();
	}
	
	
	public World getWorld() {
		return world;
	}
	
	protected WorldObject createWorldObject(CellSetResource set, CellSetResource.WORLD.SPR world_set)
	{
		return new WorldObject(set, world_set);
	}
	
	
	public int getCameraX() {
		return camera_x;
	}
	
	public int getCameraY() {
		return camera_y;
	}
	
	public void locateCamera(int x, int y) 
	{
		camera_x = x ;
		camera_y = y ;
		
		if (camera_x < world.local_bounds.x) {
			camera_x = world.local_bounds.x;
		}
		if (camera_x > world.local_bounds.width - local_bounds.width) {
			camera_x = world.local_bounds.width - local_bounds.width;
		}
		if (camera_y < world.local_bounds.y) {
			camera_y = world.local_bounds.y;
		}
		if (camera_y > world.local_bounds.height - local_bounds.height) {
			camera_y = world.local_bounds.height - local_bounds.height;
		}
		
		world.x = -camera_x;
		world.y = -camera_y;
	}
	
	public void locateCameraCenter(int x, int y) 
	{
		locateCamera(x - getWidth()/2, y - getHeight()/2);
	}
	
	
	public void setSize(int w, int h)
	{
		if (world!=null) {
			if (w > world.local_bounds.width) {
				w = world.local_bounds.width;
			}
			if (h > world.local_bounds.height) {
				h = world.local_bounds.height;
			}
		}
		super.setSize(w, h);
	}
	
	public void added(DisplayObjectContainer parent) {}
	
	public void removed(DisplayObjectContainer parent) {}

	public void update() {}
	
	public void render(Graphics2D g)
	{
		g.clip(local_bounds);
	}

}
