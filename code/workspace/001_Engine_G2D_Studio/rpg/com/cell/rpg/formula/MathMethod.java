package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.cell.CUtil;
import com.cell.reflect.Parser;
import com.g2d.annotation.Property;

@Property("数学函数")
public class MathMethod extends AbstractValue
{
	final public static HashMap<String, Method> methods = new HashMap<String, Method>();
	static {
		for (Method method : Math.class.getMethods()) {
			methods.put(method.getName(), method);
			method.getParameterTypes();
		}
	}

//	------------------------------------------------------------------------------------------------
	
	@Property("方法名")
	public String				method_name;
	
	@Property("参数列表")
	public AbstractValue[]		parameters;
	
	transient private Method	call_method;
	transient private Class<?>	call_params_type[];
//	------------------------------------------------------------------------------------------------
	
	public MathMethod() {}
	
	public void setMethod(Method method) {
		method_name = method.getName();
		call_method = method;
		call_params_type = method.getParameterTypes();
	}
	
	public Method getMethod() {
		if (call_method == null) {
			call_method = methods.get(method_name);
			call_params_type = call_method.getParameterTypes();
		}
		return call_method;
	}
	
	public Object[] getInvokeParams() {
		Method method = getMethod();
		if (method!=null && parameters!=null) {
			Object[] params = new Object[parameters.length];
			for (int i=0; i<parameters.length; i++) {
				params[i] = Parser.castNumber(
						parameters[i].getValue(), 
						call_params_type[i]);
			}
			return params;
		} else {
			return new Object[0];
		}
	}
	
	@Override
	public double getValue() {
		try{
			Method method = getMethod();
			if (method!=null) {
				method.invoke(null, getInvokeParams());
			}
		}catch(Exception err){}
		return 0;
	}
	
	@Override
	public String toString() {
		String ret = method_name + "(";
		if (parameters!=null) {
			for (int i=0; i<parameters.length; i++) {
				ret += parameters[i];
				if (i != parameters.length - 1) {
					ret += ", ";
				}
			}
		}
		return ret + ")";
	}

}
