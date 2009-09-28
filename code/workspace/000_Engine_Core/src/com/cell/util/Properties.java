package com.cell.util;

import java.util.Vector;

import com.cell.reflect.Parser;


public class Properties extends Property<String>
{
	public Properties(){}

	public Properties(MarkedHashtable map){
		super(map);
	}
	
	public Properties(String text, String separator){
		loadText(text, separator);
	}

	protected boolean putText(String k, String v){
		return put(k, v);
	}

	public boolean putObject(String k, Object v){
		return put(k, Parser.objectToString(v));
	}
	
}
