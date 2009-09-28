package com.cell.rpg;

import java.io.ObjectStreamException;
import java.io.Serializable;

public abstract class RPGObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public RPGObject() {
		init_transient();
	}

	protected void init_transient(){}
	
	
	final protected Object writeReplace() throws ObjectStreamException {
		return this;
	}
	
	final protected Object readResolve() throws ObjectStreamException {
		init_transient();
		return this;
	}
}
