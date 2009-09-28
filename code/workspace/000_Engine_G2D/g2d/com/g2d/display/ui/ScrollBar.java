package com.g2d.display.ui;


import java.awt.Graphics2D;

import com.cell.CMath;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.layout.UILayoutManager;


public class ScrollBar extends Container
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static class ScrollBarPair extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public ScrollBar vScroll;
		public ScrollBar hScroll;
		
		@Override
		protected void init_field() 
		{
			super.init_field();
			
			vScroll	= ScrollBar.createVScroll(12);
			hScroll = ScrollBar.createHScroll(12);
			
			super.addChild(vScroll);
			super.addChild(hScroll);
			
			this.enable_bounds 	= false;
			this.enable_input 	= false;
		}
		
		
		public ScrollBarPair() 
		{}
		
		synchronized public void addChild(UIComponent child) {
			Tools.printError("can not add a custom child component in " + getClass().getName() + " !");
		}
		synchronized public void removeChild(UIComponent child) {
			Tools.printError("can not remove a custom child component in " + getClass().getName() + " !");
		}
		
		public void update() 
		{
			super.update();
			
			int parentBorder = ((UIComponent)getParent()).layout.BorderSize;
			int parentBorder2 = parentBorder<<1;
			
			this.setSize(
					getParent().getWidth() - parentBorder2, 
					getParent().getHeight() - parentBorder2);
			
			this.setLocation(
					parentBorder, 
					parentBorder);
			
			vScroll.setSize(vScroll.size, getHeight() - hScroll.size);
			hScroll.setSize(getWidth()-vScroll.size, hScroll.size);
			
			vScroll.setLocation(
					getWidth()-vScroll.size, 
					0);
			hScroll.setLocation(
					0,
					getHeight()-hScroll.size);
			
//			if (vScroll.visible){
//				System.out.println("vScroll.max = "+ vScroll.max + "");
//			}
//			if (hScroll.visible){
//				System.out.println("hScroll.max = "+ hScroll.max);
//			}
			if (vScroll.getValueLength()+hScroll.getHeight()>=vScroll.max
					&& hScroll.getValueLength()+vScroll.getWidth()>=hScroll.max){
				vScroll.setValue(vScroll.value, vScroll.max);
				hScroll.setValue(hScroll.value, hScroll.max);
				vScroll.visible = false;
				hScroll.visible = false;
			}
			
		}
		
		public void render(Graphics2D g) {}
		
	}
	
	static public ScrollBar createVScroll(int size)
	{
		return new ScrollBar(true, size);
	}
	
	static public ScrollBar createHScroll(int size) 
	{
		return new ScrollBar(false, size);
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------
	
	
	public class Head extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public boolean isv = isvscroll;
		
		@Override
		public void added(DisplayObjectContainer parent) {
			isv = isvscroll;
			super.added(parent);
		}
		
		protected void onMouseDown(MouseEvent event) {
			ScrollBar.this.moveInterval(-1);
		}
	}
	
	public class Tail extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public boolean isv = isvscroll;
		
		@Override
		public void added(DisplayObjectContainer parent) {
			isv = isvscroll;
			super.added(parent);
		}
		
		protected void onMouseDown(MouseEvent event)
		{
			ScrollBar.this.moveInterval(1);
		}
	}
	
	public class Back extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public boolean isv = isvscroll;
	
		public Back() {
			enable_focus = false;
//			enable_input = false;
		}

		@Override
		public void added(DisplayObjectContainer parent) {
			isv = isvscroll;
			super.added(parent);
		}
		
		protected void onMouseDown(MouseEvent event) 
		{
			int direct = 0;
			if (ScrollBar.this.isvscroll) {
				direct = getParent().getMouseY() - (int)ScrollBar.this.strip.y;
				direct = CMath.getDirect(direct) * strip.getHeight();
			}else{
				direct = getParent().getMouseX() - (int)ScrollBar.this.strip.x;
				direct = CMath.getDirect(direct) * strip.getWidth();
			}
			ScrollBar.this.moveStrip(direct);
		}
		
		protected void onDrawMouseHover(Graphics2D g) {}
	}
	
	public class Strip extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public boolean isv = isvscroll;
		
		public Strip() {
			enable_drag = true;
		}

		@Override
		public void added(DisplayObjectContainer parent) {
			isv = isvscroll;
			super.added(parent);
		}
		
		public void update()
		{
			super.update();
		}
		
		protected void onMouseDraged(MouseMoveEvent event) {
			if (ScrollBar.this.isvscroll) {
				ScrollBar.this.setStripPos(getParent().getMouseY()-event.mouseDownStartY);
			}else{
				ScrollBar.this.setStripPos(getParent().getMouseX()-event.mouseDownStartX);
			}
		}
		
		protected void onDrawMouseHover(Graphics2D g) {}
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------
	
	public Head 			head;
	public Tail 			tail;
	public Back				back;
	public Strip			strip;
	
	private boolean			isvscroll		;
	private double			max 			= 100;
	private double			value 			= 0;
	private double			valuelength 	= 50;

	public int 				size			;
	public int 				interval		= 20;
	
//	--------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	protected void init_field()
	{
		super.init_field();
		
		head 		= new Head();
		tail 		= new Tail();
		back		= new Back();
		strip 		= new Strip();
		
		super.addChild(back);
		super.addChild(head);
		super.addChild(tail);
		super.addChild(strip);
	}
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
		
		UILayoutManager.getInstance().setLayout(back);
		UILayoutManager.getInstance().setLayout(head);
		UILayoutManager.getInstance().setLayout(tail);
		UILayoutManager.getInstance().setLayout(strip);
	}
	
	private ScrollBar(boolean vscrool, int size) 
	{
		this.isvscroll = vscrool;
		this.size = size;
	}
	
	synchronized public void addChild(UIComponent child) {
		Tools.printError("can not add a custom child component in " + getClass().getName() + " !");
	}
	synchronized public void removeChild(UIComponent child) {
		Tools.printError("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	public double getMax() {
		return this.max;
	}
	
	public void setMax(double max) {
		if (max<=0) {
			max = 1;
		}
		this.max = max;
	}

	public double getValue() {
		return this.value;
	}
	
	public double getValueLength() {
		return this.valuelength;
	}
	
	public void setValue(double value) {
		this.value = Math.min(value, max-this.valuelength);
		this.value = Math.max(this.value, 0);
	}
	
	public void setValue(double value, double len) {
		this.valuelength = Math.min(len, max);
		this.valuelength = Math.max(this.valuelength, 0);
		
		this.value = Math.min(value, max-this.valuelength);
		this.value = Math.max(this.value, 0);
	}
	
	public void moveInterval(int direction){
		setValue(value+direction*interval, valuelength);
	}

	
	private void setStripPos(int pos) {
		if (isvscroll) {
			double rate = max / back.getHeight();
			pos -= head.getHeight();
			setValue(pos * rate, this.valuelength);
		}
		else{
			double rate = max / back.getWidth();
			pos -= head.getWidth();
			setValue(pos * rate, this.valuelength);
		}
	}
	
	private void moveStrip(int len) {
		if (isvscroll) {
			setStripPos((int)strip.y+len);
		}
		else{
			setStripPos((int)strip.x+len);
		}
	}
	
	public void update()
	{
		if (isvscroll)
		{
			head.setSize(getWidth(), getWidth());
			head.setLocation(0, 0);
			
			tail.setSize(getWidth(), getWidth());
			tail.setLocation(0, getHeight()-getWidth());

			back.setSize(getWidth(), getHeight()-getWidth()*2);
			back.setLocation(0, getWidth());

			double rate = back.getHeight() / max;
			int sh = (int)(valuelength * rate);
			int sy = getWidth() + (int)(value * rate);
			strip.setSize(getWidth(), sh);
			strip.setLocation(0, sy);
		}
		else
		{
			head.setSize(getHeight(), getHeight());
			head.setLocation(0, 0);
			
			tail.setSize(getHeight(), getHeight());
			tail.setLocation(getWidth()-getHeight(), 0);

			back.setSize(getWidth() - getHeight()*2, getHeight());
			back.setLocation(getHeight(), 0);

			double rate = back.getWidth() / max;
			int sw = (int)(valuelength * rate);
			int sx = getHeight() + (int)(value * rate);
			strip.setSize(sw, getHeight());
			strip.setLocation(sx, 0);
		}
		
		if (valuelength == max) {
//			strip.visible = false;
			this.visible = false;
		}else{
//			strip.visible = true;
			this.visible = true;
		}
		
	}
	
	public int getWidth()
	{
		if (visible){
			return super.getWidth();
		}else{
			return 0;
		}
	}
	
	public int getHeight()
	{
		if (visible){
			return super.getHeight();
		}else{
			return 0;
		}
	}
}
