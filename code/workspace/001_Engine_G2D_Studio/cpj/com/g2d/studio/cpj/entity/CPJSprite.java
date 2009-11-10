package com.g2d.studio.cpj.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import com.cell.game.CSprite;
import com.g2d.Tools;
import com.g2d.cell.CellSprite;
import com.g2d.cell.CellSetResource.SpriteSet;
import com.g2d.studio.cpj.CPJResourceType;


public class CPJSprite extends CPJObject<SpriteSet>
{
	CellSprite	cell_sprite;
	
	public CPJSprite(CPJFile parent, String name, CPJResourceType res_type) {
		super(parent, name, SpriteSet.class, res_type);
		System.out.println("read a cpj sprite : " + name);
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
				File file = new File(parent.getCPJDir()+"/icon.png");
				if (file.exists()) {
					snapshoot = Tools.readImage(file.getPath());
					snapshoot = Tools.combianImage(32, 32, snapshoot);
				}
				else {
					synchronized (parent.getSetResource()) {
						boolean unload = !parent.getSetResource().isLoadImages();
						parent.getSetResource().initAllStreamImages();
						CSprite spr = parent.getSetResource().getSprite(name);
						Image img = spr.getFrameImage(0, 0, 0).getSrc();
						snapshoot = Tools.combianImage(32, 32, img);
						if (unload) {
							parent.getSetResource().destoryAllStreamImages();
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
