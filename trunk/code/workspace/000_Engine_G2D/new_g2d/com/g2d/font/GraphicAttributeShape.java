package com.g2d.font;

import com.g2d.Graphics2D;
import com.g2d.geom.Ellipse2D;
import com.g2d.geom.Rectangle;
import com.g2d.geom.Rectangle2D;
import com.g2d.geom.Shape;



public class GraphicAttributeShape extends GraphicAttribute
{
    /** 
     * A key indicating the shape should be stroked with a 1-pixel wide stroke. 
     */
    public static final boolean STROKE = true;

    /** 
     * A key indicating the shape should be filled. 
     */
    public static final boolean FILL = false;

    
    private Shape fShape;

    private Rectangle fShapeBounds;

    private boolean fStroke;
    
	public GraphicAttributeShape(Shape shape, int alignment, boolean stroke) {
		super(alignment);
		fShape = shape;
		fStroke = stroke;
		fShapeBounds = fShape.getBounds();
	}

	public void draw(Graphics2D graphics, float x, float y) {
		graphics.translate((int) x, (int) y);
		try {
			if (fStroke == STROKE) {
				graphics.draw(fShape);
			} else {
				graphics.fill(fShape);
			}
		} finally {
			graphics.translate(-(int) x, -(int) y);
		}
	}

    public Rectangle getBounds() {
        return fShapeBounds;
    }
}