package com.g2d.display.ui;

import com.g2d.Version;
import com.g2d.display.event.MouseWheelEvent;

public class Panel extends Container
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	ScrollBar.ScrollBarPair		scrollbar;
	
	/** 实际的容器 */
	protected PanelContainer	container;
	/** 视口 */
	protected Pan 				pan;
	
	
	@Override
	protected void init_field()
	{
		super.init_field();
		
		enable_mouse_wheel = true;
		
		scrollbar 	= new ScrollBar.ScrollBarPair();
		pan 		= new Pan();
		container 	= new PanelContainer();
		
		pan.addChild(container);
		super.addChild(scrollbar);
		super.addChild(pan);
	}
	
	
	public Panel() 
	{
	}
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		scrollbar.vScroll.moveInterval(event.scrollDirection);
	}

	

	public int getPanelChildCount() {
		return container.getChildCount();
	}
	
	public synchronized boolean addChild(UIComponent child) {
		return container.addChild(child);
	}
	
	public synchronized boolean removeChild(UIComponent child) {
		return container.removeChild(child);
	}
	
	public ScrollBar getVScrollBar() {
		return scrollbar.vScroll;
	}
	public ScrollBar getHScrollBar() {
		return scrollbar.hScroll;
	}
	
	public int getViewPortWidth() {
		return pan.getWidth();
	}
	
	public int getViewPortHeight() {
		return pan.getHeight();
	}
	
	public void removeScrollBar() {
		super.removeChild(scrollbar);
		scrollbar = null;
		enable_mouse_wheel = false;
	}
	
	public void update() 
	{
		super.update();
		
		if (scrollbar!=null){
			pan.setSize(
					getWidth()-scrollbar.vScroll.getWidth()-(layout.BorderSize<<1), 
					getHeight()-scrollbar.hScroll.getHeight()-(layout.BorderSize<<1));
			
			scrollbar.vScroll.setMax(container.local_bounds.height);
			scrollbar.vScroll.setValue(scrollbar.vScroll.getValue(), pan.getHeight());
			
			scrollbar.hScroll.setMax(container.local_bounds.width);
			scrollbar.hScroll.setValue(scrollbar.hScroll.getValue(), pan.getWidth());
			
			int tx = -(int)scrollbar.hScroll.getValue();
			int ty = -(int)scrollbar.vScroll.getValue();
			container.setLocation(tx, ty);
		}else{
			pan.setSize(
					getWidth()-(layout.BorderSize<<1), 
					getHeight()-(layout.BorderSize<<1));
		}
		pan.setLocation(layout.BorderSize, layout.BorderSize);

		
//		if (scrollbar.hScroll.getValueLength() == scrollbar.hScroll.getMax()) {
//			scrollbar.hScroll.visible = false;
//		}else{
//			scrollbar.hScroll.visible = true;
//		}
//		
//		if (scrollbar.vScroll.getValueLength() == scrollbar.vScroll.getMax()) {
//			scrollbar.vScroll.visible = false;
//		}else{
//			scrollbar.vScroll.visible = true;
//		}
	}
	
	
	public class PanelContainer extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public PanelContainer() 
		{
			this.enable_input = false;
		}
		
		public void update() 
		{
			local_bounds.width = pan.getWidth();
			local_bounds.height = pan.getHeight();
			for (UIComponent item : comonents) {	
				local_bounds.width  = (int)Math.max(local_bounds.width,  item.x + item.getWidth());
				local_bounds.height = (int)Math.max(local_bounds.height, item.y + item.getHeight());
			}
		}
		
	}
	
}
