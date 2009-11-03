package com.cell.loader;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JApplet;

import sun.plugin2.applet.JNLP2ClassLoader;


import com.cell.classloader.jcl.CC;
import com.cell.classloader.jcl.JarClassLoader;
import com.cell.loader.LoadTask.LoadTaskListener;



/***
 * 
 * @author WAZA

	<PARAM name="l_jars"				value="lordol.jar,lordolres.jar">
	<PARAM name="l_applet"				value="lord.LordApplet">
	<PARAM name="l_font"				value="System">
	<PARAM name="l_natives"				value="OpenGL32,OpenAL32,warp_openal,joal_native">
	
	<PARAM name="img_bg"				value="bg.png">
	<PARAM name="img_loading_f"			value="loading_f.png">
	<PARAM name="img_loading_s" 		value="loading_s.png">
	<PARAM name="img_loading_b" 		value="loading_b.png">
	
	<PARAM name="l_text_loading"		value="loading...">
	<PARAM name="l_text_initializing"	value="initializing...">
	<PARAM name="l_text_error"			value="loading error, please refresh browser!">
	
	<PARAM name="load_retry_count"		value="5">
	<PARAM name="load_timeout"			value="10000">
 */
public class AppletLoader extends JApplet implements LoadTaskListener
{
	private static final long serialVersionUID = 1L;
	
	// param
	String 			l_jars;
	String 			l_applet;
	String			l_font;
	ArrayList<String>	l_natives = new ArrayList<String>();
	
	String			img_bg;
	String			img_loading_f;
	String			img_loading_s;
	String			img_loading_b;
	
	String 			l_text_loading;
	String 			l_text_initializing;
	String 			l_text_error;
	
	int				load_retry_count	=5;
	int				load_timeout		=10000;

	// other
	URL 			root_dir;
	
	Object 			applet_obj;
	JApplet 		applet_game = null;
	
	PaintTask 		paint_task;
	LoadTask 		load_task;

	String			dk;
	


	public String getParameter(String name, String default_value) {
		String ret = super.getParameter(name);
		if (ret==null) {
			ret = default_value;
		}
		return ret;
	}
	
	public void init()
	{
		try
		{
			dk = LoadTask.getVK(getClass().getResourceAsStream("vk.enc"));
			
			paint_task = new PaintTask(this);
			
			root_dir 			= new URL(getCodeBase().toString());
			
			// init applet parameter
			{
				l_jars				= getParameter("l_jars");
				l_applet			= getParameter("l_applet");
				l_font				= getParameter("l_font");

				img_bg				= getParameter("img_bg", 				"bg.png");
				img_loading_f		= getParameter("img_loading_f",			"loading_f.png");
				img_loading_s 		= getParameter("img_loading_s",			"loading_s.png");
				img_loading_b 		= getParameter("img_loading_b", 		"loading_b.png");
				
				l_text_loading		= getParameter("l_text_loading",		"loading...");
				l_text_initializing	= getParameter("l_text_initializing",	"initializing...");
				l_text_error		= getParameter("l_text_error",			"error");
				
				try {
				load_retry_count	= Integer.parseInt(getParameter("load_retry_count",		"5"));
				load_timeout		= Integer.parseInt(getParameter("load_timeout",			"10000"));
				} catch (Exception e) {
				load_retry_count	=5;
				load_timeout		=10000;
				}
				
				try{
					String natives = getParameter("l_natives");
					if (natives!=null) {
						for (String n : natives.split(",")) {
							l_natives.add(n);
						}
					}
				} catch (Exception err) {}
				
				LoadTask.LoadRetryTime 	= load_retry_count;
				LoadTask.LoadTimeOut	= load_timeout;
			}
			
			// make paint task
			{
				Thread imageloadtask = new Thread(){
					@Override
					public void run()
					{
						try
						{
							Image	bg 			= getImage(root_dir, img_bg);
							Image 	loading_f 	= getImage(root_dir, img_loading_f);
							Image 	loading_s 	= getImage(root_dir, img_loading_s);
							Image 	loading_b 	= getImage(root_dir, img_loading_b);
							Font 	font 		= new Font(l_font, Font.PLAIN, 14);

							paint_task.setPaintUnit(bg, loading_f, loading_s, loading_b, font);
							
						}catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("imageloadtask exited!");
					}
				};
				imageloadtask.start();
				
				paint_task.start();
			}
			
			// make load task
			{
				String[]	jarfiles 	= l_jars.split(",");
				for (int i=0; i<jarfiles.length; ++i) 
				{
					jarfiles[i] = jarfiles[i].trim();
					
					if (!jarfiles[i].startsWith("http://"))
					{
						System.out.print("redist " + jarfiles[i]);
						jarfiles[i] = getCodeBase().toString() + jarfiles[i];
						System.out.println("\t -> " + jarfiles[i]);
					}
				}
				
				load_task = new LoadTask(this, jarfiles);
			}
			
		}
		catch(Throwable err)
		{
			err.printStackTrace();
			paint_task.setState(l_text_error + " : " + err.getMessage());
		}
	}
	
	public void paint(Graphics g)
	{
		if (applet_game==null)
		{
			if (paint_task != null)
				paint_task.repaint((Graphics2D)g);
		}
	}
	
	
	
	public void loadBegin(URL[] files) {
		paint_task.setState(l_text_loading);
	}
	
	public void loadJarBegin(URL file, int current, int total) {
		paint_task.setState(l_text_loading+"(" + (current) + "/" + total + ")");
		paint_task.reset();
	}
	public void loadJarStart(URL file, int current, int total, int length) {
		paint_task.setMax(length);
	}
	public void loadJarProgress(URL file, int current, int total, int actual) {
		paint_task.acc(actual);
	}
	public void loadJarComplete(URL file, int current, int total, byte[] data) {
		
	}
	
	@SuppressWarnings("unchecked")
	public void loadAllComplete(Vector<byte[]> datas) 
	{
		System.out.println("loadCompleted !");

		paint_task.setState(l_text_initializing);
		paint_task.exit();
		
		try
		{			
//			
			ClassLoader			old_class_loader	= Thread.currentThread().getContextClassLoader();
			JarClassLoader		jar_class_loader	= JarClassLoader.createJarClassLoader(
					old_class_loader, datas, dk, true);
			Thread.currentThread().setContextClassLoader(jar_class_loader);
			System.out.println("Class loader changed : " + 
					old_class_loader.getClass().getName() + " -> " + 
					jar_class_loader.getClass().getName());
//			JarClassLoader.loadNatives(jar_class_loader, l_natives);

			
			Class mainclass = jar_class_loader.findClass(l_applet);
			applet_obj = mainclass.newInstance();
			JApplet game = null;
			
			if (applet_obj instanceof JApplet)
			{
				game = (JApplet)applet_obj;
				
				game.setStub(new AppletStubAdapter());
				
				game.init();
			
				System.out.println("game applet initrilized !");
				
				this.resize(game.getSize());
				
				this.setVisible(true);
				this.setEnabled(true);
				this.setFocusable(true);
				
				
				this.add(game);
				this.repaint();
				
				applet_game = game;
				
				System.out.println("applet added !");
				
				game.repaint();
				
				paint_task = null;
			}
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			paint_task.setState(l_text_error + " : " + e.getMessage());
		}
		
		this.validate();
	}
	
	public void loadError(Throwable cause)
	{
		System.out.println("loadError !");
		paint_task.setState(l_text_error + " : " + cause.getMessage());
	}
	
	
	class AppletStubAdapter implements AppletStub
	{
		public void appletResize(int width, int height) {
			AppletLoader.this.resize(width, height);
		}
		public AppletContext getAppletContext() {
			return AppletLoader.this.getAppletContext();
		}
		public URL getCodeBase() {
			return AppletLoader.this.getCodeBase();
		}
		public URL getDocumentBase() {
			return AppletLoader.this.getDocumentBase();
		}
		public String getParameter(String name) {
			return AppletLoader.this.getParameter(name);
		}
		public boolean isActive() {
			return AppletLoader.this.isActive();
		}
	}

}
