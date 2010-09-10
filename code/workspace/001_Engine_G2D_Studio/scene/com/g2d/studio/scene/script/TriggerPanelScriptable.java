package com.g2d.studio.scene.script;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.TextEditor;

@SuppressWarnings("serial")
public class TriggerPanelScriptable extends TriggerPanel<SceneTriggerScriptable>
{
	TextEditor script_text = new TextEditor();

	public TriggerPanelScriptable(SceneTriggerScriptable trigger, Class<? extends Scriptable> trigger_object_type)
	{
		super(trigger, trigger_object_type);
		
		String text = "";
		for (Class<? extends Event> evt : trigger.getEventTypes()) {
			text += Studio.getInstance().getSceneScriptManager().createTemplateScript(evt) + "\n";
		}
		script_text.setText(text);
		getMainPanel().add(new JScrollPane(script_text));
	}
	
	protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node) {
		
	}
	
	protected void onAddEventNode(TriggerEventRoot.EventNode en) {
	
		String append =
			script_text.getText() + "\n" + 
			Studio.getInstance().getSceneScriptManager().createTemplateScript(en.evt);
		script_text.setText(append);
	}
	
	
	
}
