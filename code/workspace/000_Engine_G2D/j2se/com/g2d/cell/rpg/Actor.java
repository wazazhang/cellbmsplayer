package com.cell.g2d.cell.rpg;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import com.cell.CMath;
import com.cell.g2d.Drawing;
import com.cell.g2d.Tools;
import com.cell.g2d.cell.CellSprite;
import com.cell.g2d.display.DisplayObject;
import com.cell.g2d.display.DisplayObjectContainer;
import com.cell.g2d.display.DisplayShape;
import com.cell.game.CSprite;
import com.cell.game.ai.Astar.WayPoint;

public class Actor extends CellSprite
{
	protected double move_target_x = 0;
	protected double move_target_y = 0;
	
	protected Scene.World owner_world;
	protected Scene owner_scene;
	
	WayPoint path;
	
	public Actor(CSprite cspr) {
		super(cspr);
		debug = true;
	}

	public void setLocation(double x, double y) 
	{
		super.setLocation(x, y);
		move_target_x = x;
		move_target_y = y;
	}

	public void move(double dx, double dy) 
	{
//		{
//			super.move(dx, 0);
//			int wx = owner_world.localToGridX((int)x);
//			int wy = owner_world.localToGridY((int)y);
//			if (owner_world.grid_matrix[wy][wx]!=0) {
//				super.move(-dx, 0);
//			}
//		}{
//			super.move(0, dy);
//			int wx = owner_world.localToGridX((int)x);
//			int wy = owner_world.localToGridY((int)y);
//			if (owner_world.grid_matrix[wy][wx]!=0) {
//				super.move(0, -dy);
//			}
//		}
		
		
		super.move(dx, dy);
		int wx = owner_world.localToGridX((int)x);
		int wy = owner_world.localToGridY((int)y);
		if (owner_world.grid_matrix[wy][wx]!=0) {
			super.move(-dx, -dy);
			move_target_x = x;
			move_target_y = y;
		}
		
	}
	
	public void added(DisplayObjectContainer parent) {
		if (parent instanceof Scene.World) {
			owner_world = (Scene.World)parent;
			owner_scene = (Scene)parent.getParent();
		}else{
			try{
				throw new Exception("Actor must be add to Scene.World !");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void beginMove(double targetX, double targetY) 
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
			owner_scene.actorBeginMove(this, owner_world.gridXToLocal(dx), owner_world.gridYToLocal(dy));
		}
		else {
			owner_scene.actorBeginMove(this, (int)targetX, (int)targetY);
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
		
		
		path = owner_world.astar_path_finder.findPath(sx, sy, dx, dy).Next;
		
		if (path!=null)
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
		
	}
	
	
	public void update() 
	{
		if (moveTo(move_target_x, move_target_y, 4))
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
	
//	int ci = 0;
//	AlphaComposite[] composites = new AlphaComposite[]{
//		AlphaComposite.Clear,
//		AlphaComposite.Src,
//		AlphaComposite.Dst,
//		AlphaComposite.SrcOver,
//		AlphaComposite.DstOver,
//		AlphaComposite.SrcIn,
//		AlphaComposite.DstIn,
//		AlphaComposite.SrcOut,
//		AlphaComposite.DstOut,
//		AlphaComposite.SrcAtop,
//		AlphaComposite.DstAtop,
//		AlphaComposite.Xor,
//	};
	
	public void render(Graphics2D g) 
	{

		super.render(g);
		
		int wx = owner_world.localToGridX((int)x);
		int wy = owner_world.localToGridY((int)y);
		
		g.setColor(Color.BLUE);
		Drawing.drawString(g, wx+","+wy, 0, -16, Drawing.TEXT_ANCHOR_TOP | Drawing.TEXT_ANCHOR_HCENTER);
		
//		if (hitTestMouse())
//		{
//			Composite composite = g.getComposite();
//			g.setComposite(composites[ci]);
//			
////			g.setXORMode(new Color(0x80ffffff, true));
//			
//			g.setColor(new Color(0x80ffffff, true));
//			g.fill(local_bounds);
//			
//			g.setComposite(composite);
//			
//			if (getRoot().isMouseDown(MouseEvent.BUTTON1)) {
//				ci ++;
//				ci %= composites.length;
//				System.out.println(composites[ci]);
//			}
//		}
		
		
		
	
		
		
	}
	
}
