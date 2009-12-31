package com.g2d.display.ui.text;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;
import java.util.Vector;

import com.cell.CMath;
import com.g2d.Tools;
import com.g2d.display.DisplayObject;

public class MultiTextLayout 
{
	/**
	 * MultiTextLayout 中的一行数据
	 * @author WAZA
	 */
	class TextLine 
	{
		// 相对于MultiTextLayout的坐标
		int x;
		int y;
		final int 			width;
		final int 			height;
		
		// 字符对齐的修正值
		final int			offsetx;
		final int			offsety;

		final TextLayout	text_layout;
		final int 			line_index;
		
		Rectangle 			selected;
		Color 				selected_color = new Color(0x40ffffff, true);
		
		public TextLine(TextLayout layout, int line)
		{
			this.text_layout	= layout;
			this.line_index		= line;
			this.offsetx		= -(int)layout.getBounds().getX();
			this.offsety		= -(int)layout.getBounds().getY();
			this.width			= (int)text_layout.getBounds().getWidth();
			this.height			= Math.max(10, (int)text_layout.getBounds().getHeight()+space);
		}
		
		final public TextLayout getLayout() 
		{
			return text_layout;
		}
		
		public void render(Graphics2D g)
		{
//			{
//				Color c = g.getColor();
//					g.translate(x, y);
//						g.translate(offsetx, offsety);
//							g.setColor(Color.green);
//							g.fill(text_layout.getBounds());
//						g.translate(-offsetx, -offsety);
//					g.translate(-x, -y);
//				g.setColor(c);
//			}
			synchronized (g) {
				g.translate(x, y);
				if (selected!=null){
					Color pc = g.getColor();
					g.setColor(selected_color);
					g.fill(selected);
					g.setColor(pc);
				}
				text_layout.draw(g, offsetx, offsety);
				g.translate(-x, -y);
			}
		}
	
	}
	
	/**
	 * 改变字符或尺寸的事件
	 * @author WAZA
	 */
	class TextChange
	{
		String 				text;
		AttributedString 	textStyle;
		int 				width;
		
		TextChange(int width)
		{
			this.width 		= width;
			this.text 		= MultiTextLayout.this.text;
			this.textStyle 	= MultiTextLayout.this.attr_text;
		}
		
		TextChange(String text)
		{
			this.width 		= MultiTextLayout.this.width;
			this.text 		= text;
			this.textStyle	= new AttributedString(encodeChars(text));
		}
		
		TextChange(AttributedString atext)
		{
			this.width		= MultiTextLayout.this.width;
			this.textStyle	= atext;
			this.text 		= Tools.toString(atext);
			
		}
		
		void set(int width) {
			this.width = width;
		}

		void set(String text) {
			this.text = text;
			this.textStyle = new AttributedString(encodeChars(text));
		}

		void set(AttributedString atext) {
			this.textStyle = atext;
			this.text = Tools.toString(atext);
		}
	}
	
	/**
	 * 一段有属性的字符串
	 * @author WAZA
	 */
	public class AttributedSegment
	{
		final public Attribute	attribute;
		final public Object		attribute_value;
		final public String		text;
		
		AttributedSegment(Attribute	attribute, Object value, String text){
			this.attribute = attribute;
			this.attribute_value = value;
			this.text = text;
		}
		
		@Override
		public String toString() {
			return "\"" + text + "\" : " + attribute + " : " + attribute_value;
		}
	}
	
//	----------------------------------------------------------------------------------------------------------------
//	显示
	
	private int 				width;
	private int 				height;
	private int 				space 			= 2;
	
	private String 				text 			= "";
	private AttributedString 	attr_text;

	private Vector<TextLine> 	textlines 		= new Vector<TextLine>();


	/**是否为单行*/
	final public boolean 		is_single_line;
	/**是否只读*/
	public boolean 				is_read_only;
	/**是否显示光标*/
	public boolean 				is_show_caret 	= true;
	/**是否显示为密码*/
	private boolean 			is_password 	= false;
	
	transient private int		render_timer;
	
//	----------------------------------------------------------------------------------------------------------------
//	交互
	
	// 文字光标
	Rectangle 					caret_bounds 	= new Rectangle();
	
	// 选择的文字
	int 						caret_line;
	int 						caret_start_line;
	int 						caret_end_line;
	
	int 						caret_position;
	int 						caret_start_position;
	int 						caret_end_position;
	
	TextHitInfo 				caret_start_hit;
	TextHitInfo 				caret_end_hit;

//	----------------------------------------------------------------------------------------------------------------
//	设置用以改变状态
	
	private TextChange 			textChange;
	private String 				inserted_text	= null;
	private char 				inserted_char	= 0;
	int							set_caret_position	= -1;
	
//	----------------------------------------------------------------------------------------------------------------

	public MultiTextLayout(boolean single) {
		is_single_line = single;
	}
	
	public MultiTextLayout() {
		this(false);
	}
	
	synchronized public boolean isIsPassword() {
		return is_password;
	}

	synchronized public void setIsPassword(boolean isPassword) {
		is_password = isPassword;
		if (textChange!=null) {
			textChange.set(textChange.text);
		} else {
			setText(text);
		}
	}

//	----------------------------------------------------------------------------------------------------------------

	synchronized public void setCaret(int x, int y)
	{
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		if (!is_read_only) {
			caret_bounds = new Rectangle(x, y, 2, 10);
			caret_end_hit = null;
			caret_start_hit = null;
			caret_start_position = 0;
			caret_position = 0;
			
			if (!is_single_line && textlines.size()>1){
				for (TextLine line : textlines) {
					if (caret_bounds.intersects(line.x, line.y, width, line.height)) {
						testCaretStart(line, x, y);
						break;
					} else {
						caret_start_position += line.getLayout().getCharacterCount();
					}
				}
				if (caret_start_hit==null) {
					testCaretStart(textlines.elementAt(textlines.size()-1), x, y);
				}
			}
			else if (textlines.size()>0) {
				testCaretStart(textlines.elementAt(0), x, y);
			}
		}
	}

	synchronized public void dragCaret(int x, int y)
	{
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		if (!is_read_only) {
			caret_bounds = new Rectangle(x, y, 2, 10);
			caret_end_hit = null;
			caret_end_position = 0;
			caret_position = 0;
			if (!is_single_line && textlines.size()>1){
				for (TextLine line : textlines) {
					if (caret_bounds.intersects(line.x, line.y, width, line.height)) {
						testCaretEnd(line, x, y);
						break;
					} else {
						caret_end_position += line.getLayout().getCharacterCount();
					}
				}
				if (caret_end_hit==null) {
					testCaretEnd(textlines.elementAt(textlines.size()-1), x, y);
				}
			}
			else if (textlines.size()>0) {
				testCaretEnd(textlines.elementAt(0), x, y);
			}
			
			testCaretSelected();
		}
	}
	
	private void testCaretStart(TextLine line, int x, int y)
	{
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		caret_start_hit = line.getLayout().hitTestChar(
				x-line.x+line.offsetx, 
				y-line.y+line.offsety);
		
		caret_bounds = line.getLayout().getCaretShape(caret_start_hit).getBounds();
		caret_bounds.x = caret_bounds.x-1;
		caret_bounds.y = line.y-space;
		caret_bounds.height = line.height+space;
		caret_bounds.width = 2;
		
		caret_start_position += caret_start_hit.getInsertionIndex();
		caret_start_line = line.line_index;
		
		caret_position = caret_start_position;
		caret_line = caret_start_line;
		
		if (caret_position > 0) {
			if (caret_start_hit.getInsertionIndex()>0) {
				int pos = caret_position - 1;
				if (pos<text.length() && text.charAt(pos) == '\n') {
					caret_position -= 1;
				}
			}
		}
	}
	
	private void testCaretEnd(TextLine line, int x, int y)
	{
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		caret_end_hit = line.getLayout().hitTestChar(
				x-line.x+line.offsetx, 
				y-line.y+line.offsety);
		
		caret_bounds = line.getLayout().getCaretShape(caret_end_hit).getBounds();
		caret_bounds.x = caret_bounds.x-1;
		caret_bounds.y = line.y-space;
		caret_bounds.height = line.height+space;
		caret_bounds.width = 2;
		
		caret_end_position += caret_end_hit.getInsertionIndex();
		caret_end_line = line.line_index;
		
		caret_position = caret_end_position;
		caret_line = caret_end_line;
		
		if (caret_position > 0) {
			if (caret_end_hit.getInsertionIndex()>0) {
				int pos = caret_position - 1;
				if (pos<text.length() && text.charAt(pos) == '\n') {
					caret_position -= 1;
				}
			}
		}
	}
	

	
	private void testCaretSelected()
	{
		if (caret_start_hit!=null && caret_end_hit!=null) 
		{
			try{
				int max = Math.max(caret_start_line, caret_end_line);
				int min = Math.min(caret_start_line, caret_end_line);
				
				for (int i=textlines.size()-1; i>=0; --i)
				{
					TextLine line = textlines.get(i);
					Rectangle rect = null;
					// same line
					if (i == min && i == max){
						rect = line.getLayout().getVisualHighlightShape(caret_start_hit, caret_end_hit).getBounds();
					}
					// end line
					else if (i==max && i==caret_end_line) {
						rect = line.getLayout().getCaretShape(caret_end_hit).getBounds();
						rect.width = rect.x;
						rect.x = 0;
					}
					else if (i==max && i==caret_start_line) {
						rect = line.getLayout().getCaretShape(caret_start_hit).getBounds();
						rect.width = rect.x;
						rect.x = 0;
					}
					// start line
					else if (i==min && i==caret_end_line) {
						rect = line.getLayout().getCaretShape(caret_end_hit).getBounds();
						rect.width = width - rect.x;
					}
					else if (i==min && i==caret_start_line) {
						rect = line.getLayout().getCaretShape(caret_start_hit).getBounds();
						rect.width = width - rect.x;
					}
					// included line
					else if (CMath.isInclude(i, min, max)){
						rect = new Rectangle(0, 0, width, line.height);
					}
				
					if (rect!=null) {
						line.selected = rect.getBounds();
						line.selected.y = -space;
						line.selected.height = line.height+space;
						//System.out.println(line.selected + " line=" + line.line);
					}else{
						line.selected = null;
					}
					
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//System.out.println(getSelectedText());
		}
	}
	
	
	
	synchronized public int getCaretStartPosition() {
		return caret_start_position;
	}

	synchronized public int getCaretEndPosition() {
		return caret_end_position;
	}
	
	/**
	 * 
	 * @return 获得当前光标位置？
	 * @author yagamiya
	 */
	synchronized public int getCaretPosition() {
		return caret_position;
	}
	
	/**
	 * 移动光标位置
	 * @param d
	 */
	synchronized public void moveCaretPosition(int d) {
		setCaretPosition(getCaretPosition() + d);
	}
	
	synchronized public void setCaretPosition(int position) {
		set_caret_position = position;
		set_caret_position = Math.max(set_caret_position, 0);
		set_caret_position = Math.min(set_caret_position, getText().length());
	}
	
//	public void moveCaretStartPosition(int d) {
//		caret_start_position += d;
//		caret_start_position = Math.max(caret_start_position, 0);
//		caret_start_position = Math.min(caret_start_position, text.length());
//	}
//	
//	public void moveCaretEndPosition(int d) {
//		caret_end_position += d;
//		caret_end_position = Math.max(caret_end_position, 0);
//		caret_end_position = Math.min(caret_end_position, text.length());
//	}
//	
	
	
	synchronized public String getSelectedText(){
		if (caret_start_hit!=null && caret_end_hit!=null) {
			int max = Math.max(caret_start_position, caret_end_position);
			int min = Math.min(caret_start_position, caret_end_position);
			try{
				max = Math.min(max, text.length());
				min = Math.max(min, 0);
				return encodeChars(text.substring(min, max));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	synchronized public int getCaretPosX() {
		if (text.length()>0) {
			return caret_bounds.x;
		}else{
			return 0;
		}
	}
	
	synchronized public int getCaretPosY() {
		if (text.length()>0) {
			return caret_bounds.y;
		}else{
			return 0;
		}
	}
	
	synchronized public int pointToPosition(int x, int y)
	{
		if (x<0) x = 0;
		if (y<0) y = 0;
		
		int position = 0;
		
		for (TextLine line : textlines) 
		{
			if (CMath.intersectRect2(line.x, line.y, width, line.height, x, y, 2, 10))
			{
				x -= line.x;
				y -= line.y;
				TextHitInfo hit = line.getLayout().hitTestChar(x, y);
				position += hit.getCharIndex();
				break;
			} else {
				position += line.getLayout().getCharacterCount();
			}
		}
		
		position = Math.min(position, text.length());
		position = Math.max(position, 0);
//		if (text.length()>0){
//			System.out.println("pos=" + position + " ch=" + text.charAt(position));
//		}
		return position;
	}
	
	synchronized public AttributedSegment getSegment(int position, Attribute attribute, Object value)
	{
		if (attr_text != null)
		{
			AttributedCharacterIterator it	= attr_text.getIterator();
			return getSegment(it, position, attribute, value);
		}
		return null;
	}
	
	synchronized public AttributedSegment getSegment(int position, Attribute attribute)
	{
		if (attr_text != null)
		{
			AttributedCharacterIterator it	= attr_text.getIterator();
			it.setIndex(position);
			return getSegment(it, position, attribute, it.getAttribute(attribute));
		}
		return null;
	}
	
	private AttributedSegment getSegment(AttributedCharacterIterator it, int position, Attribute attribute, Object value)
	{
		try
		{
			it.setIndex(position);
			
			Object cur_value = it.getAttribute(attribute);
		
			if (value!=null && value.equals(cur_value))
			{
				String text_segment = "";
				
				for (char c = it.current(); c != CharacterIterator.DONE; c = it.next()) {
					if (value.equals(it.getAttribute(attribute))){
						text_segment = text_segment + c;
					}
				}
				it.setIndex(position);
				for (char c = it.previous(); c != CharacterIterator.DONE; c = it.previous()) {
					if (value.equals(it.getAttribute(attribute))){
						text_segment = c + text_segment;
					}
				}
				
				return new AttributedSegment(attribute, value, text_segment);
			}
			
		}
		catch (Exception e) {
		}
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------------


	
	synchronized public void insertText(String str) {
		if (is_read_only) return;
		if (inserted_text==null){
			inserted_text="";
		}
		if (is_single_line) {
			str = str.replaceAll("\n", "");
		}
		inserted_text += str;
	}

	
	
	final public static char CHAR_CUT			= 24;	// ctrl + x
	final public static char CHAR_PASTE		= 22;	// ctrl + v
	final public static char CHAR_COPY			= 3;	// ctrl + c
	
	final public static char CHAR_BACKSPACE	= 8;	// backspace
	final public static char CHAR_DELETE		= 127;	// delete
	
	
	synchronized public void insertChar(char c)
	{
		if (is_read_only) return;
		
		if (inserted_text==null){
			inserted_text="";
		}
		
		if(c == CHAR_CUT)// ctrl + x
		{
			Tools.setClipboardText(getSelectedText());
			inserted_char = CHAR_DELETE;
		}
		else if(c == CHAR_COPY)// ctrl + c
		{
			Tools.setClipboardText(getSelectedText());
		}
		else if(c == CHAR_PASTE)// ctrl + v
		{
			String str = Tools.getClipboardText();
			if (is_single_line) {
				str = str.replaceAll("\n", "");
			}
			inserted_text += str;
		}
		else if(c == CHAR_BACKSPACE)// backspace
		{
			inserted_char = c;
		}
		else if(c == CHAR_DELETE)// delete
		{
			inserted_char = c;
		}
		else if (c=='\t')
		{
			if (!is_single_line) {
				inserted_text += "    ";
			}
		}
		else if (c=='\n')
		{
			if (!is_single_line) inserted_text += "\n";
		}
		else if(c >= 32)
		{
			inserted_text += c;
		}
		
	}

//	----------------------------------------------------------------------------------------------------------------

	synchronized public void setText(String text){
		if (is_single_line) {
			text = text.replaceAll("\n", "");
		}
		if (textChange!=null){
			this.text = textChange.text;
			this.attr_text = textChange.textStyle;
			textChange = null;
		}
		if (!text.equals(this.text)) {
			if (textChange != null) {
				textChange.set(text);
			} else {
				textChange = new TextChange(text);
			}
		}
	}
	
	synchronized public void setText(AttributedString atext)
	{
		if (textChange!=null){
			this.text = textChange.text;
			this.attr_text = textChange.textStyle;
			textChange = null;
		}
		if (atext!=null && !atext.equals(attr_text)) {
			if (textChange != null) {
				textChange.set(atext);
			} else {
				textChange = new TextChange(atext);
			}
		}
	}
	
	synchronized public void appendText(String text) {
		if (is_single_line) {
			text = text.replaceAll("\n", "");
		}
		if (textChange != null) {
			this.text = textChange.text;
			this.attr_text = textChange.textStyle;
			textChange = null;
		}
		if (this.text != null) {
			textChange = new TextChange(this.text + text);
		} else {
			textChange = new TextChange(text);
		}
	}
	
	synchronized public void appendText(AttributedString atext)
	{
		if (textChange!=null){
			this.text		= textChange.text;
			this.attr_text	= textChange.textStyle;
			textChange = null;
		}
		if (this.attr_text!=null) {
			textChange = new TextChange(Tools.linkAttributedString(this.attr_text, atext));
		} else {
			textChange = new TextChange(atext);
		}
		
	}
	
	
	
	
	
	synchronized public String getText() {
		if (textChange!=null) {
			return textChange.text;
		}
		return text==null ? "" : text;
	}
	
	synchronized public void setWidth(int width) {
		if (this.width != width) {
			if (textChange != null) {
				textChange.set(width);
			} else {
				textChange = new TextChange(width);
			}
		}
	}
	
	synchronized public void setSpace(int space) {
		this.space = space;
	}
	
//	----------------------------------------------------------------------------------------------------------------
	
	synchronized public int getWidth() {
		return width;
	}
	
	synchronized public int getHeight() {
		return height;
	}
	
	synchronized public int getSpace() {
		return space;
	}
	
//	----------------------------------------------------------------------------------------------------------------
	
	Dimension render_size = new Dimension();
	
	synchronized public Dimension drawText(Graphics2D g, int x, int y) {
		return this.drawText(g, x, y, 0, 0, width, height);
	}
	
	synchronized public Dimension drawText(Graphics2D g, int x, int y, int sx, int sy, int sw, int sh) 
	{
		x += 1;
		y += 1;
		sx -= 1;
		sy -= 1;
		sw += 2;
		sh += 2;
		
		tryChangeTextAndCaret(g);
		
		render_size.setSize(0, height);
		{
			Rectangle rect = new Rectangle(sx, sy, sw, sh);
			
			g.translate(x, y);	
			Shape prewShape = g.getClip();
			g.clip(rect);
			{
				for (TextLine line : textlines) {
					if (rect.intersects(line.x, line.y, line.width, line.height)) {
						line.render(g);
					}
					render_size.width = Math.max(line.width, render_size.width);
				}
				if (!is_read_only && is_show_caret) {
					if (render_timer++/6%2==0 && caret_bounds!=null) {
						g.setColor(Color.WHITE);
						if (text.length()>0) {
							g.fillRect(caret_bounds.x, caret_bounds.y, 2, caret_bounds.height);
						}else{
							g.fillRect(0, 0, 2, g.getFont().getSize());
						}
					}
				}
			}
			g.setClip(prewShape);
			g.translate(-x, -y);
		}
		return render_size;
	}

	private void tryChangeTextAndCaret(Graphics2D g) 
	{
		// try insert text
		if (!is_read_only) {
			if (inserted_text!=null && inserted_text.length()>0){
				instertText();
				inserted_text = null;
			}
			if (inserted_char!=0){
				instertChar();
				inserted_char = 0;
			}
		}
		// try change text
		if (textChange!=null){
			resetText(g, textChange.text, textChange.textStyle, textChange.width) ;
			textChange = null;
			// update caret
			if (!is_read_only) {
				resetCaret();
			}
		}
		// try change caret
		else if (set_caret_position >= 0) {
			if (!is_read_only) {
				caret_position			= set_caret_position;
				caret_start_position	= set_caret_position;
				caret_end_position		= set_caret_position;
				set_caret_position		= -1;
				resetCaret();
			}
		}
	}
	
	private void resetText(Graphics2D g, String text, AttributedString atext, int width) 
	{
		this.text		= text;
		this.attr_text	= atext;
		this.width		= width;
		this.height		= space;
		this.textlines.clear();
		
		if (text.length()>0)
		{
			{
				AttributedCharacterIterator it = atext.getIterator();
				attr_text = new AttributedString(text);
				int i=0, e=1;
				for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) 
				{
					Map<Attribute,Object> map = it.getAttributes();
					Number 	size = (Number)map.get(TextAttribute.SIZE);
					Font 	font = (Font)map.get(TextAttribute.FONT);
					if (font == null) {
						font = g.getFont();
					}
					if (size != null && size.intValue() != font.getSize()) {
						font = new Font(font.getName(), 0, size.intValue());
					}
					
					attr_text.addAttribute(TextAttribute.FONT, font, i, e);
					attr_text.addAttributes(map, i, e);
					i++;
					e++;
				}
			}
			// lines
			if (!is_single_line)
			{
				LineBreakMeasurer textMeasurer = new LineBreakMeasurer(
						attr_text.getIterator(), 
						g.getFontRenderContext());
		
				while (textMeasurer.getPosition()>=0 && textMeasurer.getPosition()<text.length())
				{
					TextLayout layout = null;
					int limit = text.indexOf('\n', textMeasurer.getPosition());
					if (limit >= textMeasurer.getPosition()) {
						layout = textMeasurer.nextLayout(width, limit+1, false);
					}
					else {
						layout = textMeasurer.nextLayout(width);
					}
					
					TextLine line = new TextLine(layout, textlines.size());
					line.x = 0;
					line.y = height;
					height += line.height + space;
					textlines.add(line);

					//System.out.println(line.x+","+line.y+","+line.width+","+line.height);
				}
			}
			else
			{
				TextLayout layout = new TextLayout(attr_text.getIterator(), g.getFontRenderContext());
				TextLine line = new TextLine(layout, textlines.size());
				line.x = 0;
				line.y = height;
				this.height += line.height + space;
				this.width = line.width;
				this.textlines.add(line);
			}
		}
	}
	
	private void resetCaret() 
	{
		int text_remain = text.length();
		
		for (int i=textlines.size()-1; i>=0; --i) 
		{
			TextLine line = textlines.elementAt(i);
			
			text_remain -= line.getLayout().getCharacterCount();
			
			if (text_remain <= caret_position)
			{
				try
				{
					int pos = Math.max(1, (caret_position - text_remain)+1);
					try {
						caret_start_hit = line.getLayout().getNextLeftHit(pos);
						caret_bounds 	= line.getLayout().getCaretShape(caret_start_hit).getBounds();
						caret_bounds.x	= caret_bounds.x-1;
					} catch (Exception e) {
						caret_bounds.x 	= line.width+1;
					}
					
					caret_bounds.y 		= line.y-space;
					caret_bounds.height	= line.height+space;
					caret_bounds.width	= 2;
					
					//System.out.println(caret_bounds+" caret_position="+caret_position+" pos="+pos);
					//System.out.println("caret_position="+caret_position+" pos="+pos);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				caret_start_line = line.line_index;
				caret_start_position = caret_position;
				
				break;
			}
		}
	}
	
	private void instertChar()
	{
		try {
			if (caret_start_hit!=null && caret_end_hit!=null && caret_start_position!=caret_end_position) {
				int max = Math.max(caret_start_position, caret_end_position);
				int min = Math.min(caret_start_position, caret_end_position);
				switch (inserted_char) {
				case CHAR_BACKSPACE:
				case CHAR_DELETE:
					if (max<text.length()) {
						setText(text.substring(0, min) + text.substring(max));
					}else{
						setText(text.substring(0, min));
					}
					
					caret_position = min;
					break;
				}
			}
			else {
				switch (inserted_char) {
				case CHAR_BACKSPACE:
					if (caret_position>0 && text.length()>0) {
						if (caret_position<text.length()) {
							setText(text.substring(0, caret_position-1) + text.substring(caret_position));
						}else{
							setText(text.substring(0, text.length()-1));
						}
						caret_position -= 1;
					}
					break;
				case CHAR_DELETE:
					if (caret_position>=0 && text.length()>0){
						if (caret_position<text.length()) {
							setText(text.substring(0, caret_position) + text.substring(caret_position+1));
						}else{
//							setText(text.substring(0, caret_position));
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		caret_end_hit = null;
	}
	
	private void instertText()
	{
		try {
			if (caret_start_hit!=null && caret_end_hit!=null && caret_start_position!=caret_end_position) {
				int max = Math.max(caret_start_position, caret_end_position);
				int min = Math.min(caret_start_position, caret_end_position);
				if (max < text.length()) {
					setText(text.substring(0, min) + inserted_text + text.substring(max));
				}else{
					setText(text.substring(0, min) + inserted_text);
				}
				caret_position = min + inserted_text.length();
			} else {
				if (caret_position < text.length()){
					setText(text.substring(0, caret_position) + inserted_text + text.substring(caret_position));
				}else{
					setText(text + inserted_text);
				}
				caret_position += inserted_text.length();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		caret_end_hit = null;
	}
	

	private String encodeChars(String text)
	{
		if (is_password) {
			char[] chars = new char[text.length()];
			for (int i = text.length() - 1; i >= 0; --i) {
				chars[i] = ('*');
			}
			return new String(chars);
		}
		return text;
	}
}
