package com.cell.gfx;


public interface IGraphics
{
	final static public double ANGLE_90		= Math.PI / 2;
	final static public double ANGLE_270	= Math.PI * 3 / 2;
	
	
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;
    
    public static final int BLEND_MODE_NONE = 0;
    public static final int BLEND_MODE_AVERAGE = 1;
    public static final int BLEND_MODE_MULTIPLY = 2;
    public static final int BLEND_MODE_SCREEN = 3;
    public static final int BLEND_MODE_DARKEN = 4;
    public static final int BLEND_MODE_LIGHTEN = 5;
    public static final int BLEND_MODE_OVERLAY = 6;
    public static final int BLEND_MODE_HARD_LIGHT = 7;
    public static final int BLEND_MODE_SOFT_LIGHT = 8;
    public static final int BLEND_MODE_DIFFERENCE = 9;
    public static final int BLEND_MODE_NEGATION = 10;
    public static final int BLEND_MODE_EXCLUSION = 11;
    public static final int BLEND_MODE_COLOR_DODGE = 12;
    public static final int BLEND_MODE_INVERSE_COLOR_DODGE = 13;
    public static final int BLEND_MODE_SOFT_DODGE = 14;
    public static final int BLEND_MODE_COLOR_BURN = 15;
    public static final int BLEND_MODE_INVERSE_COLOR_BURN = 16;
    public static final int BLEND_MODE_SOFT_BURN = 17;
    public static final int BLEND_MODE_REFLECT = 18;
    public static final int BLEND_MODE_GLOW = 19;
    public static final int BLEND_MODE_FREEZE = 20;
    public static final int BLEND_MODE_HEAT = 21;
    public static final int BLEND_MODE_ADD = 22;
    public static final int BLEND_MODE_SUBTRACT = 23;
    public static final int BLEND_MODE_STAMP = 24;
    public static final int BLEND_MODE_RED = 25;
    public static final int BLEND_MODE_GREEN = 26;
    public static final int BLEND_MODE_BLUE = 27;
    public static final int BLEND_MODE_HUE = 28;
    public static final int BLEND_MODE_SATURATION = 29;
    public static final int BLEND_MODE_COLOR = 30;
    public static final int BLEND_MODE_LUMINOSITY = 31;
    
    public static final int BLEND_MODE_ALPHA_CLEAR = 101;
    public static final int BLEND_MODE_ALPHA_DST = 102;
    public static final int BLEND_MODE_ALPHA_DST_ATOP = 103;
    public static final int BLEND_MODE_ALPHA_DST_IN = 104;
    public static final int BLEND_MODE_ALPHA_DST_OUT = 105;
    public static final int BLEND_MODE_ALPHA_DST_OVER = 106;
    public static final int BLEND_MODE_ALPHA_SRC = 107;
    public static final int BLEND_MODE_ALPHA_SRC_ATOP = 108;
    public static final int BLEND_MODE_ALPHA_SRC_IN = 109;
    public static final int BLEND_MODE_ALPHA_SRC_OUT = 110;
    public static final int BLEND_MODE_ALPHA_SRC_OVER = 111;
    public static final int BLEND_MODE_ALPHA_XOR = 112;
	
    public void dispose();
    
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle); 
	
	public void drawImage(IImage img, int x, int y, int transform, int blend_mode, float blend_alpha); 
	
	//public void drawImage(IImage img, int x, int y, int w, int h, int transform); 
	
	public void drawRoundImage(IImage img, int x, int y, int widht, int height, int transform, int blend_mode, float blend_alpha); 
	
	public void drawLine(int x1, int y1, int x2, int y2); 
	public void drawRect(int x, int y, int width, int height); 
	public void drawRegion(IImage src, int x_src, int y_src, int width, int height, int transform, int blend_mode, float blend_alpha, int x_dest, int y_dest); 
	public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha); 
	public void drawString(String str, int x, int y); 
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle); 
	public void fillRectAlpha(int argb, int x, int y, int width, int height); 
	public void fillRect(int x, int y, int width, int height); 
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3); 
	
	public int getStringWidth(String src);
	public int getStringHeight();
	
	public int getClipHeight(); 
	public int getClipWidth(); 
	public int getClipX(); 
	public int getClipY(); 
	
	public void setClip(int x, int y, int width, int height); 
	public void clipRect(int x, int y, int width, int height);
	
	public void setColor(int RGB); 
	public void setColor(int red, int green, int blue); 
	
	public void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY);
	
	public void setFont(String name, int size);
	public String getFontName();
	public int getFontSize();
	
	public void setStringAntiAllias(boolean antiallias);
	
//	public IImage createBuffer(int argb, int width, int height);
//	
//	public IImage createBuffer(int width, int height);
//	
//	public IImage createImage(byte[] data);
	/*
	public void flush();
	
	public void flush(int x,int y, int w, int h);
	*/
	
	public StringLayer createStringLayer(String src);
	public StringLayer createStringLayer(String src, StringAttribute[] attributes);
	
	public String[] getStringLines(String text, int w, int[] out_para);
	
	public static class StringAttribute
	{
		public static enum Attribute{
			FONT(),
			SIZE(),
			COLOR(),
			;
			
		}
		
		public Attribute Attr;
		
		public Object Value;
		
		/** inclusive */
		public int Start;
		
		/** exclusive */
		public int End;
		
		public StringAttribute(StringAttribute attr)
		{
			Attr = attr.Attr;
			Value = attr.Value;
			Start = attr.Start;
			End = attr.End;
		}
		
		public StringAttribute(Attribute k, Object v, int start, int end)
		{
			Attr = k;
			Value = v;
			Start = start;
			End = end;
		}
		
		public StringAttribute(Attribute k, Object v)
		{
			Attr = k;
			Value = v;
			Start = -1;
			End = -1;
		}
		
		public static StringAttribute createColorAttribute(int start, int end, int color) {
			StringAttribute attr = new StringAttribute(Attribute.COLOR, color, start, end);
			return attr;
		}
		
		public static StringAttribute createSizeAttribute(int start, int end, int size) {
			StringAttribute attr = new StringAttribute(Attribute.SIZE, size, start, end);
			return attr;
		}
	}
	
	
	public interface StringLayer
	{
		public StringAttribute[] getAttributes();
		
		public String getString();
		public int getWidth();
		public int getHeight();
		
//		public int getWidth(int start, int end);
//		public int getIndex(int x, int y);
		
		
//		public StringLayer split(String regex);
		public StringLayer subString(int start);
		public StringLayer subString(int start, int end);
		
		public StringLayer concat(StringLayer append);
		
		public void render(IGraphics g, int x, int y);
		
		public StringLayer[] getStringLines(int w, int[] out_para);
		
	}
	
}

