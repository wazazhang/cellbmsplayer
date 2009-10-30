package com.g2d.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.text.AttributedString;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.event.Event;
import com.g2d.display.event.MouseDragDropAccepter;
import com.g2d.display.event.MouseDragDropListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.text.TextBuilder;



public abstract class Stage extends DisplayObjectContainer
{
	private static final long serialVersionUID = Version.VersionG2D;
//	-----------------------------------------------------------------------------------------------------------

//	transition
	/** 切换场景时用的贞数 */
	private int 						transition_max_time			= 10;
	transient private boolean 			is_transition_in 		= true;
	transient private int 				transition_in_timer		= 0;
	transient private boolean 			is_transition_out		= false;
	transient private int 				transition_out_timer	= 0;
	
//	tip
	transient private TextTip			default_tip;
	transient private Tip				next_tip;
	transient private String			next_tip_text;
	transient private AttributedString	next_tip_atext;

//	picked object
	/**当前控件内，最后被鼠标捕获到的单位*/
	transient private DisplayObject		mouse_picked_object;
	transient private DisplayObject		last_mouse_picked_object;

//	drag drop object
	/** 当鼠标拖拽渲染时，被渲染的单位alpha度 */
	float								drag_drop_object_alpha		= 0.5f;
	/** 当鼠标拖拽渲染时，被渲染的单位缩放比率 */
	float								drag_drop_scale_x 			= 1.0f, 
										drag_drop_scale_y			= 1.0f;
	
	/**被拖拽的单位，该控件将覆盖显示鼠标提示*/
	transient private InteractiveObject	mouse_drag_drop_object;
	
//	-----------------------------------------------------------------------------------------------------------
	
	protected Stage() {}
	
	public void inited(Canvas root, Object[] args)
	{
		// no args process
	}

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
	
//	---------------------------------------------------------------------------------------------------------------

	public void setTransitionMaxTime(int time) {
		transition_max_time = Math.max(1, time);
	}
	
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

	@Override
	final public boolean onPoolEvent(Event<?> event) {
		return super.onPoolEvent(event);
	}

	final public void onAdded(Canvas canvas, int w, int h){
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
	
	final public void onRemoved(Canvas canvas) {
		super.onRemoved(this);
	}
	
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
		
		super.onUpdate(this);
		
		DisplayObject.main_timer ++;
	}
	
	@Override
	final public void onRender(Graphics2D g)
	{
		g.clip(local_bounds);
		
		super.onRender(g);
		
		if (getRoot().isMouseDown(MouseEvent.BUTTON_LEFT)) {
			if (mouse_picked_object instanceof InteractiveObject) {
				InteractiveObject interactive = (InteractiveObject) mouse_picked_object;
				if (interactive.enable_drag_drop) {
					startDragDrop(interactive);
				}
			}
		} 
		if (!getRoot().isMouseHold(MouseEvent.BUTTON_LEFT)) {
			if (mouse_drag_drop_object!=null) {
				stopDragDrop();
			}
		}
		

		last_mouse_picked_object = mouse_picked_object;
		mouse_picked_object = null;
		
		if (mouse_drag_drop_object!=null){
			renderDragged(g);
		}else{
			renderTip(g);
		}
		
		renderTransition(g);
	}

	private void renderDragged(Graphics2D g) 
	{
		mouse_drag_drop_object.onRenderDragDrop(this, g);
	}
	
	private void renderTip(Graphics2D g)
	{
//		System.out.println("last_mouse_picked_object = "+last_mouse_picked_object);
		
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
	
	private void renderTransition(Graphics2D g)
	{
		// default is alpha transition
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

	private void startDragDrop(InteractiveObject obj) {
//		if (mouse_drag_drop_object == null) {
			mouse_drag_drop_object = obj;
			mouse_drag_drop_object.onMouseStartDragDrop();
			for (MouseDragDropListener l : mouse_drag_drop_object.mouse_drag_drop_listeners) {
				l.onMouseStartDragDrop(mouse_drag_drop_object);
			}
//			System.out.println("start drag drop : " + mouse_drag_drop_object);
//		}
	}
	
	private void stopDragDrop() {
		if (mouse_drag_drop_object!=null){
			mouse_drag_drop_object.onMouseStopDragDrop(last_mouse_picked_object);
			for (MouseDragDropListener l : mouse_drag_drop_object.mouse_drag_drop_listeners) {
				l.onMouseStopDragDrop(mouse_drag_drop_object, last_mouse_picked_object);
			}
			if (last_mouse_picked_object instanceof InteractiveObject) {
				InteractiveObject accepter = ((InteractiveObject)last_mouse_picked_object);
				if (accepter.enable_accept_drag_drop){
					accepter.onDragDropedObject(mouse_drag_drop_object);
					for (MouseDragDropAccepter l : accepter.mouse_drag_drop_accepters) {
						l.onDragDropedObject(accepter, mouse_drag_drop_object);
					}
				}
			}
//			System.out.println("stop drag drop : " + mouse_drag_drop_object + " in " + last_mouse_picked_object);
			mouse_drag_drop_object = null;
		}
	}
	
//	---------------------------------------------------------------------------------------------------------------

	final public void focuseClean(Canvas canvas) {
		stopDragDrop();
	}
	
	protected void renderTransitionSplash(Graphics2D g) {
		
	}
	
	public void renderLostFocus(Graphics2D g){
		
	}
	
	public void onFocusGained(FocusEvent e) {
	}
	
	public void onFocusLost(FocusEvent e) {
	}
	
	/**
	 * 监听客户端窗口关闭按钮
	 * @return 如果使用默认的关闭方法则返回false，即点击关闭后程序退出，如果返回true，则会阻挡窗口关闭由子控件处理。
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

	public void setTipTextAntialiasing(boolean enable) {
		default_tip.enable_antialiasing = enable;
	}
	
//	----------------------------------------------------------------------------------------------------------------------

	/**
	 * 得到当前获得鼠标的最高层单位
	 * @return
	 */
	public DisplayObject getMousePickedObject() {
		return last_mouse_picked_object;
	}
	
	void setMousePickedObject(DisplayObject object) {
		mouse_picked_object = object;
	}

//	----------------------------------------------------------------------------------------------------------------------
	
	public void setDragDropObjectAlpha(float alpha) {
		alpha = Math.min(1.0f, alpha);
		alpha = Math.max(0.1f, alpha);
		drag_drop_object_alpha = alpha;
	}
	
	public InteractiveObject getDraggedObject() {
		return mouse_drag_drop_object;
	}
}
