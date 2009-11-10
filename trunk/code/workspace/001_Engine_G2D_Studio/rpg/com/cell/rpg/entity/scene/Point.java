package com.cell.rpg.entity.scene;

import java.util.ArrayList;

import com.cell.rpg.entity.Unit;


public class Point extends SceneUnit
{	
	public int 		color;
	public float 	alpha;

	public ArrayList<String> next_names = new ArrayList<String>();
	
	public Point(int x, int y) 
	{
		pos.x = x;
		pos.y = y;
	}

	public Class<?>[] getSubAbilityTypes()
	{
		return new Class<?>[]{};
	}
}
