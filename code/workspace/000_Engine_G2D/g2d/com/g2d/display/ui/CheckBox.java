package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.AnimateCursor;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.CheckBox.CheckBoxGroup.Mode;
import com.g2d.display.ui.ComboBox.ComboxListener;
import com.g2d.util.Drawing;

public class CheckBox extends UIComponent 
{
	private static final long serialVersionUID = Version.VersionG2D;
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	
	
	transient public Image	check_image;
	transient public Image	uncheck_image;
	
	@Property("文字颜色")
	public Color			textColor;
	
	@Property("text")
	public String			text;
	
	@Property("text_anchor")
	public int 				text_anchor;
	
	boolean 				is_checked;
	
	public boolean			is_check_right = false;

	CheckBoxGroup			group;
	
	transient Vector<CheckChangeListener> check_change_listeners;
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	protected void init_field()
	{
		super.init_field();
		textColor 	= Color.WHITE;
		text 		= "";
		text_anchor = Drawing.TEXT_ANCHOR_LEFT | Drawing.TEXT_ANCHOR_VCENTER ;
		is_checked 	= false;
	}
	
	@Override
	protected void init_transient() 
	{
		super.init_transient();
		
		check_change_listeners = new Vector<CheckChangeListener>();
		
		{
			check_image = Tools.createImage(10, 10);
			Graphics2D g2d = (Graphics2D)check_image.getGraphics();
			g2d.setColor(Color.GREEN);
			g2d.fillRect(0, 0, 10, 10);
		}
		{
			uncheck_image = Tools.createImage(10, 10);
			Graphics2D g2d = (Graphics2D)check_image.getGraphics();
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, 10, 10);
		}
	}
	
	public CheckBox(String text) 
	{
		this.text = text;
	}
	
	public CheckBox(String text, int w, int h)
	{
		this.text = text;
		this.setSize(w, h);
	}
	
	public CheckBox() {
		this("CheckBox");
	}
	
	@Override
	public AnimateCursor getCursor() {
		return AnimateCursor.HAND_CURSOR;
	}
	
	public boolean isChecked() {
		return is_checked;
	}
	
	public void setChecked(boolean checked)
	{
		if (is_checked != checked)
		{
			if (group!=null) 
			{
				switch(group.mode)
				{
				case SINGLE_SELECT:
				case EXCLUDE_SELECT:
					if (checked) {
						is_checked = checked;
						onCheckChanged();
						for (CheckBox box : group) {
							if (box != this) {
								box.setChecked(false);
							}
						}
					}else{
						if (group.mode == Mode.SINGLE_SELECT) {
							is_checked = checked;
							if (group.getCheckedCount()>0) {
								onCheckChanged();
							}else{
								is_checked = true;
							}
						}else {
							is_checked = checked;
							onCheckChanged();
						}
					}
					break;
				}
			}
			else
			{
				is_checked = checked;
				onCheckChanged();
			}
		}
	}

	
	/**
	 * 设置排他性选择集合
	 * @param group
	 */
	public void setGroup(CheckBoxGroup group)
	{
		this.group = group;
		
		int modify = 0;
		
		switch(group.mode)
		{
		case EXCLUDE_SELECT:
			for (CheckBox box : group) {
				if (modify>1) {
					box.setChecked(false);
				}
				if (box.is_checked) {
					modify ++;
				}
			}
			break;
		case SINGLE_SELECT:
			for (CheckBox box : group) {
				if (modify>1) {
					box.setChecked(false);
				}
				if (box.is_checked) {
					modify ++;
				}
			}
			if (group.size()>0 && modify==0) {
				group.iterator().next().setChecked(true);
			}
			break;
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
//	events
	
	protected void onMouseClick(MouseEvent event)
	{
		setChecked(!is_checked);
		super.onMouseClick(event);
	}
	
	protected void onCheckChanged() {
		for (CheckChangeListener listener : check_change_listeners) {
			listener.checkChanged(this, is_checked);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof CheckChangeListener) {
			check_change_listeners.add((CheckChangeListener)listener);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof CheckChangeListener) {
			check_change_listeners.remove((CheckChangeListener)listener);
		}
	}
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		int sw = layout.BorderSize*2;
		if (!is_check_right){
			if (is_checked)
			{
				g.drawImage(check_image, sw, (getHeight()-check_image.getHeight(null))>>1, null);
				sw += check_image.getWidth(null) + layout.BorderSize;
			}
			else
			{
				g.drawImage(uncheck_image, sw, (getHeight()-uncheck_image.getHeight(null))>>1, this);
				sw += uncheck_image.getWidth(null) + layout.BorderSize;
			}
			
			g.setColor(textColor);
			Drawing.drawStringBorder(g, text, sw, 0, getWidth()-sw, getHeight(), text_anchor);
		}else{
			sw = check_image.getWidth(null) + layout.BorderSize*2;
			g.setColor(textColor);
			Drawing.drawStringBorder(g, text, layout.BorderSize, 0, getWidth()-sw, getHeight(), text_anchor);
			
			if (is_checked)
			{
				g.drawImage(check_image, getWidth()-sw + layout.BorderSize, (getHeight()-check_image.getHeight(null))>>1, null);
			}
			else
			{
				g.drawImage(uncheck_image, getWidth()-sw + layout.BorderSize, (getHeight()-uncheck_image.getHeight(null))>>1, this);
			}
			
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------

	static public interface CheckChangeListener extends EventListener
	{
		public void checkChanged(CheckBox checkbox, boolean check);
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------

	
	
	/**
	 * @author WAZA<br>
	 * 用于排他性选择的集合
	 */
	static public class CheckBoxGroup extends HashSet<CheckBox>
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		static public enum Mode
		{
			/** 设置互斥对象,所有互斥对象不能同时被钩选 */
			EXCLUDE_SELECT(),
			/** 设置互斥对象,所有互斥对象不能同时被钩选 */
			SINGLE_SELECT(),
			;
		}
		
		final Mode mode;
		
		public CheckBoxGroup(Mode mode){
			this.mode = mode;
		}
		
		public void addAll(CheckBox ... boxes) {
			for (CheckBox box : boxes) {
				add(box);
			}
		}
		
		@Override
		public boolean add(CheckBox e) {
			return super.add(e);
		}
		
		public int getCheckedCount() {
			int count = 0;
			for (CheckBox box : this) {
				if (box.is_checked) {
					count ++;
				}
			}
			return count;
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
}
