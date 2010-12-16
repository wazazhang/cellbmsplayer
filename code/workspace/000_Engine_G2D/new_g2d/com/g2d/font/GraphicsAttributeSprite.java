package com.g2d.font;


import com.cell.gfx.game.CCD;
import com.cell.gfx.game.CSprite;
import com.g2d.Graphics2D;
import com.g2d.geom.Rectangle;


public class GraphicsAttributeSprite extends GraphicAttribute
{
	private CSprite		sprite;
	private Rectangle	frame_bounds;
    
	public GraphicsAttributeSprite(CSprite sprite, int animate) {
		super(GraphicsAttributeImage.BOTTOM_ALIGNMENT);
		this.sprite = sprite.copy();
		this.sprite.setCurrentAnimate(animate);
		CCD bounds = this.sprite.getFrameBounds(animate);
		this.frame_bounds = new Rectangle(
				bounds.X1, 
				bounds.Y1,
				bounds.getWidth(), 
				bounds.getHeight());
	}
	
    public float getAscent() {
        return 0;
    }

    public float getDescent() {
        return frame_bounds.height;
    }

    public float getAdvance() {
        return frame_bounds.width;
    }

    public Rectangle getBounds() {
		return frame_bounds;
    }

    public void draw(Graphics2D graphics, float x, float y) {
    	sprite.render(graphics, (int) (x-frame_bounds.x), (int) (y-frame_bounds.y));
    	sprite.nextCycFrame();
    }


}
