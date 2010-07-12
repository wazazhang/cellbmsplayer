package com.cell.script.js;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.cell.CIO;
import com.cell.CUtil;

public class JSManager
{
//	-----------------------------------------------------------------------------------------------
	
	public <T> T getInterface(String path, Class<T> type)
	{
		String script 	= CIO.readAllText(path, "UTF-8");
		String root   	= CUtil.replaceString(path, "\\", "/");
		root 			= root.substring(0, path.lastIndexOf('/'));			
//		script			= importScript(root, script);

		try {
			// 创建虚拟机
			ScriptEngineManager vm_sem 		= new ScriptEngineManager();
			ScriptEngine 		vm_engine 	= vm_sem.getEngineByName("JavaScript");
			
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
	
	protected void eval(ScriptEngine vm_engine, String script) throws ScriptException {
		if (vm_engine instanceof Compilable) {
			Compilable 		compilable 	= (Compilable)vm_engine;
			CompiledScript 	compiled 	= compilable.compile(script);
			compiled.eval(vm_engine.getContext());
		} else {
			vm_engine.eval(script);
		}
	}
	
	protected String importScript(ScriptEngine vm_engine, String root, String script) throws ScriptException 
	{
		HashSet<String> readed_path = new LinkedHashSet<String>();
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
							if (!readed_path.contains(import_path)) {
								readed_path.add(import_path);
								lib_js = CIO.readAllText(root + "/" + import_path, "UTF-8");
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
}
