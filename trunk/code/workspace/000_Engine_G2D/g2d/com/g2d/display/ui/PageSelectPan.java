package com.g2d.display.ui;

public class PageSelectPan extends PageSelect<com.g2d.display.ui.PageSelectPan.Page>
{
	public PageSelectPan() {
		super();
	}

	@Override
	protected Page createPage(String text) {
		return new Page(text);
	}


	public static class Page extends com.g2d.display.ui.PageSelect.Page
	{
		Pan pan = new Pan();

		public Page(String headText) {
			super(headText);
		}
		@Override
		protected UIComponent getPageView() {
			return pan;
		}
	}
	
	
}
