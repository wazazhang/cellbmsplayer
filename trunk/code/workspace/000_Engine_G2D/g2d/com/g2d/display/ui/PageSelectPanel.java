package com.g2d.display.ui;

import java.awt.Graphics2D;
import java.util.Vector;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.ui.layout.UILayout;
import com.g2d.editor.UIComponentEditor;

public class PageSelectPanel extends UIComponent
{
	private static final long serialVersionUID = Version.VersionG2D;


	public static class Page extends Button
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		Panel			panel;
		PageSelectPanel	page_select;
		
		
		@Override
		protected void init_field() {
			super.init_field();
			panel 		= new Panel();
		}
		
		public Page(String headText)
		{
			super(headText);
			setSize(60, 20);
		}
		
		/** call getPanel().getContainer().addChild(DisplayObject child); */
		@Deprecated
		public boolean addChild(DisplayObject child) {
			throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
		}
		/**  call getPanel().getContainer().removeChild(DisplayObject child); */
		@Deprecated
		public boolean removeChild(DisplayObject child) {
			throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
		}
		
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (page_select!=null) {
				page_select.selectPage(this);
			}
		}
		
		public void render(Graphics2D g) {
			UILayout up = custom_layout_up;
			UILayout down = custom_layout_down;
			
			if (page_select.selected_page == this) {
				custom_layout_up = custom_layout_down = (custom_layout_down!=null?custom_layout_down:layout_down);
				super.render(g);
			}else{
				custom_layout_down = custom_layout_up = (custom_layout_up!=null?custom_layout_up:layout_up);
				super.render(g);
			}
			
			custom_layout_up = up;
			custom_layout_down = down;
		}
		
		@Override
		public UIComponentEditor<?> createEditorForm() {
			if (page_select!=null) {
				return page_select.createEditorForm();
			}
			else {
				return super.createEditorForm();
			}
		}
		
		public Panel getPanel(){
			return panel;
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
	
	public int 		page_header_width	= 60;
	
	public int		page_header_height	= 20;
	
	Vector<Page>	pages;
	
	Page			selected_page;
	
//	----------------------------------------------------------------------------------------------------------------------------

	public PageSelectPanel() 
	{
//		PageSelectPanel.Page pan = new PageSelectPanel.Page("page 1");
//		addPage(pan);
	}

	@Override
	protected void init_field()
	{
		super.init_field();
		pages 			= new Vector<Page>();
		enable_input 	= false;
	}

	public int getViewPortWidth() {
		if (selected_page!=null) {
			return selected_page.panel.getViewPortWidth();
		}
		return getWidth();
	}
	
	public int getViewPortHeight() {
		if (selected_page!=null) {
			return selected_page.panel.getViewPortHeight();
		}
		return getHeight() - this.page_header_height;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
//	pages
	
	public void selectPage(int index) {
		selectPage(getPage(index));
	}
	
	public void selectPage(Page page) {
		if (page!=null && page!=selected_page) {
			if (selected_page != null) {
//				selected_page.panel.removeFromParent();
				super.removeChild(selected_page.panel);
			}
			selected_page = page;
			super.addChild(selected_page.panel);
			onPageSelected(selected_page);
		}
	}
	

	public void addPage(Page page) {
		if (page!=null) {
			page.setMinimumSize(page_header_width, page_header_height);
			pages.add(page);
			page.page_select = this;
			super.addChild(page);
			if (selected_page==null) {
				selectPage(page);
			}
		}
	}
	
	
	public void removePage(Page page) {
		if (page!=null){
			if (selected_page==page) {
				super.removeChild(page.panel);
				selected_page = null;
			}
			super.removeChild(page);
			pages.remove(page);
		}
	}
	
	
	public void addPage(String text) {
		addPage(new Page(text));
	}
	public Page getPage(int index) {
		return pages.elementAt(index);
	}
	public void removePage(int index) {
		removePage(getPage(index));
	}
	public void removeAllPage() {
		while (!pages.isEmpty()) {
			removePage(pages.elementAt(0));
		}
	}
	public Page getSelectedPage(){
		return selected_page;
	}
	
	public Vector<Page> getPages(){
		return new Vector<Page>(pages);
	}
	
	protected void onPageSelected(Page page){}
	
	
//	----------------------------------------------------------------------------------------------------------------------------

	@Override
	protected void renderAfter(Graphics2D g) 
	{
		super.renderAfter(g);
		
		if (!pages.isEmpty())
		{
			int h = page_header_height;
			int w = Math.min(getWidth()/pages.size(), page_header_width);
			int x = 0;
			for (Page page : pages) {
				page.setSize(w, h);
				page.setLocation(x, 0);
				page.panel.setSize(getWidth(), getHeight()-h);
				page.panel.setLocation(0, h);
				x += page.getWidth();
			}
		}
	}
	
	
	synchronized public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	synchronized public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	
//	----------------------------------------------------------------------------------------------------------------------------


	
}
