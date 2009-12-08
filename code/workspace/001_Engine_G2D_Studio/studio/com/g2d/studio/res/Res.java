package com.g2d.studio.res;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import com.cell.CIO;
import com.g2d.Tools;

public class Res 
{
//	---------------------------------------------------------------------------------------------------------------
//	icon
	static public BufferedImage icon_cpj			= Tools.readImage("/com/g2d/studio/res/icon_cpj.png");
	static public BufferedImage	icon_edit			= Tools.readImage("/com/g2d/studio/res/icon_edit.png");
	static public BufferedImage	icon_error			= Tools.readImage("/com/g2d/studio/res/icon_error.png");
	static public BufferedImage icon_hd				= Tools.readImage("/com/g2d/studio/res/icon_hd.png");
	static public BufferedImage	icon_grid			= Tools.readImage("/com/g2d/studio/res/icon_grid.png");
	static public BufferedImage icon_quest			= Tools.readImage("/com/g2d/studio/res/icon_quest.png");
	
	static public BufferedImage	icon_res_1			= Tools.readImage("/com/g2d/studio/res/icon_res_1.png");
	static public BufferedImage	icon_res_2			= Tools.readImage("/com/g2d/studio/res/icon_res_2.png");
	static public BufferedImage	icon_res_3			= Tools.readImage("/com/g2d/studio/res/icon_res_3.png");
	static public BufferedImage	icon_res_4			= Tools.readImage("/com/g2d/studio/res/icon_res_4.png");
	static public BufferedImage	icon_res_5			= Tools.readImage("/com/g2d/studio/res/icon_res_5.png");
	static public BufferedImage	icon_res_6			= Tools.readImage("/com/g2d/studio/res/icon_res_6.png");
	
//	---------------------------------------------------------------------------------------------------------------
//	icons
	static public BufferedImage icons_bar[]			= clipImage("/com/g2d/studio/res/icons_bar.png", 10, 1);
	static public BufferedImage icons_tool_bar[]	= clipImage("/com/g2d/studio/res/icons_tool_bar.png", 3, 1);

//	---------------------------------------------------------------------------------------------------------------
//	images
	static public BufferedImage	img_item_info		= Tools.readImage("/com/g2d/studio/res/img_item_info.png");
	static public BufferedImage	img_quest_info		= Tools.readImage("/com/g2d/studio/res/img_quest_info.png");
	static public BufferedImage	img_quest_info2		= Tools.readImage("/com/g2d/studio/res/img_quest_info2.png");
	static public BufferedImage	img_splash			= Tools.readImage("/com/g2d/studio/res/splash.jpg");
	
//	---------------------------------------------------------------------------------------------------------------
	
	
	static public ByteArrayInputStream snd_openning	= CIO.loadStream("/com/g2d/studio/res/openning.wav");
	
	
	
	
//	---------------------------------------------------------------------------------------------------------------
	
	static public BufferedImage[] clipImage(String src, int column, int row) {
		BufferedImage src_img = Tools.readImage(src);	
		BufferedImage[] ret = new BufferedImage[column * row];
		int sw = src_img.getWidth() / column;
		int sh = src_img.getHeight() / row;
		int i = 0;
		for (int c = 0; c < column; c++) {
			for (int r = 0; r < row; r++) {
				ret[i] = src_img.getSubimage(c*sw, r*sh, sw, sh);
				i ++;
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	
	
}
