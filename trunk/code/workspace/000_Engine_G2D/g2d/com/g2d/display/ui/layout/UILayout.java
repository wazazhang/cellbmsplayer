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
		;
		
		public static ImageStyle getStyle(String style) 
		{
			if (IMAGE_STYLE_ALL_9.toString().equals(style)) {
				return IMAGE_STYLE_ALL_9;
			}
			else if (IMAGE_STYLE_ALL_8.toString().equals(style)) {
				return IMAGE_STYLE_ALL_8;
			}
			else if (IMAGE_STYLE_H_012.toString().equals(style)) {
				return IMAGE_STYLE_H_012;
			}
			else if (IMAGE_STYLE_V_036.toString().equals(style)) {
				return IMAGE_STYLE_V_036;
			}
			else if (IMAGE_STYLE_BACK_4.toString().equals(style)) {
				return IMAGE_STYLE_BACK_4;
			}
			
			return NULL;
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
	public int				BorderSizeTop = 1;
	public int				BorderSizeBottom = 1;
	
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
	
	private int				ClipSize;
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
		BorderSizeTop	= set.BorderSizeTop;
		BorderSizeBottom = set.BorderSizeBottom;
		BorderColor		= set.BorderColor;
		
		BorderT			= set.BorderT;
		BorderB			= set.BorderB;
		BorderL			= set.BorderL;
		BorderR			= set.BorderR;
		
		BorderTL		= set.BorderTL;
		BorderTR		= set.BorderTR;
		BorderBL		= set.BorderBL;
		BorderBR		= set.BorderBR;
		
		validateImages();
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
	public void setImages(Image[] images, int bordersize)
	{
		if(images==null)return;

		BorderSize = bordersize;
		
		BorderTL = images[0];BorderT = images[1];BorderTR = images[2];//
		BorderL  = images[3];BackImage=images[4];BorderR  = images[5];//
		BorderBL = images[6];BorderB = images[7];BorderBR = images[8];//
		
		validateImages();
	}
	
	public int getClipSize() {
		return ClipSize;
	}
	
	public void setImages(Image src, ImageStyle style, int clipsize)
	{
		setImages(src, style, clipsize, clipsize>>1);
	}
	
	public void setImages(Image src, ImageStyle style, int clipsize, int bordersize)
	{
		BorderSize = bordersize;
		BorderSizeTop = bordersize;
		BorderSizeBottom = bordersize;
		
		BorderTL	= null;
		BorderT		= null;
		BorderTR 	= null;
		BorderL  	= null;
		BackImage 	= null;
		BorderR  	= null;
		BorderBL	= null;
		BorderB		= null;
		BorderBR	= null;

		if(src!=null)
		{
			switch(style)
			{
			case IMAGE_STYLE_ALL_9:
				BackImage	= Tools.subImage(src, clipsize, clipsize, src.getWidth(null)-clipsize*2, src.getHeight(null)-clipsize*2);
			case IMAGE_STYLE_ALL_8:
				BorderTL	= Tools.subImage(src, 0, 0, clipsize, clipsize);
				BorderT		= Tools.subImage(src, clipsize, 0, src.getWidth(null)-clipsize*2, clipsize);
				BorderTR 	= Tools.subImage(src, src.getWidth(null)-clipsize, 0, clipsize, clipsize);
				BorderL  	= Tools.subImage(src, 0, clipsize, clipsize, src.getHeight(null)-clipsize*2);
				BorderR  	= Tools.subImage(src, src.getWidth(null)-clipsize, clipsize, clipsize, src.getHeight(null)-clipsize*2);
				BorderBL	= Tools.subImage(src, 0, src.getHeight(null)-clipsize, clipsize, clipsize);
				BorderB		= Tools.subImage(src, clipsize, src.getHeight(null)-clipsize, src.getWidth(null)-clipsize*2, clipsize);
				BorderBR	= Tools.subImage(src, src.getWidth(null)-clipsize, src.getHeight(null)-clipsize, clipsize, clipsize);
//				BackImage	= Tools.subImage(src, clipsize, clipsize, 1, 1);
				IImage m = new CImage(src).newInstance();
				int rgb[] = new int[1];
				m.getRGB(rgb, 0, 1, clipsize, clipsize, 1, 1);
				BackColor = new Color(rgb[0]);
				break;
				
			case IMAGE_STYLE_H_012:
				BorderTL	= Tools.subImage(src, 0, 0, clipsize, src.getHeight(null));
				BorderT		= Tools.subImage(src, clipsize, 0, src.getWidth(null)-clipsize*2, src.getHeight(null));
				BorderTR 	= Tools.subImage(src, src.getWidth(null)-clipsize, 0, clipsize, src.getHeight(null));
				break;
				
			case IMAGE_STYLE_V_036:
				BorderTL	= Tools.subImage(src, 0, 0, src.getWidth(null), clipsize);
				BorderL  	= Tools.subImage(src, 0, clipsize, src.getWidth(null), src.getHeight(null)-clipsize*2);
				BorderBL	= Tools.subImage(src, 0, src.getHeight(null)-clipsize, src.getWidth(null), clipsize);
				break;
				
			case IMAGE_STYLE_BACK_4:
				BackImage 	= src;
				break;
			}
		}
		
		validateImages();
	}
	
	public void setImages(Image src, ImageStyle style, int Topsize, int Bottomsize, int bordersize){
		BorderSize = bordersize;
		BorderSizeTop = Topsize;
		BorderSizeBottom = Bottomsize;
		
		BorderTL	= null;
		BorderT		= null;
		BorderTR 	= null;
		BorderL  	= null;
		BackImage 	= null;
		BorderR  	= null;
		BorderBL	= null;
		BorderB		= null;
		BorderBR	= null;

		if(src!=null)
		{
			switch(style)
			{
			case IMAGE_STYLE_ALL_9:
				BackImage	= Tools.subImage(src, bordersize, Topsize, src.getWidth(null)-bordersize*2, src.getHeight(null)-Topsize - Bottomsize);
			case IMAGE_STYLE_ALL_8:
				BorderTL	= Tools.subImage(src, 0, 0, bordersize, Topsize);
				BorderT		= Tools.subImage(src, bordersize, 0, src.getWidth(null)-bordersize*2, Topsize);
				BorderTR 	= Tools.subImage(src, src.getWidth(null)-bordersize, 0, bordersize, Topsize);
				BorderL  	= Tools.subImage(src, 0, Topsize, bordersize, src.getHeight(null)-Topsize - Bottomsize);
				BorderR  	= Tools.subImage(src, src.getWidth(null)-bordersize, Topsize, bordersize, src.getHeight(null)-Topsize - Bottomsize);
				BorderBL	= Tools.subImage(src, 0, src.getHeight(null)-Bottomsize, bordersize, Bottomsize);
				BorderB		= Tools.subImage(src, bordersize, src.getHeight(null)-Bottomsize, src.getWidth(null)-bordersize*2, Bottomsize);
				BorderBR	= Tools.subImage(src, src.getWidth(null)-bordersize, src.getHeight(null)-Bottomsize, bordersize, Bottomsize);
				IImage m = new CImage(src).newInstance();
				int rgb[] = new int[1];
				m.getRGB(rgb, 0, 1, bordersize, Topsize, 1, 1);
				BackColor = new Color(rgb[0]);
				break;
				
			case IMAGE_STYLE_H_012:
				BorderTL	= Tools.subImage(src, 0, 0, bordersize, src.getHeight(null));
				BorderT		= Tools.subImage(src, bordersize, 0, src.getWidth(null)-bordersize*2, src.getHeight(null));
				BorderTR 	= Tools.subImage(src, src.getWidth(null)-bordersize, 0, bordersize, src.getHeight(null));
				break;
				
			case IMAGE_STYLE_V_036:
				BorderTL	= Tools.subImage(src, 0, 0, src.getWidth(null), Topsize);
				BorderL  	= Tools.subImage(src, 0, Topsize, src.getWidth(null), src.getHeight(null)-Topsize - Bottomsize);
				BorderBL	= Tools.subImage(src, 0, src.getHeight(null)-Bottomsize, src.getWidth(null), Bottomsize);
				break;
				
			case IMAGE_STYLE_BACK_4:
				BackImage 	= src;
				break;
			}
		}
		
		validateImages();
	}
	
	protected void validateImages()
	{
		if (
				BorderT != null &&
				BorderB != null &&
				BorderL != null &&
				BorderR	!= null &&
				BackImage != null &&
				BorderTL != null &&
				BorderTR != null &&
				BorderBL != null &&
				BorderBR != null )
		{
			Style = ImageStyle.IMAGE_STYLE_ALL_9;
		}
		else if (
				BorderT != null &&
				BorderB != null &&
				BorderL != null &&
				BorderR	!= null &&
				BorderTL != null &&
				BorderTR != null &&
				BorderBL != null &&
				BorderBR != null )
		{
			Style = ImageStyle.IMAGE_STYLE_ALL_8;
		}
		else if (
				BorderT != null &&
				BorderTL != null &&
				BorderTR != null )
		{
			Style = ImageStyle.IMAGE_STYLE_H_012;
		}
		else if (
				BorderL != null &&
				BorderTL != null &&
				BorderBL != null )
		{
			Style = ImageStyle.IMAGE_STYLE_V_036;
		}
		else if (BackImage != null)
		{
			Style = ImageStyle.IMAGE_STYLE_BACK_4;
		}
		else
		{
			Style = ImageStyle.NULL;
		}
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
}
