package lord;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class PaintTask extends Thread
{
	public static final String VERSION = "1.0.1.90819";
	
	static
	{
		System.out.println("loader ver : " + VERSION);
	}
	
	Component 	paint_component;
	
	Image 		BG;
	Image 		LoadingF;
	Image 		LoadingS;
	Image 		LoadingB;
	Font 		PorgressFont;
	
	private String ProgressStatus = "";
	private int ProgressMax = 1;
	private int ProgressCur = 0;
	private int ProgressCurS = 0;
	private int ProgressCurV = 0;
	
	BufferedImage	buffer;
	
	private long Timer = 0;
	
	private boolean exit = false;
	
	public PaintTask(Component component) 
	{
		this.paint_component = component;
	}
	
	public void setPaintUnit(Image bg, Image loadingf, Image loadings, Image loadingb, Font font) 
	{
		this.BG = bg;
		this.LoadingF = loadingf;
		this.LoadingS = loadings;
		this.LoadingB = loadingb;
		this.PorgressFont = font;
	}
	
	public void reset() {
		ProgressCur = 0;
		ProgressCurS = 0;
		ProgressCurV = 0;
		ProgressMax = 1;
		paint_component.repaint();
	}
	
	public void setMax(int max) {
		ProgressMax = max;
		paint_component.repaint();
	}
	
	public void setState(String state) {
		ProgressStatus = state;
		paint_component.repaint();
	}

	public void acc(int cur) {
		ProgressCurS = (ProgressCur - ProgressCurV) / 10;
		ProgressCur += cur;
		paint_component.repaint();
	}
	
	public void exit(){
		exit = true;
	}
	
	public void run() 
	{
		while (!exit) 
		{
			try {
				if (ProgressCurV<ProgressCur) {
					ProgressCurV += Math.max(1, Math.min(ProgressCurS, ProgressCur - ProgressCurV));
				}
				ProgressCurV = Math.min(ProgressMax, ProgressCurV);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				paint_component.repaint();
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Timer++;
		}
		
		System.out.println("paint task thread exited !");
	}
	
	public int getWidth(){
		return paint_component.getWidth();
	}
	
	public int getHeight(){
		return paint_component.getHeight();
	}
	
	public void repaint(Graphics2D dg)
	{
		try 
		{
			if (buffer != null && 
				buffer.getWidth() == getWidth() && 
				buffer.getHeight() == getHeight())
			{
				Graphics2D g = buffer.createGraphics();
				
				g.setRenderingHint(
						RenderingHints.KEY_TEXT_ANTIALIASING, 
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				
				paint(g);

				dg.drawImage(buffer, 0, 0, null);
			}
			else
			{
				buffer = dg.getDeviceConfiguration().createCompatibleImage(getWidth(), getHeight());
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
	}
	
	private void paint(Graphics g)
	{
		float rate = 0;
		
		if (ProgressMax>0) 
		{
			rate = ((float)ProgressCurV) / ((float)ProgressMax);
		}
		
		String info = ProgressStatus + " " + (int)(rate * 100) + "%";
		
		if (PorgressFont!=null)
		{
			g.setFont(PorgressFont);
		}
		
		g.setClip(0, 0, getWidth(), getHeight());
		
		if (BG!=null)
		{
			g.drawImage(BG, 0, 0, null);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		if (LoadingS!=null && LoadingF!=null && LoadingS!=null)
		{
			ImageObserver observer = paint_component;
			
			int bx = (getWidth() - LoadingB.getWidth(observer))/2;
			int by = getHeight() - 64 - LoadingB.getHeight(observer)/2;
			g.setClip(bx, by, (int)(LoadingB.getWidth(observer)), LoadingB.getHeight(observer));
			g.drawImage(LoadingB, bx, by, null);
			
			int sx = (getWidth() - LoadingS.getWidth(observer))/2;
			int sy = getHeight() - 64 - LoadingS.getHeight(observer)/2;
			g.setClip(sx, sy, (int)(LoadingS.getWidth(observer) * rate), LoadingS.getHeight(observer));
			g.drawImage(LoadingS, sx, sy, null);
			
			int fx = (getWidth() - LoadingF.getWidth(observer))/2;
			int fy = getHeight() - 64 - LoadingF.getHeight(observer)/2;
			g.setClip(fx, fy, (int)(LoadingF.getWidth(observer)), LoadingF.getHeight(observer));
			g.drawImage(LoadingF, fx, fy, null);
			
			int c = (int)(0xff - Math.abs(80 * Math.sin(Math.PI/30*Timer)));
			g.setClip(0, 0, getWidth(), getHeight());
			
			int tx = getWidth()/2 - g.getFontMetrics().stringWidth(info)/2;
			int ty = getHeight() - 64 + g.getFontMetrics().getAscent()/2;
			g.setColor(new Color(0));
			g.drawString(info, tx, ty);
			g.setColor(new Color(0xff, 0xff, c));
			g.drawString(info, tx-1, ty-1);
			
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString(info, 
					getWidth()/2  - g.getFontMetrics().stringWidth(info)/2,
					getHeight()/2 - g.getFontMetrics().getHeight()/2);
		}
		
	}
	
	
	
}


