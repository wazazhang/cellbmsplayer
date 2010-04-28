package com.g2d.cell;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphics;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.AnimateCursor;
import com.g2d.display.Sprite;
import com.g2d.util.Drawing;

public class CellSprite extends Sprite
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient final public CSprite cspr;
	
	public Object user_data;
	
	public CellSprite(CSprite spr) {
		cspr = spr.copy();
		this.setLocalBounds(
				cspr.getVisibleLeft(), 
				cspr.getVisibleTop(), 
				cspr.getVisibleWidth(), 
				cspr.getVisibleHeight());
	}
	
	public void render(Graphics2D g) 
	{
		try{
			cspr.render(new CGraphics(g), 0, 0);
		}catch(Exception err){}
		z = getY();
	}
	
	public CSprite getSprite() {
		return cspr;
	}
	
	/**
	 * 由指定的精灵，创建一个动画鼠标
	 * @param cspr
	 * @return
	 */
	public static AnimateCursor createSpriteCursor(String name, CSprite cspr, int anim)
	{
		int frame_count = cspr.getFrameCount(anim);

		Cursor[] cursors = new Cursor[frame_count];

		for (int f = 0; f < frame_count; f++) {
			BufferedImage img = getFrameSnapshot(cspr, anim, f);
			CCD bounds = cspr.getFrameBounds(anim, f);
			Cursor cur = Tools.createCustomCursor(img, 
					new Point(-bounds.X1, -bounds.Y1), 
					name + "_" + anim + "_" + f);
			cursors[f] = cur;
//			System.out.println(name + "_" + anim + "_" + f);
		}

		return new AnimateCursor(cursors);
	}
	
	public static BufferedImage getFrameSnapshot(CSprite cspr, int anim, int frame)
	{
		CCD border = cspr.getFrameBounds(anim, frame);
		
		BufferedImage ret = Tools.createImage(border.getWidth(), border.getHeight());
		Graphics2D g = (Graphics2D)ret.getGraphics();
		cspr.render(new CGraphics(g), -border.X1, -border.Y1, anim, frame);
		g.dispose();
		
		return ret;
	}
}
