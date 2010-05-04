package com.g2d.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

import com.cell.CMath;
import com.cell.DObject;
import com.cell.util.MarkedHashtable;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.ui.UIComponent;
import com.g2d.editor.DisplayObjectEditor;
import com.g2d.editor.UIComponentEditor;
import com.sun.org.apache.bcel.internal.classfile.Code;


/**
 * @author WAZA
 *
 */
public abstract class DisplayObject extends DObject implements Comparable<DisplayObject>, ImageObserver
{
	private static final long serialVersionUID = Version.VersionG2D;

//	--------------------------------------------------------------------------------------------------------------------------
	
//	public static int 		main_timer;

	static Stack<Object>	display_stack = new Stack<Object>();
	

//	-------------------------------------------------------------
//	 public
	
	/** 是否可视,如果父节点不可视,那么子节点将也不可视 */ 
	@Property("是否可视,如果父节点不可视,那么子节点将也不可视") 
	public boolean 				visible;
	
	/** 基于父节点的位置 */
	@Property("基于父节点的位置")
	public double				x, y, z;
	
	/** 优先级别 */
	@Property("优先级别")
	public int 					priority;
	
	@Property("当前坐标系的 rectangle")
	/** 当前坐标系的 rectangle */
	final public Rectangle 		local_bounds 		= new Rectangle(0,0,100,100);
	
	protected boolean			clip_local_bounds;
	
//	-------------------------------------------------------------
	
	/** debug , transient 代表运行时的数据，不能够序列化*/
	@Property("显示debug框")
	transient public boolean 	debug;
	
	/** 该对象被更新了多少次, 不可序列化 */
	transient public int 		timer;

	/**上次更新和这次更新相距的时间*/
	transient public int		interval_ms;
	
	transient long				last_update_time;
//	-------------------------------------------------------------
//	extends 
	
	/** 表示鼠标是否在local_bounds内,
	 * isHitMouse的快照，如果isHitMouse被重写，则该变量将无效*/
	transient private boolean	hit_mouse;
	
	/** isCatchedMouse的快照，如果isCatchedMouse被重写，则该变量将无效*/
	transient private boolean	catched_mouse;
	
	/**鼠标在当前对象的相对坐标*/
	transient int		mouse_y;
	transient int 		mouse_x;
	
	//	-------------------------------------------------------------
//	 local
	
	/** 父节点 */
	transient DisplayObjectContainer	parent;
	/** 父节点向子节点递归时,向子节点传输 root canvas 信息 */
	transient Canvas 					root;
	/** 屏幕坐标系的rectangle */
	transient Rectangle 				screen_rectangle;
	/** 屏幕坐标系的 x, y */
	transient int 						screen_x, screen_y;
	
//	-------------------------------------------------------------
	transient DisplayObjectEditor<?>	editor;
//	-------------------------------------------------------------

//	MarkedHashtable						data_group = new MarkedHashtable();
	
//	--------------------------------------------------------------------------------------------------------------------------------------------

//	protected DisplayObject(){
//		init_field();
//		init_transient();
//	}
	
	/**初始化可序列化字段*/
	protected void init_field() {
		visible 			= true;
		clip_local_bounds	= false;
	}
	
	/**初始化不可序列化字段, 该方法将在构造函数和反序列化后调用*/
	protected void init_transient()	{
		screen_rectangle	= new Rectangle(0,0,0,0);
	}
	
	
//	protected void onRead(MarkedHashtable data){}
//	
//	protected void onWrite(MarkedHashtable data){}
//	
////	private void writeObject(java.io.ObjectOutputStream out) throws IOException {   }   
////	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {   }
////	final public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {}
////	final public void writeExternal(ObjectOutput out) throws IOException {}
//
//	final protected Object writeReplace() throws ObjectStreamException {
//		try{
//			onWrite(data_group);
//		}catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return this;
//	}
//	
//	final protected Object readResolve() throws ObjectStreamException {
//		try{
//			onRead(data_group);
//			init_transient();
//		}catch (Throwable e) {
//			e.printStackTrace();
//		}
//		return this;
//	}
	
//  ==Serializable==
//	writeReplace invoked 
//	writeObject invoked 
//	readObject invoked 
//	readResolve invoked 
	
//	==Externalizable==
//	writeReplace invoked 
//	writeExternal invoked 
//	readExternal invoked 
//	readResolve invoked 
	
//	--------------------------------------------------------------------------------------------------------------------------------------------

	public int compareTo(DisplayObject o) {
		return (int)((z+priority) - (o.z+o.priority));
	}
	
	/**
	 * 得到stage外的最上层java控件
	 * @return
	 */
	public com.g2d.display.Canvas getRoot() {
		return root;
	}
	
	/**
	 * 得到父节点
	 * @return
	 */
	public DisplayObjectContainer getParent() {
		return parent;
	}
	
	/**
	 * 得到当前所在的stage
	 * @return
	 */
	public Stage getStage() {
		if (root != null) {
			return root.getStage();
		}
		return null;
	}
	
	/**
	 * 是否在当前操作系统中被聚焦
	 * @return
	 */
	public boolean isFocusedRoot()
	{
		if (root!=null && !root.isFocusOwner()){
			return false;
		}
		
		DisplayObjectContainer p = this.parent;
		DisplayObject c = this;
		
		while (p != null) 
		{
			if (p.getFocus() != c) {
				return false;
			}
			c = p;
			p = p.getParent();
		}
		
		return true;
	}
	
	/**
	 * 从父节点移除自己
	 * @return
	 */
	public boolean removeFromParent() {
		try{
			if( parent != null) {
				parent.removeChild(this);
				return true;
			}
		}finally{
			parent = null;
		}
		return false;
	}
//	--------------------------------------------------------------------------------------------------------------------------------------------

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------
//	advanced
	
	/**
	 * 得到该object对应的编辑窗口
	 * @return
	 */
	final public DisplayObjectEditor<?> getEditorForm() {
		if (editor == null) {
			editor = createEditorForm();
		}
		return editor;
	}
	
	/**
	 * 创建该object对应的编辑窗口，一般由子类覆盖
	 * @return
	 */
	public DisplayObjectEditor<?> createEditorForm() {
		return new DisplayObjectEditor<DisplayObject>(this);
	}
	
	/**
	 * 得到指定尺寸的快照
	 * @param width
	 * @param height
	 * @return
	 */
	public BufferedImage getSnapshot(int width, int height)
	{
		return null;
	}
	
	/**
	 * 得到快照
	 * @return
	 */
	public BufferedImage getSnapshot()
	{
		return this.getSnapshot(getWidth(), getHeight());
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------
//	local position with parent

	/**
	 * 是否为debug模式，显示一些debug信息
	 * @param d
	 */
	public void setDebug(boolean d) {
		debug = d;
	}
	
	/**
	 * 得到位于父节点的坐标
	 * @return
	 */
	public int getX() {
		return (int)x;
	}
	
	/**
	 * 得到位于父节点的坐标
	 * @return
	 */
	public int getY() {
		return (int)y;
	}
	
	/**
	 * 得到矩形尺寸
	 * @return
	 */
	public int getWidth() {
		return local_bounds.width;
	}
	
	/**
	 * 得到矩形尺寸
	 * @return
	 */
	public int getHeight() {
		return local_bounds.height;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 设置位于父节点的坐标
	 * @return
	 */
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 设置到父节点的中心
	 */
	public void setCenter(){
		if (parent!=null){
			this.x = parent.getWidth()-this.getWidth()>>1;
			this.y = parent.getHeight()-this.getHeight()>>1;
		}
	}
	
	/**
	 * 设置矩形尺寸
	 * @return
	 */
	public void setSize(int w, int h) {
		local_bounds.width = w;
		local_bounds.height = h;
	}

	public void setSize(Dimension dimension) {
		setSize(dimension.width, dimension.height);
	}
	
	/**
	 * 设置基于自己坐标系的 bounds
	 * @return
	 */
	public void setLocalBounds(int x, int y, int w, int h) {
		local_bounds.x = x;
		local_bounds.y = y;
		local_bounds.width = w;
		local_bounds.height = h;
	}
	
//	--------------------------------------------------------------------------------------------------------------------------------------------
//	screen position with root
	
	public Rectangle getScreenBounds(){
		return screen_rectangle;
	}
	
	public Rectangle getScreenBounds(DisplayObject targetCoordinateSpace){
		return screen_rectangle.intersection(targetCoordinateSpace.screen_rectangle);
	}
	
	public int getScreenX(){
		return screen_x;
	}
	
	public int getScreenY(){
		return screen_y;
	}
	
	public int screenToLocalX(int x) {
		return x - screen_x;
	}

	public int screenToLocalY(int y) {
		return y - screen_y;
	}

	public int localToScreenX(int x) {
		return screen_x + x;
	}

	public int localToScreenY(int y) {
		return screen_y + y;
	}
	
//	-------------------------------------------------------------

	/**表示鼠标是否在local_bounds内*/
	public boolean isHitMouse() {
		return hit_mouse;
	}
	
	public boolean hitTestScreenObject(DisplayObject obj) {
		return screen_rectangle.intersects(obj.screen_rectangle);
	}

	public boolean hitTestScreenPoint(int x, int y) {
		return screen_rectangle.contains(x, y);
	}

	/**鼠标在当前对象的相对坐标*/
	public int getMouseX() {
		return mouse_x;
	}
	
	/**鼠标在当前对象的相对坐标*/
	public int getMouseY() {
		return mouse_y;
	}

	/**
	 * 表示该控件是否得到鼠标<br>
	 * 最先得到鼠标的控件将获得鼠标输入,键盘输入的焦点<br>
	 * 默认情况下是鼠标在local_bounds内,并且鼠标在graphics的clip内<br>
	 * 可以覆盖 {@link testCatchMouse(Graphics2D g)}方法来确定是否能获取鼠标
	 */
	public boolean isCatchedMouse() {
		return catched_mouse;
	}

	/**
	 * 提供是否能被鼠标获取的能力，一般情况，该方法将返回isCatchedMouse()的快照。
	 * @param g
	 * @return
	 */
	protected boolean testCatchMouse(Graphics2D g) {
		if (isHitMouse() && g.hitClip(mouse_x, mouse_y, 1, 1)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 是否是当前唯一获得鼠标的最高层对象
	 * @return
	 */
	public boolean isPickedMouse() {
		if (root!=null) {
			return root.getStage().getMousePickedObject() == this;
		}
		return false;
	}
	
	
	
//	-------------------------------------------------------------

	

	void refreshScreen(DisplayObjectContainer parent) 
	{
		this.screen_x = parent.screen_x + (int)this.x;
		this.screen_y = parent.screen_y + (int)this.y;
		this.screen_rectangle.x 		= this.screen_x + this.local_bounds.x;
		this.screen_rectangle.y 		= this.screen_y + this.local_bounds.y;
		this.screen_rectangle.width		= this.local_bounds.width;
		this.screen_rectangle.height	= this.local_bounds.height;
		
		this.mouse_x = screenToLocalX(root.getMouseX());
		this.mouse_y = screenToLocalY(root.getMouseY());
		this.hit_mouse = (screen_rectangle.contains(root.getMouseX(), root.getMouseY()));
	}
	
	
	/**
	 * 递归方法
	 * @param event
	 * @return 如果该对象处理了消息,则返回true
	 */
	boolean onPoolEvent(com.g2d.display.event.Event<?> event) {
		// 直接跳过该消息
		return false;
	}
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onAdded(DisplayObjectContainer parent) {
		this.added(parent);
	}
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onRemoved(DisplayObjectContainer parent) {
		this.removed(parent);
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 递归方法
	 * @param parent
	 */
	void onUpdate(DisplayObjectContainer parent) 
	{
		this.parent 			= parent;
		this.root 				= parent.root;
		this.timer ++;
		this.interval_ms 		= (int)(System.currentTimeMillis() - last_update_time);
		this.last_update_time 	= System.currentTimeMillis();
		this.refreshScreen(parent);

		updateBefore(parent);
		
		this.update();
		
		updateAfter(parent);
	}
	
	void updateBefore(DisplayObjectContainer parent) {}
	void updateAfter(DisplayObjectContainer parent) {}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 递归方法
	 * @param g
	 */
	void onRender(Graphics2D g) 
	{
		if (visible) 
		{
			Shape			clip		= g.getClip();
			AffineTransform	transfrom	= g.getTransform();
			Composite		composite	= g.getComposite();
			try {
				g.translate(x, y);
				if (clip_local_bounds) {
					g.clip(local_bounds);
				}
				if (testCatchMouse(g)) {
					catched_mouse = true;
					getStage().setMousePickedObject(this);
				} else {
					catched_mouse = false;
				}
				this.renderBefore(g);
				this.render(g);
				this.renderDebug(g);
				this.renderInteractive(g);
				this.renderAfter(g);
			} finally {
				g.setComposite(composite);
				g.setTransform(transfrom);
				g.setClip(clip);
			}
		}
	}
	

	protected void renderBefore(Graphics2D g){}
	
	protected void renderAfter(Graphics2D g) {}
	
	void renderInteractive(Graphics2D g){}
	
	protected void renderDebug(Graphics2D g)
	{
		if (debug) {
			g.setColor(Color.GREEN);
			g.drawRect(local_bounds.x, local_bounds.y, local_bounds.width - 1, local_bounds.height - 1);
			g.setColor(Color.CYAN);
			g.drawLine(-10, 0, 0 + 10, 0);
			g.drawLine(0, 0 - 10, 0, 0 + 10);
			g.setColor(Color.YELLOW);
			g.drawLine(-10, priority, 0 + 10, priority);
			g.drawLine(0, priority - 10, 0, priority + 10);
		}
	}
	
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 在该对象被添加到场景中后发生</br>
	 * 当父控件被添加到爷爷控件时，该方法也会被调用，此时parent等于爷爷<br>
	 * 注意:不要在此处初始化持久性数据
	 * @param parent
	 */
	abstract public void added(DisplayObjectContainer parent);
	
	/**
	 * 在该对象从父控件中移除后发生</br>
	 * 当父控件从爷爷控件移除时，该方法也会被调用，此时parent等于爷爷<br>
	 * 注意:不要在此处初始化持久性数据
	 * @param parent
	 */
	abstract public void removed(DisplayObjectContainer parent);
	
	/**
	 * 主更新
	 */
	abstract public void update();
	
	/**
	 * 主渲染
	 * @param g
	 */
	abstract public void render(Graphics2D g);
	
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	public boolean isInParentBounds(DisplayObjectContainer parent)
	{
		return CMath.intersectRect2(
				parent.local_bounds.x,
				parent.local_bounds.y,
				parent.local_bounds.width,
				parent.local_bounds.height,
				x + local_bounds.x, 
				y + local_bounds.y, 
				local_bounds.width, 
				local_bounds.height);
	}
	
	
	public void setAlpha(Graphics2D g, float alpha)
	{
		if (g.getComposite() instanceof AlphaComposite) {
			AlphaComposite ac = (AlphaComposite)g.getComposite();
			g.setComposite(ac.derive(alpha));
		} else {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
	}


	public <T> void pushObject(T value){
		display_stack.push(value);
	}
	
	public <T> T popObject(Class<T> type){
		return type.cast(display_stack.pop());
	}
	
//	---------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 测试同一父节点的2个单位是否碰撞
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean touch(DisplayObject src, DisplayObject dst)
	{
		return CMath.intersectRect2(
				src.x+src.local_bounds.x, 
				src.y+src.local_bounds.y, 
				src.local_bounds.width, 
				src.local_bounds.height, 
				
				dst.x+dst.local_bounds.x, 
				dst.y+dst.local_bounds.y, 
				dst.local_bounds.width, 
				dst.local_bounds.height
				);
	}
	
	/**
	 * 测试同一父节点的2个单位是否碰撞
	 * @param src 包含者
	 * @param dst 被包含着
	 * @return
	 */
	public static boolean include(DisplayObject src, DisplayObject dst)
	{
		return CMath.includeRectPoint2(
				src.x+src.local_bounds.x, 
				src.y+src.local_bounds.y, 
				src.local_bounds.width, 
				src.local_bounds.height, 
				dst.x,
				dst.y
				);
	}
}
