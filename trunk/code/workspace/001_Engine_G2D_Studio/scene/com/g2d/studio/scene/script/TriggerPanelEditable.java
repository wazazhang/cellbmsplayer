package com.g2d.studio.scene.script;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerEditable;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.trigger.Event;
import com.g2d.studio.Studio;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.util.TextEditor;

@SuppressWarnings("serial")
public class TriggerPanelEditable extends TriggerPanel<SceneTriggerEditable>
{
	public TriggerPanelEditable(
			SceneTriggerEditable 		trigger, 
			Class<? extends Scriptable>	trigger_object_type, 
			TriggerGenerator 			root_object)
	{
		super(trigger, trigger_object_type, root_object);
	}
	
	protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node) {
		
	}
	
	protected void onAddEventNode(TriggerEventRoot.EventNode en) {
	
	
	}
	
	protected void onRemoveEventNode(TriggerEventRoot.EventNode en) {
		
	}
	
	
}
