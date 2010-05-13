package com.g2d;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.text.AttributedString;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.cell.CObject;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.AnimateCursor;
import com.g2d.display.Stage;
import com.g2d.display.event.Event;
import com.g2d.editor.DisplayObjectEditor;

final public class SimpleCanvasNoInternal extends Canvas implements CanvasContainer
{
	private static final long serialVersionUID = Version.VersionG2D;

	final CanvasAdapter canvas_adapter;
	
	boolean no_ddraw = true;
	{
		no_ddraw = "true".equals(System.getProperty("sun.java2d.noddraw"));
		
		System.out.println("sun.java2d.noddraw=" + System.getProperty("sun.java2d.noddraw"));
		
	}
	
	
	public SimpleCanvasNoInternal()
	{
		canvas_adapter = new CanvasAdapter(this);
	}
	
	public SimpleCanvasNoInternal(int width, int height)
	{
		super.setSize(width, height);
		
		canvas_adapter = new CanvasAdapter(this);
	}
	
//	--------------------------------------------------------------------------------
	
	public CanvasAdapter getCanvasAdapter() {
		return canvas_adapter;
	}
	
	public void update(Graphics dg) 
	{
		Graphics2D g = (Graphics2D)dg;
		
//		if (!no_ddraw)
//		{
//			g.setRenderingHint(
//					RenderingHints.KEY_INTERPOLATION, 
//					RenderingHints.VALUE_INTERPOLATION_BILINEAR
//					);
//		}
		
		canvas_adapter.update(g.getDeviceConfiguration());

		Image vm_buffer = canvas_adapter.getVMBuffer();
		
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	public void paint(Graphics dg)
	{
		Graphics2D g = (Graphics2D)dg;
		Image vm_buffer = canvas_adapter.getVMBuffer();
		if (vm_buffer != null) {
			g.drawImage(vm_buffer, 0, 0, getWidth(), getHeight(), this);
		}
	}

//	--------------------------------------------------------------------------------
	
	@Override
	public Component getComponent() {
		return this;
	}
	@Override
	public void superPaint(Graphics g) {
		super.paint(g);
	}
	@Override
	public void superUpdate(Graphics g) {
		super.update(g);
	}
	
}
