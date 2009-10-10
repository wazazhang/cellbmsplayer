package com.cell.bms;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.g2d.Tools;

public class JAwtImage implements IDefineImage
{
	BufferedImage buffer;
	
	public JAwtImage(BMSFile bms, String image) {
		try{
			String path = bms.bms_dir+"/"+image;
			buffer = Tools.readImage(path);
//			System.out.println("create JMFImage : " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Image getImage() {
		return buffer;
	}	
	
	@Override
	public void dispose() {}
	
}
