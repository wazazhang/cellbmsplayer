package com.g2d.display.ui.layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

import com.cell.DObject;
import com.cell.gfx.IImage;
import com.cell.j2se.CImage;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.util.Drawing;

public class UILayout extends DObject
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static enum ImageStyle
	{
		NULL,
		IMAGE_STYLE_ALL_9,
		IMAGE_STYLE_ALL_8,
		IMAGE_STYLE_H_012, 
		IMAGE_STYLE_V_036,
		IMAGE_STYLE_BACK_4,
		IMAGE_STYLE_BACK_4_CENTER,
		;
		
		public static ImageStyle getStyle(String style) {
			return ImageStyle.valueOf(style);
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------------

	public static UILayout createRect(Color backcolor)
	{
		UILayout rect = new UILayout();
		rect.BackColor = backcolor;
		rect.BorderColor = backcolor;
		rect.BorderSize = 0;
		return rect;
	}

	public static UILayout createRect()
	{
		return createRect(new Color(0,0,0,0));
	}

	public static UILayout createBlankRect()
	{
		UILayout rect = createRect();
		rect.blank = true;
		return rect;
	}

//	------------------------------------------------------------------------------------------------------------------------------
	
	private boolean			blank 		= false;
	
	//border
	public int 				BorderSize	= 1;
	
	// color layout
	private Color 			BackColor	= new Color(0xffc0c0c0);//0xffc0c0c0;
	private Color 			BorderColor	= new Color(0xff000000);//0xff000000;
	
	// image layout
	private ImageStyle 		Style		= ImageStyle.NULL;
	
	transient private Image BorderT;
	transient private Image BorderB;
	transient private Image BorderL;
	transient private Image BorderR;
	transient private Image BackImage;
	transient private Image BorderTL;
	transient private Image BorderTR;
	transient private Image BorderBL;
	transient private Image BorderBR;

//	------------------------------------------------------------------------------------------------------------------------------

	
	public UILayout(){}
	
	public UILayout(UILayout set){
		this.set(set);
	}
	
	
	public void set(UILayout set)
	{
//		body
		BackColor		= set.BackColor;
		BackImage		= set.BackImage;
//		BackAlphaColor 	= set.BackAlphaColor;
		
//		border
		BorderSize		= set.BorderSize;
		BorderColor		= set.BorderColor;
		
		BorderT			= set.BorderT;
		BorderB			= set.BorderB;
		BorderL			= set.BorderL;
		BorderR			= set.BorderR;
		
		BorderTL		= set.BorderTL;
		BorderTR		= set.BorderTR;
		BorderBL		= set.BorderBL;
		BorderBR		= set.BorderBR;
		
		Style			= set.Style;
	}
	
	public void setBackColor(Color color){
		BackColor = color;
	}
	
	public void setBorderColor(Color color){
		BorderColor = color;
	}
	
	/**
	 * @param images
	 * 
	 * Rect                                  </br>
	 *                                       </br>
	 * TopLeft----------Top---------TopRight </br>
	 * |                                   | </br>
	 * Left         Background         Right </br>
	 * |                                   | </br>
	 * BottomLeft-----Bottom-----BottomRight </br>
	 *                                       </br>
	 * images[0] images[1] images[2]         </br>
	 * images[3] images[4] images[5]         </br>
	 * images[6] images[7] images[8]         </br>
	 * 
	 * 0,1,2 可以用来横向平铺
	 * 0,3,6 可以用来纵向平铺
	 * 4 可以用来缩放
	 */
	public void setImages(Image[] images, ImageStyle style, int bordersize)
	{
		if(images==null)return;

		BorderSize = bordersize;
		
		BorderTL = images[0];BorderT = images[1];BorderTR = images[2];//
		BorderL  = images[3];BackImage=images[4];BorderR  = images[5];//
		BorderBL = images[6];BorderB = images[7];BorderBR = images[8];//
		
		Style = style;
	}
	
	public void setImages(Image src, ImageStyle style, int clipsize)
	{
		setImages(src, style, clipsize, clipsize>>1);
	}

	public void setImages(Image src, ImageStyle style, int clipsize, int bordersize)
	{
		BorderSize = bordersize;
		clipImages(src, style, clipsize, clipsize, clipsize, clipsize);
	}
	

	protected void clipImages(Image src, ImageStyle style, int L, int R, int T, int B)
	{
		BorderTL	= null;
		BorderT		= null;
		BorderTR 	= null;
		BorderL  	= null;
		BackImage 	= null;
		BorderR  	= null;
		BorderBL	= null;
		BorderB		= null;
		BorderBR	= null;

		if (src != null)
		{
			int W = src.getWidth(null);
			int H = src.getHeight(null);

			switch(style)
			{
			case IMAGE_STYLE_ALL_9:
				BackImage 	= Tools.subImage(src, L, T, W - L - R, H - T - B);
				
			case IMAGE_STYLE_ALL_8:
				BorderTL 	= Tools.subImage(src, 0, 0, L, T);
				BorderT 	= Tools.subImage(src, L, 0, W - L - R, T);
				BorderTR 	= Tools.subImage(src, W - R, 0, R, T);
				BorderL 	= Tools.subImage(src, 0, T, L, H - T - B);
				BorderR 	= Tools.subImage(src, W - R, T, R, H - T - B);
				BorderBL 	= Tools.subImage(src, 0, H - B, L, B);
				BorderB 	= Tools.subImage(src, L, H - B, W - L - R, B);
				BorderBR 	= Tools.subImage(src, W - R, H - B, R, B);
				IImage m = new CImage(src).newInstance();
				int rgb[] = new int[1];
				m.getRGB(rgb, 0, 1, R, T, 1, 1);
				BackColor = new Color(rgb[0]);
				break;
				
			case IMAGE_STYLE_H_012:
				BorderTL	= Tools.subImage(src, 0, 0, L, H);
				BorderT		= Tools.subImage(src, L, 0, W-R-L, H);
				BorderTR 	= Tools.subImage(src, W-R, 0, R, H);
				break;
				
			case IMAGE_STYLE_V_036:
				BorderTL 	= Tools.subImage(src, 0, 0, W, T);
				BorderL 	= Tools.subImage(src, 0, T, W, H - T - B);
				BorderBL 	= Tools.subImage(src, 0, H - B, W, B);
				break;
				
			case IMAGE_STYLE_BACK_4:
			case IMAGE_STYLE_BACK_4_CENTER:
				BackImage 	= src;
				break;
			}
			Style = style;
		}
		
	}
	

	
	
	public int getTopClip() {
		if (BorderTL != null) {
			return BorderTL.getHeight(null);
		}
		return 0;
	}

	public int getBottomClip() {
		if (BorderBL != null) {
			return BorderBL.getHeight(null);
		}
		return 0;
	}
	
	
	public int getLeftClip() {
		if (BorderTL != null) {
			return BorderTL.getWidth(null);
		}
		return 0;
	}

	public int getRightClip() {
		if (BorderTR != null) {
			return BorderTR.getWidth(null);
		}
		return 0;
	}
	
	
	public void render(Graphics2D g, int x, int y, int W, int H)
	{
		if (blank) return;
		
		switch(Style)
		{
		case NULL:
			render0(g, x, y, W, H);
			break;
		case IMAGE_STYLE_ALL_8:
			renderAll8(g, x, y, W, H);
			break;
		case IMAGE_STYLE_ALL_9:
			renderAll9(g, x, y, W, H);
			break;
		case IMAGE_STYLE_H_012:
			renderH012(g, x, y, W, H);
			break;
		case IMAGE_STYLE_V_036:
			renderV036(g, x, y, W, H);
			break;
		case IMAGE_STYLE_BACK_4:
			renderBack4(g, x, y, W, H);
			break;
		case IMAGE_STYLE_BACK_4_CENTER:
			renderBack4Center(g, x, y, W, H);
			break;
		}
	}
	
	protected void render0(Graphics2D g, int x, int y, int W, int H) 
	{
		int width  = W - (BorderSize<<1);
		int height = H - (BorderSize<<1);
		
		if ( (width > 0) && (height > 0))
		{
			g.setColor(BackColor);
			g.fillRect(x+BorderSize, y+BorderSize, width, height);
		}
		
		g.setColor(BorderColor);
		g.drawRect(x, y, W-1, H-1);
	}
	
	protected void render0123_5678(Graphics2D g, int x, int y, int W, int H)
	{
		g.drawImage(BorderTL, x, y, null);
		g.drawImage(BorderBL, x, y+H-BorderBL.getHeight(null), null);
		g.drawImage(BorderTR, x+W-BorderTR.getWidth(null), y, null);
		g.drawImage(BorderBR, x+W-BorderBR.getWidth(null), y+H-BorderBR.getHeight(null), null);
		
		Drawing.drawRoundImage(g, BorderL, 
				x, 
				y+BorderTL.getHeight(null),
				BorderL.getWidth(null),
				H-BorderTL.getHeight(null)-BorderBL.getHeight(null));
		Drawing.drawRoundImage(g, BorderR, 
				x+W-BorderR.getWidth(null), 
				y+BorderTR.getHeight(null),
				BorderR.getWidth(null),
				H-BorderTR.getHeight(null)-BorderBR.getHeight(null));
		Drawing.drawRoundImage(g, BorderT, 
				x+BorderTL.getWidth(null), 
				y, 
				W-BorderTL.getWidth(null)-BorderTR.getWidth(null),
				BorderT.getHeight(null));
		Drawing.drawRoundImage(g, BorderB, 
				x+BorderBL.getWidth(null), 
				y+H-BorderB.getHeight(null), 
				W-BorderBL.getWidth(null)-BorderBR.getWidth(null),
				BorderB.getHeight(null));	
	}
	
	protected void renderAll8(Graphics2D g, int x, int y, int W, int H)
	{
//		int width  = W - (BorderSize<<1);
//		int height = H - (BorderSize<<1);
		int mx = BorderL.getWidth(null);
		int my = BorderT.getHeight(null);
		int mw = W - BorderL.getWidth(null) - BorderR.getWidth(null);
		int mh = H - BorderT.getHeight(null) - BorderB.getHeight(null);
		
		if ( (mw > 0) && (mh > 0))
		{
			g.setColor(BackColor);
			g.fillRect(mx, my, mw, mh);
		}
		
		render0123_5678(g, x, y, W, H);
	}
	
	protected void renderAll9(Graphics2D g, int x, int y, int W, int H)
	{
		int mx = BorderL.getWidth(null);
		int my = BorderT.getHeight(null);
		int mw = W - BorderL.getWidth(null) - BorderR.getWidth(null);
		int mh = H - BorderT.getHeight(null) - BorderB.getHeight(null);
		
		if ( (mw > 0) && (mh > 0))
		{
			Drawing.drawRoundImage(g, BackImage, mx, my, mw, mh);
		}

		render0123_5678(g, x, y, W, H);
	}
	
	protected void renderH012(Graphics2D g, int x, int y, int W, int H)
	{
		int bs = BorderTL.getWidth(null);
		if (bs*2>W){	//画血条时可能会用到，当血条长度小于左右块长度时
			int w1 = W>>1;
			int w2 = W-w1;
			g.drawImage(BorderTL, 
					x, y, w1, H, null);
			g.drawImage(BorderTR, 
					x+w1, y, x+W, y+H, 
					BorderTR.getWidth(null)-w2, 0, BorderTR.getWidth(null), H,
					null);
		}else{
			g.drawImage(BorderTL, x, y, 
					BorderTL.getWidth(null), H, null);
			Drawing.drawRoundImageH(g, BorderT, x+BorderTL.getWidth(null), y, W-BorderTL.getWidth(null)-BorderTR.getWidth(null), H);
			g.drawImage(BorderTR, x+W-BorderTR.getWidth(null), y, 
					BorderTR.getWidth(null), H, null);
		}
	}
	
	protected void renderV036(Graphics2D g, int x, int y, int W, int H)
	{
		g.drawImage(BorderTL, x, y, 
				W, BorderTL.getHeight(null), null);
		Drawing.drawRoundImageV(g, BorderL, x, y+BorderTL.getHeight(null), W, H-BorderTL.getHeight(null)-BorderBL.getHeight(null));
		g.drawImage(BorderBL, x, y+H-BorderBL.getHeight(null), 
				W, BorderBL.getHeight(null), null);
	}
	
	protected void renderBack4(Graphics2D g, int x, int y, int W, int H)
	{
		//Drawing.drawRoundImage(g, BackImage, x, y, W, H);
		g.drawImage(BackImage, x, y, W, H, null);
	}
	
	protected void renderBack4Center(Graphics2D g, int x, int y, int W, int H)
	{
		//Drawing.drawRoundImage(g, BackImage, x, y, W, H);
		g.drawImage(BackImage, 
				x + ((W - BackImage.getWidth(null)) >> 1),
				y + ((H - BackImage.getHeight(null)) >> 1), 
				null);
	}
}
