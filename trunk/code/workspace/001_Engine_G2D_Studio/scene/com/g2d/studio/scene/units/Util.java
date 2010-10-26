package com.g2d.studio.scene.units;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

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
			Rectangle rect = actor.getGameUnit().local_bounds;
			
			if (editor.getSelectedUnit() == actor)
			{
				int sy = 0;
				g.setColor(Color.WHITE);
				ArrayList<InstanceZoneUnitVisible> vs = actor.getUnit()
						.getAbilities(InstanceZoneUnitVisible.class);
				ArrayList<InstanceZoneUnitKillAction> ks = actor.getUnit()
						.getAbilities(InstanceZoneUnitKillAction.class);
				for (InstanceZoneUnitVisible v : vs) {
					Rectangle fb = Drawing.drawStringBorder(g, "显示 - " + v.dst_value, 
							rect.x, rect.y + sy, rect.width, rect.height, 
							0);
					sy += fb.height + 1;
				}
				for (InstanceZoneUnitKillAction k : ks) {
					Rectangle fb = Drawing.drawStringBorder(g, "死后 - " + k.dst_value, 
							rect.x, rect.y + sy, rect.width, rect.height, 
							0);
					sy += fb.height + 1;
				}
			} else {
				InstanceZoneUnitVisible v = actor.getUnit().getAbility(InstanceZoneUnitVisible.class);
				InstanceZoneUnitKillAction k = actor.getUnit().getAbility(InstanceZoneUnitKillAction.class);
				if (v != null || k != null) {
					g.setColor(Color.YELLOW);
					Drawing.drawStringBorder(g, "SC", 
							rect.x, rect.y, rect.width, rect.height, 
							Drawing.TEXT_ANCHOR_HCENTER | Drawing.TEXT_ANCHOR_TOP);
				}
			}
		}
	}
}
