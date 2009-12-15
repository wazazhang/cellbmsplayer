package com.cell.rpg.formula;

public abstract class AbstractValue 
{

	abstract public double getValue();
	
	@Override
	public String toString() {
		return getValue() + "";
	}
}
