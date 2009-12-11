package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Vector;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.DisplayObject;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.MouseEvent;
import com.g2d.editor.UIComponentEditor;
import com.g2d.util.Drawing;

public class ListView extends UIComponent 
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	public static enum ViewMode
	{
		LIST,
		BIG_ICON,
		SMALL_ICON,
		DETAIL,
		;
	}
	
	
	public static interface ListViewListener<DataType> extends EventListener
	{
		public void itemSelected(ListView sender, Item<DataType> item);
	}
	
	public static abstract class Item<DataType> extends Container
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public DataType data;
		
		ListView list_view;
		
		protected void onMouseClick(MouseEvent event) {
			super.onMouseClick(event);
			if (list_view!=null) {
				list_view.selectItem(this);
			}
		}

		protected void onDrawMouseHover(Graphics2D g) {
			g.setColor(new Color(1,1,1,0.2f));
			g.fill(local_bounds);
		}
	}
	
	public static class TextItem<DataType> extends Item<DataType>
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		@Property("文字颜色")
		public Color textColor = Color.WHITE;
		
		@Property("文字")
		public String text;
		
		public String[] texts;
		
		
		public TextItem(String text) {
			this.text = text;
		}
		public TextItem(String text, DataType data) {
			this.text = text;
			this.data = data;
		}
		
		public TextItem(String[] texts) {
			this.texts = texts;
			this.text = texts[0];
		}
		public TextItem(String[] texts, DataType data) {
			this.texts = texts;
			this.text = texts[0];
			this.data = data;
		}
		
		public String toString() {
			return "Item["+text+"]["+data+"]";
		}
	
		public void render(Graphics2D g) 
		{
			super.render(g);
			
			g.setColor(textColor);
			Drawing.drawString(g, text, 
					layout.BorderSize, 
					layout.BorderSize, 
					getWidth() - layout.BorderSize*2,
					getHeight() - layout.BorderSize*2, 
					Drawing.TEXT_ANCHOR_LEFT | Drawing.TEXT_ANCHOR_VCENTER
			);
		}
		
	}
	
	
//	----------------------------------------------------------------------------------------------------------

	public class ListViewPanel extends Panel
	{
		private static final long serialVersionUID = Version.VersionG2D;
		
		public void addChild(Item<?> child) {
			super.getContainer().addChild(child);
		}
		public void removeChild(Item<?> child) {
			super.getContainer().removeChild(child);
		}
		
		@Override
		public UIComponentEditor<?> createEditorForm() {
			return ListView.this.createEditorForm();
		}
		
		void resetViewMode()
		{
			switch (view_mode)
			{
			case LIST: 
			case DETAIL: {
				int h = 0;
				for (UIComponent item : container.getComonents()) {
					item.setLocation(0, h);
					item.setSize(view_port.getWidth(), 20);
					h += 20;
				}
				break;
			}
			
			case BIG_ICON: 
			case SMALL_ICON: {
				int h = 0;
				int w = 0;
				for (UIComponent item : container.getComonents()) {
					item.setLocation(w, h);
					item.setSize(40, 40);
					w += 40;
					if (w >= view_port.getWidth()) {
						h += 40;
						w = 0;
					}
				}
				break;
			}
			
			}
		}
		
	}
	
	
	ViewMode 					view_mode;
	ViewMode 					next_view_mode;
	
	ListViewPanel 				panel;
	
	Item<?> 					selected_item;
	
	transient Vector<ListViewListener<?>>	listview_listeners;
	
//	----------------------------------------------------------------------------------------------------------

	@Override
	protected void init_field() 
	{
		super.init_field();
		
		view_mode 			= ViewMode.BIG_ICON;
		next_view_mode		= ViewMode.BIG_ICON;
		
		panel 				= new ListViewPanel();
		enable_input 		= false;
		
		super.addChild(panel);
	}
	
	@Override
	protected void init_transient() {

		super.init_transient();

		listview_listeners	= new Vector<ListViewListener<?>>();
	}
	
	public ListView()
	{
		int count = Tools.getRandomUInt() % 100;
		String[] texts = new String[count];
		for (int i=0; i<count; i++) {
			texts[i] = "item " + i;
		}
		addItems(texts);
	}
	
	public ListView(String items[]){
		addItems(items);
	}
	
	public ListView(Item<?> items[]){
		addItems(items);
	}

	public ListView(Collection<? extends Item<?>> items){
		addItems(items);
	}
	
	public void setViewMode(ViewMode mode) 
	{
		this.next_view_mode = mode;
	}
	
	
//	----------------------------------------------------------------------------------------------------------
//	items
	
	public void selectItem(Item<?> item) {
		selected_item = item;
		onItemSelected(selected_item);
	}
	
	public Item<?> getSelectedItem() {
		return selected_item;
	}
	
	public int getItemCount() {
		return panel.getContainer().getChildCount();
	}
	
	synchronized public void addItem(Item<?> item) {
		panel.addChild(item);
		item.list_view = this;
		if (selected_item == null) {
			selectItem(item);
		}
	}
	
	synchronized public void removeItem(Item<?> item) {
		panel.removeChild(item);
		item.list_view = null;
		item.removeFromParent();
		if (selected_item==item) {
			selected_item = null;
		}
	}
	
	synchronized public void addItem(String item) {
		addItem(new TextItem<Object>(item));
	}
	synchronized public void addItems(String[] items) {
		for (String item : items) {
			addItem(item);
		}
	}	
	synchronized public void addItems(Item<?>[] items) {
		for (Item<?> item : items) {
			addItem(item);
		}
	}
	synchronized public void addItems(Collection<? extends Item<?>> items) {
		for (Item<?> item : items) {
			addItem(item);
		}
	}
	synchronized public void clearItem(Item<?> child) {
		for (UIComponent item : panel.container.getComonents()) {
			removeItem((Item<?>)item);
		}
	}
	

//	----------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	protected void onItemSelected(Item item) {
		for (ListViewListener listener : listview_listeners) {
			listener.itemSelected(this, item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addEventListener(EventListener listener) {
		super.addEventListener(listener);
		if (listener instanceof ListViewListener) {
			listview_listeners.add((ListViewListener)listener);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeEventListener(EventListener listener) {
		super.removeEventListener(listener);
		if (listener instanceof ListViewListener) {
			listview_listeners.remove((ListViewListener)listener);
		}
	}
	
//	----------------------------------------------------------------------------------------------------------

	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	
	public void render(Graphics2D g) {
		super.render(g);
		
		panel.setLocation(0, 0);
		panel.setSize(getWidth(), getHeight());
		
		if (next_view_mode!=null)
		{
			view_mode = next_view_mode;
			panel.resetViewMode();
		}
	}
	
	
	
}
