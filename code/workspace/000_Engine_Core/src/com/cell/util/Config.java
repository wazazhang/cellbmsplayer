package com.cell.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.cell.CIO;
import com.cell.CUtil;
import com.cell.reflect.Parser;


public abstract class Config
{
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Config config, String file)
	{
		return load(config, config.getClass(), file, true);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, String file)
	{
		return load(config_class, file, true);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	public static Map<Field, Object> load(Class<? extends Config> config_class, String file, boolean verbos)
	{
		return load((Config)null, config_class, file, verbos);
	}
	
	/**
	 * 将 Config 中的所有静态字段全部初始化
	 * @param config_class
	 * @param file
	 * @param verbos 打印详细信息
	 * @return 返回静态变量的 名字：值　对应关系
	 */
	protected static Map<Field, Object> load(Config instance, Class<? extends Config> config_class, String file, boolean verbos)
	{
		HashMap<Field, Object> map = new HashMap<Field, Object>();
		
		System.out.println("loading config \"" + config_class.getName() + "\"");
		
		try
		{
			Properties cfg = new Properties();
			cfg.load(CIO.loadData(file));
			
			Field[] fields = config_class.getFields();
			
			for (Field field : fields)
			{
				try
				{
					String v = cfg.get(field.getName());
					Object value = null;
					
					if (v != null) {
						value = Parser.stringToObject(v.trim(), field.getType());
						if (value != null) {
							try {
								field.set(instance, value);
								map.put(field, value);
							} catch (Exception e) {
								value = null;
							}
						}
					}
					if (verbos)
					{
						if (v == null)
						{
								System.out.println("\t"+ //
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " + // 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   " + //
										" (default)");// 
						}
						else if (value == null)
						{
								System.err.println("\t"+// 
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " +// 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   " +// 
										" (bad exchange \"" + v + "\", set default)");// 
						}
						else
						{
								System.out.println("\t"+// 
										CUtil.snapStringRightSize(field.getName(),		32, ' ') + " = " + // 
										CUtil.snapStringRightSize(field.get(instance)+"",	32, ' ') + "   ");// 
						}
					}
					
					
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("load config completed !");
		return map;
	}
	
}
