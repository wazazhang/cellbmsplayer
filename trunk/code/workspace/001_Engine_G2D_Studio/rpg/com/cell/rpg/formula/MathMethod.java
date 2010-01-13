package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.g2d.annotation.Property;

@Property("数学-函数")
public class MathMethod extends StaticMethod
{
//	------------------------------------------------------------------------------------------------
	
	public MathMethod() {}
	
	public MathMethod(String method_name, AbstractValue[] parameters) {
		super(method_name, parameters);
	}

	public LinkedHashMap<String, Method> getMethods() {
		return getStaticMethods();
	}
	
//	------------------------------------------------------------------------------------------------
//	Edit Mode
	static private LinkedHashMap<String, Method> methods;

	public static LinkedHashMap<String, Method> getStaticMethods() {
		if (methods == null) {
			methods = new LinkedHashMap<String, Method>();
			for (Method method : Math.class.getMethods()) {
				if ((method.getModifiers() & Modifier.STATIC) != 0) {
					if (method.getReturnType().equals(Double.class) || 
						method.getReturnType().equals(double.class)) {
						if (validateMethod(method)) {
							methods.put(method.getName(), method);
							System.out.println("MathMethod - " + method);
						}
					}
				}
			}
		}
		return methods;
	}
//	
}
