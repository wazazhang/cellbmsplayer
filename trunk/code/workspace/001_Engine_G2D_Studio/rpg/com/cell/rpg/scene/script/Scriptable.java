package com.cell.rpg.scene.script;

public interface Scriptable 
{
	public Scriptable putAttribute(String key, Scriptable value);
	
	public Scriptable getAttribute(String key);
}
