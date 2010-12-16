package com.cell.j2se;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;

import com.cell.CMath;
import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.j2se.AlphaComposite.AlphaBlendMode;
import com.cell.j2se.BlendComposite.BlendingMode;

/**
 * 仅支持图片绘制
 * @author WAZA
 */
public class CGraphicsImage implements IGraphics 
{
	protected Graphics2D 	m_graphics2d ;

	public CGraphicsImage(Graphics2D graphics){
		setGraphics(graphics);
	}

	public void dispose() {
		m_graphics2d.dispose();
	}
	
	final public void setGraphics(Graphics2D graphics) {
		m_graphics2d = graphics;
	}

	final public Graphics2D getGraphics() {
		return m_graphics2d;
	}
	
	final  public  void setClip(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.setClip(arg0, arg1, arg2, arg3);
	}

	final  public  void clipRect(int arg0, int arg1, int arg2, int arg3){
		m_graphics2d.clipRect(arg0, arg1, arg2, arg3);
	}
	

	final  public void drawRoundImage(IImage src, int x, int y, int width, int height, int transform, int blend_mode, float blend_alpha) 
	{
		Composite saved_composite = m_graphics2d.getComposite();   
		
		if (blend_mode != IGraphics.BLEND_MODE_NONE)
		{
			Composite toset_composite = this.getCompositeFrom(blend_mode, blend_alpha);			
			if (toset_composite != null)
				m_graphics2d.setComposite(toset_composite);
		}	
		
		Image img = ((CImage)src).getSrc();

		Rectangle rect = m_graphics2d.getClipBounds();

		m_graphics2d.clipRect(x, y, width, height);

		int w = src.getWidth();
		int h = src.getHeight();

		for (int dx = 0; dx < width;) {
			for (int dy = 0; dy < height;) {
				m_graphics2d.drawImage(img, x + dx, y + dy, null);
				AScreen.FrameImageDrawed++;
				dy += h;
			}
			dx += w;
		}

		m_graphics2d.setClip(rect);
		
		m_graphics2d.setComposite(saved_composite);		
	}
	
	private Stack<java.awt.Shape> stack_clip = new Stack<java.awt.Shape>();

	@Override
	public void pushClip() {
		stack_clip.push(m_graphics2d.getClip());
	}
	
	@Override
	public void popClip() {
		m_graphics2d.setClip(stack_clip.pop());
	}
	
	final  public  int getClipX() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.x;
		return 0;
	}

	
	final  public  int getClipY() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.y;
		return 0;
	}

	
	final  public  int getClipHeight() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.height;
		return 0;
	}

	
	final  public  int getClipWidth() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.width;
		return 0;
	}

	final public void transform(int transform, int width, int height)
	{
		switch (transform) 
		{
		case TRANS_ROT90: {
			m_graphics2d.translate(height, 0);
			m_graphics2d.rotate(ANGLE_90);
			break;
		}
		case TRANS_ROT180: {
			m_graphics2d.translate(width, height);
			m_graphics2d.rotate(Math.PI);
			break;
		}
		case TRANS_ROT270: {
			m_graphics2d.translate(0, width);
			m_graphics2d.rotate(ANGLE_270);
			break;
		}
		case TRANS_MIRROR: {
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT90: {
			m_graphics2d.translate(height, 0);
			m_graphics2d.rotate(ANGLE_90);
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT180: {
			m_graphics2d.translate(width, 0);
			m_graphics2d.scale(-1, 1);
			m_graphics2d.translate(width, height);
			m_graphics2d.rotate(Math.PI);
			break;
		}
		case TRANS_MIRROR_ROT270: {
			m_graphics2d.rotate(ANGLE_270);
			m_graphics2d.scale(-1, 1);
			break;
		}
		}
	}
	

	final  public  void drawImage(IImage src, int x, int y, int transform, int blend_mode, float blend_alpha) 
	{
		Composite saved_composite = m_graphics2d.getComposite();   
		
		if (blend_mode != IGraphics.BLEND_MODE_NONE)
		{
			Composite toset_composite = this.getCompositeFrom(blend_mode, blend_alpha);			
			if (toset_composite != null)
				m_graphics2d.setComposite(toset_composite);
		}      
		
		Image img = ((CImage)src).getSrc();
        
        if (transform == 0)
        {
        	 m_graphics2d.drawImage(img, x, y, null);
        }
        else
        {
			AffineTransform savedT = m_graphics2d.getTransform();
			m_graphics2d.translate(x, y);
			transform(transform, src.getWidth(), src.getHeight());
			m_graphics2d.drawImage(img, 0, 0, null);
			m_graphics2d.setTransform(savedT);

		}
        
		m_graphics2d.setComposite(saved_composite);        
        
        AScreen.FrameImageDrawed++;
	}
	
	final  public void drawRegion(IImage src, int x_src, int y_src, int width, int height, int transform, int blend_mode, float blend_alpha, int x_dst, int y_dst)
    {
		Composite saved_composite = m_graphics2d.getComposite();   
		
		if (blend_mode != IGraphics.BLEND_MODE_NONE)
		{
			Composite toset_composite = this.getCompositeFrom(blend_mode, blend_alpha);			
			if (toset_composite != null)
				m_graphics2d.setComposite(toset_composite);
		}		
		
		Image img = ((CImage)src).getSrc();

		AffineTransform savedT = m_graphics2d.getTransform();
		m_graphics2d.translate(x_dst, y_dst);
		transform(transform, width, height);
		m_graphics2d.drawImage(img, 0, 0, width, height, x_src, y_src, x_src + width, y_src + height, null);
		m_graphics2d.setTransform(savedT);
		
		m_graphics2d.setComposite(saved_composite); 		
		
		AScreen.FrameImageDrawed++;

	}


	final  public  void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) 
	{
        if (rgbData == null){
        	return;
        }
        if (width == 0 || height == 0) {
        	return;
        }

    	BufferedImage buf = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		buf.setRGB(0, 0, width, height, rgbData, 0, scanlength);
        
        m_graphics2d.drawImage(buf, x, y, null);
        AScreen.FrameImageDrawed++;
    }
    

	final  public  void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}
	final  public  void drawLine(int arg0, int arg1, int arg2, int arg3) {}
	final  public void drawRect(int arg0, int arg1, int arg2, int arg3) {}
	final  public  void drawString(String arg0, int arg1, int arg2) {}
	final  public  void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}
	final  public  void fillRect(int arg0, int arg1, int arg2, int arg3) {}
	final  public  void fillRectAlpha(int argb, int x, int y, int width, int height){}
	final  public  void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}
	final  public  void fillTriangle(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {}
	final  public  void setColor(int arg0, int arg1, int arg2) {}
	final  public  void setColor(int arg0) {}

	final public int getStringHeight() {return 1;}
	final public int getStringWidth(String src) {return 1;}
	final public void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY) {}
	final public  void setStringAntiAllias(boolean antiallias){}
	final public  void setFont(String name, int size) {}
	final public  String getFontName(){return "";}
	final public  void setStringSize(int size){}
	final  public  int getFontSize(){return 1;}
	public String[] getStringLines(String text, int w, int[] out_para){return new String[]{};}
	public StringLayer createStringLayer(String src){return null;}
	public StringLayer createStringLayer(String src, StringAttribute[] attributes){return null;}
	
	
	protected final Composite getCompositeFrom(int blend_mode, float blend_alpha)
	{
		Composite composite = null;
		
		if (blend_mode != IGraphics.BLEND_MODE_NONE)
		{
			if (blend_mode < IGraphics.BLEND_MODE_ALPHA_CLEAR)
			{
				BlendingMode mode = BlendingMode.values()[blend_mode];
				composite = BlendComposite.getInstance(mode, blend_alpha);
			}
			else
			{
				AlphaBlendMode mode = AlphaBlendMode.values()[blend_mode-IGraphics.BLEND_MODE_ALPHA_CLEAR];
				if (mode != null)
					composite = java.awt.AlphaComposite.getInstance(mode.getValue(), blend_alpha);
			}
		}
		
		return composite;
	}	
	
}
