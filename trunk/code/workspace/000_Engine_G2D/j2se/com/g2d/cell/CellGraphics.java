package com.g2d.cell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Vector;

import com.cell.AScreen;
import com.cell.IGraphics;
import com.cell.IImage;

public class CellGraphics implements IGraphics 
{
	// transform constants
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;
	
	public static Font DefaultFont;
	
	public static boolean DefaultAntiallias = false;
	
	private static FontRenderContext DEFAULT_FRC;
	
	//
	
	protected Font 	CurFont 		= null;
	
	protected int font_t;
	protected int font_b;
	protected int font_h;
	
	protected int m_color 	= 0x00000000;
	
	protected Graphics2D 	m_graphics2d ;
//	protected Graphics 	m_graphics;
	
	
	
	public CellGraphics(Graphics2D graphics){
		setGraphics(graphics);
	}
	
	final synchronized public Graphics2D getGraphics() {
		return m_graphics2d;
	}
	
	final synchronized public void setGraphics(Graphics2D graphics)
	{
		m_graphics2d 	= graphics;
		setStringAntiAllias(DefaultAntiallias);
		
		if (CurFont!=null){
			setFont(CurFont);
		}
		else if (DefaultFont!=null){
			setFont(DefaultFont);
		}
		else{
			setFont(graphics.getFont());
		}
		
		DEFAULT_FRC = graphics.getFontRenderContext();
		
	}
	
	
//	protected CGraphics(Graphics2D graphics2d, Graphics graphics){
//		m_graphics2d = graphics2d;
//		m_graphics = graphics;
//		font = graphics.getFont();
////		m_graphics.setFont(font);
//	}
//	
	final synchronized public  void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		if((m_color)!=0)
		m_graphics2d.drawArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final synchronized public  void drawLine(int arg0, int arg1, int arg2, int arg3) {
		if((m_color)!=0)
		m_graphics2d.drawLine(arg0, arg1, arg2, arg3);
	}

	final synchronized public void drawRect(int arg0, int arg1, int arg2, int arg3) {
		if((m_color)!=0)
		m_graphics2d.drawRect(arg0, arg1, arg2-1, arg3-1);
	}
	
	final synchronized public  void drawString(String arg0, int arg1, int arg2) {
		if((m_color)!=0)
		m_graphics2d.drawString(arg0, arg1, arg2 + font_t);
	}

	final synchronized public  void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		if((m_color)!=0)
		m_graphics2d.fillArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final synchronized public  void fillRect(int arg0, int arg1, int arg2, int arg3) {
		if((m_color)!=0)
		m_graphics2d.fillRect(arg0, arg1, arg2, arg3);
	}
	
	final synchronized public  void fillRectAlpha(int argb, int x, int y, int width, int height){
		if(argb==0)return;
		Color old = m_graphics2d.getColor();
		m_graphics2d.setColor(new Color(argb, true));
		m_graphics2d.fillRect(x, y, width, height);
		m_graphics2d.setColor(old);
	}
	
	
	final synchronized public  void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		if((m_color)!=0)
		m_graphics2d.fillRoundRect(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	final synchronized public  void fillTriangle(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		if((m_color)!=0)
		{
			int[] x = new int[]{arg0,arg2,arg4,};
			int[] y = new int[]{arg1,arg3,arg5,};
			m_graphics2d.fillPolygon(x,y,3);
		}
	}

	final synchronized public  void setClip(int arg0, int arg1, int arg2, int arg3) {
		m_graphics2d.setClip(arg0, arg1, arg2, arg3);
	}
	
	final synchronized public  void clipRect(int arg0, int arg1, int arg2, int arg3){
		m_graphics2d.clipRect(arg0, arg1, arg2, arg3);
	}
	
	final synchronized public  void setColor(int arg0, int arg1, int arg2) {
		m_color = 0xff000000;
		m_graphics2d.setColor(new Color(arg0, arg1, arg2, 0xff));
	}
	final synchronized public  void setColor(int arg0) {
		m_color = arg0;
		m_graphics2d.setColor(new Color(arg0, true));
	}

	final synchronized public void drawRoundImage(IImage src, int x, int y,
			int width, int height, int transform) {

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
	}
	
	final synchronized public  void flush()
	{
		//synchronized (Canvas)
		{
			//IsFlush = true;
			//Canvas.repaint(0);
		}
		//Thread.yield();
	}
	
	final synchronized public  void flush(int x,int y, int w, int h)
	{
		//synchronized (Canvas)
		{
			//IsFlush = true;
			/*if(FullFlushMode){
				Canvas.repaint(0);
			}else{
				Canvas.repaint(x, y, w, h);
			}*/
		}
		//Thread.yield();
	}

	


	
	final synchronized public  int getClipX() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.x;
		return 0;
	}

	
	final synchronized public  int getClipY() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.y;
		return 0;
	}

	
	final synchronized public  int getClipHeight() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.height;
		return 0;
	}

	
	final synchronized public  int getClipWidth() 
	{
		Rectangle b = m_graphics2d.getClipBounds();
		if(b!=null)return b.width;
		return 0;
	}



	final synchronized public  void drawImage(IImage src, int x, int y, int transform) {

		 Image img = src.getSrc();
        
        if(transform==0)
        {
        	 m_graphics2d.drawImage(img, x, y, null);
        	 AScreen.FrameImageDrawed++;
        }
        else
        { 
        	int width = src.getWidth();
        	int height = src.getHeight();
        
        	AffineTransform t = new AffineTransform();
             
        	int dW = width, dH = height;
     		switch (transform) {
     		case TRANS_NONE: {
     			break;
     		}
     		case TRANS_ROT90: {
     			t.translate((double) height, 0);
     			t.rotate(Math.PI / 2);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_ROT180: {
     			t.translate(width, height);
     			t.rotate(Math.PI);
     			break;
     		}
     		case TRANS_ROT270: {
     			t.translate(0, width);
     			t.rotate(Math.PI * 3 / 2);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_MIRROR: {
     			t.translate(width, 0);
     			t.scale(-1, 1);
     			break;
     		}
     		case TRANS_MIRROR_ROT90: {
     			t.translate((double) height, 0);
     			t.rotate(Math.PI / 2);
     			t.translate((double) width, 0);
     			t.scale(-1, 1);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_MIRROR_ROT180: {
     			t.translate(width, 0);
     			t.scale(-1, 1);
     			t.translate(width, height);
     			t.rotate(Math.PI);
     			break;
     		}
     		case TRANS_MIRROR_ROT270: {
     			t.rotate(Math.PI * 3 / 2);
     			t.scale(-1, 1);
     			dW = height;
     			dH = width;
     			break;
     		}
     		default:
     			throw new IllegalArgumentException("Bad transform");
     		}
     		
     		AffineTransform savedT = m_graphics2d.getTransform();
             
             m_graphics2d.translate(x, y);
             m_graphics2d.transform(t);

             m_graphics2d.drawImage(img, 0, 0, null);
             AScreen.FrameImageDrawed++;
             
             // return to saved
             m_graphics2d.setTransform(savedT);
        }
        
	}
	
	final synchronized 
	public void drawImage(IImage src, int x, int y, int w, int h, int transform) {


		 Image img = src.getSrc();
        
        if(transform==0)
        {
        	 m_graphics2d.drawImage(img, x, y, null);
        	 AScreen.FrameImageDrawed++;
        }
        else
        { 
        	int width = src.getWidth();
        	int height = src.getHeight();
        
        	AffineTransform t = new AffineTransform();
             
        	int dW = width, dH = height;
     		switch (transform) {
     		case TRANS_NONE: {
     			break;
     		}
     		case TRANS_ROT90: {
     			t.translate((double) height, 0);
     			t.rotate(Math.PI / 2);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_ROT180: {
     			t.translate(width, height);
     			t.rotate(Math.PI);
     			break;
     		}
     		case TRANS_ROT270: {
     			t.translate(0, width);
     			t.rotate(Math.PI * 3 / 2);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_MIRROR: {
     			t.translate(width, 0);
     			t.scale(-1, 1);
     			break;
     		}
     		case TRANS_MIRROR_ROT90: {
     			t.translate((double) height, 0);
     			t.rotate(Math.PI / 2);
     			t.translate((double) width, 0);
     			t.scale(-1, 1);
     			dW = height;
     			dH = width;
     			break;
     		}
     		case TRANS_MIRROR_ROT180: {
     			t.translate(width, 0);
     			t.scale(-1, 1);
     			t.translate(width, height);
     			t.rotate(Math.PI);
     			break;
     		}
     		case TRANS_MIRROR_ROT270: {
     			t.rotate(Math.PI * 3 / 2);
     			t.scale(-1, 1);
     			dW = height;
     			dH = width;
     			break;
     		}
     		default:
     			throw new IllegalArgumentException("Bad transform");
     		}
     		
     		t.scale(((float)w) / dW, ((float)h) / dH);
     		
     		AffineTransform savedT = m_graphics2d.getTransform();
             
             m_graphics2d.translate(x, y);
             m_graphics2d.transform(t);

             m_graphics2d.drawImage(img, 0, 0, null);
             AScreen.FrameImageDrawed++;
             
             // return to saved
             m_graphics2d.setTransform(savedT);
        }
        
	
		
	}
	
    // Andres Navarro
	final  public void drawRegion(IImage src, int x_src, int y_src, int width, int height, int transform, int x_dst, int y_dst)
    {
    	 // may throw NullPointerException, this is ok
        if (x_src + width > src.getWidth() ||
        	y_src + height > src.getHeight() ||
        	width < 0 || height < 0 ||
        	x_src < 0 || y_src < 0)
        {
//        	throw new IllegalArgumentException("Area out of Image");
        	return;
        }
        
        Image img = src.getSrc();
        
//        if(transform == 0)
//        {
//        
//        }
//        else
        {
            AffineTransform t = new AffineTransform();
            
            int dW = width, dH = height;
    		switch (transform) {
    		case TRANS_NONE: {
    			break;
    		}
    		case TRANS_ROT90: {
    			t.translate((double) height, 0);
    			t.rotate(Math.PI / 2);
    			dW = height;
    			dH = width;
    			break;
    		}
    		case TRANS_ROT180: {
    			t.translate(width, height);
    			t.rotate(Math.PI);
    			break;
    		}
    		case TRANS_ROT270: {
    			t.translate(0, width);
    			t.rotate(Math.PI * 3 / 2);
    			dW = height;
    			dH = width;
    			break;
    		}
    		case TRANS_MIRROR: {
    			t.translate(width, 0);
    			t.scale(-1, 1);
    			break;
    		}
    		case TRANS_MIRROR_ROT90: {
    			t.translate((double) height, 0);
    			t.rotate(Math.PI / 2);
    			t.translate((double) width, 0);
    			t.scale(-1, 1);
    			dW = height;
    			dH = width;
    			break;
    		}
    		case TRANS_MIRROR_ROT180: {
    			t.translate(width, 0);
    			t.scale(-1, 1);
    			t.translate(width, height);
    			t.rotate(Math.PI);
    			break;
    		}
    		case TRANS_MIRROR_ROT270: {
    			t.rotate(Math.PI * 3 / 2);
    			t.scale(-1, 1);
    			dW = height;
    			dH = width;
    			break;
    		}
    		default:
    			throw new IllegalArgumentException("Bad transform");
    		}
    		
    		AffineTransform savedT = m_graphics2d.getTransform();
            
            m_graphics2d.translate(x_dst, y_dst);
            m_graphics2d.transform(t);

            m_graphics2d.drawImage(img, 0, 0, width, 
                    height, x_src, y_src, x_src + width,
                    y_src + height, null);
            AScreen.FrameImageDrawed++;
            
            // return to saved
            m_graphics2d.setTransform(savedT);
        }
        
            
    }


	final synchronized public  void drawRGB(int[] rgbData, int offset, int scanlength, 
            int x, int y, int width, int height, boolean processAlpha) {
            // this is less than ideal in terms of memory
            // but it's the easiest way
        
        if (rgbData == null)
            throw new NullPointerException();
        
        if (width == 0 || height == 0) {
        	return;
        }

        int l = rgbData.length;
        
        if (width < 0 || height < 0 || offset < 0 || offset >= l ||
                (scanlength < 0 && scanlength * (height-1) < 0) ||
                (scanlength >= 0 && scanlength * (height-1) + width-1 >= l))
                throw new ArrayIndexOutOfBoundsException();
        
        // help gc
        
    	BufferedImage buf = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		buf.setRGB(0, 0, width, height, rgbData, 0, scanlength);
        
        m_graphics2d.drawImage(buf, x, y, null);
        AScreen.FrameImageDrawed++;
    }
    

	final public  int getStringHeight() {
		return font_h;
	}

	final synchronized public  int getStringWidth(String src) {
		return m_graphics2d.getFontMetrics().stringWidth(src);
	}

	
	final synchronized public  void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY)
	{
		if (shandowColor!=0){
			m_graphics2d.setColor(new Color(shandowColor, true));
			m_graphics2d.drawString(str, x + shandowX, y + font_t + shandowY);
			m_graphics2d.setColor(new Color(m_color, true));
		}
		if((m_color)!=0)
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

	final public  void setFont(String name, int size) {
		try{
			CurFont = new Font(name,CurFont.PLAIN,size);
		}catch(Exception err){
			err.printStackTrace();
		}
		if(CurFont!=null){
			setFont(CurFont);
		}
	}
	final public  String getFontName(){
		return CurFont.getName();
	}
	final public  void setStringSize(int size) {
		setFont(CurFont.getName(), size);
	}
	
	final synchronized public  int getFontSize(){
		return CurFont.getSize();
	}
	
	final synchronized private void setFont(Font font){
		CurFont = font;
		m_graphics2d.setFont(font);
		try{
		font_t = m_graphics2d.getFontMetrics().getAscent();
		font_b = m_graphics2d.getFontMetrics().getDescent();
		font_h = m_graphics2d.getFontMetrics().getHeight();
		font_t = font_h/2 + font_t/2 - 2;
		font_b = font_h - font_t;
		}catch(Exception err){
			err.printStackTrace();
			font_h = CurFont.getSize();
		}
	}
	

	public String[] getStringLines(String text, int w, int[] out_para)
	{
		try
		{
			// calc new text space
			char ret = '\n';
			char chars[] = text.toCharArray();
			int prewPos = 0;
			Vector<String> lines = new Vector<String>();
			
			for(int i=0;i<chars.length;i++){
				if(chars[i]==ret){
					lines.addElement(new String(chars,prewPos,i-prewPos+1));
					prewPos = i + 1;
					continue;
				}
				if (getStringWidth(new String(chars,prewPos,i-prewPos+1)) > w){
					lines.addElement(new String(chars,prewPos,i-prewPos+0));
					prewPos = i + 0;
					continue;
				}
				if(i==chars.length-1){
					lines.addElement(new String(chars,prewPos,chars.length - prewPos));
					break;
				}
			}
			
			String[] texts = new String[lines.size()];
			lines.copyInto(texts);
			lines = null;
			
			return texts;
		}
		catch(Exception err)
		{
			err.printStackTrace();
			return new String[]{"(Error !)"};
		}
	}
	
	
	public StringLayer createStringLayer(String src){
		StringAttribute attr = StringAttribute.createColorAttribute(0, src.length(), m_color);
		CStringLayer layer = new CStringLayer(src, new StringAttribute[]{attr});
		return layer;
		
	}
	public StringLayer createStringLayer(String src, StringAttribute[] attributes){
		CStringLayer layer = new CStringLayer(src, attributes);
		return layer;
	}
	
	public class CStringLayer implements StringLayer
	{
		String Src;
		AttributedString AString;
		AttributedCharacterIterator AChars;
		StringAttribute[] Attributes;
		TextLayout Layout;
		int W, H; 
		private float ascent;
		
		CStringLayer(String text, StringAttribute[] attributes)
		{
			Src = text;
			AString = new AttributedString(text);
			Attributes = attributes;
			
			if (text.length()>0)
			{
				AString.addAttribute(TextAttribute.FONT, CellGraphics.this.CurFont, 0, text.length());
				AString.addAttribute(TextAttribute.SIZE, CellGraphics.this.CurFont.getSize(), 0, text.length());
				
//				if (attributes!=null){
//					for (int i=attributes.length-1; i>=0; --i){
//						TextAttribute k = null;
//						Object v = null;
//						if (attributes[i].Attr == StringAttribute.Attribute.COLOR){
//							k = TextAttribute.FOREGROUND;
//							v = new Color((Integer)attributes[i].Value);
//						}
//						else if (attributes[i].Attr == StringAttribute.Attribute.FONT){
//							k = TextAttribute.FONT;
//							v = new Font((String)attributes[i].Value,Font.PLAIN,DefaultFont.getSize());
//						}
//						else if (attributes[i].Attr == StringAttribute.Attribute.SIZE){
//							//k = TextAttribute.SIZE;
//							//v = new Float((Integer)attributes[i].Value);
//							k = TextAttribute.FONT;
//							v = new Font(DefaultFont.getName(),Font.PLAIN,(Integer)attributes[i].Value);
//						}
//						if (k!=null && v!=null){
//							if (attributes[i].Strat>=0){
//								if (attributes[i].Strat!=attributes[i].End){
//									AString.addAttribute(k, v, attributes[i].Strat, attributes[i].End);
//								}
//							}else{
//								AString.addAttribute(k, v);
//							}
//						}
//					}
//				}
				
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
							if (attr.Strat>=0 && attr.End<=Src.length()){
								if (attr.Strat!=attr.End){
									AString.addAttribute(k, v, attr.Strat, attr.End);
								}
							}else{
								//AString.addAttribute(k, v);
							}
						}
					}
				}
				
				AChars = AString.getIterator();
				Layout = new TextLayout(AChars, DEFAULT_FRC);
				W = (int)Layout.getAdvance();
				H = (int)(Layout.getAscent() + Layout.getDescent() + Layout.getLeading());
				ascent = Layout.getAscent();
			}
			
		}
		
		CStringLayer(CStringLayer src, int start, int end)
		{
			Src = src.Src.substring(start, end);
			AString = new AttributedString(src.AChars, start, end);
			AChars = AString.getIterator();
			Layout = new TextLayout(AChars, DEFAULT_FRC);
			W = (int)Layout.getAdvance();
			H = (int)(Layout.getAscent() + Layout.getDescent() + Layout.getLeading());
			ascent = Layout.getAscent();
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
			Layout.draw(((CellGraphics)g).m_graphics2d, x, y+ascent);
		}

		public int getIndex(int x, int y) {
			return 0;
		}

		public int getWidth(int start, int end) {
			return 0;
		}
		
		public StringLayer split(String regex){
			return null;
		}
		
		public StringLayer subString(int start){
			return new CStringLayer(this, start, this.Src.length());
		}
		public StringLayer subString(int start, int end){
			return new CStringLayer(this, start, end);
		}
		
		public StringLayer append(StringLayer append)
		{
			
			return null;
			
		}
		
		public StringLayer[] getStringLines(int w, int[] out_para)
		{
			try
			{
				// calc new text space
				char ret = '\n';
				char chars[] = Src.toCharArray();
				int prewPos = 0;
				Vector<StringLayer> lines = new Vector<StringLayer>();
				
				for(int i=0;i<chars.length;i++){
					if(chars[i]==ret){
						if (prewPos < i+1) {
							lines.addElement(new CStringLayer(this, prewPos, i+1));
						}
						prewPos = i + 1;
						continue;
					}
					else if (getStringWidth(new String(chars, prewPos, i-prewPos+1)) > w){
						if (prewPos<i) {
							lines.addElement(new CStringLayer(this, prewPos, i));
						}
						prewPos = i + 0;
						continue;
					}
					else if(i==chars.length-1){
						if (prewPos < chars.length) {
							lines.addElement(new CStringLayer(this, prewPos, chars.length));
						}
						break;
					}
				}
				
				StringLayer[] texts = new StringLayer[lines.size()];
				lines.copyInto(texts);
				lines = null;
				
				return texts;
			}
			catch(Exception err)
			{
				err.printStackTrace();
				return new CStringLayer[]{new CStringLayer("(Error !)", null)};
			}
		}
		

	}
	
}
