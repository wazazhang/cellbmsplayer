package com.g2d.display;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import com.cell.math.MathVector;
import com.cell.math.Vector;
import com.g2d.Version;
import com.g2d.annotation.Property;

public class Sprite extends InteractiveObject implements Vector
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	@Property("缩放比率")
	public double		scale_x, scale_y;
	
	@Property("旋转参数(弧度)")
	public double		rotate;
	
	@Property("透明度(范围0.0~1.0)")
	public float		alpha;
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		
		scale_x			= 1;
		scale_y			= 1;
		rotate			= 0;
		alpha			= 1f;
		
		enable 			= false;
		enable_input	= false;
		enable_focus	= false;
	}
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
	}
	
	public Sprite() {}
	
//	 ------------------------------------------------------------------------------------------------------------------------
//	 math util
	
	public void addVectorX(double dx) {
		this.x += dx;
	}
	public void addVectorY(double dy) {
		this.y += dy;
	}
	public void setVectorX(double x) {
		this.x = x;
	}
	public void setVectorY(double y) {
		this.y = y;
	}
	public double getVectorX() {
		return this.x;
	}
	public double getVectorY() {
		return this.y;
	}

	public void move(double dx, double dy){
		MathVector.move(this, dx, dy);
	}
	
	/**
	 * 通过极坐标来移动
	 * @param angle 弧度
	 * @param distance
	 */
	public void movePolar(double angle, double distance){
		MathVector.movePolar(this, angle, distance);
	}
	
	/**
	 * 向目标移动
	 * @param dx
	 * @param dy
	 * @return 是否到达目标点
	 */
	public boolean moveTo(double x, double y, double distance){
		return MathVector.moveTo(this, x, y, distance);
	}

//	 ------------------------------------------------------------------------------------------------------------------------
	
//	final protected void onUpdate(DisplayObjectContainer parent) {
//		super.onUpdate(parent);
//	}
	
	final protected void onRender(Graphics2D g) {
		super.onRender(g);
	}

	@Override
	final protected void renderBefore(Graphics2D g) {
		super.renderBefore(g);
		if (scale_x != 1 || scale_y != 1)
			g.scale(scale_x, scale_y);
		if (rotate != 0)
			g.rotate(rotate);
		if (alpha < 1f)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}
	
	public void added(DisplayObjectContainer parent) {}
	public void removed(DisplayObjectContainer parent) {}
	public void render(Graphics2D g) {}
	public void update() {}
	
}
