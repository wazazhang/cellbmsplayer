package com.g2d;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.g2d.geom.AffineTransform;
import com.g2d.geom.Path2D;
import com.g2d.geom.Polygon;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Rectangle2D;
import com.g2d.geom.Shape;

public abstract class Graphics2D implements IGraphics
{

	@Override
	final public void drawString(String str, int x, int y, int shandowColor, int shandowX, int shandowY) {
		Color c = getColor();
		drawString(str, x, y);
		setColor(new Color(shandowColor));
		drawString(str, x + shandowX, y + shandowY);
		setColor(c);
	}

	@Override
	final public void fillRectAlpha(int argb, int x, int y, int width, int height) {
		Color c = getColor();
		setColor(new Color(argb));
		fillRect(x, y, width, height);
		setColor(c);
	}

	@Override
	final public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		fillPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
	}

	@Override
	final public String getFontName() {
		return getFont().getName();
	}
	
	@Override
	final public int getFontSize() {
		return getFont().getSize();
	}
	
	@Override
	final public void setColor(int red, int green, int blue) {
		setColor(new Color(red, green, blue));
	}
	
	@Override
	final public void setColor(int RGB) {
		setColor(new Color(RGB));
	}
	
	@Override
	final public void setStringAntiAllias(boolean antiallias) {
		this.setFontAntialiasing(antiallias);
	}
	

	abstract public Color 			getColor();
	abstract public void 			setColor(Color c);

	
	abstract public Font 			getFont();
	abstract public void 			setFont(Font font);

//	---------------------------------------------------------------------------------------------------------------------------

	final public void clip(Rectangle rect) {
		clipRect(rect.x, rect.y, rect.width, rect.height);
	}
	final public void setClip(Rectangle rect) {
		setClip(rect.x, rect.y, rect.width, rect.height);
	}
	abstract public void 			setClip(int x, int y, int width, int height) ;
	abstract public void 			clipRect(int x, int y, int width, int height) ;
	
	
	abstract public boolean			hitClip(int x, int y, int width, int height);

//	---------------------------------------------------------------------------------------------------------------------------

	
	abstract public void 			setComposite(Composite comp);
	abstract public Composite 		getComposite();
	abstract public void			pushComposite();
	abstract public void			popComposite();
	
	
	abstract public void 			setPaint(Paint paint);
	abstract public Paint 			getPaint();
	abstract public void			pushPaint();
	abstract public void			popPaint();
	
	abstract public Stroke 			getStroke();
	abstract public void 			setStroke(Stroke s);
	abstract public void			pushStroke();
	abstract public void			popStroke();

	
//	---------------------------------------------------------------------------------------------------------------------------

	
	abstract public void 			fill(Shape shape);
	abstract public void 			draw(Shape shape);
	
	abstract public void 			drawLine(int x1, int y1, int x2, int y2);
	
	abstract public void 			drawPath(Path2D path);
	abstract public void 			fillPath(Path2D path);
	
	abstract public void 			fillRect(int x, int y, int width, int height);
	abstract public void 			fillRect(Rectangle2D rect);
	
	abstract public void 			drawRect(int x, int y, int width, int height);
	abstract public void 			drawRect(Rectangle2D rect);
	
	abstract public void 			drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);
	abstract public void 			fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);
	
	abstract public void 			drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);
	abstract public void 			fillArc(int x, int y, int width, int height, int startAngle, int arcAngle);
	
	abstract public void 			drawPolyline(int xPoints[], int yPoints[], int nPoints);
	abstract public void 			drawPolygon(int xPoints[], int yPoints[], int nPoints);
	abstract public void 			drawPolygon(Polygon p);
	abstract public void 			fillPolygon(int xPoints[], int yPoints[], int nPoints);
	abstract public void 			fillPolygon(Polygon p);

	
	abstract public boolean			drawImage(Image img, int x, int y);
	abstract public boolean			drawImage(Image img, int x, int y, int width, int height);
	abstract public boolean			drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2);

	

	abstract public void			drawChars(char data[], int offset, int length, int x, int y);
	abstract public void 			drawString(String str, int x, int y);
	abstract public void 			drawString(String str, float x, float y);
	abstract public void 			drawString(AttributedString atext, int x, int y);
	abstract public void 			drawString(AttributedString atext, float x, float y);


	

//	--------------------------------------------------------------------------------------------


	abstract public void 			translate(int x, int y);
	abstract public void 			translate(double tx, double ty);
	abstract public void 			rotate(double theta);
	abstract public void 			rotate(double theta, double x, double y);
	abstract public void 			scale(double sx, double sy);
	abstract public void 			shear(double shx, double shy);

	abstract public void 			popTransform();
	abstract public void 			pushTransform();

//	--------------------------------------------------------------------------------------------


	
	abstract public float 			setAlpha(float alpha);
	abstract public float 			getAlpha();
	
	
	abstract public boolean 		setFontAntialiasing(boolean enable);
	abstract public boolean 		getFontAntialiasing();

	
	
	
	
	
	
	
//	--------------------------------------------------------------------------------------------


	
}