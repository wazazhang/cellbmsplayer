package com.fc.castle.data.template;

import com.cell.net.io.Comment;
import com.fc.castle.data.message.AbstractData;

public class FormualMap extends AbstractData
{
	private static final long serialVersionUID = 1L;

	@Comment(
			"[attackType, defenseType, 攻击加成]," +
			"[attackType, defenseType, 攻击加成],")
	public float[][] ammor_map;
	

}
