package com.g2d.jogl.impl;

import java.io.InputStream;
import java.nio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.color.ColorSpace;

import javax.imageio.ImageIO;
import javax.media.opengl.*;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.gfx.IPalette;

public class GLImage implements com.g2d.BufferedImage
{
	private BufferedImage	m_image;

	/* OpenGL name for the sprite texture */
	final private GL		gl;
	private int[] 			gl_texture;
	private ByteBuffer 		gl_pixels;
	private int				w, h;
	
	public GLImage(GL gl, Image src)
	{
		this.gl	= gl;
		
		try 
		{
			this.m_image = GLEngine.createRaster(src.getWidth(null), src.getHeight(null));
	
			Graphics2D g = m_image.createGraphics();
			g.drawImage(src, null, null);
			g.dispose();
			
			DataBufferByte imgBuf 	= (DataBufferByte) m_image.getRaster().getDataBuffer();
			byte[] rgba 			= imgBuf.getData();
			
			init(rgba, m_image.getWidth(), m_image.getHeight());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public GLImage(GL gl, int w, int h)
	{
		this.gl	= gl;
		
		try 
		{
			this.m_image = GLEngine.createRaster(w, h);

			DataBufferByte imgBuf 	= (DataBufferByte) m_image.getRaster().getDataBuffer();
			byte[] rgba 			= imgBuf.getData();
			
			init(rgba, m_image.getWidth(), m_image.getHeight());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void init(byte[] rgba, int w, int h)
	{
		this.w = w;
		this.h = h;
		
		this.gl_pixels = ByteBuffer.wrap(rgba);
		
		this.gl_texture = new int[1];
		
		gl.glGenTextures(1, gl_texture, 0);
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	// 线形滤波
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);	// 线形滤波

		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, w, h, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, gl_pixels);
		
	}
	
	void draw(GL gl, float dx, float dy, float dw, float dh, float sx, float sy, float sw, float sh)
	{
		float cw = dw / sw;
		float ch = dw / sw;
		gl.glTranslatef(dx, dy, 0);
		{
			gl.glEnable(GL.GL_TEXTURE_2D);
			gl.glBindTexture(GL.GL_TEXTURE_2D, gl_texture[0]);
			gl.glColor4f(1, 1, 1, 1);
			gl.glBegin(GL.GL_QUADS);
			{
				gl.glTexCoord2f(0f, 0f); gl.glVertex2f(  0,  0);
				gl.glTexCoord2f(cw, 0f); gl.glVertex2f( dw,  0);
				gl.glTexCoord2f(cw, ch); gl.glVertex2f( dw, dh);
				gl.glTexCoord2f(0f, ch); gl.glVertex2f(  0, dh);
			}
			gl.glEnd();
			gl.glDisable(GL.GL_TEXTURE_2D);
		}
		gl.glTranslatef(-dx, -dy, 0);
	}
	
	public void despose() 
	{
		if (gl_texture != null) {
			gl.glDeleteTextures(1, gl_texture, 0);
			gl_texture = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			if (m_image != null) {
				m_image.flush();
			}
		} finally {
			despose();
		}
	}

	public BufferedImage getSrc() {
		return m_image;
	}

	
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	


	
	
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	public IImage newInstance() {
		return new GLImage(gl, m_image);
	}
	
	public IImage createBuffer(int width, int height) {
		return new GLImage(gl, width, height);
	}

	public IImage subImage(int x, int y, int width, int height) {
		return new GLImage(gl, m_image.getSubimage(x, y, width, height));
	}
	
	public IImage resize(int newWidth, int newHeight) {
		return new GLImage(gl, m_image.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH));
	}

	public IGraphics getIGraphics() {
		return new GLGraphics2D(gl);
	}

	public int getHeight() {
		return h;
	}

	public int getWidth() {
		return w;
	}

	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y,
			int width, int height) {
		m_image.getRGB(x, y, width, height, rgbData, offset, scanlength);
	}

	public int setMode(int mode) {
		return mode;
	}
	
	public int getColorModel() {
		return COLOR_MODEL_DIRECT;
	}

	public IPalette getPalette() {
		return null;
	}

	public void setPalette(IPalette palette) {
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public com.g2d.Graphics2D createGraphics() {
		return getGraphics();
	}
	
	@Override
	public com.g2d.Graphics2D getGraphics() {
		return new GLGraphics2D(gl);
	}
	
	@Override
	public int[] getRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
		return m_image.getRGB(startX, startY, w, h, rgbArray, offset, scansize);
	}
	
	@Override
	public int getRGB(int x, int y) {
		return m_image.getRGB(x, y);
	}
	
	@Override
	public com.g2d.BufferedImage getSubimage(int x, int y, int w, int h) {
		return (com.g2d.BufferedImage)subImage(x, y, w, h);
	}
	
	@Override
	public void setRGB(int startX, int startY, int w, int h, int[] rgbArray, int offset, int scansize) {
		m_image.setRGB(startX, startY, w, h, rgbArray, offset, scansize);
	}
	@Override
	public void setRGB(int x, int y, int argb) {
		m_image.setRGB(x, y, argb);
	}
	
	@Override
	public com.g2d.BufferedImage getScaledInstance(int w, int h) {
		return (com.g2d.BufferedImage)resize(w, h);
	}



}
