package com.g2d.java2d.impl;

import java.awt.RenderingHints;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
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
import com.cell.gfx.IGraphics.StringAttribute;
import com.cell.gfx.IGraphics.StringLayer;


import com.g2d.BasicStroke;
import com.g2d.Color;
import com.g2d.Composite;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.Stroke;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Ellipse;
import com.g2d.geom.Ellipse2D;
import com.g2d.geom.Line2D;
import com.g2d.geom.Path2D;
import com.g2d.geom.PathIterator;
import com.g2d.geom.Polygon;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Rectangle2D;
import com.g2d.geom.Shape;
import com.g2d.java2d.impl.composite.BlendComposite;
import com.g2d.java2d.impl.composite.AlphaComposite.AlphaBlendMode;
import com.g2d.java2d.impl.composite.BlendComposite.BlendingMode;


public class AwtGraphics2D extends Graphics2D
{
	final java.awt.Graphics2D	g2d;
	
	private Color				cur_color;
	private Font				cur_font;
	private int					cur_font_h;
	private AffineTransform		cur_trans;
	private Stroke				cur_stroke;
	
	private Stack<java.awt.Shape> 					stack_clip 		= new Stack<java.awt.Shape>();
	private Stack<java.awt.Composite> 				stack_comp 		= new Stack<java.awt.Composite>();
	private Stack<java.awt.geom.AffineTransform> 	stack_trans		= new Stack<java.awt.geom.AffineTransform>();
	private Stack<java.awt.Stroke> 					stack_stroke	= new Stack<java.awt.Stroke>();
	private Stack<java.awt.Paint> 					stack_paint		= new Stack<java.awt.Paint>();


	public AwtGraphics2D(java.awt.Graphics2D g2d)
	{
		this.g2d 			= g2d;
		this.cur_color		= new Color(g2d.getColor().getRGB());
		this.cur_font		= new AwtFont(g2d.getFont());
		this.cur_font_h 	= g2d.getFontMetrics().getHeight();
		this.cur_stroke	= new BasicStroke(1);
		double[] flatmatrix = new double[6];
		g2d.getTransform().getMatrix(flatmatrix);
		this.cur_trans		= new AffineTransform(flatmatrix);
	}
	
	public java.awt.Graphics2D getG2d() {
		return g2d;
	}
	
	@Override
	public void dispose() {
		g2d.dispose();
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	blending
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setComposite(Composite comp) {
		
	}

	public Composite getComposite() {
		return null;
	}

	@Override
	public void pushComposite() {
		stack_comp.push(g2d.getComposite());
	}

	@Override
	public void popComposite() {
		g2d.setComposite(stack_comp.pop());
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
//	paint
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setPaint(Paint paint) {
	}

	public Paint getPaint() {
		return null;
	}

	public void pushPaint() {
		stack_paint.push(g2d.getPaint());
	}
	public void popPaint() {
		g2d.setPaint(stack_paint.pop());
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	stroke
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setStroke(Stroke s) {
		cur_stroke = s;
		if (s instanceof BasicStroke) {
			g2d.setStroke(new java.awt.BasicStroke(((BasicStroke)s).getSize()));
		}
	}

	public Stroke getStroke() {
		return cur_stroke;
	}

	public void pushStroke() {
		stack_stroke.push(g2d.getStroke());
	}

	public void popStroke() {
		g2d.setStroke(stack_stroke.pop());
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	color
//	-------------------------------------------------------------------------------------------------------------------------
	
	public Color getColor() {
		return this.cur_color;
	}
	
	public void setColor(Color c) {
		this.cur_color = c;
		g2d.setColor(new java.awt.Color(c.getARGB(), true));
	}
	
	public Font getFont() {
		return this.cur_font;
	}
	
	public void setFont(Font font) {
		this.cur_font = font;
		g2d.setFont(((AwtFont)font).getFont());
		cur_font_h = g2d.getFontMetrics().getHeight();
	}
	
	public void setFont(String name, int size) {
		AwtFont font = new AwtFont(new java.awt.Font(name, java.awt.Font.PLAIN, size));
		setFont(font);
	}
	
	public int getStringHeight() {
		return cur_font_h;
	}
	
	public int getStringWidth(String src) {
		return (int)g2d.getFontMetrics().getStringBounds(src, g2d).getWidth();
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	clip
//	-------------------------------------------------------------------------------------------------------------------------

	final public int getClipX() {
		java.awt.Rectangle b = g2d.getClipBounds();
		if (b != null)
			return b.x;
		return 0;
	}

	final public int getClipY() {
		java.awt.Rectangle b = g2d.getClipBounds();
		if (b != null)
			return b.y;
		return 0;
	}

	final public int getClipHeight() {
		java.awt.Rectangle b = g2d.getClipBounds();
		if (b != null)
			return b.height;
		return 0;
	}

	final public int getClipWidth() {
		java.awt.Rectangle b = g2d.getClipBounds();
		if (b != null)
			return b.width;
		return 0;
	}

	final public void clipRect(int x, int y, int width, int height) {
		g2d.clipRect(x, y, width, height);
	}
	
	final public void setClip(int x, int y, int width, int height) {
		g2d.setClip(x, y, width, height);
	}
	
	@Override
	public void pushClip() {
		stack_clip.push(g2d.getClip());
	}
	
	@Override
	public void popClip() {
		java.awt.Shape c = stack_clip.pop();
		if (c != null) {
			g2d.setClip(c);
		}
	}

	public boolean hitClip(int x, int y, int width, int height){
		return g2d.hitClip(x, y, width, height);
	}
	

	

//	-------------------------------------------------------------------------------------------------------------------------
//	base shape
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void fill(Shape shape) 
	{
		if (shape instanceof Line2D) {
			Line2D line = (Line2D)shape;
			drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
		}
		else if (shape instanceof Rectangle2D) {
			Rectangle2D rect = (Rectangle2D)shape;
			fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
		}
		else if (shape instanceof Ellipse2D) {
			Ellipse2D arc = (Ellipse2D)shape;
			fillArc((int)arc.getX(), (int)arc.getY(), (int)arc.getWidth(), (int)arc.getHeight(), 0, 360);
		}
		else if (shape instanceof Polygon) {
			Polygon poly = (Polygon)shape;
			fillPolygon(poly.xpoints, poly.ypoints, poly.npoints);
		}
		else if (shape instanceof Path2D) {
			Path2D path = (Path2D)shape;
			fillPath(path);
		}
	}
	
	public void draw(Shape shape) 
	{
		if (shape instanceof Line2D) {
			Line2D line = (Line2D)shape;
			drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(), (int)line.getY2());
		}
		else if (shape instanceof Rectangle2D) {
			Rectangle2D rect = (Rectangle2D)shape;
			drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
		}
		else if (shape instanceof Ellipse2D) {
			Ellipse2D arc = (Ellipse2D)shape;
			drawArc((int)arc.getX(), (int)arc.getY(), (int)arc.getWidth(), (int)arc.getHeight(), 0, 360);
		}
		else if (shape instanceof Polygon) {
			Polygon poly = (Polygon)shape;
			drawPolygon(poly.xpoints, poly.ypoints, poly.npoints);
		}
		else if (shape instanceof Path2D) {
			Path2D path = (Path2D)shape;
			drawPath(path);
		}
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		g2d.drawLine(x1, y1, x2, y2);
	}

	public void drawPath(Path2D path) {
		java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();
		for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
			p.append(new AwtPathIterator(it), true);
		}
		g2d.draw(p);
	}
	
	public void fillPath(Path2D path) {
		java.awt.geom.GeneralPath p = new java.awt.geom.GeneralPath();
		for (PathIterator it = path.getPathIterator(null); !it.isDone(); it.next()) {
			p.append(new AwtPathIterator(it), true);
		}
		g2d.fill(p);
	}
	
	class AwtPathIterator implements java.awt.geom.PathIterator
	{
		private PathIterator it;
		
		public AwtPathIterator(PathIterator it) {
			this.it = it;
		}
		@Override
		public int currentSegment(float[] coords) {
			return it.currentSegment(coords);
		}
		@Override
		public int currentSegment(double[] coords) {
			return it.currentSegment(coords);
		}
		@Override
		public int getWindingRule() {
			return it.getWindingRule();
		}
		@Override
		public boolean isDone() {
			return it.isDone();
		}
		@Override
		public void next() {
			it.next();
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void fillRect(int x, int y, int width, int height){
		g2d.fillRect(x, y, width, height);
	}
	public void fillRect(Rectangle2D rect) {
		fillRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	}
	public void drawRect(int x, int y, int width, int height){
		g2d.drawRect(x, y, width, height);
	}
	public void drawRect(Rectangle2D rect){
		drawRect((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	round rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	arc
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		g2d.drawArc(x, y, width, height, startAngle, arcAngle);
	}
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		g2d.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	
//	-------------------------------------------------------------------------------------------------------------------------
//	polygon
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {
		g2d.drawPolyline(xPoints, yPoints, nPoints);
	}
	public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {
		g2d.drawPolygon(xPoints, yPoints, nPoints);
	}
	public void drawPolygon(Polygon p) {
		g2d.drawPolygon(p.xpoints, p.ypoints, p.npoints);
	}
	public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {
		g2d.fillPolygon(xPoints, yPoints, nPoints);
	}
	public void fillPolygon(Polygon p) {
		g2d.fillPolygon(p.xpoints, p.ypoints, p.npoints);
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	chars
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawChars(char data[], int offset, int length, int x, int y) {
		java.awt.geom.Rectangle2D rect = g2d.getFont().getStringBounds(data, offset, length, g2d.getFontRenderContext());
		g2d.drawChars(data, offset, length, (int)(x-rect.getX()), (int)(y - rect.getY()));
	}
	public void drawString(String str, int x, int y) {
		java.awt.geom.Rectangle2D rect = g2d.getFont().getStringBounds(str, g2d.getFontRenderContext());
		g2d.drawString(str, (int)(x-rect.getX()), (int)(y - rect.getY()));
	}
	public void drawString(String str, float x, float y) {
		java.awt.geom.Rectangle2D rect = g2d.getFont().getStringBounds(str, g2d.getFontRenderContext());
		g2d.drawString(str, (float)(x-rect.getX()), (float)(y - rect.getY()));
	}
	public void drawString(AttributedString atext, int x, int y) {
		drawString(atext, (float)x, (float)y);
	}
	public void drawString(AttributedString atext, float x, float y) {
		AwtFont.decode(atext);
		AttributedCharacterIterator it = atext.getIterator();
		int begin = it.getBeginIndex();
		java.awt.geom.Rectangle2D rect = g2d.getFont().getStringBounds(it, 
				it.getBeginIndex(), it.getEndIndex(), g2d.getFontRenderContext());
		it.setIndex(begin);
		g2d.drawString(it, (float)(x-rect.getX()), (float)(y - rect.getY()));
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	image
//	-------------------------------------------------------------------------------------------------------------------------
	
	public boolean drawImage(Image img, int x, int y) {
		return g2d.drawImage(((AwtImage)img).getSrc(), x, y, null);
	}
	public boolean drawImage(Image img, int x, int y, int width, int height) {
		return g2d.drawImage(((AwtImage)img).getSrc(), x, y, width, height, null);
	}
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		return g2d.drawImage(((AwtImage)img).getSrc(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}
	@Override
	public void drawImage(IImage src, int x, int y, int transform, int blendMode, float blendAlpha) 
	{
		java.awt.Composite saved_composite = g2d.getComposite();   
		try {
			if (blendMode != IGraphics.BLEND_MODE_NONE) {
				java.awt.Composite toset_composite = getCompositeFrom(blendMode, blendAlpha);			
				if (toset_composite != null)
					g2d.setComposite(toset_composite);
			}
			BufferedImage img = ((AwtImage) src).getSrc();
	        if (transform == 0)  {      	 
	        	g2d.drawImage(img, x, y, null);
	        }  else {
				java.awt.geom.AffineTransform savedT = g2d.getTransform();
				try {
					g2d.translate(x, y);
					transform(transform, src.getWidth(), src.getHeight());
					g2d.drawImage(img, 0, 0, null);
				} finally {
					g2d.setTransform(savedT);
				}
			}
		} finally {
	        g2d.setComposite(saved_composite);
		}
	}
	@Override
	public void drawRegion(IImage src, 
			int xSrc, int ySrc, int width, int height, 
			int transform, int blendMode, float blendAlpha, 
			int xDest, int yDest) 
	{
		java.awt.Composite saved_composite = g2d.getComposite();   
		try {
			if (blendMode != IGraphics.BLEND_MODE_NONE) {
				java.awt.Composite toset_composite = this.getCompositeFrom(blendMode, blendAlpha);			
				if (toset_composite != null)
					g2d.setComposite(toset_composite);
			}
			BufferedImage img = ((AwtImage) src).getSrc();
			java.awt.geom.AffineTransform savedT = g2d.getTransform();
			try {
				g2d.translate(xDest, yDest);
				transform(transform, width, height);
				g2d.drawImage(img, 0, 0, width, height, xSrc, ySrc, xSrc + width, ySrc + height, null);
			} finally {
				g2d.setTransform(savedT);
			}
		} finally {
	        g2d.setComposite(saved_composite);
		}
	}

	@Override
	public void drawRoundImage(IImage src, int x, int y, int width, int height,
			int transform, int blendMode, float blendAlpha) 
	{
		java.awt.Composite saved_composite = g2d.getComposite();   
		try {
			if (blendMode != IGraphics.BLEND_MODE_NONE) {
				java.awt.Composite toset_composite = this.getCompositeFrom(
						blendMode, blendAlpha);
				if (toset_composite != null) {
					g2d.setComposite(toset_composite);
				}
			}
			BufferedImage 		img 	= ((AwtImage)src).getSrc();
			java.awt.Rectangle 	rect 	= g2d.getClipBounds();
			try {
				g2d.clipRect(x, y, width, height);
				int w = src.getWidth();
				int h = src.getHeight();
				for (int dx = 0; dx < width;) {
					for (int dy = 0; dy < height;) {
						g2d.drawImage(img, x + dx, y + dy, null);
						dy += h;
					}
					dx += w;
				}
			} finally {
				g2d.setClip(rect);
			}
		} finally {
			g2d.setComposite(saved_composite);
		}
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	transform
//	-------------------------------------------------------------------------------------------------------------------------

	public void translate(int x, int y) {
		cur_trans.translate(x, y);
		g2d.translate(x, y);
	}
	public void translate(double tx, double ty) {
		cur_trans.translate(tx, ty);
		g2d.translate(tx, ty);
	}
	public void rotate(double theta) {
		cur_trans.rotate(theta);
		g2d.rotate(theta);
	}
	public void rotate(double theta, double x, double y) {
		cur_trans.rotate(theta, x, y);
		g2d.rotate(theta, x, y);
	}
	public void scale(double sx, double sy) {	
		cur_trans.scale(sx, sy);
		g2d.scale(sx, sy);
	}
	public void shear(double shx, double shy) {
		cur_trans.shear(shx, shy);
		g2d.shear(shx, shy);
	}
	
	@Override
	public void pushTransform() {
		stack_trans.push(g2d.getTransform());
	}
	@Override
	public void popTransform() {
		g2d.setTransform(stack_trans.pop());
	}
	
	
	

//	-------------------------------------------------------------------------------------------------------------------------
//	flag
//	-------------------------------------------------------------------------------------------------------------------------
	
	public float setAlpha(float alpha) {
		java.awt.Composite com = g2d.getComposite();
		if (com instanceof java.awt.AlphaComposite) {
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
			return ((java.awt.AlphaComposite)com).getAlpha();
		}
		return 1f;
	}
	
	public float getAlpha() {
		java.awt.Composite com = g2d.getComposite();
		if (com instanceof java.awt.AlphaComposite) {
			return ((java.awt.AlphaComposite)com).getAlpha();
		}
		return 1f;
	}
	
	
	public boolean setFontAntialiasing(boolean enable) {
		Object old = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
		if (enable) {
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		} else {
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
		return RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(old);
	}
	
	public boolean getFontAntialiasing() {
		Object old = g2d.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
		return RenderingHints.VALUE_TEXT_ANTIALIAS_ON.equals(old);
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	
//	-------------------------------------------------------------------------------------------------------------------------
	
	protected final void transform(int transform, int width, int height)
	{
		switch (transform) 
		{
		case TRANS_ROT90: {
			g2d.translate(height, 0);
			g2d.rotate(ANGLE_90);
			break;
		}
		case TRANS_ROT180: {
			g2d.translate(width, height);
			g2d.rotate(Math.PI);
			break;
		}
		case TRANS_ROT270: {
			g2d.translate(0, width);
			g2d.rotate(ANGLE_270);
			break;
		}
		case TRANS_MIRROR: {
			g2d.translate(width, 0);
			g2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT90: {
			g2d.translate(height, 0);
			g2d.rotate(ANGLE_90);
			g2d.translate(width, 0);
			g2d.scale(-1, 1);
			break;
		}
		case TRANS_MIRROR_ROT180: {
			g2d.translate(width, 0);
			g2d.scale(-1, 1);
			g2d.translate(width, height);
			g2d.rotate(Math.PI);
			break;
		}
		case TRANS_MIRROR_ROT270: {
			g2d.rotate(ANGLE_270);
			g2d.scale(-1, 1);
			break;
		}
		}
	}

	protected final java.awt.Composite getCompositeFrom(int blend_mode, float blend_alpha)
	{
		java.awt.Composite composite = null;
		
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

//	-------------------------------------------------------------------------------------------------------------------------
//	stack
//	-------------------------------------------------------------------------------------------------------------------------
	
	

//	-------------------------------------------------------------------------------------------------------------------------
//	string layer
//	-------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String[] getStringLines(String text, int w, int[] out_para)
	{
		try 
		{
			AttributedString atext = new AttributedString(text);
			atext.addAttribute(TextAttribute.FONT, g2d.getFont(), 0, text.length());
			atext.addAttribute(TextAttribute.SIZE, g2d.getFont(), 0, text.length());
			
			Vector<String> lines = new Vector<String>();
			LineBreakMeasurer textMeasurer = new LineBreakMeasurer(
					atext.getIterator(),
					g2d.getFontRenderContext());

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
	}
	
	@Override
	public StringLayer createStringLayer(String src){
		StringAttribute attr = StringAttribute.createColorAttribute(0, src.length(), g2d.getColor().getRGB());
		CStringLayer layer = new CStringLayer(src, new StringAttribute[]{attr});
		return layer;
	}
	
	@Override
	public StringLayer createStringLayer(String src, StringAttribute[] attributes){
		CStringLayer layer = new CStringLayer(src, attributes);
		return layer;
	}
	

	StringAttribute[] attributesLink(CStringLayer s1, CStringLayer s2)
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
	
	StringAttribute[] attributesSub(CStringLayer src, int start, int end)
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
				AString.addAttribute(TextAttribute.FONT, g2d.getFont(), 0, text.length());
				AString.addAttribute(TextAttribute.SIZE, g2d.getFont(), 0, text.length());

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
							v = new java.awt.Font((String) attr.Value, java.awt.Font.PLAIN, g2d.getFont().getSize());
							break;
						case SIZE:
							k = TextAttribute.FONT;
							v = new java.awt.Font(g2d.getFont().getName(), java.awt.Font.PLAIN, (Integer) attr.Value);
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
				Layout = new TextLayout(AChars, g2d.getFontRenderContext());
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
			Layout.draw(((AwtGraphics2D)g).g2d, x, y+ascent);
		}
//
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
						g2d.getFontRenderContext());
	
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
		}
	}
	
	
	
	
}

