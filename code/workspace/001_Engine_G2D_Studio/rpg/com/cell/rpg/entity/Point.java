package com.cell.rpg.entity;

import java.util.ArrayList;


public class Point extends Unit
{	
	public int 		color;
	public float 	alpha;

	public ArrayList<Integer> next_index = new ArrayList<Integer>();
	
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
