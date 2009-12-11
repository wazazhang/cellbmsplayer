package com.cell.rpg.struct;

public enum Comparison 
{
	EQUAL						("等于"),
	NOT_EQUAL					("不等于"),
	
	GREATER_THAN 				("大于"),
	LESSER_THAN					("小于"),
	
	EQUAL_OR_GREATER_THAN		("大于等于"),
	EQUAL_OR_LESSER_THAN 		("小于等于"),
	;
	
	
	final private String text;
	private Comparison(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}
