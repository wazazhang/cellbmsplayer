package com.g2d.font;

import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;



public abstract class GraphicAttribute
{
    /** 
     * Aligns top of graphic to top of line. 
     */
    public static final int TOP_ALIGNMENT = -1;

    /** 
     * Aligns bottom of graphic to bottom of line. 
     */
    public static final int BOTTOM_ALIGNMENT = -2;
    
    
    private int alignment;
    
    public GraphicAttribute(int alignment) {
		this.alignment = alignment;
	}

	public abstract Rectangle getBounds();


    public abstract void draw(Graphics2D graphics, float x, float y);

    public int getAlignment(){
    	return alignment;
    }


}