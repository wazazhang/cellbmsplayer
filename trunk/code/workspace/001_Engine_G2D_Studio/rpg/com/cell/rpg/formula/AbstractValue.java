package com.cell.rpg.formula;

import java.io.Serializable;

import com.cell.rpg.formula.helper.IFormulaAdapter;

public abstract class AbstractValue implements Serializable
{
	final public Number getValue(IFormulaAdapter adapter){
		return adapter.getValue(this);
	}

	abstract public String toString();
}
