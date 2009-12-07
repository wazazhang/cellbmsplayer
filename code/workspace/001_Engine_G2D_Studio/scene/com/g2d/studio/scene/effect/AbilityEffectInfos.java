package com.g2d.studio.scene.effect;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import com.cell.rpg.ability.AbstractAbility;
import com.cell.rpg.quest.ability.QuestAccepter;
import com.cell.rpg.quest.ability.QuestPublisher;
import com.cell.rpg.scene.Actor;
import com.cell.rpg.scene.SceneUnit;
import com.cell.rpg.scene.ability.ActorDropItem;
import com.g2d.studio.res.Res;
import com.g2d.studio.scene.units.SceneUnitTag;

public class AbilityEffectInfos<T extends SceneUnit> {

	Vector<AbilityEffectInfo> effects = new Vector<AbilityEffectInfo>();
	
	public AbilityEffectInfos(Class<?>[] types, BufferedImage[] images) 
	{
		for (int i=0; i<types.length; i++) {
			AbilityEffectInfo effect = new AbilityEffectInfo(types[i], images[i]);
			effects.add(effect);
		}
	}
	
	public void updateActor(SceneUnitTag<T> unit) {
		
		ArrayList<AbilityEffectInfo> deffects = new ArrayList<AbilityEffectInfo>(effects.size());
		for (AbilityEffectInfo e : effects) {
			if (e.updateActor(unit)) {
				deffects.add(e);
			}
		}
		if (!deffects.isEmpty()) {
			int 	div		= 20;
			double	alphad	= Math.PI / div;
			double	alphat	= unit.getGameUnit().timer * alphad;
			int		index	= (int)(alphat / Math.PI) % deffects.size();
			for (int i=0; i<deffects.size(); i++) {
				AbilityEffectInfo effect = deffects.get(i);
				if (i == index) {			
					effect.alpha = (float)Math.abs(Math.sin(unit.getGameUnit().timer * alphad));
					effect.visible = true;
				} else {
					effect.visible = false;
				}
			}
		}
	}
	
}
