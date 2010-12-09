package com.g3d.jogl.test;
import java.io.*;
import java.nio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import javax.media.opengl.*;

public class TextureImage 
{

	// /////////////// Functions /////////////////////////

	public TextureImage(String filename)
	{
		try {
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream(filename));
		
	
			WritableRaster raster = Raster.createInterleavedRaster(
					DataBuffer.TYPE_BYTE, img.getWidth(), img.getHeight(), 4, null);
			ComponentColorModel colorModel = new ComponentColorModel(
					ColorSpace.getInstance(ColorSpace.CS_sRGB), 
					new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT,
					DataBuffer.TYPE_BYTE);
			BufferedImage bufImg = new BufferedImage(
					colorModel,
					raster, false,
					null);
	
			Graphics2D g = bufImg.createGraphics();
			AffineTransform gt = new AffineTransform();
			gt.translate(0, img.getHeight());
			gt.scale(1, -1d);
			g.transform(gt);
			g.drawImage(img, null, null);
			{
				
			}
			g.dispose();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void draw(GL gl, int x, int y) 
	{

		
	}

}
