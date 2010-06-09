package com.g2d.display.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
import com.g2d.display.DisplayObject;
import com.g2d.display.Tip;
import com.g2d.display.event.EventListener;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.TextInputer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.ui.text.MultiTextLayout;
import com.g2d.display.ui.text.TextBuilder;
import com.g2d.display.ui.text.MultiTextLayout.AttributedSegment;



public class TextBox extends UIComponent implements Serializable, TextInputer
{
	private static final long serialVersionUID = Version.VersionG2D;

	public static int SCROLL_BAR_SIZE = 12;
	
	@Property("文字颜色")
	public Color 						textColor			= new Color(0xffffffff, true);
	public boolean 						is_readonly			= false;
	public boolean						is_show_link;
	protected ScrollBar					v_scrollbar			= ScrollBar.createVScroll(SCROLL_BAR_SIZE);

	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
	public boolean						enable_antialiasing	 = false;

	public int							text_shadow_x = 0;
	public int							text_shadow_y = 0;

//	-------------------------------------------------------------------------------------------------------------------
	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text				= new MultiTextLayout();

	transient Hashtable<Attribute, ClickSegmentListener> click_segment_listeners;
	
//	-------------------------------------------------------------------------------------------------------------------
	
	public TextBox() 
	{
		this("");
	}
	
	public TextBox(String text)
	{
		this(text, 100, 100);
	}
	
	public TextBox(String text, int w, int h)
	{
		enable_key_input	= true;
		enable_mouse_wheel	= true;
		super.addChild(v_scrollbar);
		
		this.text.setText(text);
		this.setSize(w, h);
	}
	
	@Deprecated
	public boolean addChild(DisplayObject child) {
		throw new IllegalStateException("can not add a custom child component in " + getClass().getName() + " !");
	}
	@Deprecated
	public boolean removeChild(DisplayObject child) {
		throw new IllegalStateException("can not remove a custom child component in " + getClass().getName() + " !");
	}
	
	@Override
	public boolean isInput() {
		return text.is_show_caret;
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
	
	public AttributedSegment getSegment(Attribute attribute, int x, int y) {
		int position = text.pointToPosition(x - text_draw_x, y - text_draw_y);
		AttributedSegment segment = text.getSegment(position, attribute);
		return segment;
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
	public AnimateCursor getCursor() {
		if (CMath.includeRectPoint(
				layout.BorderSize, 
				layout.BorderSize, 
				getWidth()-(layout.BorderSize<<1), 
				getHeight()-(layout.BorderSize<<1), 
				getMouseX(), getMouseY())) {
			if (is_show_link) {
				AttributedSegment segment = text.getSegment(
						text.pointToPosition(getMouseX()-text_draw_x, getMouseY()-text_draw_y), 
						com.g2d.display.ui.text.TextAttribute.LINK);
				if (segment!=null) {
					return AnimateCursor.HAND_CURSOR;
				}
			}
			return AnimateCursor.TEXT_CURSOR;
		}
		return super.getCursor();
	}
	
	
	
	public ScrollBar getVScrollBar() {
		return v_scrollbar;
	}
	
	public void update() 
	{
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
		
		{
			int sw = getWidth() -(layout.BorderSize<<1);
			int sh = getHeight()-(layout.BorderSize<<1);
			
			v_scrollbar.setMax(Math.max(text.getHeight(), sh));
			v_scrollbar.setValue(v_scrollbar.getValue(), sh);
			
			v_scrollbar.setLocation(getWidth()-layout.BorderSize-v_scrollbar.size, layout.BorderSize);
			v_scrollbar.setSize(v_scrollbar.size, sh);
			
			if (v_scrollbar.isMaxLength()) {
				text.setWidth(sw);
			} else {
				text.setWidth(sw-v_scrollbar.size);
			}
			
			text_draw_x = layout.BorderSize;
			text_draw_y = layout.BorderSize - (int)v_scrollbar.getValue();
		}
		
		super.update();
		
	}
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		{
			int tsx = 0;
			int tsy = (int)v_scrollbar.getValue();
			int tsw = text.getWidth();
			int tsh = (int)v_scrollbar.getValueLength();
			
			Object v = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			{
				if (enable_antialiasing) {
					g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				}
				if (text_shadow_x!=0 || text_shadow_y!=0) {
					Composite composite = g.getComposite();
					g.setComposite(AlphaComposite.SrcOut);
					g.setColor(Color.BLACK);
					text.drawText(g, text_draw_x+text_shadow_x, text_draw_y+text_shadow_y, tsx, tsy, tsw, tsh);
					g.setComposite(composite);
				}
				g.setColor(textColor);
				text.drawText(g, text_draw_x, text_draw_y, tsx, tsy, tsw, tsh);
			}
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, v);
			
		}
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
