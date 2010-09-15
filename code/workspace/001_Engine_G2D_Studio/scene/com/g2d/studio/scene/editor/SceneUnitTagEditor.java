package com.g2d.studio.scene.editor;

import java.io.File;

import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.res.Res;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.script.TriggersEditor;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitTagEditor extends DisplayObjectEditor<Unit>
{
	private static final long serialVersionUID = 1L;

	TriggersEditor triggers_editor;
	
	public SceneUnitTagEditor(
			SceneEditor se,
			SceneUnitTag<?> unit, 
			AbilityCellEditAdapter<?> ... adapters)
	{
		super(unit.getGameUnit(),
				new RPGObjectPanel(unit.getUnit()), 
				new AbilityPanel(unit.getUnit(),
						adapters));
		super.setSize(800, 500);
		this.setIconImage(Res.icon_edit);
		
//		this.triggers_editor = new TriggersEditor(new TriggerGenerateTreeNode(unit.getUnit()));
//		this.table.addTab("事件触发器", this.triggers_editor);
	}
}
