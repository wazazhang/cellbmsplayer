package com.cell.rpg.scene;

import java.io.File;
import java.io.Serializable;

import com.cell.CIO;
import com.cell.io.CFile;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.anno.EventType;
import com.cell.rpg.scene.script.trigger.Event;

/**
 * 自定义脚本触发器
 * @author WAZA
 */
public class SceneTriggerScriptable extends SceneTrigger implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private transient String script = null;
	
	public SceneTriggerScriptable(TriggerGenerator parent, String name) throws Exception {
		super(parent, name);
	}
	
	/**
	 * 设置编辑器生命周期的脚本, 该数据不会被序列化。
	 * @param script
	 */
	public void loadEditScript(File file) {
		if (file.exists()) {
			this.script = CFile.readText(file, "UTF-8");
		}
	}
	
	/**
	 * 设置编辑器生命周期的脚本, 该数据不会被序列化。
	 * @param script
	 */
	public void saveEditScript(File file) {
		if (script != null) {
			file.getParentFile().mkdirs();
			CFile.writeText(file, this.script, "UTF-8");
		}
	}

	/**
	 * 得到编辑器生命周期的脚本, 该数据不会被序列化。
	 * @param script
	 */
	public String getScript() {
		return script;
	}
	
	/**
	 * 设置编辑器生命周期的脚本, 该数据不会被序列化。
	 * @param script
	 */
	public void setScript(String script) {
		this.script = script;
	}
}
