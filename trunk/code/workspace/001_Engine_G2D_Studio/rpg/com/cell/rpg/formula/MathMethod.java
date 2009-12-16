package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.g2d.annotation.Property;

@Property("数学-函数")
public class MathMethod extends AbstractMethod
{
	
	public MathMethod() {}
	
	public MathMethod(String method_name, AbstractValue[] parameters) {
		super(method_name, parameters);
	}

	@Override
	public LinkedHashMap<String, Method> getStaticMethods() {
		return getMethods();
	}

//	------------------------------------------------------------------------------------------------
	
	static private LinkedHashMap<String, Method> methods;
	
	static public LinkedHashMap<String, Method> getMethods() 
	{
		if (methods == null) {
			methods = new LinkedHashMap<String, Method>();
			for (Method method : Math.class.getMethods()) {
				if ((method.getModifiers() & Modifier.STATIC) != 0) {
					if (method.getReturnType().equals(Double.class) || 
						method.getReturnType().equals(double.class)) {
						methods.put(method.getName(), method);
						System.out.println(method);
					}
				}
			}
		}
		return methods;
	}

	public static void main(String args[])
	{
		getMethods() ;
	}
	
}
