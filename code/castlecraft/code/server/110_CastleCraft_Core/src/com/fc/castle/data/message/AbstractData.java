package com.fc.castle.data.message;

import java.lang.reflect.Field;

import com.cell.net.io.MutualMessage;

public abstract class AbstractData implements MutualMessage
{	
	private static final long serialVersionUID = 1L;

	public void setField(Field field, Object value) throws Exception
	{
		field.set(this, value);
	}
	
	public Object getField(Field field) throws Exception
	{
		return field.get(this);
	}

}
