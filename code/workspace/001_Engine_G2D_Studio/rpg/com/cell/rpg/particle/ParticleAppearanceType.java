package com.cell.rpg.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.cell.CMath;
import com.cell.gfx.game.CSprite;
import com.cell.j2se.CGraphicsImage;
import com.g2d.display.particle.Layer;
import com.g2d.display.particle.ParticleAppearance;

public enum ParticleAppearanceType
{
	IMAGE(DisplayNodeImage.class), 
	SPRITE(DisplayNodeSprite.class), 
	;

//	-----------------------------------------------------------------------------------------------
	
	final private Class<? extends ParticleAppearance> type;
	
	ParticleAppearanceType(Class<? extends ParticleAppearance> type) {
		this.type = type;
	}
	
	public Class<? extends ParticleAppearance> getType() {
		return type;
	}
	
//	-----------------------------------------------------------------------------------------------
	
	public static class DisplayNodeImage implements ParticleAppearance, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		/** CPJ工程名 */
		public String	cpj_project_name;
		
		/** CPJ图片组名*/
		public String	cpj_sprite_name;
		
		/** CPJ图片组图片编号*/
		public int		cpj_image_id;

		private transient Image image;
		
		public void setImage(Image src) {
			this.image = src;
		}
		
		public Image getImage() {
			return image;
		}
		
		public DisplayNodeImage cloneDisplay() {
			return this;
		}
		
		@Override
		public void render(Graphics2D g, Layer layer) {
			if (getImage() != null) {
				g.drawImage(getImage(), -getImage().getWidth(null) >> 1, -getImage().getHeight(null) >> 1, null);
			} else {
				g.setColor(Color.WHITE);
				g.drawArc(-2, -2, 4, 4, 0, 360);
			}
		}
	}
	
	public static class DisplayNodeSprite implements ParticleAppearance, Serializable
	{
		private static final long serialVersionUID = 1L;
		
		/** CPJ工程名 */
		public String	cpj_project_name;
		
		/** CPJ图片组名*/
		public String	cpj_sprite_name;
		
		/** 精灵动画号 */
		public int		sprite_anim;
		
		transient 
		public CSprite	sprite;
		
		transient
		private int		st_current_timer;
		
		public DisplayNodeSprite cloneDisplay() {
			DisplayNodeSprite ret = new DisplayNodeSprite();
			ret.st_current_timer 	= st_current_timer;
			ret.sprite_anim 		= sprite_anim;
			ret.sprite 				= sprite;
			return ret;
		}
		
		@Override
		public void render(Graphics2D g, Layer layer) {
			if (sprite != null) {
				CGraphicsImage cg = new CGraphicsImage(g);
				int anim = CMath.cycNum(sprite_anim, 0, sprite.getAnimateCount());
				int fram = CMath.cycNum(st_current_timer, 0, sprite.getFrameCount(anim));
				sprite.render(cg, 0, 0, anim, fram);
				st_current_timer ++;
			} else {
				g.setColor(Color.WHITE);
				g.drawArc(-2, -2, 4, 4, 0, 360);
			}
		}
	}
}
