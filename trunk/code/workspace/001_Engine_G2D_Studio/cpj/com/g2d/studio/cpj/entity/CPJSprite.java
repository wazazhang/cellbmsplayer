package com.g2d.studio.cpj.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import com.cell.game.CCD;
import com.cell.game.CSprite;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.cell.CellSprite;
import com.g2d.cell.CellSetResource.SpriteSet;
import com.g2d.studio.cpj.CPJResourceType;


public class CPJSprite extends CPJObject<SpriteSet>
{
	CellSprite	cell_sprite;
	
	public CPJSprite(CPJFile parent, String name, CPJResourceType res_type) {
		super(parent, name, SpriteSet.class, res_type);
//		System.out.println("read a cpj sprite : " + name);
	}
	
	@Override
	public CellSprite getDisplayObject() {
		if (cell_sprite==null) {
			parent.getSetResource().initAllStreamImages();
			CSprite cspr = parent.getSetResource().getSprite(name);
			cell_sprite = new CellSprite(cspr);					
			cell_sprite.user_data = parent.getName()+"/" + name;
		}
		return cell_sprite;
	}
	
	@Override
	public BufferedImage getSnapShoot() {
		if (snapshoot==null) {
			try {
				File file = new File(parent.getCPJDir()+"/icon_" + name + ".png");
//				File file = new File(parent.getCPJDir()+"/icon.png");
				if (file.exists()) {
					snapshoot = Tools.readImage(file.getPath());
					if (snapshoot != null) {
						snapshoot = Tools.combianImage(32, 32, snapshoot);
					}
				}
				
				if (snapshoot == null) 
				{
					synchronized (parent.getSetResource()) 
					{
						boolean unload = !parent.getSetResource().isLoadImages();
						try{
							parent.getSetResource().initAllStreamImages();
							CSprite			spr		= parent.getSetResource().getSprite(name);
							CCD				bounds	= spr.getFrameBounds(0, 0);
							BufferedImage 	img		= Tools.createImage(bounds.getWidth(), bounds.getHeight());
							
							Graphics2D		g2d		= img.createGraphics();
							spr.render(new CGraphics(g2d), -bounds.X1, -bounds.Y1, 0, 0);
							g2d.dispose();

							snapshoot = Tools.combianImage(32, 32, img);
							Tools.writeImage(file.getPath(), snapshoot);
							System.err.println("create a sprite icon file : " + name);
						} catch (Throwable ex) {
							System.err.println("create a sprite icon file error : " + name);
						} finally {
							if (unload) {
								parent.getSetResource().destoryAllStreamImages();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.getSnapShoot();
	}
	
}
