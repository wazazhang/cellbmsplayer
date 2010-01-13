package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import com.cell.reflect.Parser;
import com.cell.rpg.formula.helper.IFormulaAdapter;
import com.g2d.annotation.Property;

public abstract class AbstractMethod extends AbstractValue 
{
//	------------------------------------------------------------------------------------------------------------------------------
	
	@Property("方法名")
	public String				method_name;
	
	@Property("参数列表")
	public AbstractValue[]		parameters;

//	------------------------------------------------------------------------------------------------------------------------------
	
	public AbstractMethod() {}

	public AbstractMethod(String method_name, AbstractValue[] parameters) 
	{
		this.method_name	= method_name;
		this.parameters		= parameters;
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

	/**
	 * Get Edit Methods
	 * @return
	 */
	abstract protected Map<String, Method> getMethods();
	
//	------------------------------------------------------------------------------------------------------------------------------

	transient Method	call_method;
	
	transient Class<?>	call_params_type[];
	
	final public void setMethod(Method method) {
		method_name 		= method.getName();
		call_method 		= method;
		call_params_type 	= method.getParameterTypes();
	}
	
	final public Method getMethod() {
		if (call_method == null) {
			call_method 			= getMethods().get(method_name);
			if (call_method != null) {
				call_params_type	= call_method.getParameterTypes();
			}
		}
		return call_method;
	}
	
	final public Object[] getInvokeParams(IFormulaAdapter adapter) throws Throwable {
		Method method = getMethod();
		if (method!=null && parameters!=null) {
			Object[] params = new Object[parameters.length];
			for (int i=0; i<parameters.length; i++) {
				params[i] = Parser.castNumber(
						parameters[i].getValue(adapter), 
						call_params_type[i]);
			}
			return params;
		} else {
			return new Object[0];
		}
	}

//	------------------------------------------------------------------------------------------------------------------------------

	static public boolean validateMethod(Method method) {
		if (!Parser.isNumber(method.getReturnType())) {
			return false;
		}
		for (Class<?> parm : method.getParameterTypes()) {
			if (!Parser.isNumber(parm)) {
				return false;
			}
		}
		return true;
	}
}
