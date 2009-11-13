package com.g2d.studio.scene.editor;

import com.g2d.editor.DisplayObjectEditor;
import com.g2d.game.rpg.Unit;
import com.g2d.studio.rpg.AbilityPanel;
import com.g2d.studio.rpg.RPGObjectPanel;
import com.g2d.studio.rpg.AbilityPanel.AbilityCellEditAdapter;
import com.g2d.studio.scene.units.SceneUnitTag;

public class SceneUnitTagEditor extends DisplayObjectEditor<Unit>
{
	private static final long serialVersionUID = 1L;

	public SceneUnitTagEditor(
			SceneUnitTag<?> unit, 
			AbilityCellEditAdapter<?> ... adapters)
	{
		super(unit.getGameUnit(),
				new RPGObjectPanel(unit.getUnit()), 
				new AbilityPanel(unit.getUnit(),
						adapters));
		super.setSize(800, 500);
		
	}
}
