package com.g2d.studio.scene.script;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.script.Scriptable;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;

@SuppressWarnings("serial")
public class TriggerPanelScriptable extends TriggerPanel<SceneTriggerScriptable>
{

	public TriggerPanelScriptable(SceneTriggerScriptable trigger, Class<? extends Scriptable> trigger_object_type) {
		super(trigger, trigger_object_type);
	}
	
	protected void onTreeSelectChanged(
			TriggerTreeView tree_view, 
			TreeNode node) {
		
	}
}
