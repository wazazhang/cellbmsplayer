package com.cell.gfx;

import java.awt.Image;
import java.awt.image.BufferedImage;

public interface IImage {

	/** None Flip Rotate */
	final public static byte TRANS_NONE = 0;
	
	/** Flip Vertical */
	final public static byte TRANS_V = 1;
	
	/** Flip Horizental */
	final public static byte TRANS_H = 2;
	
	/** Flip Horizental and Vertical */
	final public static byte TRANS_HV = 3;
	
	/** anticlockwise rotate 90 angle */
	final public static byte TRANS_90 = 6;
	
	/** anticlockwise rotate 270 angle */
	final public static byte TRANS_270 = 5;
	
	/** first anticlockwise rotate 90 angle , second flip Horizental */
	final public static byte TRANS_H90 = 4;
	
	/** first anticlockwise rotate 90 angle , second flip Vertical */
	final public static byte TRANS_V90 = 7;
	
	/** 180 Rotate */
	final public static byte TRANS_180 = 3; 

	public IImage newInstance();
	
	//Change size with a new surface
	public void createBuffer(int width, int height);
	
	public IImage resize(int newWidth, int newHeight);
	
	public Image getSrc() ;
	
//	public void setAlphaLevel(int value){
//		
//		
//	}
	
	//Copy a rect scope to a new Image of the image
	public IImage subImage(int x, int y, int width, int height);
	
	//Creates a new Graphics object that renders to this image. 
	public IGraphics getIGraphics() ;
	
	//Obtains ARGB pixel data from the specified region of this image and stores it in the provided array of integers. 
	public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) ;
	
	//Gets the width of the image in pixels. 
	public int getWidth() ;
    
    //Gets the height of the image in pixels. 
	public int getHeight() ;
	
	
	final static public int MODE_RAM	= 1;
	final static public int MODE_VRAM	= 2;
	final static public int MODE_FILE	= 3;
	
	public int setMode(int mode);
}
