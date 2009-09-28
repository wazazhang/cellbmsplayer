package com.g2d.display.ui.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import com.cell.CUtil;
import com.cell.script.objective.IObjectiveFactory;
import com.cell.script.objective.Objective;
import com.cell.util.EnumManager;
import com.cell.util.EnumManager.ValueEnum;

/** 
 * 通过在字符串中嵌入代码，构造样式文本。<br>
 * @see com.g2d.display.ui.text.TextBuilder.Instruction
 */
final public class TextBuilder extends IObjectiveFactory
{
	public static enum Instruction implements ValueEnum<String>
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

	final public static Hashtable<String, Attribute> Instructions = new Hashtable<String, Attribute>();

	static 
	{
		for (Instruction ins : Instruction.values()) {
			Instructions.put(ins.value, ins.attribute);
		}
	}
	
	static Object getInstructionValue(String key, String value) 
	{
		Instruction instruction = EnumManager.getEnum(Instruction.class, key);
		
		try
		{
			if (value==null || value.length()==0) {
				return null;
			}
			
			if (instruction != null)
			{
				switch (instruction) {
				case COLOR:
					return new Color((int)Long.parseLong(value, 16) & 0x00ffffffff, true);
				case BACK_COLOR:
					return new Color((int)Long.parseLong(value, 16) & 0x00ffffffff, true);
				case SIZE:
					return new Integer(Integer.parseInt(value));
				case BOLD:
					return TextAttribute.WEIGHT_BOLD;
				case UNDERLINE:
					return TextAttribute.UNDERLINE_ON;
				case FONT:
					String[] fs = CUtil.splitString(value, "@");
					return new Font(fs[0], Integer.parseInt(fs[1]), Integer.parseInt(fs[2]));
				case LINK:
					return value;
				case ANTIALIASING:
					return Integer.parseInt(value);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.println("getInstructionValue : key=" + key + " : value=" + value);
		}
		
		return null;
	}
	
	public static AttributedString buildScript(String script)
	{
		if (script!=null){
			return new TextBuilder(script).attributed_text;
		} 
		return null;
	}
	
	public static String toString(AttributedString atext) {
		String text = "";
		CharacterIterator iter = atext.getIterator();
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
			text += c;
		}
		return text;
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	class InstructionObjective extends Objective<Attribute>
	{
		public Attribute	Attr;
		public Object 		AttrValue;
		int					AttrStart;
		int					AttrEnd;
		
		public InstructionObjective(Attribute attr, Object attrvalue)
		{
			Attr 		= attr;
			AttrValue	= attrvalue;
		}
		
		public Attribute getValue()
		{
			return Attr;
		}
	}
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	AttributedString								attributed_text;
	
	Hashtable<Attribute, Stack<InstructionObjective>>	CallStacks;
	Vector<InstructionObjective> 						ObjectQueue;
	
//	-----------------------------------------------------------------------------------------------------------------------------------
	
	public TextBuilder(String script)
	{
		if (script.length()>3)
		{
			CallStacks	= new Hashtable<Attribute, Stack<InstructionObjective>>();
			ObjectQueue	= new Vector<InstructionObjective>();

			build(script);
			
			if (!ObjectQueue.isEmpty())
			{
				try
				{
					attributed_text = new AttributedString(build_state);
					
					for (InstructionObjective item : ObjectQueue)
					{
						if (item.AttrValue!=null) {
							attributed_text.addAttribute(
									item.Attr, 
									item.AttrValue,
									item.AttrStart,
									item.AttrEnd);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
		
		attributed_text = new AttributedString(script);
	}

	Stack<InstructionObjective> getStack(Attribute attribute) 
	{
		Stack<InstructionObjective> stack = CallStacks.get(attribute);
		if (stack==null){
			stack = new Stack<InstructionObjective>();
			CallStacks.put(attribute, stack);
		}
		return stack;
	}
	
	@Override
	public Objective<Attribute> getObjective(String src, int begin, int end, String key, String value) 
	{
		// 对应的 TextAttribute
		Attribute attribute = Instructions.get(key);
		
		if (attribute != null)
		{
			// 该 TextAttribute对应的堆栈
			Stack<InstructionObjective> stack = getStack(attribute);
			// 得到值
			Object valueObject = getInstructionValue(key, value);
			
			InstructionObjective objective = new InstructionObjective(attribute, valueObject);
			
			// 如果 value不为空,则为color头 [color:AARRGGBB]
			if (valueObject!=null)
			{
				objective.AttrStart	= build_state.length();
				
				// 入栈
				stack.push(objective);
			}
			// 如果value为空,则为color尾 [color]
			else if (!stack.isEmpty()) 
			{
				// 出栈上一个匹配值
				InstructionObjective prew	= stack.pop();

				prew.AttrEnd		= build_state.length();
				
				// 添加到对象集
				ObjectQueue.addElement(prew);
			}
			
			return objective;
		}
		else
		{
//			System.err.println("unknow instruction : " + key + " : " + value);
		}
		
		return null;
	}
	
	
	public AttributedString getAttributedText() 
	{
		return attributed_text;
	}
	
	public String getText()
	{
		return build_state;
	}
	
	@Override
	public String toString() {
		return build_state;
	}

}