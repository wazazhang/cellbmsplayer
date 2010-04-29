package com.g2d.cell.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.cell.CMath;
import com.cell.game.ai.pathfind.AstarManhattanMap;
import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.gfx.game.CWayPoint;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.cell.CellSetResource;
import com.g2d.cell.CellSetResourceManager;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.cell.CellSetResource.WorldSet.SpriteObject;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.DisplayShape;
import com.g2d.display.InteractiveObject;
import com.g2d.util.Drawing;

public class Scene extends com.g2d.game.rpg.Scene 
{
	public Scene() {
	}
	
	public Scene(WorldMap world) {
		super(world);
	}
	
	@SuppressWarnings("deprecation")
	public Scene(CellSetResource resource, String worldname) 	{
		addChild(new WorldMap(resource, resource.WorldTable.get(worldname)));
	}
	

	
	
//	-----------------------------------------------------------------------------------------------------------

	public class WorldMap extends com.g2d.game.rpg.SceneMap
	{
		final protected CellSetResource			set_resource;
		final protected CellSetResource.WorldSet	set_world;

		public WorldMap(CellSetResource resource, CellSetResource.WorldSet set_world) 
		{
			super(Scene.this, 
					set_world.GridW, 
					set_world.GridH, 
					set_world.GridXCount, 
					set_world.GridYCount, 
					set_world.Terrian);

			this.set_resource 	= resource;
			this.set_world		= set_world;
			
			for (WorldSet.SpriteObject wspr : sortWorldObject(new ArrayList<SpriteObject>(set_world.Sprs))){
				WorldObject cs = createWorldObject(resource, wspr);
				addChild(cs);
			}
		}
		
		/**
		 * 自定义地图单元的加载顺序
		 * @param objects
		 * @return
		 */
		protected List<SpriteObject> sortWorldObject(List<SpriteObject> objects) {
			return objects;
		}
		
		/**
		 * 返回自定义的地图单位实现
		 * @param set
		 * @param world_set
		 * @return
		 */
		protected WorldObject createWorldObject(CellSetResource set, CellSetResource.WorldSet.SpriteObject world_set) {
			return new WorldObject(set, world_set);
		}
		
		final public CellSetResource getSetResource(){
			return set_resource;
		}
		
		final public CellSetResource.WorldSet getSetWorld() {
			return set_world;
		}

		public boolean isStreamingImages()
		{
			for (int i=set_world.Sprs.size()-1; i>=0; --i){
				WorldSet.SpriteObject wspr = set_world.Sprs.elementAt(i);
				if (!set_resource.isStreamingImages(wspr.ImagesID)){
					return false;
				}
			}
			return true;
		}
		
		public BufferedImage createMiniMap(double width, double height) 
		{
			if (isStreamingImages()) {
				return null;
			} else {
				BufferedImage buffer = Tools.createImage((int) width, (int) height);
				Graphics2D g2d = (Graphics2D) buffer.getGraphics();
				double scalew = width / getWidth();
				double scaleh = height / getHeight();
				g2d.scale(scalew, scaleh);
				CGraphics cg = new CGraphics(g2d);
				for (int i = set_world.Sprs.size() - 1; i >= 0; --i) {
					WorldSet.SpriteObject wspr = set_world.Sprs.elementAt(i);
					CSprite csprite = set_resource.getSprite(wspr.SprID);
					csprite.render(cg, wspr.X, wspr.Y, wspr.Anim, wspr.Frame);
				}
				g2d.dispose();
				return buffer;
			}
		}
		
		public BufferedImage createScreenshot(int x, int y, double width, double height)
		{
			if (isStreamingImages()) {
				return null;
			} else {
				BufferedImage buffer = Tools.createImage((int) width, (int) height);
				Graphics2D g2d = (Graphics2D) buffer.getGraphics();
				CGraphics cg = new CGraphics(g2d);
				for (int i = set_world.Sprs.size() - 1; i >= 0; --i) {
					WorldSet.SpriteObject wspr = set_world.Sprs.elementAt(i);
					CSprite csprite = set_resource.getSprite(wspr.SprID);
					csprite.render(cg, x+wspr.X, y+wspr.Y, wspr.Anim, wspr.Frame);
				}
				g2d.dispose();
				return buffer;
			}
		}
	}
	

	public static class WorldObject extends SceneSprite
	{
		final protected CellSetResource.WorldSet.SpriteObject 		set_world_sprite;
		
		public WorldObject(CellSetResource set, CellSetResource.WorldSet.SpriteObject world_set) 
		{
			synchronized(this) {
				super.init(set, world_set.SprID);
				set_world_sprite = world_set;
				setLocation(world_set.X, world_set.Y);
			}
		}
		
		@Override
		public void loaded(CellSetResource set, CSprite cspr, com.g2d.cell.CellSetResource.SpriteSet spr) {
			synchronized(this) {
				super.loaded(set, cspr, spr);
				while (set_world_sprite==null) {}
				csprite.setCurrentFrame(set_world_sprite.Anim, set_world_sprite.Frame);
			}
		}
	}
	
}
