package com.g2d.game.rpg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.cell.CMath;
import com.cell.game.ai.Astar;
import com.cell.game.ai.Astar.WayPoint;

import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.util.Drawing;

public abstract class SceneMap extends DisplayObjectContainer implements Astar.AstarMap
{
	final public Scene		owner_scene;
	
	final protected int 	gridW, gridH;
	final protected int 	world_grid_x_size;
	final protected int 	world_grid_y_size;
	final protected int[][] grid_matrix;
	final protected Astar	astar_path_finder;
	final TerrainViewer		grid_viewer;
	
	public boolean			runtime_sort		= true;
	
	

//	---------------------------------------------------------------------------------------------------------------------------

	
	private Map<Object, Unit>	units_name		= new HashMap<Object, Unit>(100);
	private AtomicInteger		units_index		= new AtomicInteger(1);
	
	
	
//	---------------------------------------------------------------------------------------------------------------------------

	public SceneMap(Scene scene, 
			int grid_w, 
			int grid_h, 
			int grid_x_count,
			int grid_y_count) 
	{
		owner_scene			= scene;

		local_bounds.x		= 0;
		local_bounds.y		= 0;
		local_bounds.width	= grid_w * grid_x_count;
		local_bounds.height	= grid_h * grid_y_count;
		gridW				= grid_w;
		gridH				= grid_h;
		world_grid_x_size	= grid_x_count;
		world_grid_y_size	= grid_y_count;
		grid_matrix			= new int[grid_x_count][grid_y_count];
		grid_viewer 		= new TerrainViewer();
		
		astar_path_finder = new Astar(this);
	}

	@Override
	synchronized public boolean addChild(DisplayObject child) {
		if ((child instanceof Unit)) {
			Unit unit = (Unit)child;
			if (unit.getID() == null) {
				for (int i = Integer.MAX_VALUE; i > Integer.MIN_VALUE; --i) {
					String id = unit.getClass().getSimpleName() + "_" + units_index.getAndIncrement();
//					System.out.println("crate default id = " + id);
					if (unit.setID(this, id)) {
						break;
					}
				}
			}
			if (!units_name.containsKey(unit.getID())) {
				if (super.addChild(child)) {
					units_name.put(unit.getID(), unit);
					unit.setOwnerScene(owner_scene);
					unit.setOwnerWorld(this);
					return true;
				}
			}
			return false;
		}
		return super.addChild(child);
	}
	
	@Override
	synchronized public boolean removeChild(DisplayObject child) {
		if ((child instanceof Unit)) {
			Unit unit = (Unit)child;
			if (super.removeChild(child)) {
				units_name.remove(unit.getID());
				unit.setOwnerScene(null);
				unit.setOwnerWorld(null);
				return true;
			}
			return false;
		}
		return super.removeChild(child);
	}
	
	synchronized public boolean containsUnitWithID(Object id) {
		return units_name.containsKey(id);
	}
	
	synchronized public Unit getUnitWithID(Object id) {
		return units_name.get(id);
	}
	
	synchronized boolean tryChangeUnitID(Unit unit, Object new_id) {
		if (new_id == null || containsUnitWithID(new_id)) {
			return false;
		} else if(contains(unit)) {
			units_name.remove(unit.getID());
			units_name.put(new_id, unit);
			return true;
		}
		return false;
	}
	
//	---------------------------------------------------------------------------------------------------------------------------

	final public Astar.WayPoint findPath(int sx, int sy, int dx, int dy) {
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
		} else {
			if (contains(grid_viewer)) {
				this.removeChild(grid_viewer);
			}
		}
	}

//		---------------------------------------------------------------------------------------------------------------------------

	final public int localToGridX(int x) {
		x = CMath.cycMod(x, gridW);
		x = Math.min(x, world_grid_x_size - 1);
		x = Math.max(x, 0);
		return x;
	}

	final public int localToGridY(int y) {
		y = CMath.cycMod(y, gridH);
		y = Math.min(y, world_grid_y_size - 1);
		y = Math.max(y, 0);
		return y;
	}

	final public int gridXToLocal(int x) {
		x *= gridW;
		x += gridW / 2;
		return x;
	}

	final public int gridYToLocal(int y) {
		y *= gridH;
		y += gridH / 2;
		return y;
	}

	final public int getCellH() {
		return gridW;
	}

	final public int getCellW() {
		return gridH;
	}

	final public int getXCount() {
		return world_grid_x_size;
	}

	final public int getYCount() {
		return world_grid_y_size;
	}
		
//	---------------------------------------------------------------------------------------------------------------------------

	public int getFlag(int bx, int by) {
		return grid_matrix[bx][by];
	}

	public boolean touchMap(Shape shape) 
	{
		Rectangle srect = shape.getBounds();
		int sx = localToGridX(srect.x);
		int sy = localToGridY(srect.y);
		int dx = localToGridX(srect.x + srect.width);
		int dy = localToGridY(srect.y + srect.height);
		for (int y = sy; y <= dy; ++y) {
			for (int x = sx; x <= dx; ++x) {
				if (grid_matrix[x][y] != 0) {
					Rectangle rect = new Rectangle(x * gridW, y * gridH, gridW,
							gridH);
					if (shape instanceof Rectangle2D) {
						if (rect.intersects((Rectangle2D) shape)) {
							return true;
						}
					} else if (shape instanceof Line2D) {
						if (rect.intersectsLine((Line2D) shape)) {
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
		if (root != null) {
			WayPoint current = root;
			WayPoint prev = root.Next;
			WayPoint next = root.Next;
			while (next != null) {
				Line2D.Double line = new Line2D.Double(current.X, current.Y, next.X, next.Y);
				if (!touchMap(line)) {
					current.Next = next;
				} else {
					current = prev;
				}
				prev = next;
				next = next.Next;
			}
		}

		return root;
	}
		

//		---------------------------------------------------------------------------------------------------------------------------

	public void added(DisplayObjectContainer parent) {}
	public void removed(DisplayObjectContainer parent) {}
	public void update() {
		if (runtime_sort) {
			super.sort();
		}
	}

	public void render(Graphics2D g) {}

	abstract public BufferedImage createMiniMap(double width, double height);

//	---------------------------------------------------------------------------------------------------------------------------

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

}
