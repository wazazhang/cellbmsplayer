package com.g2d.studio.cpj.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import com.g2d.Tools;
import com.g2d.cell.CellSetResource.WorldSet;
import com.g2d.cell.game.Scene;
import com.g2d.studio.cpj.CPJResourceType;

public class CPJWorld extends CPJObject<WorldSet>
{	
//	Scene scene; 
	
	public BufferedImage scene_snapshoot;
	
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
	public void setSetObject(WorldSet obj) {
		super.setSetObject(obj);
		resetIcon();
		snapshoot = null;
		scene_snapshoot=null;
	}
	
	@Override
	public BufferedImage getSnapShoot() {
		if (snapshoot==null) {
			try{
				File snap_file = new File(parent.getCPJDir(), name + ".png");
				if (snap_file.exists()) {
					snapshoot = Tools.readImage(snap_file.getPath());
					float rate = 80f / (float)snapshoot.getWidth();
					snapshoot = Tools.combianImage(80, (int)(snapshoot.getHeight()*rate), snapshoot);
					scene_snapshoot = snapshoot;
				}  
			}catch(Exception err){
				System.err.println(err.getMessage());
			}
		}
		return snapshoot;
	}

	public void saveSnapshot(BufferedImage image) {
		if (scene_snapshoot == null) {
			File snap_file = new File(parent.getCPJDir(), name + ".png");
			scene_snapshoot = image;
			float rate = 80f / (float)scene_snapshoot.getWidth();
			scene_snapshoot = Tools.combianImage(80, (int)(scene_snapshoot.getHeight()*rate), scene_snapshoot);
			Tools.writeImage(snap_file.getPath(), scene_snapshoot);
			snapshoot = scene_snapshoot;
			getIcon(true);
		}
	}
	
//	@Override
//	public String toString() {
//		return super.toString() + "\n" + getSetObject().Width+"x"+getSetObject().Height;
//	}
}
