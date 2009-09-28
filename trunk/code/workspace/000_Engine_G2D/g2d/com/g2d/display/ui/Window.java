package com.g2d.display.ui;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.editor.FormEditor;
import com.g2d.editor.UIComponentEditor;
import com.g2d.util.Drawing;


public abstract class Window extends Container
{
	private static final long serialVersionUID = Version.VersionG2D;

//	--------------------------------------------------------------------------------------------------------------------------

	FormTransition 	transition 			= new SimpleScaleTransition();
	
	@Property("always_in_screen")
	public boolean	always_in_screen	= false;
	
	public boolean IsTransitionComplete = false;

	/**
	 * 是否只允许一个该类型的控件
	 */
	public boolean	is_single_view		= false;
	
//	--------------------------------------------------------------------------------------------------------------------------

	@Override
	protected void init_field() 
	{
		super.init_field();
		enable_drag = true;
		root_form = this;
	}
	
	public Window(){}
	
	@Override
	protected void render_childs(Graphics2D g) {
		if (transition!=null) {
			if (transition.isClosing() || transition.isOpening()){
				return;
			}
		}
		super.render_childs(g);
	}
	
	
	public void setTransition(FormTransition transition) {
		this.transition = transition;
	}
	
	public void open(DisplayObjectContainer screen) {
		if (is_single_view) {
			if (!screen.getChildsSubClass(getClass()).isEmpty()) {
				return;
			}
		}
		screen.addChild(this);
		if (transition!=null) {
			transition.startOpen();
		}
	}
	
	public void show(DisplayObjectContainer screen) {
		open(screen);
	}
	
	public void close(){
		if (transition!=null) {
			transition.startClose();
		}
		else {
			removeFromParent();
		}
	}
	
	public boolean isClosed() {
		return this.getParent() == null;
	}
	
	
	public void setCenter(DisplayObjectContainer screen) {
		setLocation(
				screen.getWidth()/2 - getWidth()/2, 		
				screen.getHeight()/2 - getHeight()/2
			);
	}
	
	
//	--------------------------------------------------------------------------------------------------------------------------

	@Override
	public void update() {
		if (transition!=null) {
			if (transition.isClosing() || transition.isOpening()) {
				transition.transition(this);
			}
			if (transition.isClosed()){
				visible = false;
				removeFromParent();
			}
		}
		
		if (always_in_screen) 
		{
			if (x < 0)
				x = 0;

			if (y < 0)
				y = 0;
			
			if (x > getRoot().getStageWidth() - getWidth())
				x = getRoot().getStageWidth() - getWidth();
			
			if (y > getRoot().getStageHeight() - getHeight())
				y = getRoot().getStageHeight() - getHeight();
		}
		
		super.update();
	}

	@Override
	public void render(Graphics2D g) {
		if (transition!=null){
			if (transition.isClosing() || transition.isOpening()){
				transition.transition_render(this, g);
			}
		}
		super.render(g);
	}
	
	/**
	 * 当 Transition 完成后调用，即窗口在打开或关闭时的变形
	 * @param transition
	 */
	protected void onTransitionComplete(FormTransition transition) {}
	
	
//	--------------------------------------------------------------------------------------------------------------------------
	
	public static abstract class FormTransition implements Serializable
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		protected int 		duration	= 10;
		protected int 		time		= 0;
		protected double	time_rate	= 0;
		
		private boolean 	is_open 	= true;
		
		public FormTransition(int duration) {
			this.duration = duration;
		}
		
		public FormTransition() {}
		
		public void startOpen() {
			time = 0;
			is_open = true;
		}
		
		public void startClose(){
			time = 0;
			is_open = false;
		}
		
		public boolean isOpening(){
			if (is_open) {
				if (time < duration) {
					return true;
				}
			}
			return false;
		}
		
		public boolean isClosing(){
			if (!is_open) {
				if (time < duration) {
					return true;
				}
			}
			return false;
		}
		
		public boolean isOpened(){
			if (is_open) {
				if (time >= duration) {
					return true;
				}
			}
			return false;
		}
		
		public boolean isClosed(){
			if (!is_open) {
				if (time >= duration) {
					return true;
				}
			}
			return false;
		}
		
		public void stopTransition() {
			time = duration;
		}
		
		
		public void transition(Window form){
			time ++;
			time_rate = time / (double)duration;
			
			if (time < duration){
				form.enable = false;
			}else{
				form.enable = true;
				form.IsTransitionComplete = true;
				form.onTransitionComplete(this);
			}
		}
		
		abstract public void transition_render(Window form, Graphics2D g);
		
	}
	
//	--------------------------------------------------------------------------------------------------------------------------
	
	public static class SimpleScaleTransition extends FormTransition
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public SimpleScaleTransition(int duration) {
			super(duration);
		}
		public SimpleScaleTransition() {}
		
		public void transition_render(Window form, Graphics2D g){
			int px = form.getWidth()>>1;
			int py = form.getHeight()>>1;
			g.translate(px, py);
			if (isOpening()) {
				g.scale(time_rate, time_rate);
			}else{
				g.scale(1-time_rate, 1-time_rate);
			}
			g.translate(-px, -py);
		}
		
	}
	
	public static class SimpleAlphaTransition extends FormTransition
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public SimpleAlphaTransition(int duration) {
			super(duration);
		}
		public SimpleAlphaTransition() {}
		
		public void transition_render(Window form, Graphics2D g){
			if (isOpening()) {
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)time_rate));
			}else{
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f-(float)time_rate));
			}
		}
		
	}
	
	
}
