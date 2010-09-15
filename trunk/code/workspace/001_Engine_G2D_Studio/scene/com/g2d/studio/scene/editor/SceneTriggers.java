package com.g2d.studio.scene.editor;

import java.awt.BorderLayout;

import com.g2d.studio.res.Res;
import com.g2d.studio.scene.script.TriggersEditor;
import com.g2d.util.AbstractDialog;

@SuppressWarnings("serial")
public class SceneTriggers extends AbstractDialog
{
	TriggersEditor triggers;
	
	public SceneTriggers(SceneEditor root) {
		super(root);
		super.setSize(800, 600);
		super.setIconImage(Res.icon_action);
		super.setCenter();
		super.setLayout(new BorderLayout());
		
		this.triggers = new TriggersEditor(
				root.getSceneNode().getData().getTriggersPackage());
		this.add(triggers, BorderLayout.CENTER);
	}
}
