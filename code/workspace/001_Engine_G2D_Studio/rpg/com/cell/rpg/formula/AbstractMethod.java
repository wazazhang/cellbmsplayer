package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import com.cell.reflect.Parser;
import com.g2d.annotation.Property;

public abstract class AbstractMethod extends AbstractValue
{
//	------------------------------------------------------------------------------------------------------------------------------
	
	
	@Property("方法名")
	public String				method_name;
	
	@Property("参数列表")
	public AbstractValue[]		parameters;

//	------------------------------------------------------------------------------------------------------------------------------
	
	transient private Method	call_method;
	
	transient private Class<?>	call_params_type[];
	

//	------------------------------------------------------------------------------------------------------------------------------

	public AbstractMethod() {}

	public AbstractMethod(String method_name, AbstractValue[] parameters) 
	{
		this.method_name	= method_name;
		this.parameters		= parameters;
	}
		
//	------------------------------------------------------------------------------------------------------------------------------

	public abstract LinkedHashMap<String, Method> getStaticMethods();
	
	public void setMethod(Method method) {
		method_name = method.getName();
		call_method = method;
		call_params_type = method.getParameterTypes();
	}
	
	public Method getMethod() {
		if (call_method == null) {
			call_method = getStaticMethods().get(method_name);
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
	public Number getValue() {
		try{
			Method method = getMethod();
			if (method!=null) {
				Object ret = method.invoke(null, getInvokeParams());
				return (Number)ret;
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
