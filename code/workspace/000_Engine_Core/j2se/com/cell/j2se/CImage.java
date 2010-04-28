package com.cell.j2se;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cell.CObject;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;


public class CImage implements IImage 
{
	static GraphicsEnvironment 		ge	= GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice 			gd	= ge.getDefaultScreenDevice();
	static GraphicsConfiguration	gc	= gd.getDefaultConfiguration();

	private BufferedImage m_image;
	
//	private VolatileImage v_image;
	
	public CImage(Image image)
	{
		m_image = createBuffer(image);
	}
	
	public CImage()
	{
		m_image = createBuffer(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
	}
	
	public CImage(String file) 
	{
		try{
			m_image = createBuffer(ImageIO.read(CObject.getAppBridge().getResource(file)));
		}catch(Exception err){
			err.printStackTrace();
			System.err.println("CImage.<init> :  File="+file+" Failed !");
		}
	}
	
	public CImage(InputStream is)
	{
		try{
			m_image = createBuffer(ImageIO.read(is));
		}catch(Exception err){
			err.printStackTrace();
			System.err.println("CImage.<init> : Failed !");
		}

	}
	
	private final BufferedImage createBuffer(Image src)
	{
		try
		{
			m_image = gc.createCompatibleImage(
					src.getWidth(null), 
					src.getHeight(null), 
					Transparency.TRANSLUCENT);
			Graphics2D g = m_image.createGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			
//			v_image = gc.createCompatibleVolatileImage(
//					src.getWidth(null), 
//					src.getHeight(null), 
//					Transparency.TRANSLUCENT);
//			Graphics2D g2d = v_image.createGraphics();
//			g2d.drawImage(m_image, 0, 0, null);
//			g2d.dispose();
			
			return m_image;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
//		if (v_image != null) {
//			v_image.flush();
//		}
		if (m_image != null) {
			m_image.flush();
		}
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	
	public IImage newInstance()
	{
		IImage ret = subImage(0, 0, getWidth(), getHeight());
		return ret;
	}
	
	public Image getSrc() 
	{
//		if (v_image.validate(g.getDeviceConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE) 
//		{
//			v_image.flush();
//			v_image = gc.createCompatibleVolatileImage(getWidth(), getHeight(), Transparency.TRANSLUCENT);
//			Graphics2D g2d = v_image.createGraphics();
//			g2d.drawImage(m_image, 0, 0, null);
//			g2d.dispose();
//		}
//		
		return m_image;
	}
	
	
	
	public void createBuffer(int width, int height) 
	{
		m_image = createBuffer(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
	}

	public IImage subImage(int x, int y, int width, int height)
	{
		return (new CImage(m_image.getSubimage(x, y, width, height)));
	}
	
	public IImage resize(int newWidth, int newHeight)
	{
		try
		{
			BufferedImage buf = gc.createCompatibleImage(
					newWidth, 
					newHeight, 
					Transparency.TRANSLUCENT);

			Graphics2D g = buf.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.drawImage(m_image, 0, 0, newWidth, newHeight, null);
			g.dispose();
			return new CImage(buf);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}

		return null;
	}
	
	public IGraphics getIGraphics() 
	{
		return new CGraphics((Graphics2D)m_image.getGraphics());
	}

	public int getHeight()
	{
		return m_image.getHeight();
	}

	public int getWidth()
	{
		return m_image.getWidth();
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) 
	{
		m_image.getRGB(x, y, width, height, rgbData, offset, scanlength);
	}

	public int setMode(int mode) 
	{
		return mode;
	}
}
