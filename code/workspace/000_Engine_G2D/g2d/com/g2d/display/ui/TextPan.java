package com.g2d.display.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.Serializable;
import java.text.AttributedString;

import com.g2d.Tools;
import com.g2d.Version;
import com.g2d.annotation.Property;
import com.g2d.display.event.KeyEvent;
import com.g2d.display.event.MouseEvent;
import com.g2d.display.event.MouseMoveEvent;
import com.g2d.display.event.MouseWheelEvent;
import com.g2d.display.ui.text.MultiTextLayout;



public class TextPan extends UIComponent implements Serializable
{
	private static final long 			serialVersionUID = Version.VersionG2D;
	
	transient int 						text_draw_x;
	transient int 						text_draw_y;
	transient MultiTextLayout			text;
	
	@Property("文字颜色")
	public Color 						textColor;
	
	@Override
	protected void init_field() 
	{
		super.init_field();
		textColor			= new Color(0xffffffff, true);
		text				= new MultiTextLayout();
		enable_key_input	= false;
		enable_mouse_wheel	= false;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {   
		out.writeObject(textColor);
		out.writeUTF(getText());
	}
	  
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {   
		textColor	= (Color)in.readObject();
		String text	= (String)in.readUTF();
		{
			this.text = new MultiTextLayout();
			this.setText(text);
		}
	}  
	
	
	public TextPan() 
	{
		this("");
	}
	
	public TextPan(String text)
	{
		setText(text);
	}
	
	public TextPan(String text, int w, int h)
	{
		setText(text);
		setSize(w, h);
	}
	
	public void setText(String text)
	{
//		AttributedString astring = new AttributedString(text);
//		astring.addAttribute(TextAttribute.FONT, new Font("song", Font.PLAIN, 12), 0, text.length());
		this.text.setText(text);
	}
	
	public void appendLine(String text) {
		this.text.appendText(text);
	}
	
	public String getText() {
		return this.text.getText();
	}
	
	public void appendText(String text) {
		this.text.appendText(text);
	}
	
	
	public void update() 
	{
		super.update();
		text.is_show_caret = false;
		
		text.setWidth((getWidth()-layout.BorderSize*2));
		text_draw_x = layout.BorderSize;
		text_draw_y = layout.BorderSize;
	}
	
	
	public void render(Graphics2D g) 
	{
		super.render(g);
		
		
		
		g.setColor(textColor);
		text.drawText(g, text_draw_x, text_draw_y, 0, 0, getWidth(), getHeight());
	
		
	}

	
	
}
