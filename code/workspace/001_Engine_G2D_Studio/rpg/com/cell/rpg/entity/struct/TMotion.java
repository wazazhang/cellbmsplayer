package com.cell.rpg.entity.struct;

import java.io.Serializable;

import com.cell.rpg.RPGObject;

public class TMotion extends RPGObject
{
	private static final long serialVersionUID = 1L;
	
	/** 每秒多少距离 */
	public float 	speed;
	
	/** 加速度 */
	public float 	acceleration;
	
	/** 移动角度 */
	public float	radians;
	

}
