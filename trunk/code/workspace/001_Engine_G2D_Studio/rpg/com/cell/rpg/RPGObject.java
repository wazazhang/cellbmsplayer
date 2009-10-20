package com.cell.rpg;

import java.io.ObjectStreamException;
import java.io.Serializable;

import com.cell.DObject;
import com.cell.util.MarkedHashtable;

public abstract class RPGObject extends DObject implements Serializable
{
	private static final long	serialVersionUID = 1L;
	
	protected MarkedHashtable	data_group;
	
	public RPGObject() {}

}
