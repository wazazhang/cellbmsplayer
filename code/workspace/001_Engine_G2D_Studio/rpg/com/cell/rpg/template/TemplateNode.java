package com.cell.rpg.template;

import java.util.ArrayList;

import com.cell.rpg.RPGObject;
import com.cell.util.zip.ZipNode;

public abstract class TemplateNode extends RPGObject
{
	public String	name		= "no name";
	public String	icon_index;
	
	public TemplateNode(String id, String name) {
		super(id);
		this.name	= name;
	}
	
}
