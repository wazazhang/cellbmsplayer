package com.cell.rpg.formula;

import java.io.Serializable;

public abstract class AbstractValue implements Serializable
{
	abstract public double getValue();
	
	@Override
	public String toString() {
		return getValue() + "";
	}
}
