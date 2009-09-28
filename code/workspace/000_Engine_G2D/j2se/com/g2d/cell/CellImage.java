package com.g2d.cell;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.cell.CObject;
import com.cell.IGraphics;
import com.cell.IImage;


public class CellImage implements IImage , ImageObserver
{
	GraphicsEnvironment ge 		= GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice gd 			= ge.getDefaultScreenDevice();
	GraphicsConfiguration gc 	= gd.getDefaultConfiguration();
	
	protected BufferedImage m_image;
	
	protected int m_mode = MODE_RAM;;

	public CellImage(Image image)
	{
		m_image = createVMBuffer(image,m_mode);
	}

	public CellImage()
	{
		m_image = createVMBuffer(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),m_mode);
	}
	
	public CellImage(String file) {
		try {
			m_image = createVMBuffer(ImageIO.read(CObject.AppBridge.getResource(file)), m_mode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CellImage(InputStream is)
	{
		try{
			m_image = createVMBuffer(ImageIO.read(is), m_mode);
		}catch(Exception err){
			err.printStackTrace();
			System.err.println("CImage.<init> : Failed !");
		}

	}
	public IImage newInstance()
	{
		return new CellImage(m_image);
	}
	
	public BufferedImage getSrc() 
	{
		return m_image;
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	protected final BufferedImage createVMBuffer(Image src, int mode)
	{
		try
		{
			BufferedImage buf = null;
			
			buf = gc.createCompatibleImage(
					src.getWidth(this), 
					src.getHeight(this), 
					Transparency.TRANSLUCENT);
			
			Graphics2D g = buf.createGraphics();
			g.drawImage(src, 0, 0, this);
			g.dispose();
			return buf;
			
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
	}


	
	public void createBuffer(int width, int height) 
	{
		m_image = createVMBuffer(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB),m_mode);
	}

	
	public IImage subImage(int x, int y, int width, int height)
	{
		return (new CellImage(m_image.getSubimage(x, y, width, height)));
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
			g.drawImage(m_image, 0, 0, newWidth, newHeight, this);
			g.dispose();
			
			return new CellImage(buf);
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}

		return null;
	}
	
	public IGraphics getIGraphics()
	{
		return new CellGraphics((Graphics2D)m_image.getGraphics());
	}

	public int getHeight() 
	{
		return m_image.getHeight();
	}

	public int getWidth()
	{
		return m_image.getWidth();
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
		m_image.getRGB(x, y, width, height, rgbData, offset, scanlength);
	}

	public int setMode(int mode)
	{
		m_mode = mode;
		return m_mode;
	}
}
