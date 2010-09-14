package com.g2d.studio.scene.script;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cell.rpg.scene.SceneTrigger;
import com.cell.rpg.scene.SceneTriggerScriptable;
import com.cell.rpg.scene.TriggerGenerator;
import com.cell.rpg.scene.script.Scriptable;
import com.cell.rpg.scene.script.trigger.Event;

import com.g2d.studio.Config;
import com.g2d.studio.Studio;
import com.g2d.studio.scene.script.TriggerPanel.TriggerEventRoot;
import com.g2d.studio.swing.G2DTree;
import com.g2d.studio.swing.G2DTreeNode;
import com.g2d.studio.swing.G2DWindowToolBar;
import com.g2d.util.TextEditor;

@SuppressWarnings("serial")
public class TriggerPanelScriptable extends TriggerPanel<SceneTriggerScriptable> implements ActionListener, AncestorListener
{
	TextEditor			script_text = new TextEditor();
//	G2DWindowToolBar	tool_g2d	= new G2DWindowToolBar(this);
	JLabel				lbl			= new JLabel();
	
	public TriggerPanelScriptable(
			SceneTriggerScriptable 		trigger, 
			Class<? extends Scriptable>	trigger_object_type,
			TriggerGenerator 			root_object)
	{
		super(trigger, trigger_object_type, root_object);
		
//		tool_g2d.setFloatable(false);
//		getMainPanel().add(tool_g2d, BorderLayout.NORTH);
		
		String text = trigger.getScript();
		if (text == null || text.isEmpty()) {
			text = "";
			for (Class<? extends Event> evt : trigger.getEventTypes()) {
				text += Studio.getInstance().getSceneScriptManager().createTemplateScript(
						evt, root_object, trigger) + "\n";
			}
		}
		script_text.setText(text);
		script_text.setPreferredSize(new Dimension(200, 400));
		Font font = new Font(Config.DEFAULT_FONT, 
				script_text.getFont().getStyle(), 
				script_text.getFont().getSize());
		script_text.getTextPane().setFont(font);
		getMainPanel().add(script_text, BorderLayout.CENTER);
		this.addAncestorListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	@Override
	public void ancestorRemoved(AncestorEvent event) {
		super.ancestorRemoved(event);
		if (!script_text.getText().isEmpty()) {
			this.trigger.setScript(script_text.getText());
		}
	}
	
	protected void onTreeSelectChanged(TriggerTreeView tree_view, TreeNode node) {
		
	}
	
	protected void onAddEventNode(TriggerEventRoot.EventNode en) {
		String append =
			script_text.getText() + 
			Studio.getInstance().getSceneScriptManager().createTemplateScript(
					en.evt, root_object, trigger) + "\n";
		script_text.setText(append);
	}

	protected void onRemoveEventNode(TriggerEventRoot.EventNode en) {
		
	}
	
}
