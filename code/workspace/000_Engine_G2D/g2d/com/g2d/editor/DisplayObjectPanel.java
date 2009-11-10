package com.g2d.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import com.g2d.CanvasAdapter;
import com.g2d.SimpleCanvasNoInternal;
import com.g2d.Version;
import com.g2d.display.Canvas;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.Stage;

import com.g2d.util.AbstractFrame;

public class DisplayObjectPanel extends JPanel implements Runnable, ComponentListener
{
	private static final long serialVersionUID = 1L;
	
	final SimpleCanvasNoInternal canvas = new SimpleCanvasNoInternal(100, 100);
	ReentrantLock service_lock = new ReentrantLock();
	Thread service;
	
	public DisplayObjectPanel()
	{
		this.setLayout(new BorderLayout());
		this.addComponentListener(this);
		this.canvas.getCanvasAdapter().setStage(new ObjectStage());
		this.add(canvas);
	}
	
	public void componentResized(ComponentEvent e) {
		canvas.getCanvasAdapter().setStageSize(canvas.getWidth(), canvas.getHeight());
	}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	
	
	
	public Canvas getCanvas() {
		return canvas.getCanvasAdapter();
	}
	
	public Stage getStage() {
		return this.canvas.getCanvasAdapter().getStage();
	}

	public void start() {
		service_lock.lock();
		try{
			if (service==null) {
				service = new Thread(this);
				service.start();
				System.out.println("start");
			}
		} finally {
			service_lock.unlock();
		}
	}
	
	public void stop()
	{
		service_lock.lock();
		try{
			if (service!=null) {
				Thread t = service;
				try {
					service = null;
					t.join(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					t.interrupt();
				}				
				System.out.println("stop");
			}
		} finally {
			service_lock.unlock();
		}
	}

	public void run()
	{
		while (service != null) {
			try {
				if (isVisible()) {
					canvas.getCanvasAdapter().repaint_game();
					Thread.sleep(canvas.getCanvasAdapter().getFrameDelay());
				} else {
					Thread.sleep(1000);
					Thread.yield();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ObjectStage extends Stage
	{
		@Override
		public void added(DisplayObjectContainer parent) {
			getRoot().setFPS(40);
		}
		
		@Override
		public void removed(DisplayObjectContainer parent) {}
		
		@Override
		public void render(Graphics2D g) {
			g.setColor(Color.GREEN);
			g.fill(local_bounds);
		}
		
		@Override
		public void update() {}
	}
}
