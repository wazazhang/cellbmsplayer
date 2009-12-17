package com.cell.rpg.formula.helper;

import java.lang.reflect.Method;

import com.cell.rpg.formula.AbstractMethod;
import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.Arithmetic;
import com.cell.rpg.formula.ObjectProperty;
import com.cell.rpg.formula.TimeDurationValue;
import com.cell.rpg.formula.TimeValue;
import com.cell.rpg.formula.Value;
import com.cell.rpg.formula.SystemMethod.ISystemMethodAdapter;

/**
 * 基础的计算工具
 * @author WAZA
 *
 */
public abstract class AFormulaAdapterImpl implements IFormulaAdapter
{
	
	public Number getValue(AbstractValue value)
	{
		if (value instanceof Value) {
			return calculateValue((Value)value);
		}
		if (value instanceof TimeValue) {
			return calculateTimeValue((TimeValue)value);
		}
		if (value instanceof TimeDurationValue) {
			return calculateTimeDurationValue((TimeDurationValue)value);
		}
		if (value instanceof AbstractMethod) {
			return calculateAbstractMethod((AbstractMethod)value);
		}
		if (value instanceof Arithmetic) {
			return calculateArithmetic((Arithmetic)value);
		}
		if (value instanceof ObjectProperty) {
			return calculateObjectProperty((ObjectProperty)value);
		}
		return 0;
	}

	protected Number calculateValue(Value value)
	{
		return value.value;
	}
	
	protected Number calculateTimeValue(TimeValue value) 
	{
		return value.time;
	}
	
	protected Number calculateTimeDurationValue(TimeDurationValue value)
	{
		if (value.time_unit!=null) {
			return value.time_unit.toMillis(value.duration);
		}
		return value.duration;
	}
	
	protected Number calculateAbstractMethod(AbstractMethod value)
	{
		try{
			Method method = value.getMethod();
			if (method!=null) {
				Object ret = method.invoke(null, value.getInvokeParams(this));
				return (Number)ret;
			}
		}catch(Exception err){
			err.printStackTrace();
		}
		return 0;
	}
	
	protected Number calculateArithmetic(Arithmetic value) 
	{
		if (value.op != null) {
			return value.op.calculat(value.left.getValue(this), value.right.getValue(this));
		}
		return 0;
	}
	
	abstract protected Number calculateObjectProperty(ObjectProperty value) ;
	
	
	
	abstract protected ISystemMethodAdapter getSystemMethodAdapter();
	
	
	
}
