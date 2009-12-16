package com.cell.rpg.formula;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.sql.SQLFieldGroup;
import com.cell.util.Pair;
import com.g2d.annotation.Property;

@Property("数学-运算")
public class Arithmetic extends AbstractValue
{
	@Property("被运算数")
	public AbstractValue	left	= new Value(1);
	@Property("运算数")
	public AbstractValue	right	= new Value(1);
	@Property("运算符")
	public Operator			op		= Operator.ADD;
	
	@Override
	public Number getValue() 
	{
		if (op != null) {
			switch(op) {
			case ADD: return left.getValue().doubleValue() + right.getValue().doubleValue();
			case SUB: return left.getValue().doubleValue() - right.getValue().doubleValue();
			case MUL: return left.getValue().doubleValue() * right.getValue().doubleValue();
			case DIV: return left.getValue().doubleValue() / right.getValue().doubleValue();
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "(" + left + " " + op + " " + right + ")";
	}
	
//	---------------------------------------------------------------------------------------------

	public static enum Operator
	{
		ADD("+"),
		SUB("-"),
		MUL("x"),
		DIV("/"),
		MOD("%"),
		;

		final private String text;
		private Operator(String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return text;
		}
	}
	
//	---------------------------------------------------------------------------------------------

}
