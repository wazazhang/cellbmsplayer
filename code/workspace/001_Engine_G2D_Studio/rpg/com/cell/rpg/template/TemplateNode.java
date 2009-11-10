package com.cell.rpg.template;

import java.util.ArrayList;

import com.cell.rpg.RPGObject;

public abstract class TemplateNode extends RPGObject
{
	final public String		id;
	
	final public String		name;
	
	final ArrayList<String> battle_team = new ArrayList<String>();
	
	
	public TemplateNode(String id, String name) {
		this.id		= id;
		this.name	= name;
	}
	
	
	
}
