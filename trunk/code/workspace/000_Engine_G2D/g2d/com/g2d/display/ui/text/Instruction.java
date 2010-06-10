package com.g2d.display.ui.text;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator.Attribute;

import com.cell.util.EnumManager.ValueEnum;

public enum Instruction implements ValueEnum<String>
{
	/** [color:color as AARRGGBB]text[color] */
	COLOR			("color",	TextAttribute.FOREGROUND),

	/** [back:color as AARRGGBB]text[back] */
	BACK_COLOR		("back",	TextAttribute.BACKGROUND),
	
	/** [size:font size]text[size] */
	SIZE			("size",	TextAttribute.SIZE),

	/** [bold]text[bold] */
	BOLD			("bold",	TextAttribute.WEIGHT),
	
	/** [font:font name@style@size]text[font] <br> 
	 * @see java.awt.Font*/
	FONT			("font",	TextAttribute.FONT),
	
	/** [under]text[under] */
	UNDERLINE		("under",	TextAttribute.UNDERLINE),
	
	/** [link:text or url]text[link] */
	LINK			("link",	com.g2d.display.ui.text.TextAttribute.LINK),
	
	/** [anti:1 or 0]text[anti] */
	ANTIALIASING	("anti",	com.g2d.display.ui.text.TextAttribute.ANTIALIASING),

	/** [image:path]replacement[image]<br>
	 * 其中的文字将用图片填充*/
	IMAGE			("image",	TextAttribute.FOREGROUND),
	
	;

	final public String		value;
	final public Attribute	attribute;
	
	Instruction(String v, Attribute attr) {
		value = v;
		attribute = attr;
	}
	
	public String getValue() {
		return value;
	}
}
