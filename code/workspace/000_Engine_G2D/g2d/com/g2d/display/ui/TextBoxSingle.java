package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;

import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.AnimateCursor;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.ui.text.MultiTextLayout;

public class TextBoxSingle extends UIComponent implements Serializable
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
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		textColor			= new Color(0xffffffff, true);
		is_readonly			= false;
		text				= new MultiTextLayout(true);
		enable_key_input	= true;
		enable_mouse_wheel	= true;
		setCursor(AnimateCursor.TEXT_CURSOR);
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
	
	/**
	 * onKeyTyped 接收不到ActionKey的事件
	 * ActionKey只有Down和Up事件
	 */
	
//	protected void onKeyTyped(KeyEvent event) {
//	protected void onKeyDown(KeyEvent event) {
//		if (enable_focus){
//			if (event.keyCode == java.awt.event.KeyEvent.VK_LEFT){
//				if (getRoot().isKeyHold(java.awt.event.KeyEvent.VK_SHIFT)){
//					text.setCaret(
//							getMouseX()-text_draw_x + xoffset, 
//							getMouseY()-text_draw_y);
//					if (text.getCaretPosition()==text.getCaretStartPosition()){
//						text.moveCaretStartPosition(-1);
//						text.moveCaretPosition(-1);
//					}else{
//						text.moveCaretEndPosition(-1);
//						text.moveCaretPosition(-1);
//					}
//				}else{
//					text.moveCaretStartPosition(-1);
//					text.moveCaretPosition(-1);
//					System.out.println("text.moveCaretPosition(-1);");
//					System.out.println("text.getCaretPosX() = "+text.getCaretPosX());
//					text.getCaretPosX();
//				}
//			}else if (event.keyCode == java.awt.event.KeyEvent.VK_RIGHT){
//				if (getRoot().isKeyHold(java.awt.event.KeyEvent.VK_SHIFT)){
//					if (text.getCaretPosition()==text.getCaretStartPosition()){
//						text.moveCaretStartPosition(1);
//						text.moveCaretPosition(1);
//					}else{
//						text.moveCaretEndPosition(1);
//						text.moveCaretPosition(1);
//					}
//				}else{
//					text.moveCaretStartPosition(1);
//					text.moveCaretPosition(1);
//					System.out.println("text.moveCaretPosition(1);");
//					System.out.println("text.getCaretPosition() = "+text.getCaretPosition());
//					System.out.println("text.getCaretPosX() = "+text.getCaretPosX());
//				}
//			}
//		}
//	}
	
	protected void onKeyTyped(KeyEvent event) {
		insertCharAtCurrentCaret(event.keyChar);
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
		text.drawText(g, 
				text_draw_x-xoffset, 
				text_draw_y, 
				xoffset, 
				0, 
				text_draw_w+2, 
				getHeight());
	
		
	}

	
	
}
