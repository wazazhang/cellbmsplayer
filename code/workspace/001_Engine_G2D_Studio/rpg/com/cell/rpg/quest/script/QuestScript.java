package com.cell.rpg.quest.script;

public class QuestScript 
{
	public static enum Chunk 
	{
		BRIEF			("<brief>"),
		DISCUSSION		("<discussion>"),
		COMPLETE		("<complete>"),
		;
		final private String chunk;
		private Chunk(String chunk) {
			this.chunk = chunk;
		}
		@Override
		public String toString() {
			return this.chunk;
		}
	}
	
	final public String SYMBOL_LINE_COMMENT		= "#";
	
		
	
	public static String createExample() {
		return 
		Chunk.BRIEF + "\n" +
		"this is an example brief ...\n\n" + 
		Chunk.DISCUSSION + "\n" +
		"this is an example discussion ...\n\n" + 
		Chunk.COMPLETE + "\n" +
		"this is an example complete ...\n\n" + 
		"";
	}
	
}
