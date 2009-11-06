package com.g2d.studio.res;

import java.awt.image.BufferedImage;

import com.g2d.Tools;

public class Res {
	
	static public BufferedImage icon_cpj	= Tools.readImage("/com/g2d/studio/res/icon_cpj.png");
	static public BufferedImage	icon_edit	= Tools.readImage("/com/g2d/studio/res/icon_edit.png");
	static public BufferedImage	icon_error	= Tools.readImage("/com/g2d/studio/res/icon_error.png");
	static public BufferedImage icon_hd		= Tools.readImage("/com/g2d/studio/res/icon_hd.png");
	
	static public BufferedImage	icon_res_1	= Tools.readImage("/com/g2d/studio/res/icon_res_1.png");
	static public BufferedImage	icon_res_2	= Tools.readImage("/com/g2d/studio/res/icon_res_2.png");
	static public BufferedImage	icon_res_3	= Tools.readImage("/com/g2d/studio/res/icon_res_3.png");
	static public BufferedImage	icon_res_4	= Tools.readImage("/com/g2d/studio/res/icon_res_4.png");
	static public BufferedImage	icon_res_5	= Tools.readImage("/com/g2d/studio/res/icon_res_5.png");
	static public BufferedImage	icon_res_6	= Tools.readImage("/com/g2d/studio/res/icon_res_6.png");
	
	static public BufferedImage icons_bar[];
	static
	{
		BufferedImage src = Tools.readImage("/com/g2d/studio/res/icons_bar.png");	
		icons_bar = new BufferedImage[src.getWidth() / 16];
		for (int i=0; i<icons_bar.length; i++) {
			icons_bar[i] = src.getSubimage(i*16, 0, 16, 16);
		}
	}
	static public BufferedImage save_tool_bar[];
	static
	{
		BufferedImage src = Tools.readImage("/com/g2d/studio/res/save_tool_bar.png");	
		save_tool_bar = new BufferedImage[src.getWidth() / 16];
		for (int i=0; i<save_tool_bar.length; i++) {
			save_tool_bar[i] = src.getSubimage(i*16, 0, 16, 16);
		}
	}
}
