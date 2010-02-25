package com.g2d;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sun.awt.image.ImageWatched.Link;

import com.cell.CIO;
import com.cell.gfx.IGraphics;
import com.cell.gfx.IImage;
import com.cell.j2se.CGraphics;
import com.cell.math.MathVector;
import com.cell.math.TVector;
import com.cell.math.Vector;


public class Tools
{

//	--------------------------------------------------------------------------------

	static GraphicsEnvironment		ge;
	static GraphicsDevice			gd;
	static GraphicsConfiguration	gc;
	
	static Font 					default_font;
	static AlphaComposite[] 		all_composite;
	
	static
	{
		ge		= GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd		= ge.getDefaultScreenDevice();
		gc		= gd.getDefaultConfiguration();
		
		all_composite = new AlphaComposite[] {
				AlphaComposite.Clear, 
				AlphaComposite.Src,
				AlphaComposite.Dst,
				AlphaComposite.SrcOver,
				AlphaComposite.DstOver,
				AlphaComposite.SrcIn, 
				AlphaComposite.DstIn, 
				AlphaComposite.SrcOut,
				AlphaComposite.DstOut,
				AlphaComposite.SrcAtop,
				AlphaComposite.DstAtop, 
				AlphaComposite.Xor,
				};
		
		try{
			Graphics2D g2d = gc.createCompatibleImage(1, 1, Transparency.TRANSLUCENT).createGraphics();
			default_font = g2d.getFont();
			g2d.dispose();
		}catch(Exception err){
			err.printStackTrace();
		}
	}
	
//	--------------------------------------------------------------------------------

	/**
	 * {@link CanvasAdapter}.setDefaultFont(Font font)
	 * @param font
	 */
	static void setDefaultFont(Font font)
	{
		default_font = font;
	}

	public static Font getDefaultFont()
	{
		return default_font;
	}
	
	public static Cursor createCustomCursor(Image cursor, Point hotSpot, String name)
	{
		Dimension bestCursorSize = Toolkit.getDefaultToolkit().getBestCursorSize(
				cursor.getWidth(null), 
				cursor.getHeight(null));

//		Dimension bestCursorSize = new Dimension(
//				cursor.getWidth(null),
//				cursor.getHeight(null));

		System.out.println(
				cursor.getWidth(null) +","+ cursor.getHeight(null) + " -> " + 
				bestCursorSize.width + "," + bestCursorSize.height
		);
		
		
		
		if (cursor.getWidth(null) > bestCursorSize.width ||
			cursor.getHeight(null) > bestCursorSize.height ) 
		{
			BufferedImage bufferedImage = combianImage(
					bestCursorSize.width, 
					bestCursorSize.height,
					cursor);
			
			return Toolkit.getDefaultToolkit().createCustomCursor(
					bufferedImage,
					new Point(
					(int)(hotSpot.getX() * bestCursorSize.width / cursor.getWidth(null)),
					(int)(hotSpot.getY() * bestCursorSize.height/ cursor.getHeight(null))
					), 
					name);
		}
		else
		{
			BufferedImage bufferedImage = createImage(
					bestCursorSize.width, 
					bestCursorSize.height);
			
			Graphics g = bufferedImage.getGraphics();
			g.drawImage(cursor, 0, 0, null);
			g.dispose();
			
			return Toolkit.getDefaultToolkit().createCustomCursor(
					bufferedImage, hotSpot, name);
		}
	}

	public static ImageIcon createIcon(Image image)
	{
		ImageIcon icon = new ImageIcon(image);
		return icon;
	}
	
	
//	--------------------------------------------------------------------------------
	
	static public void setAlpha(Graphics2D g, float alpha)
	{
		if (alpha>=0 && alpha<1f){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
	}
	
	static public void setTextAntialiasing(Graphics2D g, boolean enable)
	{
		if (enable) {
			g.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		else {
			g.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING, 
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}
	
//	--------------------------------------------------------------------------------

	static public BufferedImage toAlpha(BufferedImage src, float alpha, int rgb)
	{
		rgb = 0x00ffffff & rgb;
		
		int a ;
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int x = w - 1; x >= 0; x--) {
			for (int y = h - 1; y >= 0; y--) {
				
				a = src.getRGB(x, y) & 0xff000000;
				if (a != 0) {
					a = a >>> 24;
					a = ((int) (a * alpha)) << 24;
					src.setRGB(x, y, a | rgb);
				}
			}
		}
		
		return src;
	}
	
	public static BufferedImage toTurngrey(BufferedImage src)
	{
		int rgb;
		int a, r, g, b;
		int w = src.getWidth();
		int h = src.getHeight();
		
		for (int x = w - 1; x >= 0; x--) {
			for (int y = h - 1; y >= 0; y--) {
				
				rgb = src.getRGB(x, y);
				a = (rgb & 0xff000000) >>> 24;
				if (a != 0) {
					r = (rgb & 0x00ff0000) >>> 16;
					g = (rgb & 0x0000ff00) >>> 8;
					b = (rgb & 0x000000ff);
					r = g;
					b = g;
					rgb = (a << 24) | (r << 16) | (g << 8) | b;
					src.setRGB(x, y, rgb);
				}
			}
		}
		
		return src;
	}

	static public BufferedImage createAlpha(BufferedImage src, float alpha, int rgb)
	{
		BufferedImage dst = combianImage(src.getWidth(), src.getHeight(), src);
		return toAlpha(dst, alpha, rgb);
	}
	
	static public BufferedImage createTurngrey(BufferedImage src) 
	{
		BufferedImage dst = combianImage(src.getWidth(), src.getHeight(), src);
		return toTurngrey(dst);
	}

	static public BufferedImage createImage(int width, int height)
	{
		try
		{
			BufferedImage buf = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);

			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createImage(IImage image)
	{
		try
		{
			BufferedImage buf = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);
			Graphics2D g = (Graphics2D)buf.createGraphics();
			IGraphics ig = new CGraphics(g);
			ig.drawImage(image, 0, 0, 0);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage combianImage(int width, int height, Image ... args)
	{
		try
		{
			BufferedImage dst = createImage(width, height);
			Graphics2D g = (Graphics2D)dst.createGraphics();
			for (Image img : args) {
				g.drawImage(img, 0, 0, width, height, null);
			}
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage scaleImage(Image args, double scale)
	{
		double dw = args.getWidth(null) * scale;
		double dh = args.getHeight(null) * scale;
		return combianImage((int)dw, (int)dh, args);
	}
	
	static public BufferedImage createTransformImage(Image src, int width, int height, AffineTransform transform)
	{
		try
		{
			BufferedImage dst = createImage(width, height);
			Graphics2D g = (Graphics2D)dst.createGraphics();
			
			g.setTransform(transform);
			g.drawImage(src, 0, 0, null);
			
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage createAlphaImage(Image src, float alpha)
	{
		try
		{
			BufferedImage dst = createImage(src.getWidth(null), src.getHeight(null));
			Graphics2D g = (Graphics2D)dst.createGraphics();
			
			setAlpha(g, alpha);
			g.drawImage(src, 0, 0, null);
			
			g.dispose();
			return dst;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		return null;
	}
	
	static public BufferedImage readImage(String file)
	{
		try
		{
			InputStream is = CIO.loadStream(file);

			BufferedImage src = ImageIO.read(is);
			
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			System.err.println(file);
			err.printStackTrace();
		}
		return null;
	}
	
	
	static public BufferedImage readImage(InputStream is)
	{
		try
		{
			BufferedImage src = ImageIO.read(is);
			
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
		
	}
	
	static public BufferedImage subImage(Image src, int sx, int sy, int sw, int sh)
	{
		try
		{
//			Image buf = gc.createCompatibleVolatileImage(src.getWidth(), src.getHeight(), Transparency.TRANSLUCENT);
			BufferedImage buf = gc.createCompatibleImage(sw, sh, Transparency.TRANSLUCENT);

			Graphics g = buf.getGraphics();
			g.drawImage(src, -sx, -sy, null);
			g.dispose();
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
		
	}
	
	static public BufferedImage cloneImage(Image src)
	{
		try
		{
			BufferedImage buf = gc.createCompatibleImage(src.getWidth(null), src.getHeight(null), Transparency.TRANSLUCENT);
			
			Graphics g = buf.getGraphics();
			g.drawImage(src, 0, 0, null);
			g.dispose();
			
			return buf;
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
		
		return null;
		
	}
	
	static public void writeImage(String file, Image image)
	{
		try
		{
			BufferedImage buf = null;
			
			if (image instanceof BufferedImage){
				buf = (BufferedImage)image;
			}else{
				buf = cloneImage(image);
			}
			
			ImageIO.write(buf, "png", new File(file));
			
		}
		catch(Exception err)
		{
			System.err.println(file);
			err.printStackTrace();
		}
	}
	
	
	
	
//	--------------------------------------------------------------------------------
	
	static private Random random = new Random();
	
	static public int getRandomInt() {
		return random.nextInt();
	}
	
	static public int getRandomUInt() {
		return Math.abs(random.nextInt());
	}
	
	static public float getRandomFloat() {
		return random.nextFloat();
	}
	
	static public float getRandomUFloat() {
		return Math.abs(random.nextFloat());
	}
	
	static public Random getRandom() {
		return random;
	}
	
//	--------------------------------------------------------------------------------

	static public void setClipboardText(String str){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection text = new StringSelection(str);
        clipboard.setContents(text,null);
	}
	
	static public String getClipboardText(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(clipboard);
        DataFlavor flavor = DataFlavor.stringFlavor;
        if(contents.isDataFlavorSupported(flavor)){
            try{
                return (String)contents.getTransferData(flavor);
            }catch(Exception ee){ee.printStackTrace();}    
        }
        return "";
	}
	
//	--------------------------------------------------------------------------------
	
	static public void printError(String text) {
		try {
			throw new Exception(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	--------------------------------------------------------------------------------
	
//	private static ThreadPool g2d_thread_pool;
//	
//	synchronized public static ThreadPool getThreadPool() 
//	{
//		if (g2d_thread_pool == null) {
//			g2d_thread_pool = new ThreadPool("G2D");
//		}
//		return g2d_thread_pool;
//	}
	
//	--------------------------------------------------------------------------------

	
	public static String toString(AttributedString texta)
	{
		StringBuilder sb = new StringBuilder();
		CharacterIterator iter = texta.getIterator();
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static AttributedString linkAttributedString(AttributedString texta, AttributedString textb)
	{
		String dst_text = toString(texta) + toString(textb);

		AttributedString ret = new AttributedString(dst_text);
		
		int i=0;
		{
			AttributedCharacterIterator ita = texta.getIterator();
			for (char c = ita.first(); c != CharacterIterator.DONE; c = ita.next()) {
				ret.addAttributes(ita.getAttributes(), i, i+1);
				i ++;
			}
		}{
			AttributedCharacterIterator itb = textb.getIterator();
			for (char c = itb.first(); c != CharacterIterator.DONE; c = itb.next()) {
				ret.addAttributes(itb.getAttributes(), i, i+1);
				i ++;
			}
		}
		return ret;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
