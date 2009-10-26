package com.g2d.display.ui;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.cell.CMath;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.layout.UILayoutManager;


public class ScrollBar extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;
	
//	--------------------------------------------------------------------------------------------------------------------------------
	int mouse_freeze_time = 500;
	
	public class Head extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public boolean isVscroll(){
			return ScrollBar.this.isvscroll;
		}

		protected void onMouseDown(MouseEvent event) {
			if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
				ScrollBar.this.moveInterval(-1);
			}
		}
		@Override
		public void update() {
			super.update();
			if (isPickedMouse() && isFocused() && getRoot().isMouseContinuous(mouse_freeze_time, MouseEvent.BUTTON_LEFT)){
				ScrollBar.this.moveInterval(-1);
			}
		}
	}
	
	public class Tail extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;

		public boolean isVscroll(){
			return ScrollBar.this.isvscroll;
		}

		protected void onMouseDown(MouseEvent event) {
			if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
				ScrollBar.this.moveInterval(1);
			}
		}
		@Override
		public void update() {
			super.update();
			if (isPickedMouse() && isFocused() && getRoot().isMouseContinuous(mouse_freeze_time, MouseEvent.BUTTON_LEFT)){
				ScrollBar.this.moveInterval(1);
			}
		}
	}
	
	public class Back extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;

		public boolean isVscroll(){
			return ScrollBar.this.isvscroll;
		}

		public Back() {}

		private int getDirect() {
			int direct = 0;
			if (ScrollBar.this.isvscroll) {
				direct = getParent().getMouseY() - (int)ScrollBar.this.strip.y;
				direct = CMath.getDirect(direct) * strip.getHeight();
			}else{
				direct = getParent().getMouseX() - (int)ScrollBar.this.strip.x;
				direct = CMath.getDirect(direct) * strip.getWidth();
			}
			return direct;
		}
		
		protected void onMouseDown(MouseEvent event) {
			if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
				ScrollBar.this.moveStrip(getDirect());
			}
		}
		
//		@Override
//		public void update() {
//			super.update();
//			if (isPickedMouse() && ScrollBar.this.isFocused() && getRoot().isMouseContinuous(mouse_freeze_time, MouseEvent.BUTTON_LEFT)){
//				ScrollBar.this.moveStrip(getDirect());
//			}
//		}
		
		protected void onDrawMouseHover(Graphics2D g) {}
		
	}
	
	public class Strip extends BaseButton
	{
		private static final long serialVersionUID = Version.VersionG2D;

		public boolean isVscroll(){
			return ScrollBar.this.isvscroll;
		}

		public Strip() {
			enable_drag = true;
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
		super.setAlwaysBottom(back);
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
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
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
	
	public boolean isMaxLength() {
		return valuelength >= max;
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
			int sh = (int)(valuelength * rate) + 1;
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
			int sw = (int)(valuelength * rate) + 1;
			int sx = getHeight() + (int)(value * rate);
			strip.setSize(sw, getHeight());
			strip.setLocation(sx, 0);
		}
		
		if (isMaxLength()) {
//			strip.visible = false;
			this.visible = false;
		}else{
//			strip.visible = true;
			this.visible = true;
		}
		
	}
//	
//	public int getWidth()
//	{
//		if (visible){
//			return super.getWidth();
//		}else{
//			return 0;
//		}
//	}
//	
//	public int getHeight()
//	{
//		if (visible){
//			return super.getHeight();
//		}else{
//			return 0;
//		}
//	}
//	
	

//	--------------------------------------------------------------------------------------------------------------------------------

	static public ScrollBar createVScroll(int size)
	{
		return new ScrollBar(true, size);
	}
	
	static public ScrollBar createHScroll(int size) 
	{
		return new ScrollBar(false, size);
	}
	


	public static class ScrollBarPair
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public ScrollBar v_scroll;
		public ScrollBar h_scroll;
		
		public ScrollBarPair(int size) 
		{
			v_scroll = ScrollBar.createVScroll(size);
			h_scroll = ScrollBar.createHScroll(size);
			v_scroll.visible = false;
			h_scroll.visible = false;
		}
		
		public Rectangle update(
				DisplayObjectContainer parent, 
				int sx, int sy, 
				int sw, int sh,
				double h_len, double h_max,
				double v_len, double v_max) 
		{
			int vw = sw;
			int vh = sh;
			
			{
				if (v_scroll.enable) {
					v_scroll.setLocation(sx + sw - v_scroll.size, sy);
					if (h_scroll.enable && h_scroll.visible) {
						v_scroll.setSize(v_scroll.size, sh - h_scroll.size);
					} else {
						v_scroll.setSize(v_scroll.size, sh);
					}
				}
				if (h_scroll.enable) {
					h_scroll.setLocation(sx, sy + sh - h_scroll.size);
					if (v_scroll.enable && v_scroll.visible) {
						h_scroll.setSize(sw - v_scroll.size, h_scroll.size);
					} else {
						h_scroll.setSize(sw, h_scroll.size);
					}
				}
				
				if (v_scroll.enable) {
					v_scroll.setMax(v_max);
					v_scroll.setValue(v_scroll.getValue(), v_len);
				}
				if (h_scroll.enable) {
					h_scroll.setMax(h_max);
					h_scroll.setValue(h_scroll.getValue(), h_len);
				}
				
				v_scroll.visible = !v_scroll.isMaxLength();
				h_scroll.visible = !h_scroll.isMaxLength();
				
				if (v_scroll.enable && v_scroll.visible) {
					vh = v_scroll.getHeight();
				}
				if (h_scroll.enable && h_scroll.visible) {
					vw = h_scroll.getWidth();
				}
			}
			return new Rectangle(sx, sy, vw, vh);
		}

	}
	
}
