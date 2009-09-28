package com.g2d.cell.game;

import java.awt.Color;
import java.awt.geom.Line2D;

import com.cell.CMath;
import com.cell.game.ai.Astar;
import com.cell.game.ai.Astar.WayPoint;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.DisplayShape;
import com.g2d.display.Sprite;

public abstract class SceneUnit extends Sprite
{
	private static final long serialVersionUID = Version.VersionG2D;

//	------------------------------------------------------------------------------------------------------------------------------------

	transient protected Scene.World owner_world;
	transient protected Scene 		owner_scene;

//	------------------------------------------------------------------------------------------------------------------------------------
//	scene
	
	/** 移动速度 距离/秒 */
	@Property("移动速度 距离/秒")
	public double		move_speed 		= 4;
	
	@Property("是否被阻挡")
	public boolean		move_blockade 			= false;

	/** 是否只在camera范围内可视 */
	public boolean		is_visible_camera_only	= true;
	
//	------------------------------------------------------------------------------------------------------------------------------------

	@Property("移动目标")
	double 				move_target_x = 0, move_target_y = 0;
	
	@Property("在场景中的名字")
	String				name;

	transient WayPoint 	path;
	
//	------------------------------------------------------------------------------------------------------------------------------------

	public boolean setName(Scene scene, String name) {
		if (name == null || name.length()==0) {
			return false;
		}
		for (DisplayObject obj : scene.getWorld().getChilds()) {
			if (obj!=this) {
				if (obj instanceof SceneUnit) {
					if (name.equals(((SceneUnit) obj).name)) {
						return false;
					}
				}
			}
		}
		this.name = name;
	
		return true;
	}
	
	public String getName(){
		return name;
	}
	
	
	public void added(DisplayObjectContainer parent) 
	{
		super.added(parent);
		
		if (parent instanceof Scene.World) {
			owner_world = (Scene.World)parent;
			owner_scene = (Scene)parent.getParent();
			if (name == null) {
				name = "SceneUnit_" + this.hashCode();
			}
		}else{
		//	System.err.println("warrning : Actor not add to Scene.World ! \n\t" + toString() + " : " + parent);
		}
	}


	public double getMoveTargetX()
	{
		return move_target_x;
	}
	
	public double getMoveTargetY() 
	{
		return move_target_y;
	}
	
	public void stopMove()
	{
		move_target_x = x;
		move_target_y = y;
		path = null;
	}
	
	public void beginMoveTarget(double targetX, double targetY)
	{
		move_target_x = targetX;
		move_target_y = targetY;
	}

	public Astar.WayPoint beginMove(double targetX, double targetY) 
	{
		move_target_x = targetX;
		move_target_y = targetY;
		
		int sx = owner_world.localToGridX((int)x);
		int sy = owner_world.localToGridY((int)y);
		int dx = owner_world.localToGridX((int)targetX);
		int dy = owner_world.localToGridY((int)targetY);
		
		WayPoint end = new WayPoint((int)targetX, (int)targetY);
		
		if (owner_world.getFlag(dx, dy)!=0) {
			end = null;
			int rx = CMath.getDirect(dx - sx);
			int ry = CMath.getDirect(dy - sy);
			int r = Math.max(Math.abs(dx - sx), Math.abs(dy - sy));
//			System.out.println("redirect count = " + r);
			for (int i=0; i<r; i++) {
				if (dx != sx) dx -= rx;
				if (dy != sy) dy -= ry;
				if (owner_world.getFlag(dx, dy)==0) {
					break;
				}
			}
		}

//		System.out.println("find path : " + sx + "," + sy + " -> " + dx + "," + dy);
		
		if (debug) {
			while (path != null) {
				if (path.Data != null) {
					owner_world.grid_viewer.removeChild((DisplayObject) path.Data);
				}
				path = path.Next;
			}
		}
		
		path = new WayPoint((int)x, (int)y);
		path.Next = owner_world.findPath(sx, sy, dx, dy).Next;

		{
			// 让 end 点成为最后一点
			WayPoint rp = path;
			while (rp!=null) {
				if (rp.Next == null) {
					rp.Next = end;
					break;
				}
				rp = rp.Next;
			}
			
			// 优化路径
			path = owner_world.optimizePath(path);
			
			move_target_x = path.X;
			move_target_y = path.Y;
			
			if (debug)
			{
				WayPoint p = path;
				Color pcolor = Color.BLUE;
				while (p!=null) {
					if (p.Next!=null) {
						DisplayShape ds = new DisplayShape(
								new Line2D.Double(p.X, p.Y, p.Next.X, p.Next.Y),
								pcolor);
//						ds.enable_fill = true;
						p.Data = ds;
						owner_world.grid_viewer.addChild(ds);
//						System.out.println(ds.shape);
					}
					p = p.Next;
				}
			}
		}
		
		return path;
	}
	
	
	public void setLocation(double x, double y) 
	{
		super.setLocation(x, y);
		move_target_x = x;
		move_target_y = y;
	}
	
	public void setLocation(int x, int y) 
	{
		super.setLocation(x, y);
		move_target_x = x;
		move_target_y = y;
	}
	
	public void move(double dx, double dy) 
	{
		super.move(dx, dy);
		if (move_blockade) {
			int wx = owner_world.localToGridX((int)x);
			int wy = owner_world.localToGridY((int)y);
			if (owner_world.getFlag(wx, wy)!=0) {
				super.move(-dx, -dy);
				move_target_x = x;
				move_target_y = y;
			}
		}
	}
	
	
	public int compareTo(DisplayObject o) {
		return (getY()+priority) - (o.getY()+o.priority);
	}
	
	public boolean isInCamera()
	{
		if (owner_scene != null) 
		{
			if (CMath.intersectRect2(
					owner_scene.camera_x, 
					owner_scene.camera_y, 
					owner_scene.local_bounds.width, 
					owner_scene.local_bounds.height,
					x + local_bounds.x,
					y + local_bounds.y, 
					local_bounds.width, 
					local_bounds.height)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void update() 
	{
		super.update();
		
		
		if (move_target_x!=x || move_target_y!=y || path!=null)
		{
			double distance = CMath.getDistance(move_speed, interval_ms);
			
			if (moveTo(move_target_x, move_target_y, distance))
			{
				if (path!=null) 
				{
					if (debug){
						if (path.Data!=null){
							owner_world.grid_viewer.removeChild((DisplayObject)path.Data);
						}
					}
					if (path.Next!=null) {
						move_target_x = path.Next.X;
						move_target_y = path.Next.Y;
					}
					path = path.Next;
				}
			}
		}
		
		if (is_visible_camera_only && owner_scene!=null && !isInCamera()) {
			this.visible = false;
		}else{
			this.visible = true;
		}
		
			
	}
	
	
	
}
