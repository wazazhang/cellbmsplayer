package com.cell.j2se;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Vector;

import com.cell.CMath;
import com.cell.gfx.AScreen;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.j2se.AlphaComposite.AlphaBlendMode;
import com.cell.j2se.BlendComposite.BlendingMode;


public class CGraphics implements IGraphics 
{
	public static Font DefaultFont;

	protected int	font_t;
	protected int	font_b;
	protected int	font_h;
	
	protected Graphics2D 	m_graphics2d ;

	
	public CGraphics(Graphics2D graphics){
		setGraphics(graphics);
	}

	public void dispose() {
		m_graphics2d.dispose();
	}
	
	final  public void setGraphics(Graphics2D graphics)
	{
		m_graphics2d 	= graphics;

		if (DefaultFont!=null){
			setFont(DefaultFont);
		}
	}
	
	final  public Graphics2D getGraphics()
	{
		return m_graphics2d;
	}
	
	
	final  public  void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		m_graphics2d.drawArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final  public  void drawLine(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.drawLine(arg0, arg1, arg2, arg3);
	}

	final  public void drawRect(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.drawRect(arg0, arg1, arg2-1, arg3-1);
	}
	
	final  public  void drawString(String arg0, int arg1, int arg2) {
		m_graphics2d.drawString(arg0, arg1, arg2 + font_t);
	}

	final  public  void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		m_graphics2d.fillArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final  public  void fillRect(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.fillRect(arg0, arg1, arg2, arg3);
	}
	
	final  public  void fillRectAlpha(int argb, int x, int y, int width, int height){
		if(argb==0)return;
		Color old = m_graphics2d.getColor();
		m_graphics2d.setColor(new Color(argb, true));
		m_graphics2d.fillRect(x, y, width, height);
		m_graphics2d.setColor(old);
	}
	
	
	final  public  void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		m_graphics2d.fillRoundRect(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final  public  void fillTriangle(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		int[] x = new int[]{arg0,arg2,arg4,};
		int[] y = new int[]{arg1,arg3,arg5,};
		m_graphics2d.fillPolygon(x,y,3);
	}

	final  public  void setClip(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.setClip(arg0, arg1, arg2, arg3);
	}
	
	final  public  void clipRect(int arg0, int arg1, int arg2, int arg3){
		m_graphics2d.clipRect(arg0, arg1, arg2, arg3);
	}
	
	final  public  void setColor(int arg0, int arg1, int arg2) {
		m_graphics2d.setColor(new Color(arg0, arg1, arg2, 0xff));
	}
	final  public  void setColor(int arg0) {
		m_graphics2d.setColor(new Color(arg0, true));
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
		
		Image img = src.getSrc();

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
     
		Image img = src.getSrc();
        
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
		
		Image img = src.getSrc();

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
    

	final public  int getStringHeight() {
		return font_h;
	}

	final  public  int getStringWidth(String src) {
		return m_graphics2d.getFontMetrics().stringWidth(src);
	}

	
	final  public  void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY)
	{
		Color oldcolor = m_graphics2d.getColor();
		m_graphics2d.setColor(new Color(shandowColor, true));
		m_graphics2d.drawString(str, x + shandowX, y + font_t + shandowY);
		m_graphics2d.setColor(oldcolor);
		m_graphics2d.drawString(str, x, y + font_t);
	}

	final public  void setStringAntiAllias(boolean antiallias)
	{
		if(antiallias){
			m_graphics2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}else{
			m_graphics2d.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}

	final public  void setFont(String name, int size) 
	{
		try {
			setFont(new Font(name, Font.PLAIN, size));
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	final public  String getFontName()
	{
		return m_graphics2d.getFont().getName();
	}
	
	final public  void setStringSize(int size)
	{
		setFont(m_graphics2d.getFont().getName(), size);
	}
	
	final  public  int getFontSize()
	{
		return m_graphics2d.getFont().getSize();
	}
	
	final  private void setFont(Font font)
	{
		m_graphics2d.setFont(font);
		try{
			font_t = m_graphics2d.getFontMetrics().getAscent();
			font_b = m_graphics2d.getFontMetrics().getDescent();
			font_h = m_graphics2d.getFontMetrics().getHeight();
			font_t = font_h/2 + font_t/2 - 2;
			font_b = font_h - font_t;
		}catch(Exception err){
			err.printStackTrace();
			font_h = m_graphics2d.getFont().getSize();
		}
	}
	

	public String[] getStringLines(String text, int w, int[] out_para)
	{
		try 
		{
			AttributedString atext = new AttributedString(text);
			atext.addAttribute(TextAttribute.FONT, m_graphics2d.getFont(), 0, text.length());
			atext.addAttribute(TextAttribute.SIZE, m_graphics2d.getFont(), 0, text.length());
			
			Vector<String> lines = new Vector<String>();
			LineBreakMeasurer textMeasurer = new LineBreakMeasurer(
					atext.getIterator(),
					m_graphics2d.getFontRenderContext());

			while (
				textMeasurer.getPosition() >= 0 && 
				textMeasurer.getPosition() < text.length())
			{
				int curr_pos = textMeasurer.getPosition();
				int next_pos = curr_pos;
				int limit = text.indexOf('\n', curr_pos);
				if (limit >= curr_pos) {
					next_pos = textMeasurer.nextOffset(w, limit + 1, false);
				} else {
					next_pos = textMeasurer.nextOffset(w);
				}
				lines.addElement(text.substring(curr_pos, next_pos));
				textMeasurer.setPosition(next_pos);
			}

			return lines.toArray(new String[lines.size()]);
		} 
		catch (Throwable err)
		{
			err.printStackTrace();
			return new String[]{"(Error !)"};
		}
		
		
//		try
//		{
//			// calc new text space
//			char ret = '\n';
//			char chars[] = text.toCharArray();
//			int prewPos = 0;
//			Vector<String> lines = new Vector<String>();
//			
//			for(int i=0;i<chars.length;i++){
//				if(chars[i]==ret){
//					lines.addElement(new String(chars,prewPos,i-prewPos+1));
//					prewPos = i + 1;
//					continue;
//				}
//				if (getStringWidth(new String(chars,prewPos,i-prewPos+1)) > w){
//					lines.addElement(new String(chars,prewPos,i-prewPos+0));
//					prewPos = i + 0;
//					continue;
//				}
//				if(i==chars.length-1){
//					lines.addElement(new String(chars,prewPos,chars.length - prewPos));
//					break;
//				}
//			}
//			
//			String[] texts = new String[lines.size()];
//			lines.copyInto(texts);
//			lines = null;
//			
//			return texts;
//		}
//		catch(Exception err)
//		{
//			err.printStackTrace();
//			return new String[]{"(Error !)"};
//		}
	}
	
	
	public StringLayer createStringLayer(String src){
		StringAttribute attr = StringAttribute.createColorAttribute(0, src.length(), m_graphics2d.getColor().getRGB());
		CStringLayer layer = new CStringLayer(src, new StringAttribute[]{attr});
		return layer;
		
	}
	
	public StringLayer createStringLayer(String src, StringAttribute[] attributes){
		CStringLayer layer = new CStringLayer(src, attributes);
		return layer;
	}
	

	static StringAttribute[] attributesLink(CStringLayer s1, CStringLayer s2)
	{
		ArrayList<StringAttribute> attributes = new ArrayList<StringAttribute>();
		if (s1.Attributes!=null) {
			for (StringAttribute attr : s1.Attributes) {
				attr = new StringAttribute(attr);
				attributes.add(attr);
			}
		}
		if (s2.Attributes!=null) {
			for (StringAttribute attr : s2.Attributes) {
				attr = new StringAttribute(attr);
				attributes.add(attr);
				attr.Start += s1.Src.length();
				attr.End += s1.Src.length();
			}
		}
		return attributes.toArray(new StringAttribute[attributes.size()]);
	}
	
	static StringAttribute[] attributesSub(CStringLayer src, int start, int end)
	{
		int dst_len = end - start;
		ArrayList<StringAttribute> attributes = new ArrayList<StringAttribute>();
		if (src.Attributes!=null) {
			for (StringAttribute attr : src.Attributes) {
				attr = new StringAttribute(attr);
				if (CMath.intersectRect(start, 1, end, 1, attr.Start, 1, attr.End, 1)) {
					attributes.add(attr);
					attr.Start	-= start;
					attr.End	-= start;
					attr.Start	= Math.max(attr.Start, 0);
					attr.End	= Math.min(attr.End, dst_len);
				}
			}
		}
		return attributes.toArray(new StringAttribute[attributes.size()]);
	}
	
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
	
	
	class CStringLayer implements StringLayer
	{
		final String 						Src;
		final AttributedString 				AString;
		final AttributedCharacterIterator 	AChars;
		final StringAttribute[] 			Attributes;
		final TextLayout 					Layout;
		final int 							W, H; 
		final private float 				ascent;
		
		CStringLayer(String text, StringAttribute[] attributes)
		{
			Src 		= text;
			AString 	= new AttributedString(text);
			Attributes 	= attributes;
			
			if (text.length()>0)
			{
				AString.addAttribute(TextAttribute.FONT, m_graphics2d.getFont(), 0, text.length());
				AString.addAttribute(TextAttribute.SIZE, m_graphics2d.getFont(), 0, text.length());

				if (attributes!=null)
				{
					for (StringAttribute attr : attributes)
					{
						TextAttribute k = null;
						Object v = null;
						switch(attr.Attr)
						{
						case COLOR:
							k = TextAttribute.FOREGROUND;
							v = new Color((Integer) attr.Value);
							break;
						case FONT:
							k = TextAttribute.FONT;
							v = new Font((String) attr.Value, Font.PLAIN, DefaultFont.getSize());
							break;
						case SIZE:
							k = TextAttribute.FONT;
							v = new Font(DefaultFont.getName(), Font.PLAIN, (Integer) attr.Value);
							break;
						}
						if (k!=null && v!=null){
							if (attr.Start>=0 && attr.End<=Src.length()){
								if (attr.Start!=attr.End){
									AString.addAttribute(k, v, attr.Start, attr.End);
								}
							}else{
								//AString.addAttribute(k, v);
							}
						}
					}
				}
				
				AChars = AString.getIterator();
				Layout = new TextLayout(AChars, m_graphics2d.getFontRenderContext());
				W = (int)Layout.getAdvance();
				H = (int)(Layout.getAscent() + Layout.getDescent() + Layout.getLeading());
				ascent = Layout.getAscent();
			}
			else
			{
				AChars = null;
				Layout = null;
				W = 0;
				H = 0;
				ascent = 0;
			}
		}
		
		CStringLayer(CStringLayer src, int start, int end)
		{
			this(src.Src.substring(start, end), attributesSub(src, start, end));
		}
		
		CStringLayer(CStringLayer s1, CStringLayer s2){
			this(s1.Src + s2.Src, attributesLink(s1, s2));
		}
		
		
		public StringAttribute[] getAttributes(){
			return Attributes;
		}
		
		public String getString(){
			return Src;
		}
		public int getWidth(){
			return W;
		}
		public int getHeight(){
			return H;
		}
		public void render(IGraphics g, int x, int y){
			if (Layout!=null)
			Layout.draw(((CGraphics)g).m_graphics2d, x, y+ascent);
		}
//
//		public int getIndex(int x, int y) {
//			return 0;
//		}
//
//		public int getWidth(int start, int end) {
//			return 0;
//		}
//		
//		public StringLayer split(String regex) {
//			return null;
//		}
		
		public StringLayer subString(int start){
			return new CStringLayer(this, start, this.Src.length());
		}
		public StringLayer subString(int start, int end){
			return new CStringLayer(this, start, end);
		}
		
		public StringLayer concat(StringLayer append) {
			return new CStringLayer(this, (CStringLayer)append);
		}
		
		public StringLayer[] getStringLines(int w, int[] out_para)
		{
			try 
			{
				Vector<StringLayer> lines = new Vector<StringLayer>();
				LineBreakMeasurer textMeasurer = new LineBreakMeasurer(
						AString.getIterator(),
						m_graphics2d.getFontRenderContext());
	
				while (
					textMeasurer.getPosition() >= 0 && 
					textMeasurer.getPosition() < Src.length())
				{
					int curr_pos = textMeasurer.getPosition();
					int next_pos = curr_pos;
					int limit = Src.indexOf('\n', curr_pos);
					if (limit >= curr_pos) {
						next_pos = textMeasurer.nextOffset(w, limit + 1, false);
					} else {
						next_pos = textMeasurer.nextOffset(w);
					}
					lines.addElement(new CStringLayer(this, curr_pos, next_pos));
					textMeasurer.setPosition(next_pos);
				}

				return lines.toArray(new StringLayer[lines.size()]);
			} 
			catch (Throwable err)
			{
				err.printStackTrace();
				return new CStringLayer[]{new CStringLayer("(Error !)", null)};
			}
			
			
//			try
//			{
//				// calc new text space
//				char ret = '\n';
//				char chars[] = Src.toCharArray();
//				int prewPos = 0;
//				Vector<StringLayer> lines = new Vector<StringLayer>();
//				
//				for (int i = 0; i < chars.length; i++) {
//					if (chars[i] == ret) {
//						if (prewPos < i+1) {
//							lines.addElement(new CStringLayer(this, prewPos, i+1));
//						}
//						prewPos = i + 1;
//						continue;
//					}
//					else if (getStringWidth(new String(chars, prewPos, i - prewPos + 1)) > w) {
//						if (prewPos < i) {
//							lines.addElement(new CStringLayer(this, prewPos, i));
//						}
//						prewPos = i + 0;
//						continue;
//					}
//					else if (i == chars.length - 1) {
//						if (prewPos < chars.length) {
//							lines.addElement(new CStringLayer(this, prewPos, chars.length));
//						}
//						break;
//					}
//				}
//				
//				StringLayer[] texts = new StringLayer[lines.size()];
//				lines.copyInto(texts);
//				lines = null;
//				
//				return texts;
//			}
//			catch(Exception err)
//			{
//				err.printStackTrace();
//				return new CStringLayer[]{new CStringLayer("(Error !)", null)};
//			}
			
			
		}
		

	}
	
}
