package com.cell.loader;

import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.Graphics;
import java.net.URL;
import java.util.Vector;

import javax.swing.JApplet;

import com.cell.classloader.jcl.JarClassLoader;
import com.cell.loader.AppletLoader.AppletStubAdapter;

public class CacheAppletLoader extends JarAppletLoader
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void onTaskInit()
	{
		
	}
	
	@Override
	protected void onTaskOver(Vector<byte[]> datas) throws Exception 
	{
		
	}

}
