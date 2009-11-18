package com.g2d.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import sun.awt.image.ImageWatched.Link;

import com.cell.CMath;
import com.cell.util.TypedHashtable;
import com.cell.util.event.EventListeners;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.event.Event;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.KeyListener;
import com.g2d.display.event.MouseDragDropAccepter;
import com.g2d.display.event.MouseDragDropListener;
import com.g2d.display.event.MouseDragResizeListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseListener;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.event.MouseWheelListener;
import com.g2d.display.ui.text.TextBuilder;

/**
 * InteractiveObject 二要素<br>
 * 1. 被聚焦 <br>
 *    处理键盘，鼠标滚轮事件<br>
 * 2. 已获得鼠标指针<br>
 *    处理鼠标点击事件<br>
 * @author WAZA
 */
public abstract class InteractiveObject extends DisplayObjectContainer
{
//	private static InteractiveObject s_focused_object = null;
	private static final long serialVersionUID = Version.VersionG2D;

	/**是否接收事件,如果为false,该对象就只相当于DisplayObjectContainer,也不向孩子传送事件*/
	public boolean				enable;
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
	/**允许拖拽到其他控件上，被拖拽时，将一直显示在鼠标上*/
	@Property("允许被拖拽到其他控件上，被拖拽时，将一直显示在鼠标上")
	public boolean				enable_drag_drop;
	/**允许接受拖拽的单位到此控件*/
	@Property("允许接受拖拽的单位到此控件")
	public boolean				enable_accept_drag_drop;
	
//	----------------------------------------------------------------------------------------------
	
	/**调整边距时的触碰范围*/
	private int					drag_border_size;
	
	private Dimension			minimum_size;
	
//	----------------------------------------------------------------------------------------------

	transient boolean 						is_focused;
	transient int 							catched_mouse_down_tick ;
	transient MouseMoveEvent 				mouse_draged_event ;

	transient Vector<KeyListener> 			keylisteners;
	transient Vector<MouseListener> 		mouselisteners;
	transient Vector<MouseWheelListener> 	mousewheellisteners;
	
	transient Vector<MouseDragResizeListener>	mouse_drag_resize_listeners;
	transient Vector<MouseDragDropListener>		mouse_drag_drop_listeners;
	transient Vector<MouseDragDropAccepter>		mouse_drag_drop_accepters;
	
	
	transient private	AnimateCursor		cursor;
	
//	-----------------------------------------------------------------------------------------------------------------
	
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
		enable_drag_drop		= false;
		drag_border_size		= 4;
		minimum_size			= new Dimension(9, 9);
	}

	@Override
	protected void init_transient() 
	{
		super.init_transient();
		is_focused 				= false;
		
		keylisteners 			= new Vector<KeyListener>();
		mouselisteners 			= new Vector<MouseListener>();
		mousewheellisteners		= new Vector<MouseWheelListener>();
		
		mouse_drag_resize_listeners	= new Vector<MouseDragResizeListener>();
		mouse_drag_drop_listeners	= new Vector<MouseDragDropListener>();
		mouse_drag_drop_accepters	= new Vector<MouseDragDropAccepter>();
	}

//	-----------------------------------------------------------------------------------------------------------------
	/**
	 * 设置控件最小值
	 * @param width
	 * @param height
	 */
	public void setMinimumSize(int width, int height) {
		minimum_size.setSize(
				Math.max((drag_border_size<<1)+1, width), 
				Math.max((drag_border_size<<1)+1, height));
	}
	
	public int getMinimumWidth() {
		return minimum_size.width;
	}
	
	public int getMinimumHeight() {
		return minimum_size.height;
	}

	public void setCursor(AnimateCursor cursor) {
		this.cursor = cursor;
	}
	public AnimateCursor getCursor() {
		return this.cursor;
	}
		
	/**
	 * 当前是否被父节点聚焦
	 * @return the is_focused
	 */
	public boolean isFocused() {
		return is_focused;
	}

//	-----------------------------------------------------------------------------------------------------------------
	
	
	@Override
	protected boolean testCatchMouse(Graphics2D g) {
		return enable && enable_focus;
	}

	protected void trySetCursor() 
	{
		if (enable_drag_resize && enable_drag && !enable_drag_drop && mouse_draged_event == null)
		{
			int drag_direct = getDragDirect(
					local_bounds,
					Math.max(drag_border_size, 4), 
					getMouseX(),
					getMouseY());
			setDragCursor(drag_direct);
		}
		else {
			getRoot().setCursor(cursor);
		}
	}

	protected void renderCatchedMouse(Graphics2D g){}

	protected void renderAcceptDrapDrop(Graphics2D g) {}
	
//	-----------------------------------------------------------------------------------------------------------------
	
	
	void onUpdate(DisplayObjectContainer parent) 
	{
		is_focused = parent.getFocus() == this;
		
		super.onUpdate(parent);
		
		if (enable)
		{
			if (enable_input && visible) {
				if (!getRoot().isMouseHold(java.awt.event.MouseEvent.BUTTON1)){
					stopDrag();
				} else if (mouse_draged_event!=null && !enable_drag_drop) {
					if (enable_drag) {
						onDrag();
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
			if (getParent()!=null) {
				if (getParent().getFocus() == this) {
					is_focused = true;
				} else if (enable_focus) {
					if ((event instanceof MouseEvent) && 
						((MouseEvent)event).type == MouseEvent.EVENT_MOUSE_DOWN) {
						if (isCatchedMouse()) {
							getParent().focus(this);
							is_focused = true;
						}
					}
				}
			}
			
			// 先向孩子传递
			if (is_focused) {
				if (super.onPoolEvent(event)) {
					return true;
				}
			}
			
			if (enable_input && visible) {
				if (event instanceof MouseWheelEvent) {
					return processMouseWheelEvent((MouseWheelEvent) event);
				} else if (event instanceof MouseEvent) {
					boolean ret = processMouseEvent((MouseEvent) event);
					if (ret) {
						System.out.println("f object = " + toString());
					}
					return ret;
				} else if (event instanceof KeyEvent) {
					return processKeyEvent((KeyEvent) event);
				}
			}
		}
		return false;
	}
	

	void renderInteractive(Graphics2D g) {
		if (enable) {
			if (isCatchedMouse()) {
				trySetCursor();
				renderCatchedMouse(g);
			}
			super.renderInteractive(g);
			if (enable_drag_resize && enable_drag && !enable_drag_drop) {
				renderDragResize(g);
			}
			if (enable_accept_drag_drop) {
				if (getStage()!=null) {
					InteractiveObject dragged = getStage().getDraggedObject();
					if (dragged!=null && dragged!=this){
						if (isPickedMouse()) {
							renderAcceptDrapDrop(g);
						}
					}
				}
			}
		} else {
			super.renderInteractive(g);
		}
	}
	
	boolean processKeyEvent(KeyEvent event) 
	{
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


	boolean processMouseWheelEvent(MouseWheelEvent event)
	{
		if (is_focused && enable_mouse_wheel)
		{
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
	
	
	boolean processMouseEvent(MouseEvent event)
	{
		if (isCatchedMouse() && isHitMouse())
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
				if (event.mouseButton == MouseEvent.BUTTON_LEFT && !enable_drag_drop) {
//					System.out.println("start drag");
					startDrag(event);
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
				stopDrag();
				return true;
			}
			
			return true;
		}
		return false;
	}

	void onRenderDragDrop(Stage stage, Graphics2D g)
	{
		Shape			clip		= g.getClip();
		AffineTransform	transfrom	= g.getTransform();
		Composite		composite	= g.getComposite();
		{
			float tx = stage.mouse_x - (getWidth()>>1)  * stage.drag_drop_scale_x;
			float ty = stage.mouse_y - (getHeight()>>1) * stage.drag_drop_scale_y;
			g.translate(tx, ty);

			g.scale(stage.drag_drop_scale_x, stage.drag_drop_scale_y);
			
			setAlpha(g, stage.drag_drop_object_alpha);
			
			this.renderBefore(g);
			this.render(g);
			this.renderInteractive(g);
			this.renderAfter(g);
			
			stage.getRoot().setCursor(getCursor());
		}
		g.setComposite(composite);
		g.setTransform(transfrom);
		g.setClip(clip);
	}
	
//	-----------------------------------------------------------------------------------------------------------------

	private void setDragCursor(int drag_direct) 
	{
		switch (drag_direct){
		case 0: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_NW); break;
		case 1: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_N); break;
		case 2: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_NE); break;
		
		case 3: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_W); break;
		case 5: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_E); break;
		
		case 6: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_SW); break;
		case 7: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_S); break;
		case 8: getRoot().setCursor(AnimateCursor.RESIZE_CURSOR_SE); break;
		default: getRoot().setCursor(cursor); break;
		}
	}
	
	private void startDrag(MouseEvent event) 
	{
		mouse_draged_event = new MouseMoveEvent(event.superEvent, getMouseX(), getMouseY());
		mouse_draged_event.user_data	= local_bounds.getBounds();
		mouse_draged_event.source		= this;
		mouse_draged_event.user_tag		= getDragDirect(
				(Rectangle)mouse_draged_event.getUserData(),
				Math.max(drag_border_size, 4), 
				getMouseX(),
				getMouseY());
		if (enable_drag_resize && mouse_draged_event.user_tag!=4) {
			this.onDragResizeStart(mouse_draged_event.user_tag);
			for (MouseDragResizeListener l : mouse_drag_resize_listeners) {
				l.onDragResizeStart(this, mouse_draged_event.user_tag);
			}
		}
	}
	
	
	private void stopDrag()
	{
		if (enable_drag_resize && mouse_draged_event!=null && mouse_draged_event.user_tag!=4) 
		{
			Rectangle start_rect = mouse_draged_event.getUserData();
			
			this.setLocation(
					x + start_rect.x, 
					y + start_rect.y);
			this.setSize(
					Math.max((int)(start_rect.getWidth()), drag_border_size), 
					Math.max((int)(start_rect.getHeight()), drag_border_size));
			
			this.onDragResizeEnd();
			for (MouseDragResizeListener l : mouse_drag_resize_listeners) {
				l.onDragResizeEnd(this);
			}
		}
		mouse_draged_event = null;
	}
	
	/**
	 * 拖动窗口和改变窗口大小，改变大小只有在右边，和在下边点时有效
	 * （event.user_tag 1 2 3）
	 * @param event
	 */
	private void onDrag() 
	{
		if (enable_drag_resize && mouse_draged_event.user_tag!=4) 
		{
			Rectangle dstart_rect = mouse_draged_event.getUserData();
			int sx = mouse_draged_event.mouseDownStartX;
			int sy = mouse_draged_event.mouseDownStartY;
			int dx = getMouseX() - sx;
			int dy = getMouseY() - sy;
			
			int rx = dstart_rect.x;
			int ry = dstart_rect.y;
			int rw = dstart_rect.width;
			int rh = dstart_rect.height;
			
			switch(mouse_draged_event.user_tag)
			{
			// north
			case 0: case 1: case 2:
				rh = local_bounds.height - dy;
				if (rh < minimum_size.height) {
					rh = minimum_size.height;
					ry = local_bounds.height - rh;
				} else {
					ry = local_bounds.y + dy;
				}
				break;
			// south
			case 6: case 7: case 8:
				rh = local_bounds.height + dy;
				if (rh < minimum_size.height) {
					rh = minimum_size.height;
				}
				break;
			}
			
			switch(mouse_draged_event.user_tag)
			{
			// west
			case 0: case 3: case 6:
				rw = local_bounds.width - dx;
				if (rw < minimum_size.width) {
					rw = minimum_size.width;
					rx = local_bounds.width - rw;
				} else {
					rx = local_bounds.x + dx;
				}
				break;
			// east
			case 2: case 5: case 8:
				rw = local_bounds.width + dx;
				if (rw < minimum_size.width) {
					rw = minimum_size.width;
				}
				break;
			}
			
			dstart_rect.setBounds(rx, ry, rw, rh);
		}
		else
		{
			this.setLocation(
					parent.getMouseX() - mouse_draged_event.mouseDownStartX, 
					parent.getMouseY() - mouse_draged_event.mouseDownStartY);
		}
	}

	/**
	 * 渲染完子控件后被调用
	 * @param g
	 */
	private void renderDragResize(Graphics2D g) 
	{
		if (mouse_draged_event != null && mouse_draged_event.user_tag != 4)		
		{
			pushObject(g.getClip());
			pushObject(g.getStroke());
			{
				Rectangle start_rect = mouse_draged_event.getUserData();			
				g.setClip(start_rect.x, start_rect.y, start_rect.width+1, start_rect.height+1);
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(drag_border_size));
				g.draw(start_rect);
			}
			g.setStroke(popObject(Stroke.class));
			g.setClip(popObject(Shape.class));
			
			setDragCursor(mouse_draged_event.user_tag);
		}
	}


	public boolean isOnDragged() {
		return mouse_draged_event!=null;
	}

//	-----------------------------------------------------------------------------------------------------------------
	
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
		if (listener instanceof MouseDragResizeListener) {
			mouse_drag_resize_listeners.add((MouseDragResizeListener)listener);
		}
		if (listener instanceof MouseDragDropListener) {
			mouse_drag_drop_listeners.add((MouseDragDropListener)listener);
		}
		if (listener instanceof MouseDragDropAccepter) {
			mouse_drag_drop_accepters.add((MouseDragDropAccepter)listener);
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
		if (listener instanceof MouseDragResizeListener) {
			mouse_drag_resize_listeners.remove((MouseDragResizeListener)listener);
		}
		if (listener instanceof MouseDragDropListener) {
			mouse_drag_drop_listeners.remove((MouseDragDropListener)listener);
		}
		if (listener instanceof MouseDragDropAccepter) {
			mouse_drag_drop_accepters.remove((MouseDragDropAccepter)listener);
		}
	}

//	-----------------------------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------------------------
//	mouse
	/**当有鼠标按键按下时发生
	 * @see MouseListener*/
	protected void onMouseDown(MouseEvent event) {}
	/**当有鼠标按键松开下时发生
	 * @see MouseListener*/
	protected void onMouseUp(MouseEvent event) {}
	/**当有鼠标按键完成了一次点击时发生(先按下，后松开)
	 * @see MouseListener*/
	protected void onMouseClick(MouseEvent event) {}
	/**当该控件被鼠标按键按住并移动时发生
	 * @see MouseListener*/
	protected void onMouseDraged(MouseMoveEvent event){}

//	-----------------------------------------------------------------------------------------------------------------
//	mouse wheell
	/**当该控件获得焦点并有鼠标滚轮事件发生时
	 * @see MouseWheelListener*/
	protected void onMouseWheelMoved(MouseWheelEvent event){}

//	-----------------------------------------------------------------------------------------------------------------
//	key
	/**当该控件获得焦点并有键盘按下某键时发生
	 * @see KeyListener*/
	protected void onKeyDown(KeyEvent event) {}
	/**当该控件获得焦点并有键盘松开某键时发生
	 * @see KeyListener*/
	protected void onKeyUp(KeyEvent event) {}
	/**当该控件获得焦点并有键盘敲入字符时发生
	 * @see KeyListener*/
	protected void onKeyTyped(KeyEvent event) {}

//	-----------------------------------------------------------------------------------------------------------------
//	drag resize
	/**
	 * 当该控件被鼠标开始拖动以改变大小时发生
	 * @param drag_type 拖拽的类型 
	 * @see InteractiveObject.getDragDirect(Rectangle bounds, int bs, int dx, int dy)
	 */
	protected void onDragResizeStart(int drag_type){}
	/**当该控件被鼠标拖动完成改变大小时发生*/
	protected void onDragResizeEnd(){}

//	-----------------------------------------------------------------------------------------------------------------
//	drag drop
	/**当该控件被鼠标开始拖拽时发生*/
	protected void onMouseStartDragDrop() {}
	/**
	 * 当该控件被鼠标放下拖拽时发生
	 * @param drop_end_object 放下时，接触到的单位
	 */
	protected void onMouseStopDragDrop(DisplayObject drop_end_object) {}

//	-----------------------------------------------------------------------------------------------------------------
//	drag accepter
	/**
	 * 当有某个控件被拖拽，并在此单位上释放时发生
	 * @param object 被拖拽的控件
	 */
	protected void onDragDropedObject(InteractiveObject object) {}
	
//	-----------------------------------------------------------------------------------------------------------------
//	-----------------------------------------------------------------------------------------------------------------
	
	/**
	 * @param bounds
	 * @param bs
	 * @param dx
	 * @param dy
	 * @return 
	 * 0 : North West<br>
	 * 1 : North <br>
	 * 2 : North East<br>
	 * 3 : West<br>
	 * 4 : Center<br>
	 * 5 : East<br>
	 * 6 : South West<br>
	 * 7 : South<br>
	 * 8 : South East<br>
	 */
	public static int getDragDirect(Rectangle bounds, int bs, int dx, int dy)
	{
		int bx = bounds.x;
		int by = bounds.y;
		int bw = bounds.width;
		int bh = bounds.height;
		int bd = bs<<1;
		int[][] bounds9 = new int[][] {
		{bx     , by,      bs,      bs     }, {bx + bs, by,      bw - bd, bs     }, {bw - bs, by,      bs,      bs     },
		{bx     , by + bs, bs,      bh - bd}, {bx + bs, by + bs, bw - bd, bh - bd}, {bw - bs, by + bs, bs,      bh - bd},
		{bx     , bh - bs, bs,      bs     }, {bx + bs, bh - bs, bw - bd, bs     }, {bw - bs, bh - bs, bs,      bs     },
		};
		for (int i = bounds9.length-1; i >=0; i--) {
			if (CMath.includeRectPoint2(
					bounds9[i][0], bounds9[i][1],
					bounds9[i][2], bounds9[i][3], 
					dx, dy)) {
				return i;
			}
		}
		return 4;
	}
	
}
