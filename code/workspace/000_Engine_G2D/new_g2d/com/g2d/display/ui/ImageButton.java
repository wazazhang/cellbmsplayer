package com.g2d.display.ui;


import java.util.concurrent.atomic.AtomicReference;

import com.g2d.BufferedImage;
import com.g2d.Graphics2D;
import com.g2d.Image;
import com.g2d.Tools;
import com.g2d.display.ui.layout.UILayout;




public class ImageButton extends BaseButton implements Runnable
{
	transient protected AtomicReference<Image>	loading;
	
	transient protected AtomicReference<Image>	background;
	
	String image_path;

	
	public ImageButton(String path)
	{
		image_path = path;

		new Thread(this).start();
	}

	public ImageButton(Image bg)
	{		
		background = new AtomicReference<Image>(bg);
	}
	
	public void setBackground(AtomicReference<Image> img)
	{
		background = img;
	}
	
	public void setBackground(Image img)
	{
		background = new AtomicReference<Image>(img);
	}
	
	public void setLoadingImage(AtomicReference<Image> img)
	{
		loading = img;
	}
	
	public void setLoadingImage(Image img)
	{
		loading = new AtomicReference<Image>(img);
	}
	
	public AtomicReference<Image> getBackground()
	{
		return background;
	}
	
	public AtomicReference<Image> getLoadingImage()
	{
		return loading;
	}	
	
	public void run() 
	{
		try {
			background = new AtomicReference<Image>(Tools.readImage(image_path));
			System.out.println("loadimage : "+image_path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics2D g)
	{
		Image img_2_render = null;
		
		if ( (background != null) && ((img_2_render=background.get())!=null) )
		{
			g.drawImage(img_2_render, 0, 0, getWidth(), getHeight());
		} 
		else if ( (loading != null) &&  ((img_2_render=loading.get())!=null) )
		{
			g.pushTransform();
			g.translate(getWidth()/2, getHeight()/2);
			g.rotate(timer / 5.f);
			g.drawImage(img_2_render, -img_2_render.getWidth()/2, -img_2_render.getHeight()/2);
			g.popTransform();
		}
		
//		renderCatchedMouse(g);
	}
	
	public static class FocusImageButton extends BaseButton
	{
		public UILayout	unfocus	= UILayout.createBlankRect();
		public UILayout	focus	= UILayout.createBlankRect();

		public FocusImageButton(BufferedImage unfocus, BufferedImage focus)
		{
			this.unfocus = new UILayout();
			this.unfocus.setImages(unfocus, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
			
			this.focus = new UILayout();
			this.focus.setImages(focus, UILayout.ImageStyle.IMAGE_STYLE_BACK_4_CENTER, 0);
		}
		
		@Override
		public void render(Graphics2D g)
		{
			if (isCatchedMouse()) {
				this.custom_layout = focus;				
			} else {
				this.custom_layout = unfocus;		
			}
			renderLayout(g);
		}
	}
}

