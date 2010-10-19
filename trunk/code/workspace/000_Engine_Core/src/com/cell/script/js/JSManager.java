package com.cell.script.js;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.cell.CIO;
import com.cell.CUtil;

public class JSManager
{
	final protected String script_name;

	final protected ScriptEngineManager vm_sem;
	
//	-----------------------------------------------------------------------------------------------

	public JSManager() {
		this("JavaScript");
	}
	
	public JSManager(String script_name) {
		this("JavaScript", Thread.currentThread().getContextClassLoader());
	}

	public JSManager(ClassLoader class_loader) {
		this("JavaScript", class_loader);
	}
	
	public JSManager(String script_name, ClassLoader class_loader) {
		this.script_name 	= script_name;
		this.vm_sem 		= new ScriptEngineManager(class_loader);
	}
	
//	-----------------------------------------------------------------------------------------------
	
	/**
	 * 创建一个脚本接口
	 * @param <T>
	 * @param path
	 * @param type
	 * @return
	 */
	public <T> T getInterface(String path, Class<T> type)
	{
		String script 	= readString(path);
		String root   	= CUtil.replaceString(path, "\\", "/");
		root 			= root.substring(0, path.lastIndexOf('/'));			
		return getInterface(script, root, type);
	}

	/**
	 * 创建一个脚本接口
	 * @param <T>
	 * @param script
	 * @param root
	 * @param type
	 * @return
	 */
	public <T> T getInterface(String script, String root, Class<T> type)
	{
		try {
			// 创建虚拟机
			ScriptEngine vm_engine = vm_sem.getEngineByName(script_name);
			System.out.println("create script engine : " + script_name + " : " + vm_engine);
			// 执行脚本
			script = importScript(vm_engine, root, script);
			eval(vm_engine, script);

			// 执行方法并传递参数
			Invocable vm_inv = (Invocable) vm_engine;
			T adapter = vm_inv.getInterface(type);
			return adapter;
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 可覆盖，从地址读入脚本，子类可以用来缓存资源
	 * @param path
	 * @return
	 */
	protected String readString(String path)
	{
		return CIO.readAllText(path, "UTF-8");
	}
	
	/**
	 * 查询脚本里所有 importScript(path); 语句，并连接脚本<br>
	 * 比如：
	 * importScript(lib/util.js);
	 * @param vm_engine
	 * @param root
	 * @param script
	 * @return
	 * @throws ScriptException
	 */
	protected String importScript(ScriptEngine vm_engine, String root, String script) throws ScriptException 
	{
		HashSet<String> readed_path	= new LinkedHashSet<String>();
		HashMap<String, String> libs = new HashMap<String, String>();
		
		for (int i = 0; i < script.length(); i++) {
			int start = script.indexOf("importScript", i);
			if (start >= 0) {
				i = start;
				int end = script.indexOf(';', start);
				if (end >= 0) {
					i = end;
					String import_cmd	= script.substring(start, end + 1);		
					if (!libs.containsKey(import_cmd)) {
						String lib_js = null;
						System.out.println("js : " + import_cmd);
						try {
							String import_path = import_cmd.substring(import_cmd.indexOf('(') + 1, import_cmd.indexOf(')')).trim();
							while (import_path.startsWith("/")) {
								import_path = import_path.substring(1);
							}
							import_path = import_cmd.replace('"', ' ');
							import_path = import_cmd.trim();
							if (!readed_path.contains(import_path)) {
								readed_path.add(import_path);
								lib_js = readString(root + "/" + import_path);
								if (lib_js != null) {
									eval(vm_engine, lib_js);
								} else {
									System.err.println("js : error load : " + import_cmd);
								}
							}
						} catch (Exception err) {
							System.err.println("js : error load : " + import_cmd);
							err.printStackTrace();
						} finally {
							libs.put(import_cmd, lib_js);
						}
					}
				}
			}
		}
		for (String import_cmd : libs.keySet()) {
			script = CUtil.replaceString(script, import_cmd, "");
		}
		return script;
	}
	
	/**
	 * 编译链接脚本
	 * @param vm_engine
	 * @param script
	 * @throws ScriptException
	 */
	protected void eval(ScriptEngine vm_engine, String script) throws ScriptException {
		if (vm_engine instanceof Compilable) {
			Compilable 		compilable 	= (Compilable)vm_engine;
			CompiledScript 	compiled 	= compilable.compile(script);
			compiled.eval(vm_engine.getContext());
		} else {
			vm_engine.eval(script);
		}
	}
	
	
	
	
}
