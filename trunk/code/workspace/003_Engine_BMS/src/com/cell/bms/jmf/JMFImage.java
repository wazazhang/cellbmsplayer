package com.cell.bms.jmf;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.cell.bms.BMSFile;
import com.cell.bms.IImage;
import com.g2d.Tools;

public class JMFImage implements IImage
{
	BufferedImage buffer;
	
	public JMFImage(BMSFile bms, String image) {
		try{
			String path = bms.bms_dir+"/"+image;
			buffer = Tools.readImage(path);
			System.out.println("create JMFImage : " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Image getImage() {
		return buffer;
	}
}
