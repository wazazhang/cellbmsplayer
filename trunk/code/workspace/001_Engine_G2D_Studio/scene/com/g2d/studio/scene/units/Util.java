package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitKillAction;
import com.cell.rpg.instance.zones.ability.InstanceZoneUnitVisible;
import com.cell.rpg.scene.SceneUnit;
import com.g2d.studio.scene.editor.SceneEditor;
import com.g2d.util.Drawing;

public class Util 
{

	public static void drawScript(Graphics2D g, SceneEditor editor, SceneUnitTag<?> actor) 
	{
		if (editor.isShowScript()) 
		{
			int count = actor.getUnit().getAbilitiesCount();
			if (count > 0) {
				Rectangle rect = actor.getGameUnit().local_bounds;
				if (editor.getSelectedUnit() == actor) {
					g.setColor(Color.WHITE);
				} else {
					g.setColor(Color.YELLOW);
				}
				Drawing.drawStringBorder(g, count+"能力", 
						rect.x, rect.y, rect.width, rect.height, 
						Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
			}
		}
	}
	
	public static String getTip(SceneEditor editor, SceneUnitTag<?> actor) {
		StringBuilder sb = new StringBuilder(actor.getListName() + "\n");
		for (AbstractAbility a : actor.getUnit().getAbilities()) {
			sb.append(" " + a + "\n");
		}
		return sb.toString();
	}
}
