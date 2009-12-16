package com.cell.rpg.quest;

import com.cell.rpg.formula.AbstractValue;
import com.cell.rpg.formula.ObjectProperty;
import com.g2d.annotation.Property;

@Property("任务-状态")
public class QuestStateProperty extends AbstractValue
{
	@Property("任务ID")
	public int					quest_id;
	
	@Property("任务状态")
	public QuestStateField 		trigger_unit_field;
	
	public QuestStateProperty(int quest_id, QuestStateField field) {
		this.trigger_unit_field = field;
	}
	public QuestStateProperty() {}

	@Override
	public String toString() {
		return "任务" + quest_id + trigger_unit_field;
	}

	@Override
	public Number getValue() {
		return value;
	}
	
	transient private long value;
	
	public void setValue(long value) {
		this.value = value;
	}
}
