package com.g2d.display;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import com.cell.CMath;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.event.Event;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.KeyListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseListener;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.event.MouseWheelListener;

public abstract class InteractiveObject extends DisplayObjectContainer
{
//	private static InteractiveObject s_focused_object = null;
	private static final long serialVersionUID = Version.VersionG2D;

	
	transient public boolean 				is_focused;
	transient int 							catched_mouse_down_tick ;
	transient MouseMoveEvent 				mouse_draged_event ;
	
	transient Vector<KeyListener> 			keylisteners;
	transient Vector<MouseListener> 		mouselisteners;
	transient Vector<MouseWheelListener> 	mousewheellisteners;
	
	/**是否接收事件,如果为false,该对象就只相当于DisplayObjectContainer,也不向孩子传送事件*/
	public boolean							enable;
	/**是否处理输入事件,如果为false,则只向孩子传送事件*/
	@Property("是否处理输入事件,如果为false,则只向孩子传送事件") 
	public boolean 				enable_input;
	/**是否可被聚焦*/
	@Property("是否可被聚焦")
	public boolean 				enable_focus;
	/**是否可被拖动*/
	@Property("是否可被拖动")
	public boolean 				enable_drag;
	/**是否可以拖拽调整大小*/
	@Property("是否可以拖拽调整大小")
	public boolean				enable_drag_resize;
	/**是否支持鼠标滚轮事件*/
	@Property("是否支持鼠标滚轮事件")
	public boolean 				enable_mouse_wheel;
	/**是否支持键盘事件*/
	@Property("是否支持键盘事件")
	public boolean 				enable_key_input;
	
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		
		enable					= true;
		enable_input			= true;
		enable_focus			= true;
		enable_drag				= false;
		enable_drag_resize		= false;
		enable_mouse_wheel		= false;
		enable_key_input		= false;

	}

	@Override
	protected void init_transient() 
	{
		super.init_transient();
		is_focused 				= false;
		
		keylisteners 			= new Vector<KeyListener>();
		mouselisteners 			= new Vector<MouseListener>();
		mousewheellisteners		= new Vector<MouseWheelListener>();
	}
	
	void onUpdate(DisplayObjectContainer parent) 
	{
		is_focused = parent.getFocus() == this/* && parent.getParent() == parent*/;
		
		super.onUpdate(parent);
		
		if (enable)
		{
			if (enable_input && visible) {
				if (!getRoot().isMouseHold(java.awt.event.MouseEvent.BUTTON1)){
					mouse_draged_event = null;
				}
				else if (mouse_draged_event!=null) {
					if (enable_drag) {
						doOnDragged(mouse_draged_event);
					}
					onMouseDraged(mouse_draged_event);
				}
			}
		}
		
	}
	
	
	boolean onPoolEvent(Event<?> event) 
	{
		if (enable)
		{
			if (enable_focus && catched_mouse && event instanceof MouseEvent && ((MouseEvent)event).type == MouseEvent.EVENT_MOUSE_DOWN) {
				getParent().focus(this);
				is_focused = true;
			}
			
			if (is_focused) {
				// 先向孩子传递
				if (super.onPoolEvent(event)) {
					return true;
				}
			}
			
			if (enable_input && visible)
			{
				if (event instanceof MouseWheelEvent) 
				{
					return processMouseWheelEvent((MouseWheelEvent)event);
				}
				else if (event instanceof MouseEvent)
				{
					return processMouseEvent((MouseEvent)event);
				}
				else if (event instanceof KeyEvent) 
				{
					return processKeyEvent((KeyEvent)event);
				}
			}
		}
		return false;
	}
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return enable_focus && g.hitClip(mouse_x, mouse_y, 1, 1) && hit_mouse;
	}
	
	boolean processKeyEvent(KeyEvent event) 
	{
//		System.out.println("key type = "+ event.type);
//		if (event.superEvent.isActionKey()){
//			System.out.println("ActionKey");
//		}
//		System.out.println("keyCode = "+ event.keyCode);
//		System.out.println("keyChar = "+ event.keyChar);
		if (is_focused && enable_key_input)
		{
			event.source = this;
			switch(event.type)
			{
			case KeyEvent.EVENT_KEY_DOWN:
				onKeyDown(event);
				for (KeyListener listener : keylisteners) {
					listener.keyDown(event);
				}
				break;
			case KeyEvent.EVENT_KEY_UP:
				onKeyUp(event);
				for (KeyListener listener : keylisteners) {
					listener.keyUp(event);
				}
				break;
			case KeyEvent.EVENT_KEY_TYPED:
				onKeyTyped(event);
				for (KeyListener listener : keylisteners) {
					listener.keyTyped(event);
				}
				break;
			}
			return true;
		}
		return false;
	}

	boolean processMouseEvent(MouseEvent event)
	{
		if (catched_mouse)
		{
			event.source = this;
			switch(event.type)
			{
			case MouseEvent.EVENT_MOUSE_DOWN:
				// mouse down event
				onMouseDown(event);
				for (MouseListener listener : mouselisteners) {
					listener.mouseDown(event);
				}
				// mouse click record
				catched_mouse_down_tick = event.mouseDownCount;
				// mouse drag start
				if (event.mouseButton == MouseEvent.BUTTON_LEFT) {
//					System.out.println("start drag");
					mouse_draged_event = new MouseMoveEvent(event.superEvent, getMouseX(), getMouseY());
					mouse_draged_event.user_tag = mouseLocation(getMouseX(), getMouseY());
					mouse_draged_event.user_data = local_bounds.getBounds();
					mouse_draged_event.source = this;
				}else{
					mouse_draged_event = null;
				}
				return true;
			case MouseEvent.EVENT_MOUSE_UP:
				// mouse up event
				onMouseUp(event);
				for (MouseListener listener : mouselisteners) {
					listener.mouseUp(event);
				}
				// mouse click test
				if (catched_mouse_down_tick == event.mouseDownCount) {
					onMouseClick(event);
					for (MouseListener listener : mouselisteners) {
						listener.mouseClick(event);
					}
				}
				// mouse drag end
				mouse_draged_event = null;
				return true;
			}
			
			return true;
		}
		return false;
	}
	
	boolean processMouseWheelEvent(MouseWheelEvent event) {
		if (is_focused && enable_mouse_wheel){
			event.source = this;
			onMouseWheelMoved(event);
			if (!mousewheellisteners.isEmpty()){
				for (MouseWheelListener listener : mousewheellisteners) {
					listener.mouseWheelMoved(event);
				}
			}
			return true;
		}
		return false;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	protected void onMouseDown(MouseEvent event) {}
	protected void onMouseUp(MouseEvent event) {}
	protected void onMouseClick(MouseEvent event) {}
	protected void onMouseDraged(MouseMoveEvent event){}
	protected void onMouseWheelMoved(MouseWheelEvent event){}
	
	protected void onKeyDown(KeyEvent event) {}
	protected void onKeyUp(KeyEvent event) {}
	protected void onKeyTyped(KeyEvent event) {}
	
//	-----------------------------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------------------------
	/**
	 * 计算鼠标在控件的位置
	 * return 0 中间  1 右 2 右下 3 下 4 左下 5 左 6 左上 7 上 8 右上
	 */
	int mouseLocation(int sx, int sy) 
	{
		if (isDragResizeE(sx, sy)){
			return 1;
		}
		else if (isDragResizeSE(sx, sy)){
			return 2;
		}
		else if (isDragResizeS(sx, sy)){
			return 3;
		}
		else if (isDragResizeSW(sx, sy)){
			return 4;
		}
		else if (isDragResizeW(sx, sy)){
			return 5;
		}
		else if (isDragResizeNW(sx, sy)){
			return 6;
		}
		else if (isDragResizeN(sx, sy)){
			return 7;
		}
		else if (isDragResizeNE(sx, sy)){
			return 8;
		}
		return 0;
	}
	
	boolean isDragResizeSE(int sx, int sy)
	{
		int sw = 4;
		int sh = 4;
		int dx = local_bounds.x + local_bounds.width  - sw;
		int dy = local_bounds.y + local_bounds.height - sh;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeS(int sx, int sy)
	{
		int sw = local_bounds.width-8;
		int sh = 4;
		int dx = local_bounds.x + 4;
		int dy = local_bounds.y + local_bounds.height - sh;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeN(int sx, int sy)
	{
		int sw = local_bounds.width-8;
		int sh = 4;
		int dx = local_bounds.x + 4;
		int dy = local_bounds.y;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeE(int sx, int sy)
	{
		int sw = 4;
		int sh = local_bounds.height-8;
		int dx = local_bounds.x + local_bounds.width  - sw;
		int dy = local_bounds.y + 4;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeW(int sx, int sy)
	{
		int sw = 4;
		int sh = local_bounds.height-8;
		int dx = local_bounds.x;
		int dy = local_bounds.y + 4;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeSW(int sx, int sy)
	{
		int sw = 4;
		int sh = 4;
		int dx = local_bounds.x;
		int dy = local_bounds.y + local_bounds.height - sh;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	boolean isDragResizeNW(int sx, int sy)
	{
		int sw = 4;
		int sh = 4;
		int dx = local_bounds.x;
		int dy = local_bounds.y;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
//		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
//		{
//			return true;
//		}
		
		return false;
	}
	
	boolean isDragResizeNE(int sx ,int sy)
	{
		int sw = 4;
		int sh = 4;
		int dx = local_bounds.x + local_bounds.width - sw;
		int dy = local_bounds.y;
		
		//System.out.println(dx + "," + dy + "," + sw + "," + sh + "," + " - > " + sx + "," + sy);
		
		if (CMath.includeRectPoint2(dx, dy, sw, sh, sx, sy)) 
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 拖动窗口和改变窗口大小，改变大小只有在右边，和在下边点时有效
	 * （event.user_tag 1 2 3）
	 * @param event
	 */
	public void doOnDragged(MouseMoveEvent event) 
	{
		if (enable_drag_resize && event.user_tag > 0 && event.user_tag < 4) 
		{
			Rectangle start_rect = event.getUserData();
//			int fx = start_rect.x + start_rect.width  - event.mouseDownStartX;
//			int fy = start_rect.y + start_rect.height - event.mouseDownStartY;
//			
//			int w = Math.abs(local_bounds.x - (mouse_x + fx));
//			int h = Math.abs(local_bounds.y - (mouse_y + fy));
//			
//			this.setSize(w, h);
			int dx = 0;
			int dy = 0;
			
//			System.out.println("getMouseX() = "+getMouseX()+"event.mouseDownStartX = "+event.mouseDownStartX);
//			System.out.println("getMouseY() = "+getMouseY()+"event.mouseDownStartY = "+event.mouseDownStartY);
			switch (event.user_tag)
			{
			case 1:
				dx = getMouseX()-event.mouseDownStartX;
				break;
			case 2:
				dx = getMouseX()-event.mouseDownStartX;
				dy = getMouseY()-event.mouseDownStartY;
				break;
			case 3:
				dy = getMouseY()-event.mouseDownStartY;
				break;
//			case 4:
//				dx = getMouseX()-event.mouseDownStartX;
//				dy = getMouseY()-event.mouseDownStartY;
//				this.setLocation(
//						parent.getMouseX() - event.mouseDownStartX, 
//						this.getY());
//				break;
//			case 5:
//				dx = getMouseX()-event.mouseDownStartX;
//				this.setLocation(
//						parent.getMouseX() - event.mouseDownStartX, 
//						this.getY());
//				break;
//			case 6:
//				dx = getMouseX()-event.mouseDownStartX;
//				dy = getMouseY()-event.mouseDownStartY;
//				this.setLocation(
//						parent.getMouseX() - event.mouseDownStartX, 
//						parent.getMouseY() - event.mouseDownStartY);
//				break;
//			case 7:
//				dy = getMouseY()-event.mouseDownStartY;
//				this.setLocation(
//						this.getX(), 
//						parent.getMouseY() - event.mouseDownStartY);
//				break;
//			case 8:
//				dx = getMouseX()-event.mouseDownStartX;
//				dy = getMouseY()-event.mouseDownStartY;
//				this.setLocation(
//						this.getX(), 
//						parent.getMouseY() - event.mouseDownStartY);
//				break;
			}
			
			int fw = Math.abs(start_rect.width +dx);
			int fh = Math.abs(start_rect.height+dy);
			
			this.setSize(fw, fh);
		}
		else
		{
			this.setLocation(
					parent.getMouseX() - event.mouseDownStartX, 
					parent.getMouseY() - event.mouseDownStartY);
			// 修正了鼠标
			if (getRoot().getStage().cursor!=null){
				getRoot().getStage().cursor.setLocation(getRoot().getMouseX(), getRoot().getMouseY());
			}
		}
	}


	@Override
	protected void afterRender(Graphics2D g) 
	{
		if (enable_drag_resize) 
		{
//			if (isDragResizeSE(mouse_x, mouse_y) || (mouse_draged_event!=null && mouse_draged_event.user_tag != 0)){
//				getRoot().setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
//			}
			int tag;
			if (mouse_draged_event!=null){
				tag = mouse_draged_event.user_tag;
			}else{
				tag = mouseLocation(mouse_x, mouse_y);
			}
			switch (tag){
			case 1:
//			case 5:
				getRoot().setCursor(AnimateCursor.E_RESIZE_CURSOR);
				break;
			case 2:
//			case 6:
				getRoot().setCursor(AnimateCursor.SE_RESIZE_CURSOR);
				break;
			case 3:
//			case 7:
				getRoot().setCursor(AnimateCursor.S_RESIZE_CURSOR);
				break;
//			case 4:
//			case 8:
//				getRoot().setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
//				break;
			}
			
		}
	}
	
	public boolean isOnDragged() {
		return mouse_draged_event!=null;
	}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	public void addEventListener(EventListener listener) {
		if (listener instanceof KeyListener) {
			keylisteners.add((KeyListener)listener);
		}
		if (listener instanceof MouseListener) {
			mouselisteners.add((MouseListener)listener);
		}
		if (listener instanceof MouseWheelListener) {
			mousewheellisteners.add((MouseWheelListener)listener);
		}
	}
	
	public void removeEventListener(EventListener listener) {
		if (listener instanceof KeyListener) {
			keylisteners.remove((KeyListener)listener);
		}
		if (listener instanceof MouseListener) {
			mouselisteners.remove((MouseListener)listener);
		}
		if (listener instanceof MouseWheelListener) {
			mousewheellisteners.remove((MouseWheelListener)listener);
		}
	}
	
	
}
