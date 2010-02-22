package com.cell.rpg.particle;

import java.io.Serializable;


public interface Shape extends Serializable
{
	/** 矩形  */
	public static class Rectangle implements Shape
	{
		public float x, y, w, h;
	}
	
	/**
	 * 圆或环
	 */
	public static class Ring implements Shape
	{
		/** 内圆半径 */
		public float radius1;
		
		/** 外圆半径 */
		public float radius2;
	}
}
