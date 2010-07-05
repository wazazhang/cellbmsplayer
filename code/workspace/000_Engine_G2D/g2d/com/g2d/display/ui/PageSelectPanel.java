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

public class PageSelectPanel extends PageSelect<com.g2d.display.ui.PageSelectPanel.Page>
{
	public PageSelectPanel() {
		super();
	}

	@Override
	protected Page createPage(String text) {
		return new Page(text);
	}

	public int getViewPortWidth() {
		if (selected_page != null) {
			return selected_page.panel.getViewPortWidth();
		}
		return getWidth();
	}

	public int getViewPortHeight() {
		if (selected_page != null) {
			return selected_page.panel.getViewPortHeight();
		}
		return getHeight() - this.page_header_height;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------


	public static class Page extends com.g2d.display.ui.PageSelect.Page
	{
		Panel panel = new Panel();

		public Page(String headText) {
			super(headText);
		}
		public Panel getPanel() {
			return panel;
		}
		@Override
		protected UIComponent getPageView() {
			return panel;
		}
	}
	
	
}
