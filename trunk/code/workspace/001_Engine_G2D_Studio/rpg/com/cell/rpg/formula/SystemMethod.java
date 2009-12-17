package com.cell.rpg.formula;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;

import com.g2d.annotation.Property;

@Property("系统-函数")
public class SystemMethod extends AbstractMethod
{
	public SystemMethod() {}
	
	public SystemMethod(String method_name, AbstractValue[] parameters) {
		super(method_name, parameters);
	}
	
	@Override
	public LinkedHashMap<String, Method> getStaticMethods() {
		return getMethods();
	}
	
//	------------------------------------------------------------------------------------------------------------

	static private LinkedHashMap<String, Method> methods;
	
	static public LinkedHashMap<String, Method> getMethods() 
	{
		if (methods == null) {
			methods = new LinkedHashMap<String, Method>();
			for (Method method : SystemMethod.class.getMethods()) {
				if ((method.getModifiers() & Modifier.STATIC) != 0) {
					if (method.getReturnType().equals(Double.class) || 
						method.getReturnType().equals(double.class) ||
						method.getReturnType().equals(Long.class) ||
						method.getReturnType().equals(Integer.class)
						) {
						methods.put(method.getName(), method);
						System.out.println(method);
					}
				}
			}
		}
		return methods;
	}

//	------------------------------------------------------------------------------------------------------------

	public static interface ISystemMethodAdapter
	{
		public Long getGameTime();
		
		public Integer getTotalPlayer();
	}
	
	private static ISystemMethodAdapter system_method_adapter;

	public static ISystemMethodAdapter getSystemMethodAdapter() {
		return system_method_adapter;
	}
	
	public static void setSystemMethodAdapter(ISystemMethodAdapter systemMethodAdapter) {
		system_method_adapter = systemMethodAdapter;
	}
	
//	------------------------------------------------------------------------------------------------------------

	
	

	public static Long getGameTime()
	{
		return system_method_adapter.getGameTime();
	}
	
	public static Integer getTotalPlayer()
	{
		return system_method_adapter.getTotalPlayer();
	}
	
}

