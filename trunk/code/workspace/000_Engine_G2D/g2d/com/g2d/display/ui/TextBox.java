package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Hashtable;

import com.cell.CMath;
import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.AnimateCursor;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.ui.text.MultiTextLayout;
import com.g2d.display.ui.text.TextBuilder;
import com.g2d.display.ui.text.MultiTextLayout.AttributedSegment;



public class TextBox extends Container implements Serializable
{
	private static final long serialVersionUID = Version.VersionG2D;

	public static int SCROLL_BAR_SIZE = 12;
	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text;
	
	@Property("文字颜色")
	public Color 						textColor;
	public boolean 						is_readonly;
	public boolean						is_show_link;
	protected ScrollBar					v_scrollbar;
	
	transient AnimateCursor				link_cursor;
	transient Hashtable<Attribute, ClickSegmentListener> click_segment_listeners;
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		
		textColor			= new Color(0xffffffff, true);
		is_readonly			= false;
		text				= new MultiTextLayout();
		
		enable_key_input	= true;
		enable_mouse_wheel	= true;
		
		v_scrollbar			= ScrollBar.createVScroll(SCROLL_BAR_SIZE);
		
		super.addChild(v_scrollbar);
		
		setCursor(AnimateCursor.TEXT_CURSOR);
		link_cursor	= AnimateCursor.HAND_CURSOR;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException 
	{
		out.writeBoolean(is_readonly);
		out.writeObject(textColor);
		out.writeObject(v_scrollbar);
		out.writeUTF(getText());
	}   
	  
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		is_readonly	= in.readBoolean();
		textColor	= (Color)in.readObject();
		v_scrollbar	= (ScrollBar)in.readObject();
		String text	= (String)in.readUTF();
		{
			this.text = new MultiTextLayout();
			this.setText(text);
		}
	}  
	
	
	public TextBox() 
	{
		this("");
	}
	
	public TextBox(String text)
	{
		this.text.setText(text);
	}
	
	public TextBox(String text, int w, int h)
	{
		this.text.setText(text);
		this.setSize(w, h);
	}
	
	synchronized public void addChild(UIComponent child) {
		Tools.printError("can not add a custom child component in " + getClass().getName() + " !");
	}
	synchronized public void removeChild(UIComponent child) {
		Tools.printError("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public void setText(String text, boolean buildScript) {
		if (buildScript) {
			this.text.setText(TextBuilder.buildScript(text));
		} else {
			setText(text);
		}
	}
	
	public void setText(AttributedString atext) {
		this.text.setText(atext);
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
	}
	
	public void appendText(AttributedString atext) {
		this.text.appendText(atext);
	}

	public void appendLine(String text) {
		this.text.appendText(text+"\n");
	}
	
	public void appendLine(AttributedString atext) {
		this.text.appendText(Tools.linkAttributedString(atext, new AttributedString("\n")));
	}
	
	protected void onMouseDown(MouseEvent event) {
//		System.out.println("TextBox onMouseDown");
		text.setCaret(getMouseX()-text_draw_x, getMouseY()-text_draw_y);
//		text.getSelectedSegment(AttributedCharacterIterator.Attribute.INPUT_METHOD_SEGMENT);
		if (click_segment_listeners != null) {
			int position = text.pointToPosition(getMouseX() - text_draw_x, getMouseY() - text_draw_y);
			for (Attribute attribute : click_segment_listeners.keySet()) {
				ClickSegmentListener listener = click_segment_listeners.get(attribute);
				if (listener != null) {
					AttributedSegment segment = text.getSegment(position, attribute);
					if (segment != null) {
						listener.segmentClicked(event, this, segment);
					}
				}
			}
		}
	}
	
	protected void onMouseDraged(MouseMoveEvent event) {
		//System.out.println("TextBox onMouseDraged");
		text.dragCaret(getMouseX()-text_draw_x, getMouseY()-text_draw_y);
	}
	
	protected void onMouseWheelMoved(MouseWheelEvent event) {
		//System.out.println(" mouseWheelMoved");
		v_scrollbar.moveInterval(event.scrollDirection);
	}
	
	protected void onKeyTyped(KeyEvent event) {
		if (!is_readonly || event.keyChar == MultiTextLayout.CHAR_COPY) {
			text.insertChar(event.keyChar);
		}
	}
	
	@Override
	protected void trySetCursor() {
		if (CMath.includeRectPoint(
				layout.BorderSize, 
				layout.BorderSize, 
				getWidth()-(layout.BorderSize<<1), 
				getHeight()-(layout.BorderSize<<1), 
				mouse_x, mouse_y)) {
			super.trySetCursor();
		}
	}
	
	public ScrollBar getVScrollBar() {
		return v_scrollbar;
	}
	
	public void update() 
	{
		super.update();
		
		if (is_readonly){
			text.is_show_caret = false;
		}
		else if (getRoot()!=null && !getRoot().isFocusOwner()) {
			text.is_show_caret = false;
		}
		else if (!isFocusedComponent()) {
			text.is_show_caret = false;
		}
		else {
			text.is_show_caret = true;
		}
		
		v_scrollbar.setLocation(
				getWidth()-v_scrollbar.size-layout.BorderSize, 
				layout.BorderSize);
		v_scrollbar.setSize(v_scrollbar.size, getHeight()-(layout.BorderSize<<1));
		
		v_scrollbar.setMax(text.getHeight());
		v_scrollbar.setValue(v_scrollbar.getValue(), getHeight()-(layout.BorderSize<<1));
		
		text.setWidth((getWidth()-v_scrollbar.size-(layout.BorderSize<<1)));
		
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize - (int)v_scrollbar.getValue();
	}
	
	
	public void render(Graphics2D g) 
	{
		AnimateCursor oldcursor = getCursor();
		if (is_show_link) {
			AttributedSegment segment = text.getSegment(
					text.pointToPosition(mouse_x-text_draw_x, mouse_y-text_draw_y), 
					com.g2d.display.ui.text.TextAttribute.LINK);
			if (segment!=null) {
				setCursor(link_cursor);
			}
		}
		
		super.render(g);
		
		setCursor(oldcursor);
		
		int tsx = 0;
		int tsy = (int)v_scrollbar.getValue();
		int tsw = text.getWidth();
		int tsh = (int)v_scrollbar.getValueLength();
		
		g.setColor(textColor);
		text.drawText(g, text_draw_x, text_draw_y, tsx, tsy, tsw, tsh);
	}

	public void addClickSegmentListener(Attribute attribute, ClickSegmentListener listener)
	{
		if (attribute != null) {
			if (click_segment_listeners == null) {
				click_segment_listeners = new Hashtable<Attribute, ClickSegmentListener>();
			}
			click_segment_listeners.put(attribute, listener);
		}
	}
	
	public void addClickSegmentListener(String instruction, ClickSegmentListener listener)
	{
		addClickSegmentListener(TextBuilder.Instructions.get(instruction), listener);
	}
	
	/**
	 * 用于监听特殊属性的字符段被点击的接口
	 * @author WAZA
	 */
	public static interface ClickSegmentListener extends EventListener
	{
		public void segmentClicked(MouseEvent event, TextBox textbox, AttributedSegment segment);
	}
	
}
