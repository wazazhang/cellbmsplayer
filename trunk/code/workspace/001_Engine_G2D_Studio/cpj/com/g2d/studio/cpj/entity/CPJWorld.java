package com.g2d.studio.cpj.entity;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.cell.game.CSprite;
import com.g2d.Tools;
import com.g2d.cell.CellSprite;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.cell.game.Scene;
import com.g2d.studio.cpj.CPJResourceType;

public class CPJWorld extends CPJObject<WorldSet>
{	
//	Scene scene; 
	
	public CPJWorld(CPJFile parent, String name) {
		super(parent, name, WorldSet.class, CPJResourceType.WORLD);
	}
	
	@Override
	public Scene getDisplayObject() {
//		if (scene==null) {
//			scene = new Scene(parent.getSetResource(), name);
//		}
//		return scene;
		return null;
	}
	
	@Override
	public BufferedImage getSnapShoot() {
		if (snapshoot==null) {
			snapshoot = Tools.readImage(parent.getCPJDir()+"/jpg.jpg");
			float rate = 80f / (float)snapshoot.getWidth();
			snapshoot = Tools.combianImage(80, (int)(snapshoot.getHeight()*rate), snapshoot);
		}
		return snapshoot;
	}
	
//	@Override
//	public String toString() {
//		return super.toString() + "\n" + getSetObject().Width+"x"+getSetObject().Height;
//	}
}
