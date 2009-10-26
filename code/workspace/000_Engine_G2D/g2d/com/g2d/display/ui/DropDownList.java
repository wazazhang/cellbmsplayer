package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.MouseWheelEvent;

public abstract class DropDownList extends UIComponent 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public class DropDownListContainer extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public DropDownListContainer()
		{
			this.enable_input = false;
		}
		
		public void update() 
		{
			int y = layout.BorderSize;
			for (UIComponent item : getComonents()) {
				item.setLocation(0, y);
				item.setSize(pan.getWidth(), 20);
				y += item.getHeight() + layout.BorderSize;
			}
			local_bounds.height = y;
		}
	}
	
	protected DropDownListContainer 	container;
	protected Pan 						pan;
	ScrollBar 							scroll;
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		
		container 			= new DropDownListContainer();
		pan  				= new Pan();
		scroll				= ScrollBar.createVScroll(8);
		
		pan.addChild(container);
		super.addChild(pan);
		super.addChild(scroll);
		
		enable_mouse_wheel	= true;
	}

	
	public DropDownList() {}
	
	public DropDownList(int w, int h)
	{
		setSize(w, h);
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		pan.setSize(
				getWidth()-scroll.getWidth()-layout.BorderSize*2, 
				getHeight()-layout.BorderSize*2);
		pan.setLocation(layout.BorderSize, layout.BorderSize);
		
		container.setSize(
				pan.getWidth(), 
				container.getHeight());

		scroll.setLocation(getWidth()-scroll.getWidth()-layout.BorderSize, layout.BorderSize);
		scroll.setSize(scroll.size, getHeight()-layout.BorderSize*2);
		
		scroll.setMax(container.getHeight());
		scroll.setValue(scroll.getValue(), pan.getHeight());
		
		container.setLocation(0, -(int)scroll.getValue());
	
	}
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		scroll.moveInterval(event.scrollDirection);
	}
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	public DropDownListContainer getContainer() {
		return container;
	}
	

}
