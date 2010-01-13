package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.cell.reflect.Parser;
import com.cell.rpg.formula.helper.IFormulaAdapter;
import com.g2d.annotation.Property;

/**
 * 将静态函数包装成变量
 * @author WAZA
 *
 */
public abstract class StaticMethod extends AbstractMethod
{
//	------------------------------------------------------------------------------------------------------------------------------
	
	public StaticMethod() {}

	public StaticMethod(String method_name, AbstractValue[] parameters) 
	{
		super(method_name, parameters);
	}
	
}
