package com.g2d.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.text.AttributedString;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.event.Event;
import com.g2d.display.ui.text.TextBuilder;



public abstract class Stage extends DisplayObjectContainer
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static int 	transition_max_time = 10;
	
//	transient CursorG2D 		cursor;
	transient private boolean 	is_transition_in 		= true;
	transient private int 		transition_in_timer		= 0;
	transient private boolean 	is_transition_out		= false;
	transient private int 		transition_out_timer	= 0;
	
	transient private TextTip			default_tip;
	transient private Tip				next_tip;
	transient private String			next_tip_text;
	transient private AttributedString	next_tip_atext;

	/**当前控件内，最后被鼠标捕获到的单位*/
	transient private DisplayObject		mouse_picked_object;
	transient private DisplayObject		last_mouse_picked_object;
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
		debug 					= false;
		is_transition_in 		= true;
		transition_in_timer		= 0;
		is_transition_out		= false;
		transition_out_timer	= 0;
		default_tip				= new TextTip();
//		cursor					= new CursorG2D();
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
		
//		if (cursor!=null) {
//			cursor.setLocation(getMouseX(), getMouseY());
//			cursor.onUpdate(this);
//		}
		
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
		
//		if (cursor!=null){
//			cursor.onRender(g);
//		}
		
//		synchronized (default_tip) 
		{
			last_mouse_picked_object = mouse_picked_object;
			mouse_picked_object = null;
			
//			System.out.println("last_mouse_picked_object = "+last_mouse_picked_object);
			
			if (next_tip != null) {
				next_tip.setLocation(this, mouse_x, mouse_y);
				next_tip.onUpdate(this);
				next_tip.onRender(g);
				next_tip = null;
			} else {
				if (next_tip_text!=null && next_tip_text.length()!=0) {
					next_tip_atext = TextBuilder.buildScript(next_tip_text);
					next_tip_text = null;
				}
				if (next_tip_atext != null) {
					default_tip.setText(next_tip_atext);
					default_tip.setLocation(this, mouse_x, mouse_y);
					default_tip.onUpdate(this);
					default_tip.onRender(g);
					next_tip_atext = null;
				}
			}
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
	
//	public CursorG2D getCursorG2D() {
//		return this.cursor;
//	}
	
	/**
	 * 设置鼠标悬停
	 * @param text
	 */
	public void setTip(AttributedString atext) {
		synchronized (default_tip) {
			next_tip_text = null;
			next_tip_atext = atext;
			next_tip = null;
		}
	}

	/**
	 * 设置鼠标悬停
	 * @param text
	 */
	public void setTip(String script) {
		synchronized (default_tip) {
			next_tip_text = script;
			next_tip_atext = null;
			next_tip = null;
		}
	}
	
	/**
	 * 设置鼠标悬停
	 * @param text
	 */
	public void setTip(Tip tip) {
		synchronized (default_tip) {
			next_tip_text = null;
			next_tip_atext = null;
			next_tip = tip;
		}
	}
	
	void setMousePickedObject(DisplayObject object) {
		mouse_picked_object = object;
	}
	
	/**
	 * 得到当前获得鼠标的最高层单位
	 * @return
	 */
	public DisplayObject getMousePickedObject() {
		return last_mouse_picked_object;
	}
}
