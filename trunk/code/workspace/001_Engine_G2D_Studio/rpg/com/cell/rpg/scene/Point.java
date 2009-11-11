package com.cell.rpg.scene;

import java.util.ArrayList;

public class Point extends SceneUnit
{	
	public int 		color;
	public float 	alpha;

	public ArrayList<String> next_names = new ArrayList<String>();
	
	public Point(String id, int x, int y) 
	{
		super(id);
		this.x = x;
		this.y = y;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{};
	}
}
