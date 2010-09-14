package com.cell.rpg.scene.script;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.rpg.scene.Scene;
import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.script.anno.EventMethod;
import com.cell.rpg.scene.script.anno.EventParam;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.Studio;

public abstract class SceneScriptManager 
{
	/**
	 * 得到所有事件类型。
	 * @return
	 */
	abstract public Collection<Class<? extends Event>> getEvents();
	
	/**
	 * 根据触发者类型，获取该触发者支持的事件类型
	 * @param trigger_type
	 * @return
	 */
	public Set<Class<? extends Event>> getEvents(Class<? extends Scriptable> trigger_type)
	{
		Set<Class<? extends Event>> ret = new HashSet<Class<? extends Event>>();
		for (Class<? extends Event> evt : Studio.getInstance().getSceneScriptManager().getEvents()) {
			if (asTriggeredObjectType(evt, trigger_type)) {
				ret.add(evt);
			}
		}
		return ret;
	}

	/**
	 * 得到指定类型事件的方法
	 * @param event_type
	 * @return
	 */
	public Collection<Method> getEventMethods(Class<? extends Event> event_type) {
		ArrayList<Method> ret = new ArrayList<Method>();
		for (Method mt : event_type.getMethods()) {
			EventMethod em = mt.getAnnotation(EventMethod.class);
			if (em != null) {
				ret.add(mt);
			}
		}
		return ret;
	}
	
	/**
	 * 检查该事件类型是否支持该触发者类型
	 * @param event_type	事件类型
	 * @param trigger_type	触发者类型
	 * @return
	 */
	public boolean asTriggeredObjectType(Class<? extends Event> event_type, Class<? extends Scriptable> trigger_type) {
		EventType et = event_type.getAnnotation(EventType.class);
		for (Class<? extends Scriptable> st : et.trigger_type()) {
			if (st.isAssignableFrom(trigger_type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 创建脚本模板
	 * @param event_type
	 * @return
	 */
	public String createTemplateScript(
			Class<? extends Event> 	event_type, 
			TriggerGenerator 		tg, 
			SceneTriggerScriptable 	sts) 
	{
		EventType et = event_type.getAnnotation(EventType.class);
		StringBuilder sb = new StringBuilder();
		String ets = "";
		for (Class<?> param : et.trigger_type()) {
			ets += param.getSimpleName() + ", ";
		}
		sb.append("/*******************************************************************************\n");
		sb.append(
				" Comment         : " + et.comment() + "\n" +
				" Create On       : " + CObject.timeToString(System.currentTimeMillis()) + "\n" +
				" Event Class     : " + event_type.getName() + "\n" +
				" Trigger Objects : " + ets + "\n");
		sb.append(" *******************************************************************************/\n");
		sb.append("\n");
		for (Method method : getEventMethods(event_type)) {
			sb.append("/**\n");
			Annotation	params_ats[][]	= method.getParameterAnnotations();
			Class<?> 	params[] 		= method.getParameterTypes();
			for (int i = 0; i < params.length; i++) {
				sb.append(" * arg" + i + " \t- " + params[i].getSimpleName() + ";");
				for (Annotation at : params_ats[i]) {
					if (at.annotationType() == EventParam.class) {
						EventParam ep = (EventParam)at;
						sb.append(CUtil.arrayToString(ep.value()));
					}
				}
				sb.append("\n");
			}
			sb.append(" */\n");
			String args ="";
			for (int i = 0; i < params.length; i++) {
				args += "arg"+i;//params[i].getSimpleName();
				if (i < params.length - 1) {
					args += ", ";
				}
			}
			if (method.getReturnType() != Void.TYPE) {
				sb.append("var " + method.getName() + " = function(" + args + ")\n");
			} else {
				sb.append("function " + method.getName() + "(" + args + ")\n");
			}
			sb.append("{\n");
			sb.append("	// TODO\n");
			sb.append("	\n");
			sb.append("}\n");
			sb.append("\n");
		}
		
		return sb.toString();
	}

//	-----------------------------------------------------------------------------------------------------------------------
	
	private File createTemplateScriptFile(
			File 					root,
			TriggerGenerator 		tg, 
			SceneTriggerScriptable 	sts) 
	{
		if (tg instanceof Scene) {
			return new File(root, 
					tg.getClass().getSimpleName() + "." + 
					sts.getName() + ".js");
		} else {
			return new File(root, 
					tg.getClass().getSimpleName() + "." + 
					tg.getTriggerObjectName() + "." + 
					sts.getName() + ".js");
		}
	}
	
	public void loadTriggers(TriggerGenerator tg, File root) {
		try {
			for (SceneTrigger st : tg.getTriggers()) {
				if (st instanceof SceneTriggerScriptable) {
					SceneTriggerScriptable sts = (SceneTriggerScriptable) st;
					File sf = Studio.getInstance().getSceneScriptManager().createTemplateScriptFile(
							root, tg, sts);
					sts.loadEditScript(sf);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	public void saveTriggers(TriggerGenerator tg, File root) {
		try {
			for (SceneTrigger st : tg.getTriggers()) {
				if (st instanceof SceneTriggerScriptable) {
					SceneTriggerScriptable sts = (SceneTriggerScriptable) st;
					File sf = Studio.getInstance().getSceneScriptManager().createTemplateScriptFile(
							root, tg, sts);
					sts.saveEditScript(sf);
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
}
