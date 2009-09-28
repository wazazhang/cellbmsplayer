package com.cell.g2d.cell.rpg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import com.cell.CMath;
import com.cell.g2d.Drawing;
import com.cell.g2d.cell.CellSprite;
import com.cell.g2d.display.DisplayObject;
import com.cell.g2d.display.DisplayObjectContainer;
import com.cell.g2d.display.DisplayShape;
import com.cell.g2d.display.InteractiveObject;
import com.cell.game.CCD;
import com.cell.game.CSprite;
import com.cell.game.CWayPoint;
import com.cell.game.ai.Astar;
import com.cell.game.ai.Astar.WayPoint;
import com.cell.game.edit.SetResource;
import com.cell.game.edit.SetResource.WORLD;
import com.cell.game.edit.SetResource.WorldMaker;
import com.cell.game.edit.SetResource.WORLD.MAP;
import com.cell.game.edit.SetResource.WORLD.SPR;

public abstract class Scene extends InteractiveObject
{
	public class World extends DisplayObjectContainer implements WorldMaker, Astar.AstarMap
	{
		class GridViewer extends DisplayObjectContainer
		{
			public GridViewer() {
				priority = 10000;
			}
			public void added(DisplayObjectContainer parent) {}
			public void removed(DisplayObjectContainer parent) {}
			public void update() {}	
			
			public void render(Graphics2D g) 
			{
				g.setColor(new Color(0xffffffff, true));
				for (int y=world_grid_y_size-1; y>=0; --y){
					for (int x=world_grid_x_size-1; x>=0; --x){
						if (grid_matrix[y][x] != 0){
							g.drawRect(x*gridW, y*gridH, gridW, gridH);
							Drawing.drawString(g, x+","+y, x*gridW, y*gridH);
						}
					}
				}
			}
		}
		
		
		
		final public SetResource setResource;
		protected int gridW = 100;
		protected int gridH = 100;
		
		GridViewer grid_viewer = new GridViewer();
		int world_grid_x_size;
		int world_grid_y_size;
		byte[][] grid_matrix;
		
		Astar astar_path_finder;
		
		public World(SetResource resource, String worldname) 
		{
			setResource = resource;
			setResource.makeWorld(worldname, this);
			debug = true;
			
			if (debug){
				this.addChild(grid_viewer);
			}
		}
		
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
			return grid_matrix[by][bx];
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
					if (grid_matrix[y][x]!=0) {
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
		
		
		public void begin(SetResource set, WORLD world) 
		{
			local_bounds.x = 0;
			local_bounds.y = 0;
			local_bounds.width	= world.Width;
			local_bounds.height	= world.Height;
			gridW = world.GridW;
			gridH = world.GridH;
			System.out.println("init world : " + world.WorldID + " : " + local_bounds.toString());
			
			world_grid_x_size = CMath.roundMod(world.Width,  gridW);
			world_grid_y_size = CMath.roundMod(world.Height, gridH);
			grid_matrix = new byte[world_grid_y_size][world_grid_x_size];
			System.out.println("world size ("+world.Width+","+world.Height+")");
			System.out.println("grid cell ("+gridW+","+gridH+")");
			System.out.println("grid matrix ("+world_grid_x_size+","+world_grid_y_size+")");
			
			try
			{
				Vector<Shape> shapes = new Vector<Shape>();
				
				CWayPoint[] waypoints = set.getWorldWayPoints(world.WorldID);
				for (CWayPoint waypoint : waypoints){
					Rectangle rect = new Rectangle(waypoint.X, waypoint.Y, 1, 1);
					shapes.add(rect);
					for (int i=0; i<waypoint.getNextCount(); i++) {
						CWayPoint point = waypoint.getNextPoint(i);
						Line2D line = new Line2D.Double(waypoint.X, waypoint.Y, point.X, point.Y);
						shapes.add(line);
					}
				}
				
				CCD[] regions = set.getWorldRegions(world.WorldID);
				for (CCD region : regions){
					Rectangle rect = new Rectangle(region.X1, region.Y1, region.getWidth(), region.getHeight());
					shapes.add(rect);
				}
				
				for (Shape shape : shapes) {
					DisplayShape s = new DisplayShape(shape, Color.MAGENTA);
					//System.out.println(s);
					grid_viewer.addChild(s);
				}
				
				//
				for (int y=world_grid_y_size-1; y>=0; --y){
					for (int x=world_grid_x_size-1; x>=0; --x){
						Rectangle rect = new Rectangle(x*gridW, y*gridH, gridW, gridH);
						for (Shape shape : shapes) {
							if (shape instanceof Rectangle) {
								if (rect.intersects((Rectangle)shape)) {
									grid_matrix[y][x] = 1;
									break;
								}
							}
							else if (shape instanceof Line2D) {
								if (rect.intersectsLine((Line2D)shape)) {
									grid_matrix[y][x] = 1;
									break;
								}
							}
						}
					}
				}
				
				astar_path_finder = new Astar(this);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		public void end(SetResource set, WORLD world) {}
		public void putMap(SetResource set, MAP wmap, com.cell.game.edit.SetResource.MAP map) {}
		public void putSprite(SetResource set, SPR wspr, com.cell.game.edit.SetResource.SPR spr) {
			// scene object
			CSprite cspr = setResource.getSprite(spr);
			cspr.setCurrentFrame(wspr.Anim, wspr.Frame);
			CellSprite cs = new CellSprite(cspr);
			cs.setLocation(wspr.X, wspr.Y);
			cs.z = y;
			addChild(cs);
			addedWorldSprite(cs, this, wspr, spr);
		}
		
		public void update() {
			super.sort();
		}
		public void added(DisplayObjectContainer parent) {}
		public void removed(DisplayObjectContainer parent) {}
		public void render(Graphics2D g) {}
		
		
		
	}
	
	final public World world;
	
	int camera_x;
	int camera_y;
	
	public Scene(SetResource resource, String worldname) 
	{
		enable_focus = false;
		world = new World(resource, worldname);
		this.addChild(world);
		this.setSize(640, 480);
	}
	
	public World getWorld() {
		return world;
	}
	
	public void locateCamera(int x, int y) 
	{
		camera_x = x ;
		camera_y = y ;
	}
	
	public void locateCameraCenter(int x, int y) 
	{
		camera_x = x - getWidth()/2;
		camera_y = y - getHeight()/2;
	}
	
	public void setSize(int w, int h) {
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
	
	public void update() 
	{
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
	
	public void render(Graphics2D g)
	{
		g.clip(local_bounds);
	}
	

	abstract public void addedWorldSprite(CellSprite cspr, World world, SPR wspr, com.cell.game.edit.SetResource.SPR spr) ;
	
	abstract public void actorBeginMove(Actor actor, int targetx, int targety) ;
	
}
