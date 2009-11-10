package com.cell.rpg.scene;

import java.util.ArrayList;

public class Point extends SceneUnit
{	
	public int 		color;
	public float 	alpha;

	public ArrayList<String> next_names = new ArrayList<String>();
	
	public Point(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{};
	}
}
