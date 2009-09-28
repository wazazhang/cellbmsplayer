package com.g2d.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.text.AttributedString;

import com.g2d.Version;
import com.g2d.display.event.Event;



public abstract class Stage extends DisplayObjectContainer
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static int 	transition_max_time = 10;
	
	transient CursorG2D 		cursor;
	transient private boolean 	is_transition_in 		= true;
	transient private int 		transition_in_timer		= 0;
	transient private boolean 	is_transition_out		= false;
	transient private int 		transition_out_timer	= 0;
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
		debug 					= false;
		is_transition_in 		= true;
		transition_in_timer		= 0;
		is_transition_out		= false;
		transition_out_timer	= 0;
		cursor					= new CursorG2D();
	}
	
	protected Stage() 
	{
		
	}
	
	public void inited(Object[] args)
	{
		// no args process
	}

//	---------------------------------------------------------------------------------------------------------------
	
	final public void startTransitionIn() {
		if (!is_transition_in) {
			is_transition_in = true;
			transition_in_timer = 0;
//			System.out.println("startTransitionOut");
		}
	}
	
	final public void startTransitionOut() {
		if (!is_transition_out) {
			is_transition_out = true;
			transition_out_timer = 0;
//			System.out.println("startTransitionOut");
		}
	}
	
	
	final public boolean isTransition() {
		return is_transition_out || is_transition_in;
	}

	final public boolean isTransitionIn() {
		return is_transition_in;
	}

	final public boolean isTransitionOut() {
		return is_transition_out;
	}

//	public void setCursorG2D(CursorG2D cursor) {
//		this.cursor = cursor;
//	}

//	---------------------------------------------------------------------------------------------------------------
	
	
	final public void onUpdate(Canvas canvas, int w, int h)
	{
		this.parent = this;
		this.root = canvas;
		
		this.x = 0;
		this.y = 0;
		this.local_bounds.x = 0;
		this.local_bounds.y = 0;
		this.local_bounds.width  = w;
		this.local_bounds.height = h;
		
		if (cursor!=null) {
			cursor.setLocation(mouse_x, mouse_y);
			cursor.onUpdate(this);
		}
		
		super.onUpdate(this);
		
		DisplayObject.main_timer ++;
	}

	final public void onAdded(Canvas canvas, int w, int h)
	{
		this.parent = this;
		this.root = canvas;
		
		this.x = 0;
		this.y = 0;
		this.local_bounds.x = 0;
		this.local_bounds.y = 0;
		this.local_bounds.width  = w;
		this.local_bounds.height = h;
		
		super.onAdded(this);
		
	}
	
	final public void onRemoved(Canvas canvas) 
	{
		super.onRemoved(this);
	}
	
	final public void onRender(Graphics2D g)
	{
		g.clip(local_bounds);
		super.onRender(g);
		if (cursor!=null){
			cursor.onRender(g);
		}
		
		if (is_transition_in) 
		{
			g.setColor(new Color(0,0,0, 1 - transition_in_timer / (float)transition_max_time));
			g.fill(local_bounds);
			
			if (transition_in_timer==0) {
				renderTransitionSplash(g);
			}
			
			transition_in_timer ++;
			
			if (transition_in_timer>transition_max_time) {
				is_transition_in = false;
			}
		}
		else if (is_transition_out) 
		{
			g.setColor(new Color(0,0,0, transition_out_timer / (float)transition_max_time));
			g.fill(local_bounds);
			
			transition_out_timer ++;
			
			if (transition_out_timer>transition_max_time) {
				is_transition_out = false;
			}
		}
	}

	@Override
	final public boolean onPoolEvent(Event<?> event) {
		return super.onPoolEvent(event);
	}
	
	
	protected void renderTransitionSplash(Graphics2D g) {}
	
	public void renderLostFocus(Graphics2D g){}
	
	public void onFocusGained(FocusEvent e) {}
	
	public void onFocusLost(FocusEvent e) {}
	
	/**
	 * 监听客户端窗口关闭按钮
	 * @return 如果使用默认的关闭方法则返回false，即点击关闭后程序退出
	 */
	public boolean onWindowClose(){return false;}

//	---------------------------------------------------------------------------------------------------------------
	
	public CursorG2D getCursorG2D() {
		return this.cursor;
	}
	
	/**
	 * 设置鼠标悬停
	 * @param text
	 */
	public void setTip(AttributedString text) {
		cursor.tip.setText(text);
	}
	
	/**
	 * 设置鼠标悬停
	 * @param text
	 */
	public void setTip(String text) {
		cursor.tip.setText(text);
	}
	
//	public void serialize(IOutput os) throws IOException {
//		super.serialize(os);
//	}
//	
//	public void deserialize(IInput is) throws IOException {
//		super.deserialize(is);
//	}

	final public class CursorG2D extends DisplayObjectContainer 
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		transient protected Tip tip;
		
		transient protected DisplayObject Focused;
		
		public CursorG2D() {}
		
		@Override
		protected void init_field() {
			super.init_field();
			tip = new Tip();
			tip.setLocation(20, 20);
			this.addChild(tip);
		}
		
		@Override
		protected void init_transient() {
			super.init_transient();
			priority	= Integer.MAX_VALUE;
		}

		@Override
		public void added(DisplayObjectContainer parent) {}
		@Override
		public void removed(DisplayObjectContainer parent) {}
		@Override
		public void update() {
			if (Focused!=null && !Focused.hit_mouse){
				Focused = null;
				tip.clearText();
			}
		}
		@Override
		public void render(Graphics2D g) {}
//		@Override
//		protected void render_childs(Graphics2D g) {
//			tip.clearText();
//		}
		
		public void setFocusedObj(DisplayObject obj){
			Focused = obj;
		}
		
		public DisplayObject getFocusedObj(){
			return Focused;
		}
		
		public void setTip(String text){
			tip.setText(text);
		}
		
		public void setTip(AttributedString text) {
			tip.setText(text);
		}
	}

}
