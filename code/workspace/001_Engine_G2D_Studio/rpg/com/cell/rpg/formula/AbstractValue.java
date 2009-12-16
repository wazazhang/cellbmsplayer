package com.cell.rpg.formula;

import java.io.Serializable;

public abstract class AbstractValue implements Serializable
{
	abstract public Number getValue();
	
	@Override
	public String toString() {
		return getValue() + "";
	}
}
