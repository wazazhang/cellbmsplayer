package com.cell.rpg.quest;

import com.cell.rpg.formula.ObjectProperty;
import com.g2d.annotation.Property;

@Property("单位属性")
public class TriggerUnitProperty extends ObjectProperty
{
	@Property("单位类型")
	public TriggerUnitType trigger_unit_type;
	
	@Override
	public String toString() {
		return trigger_unit_type+"."+super.toString();
	}
}
