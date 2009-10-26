package com.g2d.display;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.event.Event;

public abstract class DisplayObjectContainer extends DisplayObject
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	class DisplayObjectEvent extends Event<DisplayObject> implements Serializable
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		final static public byte EVENT_SORT 		= 0;
		final static public byte EVENT_ADD 			= 1;
		final static public byte EVENT_DELETE 		= 2;

		final static public byte EVENT_MOVE_TOP 	= 11;
		final static public byte EVENT_MOVE_BOT 	= 12;
		
		byte event_type = 0;
		
		DisplayObject source;
		
		public DisplayObjectEvent(byte type, DisplayObject source) {
			this.event_type = type;
			this.source = source;
		}
		
		public DisplayObjectEvent(byte type) {
			this.event_type = type;
		}
	}
	
//	-------------------------------------------------------------

	/** true 如果不在local_bounds内,则忽略事件处理 (包括孩子的) */
	@Property("如果不在local_bounds内,则忽略事件处理 (包括孩子的)")
	protected boolean 			ignore_render_without_parent_bounds;
	
	transient ReentrantLock		elements_lock = new ReentrantLock();
	Vector<DisplayObject> 		elements;
	
	Queue<DisplayObjectEvent>	events;

	DisplayObject				always_top_element;
	DisplayObject				always_bottom_element;
//	-------------------------------------------------------------

	transient private Thread		update_thread;
//	transient private Thread		render_thread;
	
//	-------------------------------------------------------------

	@Override
	protected void init_field() {
		elements_lock	= new ReentrantLock();
		super.init_field();
		ignore_render_without_parent_bounds = false;
		elements		= new Vector<DisplayObject>();
		events			= new ConcurrentLinkedQueue<DisplayObjectEvent>();
	}
	
	@Override
	protected void init_transient() {
		super.init_transient();
		for (int i=elements.size()-1; i>=0; --i) {
			DisplayObject child = elements.elementAt(i);
			child.parent = this;
		}
	}
	
	final public void processEvent()
	{
		if (events.isEmpty()) return;
		
		synchronized(elements_lock)
		{
			while (!events.isEmpty()) 
			{
				DisplayObjectEvent event = events.poll();
				
				switch (event.event_type)
				{
				case DisplayObjectEvent.EVENT_SORT:
					Collections.sort(elements);
					break;

				case DisplayObjectEvent.EVENT_ADD:
					elements.add(event.source);
					event.source.parent = this;
					event.source.root	= this.root;
					event.source.onAdded(this);
					break;
					
				case DisplayObjectEvent.EVENT_DELETE:
					elements.remove(event.source);
					event.source.onRemoved(this);
					event.source.parent = null;
					break;
					
				case DisplayObjectEvent.EVENT_MOVE_TOP:
					elements.remove(event.source);
					elements.add(event.source);
					break;
					
				case DisplayObjectEvent.EVENT_MOVE_BOT:
					elements.remove(event.source);
					elements.insertElementAt(event.source, 0);
					break;
				}
			}
		}
	}
	
	
	boolean onPoolEvent(com.g2d.display.event.Event<?> event) 
	{
		if (always_top_element!=null) {
			return always_top_element.onPoolEvent(event);
		}else{
			for (int i=elements.size()-1; i>=0; --i) {
				if (elements.elementAt(i).onPoolEvent(event)) {
					return true;
				}
			}
		}
		// 如果孩子没有处理该消息则直接跳过该消息
		return false;
	}
	
	final void onAdded(DisplayObjectContainer parent) 
	{
		for (int i=elements.size()-1; i>=0; --i) {
			elements.elementAt(i).onAdded(parent);
		}
		this.added(parent);
	}
	
	final void onRemoved(DisplayObjectContainer parent) 
	{
		for (int i=elements.size()-1; i>=0; --i) {
			elements.elementAt(i).onRemoved(parent);
		}
		this.removed(parent);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	void onUpdate(DisplayObjectContainer parent) 
	{
		this.update_thread = Thread.currentThread();
		super.onUpdate(parent);
		this.update_thread = null;
	}
	
	@Override
	final void updateBefore(DisplayObjectContainer parent) {
		processEvent();
		updateChilds();
	}
	
	@Override
	final void updateAfter(DisplayObjectContainer parent) {
		if (always_top_element != null) {		
			if (always_top_element.parent != this) {
				setAlwaysTopFocus(null);
			}
			else if (elements.lastElement() != always_top_element) {
				focus(always_top_element);
			}
		}
	}
	
	protected void updateChilds() {
		synchronized(elements_lock){
			for (int i=elements.size()-1; i>=0; --i) {
				elements.elementAt(i).onUpdate(this);
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------
	
	
	void onRender(Graphics2D g) 
	{
		if (ignore_render_without_parent_bounds && !g.hitClip(
				(int)x + local_bounds.x, 
				(int)y + local_bounds.y, 
				local_bounds.width, 
				local_bounds.height)){
			return;
		} else {
			super.onRender(g);
		}
	}
	
	@Override
	void renderInteractive(Graphics2D g) {
		renderChilds(g);
	}
	
	protected void renderChilds(Graphics2D g) {
		synchronized(elements_lock){
			int size = elements.size();
			for (int i = 0; i < size; i++) {
				elements.elementAt(i).onRender(g);
			}
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------



	public void setDebug(boolean d) {
		super.setDebug(d);
		for (int i=elements.size()-1; i>=0; --i) {
			elements.elementAt(i).setDebug(d);
		}
	}
	

	final public void sort(){
		events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_SORT));
	}
	
	// focus
	final public DisplayObject getFocus() {
		if (!elements.isEmpty()) {
			return elements.lastElement();
		}
		return null;
	}
	
	final public void focus(DisplayObject child){
		if (always_top_element != null) {
			events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_TOP, always_top_element));
		} else if (child == always_bottom_element){
			events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_BOT, child));
		} else {
			events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_TOP, child));
		}
	}
	
//	top element
	final public void removeAlwaysTopFocus(){
		setAlwaysTopFocus(null);
	}
	final public void setAlwaysTopFocus(DisplayObject child) {
		if (child != null) {
			events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_TOP, child));
		}
		always_top_element = child;
	}
	final public DisplayObject getAlwaysTopFocus() {
		return always_top_element;
	}
	
//	bottom element
	final public void removeAlwaysBottom(){
		setAlwaysBottom(null);
	}
	final public void setAlwaysBottom(DisplayObject child) {
		if (child != null) {
			events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_BOT, child));
		}
		always_bottom_element = child;
	}
	final public DisplayObject getAlwaysBottom() {
		return always_bottom_element;
	}


//	-----------------------------------------------------------------------------------------------------------
	
	public boolean addChild(DisplayObject child){
		synchronized(elements_lock){
			if (child.parent==null) {
				child.parent = this;
				child.root = this.getRoot();
				events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_ADD, child));
				if (update_thread==null) {
					processEvent();
				}
				if (always_top_element!=null) {
					events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_MOVE_TOP, always_top_element));
				}
				return true;
			}
			return false;
		}
	}
	
	public boolean removeChild(DisplayObject child) {
		synchronized(elements_lock){
			if (child.parent == this) {
				child.parent = null;
				events.offer(new DisplayObjectEvent(DisplayObjectEvent.EVENT_DELETE, child));
				if (update_thread==null) {
					processEvent();
				}
				return true;
			}
			return false;
		}
	}

	final public boolean addChild(DisplayObject child, boolean immediately){
		if (addChild(child) && immediately) {
			processEvent();
			return true;
		}
		return false;
	}
	
	final public boolean removeChild(DisplayObject child, boolean immediately) {
		if (removeChild(child, false) && immediately) {
			processEvent();
			return true;
		}
		return false;
	}

	final public void addChilds(Collection<? extends DisplayObject> childs){
		synchronized(elements_lock) {
			for (DisplayObject child : childs) {
				addChild(child);
			}
		}
	}
	
	final public void removeChilds(Collection<? extends DisplayObject> childs) {
		synchronized(elements_lock) {
			for (DisplayObject child : childs) {
				removeChild(child);
			}
		}
	}

//	-----------------------------------------------------------------------------------------------------------

	final public boolean contains(DisplayObject child) {
		synchronized(elements_lock) {
			return elements.contains(child);
		}
	}
	
	final public int getChildCount() {
		return elements.size();
	}

	@SuppressWarnings("unchecked")
	final public Vector<DisplayObject> getChilds() {
		synchronized(elements_lock) {
			Vector<DisplayObject> ret = (Vector<DisplayObject>)(elements.clone());
			for (DisplayObjectEvent event : events) {
				if (event.event_type == DisplayObjectEvent.EVENT_ADD) {
					ret.add(event.source);
				}
			}
			return ret;
		}
	}
	
	/**
	 * 得到该类赋值兼容的对象(不包括子类的)
	 * @param <T>
	 * @param c 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final public<T> Vector<T> getChilds(Class<T> c) {
		synchronized(elements_lock) {
			Vector<T> actors = new Vector<T>();
			for (DisplayObject obj : elements) {
				if (c.equals(obj.getClass())) {
					actors.add((T)obj);
				}
			}
			for (DisplayObjectEvent event : events) {
				if (event.event_type == DisplayObjectEvent.EVENT_ADD) {
					if (c.equals(event.source.getClass())) {
						actors.add((T)event.source);
					}
				}
			}
			return actors;
		}
	}
	
	/**
	 * 得到该类赋值兼容的对象(包括子类的)
	 * @param <T>
	 * @param c 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	final public<T> Vector<T> getChildsSubClass(Class<T> c) {
		synchronized(elements_lock) {
			Vector<T> actors = new Vector<T>();
			for (DisplayObject obj : elements) {
				if (c.isInstance(obj)){
					actors.add((T)obj);
				}
			}
			for (DisplayObjectEvent event : events) {
				if (event.event_type == DisplayObjectEvent.EVENT_ADD) {
					if (c.isInstance(event.source)) {
						actors.add((T)event.source);
					}
				}
			}
			return actors;
		}
	}
	
	final public DisplayObject getChildAt(int index) {
		synchronized(elements_lock) {
			return elements.elementAt(index);
		}
	}

	final public DisplayObject findChild(Object object) {
		synchronized(elements_lock) {
			for (DisplayObject child : elements) {
				if (object.equals(child)) {
					return child;
				}
			}
			return null;
		}
	}

	final public int getChildIndex(DisplayObject child) {
		return elements.indexOf(child);
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 得到该类赋值兼容的对象(不包括子类的)
	 * @param <T>
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public<T extends DisplayObject> T getChildAtPos(int x, int y, Class<T> c) {
		for (int i=elements.size()-1; i>=0; --i) {
			DisplayObject obj = elements.elementAt(i);
			if (obj.local_bounds.contains(x-obj.x, y-obj.y)) {
				if (c.equals(obj.getClass())) {
					return c.cast(obj);
				}
			}
		}
		return null;
	}

	/***
	 * 得到该类赋值兼容的对象(包括子类的)
	 * @param <T>
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public<T extends DisplayObject> T getChildAtPosSubClass(int x, int y, Class<T> c) {
		for (int i=elements.size()-1; i>=0; --i) {
			DisplayObject obj = elements.elementAt(i);
			if (obj.local_bounds.contains(x-obj.x, y-obj.y)) {
				if (c.isInstance(obj)) {
					return c.cast(obj);
				}
			}
		}
		return null;
	}
	
	public DisplayObject getChildAtPos(int x, int y) {
		for (int i=elements.size()-1; i>=0; --i) {
			DisplayObject obj = elements.elementAt(i);
			if (obj.local_bounds.contains(x-obj.x, y-obj.y)) {
				return obj;
			}
		}
		return null;
	}

//	-------------------------------------------------------------------------------------------------------------------------------------
	
//	/**
//	 * 交换两个指定子对象的 Z 轴顺序（从前到后顺序）。 
//	 * @param child1
//	 * @param child2
//	 */
//	final public void swapChildren(DisplayObject child1, DisplayObject child2){
//		int index1 = elements.indexOf(child1);
//		int index2 = elements.indexOf(child2);
//		elements.set(index1, child2);
//		elements.set(index2, child1);
//	}
//	 
//	/**
//     * 在子级列表中两个指定的索引位置，交换子对象的 Z 轴顺序（前后顺序）
//     * @param index1
//     * @param index2
//     */   
//	final public void swapChildrenAt(int index1, int index2){
//		DisplayObject child1 = elements.elementAt(index1); 
//		DisplayObject child2 = elements.elementAt(index2); 
//		elements.set(index1, child2);
//		elements.set(index2, child1);
//	}
//	 
//	
//	/**
//	 * 返回对象的数组，这些对象位于指定点下，并且是该 DisplayObjectContainer 实例的子项（或孙子项，依此类推）。  
//	 * @param point 屏幕上的点
//	 * @return
//	 */
//	final public Vector<DisplayObject> getObjectsUnderScreenPoint(Point point) {
//		Vector<DisplayObject> ret = new Vector<DisplayObject>();
//		for (DisplayObject sub : elements) {
//			getObjectsUnderScreenPoint((DisplayObjectContainer)sub, point, ret);
//		}
//		return ret;
//	}
//	
//	static private void getObjectsUnderScreenPoint(DisplayObjectContainer obj, Point point, Vector<DisplayObject> points) {
//		if (obj.elements.size()>0) {
//			for (DisplayObject sub : obj.elements) {
//				if (sub instanceof DisplayObjectContainer) {
//					getObjectsUnderScreenPoint((DisplayObjectContainer)sub, point, points);
//				}
//			}
//		}
//		if (obj.hitTestScreenPoint(point.x, point.y)) {
//			points.add(obj);
//		}
//	}

//	-------------------------------------------------------------------------------------------------------------------------------------
	
	
}
