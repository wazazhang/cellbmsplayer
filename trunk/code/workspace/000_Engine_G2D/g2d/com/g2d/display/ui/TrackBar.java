package com.g2d.display.ui;

import java.awt.Graphics2D;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObjectContainer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;

public class TrackBar extends Container 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public Button 	btn_add;
	public Button 	btn_dec;
	public Button 	btn_strip;
	
	@Property("最小值")
	double 			min = -10;
	@Property("最大值")
	double 			max = 10;
	@Property("当前值")
	double 			value = 0;
	
	@Override
	protected void init_field() {
		super.init_field();
		min = -10;
		max = 10;
		value = 0;
		
		btn_add 	= new Button("+"){
			private static final long serialVersionUID = Version.VersionG2D;
			protected void onMouseClick(MouseEvent event) {
				setValue(value+1);
			}
		};
		btn_dec 	= new Button("-"){
			private static final long serialVersionUID = Version.VersionG2D;
			protected void onMouseClick(MouseEvent event) {
				setValue(value-1);
			}
		};
		btn_strip 	= new Button(" "){
			private static final long serialVersionUID = Version.VersionG2D;
			public void added(DisplayObjectContainer parent) {
				super.added(parent);
				enable_drag = true;
			}
			protected void onMouseDraged(MouseMoveEvent event) {
				setStripPos((int)x);
			}
		};
		super.addChild(btn_add);
		super.addChild(btn_dec);
		super.addChild(btn_strip);
	}
	
	public TrackBar() 
	{
	}
	
	synchronized public void addChild(UIComponent child) {
		Tools.printError("can not add a custom child component in " + getClass().getName() + " !");
	}
	synchronized public void removeChild(UIComponent child) {
		Tools.printError("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	private void setStripPos(int pos){
		double sx = pos - btn_add.getWidth();
		double si = getWidth() - (btn_add.getWidth() + btn_dec.getWidth()) - btn_strip.getWidth();
		double sw = max - min;
		setValue(min + sw * sx / si);
	}
	
	public double getMax() {
		return max;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setRange(double max, double min) {
		this.max = Math.max(max, min);
		this.min = Math.min(max, min);
		this.value = Math.max(value, min);
		this.value = Math.min(value, max);
	}
	
	public void setValue(double value) {
		value = Math.max(value, min);
		value = Math.min(value, max);
		this.value = value;
	}
	
	public int getPercent() {
		double sw = max - min;
		double sx = value - min;
		return (int)(sx / sw * 100);
	}
	
	protected void onMouseDown(MouseEvent event) {
		if (mouse_x < btn_strip.x) {
			setStripPos((int)btn_strip.x - btn_strip.getWidth());
		}else{
			setStripPos((int)btn_strip.x + btn_strip.getWidth());
		}
	}
	
	public void render(Graphics2D g)
	{
		super.render(g);
		
		btn_add.setSize(btn_add.getWidth(), getHeight());
		btn_dec.setSize(btn_dec.getWidth(), getHeight());
		btn_strip.setSize(btn_strip.getWidth(), getHeight());
		
		double sw = max - min;
		double sv = value - min;
		
		int sx = btn_dec.getWidth();
		int si = getWidth() - (btn_add.getWidth() + btn_dec.getWidth()) - btn_strip.getWidth();
		si = (int)(si * sv / sw);
		
		btn_add.setLocation(
				getWidth()-btn_add.getWidth(), 
				0);
		btn_dec.setLocation(
				0, 
				0);
		btn_strip.setLocation(
				sx+si, 
				0);
		
	}
}
