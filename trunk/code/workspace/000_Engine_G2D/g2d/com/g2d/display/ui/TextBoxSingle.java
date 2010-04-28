package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.Serializable;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.AnimateCursor;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.TextInputer;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.text.MultiTextLayout;
import com.g2d.util.Drawing;

public class TextBoxSingle extends UIComponent implements Serializable, TextInputer
{
	private static final long serialVersionUID = Version.VersionG2D;
	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient int 						text_draw_w;
	transient MultiTextLayout			text;
	
	@Property("文字颜色")
	public Color 						textColor	= new Color(0xffffffff, true);
	
	public boolean 						is_readonly	= false;
	
	/** 光标超出范围的偏移量 */
	transient int 						xoffset = 0;

	/**文字是否抗锯齿*/
	@Property("文字是否抗锯齿")
	public boolean	enable_antialiasing	 = false;
	
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		textColor			= new Color(0xffffffff, true);
		is_readonly			= false;
		text				= new MultiTextLayout(true);
		enable_key_input	= true;
		enable_mouse_wheel	= true;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {   
		out.writeBoolean(is_readonly);
		out.writeObject(textColor);
		out.writeUTF(text.getText());
	}   
	  
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {   
		is_readonly	= in.readBoolean();
		textColor	= (Color)in.readObject();
		String text	= (String)in.readUTF();
		{
			this.text = new MultiTextLayout(true);
			this.setText(text);
		}
	}


	public TextBoxSingle() {
		this("TextBoxSingle");
	}
	
	public TextBoxSingle(String text)
	{
		this(text, 100, 25);
	}
	
	public TextBoxSingle(String text, int width, int height)
	{
		this.text.setText(text);
		this.setSize(width, height);
	}
	
	@Override
	public AnimateCursor getCursor() {
		return AnimateCursor.TEXT_CURSOR;
	}

	@Override
	public boolean isInput() {
		return text.is_show_caret;
	}
	
	
	public void setText(String text) {
		this.text.setText(text);
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
	}
	
	public void insertCharAtCurrentCaret(char ch){
		if (!is_readonly || ch == MultiTextLayout.CHAR_COPY) {
			text.insertChar(ch);
		}
	}

	public String getText() {
		return this.text.getText();
	}
	
	public void setTextPassword(boolean b) {
		this.text.setIsPassword(b);
	}
	
	protected void onMouseDown(MouseEvent event) {
		text.setCaret(
				getMouseX()-text_draw_x + xoffset, 
				getMouseY()-text_draw_y);
	}
	
	protected void onMouseDraged(MouseMoveEvent event) {
		text.dragCaret(
				getMouseX()-text_draw_x + xoffset, 
				getMouseY()-text_draw_y);
	}
	
	protected void onKeyTyped(KeyEvent event) {
		insertCharAtCurrentCaret(event.keyChar);
	}
	
	protected void onKeyDown(KeyEvent event) {
		if (event.keyCode == java.awt.event.KeyEvent.VK_LEFT) {
			text.moveCaretPosition(-1);
		} else if (event.keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
			text.moveCaretPosition(1);
		}
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
		
		
		int caret = text.getCaretPosX();
		
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize;
		
		text_draw_w = getWidth() - (layout.BorderSize<<1) - 2;
		
		if (text.getWidth() > text_draw_w) {
			if (xoffset + text_draw_w < caret) {
				xoffset = caret - text_draw_w;
			}
			else if (xoffset > caret) {
				xoffset = caret;
			}
		}else{
			xoffset = 0;
		}
	}
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		g.setColor(textColor);
		if (enable_antialiasing) {
			Object v = g.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			text.drawText(g, text_draw_x - xoffset, text_draw_y, xoffset, 0,
					text_draw_w + 2, getHeight());
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, v);
		} else {
			text.drawText(g, text_draw_x - xoffset, text_draw_y, xoffset, 0,
					text_draw_w + 2, getHeight());
		}
	}

	
	
}
