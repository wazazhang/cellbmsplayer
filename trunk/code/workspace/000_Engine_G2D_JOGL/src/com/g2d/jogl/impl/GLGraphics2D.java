package com.g2d.jogl.impl;

import java.text.AttributedString;

import javax.media.opengl.GL;

import com.cell.gfx.IImage;
import com.g2d.Color;
import com.g2d.Composite;
import com.g2d.Font;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Paint;
import com.g2d.Stroke;
import com.g2d.geom.Path2D;
import com.g2d.geom.Polygon;
import com.g2d.geom.Rectangle2D;

public class GLGraphics2D extends Graphics2D
{
	final private GL gl;
	
	private Color					cur_color;
	
	public GLGraphics2D(GL gl) 
	{	
		this.gl = gl;
		float[] color_4f = new float[4];
		gl.glGetFloatv(GL.GL_COLOR, color_4f, 0);
		this.cur_color = new Color(color_4f[0], color_4f[1], color_4f[2], color_4f[3]);
		
	}
	
	@Override
	public void dispose() {
		
	}
	

//	-------------------------------------------------------------------------------------------------------------------------
//	blending
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setComposite(Composite comp) {}
	public void pushComposite() {}
	public void popComposite() {}
	public void popBlendMode() {}
	public void pushBlendMode() {}
	public void setBlendMode(int blend, float alpha) {}
	public void setBlendMode(int blend) {}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	paint
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setPaint(Paint paint) {}
	public void pushPaint() {}
	public void popPaint() {}

//	-------------------------------------------------------------------------------------------------------------------------
//	stroke
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void setStroke(Stroke s) {}
	public void pushStroke() {}
	public void popStroke() {}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	color
//	-------------------------------------------------------------------------------------------------------------------------
	
	public Color getColor() {
		return this.cur_color;
	}
	public void setColor(Color c) {
		this.cur_color = c;
	}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	clip
//	-------------------------------------------------------------------------------------------------------------------------

	final public int getClipX() {
		return 0;
	}
	final public int getClipY() {
		return 0;
	}
	final public int getClipHeight() {
		return 0;
	}
	final public int getClipWidth() {
		return 0;
	}
	
	final public void clipRect(int x, int y, int width, int height) {}
	
	final public void setClip(int x, int y, int width, int height) {}
	
	@Override
	public void pushClip() {}
	
	@Override
	public void popClip() {}

	public boolean hitClip(int x, int y, int width, int height){
		return true;
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	transform
//	-------------------------------------------------------------------------------------------------------------------------

	public void translate(int x, int y) {
		gl.glTranslatef(x, y, 0f);
	}
	public void translate(double tx, double ty) {
		gl.glTranslated(tx, ty, 0d);
	}
	public void rotate(double theta) {
		gl.glRotated(Math.toDegrees(theta), 0, 0, 1d);
	}
	public void rotate(double theta, double x, double y) {
		gl.glRotated(Math.toDegrees(theta), x, y, 1d);
	}
	public void scale(double sx, double sy) {
		gl.glScaled(sx, sy, 0);
	}
	public void shear(double shx, double shy) {
	
	}
	
	@Override
	public void pushTransform() {
		gl.glPushMatrix();
	}
	@Override
	public void popTransform() {
		gl.glPopMatrix();
	}
	
	
	
	

//	-------------------------------------------------------------------------------------------------------------------------
//	base shape
//	-------------------------------------------------------------------------------------------------------------------------
	
	
	public void drawLine(int x1, int y1, int x2, int y2) {}

	public void drawPath(Path2D path) {}
	
	public void fillPath(Path2D path) {}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void fillRect(int x, int y, int width, int height){}
	public void fillRect(Rectangle2D rect) {}
	public void drawRect(int x, int y, int width, int height){}
	public void drawRect(Rectangle2D rect){}
	
//	-------------------------------------------------------------------------------------------------------------------------
//	round rect
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	arc
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {}

	
//	-------------------------------------------------------------------------------------------------------------------------
//	polygon
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {}
	public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {}
	public void drawPolygon(Polygon p) {}
	public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {}
	public void fillPolygon(Polygon p) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	chars
//	-------------------------------------------------------------------------------------------------------------------------
	
	public void drawChars(char data[], int offset, int length, int x, int y) {}
	public void drawString(String str, int x, int y) {}
	public void drawString(String str, float x, float y) {}
	public void drawString(AttributedString atext, int x, int y) {}
	public void drawString(AttributedString atext, float x, float y) {}

//	-------------------------------------------------------------------------------------------------------------------------
//	image
//	-------------------------------------------------------------------------------------------------------------------------
	
	public boolean drawImage(Image img, int x, int y) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, x, y, buff.getWidth(), buff.getHeight(), 0, 0, buff.getWidth(), buff.getHeight());
		return true;
	}

	public boolean drawImage(Image img, int x, int y, int width, int height) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, x, y, width, height, 0, 0, buff.getWidth(), buff.getHeight());
		return true;
	}
	
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		GLImage buff = (GLImage)img;
		buff.draw(gl, dx1, dy1, dx2-dx1, dy2-dy1, sx1, sy1, dx2-dx1, dy2-dy1);
		return true;
	}

	public void drawImage(IImage img, int x, int y, int w, int h, int transform) {}
	
	public void drawImage(IImage src, int x, int y, int transform) {}

	public void drawRegion(IImage src, int xSrc, int ySrc, int width, int height, int transform, int xDest, int yDest) {}

	public void drawRoundImage(IImage src, int x, int y, int width, int height, int transform) {}


//	-------------------------------------------------------------------------------------------------------------------------
//	flag
//	-------------------------------------------------------------------------------------------------------------------------

	public float setAlpha(float alpha) {
		return 1f;
	}

	public float getAlpha() {
		return 1f;
	}

	public boolean setFontAntialiasing(boolean enable) {
		return false;
	}

	public boolean getFontAntialiasing() {
		return false;
	}


//	-------------------------------------------------------------------------------------------------------------------------
//	string layer
//	-------------------------------------------------------------------------------------------------------------------------

	public Font getFont() {
		return null;
	}
	
	public void setFont(Font font) {}
	
	public void setFont(String name, int size) {}
	
	public int getStringHeight() {
		return 20;
	}
	
	public int getStringWidth(String src) {
		return 20;
	}
	
	@Override
	public String[] getStringLines(String text, int w, int[] out_para) {
		return null;
	}

	@Override
	public StringLayer createStringLayer(String src) {
		return null;
	}

	@Override
	public StringLayer createStringLayer(String src,
			StringAttribute[] attributes) {
		return null;
	}

//	-------------------------------------------------------------------------------------------------------------------------
//	2 impl
//	-------------------------------------------------------------------------------------------------------------------------

	public static class GraphicsPBuffer
	{
		
	}
	
	public static class GraphicsScreen
	{
		
	}
}
